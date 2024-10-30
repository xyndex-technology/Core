package technologycommunity.net.core.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import technologycommunity.net.core.color.Corelor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drawer {
    private final List<Button> buttons;
    private final String title;
    private final Integer size;
    private final Player player;

    private Drawer(List<Button> buttons, String title, Integer size, Player player) {
        this.buttons = buttons;
        this.title = title;
        this.size = size;
        this.player = player;
    }

    protected static Drawer from(List<Button> buttons, String title, Integer size, Player player) {
        return new Drawer(buttons, title, size, player);
    }

    private int getMaxPageFromButtons(List<Button> buttons) {
        return buttons.stream()
                .mapToInt(button -> button.getPosition().getPage())
                .max()
                .orElse(1);
    }

    private List<Button> getAllButtonsFromPage(List<Button> buttons, Integer page) {
        return buttons.stream()
                .filter(button -> button.getPosition().getPage().equals(page))
                .toList();
    }

    protected Map<Integer, Inventory> draw() {
        final Map<Integer, Inventory> pages = new HashMap<>();

        final Integer maxPage = this.getMaxPageFromButtons(this.buttons);

        for (int page = 1; page <= maxPage; page ++)
        {
            final String pageTitle = Corelor.format(
                    this.title
                            .replace("{current_page}", String.valueOf(page))
                            .replace("{maximum_page}", String.valueOf(maxPage)));

            final Inventory newPage = Bukkit.createInventory(null,
                    this.size * 9,
                    pageTitle);

            pages.put(page, newPage);
        }

        for (Map.Entry<Integer, Inventory> pageEntry : pages.entrySet())
        {
            for (final Button button : this.buttons)
                if (button.getPosition().getPage().equals(pageEntry.getKey()))
                    pageEntry.getValue().setItem(button.getPosition().getSlot(), button.getItem(this.player));
        }

        return pages;
    }
}
