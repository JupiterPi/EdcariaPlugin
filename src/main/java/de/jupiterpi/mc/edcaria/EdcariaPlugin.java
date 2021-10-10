package de.jupiterpi.mc.edcaria;

import de.jupiterpi.mc.edcaria.coins.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class EdcariaPlugin extends JavaPlugin {
    public static JavaPlugin plugin = null;

    @Override
    public void onEnable() {
        plugin = this;

        Registry registry = new Registry();
        registry.registerCommands();
        registry.registerEvents();

        ItemManager.registerCraftingRecipes();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Edcaria] Plugin enabled");

        super.onEnable();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Edcaria] Plugin disabled");

        super.onDisable();
    }
}
