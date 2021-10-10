package de.jupiterpi.mc.edcaria.edcoins.wallet;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.List;

public class Wallets {
    public static final String LINE1 = "Edcoin Wallet";
    public static final String LINE2_START = "EDC $";

    public static ItemStack makeWallet() {
        ItemStack item = new ItemStack(Material.MOJANG_BANNER_PATTERN);
        item.editMeta(meta -> {
            meta.lore(Arrays.asList(
                    Component.text(LINE1, TextColor.color(94, 94, 94)),
                    Component.text(LINE2_START + "0", TextColor.color(94, 94, 94))
            ));
        });
        return item;
    }

    public static boolean isWallet(ItemStack item) {
        if (item.getType() != Material.MOJANG_BANNER_PATTERN) return false;
        List<Component> lore = item.getItemMeta().lore();
        if (lore == null) return false;
        try {
            String line1 = ((TextComponent) lore.get(0)).content();
            String line2 = ((TextComponent) lore.get(1)).content();
            return line1.equals(LINE1) && line2.startsWith(LINE2_START);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    public static void register() {
        // wallet
        Bukkit.getServer().addRecipe(new ShapedRecipe(NamespacedKey.fromString("edcaria:wallet"), makeWallet())
                .shape("sl ", "l l", " l ")
                .setIngredient('s', Material.STRING)
                .setIngredient('l', Material.LEATHER)
        );
    }
}
