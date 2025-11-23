package com.xdd.serverPlugin;

import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.window.Window;

@Getter
public abstract class Menu {

    private final ServerPlugin plugin;

    public Menu(ServerPlugin plugin) {
        this.plugin = plugin;
    }

    public void openMenu(Player player) {
        Window.single().setViewer(player).setGui(setupGui()).setTitle(setupTitle()).open(player);
    }

    public abstract String setupTitle();

    public abstract Gui setupGui();
}
