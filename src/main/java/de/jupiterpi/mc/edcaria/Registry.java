package de.jupiterpi.mc.edcaria;

import de.jupiterpi.mc.edcaria.warp.WarpCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public class Registry {
    private JavaPlugin plugin = EdcariaPlugin.plugin;

    public void registerCommands() {
        // misc
        cmd("warp", new WarpCommand());
    }
    private void cmd(String cmd, CommandExecutor exec) {
        plugin.getCommand(cmd).setExecutor(exec);
    }
}