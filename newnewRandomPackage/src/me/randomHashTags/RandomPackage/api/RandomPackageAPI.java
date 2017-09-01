package me.randomHashTags.RandomPackage.api;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.api.enums.RPConfigs;
import me.randomHashTags.RandomPackage.api.enums.RPItem;
import me.randomHashTags.RandomPackage.api.enums.RandomPackageEnchants;
import me.randomHashTags.RandomPackage.listeners.RPListeners;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RandomPackageAPI implements Listener {

    private static RandomPackageAPI instance;
    public RPListeners lapi;
    /*
     *
     * Variables
     *
     */
    public HashMap<String, String> enchants = new HashMap<>();

    public static RandomPackageAPI getInstance() {
        if (instance == null) {
            instance = new RandomPackageAPI();
        }
        return instance;
    }

    @EventHandler
    private void pluginEnableEvent(PluginEnableEvent event) {
        if (event.getPlugin().equals(RandomPackage.getPlugin)) {
            enchants = RPListeners.getInstance().enchants;
            lapi = RPListeners.getInstance();
        }
    }

    public ItemStack getRPItem(RPItem rpi) {
        return lapi.getRPItem(rpi).clone();
    }

    public int getRandomBlackScrollPercent() {
        return lapi.getRandomBlackScrollPercent();
    }

    /*
     *
     *
     *
     */
    public void openShowcase(Player player, Player target) {
        FileConfiguration config;
        if (target == null || target == player) {
            config = getPlayerConfig(player);
            player.openInventory(Bukkit.createInventory(player, config.getInt("showcase.size"), lapi.SHOWCASE_SELF));
        } else {
            config = getPlayerConfig(target);
            player.openInventory(Bukkit.createInventory(player, config.getInt("showcase.size"), lapi.SHOWCASE_OTHER.replace("{PLAYER}", target.getName())));
        }
        for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
            if (!(config.get("showcase.items." + i) == null)) {
                player.getOpenInventory().getTopInventory().setItem(i, config.getItemStack("showcase.items." + i));
            }
        }
    }

    public FileConfiguration getPlayerConfig(Player player) {
        File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "player-data" + File.separator, player.getUniqueId().toString() + ".yml");
        if (!(file.exists())) {
            createPlayerConfig(player);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    private void createPlayerConfig(Player player) {
        File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "player-data" + File.separator, player.getUniqueId().toString() + ".yml");
        FileConfiguration config = new YamlConfiguration();
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("MMMM dd, YYYY; HH:mm:ss; z");
        config.set("date-and-time-file-was-created", format.format(date));
        config.set("first-join-username", player.getName());
        config.set("coinflip.wins", 0);
        config.set("coinflip.losses", 0);
        config.set("duels.ELO", 1000);
        config.createSection("duels.custom-inventories");
        config.set("filter.activated", false);
        config.createSection("filter.items");
        int pSize = 0;
        for (int i = 1; i <= 54; i++) {
            if (player.hasPermission("RandomPackage.showcase." + i)) {
                pSize = i;
            }
        }
        config.set("showcase.permission-size", pSize);
        config.set("showcase.size", 9);
        config.createSection("showcase.items");
        config.set("titles.active", "");
        config.createSection("titles.owned");
        config.createSection("unlocked-kits.vkits");
        config.createSection("unlocked-kits.gkits");
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOriginalEnchantmentName(String string) {
        if (!(RandomPackage.getConfig(RPConfigs.ENCHANTMENTS_INFO).get(string + ".enchant-name") == null)) {
            return string;
        } else {
            string = ChatColor.stripColor(string);
            for (RandomPackageEnchants enchant : RandomPackageEnchants.values()) {
                if (string.toLowerCase().replace(" ", "").startsWith(enchant.name().toLowerCase())
                        || string.toLowerCase().replace(" ", "").startsWith(RandomPackage.getConfig(RPConfigs.ENCHANTMENTS_INFO).getString(enchant.name() + ".enchant-name").toLowerCase().replace(" ", ""))) {
                    return enchant.name();
                }
            }
        }
        return null;
    }

    public int getEnchantmentLevel(String string) {
        if (string.split(" ").length == 2) {
            string = string.split(" ")[1].toLowerCase();
        } else if (string.split(" ").length == 3) {
            string = string.split(" ")[2].toLowerCase();
        } else if (string.split(" ").length == 4) {
            string = string.split(" ")[3].toLowerCase();
        }
        string = string.replace("i", "1").replace("v", "2").replace("x", "3").replaceAll("\\p{L}", "").replace("1", "i").replace("2", "v").replace("3", "x").replaceAll("\\p{N}", "").replaceAll("\\p{P}", "").replaceAll("\\p{S}", "").replaceAll("\\p{M}", "").replaceAll("\\p{Z}", "");
        if (string.equalsIgnoreCase("i")) {
            return 1;
        } else if (string.equalsIgnoreCase("ii")) {
            return 2;
        } else if (string.equalsIgnoreCase("iii")) {
            return 3;
        } else if (string.equalsIgnoreCase("iv")) {
            return 4;
        } else if (string.equalsIgnoreCase("v")) {
            return 5;
        } else if (string.equalsIgnoreCase("vi")) {
            return 6;
        } else if (string.equalsIgnoreCase("vii")) {
            return 7;
        } else if (string.equalsIgnoreCase("viii")) {
            return 8;
        } else if (string.equalsIgnoreCase("ix")) {
            return 9;
        } else if (string.equalsIgnoreCase("x")) {
            return 10;
        } else if (string.equalsIgnoreCase("xi")) {
            return 11;
        } else if (string.equalsIgnoreCase("xii")) {
            return 12;
        } else if (string.equalsIgnoreCase("xiii")) {
            return 13;
        } else if (string.equalsIgnoreCase("xiv")) {
            return 14;
        } else if (string.equalsIgnoreCase("xv")) {
            return 15;
        } else if (string.equalsIgnoreCase("xvi")) {
            return 16;
        } else if (string.equalsIgnoreCase("xvii")) {
            return 17;
        } else if (string.equalsIgnoreCase("xviii")) {
            return 18;
        } else if (string.equalsIgnoreCase("xix")) {
            return 19;
        } else if (string.equalsIgnoreCase("xx")) {
            return 20;
        } else {
            return -1;
        }
    }

    public ArrayList<String> getEnchantmentsOnItem(ItemStack is) {
        ArrayList<String> enchants = new ArrayList<>();
        if (!(is == null) && is.hasItemMeta() && is.getItemMeta().hasLore()) {
            List<String> itemlore = is.getItemMeta().getLore();
            for (String string : itemlore) {
                for (String enchant : this.enchants.keySet()) {
                    String level = string.split(" ")[string.split(" ").length - 1]
                            .replace("X", "10").replace("IX", "9").replace("VIII", "8").replace("VII", "7").replace("VI", "6").replace("V", "5").replace("IV", "4").replace("III", "3").replace("II", "2").replace("I", "1")
                            .replace("\\p{L}", "").replace("\\p{M}", "").replace("\\p{Sc}", "").replace("\\p{Sk}", "").replace("\\p{So}", "").replace("\\p{Nl}", "").replace("\\p{No}", "");
                    if (ChatColor.stripColor(string.replace(" ", "")).startsWith(enchant)) {
                        enchants.add(enchant + " " + level);
                    }
                }
            }
        }
        return enchants;
    }
}
