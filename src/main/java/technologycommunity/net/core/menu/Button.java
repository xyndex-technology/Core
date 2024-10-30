package technologycommunity.net.core.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.jetbrains.annotations.NotNull;

import technologycommunity.net.core.menu.structures.ButtonPosition;

public abstract class Button {
    public Button() {

    }

    public abstract void onButtonClick(final @NotNull Player clicker, final @NotNull Menu clickedMenu);
    public abstract @NotNull ButtonPosition getPosition();
    public abstract @NotNull ItemStack getItem(final @NotNull Player viewer);
}
