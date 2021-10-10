package de.jupiterpi.mc.edcaria.edcoins.wallet;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Wallet {
    private ItemStack item;

    public Wallet(ItemStack item) {
        this.item = item;
    }

    public float getWorth() {
        List<Component> lore = item.getItemMeta().lore();
        String line2 = ((TextComponent) lore.get(1)).content();
        return Float.parseFloat(line2.substring(5));
    }

    public void setWorth(float worth) {
        item.editMeta(meta -> {
            meta.lore(Arrays.asList(
                    Component.text(Wallets.LINE1, TextColor.color(94, 94, 94)),
                    Component.text(Wallets.LINE2_START + worth, TextColor.color(94, 94, 94))
            ));
        });
    }
}
