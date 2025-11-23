package com.xdd.serverPlugin.cuboids.camp.generator;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import com.xdd.serverPlugin.ConstantValues;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.MessageUtils;
import com.xdd.serverPlugin.Utils.TextUtils;
import com.xdd.serverPlugin.cache.AdminActionsManager;
import com.xdd.serverPlugin.locations.ServerWorlds;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CampGenerator {

    private final int amount;
    private final Player player;
    private final World adaptedWorld;
    private final File schematicFile;
    private final Clipboard clipboard;
    private final ServerPlugin plugin;
    private final static int MAX_AMOUNT_BEFORE_BREAK = 25;
    private final static int BREAK_AMOUNT_SECONDS = 60;
    private final AdminActionsManager adminActionsManager;

    @Getter @Setter private boolean paused = false;
    @Getter private BukkitRunnable pasteTask;

    public CampGenerator(int amount, Player player) {
        this.amount = amount;
        this.player = player;
        this.plugin = ServerPlugin.getInstance();
        adminActionsManager = plugin.getAdminActionsManager();

        schematicFile = new File(plugin.getDataFolder(), "oboz.schem");
        if(!schematicFile.exists()){
            throw new NullPointerException("Nie znaleziono pliku schematic");
        }
        adaptedWorld = BukkitAdapter.adapt(ServerWorlds.CAMP_WORLD.getWorld());
        clipboard = loadSchematic();

        if(clipboard == null){
            MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(("Clipboard nie został zainicjalizowany," +
                    " nie udało się skopiować .schem"), TextColor.color(0xFF7070)));
            throw new IllegalStateException("Nie udało się załadować schematu, clipboard jest null.");
        }
    }

    /**
     * Uruchamia zadanie sekwencyjnego wklejania N schematów (zdefiniowanych w 'amount').
     * Operacje są rozłożone w czasie, aby nie przeciążyć serwera.
     */
    public void pasteSchematics(){
        adminActionsManager.registerCampGeneration(player, this);

        new BukkitRunnable() {
            private int schematicsStarted = 0; // Licznik rozpoczętych wklejeń

            @Override
            public void run() {
                if(pasteTask == null) {
                    pasteTask = this;
                }

                // Sprawdź, czy zakończono
                if (schematicsStarted >= amount) {
                    MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                            "Zakończono kolejkę generowania %d wysp.".formatted(amount), TextColor.color(0x45FF4A)));
                    adminActionsManager.unregisterCampGeneration(player);
                    this.cancel();
                    return;
                }

                if(paused) return;

                // --- GŁÓWNY WĄTEK ---
                // 1. Znajdź następne wolne miejsce
                final Location pasteLocation;
                try {
                    pasteLocation = FreePlaceFinder.findNextFreeCenter();

                    if (pasteLocation == null) {
                        MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                                "Nie można znaleźć wolnego miejsca na wyspę nr %d! Przerywanie.".formatted(schematicsStarted + 1), TextColor.color(0xFF7070)));
                        this.cancel();
                        return;
                    }
                    plugin.getLocationSaveManager().addLocation(pasteLocation, false);
                    plugin.getLocationSaveManager().save();

                } catch (Exception e) {
                    MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                            "Krytyczny błąd podczas szukania miejsca: " + e.getMessage(), TextColor.color(0xFF7070)));
                    e.printStackTrace();
                    this.cancel();
                    return;
                }

                schematicsStarted++;
                pasteSchematicAt(pasteLocation, schematicsStarted); //// Przekazujemy pasteLocation

                if(schematicsStarted > 0 && (schematicsStarted % MAX_AMOUNT_BEFORE_BREAK) == 0){
                    paused = true;

                    MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                            "Wklejono %d wysp, będę kontynuował za %ds".formatted(schematicsStarted, BREAK_AMOUNT_SECONDS), TextColor.color(0xAAAAFF)));
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        paused = false;
                    }, 20L * BREAK_AMOUNT_SECONDS); // Używamy 20L * BREAK_AMOUNT_SECONDS, żeby było w tickach
                }

            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    public void resume(){
        paused = false;
        MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                "Wznawiam generację obozów...", TextColor.color(0xAAAAFF)));
    }

    public void pause() {
        paused = true;
        MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                "Pauzuje generację obozów...", TextColor.color(0xAAAAFF)));
    }

    public void stop(){
        if (pasteTask != null) {
            pasteTask.cancel();
        }
        MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                "Przerywam generację obozów...", TextColor.color(0xAAAAFF)));
        adminActionsManager.unregisterCampGeneration(player);
    }


    /**
     * Asynchronicznie wkleja pojedynczy schemat w podanej lokalizacji.
     *
     * @param targetLocation Lokalizacja w świecie, która ma być środkiem schematu.
     * @param schematicNumber Numer porządkowy (dla wiadomości dla gracza)
     */
    private void pasteSchematicAt(Location targetLocation, int schematicNumber) {

        // Utworzenie ClipboardHolder (używamy go dwukrotnie, więc lepiej raz)
        ClipboardHolder clipboardHolder = new ClipboardHolder(clipboard);

        // 1. Obliczanie punktu wklejenia w świecie (Prawy Górny Róg schematu)
        BlockVector3 targetVector = BukkitAdapter.asBlockVector(targetLocation);
        final BlockVector3 centerOffset = getCenteredOffsetXZ(clipboardHolder);

        // Wektor wklejenia = Wektor celu (środek) - Offset środka schematu
        final BlockVector3 pasteLocationVector = targetVector.subtract(centerOffset);
        final BlockVector3 idealVector = BlockVector3.at(pasteLocationVector.x(), ConstantValues.CENTER_CAMP_Y, pasteLocationVector.z());

        MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                "Rozpoczynanie wklejania wyspy nr %d...".formatted(schematicNumber), TextColor.color(0xAAAAFF)));

        // 2. Uruchomienie ciężkiej operacji w osobnym wątku
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            // --- WĄTEK ASYNCHRONICZNY ---
            final long start = System.currentTimeMillis();

            try (final EditSession editSession = WorldEdit.getInstance().newEditSession(adaptedWorld)) {

                final Operation pasteOperation = clipboardHolder
                        .createPaste(editSession)
                        .to(idealVector) // Prawidłowy punkt wklejenia
                        .build();

                // To jest główna, czasochłonna operacja.
                Operations.complete(pasteOperation);

                final long duration = System.currentTimeMillis() - start;

                Bukkit.getScheduler().runTask(plugin, () -> {
                    MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                            "Wyspa nr %d została wklejona w %dms w lokalizacji X:%d, Y:%d, Z:%d.".formatted(
                                    schematicNumber, duration, idealVector.x(),
                                    idealVector.y(), idealVector.z()), TextColor.color(0x45FF4A)));
                });
            } catch (WorldEditException e) {
                // 4. Obsługa błędów WorldEdit - wyślij wiadomość na główny wątek
                Bukkit.getScheduler().runTask(plugin, () -> {
                    MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                            "Błąd WorldEdit przy wyspie nr %d: %s".formatted(schematicNumber, e.getMessage()), TextColor.color(0xFF7070)));
                });
                e.printStackTrace();
            } catch (Exception e) {
                // 5. Obsługa wszystkich innych błędów
                Bukkit.getScheduler().runTask(plugin, () -> {
                    MessageUtils.sendToPlayerAndConsole(player, TextUtils.formatMessage(
                            "Nieoczekiwany błąd przy wyspie nr %d: %s".formatted(schematicNumber, e.getMessage()), TextColor.color(0xFF7070)));
                });
                e.printStackTrace();
            }
        });
    }

    private BlockVector3 getCenteredOffsetXZ(ClipboardHolder clipboardHolder) {

        Clipboard primaryClipboard = clipboardHolder.getClipboards().getFirst();
        Region region = primaryClipboard.getRegion();


        // 1. Obliczamy środek X i Z (jako Vector3)
        com.sk89q.worldedit.math.Vector3 center = region.getCenter();

        int offsetY = region.getMinimumY() - ConstantValues.CENTER_CAMP_Y;
        // 3. Budujemy nowy BlockVector3 z wyśrodkowanym X/Z i bazowym Y
        return BlockVector3.at(
                center.getBlockX(), // Wyśrodkowany X
                offsetY,
                center.getBlockZ()  // Wyśrodkowany Z
        );
    }

    private Clipboard loadSchematic() {
        final ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
        if (format == null) return null;
        try (final ClipboardReader reader = format.getReader(new FileInputStream(schematicFile))) {
            return reader.read();
        } catch (IOException e) {
            plugin.getLogger().severe("Nie udało się wczytać pliku schematic: " + schematicFile.getName());
            e.printStackTrace();
            throw new RuntimeException("Błąd podczas wczytywania schematic", e);
        }
    }
}