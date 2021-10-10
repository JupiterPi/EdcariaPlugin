package de.jupiterpi.mc.edcaria.edcoins.coins;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Coin {
    private ItemStack item;

    public Coin(ItemStack item) {
        this.item = item;
    }

    public float getTotalWorth() {
        return getAmount() * getPieceWorth();
    }

    public int getAmount() {
        return item.getAmount();
    }

    public void setAmount(int amount) {
        item.setAmount(amount);
    }

    public float getPieceWorth() {
        List<Component> lore = item.getItemMeta().lore();
        String line2 = ((TextComponent) lore.get(1)).content();
        return Float.parseFloat(line2.substring(5));
    }
}
