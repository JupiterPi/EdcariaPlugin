package de.jupiterpi.mc.edcaria.edcoins;

import de.jupiterpi.mc.edcaria.EdcariaPlugin;
import de.jupiterpi.mc.edcaria.edcoins.coins.Coin;
import de.jupiterpi.mc.edcaria.edcoins.coins.Coins;
import de.jupiterpi.mc.edcaria.edcoins.wallet.Wallet;
import de.jupiterpi.mc.edcaria.edcoins.wallet.Wallets;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WalletEventListener implements Listener {
    @EventHandler
    public static void onPlayerInventoryClick(InventoryClickEvent event) {
        // check right click
        if (!event.isRightClick()) return;

        ItemStack clickedItem = event.getCurrentItem();
        ItemStack cursorItem = event.getCursor();

        // check item type
        if (clickedItem.getType() != Material.MOJANG_BANNER_PATTERN) return;
        if (cursorItem.getType() != Material.IRON_NUGGET) return;

        // check true item
        if (!Wallets.isWallet(clickedItem)) return;
        if (!Coins.isCoin(cursorItem)) return;

        Wallet wallet = new Wallet(clickedItem);
        Coin coin = new Coin(cursorItem);

        float worth = wallet.getWorth() + coin.getTotalWorth();
        Bukkit.getScheduler().runTask(EdcariaPlugin.plugin, () -> {
            wallet.setWorth(worth);
        });
        coin.setAmount(0);

        event.setCancelled(true);
    }

    public static void register() {
        Bukkit.getServer().getPluginManager().registerEvents(new WalletEventListener(), EdcariaPlugin.plugin);
    }
}
