package com.xdd.serverPlugin.gui_items;

import com.xdd.serverPlugin.SpecificItems;
import com.xdd.serverPlugin.Utils.GuiUtils;
import com.xdd.serverPlugin.cuboids.camp.Camp;
import com.xdd.serverPlugin.records.UuidNick;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class PermissionItem extends AbstractItem {
    private final Camp camp;
    private final String permission;
    private boolean isPermTrue;
    private UuidNick uuidNick;

    public PermissionItem(Camp camp, String permission) {
        this.camp = camp;
        this.permission = permission;
        isPermTrue = camp.getPermissions().contains(permission);
    }

    public PermissionItem(Camp camp, String permission, UuidNick uuidNick) {
        this.camp = camp;
        this.permission = permission;
        this.uuidNick = uuidNick;
        isPermTrue = camp.getPermissionsPerPlayer().containsKey(uuidNick) && camp.getPermissionsPerPlayer().get(uuidNick).contains(permission);
    }


    @Override
    public ItemProvider getItemProvider() {
        if(isPermTrue){
            return new ItemBuilder(SpecificItems.FromGUI.permTrueItem(permission, uuidNick));
        } else {
            return new ItemBuilder(SpecificItems.FromGUI.permFalseItem(permission, uuidNick));
        }
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        isPermTrue = !isPermTrue;

      if(uuidNick == null) {

          if (isPermTrue) {
              camp.getPermissions().add(permission);
              GuiUtils.playPermOnSound(player);
          } else {
              camp.getPermissions().remove(permission);
              GuiUtils.playPermOffSound(player);
          }
      } else {
          List<String> perms = camp.getPermissionsPerPlayer().computeIfAbsent(uuidNick, k -> new ArrayList<>());

          if (isPermTrue) {
              if (!perms.contains(permission)) perms.add(permission);
              GuiUtils.playPermOnSound(player);
          } else {
              perms.remove(permission);
              GuiUtils.playPermOffSound(player);
          }
      }
        notifyWindows();
    }
}
