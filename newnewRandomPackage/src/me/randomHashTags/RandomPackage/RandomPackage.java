package me.randomHashTags.RandomPackage;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.randomHashTags.RandomPackage.api.RandomPackageAPI;
import me.randomHashTags.RandomPackage.api.enums.RPConfigs;
import me.randomHashTags.RandomPackage.listeners.RPListeners;

public class RandomPackage extends JavaPlugin {
	public static Plugin getPlugin;
	public static String factionPlugin, mcmmo;
	private PluginManager pm = getServer().getPluginManager();
	
	private static FileConfiguration commandsFeatures, masterconfig, messages, enchantments, enchantmentsInfo, otherGuis, shop, gkits, vkits, dropPackages, kitData, sounds;
	public static final String prefix = ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[" + ChatColor.RESET + ChatColor.GOLD + "RandomPackage" + ChatColor.DARK_GRAY + ChatColor.BOLD + "]" + ChatColor.RESET + " ";
	public void onEnable() {
		getPlugin = this;
		//
		File file = null;
		FileConfiguration fileConfig;
		for(int i = 1; i <= 76; i++) {
			if(i == 1) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "commands-features.yml");
			} else if(i == 2) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "master-config.yml");
			} else if(i == 3) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "messages.yml");
			//
			} else if(i == 4) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "enchantments" + File.separator, "enchantments.yml");
			} else if(i == 5) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "enchantments" + File.separator, "info.yml");
			//
			} else if(i == 8) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "other-guis.yml");
			} else if(i == 9) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "shop.yml");
			} else if(i == 10) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "gkits.yml");
			} else if(i == 11) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "vkits.yml");
			//);
			} else if(i == 13) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "data" + File.separator, "redeemed-kits.yml");
			} else if(i == 14) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "data" + File.separator, "sounds.yml");
			// Monthly Crates (54)
			} else if(i == 15) {
			// Drop Packages
			} else if(i == 70) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "_drop-packages.yml");
			} else if(i == 71) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "--god.yml");
			} else if(i == 72) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "--legendary.yml");
			} else if(i == 73) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "--ultimate.yml");
			} else if(i == 74) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "-elite.yml");
			} else if(i == 75) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "-unique.yml");
			} else if(i == 76) { file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "simple.yml");
			//
			} else { file = null; }
			if(!(file == null)) {
				if(!(file.exists())) {
					file.getParentFile().mkdirs();
					if(i >= 4 && i <= 5) {
						RandomPackage.getPlugin.saveResource("enchantments" + File.separator + file.getName(), false);
					} else if(i >= 8 && i <= 11) {
						RandomPackage.getPlugin.saveResource("guis" + File.separator + file.getName(), false);
					} else if(i >= 12 && i <= 14) {
						RandomPackage.getPlugin.saveResource("data" + File.separator + file.getName(), false);
					} else if(i >= 70 && i <= 76) {
						RandomPackage.getPlugin.saveResource("drop packages" + File.separator + file.getName(), false);
					} else {
						RandomPackage.getPlugin.saveResource(file.getName(), false);
					}
				}
				fileConfig = YamlConfiguration.loadConfiguration(file);
				if(!(file == null)) {
					try { fileConfig.load(file); } catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
				}
				//
				if(i == 1) { commandsFeatures = fileConfig;
				} else if(i == 2) { masterconfig = fileConfig;
				} else if(i == 3) { messages = fileConfig;
				} else if(i == 4) { enchantments = fileConfig;
				} else if(i == 5) { enchantmentsInfo = fileConfig;
				} else if(i == 8) { otherGuis = fileConfig;
				} else if(i == 9) { shop = fileConfig;
				} else if(i == 10) { gkits = fileConfig;
				} else if(i == 11) { vkits = fileConfig;
				//
				} else if(i == 13) { kitData = fileConfig;
				} else if(i == 14) { sounds = fileConfig;
				//
				} else if(i == 70) { dropPackages = fileConfig;
				}
			}
		}
		//
		pm.registerEvents(new RPListeners(), this);
		pm.registerEvents(new RandomPackageAPI(), this);
		getCommand("givedp").setExecutor(new RPListeners());
		getCommand("gkit").setExecutor(new RPListeners());
		getCommand("randompackage").setExecutor(new RPListeners());
		getCommand("disabledenchants").setExecutor(new RPListeners());
		getCommand("vkit").setExecutor(new RPListeners());
		//
		if(!(Bukkit.getPluginManager().getPlugin("mcMMO") == null) && Bukkit.getPluginManager().getPlugin("mcMMO").isEnabled()) {
			mcmmo = " &aMCMMO";
		} else { mcmmo = " &cMCMMO"; }
	}
	
	public static FileConfiguration getConfig(RPConfigs rpc) {
		if(rpc.equals(RPConfigs.COMMANDS_FEATURES)) { return commandsFeatures;
		} else if(rpc.equals(RPConfigs.DROP_PACKAGES)) { return dropPackages;
		} else if(rpc.equals(RPConfigs.ENCHANTMENTS)) { return enchantments;
		} else if(rpc.equals(RPConfigs.ENCHANTMENTS_INFO)) { return enchantmentsInfo;
		} else if(rpc.equals(RPConfigs.MASTER)) { return masterconfig;
		} else if(rpc.equals(RPConfigs.MESSAGES)) { return messages;
		} else if(rpc.equals(RPConfigs.SOUNDS)) { return sounds;
		} else if(rpc.equals(RPConfigs.SHOP)) { return shop;
		} else if(rpc.equals(RPConfigs.OTHER_GUIS)) { return otherGuis;
		} else if(rpc.equals(RPConfigs.KIT_DATA)) {
			File file = new File(getPlugin.getDataFolder() + File.separator + "data" + File.separator, "redeemed-kits.yml");
			return YamlConfiguration.loadConfiguration(file);
		} else if(rpc.equals(RPConfigs.GKITS)) { return gkits;
		} else if(rpc.equals(RPConfigs.VKITS)) { return vkits;
		} else { return null; }
	}
	
}
