package me.randomHashTags.RandomPackage.api.versions;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.api.enums.RPConfigs;
import me.randomHashTags.RandomPackage.listeners.RPListeners;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class v1_8 {

    public static ItemStack getItemInHand(Player player) {
        if (player.getInventory().getItemInHand() == null) {
            return new ItemStack(Material.AIR);
        } else {
            return player.getInventory().getItemInHand();
        }
    }

    public static void setItemInHand(Player player, ItemStack item) {
        player.getInventory().setItemInHand(item);
    }
    //


    public static void spawnFallenHero(PlayerInteractEvent event, int gkit) {
        FileConfiguration config = RandomPackage.getConfig(RPConfigs.MASTER);
        for (String string : RandomPackage.getConfig(RPConfigs.MESSAGES).getStringList("fallen-heroes.summon")) {
            if (string.contains("{FALLEN_HERO_NAME}")) {
                string = string.replace("{FALLEN_HERO_NAME}", ChatColor.translateAlternateColorCodes('&', config.getString("fallen-heros." + gkit + ".name")));
            }
            event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
        Skeleton skeleton = (Skeleton) event.getClickedBlock().getWorld().spawn(new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getBlockY() + 1, event.getClickedBlock().getLocation().getZ()), Skeleton.class);
        skeleton.setCanPickupItems(false);
        skeleton.setSkeletonType(SkeletonType.WITHER);
        skeleton.getEquipment().setItemInHand(new ItemStack(Material.AIR));
        skeleton.setCustomNameVisible(true);
        skeleton.setCustomName(ChatColor.translateAlternateColorCodes('&', config.getString("fallen-heros.name").replace("{FALLEN_HERO_NAME}", config.getString("fallen-heros." + gkit + ".name"))));
        for (ItemStack is : RPListeners.getInstance().getGkitItems(gkit)) {
            if (!(is == null)) {
                if (is.getType().name().endsWith("HELMET") || is.getType().name().endsWith("CHESTPLATE") || is.getType().name().endsWith("LEGGINGS") || is.getType().name().endsWith("BOOTS")
                        || is.getType().name().endsWith("SWORD") || is.getType().name().endsWith("AXE") || is.getType().name().endsWith("SPADE") || is.getType().name().endsWith("BOW") || is.getType().name().endsWith("HOE")) {
                    RPListeners.getInstance().addVanillaAndRandomPackageEnchants(event.getPlayer(), is, false, -1);
                }
                if (is.getType().name().endsWith("HELMET")) {
                    skeleton.getEquipment().setHelmet(is);
                } else if (is.getType().name().endsWith("CHESTPLATE")) {
                    skeleton.getEquipment().setChestplate(is);
                } else if (is.getType().name().endsWith("LEGGINGS")) {
                    skeleton.getEquipment().setLeggings(is);
                } else if (is.getType().name().endsWith("BOOTS")) {
                    skeleton.getEquipment().setBoots(is);
                } else if (is.getType().name().endsWith("SWORD") && skeleton.getEquipment().getItemInHand().getType().equals(Material.AIR)
                        || is.getType().name().endsWith("_AXE") && skeleton.getEquipment().getItemInHand().getType().equals(Material.AIR)
                        || is.getType().name().endsWith("BOW") && skeleton.getEquipment().getItemInHand().getType().equals(Material.AIR)) {
                    skeleton.getEquipment().setItemInHand(is);
                }
            }
        }
        skeleton.setTarget(event.getPlayer());
    }

}
