package net.haraxx.usetotemflag.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.haraxx.usetotemflag.TotemFlagPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public class ResurrectListener implements Listener {
    @EventHandler
    public void onResurrect(EntityResurrectEvent resurrectEvent) {
        Player player;
        if (!(resurrectEvent.getEntity() instanceof Player))
            return;
        player = (Player) resurrectEvent.getEntity();

        RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery regionQuery = regionContainer.createQuery();

        if (!regionQuery.testState(BukkitAdapter.adapt(player.getLocation()), null, TotemFlagPlugin.USE_TOTEM_FLAG)) {
            resurrectEvent.setCancelled(true);
        }
    }
}
