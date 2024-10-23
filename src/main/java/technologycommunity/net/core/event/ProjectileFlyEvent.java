package technologycommunity.net.core.event;

import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class ProjectileFlyEvent extends Event {
    public static final HandlerList handlerList = new HandlerList();

    private final @NotNull Projectile projectile;

    public ProjectileFlyEvent(final @NotNull Projectile projectile) {
        this.projectile = projectile;
    }

    public @NotNull Projectile getProjectile() {
        return projectile;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
