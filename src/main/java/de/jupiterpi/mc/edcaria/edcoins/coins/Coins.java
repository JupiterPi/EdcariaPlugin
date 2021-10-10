package de.jupiterpi.mc.edcaria.edcoins.coins;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Arrays;
import java.util.List;

public class Coins {
    public static final String LINE1 = "Edcoin";
    public static final String LINE2_START = "EDC $";

    public static ItemStack makeCoin(int amount, float worth) {
        ItemStack item = new ItemStack(Material.IRON_NUGGET, amount);
        item.editMeta(meta -> {
            meta.lore(Arrays.asList(
                    Component.text(LINE1, TextColor.color(94, 94, 94)),
                    Component.text(LINE2_START + worth, TextColor.color(94, 94, 94))
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

    public static boolean isCoin(ItemStack item) {
        if (item.getType() != Material.IRON_NUGGET) return false;
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
        // quarters
        /* diamond -> quarters */ Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:quarters_from_diamond"), makeQuarter(4)).addIngredient(Material.DIAMOND));
        /* quarters -> diamond */ Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:diamond_from_quarters"), new ItemStack(Material.DIAMOND, 1)).addIngredient(4, makeQuarter(1)));

        // tenths
        /* quarters -> tenths */  Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:tenths_from_quarters"), makeTenth(5)).addIngredient(2, makeQuarter(1)));
        /* tenths -> quarters */  Bukkit.getServer().addRecipe(new ShapelessRecipe(NamespacedKey.fromString("edcaria:quarters_from_tenths"), makeQuarter(2)).addIngredient(5, makeTenth(1)));
    }
}
