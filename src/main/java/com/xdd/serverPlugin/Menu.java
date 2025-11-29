package com.xdd.serverPlugin;

import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

@Getter
public abstract class Menu {

    private final ServerPlugin plugin;
    private Window window;

    public Menu(ServerPlugin plugin) {
        this.plugin = plugin;
    }

    public void openMenu(Player player) {
        window = Window.single().setViewer(player).setGui(setupGui()).setTitle(setupTitle()).build();
        window.open();
    }

    public abstract String setupTitle();

    public abstract Gui setupGui();

    public abstract void playOpenSound();
}
