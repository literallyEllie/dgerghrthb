package me.randomHashTags.RandomPackage.api.versions;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class v1_9 {

    public static ItemStack getItemInHand(Player player) {
        if (player.getInventory().getItemInMainHand() == null) {
            return new ItemStack(Material.AIR);
        } else {
            return player.getInventory().getItemInMainHand();
        }
    }

    public static void setItemInHand(Player player, ItemStack item) {
        player.getInventory().setItemInMainHand(item);
    }

    public static void setupPotion(PotionMeta meta, PotionType type, boolean extended, boolean upgraded) {
        meta.setBasePotionData(new PotionData(type, extended, upgraded));
    }
}
