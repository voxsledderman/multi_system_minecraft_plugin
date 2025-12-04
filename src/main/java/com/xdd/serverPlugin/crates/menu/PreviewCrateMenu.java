package com.xdd.serverPlugin.crates.menu;

import com.xdd.serverPlugin.Menu;
import com.xdd.serverPlugin.ServerPlugin;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.crates.Crate;
import com.xdd.serverPlugin.gui_items.ClickableItem;
import com.xdd.serverPlugin.gui_items.NextPageItem;
import com.xdd.serverPlugin.gui_items.PrevPageItem;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.builder.ItemBuilder;


public class PreviewCrateMenu extends Menu {

    private Menu returnItemMenu = null;
    private final Crate crate;

    public PreviewCrateMenu(ServerPlugin plugin, @Nullable Menu returnItemMenu, Crate crate) {
        super(plugin);
        this.returnItemMenu = returnItemMenu;
        this.crate = crate;
    }

    public PreviewCrateMenu(ServerPlugin plugin, Crate crate) {
        super(plugin);
        this.crate = crate;
    }

    @Override
    public String setupTitle() {
        return "";
    }

    @Override
    public Gui setupGui() {
        if(returnItemMenu == null) {
            return PagedGui.items().setStructure(
                            ". . . . . . . . #",
                            ". . i i i i i . .",
                            "< . i i i i i . >",
                            ". . i i i i i . .",
                            "# . . . . . . . #"
                    )
                    .addIngredient('#', GuiUtils.whiteGlass)
                    .addIngredient('.', GuiUtils.blackGlass)
                    .addIngredient('>', new NextPageItem(GuiUtils.nextPageIconNoBackground, GuiUtils.blackGlass.getItemProvider().get()))
                    .addIngredient('<', new PrevPageItem(GuiUtils.prevPageIconNoBackground, GuiUtils.blackGlass.getItemProvider().get()))
                    .addIngredient('i', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                    .setContent(crate.getItemRewards())
                    .build();
        } else {
            return PagedGui.items().setStructure(
                            "B . . . . . . . #",
                            ". . i i i i i . .",
                            "< . i i i i i . >",
                            ". . i i i i i . .",
                            "# . . . . . . . #"
                    )
                    .addIngredient('#', GuiUtils.whiteGlass)
                    .addIngredient('.', GuiUtils.blackGlass)
                    .addIngredient('>', new NextPageItem(GuiUtils.nextPageIconNoBackground, GuiUtils.blackGlass.getItemProvider().get()))
                    .addIngredient('<', new PrevPageItem(GuiUtils.prevPageIconNoBackground, GuiUtils.blackGlass.getItemProvider().get()))
                    .addIngredient('B', new ClickableItem(new ItemBuilder(GuiUtils.returnItemNoBackground).setDisplayName("Powrót").get(), returnItemMenu))
                    .addIngredient('i', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                    .setContent(crate.getItemRewards())
                    .build();
        }
    }

    @Override
    public void playOpenSound() {

    }
}
