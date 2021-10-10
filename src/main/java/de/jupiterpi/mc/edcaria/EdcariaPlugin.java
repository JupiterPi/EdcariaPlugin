package de.jupiterpi.mc.edcaria;

import de.jupiterpi.mc.edcaria.edcoins.WalletEventListener;
import de.jupiterpi.mc.edcaria.edcoins.coins.Coins;
import de.jupiterpi.mc.edcaria.edcoins.wallet.Wallets;
import de.jupiterpi.mc.edcaria.warp.WarpCommand;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class EdcariaPlugin extends JavaPlugin {
    public static JavaPlugin plugin = null;

    @Override
    public void onEnable() {
        plugin = this;

        WarpCommand.register();
        Coins.register();
        Wallets.register();
        WalletEventListener.register();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Edcaria] Plugin enabled");

        super.onEnable();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Edcaria] Plugin disabled");

        super.onDisable();
    }
}
