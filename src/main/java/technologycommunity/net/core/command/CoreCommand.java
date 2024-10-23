package technologycommunity.net.core.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;
import technologycommunity.net.core.color.Corelor;
import technologycommunity.net.core.cooldown.Cooldown;
import technologycommunity.net.core.cooldown.structures.Data;
import technologycommunity.net.core.plugin.Core;
import technologycommunity.net.core.plugin.Remain;

import java.util.List;
import java.util.UUID;

public abstract class CoreCommand extends Command {
    private final @NotNull UUID uuid;
    private final @NotNull String label;

    protected static class Messages {
        public static @NotNull String playerOnly = "&cThis command is allowed only for players.";
    }

    public CoreCommand(final @NotNull String label) {
        super(label);

        this.uuid = UUID.randomUUID();
        this.label = label;

        this.setPermission(Core.getInstance().getClass().getSimpleName() + ".commands." + label);
    }

    public final boolean register() {
        return Remain.registerCommand(this);
    }

    /*
        Return true if cooldown is need, false if cooldown is not need.
        Make sure if you use cooldown you use getCooldown() function!
     */
    protected boolean onCommand(final @NotNull Player executor, final @NotNull String label, final @NotNull String[] arguments) {
        return false;
    }

    @Override
    public final boolean execute(final @NotNull CommandSender sender, final @NotNull String label, final @NotNull String[] arguments) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Corelor.format(Messages.playerOnly));
            return true;
        }

        final double cooldown = Cooldown.get(new Data(player.getUniqueId(), this.uuid));
        final double commandCooldown = this.getCooldown(player, arguments);

        if (commandCooldown > 0) {
            if (cooldown > 0) {
                sender.sendMessage(Corelor.format(this.getCooldownMessage(cooldown)));
            } else {
                final boolean shouldCooldown = this.onCommand(player, label, arguments);

                if (shouldCooldown)
                    Cooldown.create(new Data(player.getUniqueId(), this.uuid), commandCooldown);
            }

            return true;
        }

        this.onCommand(player, label, arguments);

        return true;
    }

    protected double getCooldown(final @NotNull Player player, final @NotNull String[] arguments) {
        return 0;
    }

    protected @NotNull String getCooldownMessage(final double cooldown) {
        return "&cYou are on cooldown! Please wait " + cooldown + " second(s).";
    }

    protected @NotNull List<String> onTabComplete(final @NotNull Player player, @NotNull String alias, @NotNull String[] arguments) {
        return List.of();
    }

    @Override
    public final @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        if (!(sender instanceof Player player))
            return List.of();

        return this.onTabComplete(player, alias, args);
    }
}
