package de.jupiterpi.mc.edcaria.coins;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ItemManager {
    public static ItemStack makeCoin(int amount, float worth) {
        ItemStack item = new ItemStack(Material.IRON_NUGGET, amount);
        item.editMeta(meta -> {
            meta.lore(Arrays.asList(
                    Component.text("Edcoin", TextColor.color(94, 94, 94)),
                    Component.text("EDC $" + worth, TextColor.color(94, 94, 94))
            ));
        });
        return item;
    }

    public static ItemStack makeQuarter(int amount) {
        return makeCoin(amount, 0.25f);
    }

    public static ItemStack makeTenth(int amount) {
        return makeCoin(amount, 0.1f);
    }

    public static ItemStack makeWallet() {
        ItemStack item = new ItemStack(Material.MOJANG_BANNER_PATTERN);
        item.editMeta(meta -> {
            meta.lore(Arrays.asList(
                    Component.text("Edcoin Wallet", TextColor.color(94, 94, 94)),
                    Component.text("EDC $0", TextColor.color(94, 94, 94))
            ));
        });
        return item;
    }

    public static boolean modifyWallet(ItemStack item, Function<Float, Float> modify) {
        if (item.getType() == Material.MOJANG_BANNER_PATTERN) {
            List<Component> lore = item.getItemMeta().lore();
            if (lore != null) {
                try {
                    String line1 = ((TextComponent) lore.get(0)).content();
                    String line2 = ((TextComponent) lore.get(1)).content();
                    if (line1.equals("Edcoin Wallet")) {
                        float amount = getWorth(item);
                        amount = modify.apply(amount);
                        setWorth(item, amount);
                    }
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }
        return false;
    }

    public static float getWorth(ItemStack item) {
        List<Component> lore = item.getItemMeta().lore();
        try {
            String line1 = ((TextComponent) lore.get(0)).content();
            String line2 = ((TextComponent) lore.get(1)).content();
            if (line1.startsWith("Edcoin")) {
                if (line2.startsWith("EDC $")) {
                    float amount = Float.parseFloat(line2.substring(5));
                    return amount;
                }
            }
        } catch (IndexOutOfBoundsException e) {}
        return -1.0f;
    }

    public static void setWorth(ItemStack item, float worth) {
        List<Component> lore = item.getItemMeta().lore();
        try {
            String line1 = ((TextComponent) lore.get(0)).content();
            String line2 = ((TextComponent) lore.get(1)).content();
            if (line1.startsWith("Edcoin")) {
                if (line2.startsWith("EDC $")) {
                    item.editMeta(meta -> {
                        meta.lore(Arrays.asList(
                                Component.text("Edcoin Wallet", TextColor.color(94, 94, 94)),
                                Component.text("EDC $" + worth, TextColor.color(94, 94, 94))
                        ));
                    });
                }
            }
        } catch (IndexOutOfBoundsException e) {}
    }

    public static void registerCraftingRecipes() {
        // quarters
        /* diamond -> quarters */ Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:quarters_from_diamond"), makeQuarter(4)).addIngredient(Material.DIAMOND));
        /* quarters -> diamond */ Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:diamond_from_quarters"), new ItemStack(Material.DIAMOND, 1)).addIngredient(4, makeQuarter(1)));

        // tenths
        /* quarters -> tenths */  Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:tenths_from_quarters"), makeTenth(5)).addIngredient(2, makeQuarter(1)));
        /* tenths -> quarters */  Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:quarters_from_tenths"), makeQuarter(2)).addIngredient(5, makeTenth(1)));

        // wallet
        Bukkit.getServer().addRecipe(new ShapedRecipe(NamespacedKey.fromString("edcaria:wallet"), makeWallet())
                .shape("sl ", "l l", " l ")
                .setIngredient('s', Material.STRING)
                .setIngredient('l', Material.LEATHER)
        );
    }
}
