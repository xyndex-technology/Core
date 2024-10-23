package technologycommunity.net.core.plugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class Remain {
    public static boolean registerCommand(final @NotNull Command command, boolean ex) {
        final CommandMap map = Reflection.getCommandMap();

        if (map.getKnownCommands().containsValue(command)) {
            if (ex)
                Core.getInstance().getCoreLogger().warning("Couldn't register command because it's already been registered. (%s)".formatted(command.getLabel()));
            return false;
        }

        map.register(Core.getInstance().getDescription().getName().toLowerCase(), command);
        return true;
    }

    public static boolean registerCommand(final @NotNull Command command) {
        return registerCommand(command, true);
    }

    public static Field fastAccessField(final @NotNull Class<?> clazz, final @NotNull String name) {
        try {
            final Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException ex) {
            return null;
        }
    }
}
