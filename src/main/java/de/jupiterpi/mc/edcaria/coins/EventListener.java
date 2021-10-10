package de.jupiterpi.mc.edcaria.coins;

import de.jupiterpi.mc.edcaria.EdcariaPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EventListener implements Listener {
    static {
        Bukkit.getServer().getPluginManager().registerEvents(new EventListener(), EdcariaPlugin.plugin);
    }

    @EventHandler
    public static void onPlayerClick(InventoryClickEvent event) {
        if (event.isRightClick()) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem.getType() == Material.MOJANG_BANNER_PATTERN) {
                List<Component> clickedLore = clickedItem.getItemMeta().lore();
                if (clickedLore != null) {
                    try {
                        String clickedLine1 = ((TextComponent) clickedLore.get(0)).content();
                        String clickedLine2 = ((TextComponent) clickedLore.get(1)).content();
                        if (clickedLine1.equals("Edcoin Wallet")) {
                            float worth = ItemManager.getWorth(clickedItem);

                            ItemStack item = event.getCursor();
                            if (item.getType() == Material.IRON_NUGGET) {
                                List<Component> itemLore = item.getItemMeta().lore();
                                if (itemLore != null) {
                                    String itemLine1 = ((TextComponent) itemLore.get(0)).content();
                                    String itemLine2 = ((TextComponent) itemLore.get(1)).content();
                                    if (itemLine1.equals("Edcoin")) {
                                        float addWorth = ItemManager.getWorth(item) * item.getAmount();
                                        event.getCursor().setAmount(0);

                                        Bukkit.getScheduler().runTask(EdcariaPlugin.plugin, () -> {
                                            ItemManager.modifyWallet(clickedItem, amount -> amount + addWorth);
                                        });

                                        event.setCancelled(true);
                                        return;
                                    }
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                }
            }
        }
    }
}
