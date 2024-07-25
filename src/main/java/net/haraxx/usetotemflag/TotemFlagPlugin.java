package net.haraxx.usetotemflag;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import net.haraxx.usetotemflag.listener.ResurrectListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TotemFlagPlugin extends JavaPlugin {
    public static StateFlag USE_TOTEM_FLAG;
    private boolean registerFlagSuccess;

    @Override
    public void onLoad() {
        FlagRegistry flagRegistry = WorldGuard.getInstance().getFlagRegistry();

        try {
            StateFlag useTotemFlag = new StateFlag("use-totem", true);
            flagRegistry.register(useTotemFlag);
            USE_TOTEM_FLAG = useTotemFlag;
            registerFlagSuccess = true;
        } catch (FlagConflictException flagConflictException) {
            Flag<?> existingFlag = flagRegistry.get("use-totem");
            if (existingFlag instanceof StateFlag) {
                USE_TOTEM_FLAG = (StateFlag) existingFlag;
                registerFlagSuccess = true;
            } else {
                registerFlagSuccess = false;
            }
        }

    }

    @Override
    public void onEnable() {
        if (!registerFlagSuccess) {
            getLogger().info("There is already a flag called 'use-totem' which is not a state flag. Disabling now.");
            setEnabled(false);
            return;
        }

        getServer().getPluginManager().registerEvents(new ResurrectListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
