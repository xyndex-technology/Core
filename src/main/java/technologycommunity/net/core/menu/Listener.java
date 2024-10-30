package technologycommunity.net.core.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import technologycommunity.net.core.annotations.AutoRegister;
import technologycommunity.net.core.menu.structures.ButtonPosition;
import technologycommunity.net.core.listener.internal.CoreListener;
import technologycommunity.net.core.structures.Artist;

@AutoRegister
public class Listener extends CoreListener {
    @EventHandler
    public final void onMenuClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Menu menu = Menu.getPlayerMenu(player);

        if (menu == null ||
                event.getClickedInventory() == null ||
                    event.getClickedInventory().getType().equals(InventoryType.PLAYER) && menu.isAllowPlayerInventory())
            return;

        final Integer page = menu.getPage();
        final Integer slot = event.getSlot();

        event.setCancelled(true);

        if (event.getCurrentItem() == null)
            menu.onEmptySlotClick(player, ButtonPosition.of(page, slot));

        final Button button = menu.getButton(ButtonPosition.of(slot, page));

        if (button != null)
            button.onButtonClick(player, menu);
    }

    @EventHandler
    public final void onMenuClose(final InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Menu menu = Menu.getPlayerMenu(player);

        if (menu == null)
            return;

        if (menu.isUpdating()) {
            if (menu.getPage() == menu.getLastPage())
                menu.onMenuPageChange(player, menu, menu.getLastPage(), menu.getPage());
            else menu.onMenuUpdate(player, menu);
        } else {
            Menu.rejectPlayerMenu(player);
            menu.onMenuClose(player, menu);
            menu.restartMenu();
        }
    }
}
