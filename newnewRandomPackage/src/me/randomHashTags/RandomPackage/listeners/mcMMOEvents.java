package me.randomHashTags.RandomPackage.listeners;

import java.io.File;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.datatypes.skills.XPGainReason;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.api.RandomPackageAPI;
import me.randomHashTags.RandomPackage.api.enums.RPConfigs;
import me.randomHashTags.RandomPackage.api.enums.RPItem;

public class mcMMOEvents {
	
	private ItemStack item = new ItemStack(Material.APPLE);
	private Random random = new Random();
	//
	private static mcMMOEvents instance = null;
	public static mcMMOEvents getInstance() {
		if(instance == null) { instance = new mcMMOEvents(); }
		return instance;
	}
	public SkillType getSkill(String skillname) {
		for(SkillType type : SkillType.values()) {
			if(!(RandomPackage.getConfig(RPConfigs.MASTER).get("givedp-items.mcmmo-vouchers.skill-names." + type.name().toLowerCase().replace("_skills", "")) == null)
					&& skillname.equalsIgnoreCase(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', RandomPackage.getConfig(RPConfigs.MASTER).getString("givedp-items.mcmmo-vouchers.skill-names." + type.name().toLowerCase().replace("_skills", "")))))) {
				return type;
			}
		}
		return null;
	}
	public String getSkillName(SkillType skilltype) {
		if(!(RandomPackage.getConfig(RPConfigs.MASTER).get("givedp-items.mcmmo-vouchers.skill-names." + skilltype.name().toLowerCase().replace("_skills", "")) == null)) {
			return ChatColor.translateAlternateColorCodes('&', RandomPackage.getConfig(RPConfigs.MASTER).getString("givedp-items.mcmmo-vouchers.skill-names." + skilltype.name().toLowerCase().replace("_skills", "")));
		} else { return null; }
	}
	//
	
	@SuppressWarnings("deprecation")
	@EventHandler
	private void McMMOPlayerXpGainEvents(McMMOPlayerXpGainEvent event) {
		if(!(event.getXpGainReason().equals(XPGainReason.UNKNOWN))) {
			item = lapi.getItemInHand(event.getPlayer());
			for(String enchant : api.getEnchantmentsOnItem(item)) {
				String OEN = enchant.split(" ")[0];
				int level = api.getEnchantmentLevel(enchant), chance = lapi.evaluateProckChance(enchant, Integer.parseInt(enchant.split(" ")[1]), event.getPlayer());
				if(random.nextInt(100) <= chance) {
					if(OEN.equals("Nimble") && event.getSkill().equals(SkillType.ACROBATICS)) { ExperienceAPI.addRawXP(event.getPlayer(), SkillType.ACROBATICS.name(), (int) event.getRawXpGained() * level);
					} else if(OEN.equals("Training") && event.getSkill().equals(SkillType.SWORDS) || OEN.equals("Training") && event.getSkill().equals(SkillType.AXES)) {
						if(item.getType().name().endsWith("SWORD")) { ExperienceAPI.addRawXP(event.getPlayer(), SkillType.SWORDS.name(), event.getXpGained() * level);
						} else if(item.getType().name().endsWith("_AXE")) { ExperienceAPI.addRawXP(event.getPlayer(), SkillType.AXES.name(), event.getXpGained() * level); }
					} else if(OEN.equals("Skilling") && event.getSkill().equals(SkillType.GATHERING_SKILLS)) {
						if(item.getType().name().endsWith("SPADE")) { ExperienceAPI.addRawXP(event.getPlayer(), SkillType.EXCAVATION.name(), event.getXpGained() * level);
						} else if(item.getType().name().endsWith("PICKAXE")) { ExperienceAPI.addRawXP(event.getPlayer(), SkillType.MINING.name(), event.getXpGained() * level);
						} else if(item.getType().name().endsWith("_AXE")) { ExperienceAPI.addRawXP(event.getPlayer(), SkillType.WOODCUTTING.name(), event.getXpGained() * level);
						} else if(item.getType().equals(Material.FISHING_ROD)) { ExperienceAPI.addRawXP(event.getPlayer(), SkillType.FISHING.name(), event.getXpGained() * level); }
					}
				}
			}
			return;
		} else { return; }
	}
	/*
	 * 
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	private void playerInteractEvent(PlayerInteractEvent event) {
		if(event.getItem() == null || event.getItem().getType().equals(Material.AIR) || !(event.getItem().hasItemMeta()) || !(event.getItem().getItemMeta().hasDisplayName()) || !(event.getItem().getItemMeta().hasLore())) { return;
		} else if(event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_CREDIT_VOUCHER).getItemMeta().getDisplayName())
				|| event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_LEVEL_VOUCHER).getItemMeta().getDisplayName())
				|| event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_XP_VOUCHER).getItemMeta().getDisplayName())) {
			int numberslot = -1, skillslot = -1; String itemtype = null;
			if(event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_CREDIT_VOUCHER).getItemMeta().getDisplayName())) { itemtype = "credit";
			} else if(event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_LEVEL_VOUCHER).getItemMeta().getDisplayName())) { itemtype = "level";
			} else { itemtype = "xp"; }
			for(int i = 0; i < masterconfig.getStringList("givedp-items.mcmmo-vouchers." + itemtype + ".lore").size(); i++) {
				if(masterconfig.getStringList("givedp-items.mcmmo-vouchers." + itemtype + ".lore").get(i).contains("{XP}")
						|| masterconfig.getStringList("givedp-items.mcmmo-vouchers." + itemtype + ".lore").get(i).contains("{LEVEL}")) {
					numberslot = i;
				}
				if(masterconfig.getStringList("givedp-items.mcmmo-vouchers." + itemtype + ".lore").get(i).contains("{SKILL}")) {
					skillslot = i;
				}
			}
			if(numberslot == -1) { return; }
			SkillType typee = null;
			for(SkillType type : SkillType.values()) {
				if(event.getItem().getItemMeta().getLore().get(skillslot).equals(ChatColor.translateAlternateColorCodes('&', masterconfig.getStringList("givedp-items.mcmmo-vouchers." + itemtype + ".lore").get(skillslot).replace("{SKILL}", "" + getSkillName(type))))) {
					typee = type;
				}
			}
			int xp = getRemainingIntFromString(event.getItem().getItemMeta().getLore().get(numberslot));
			event.setCancelled(true);
			if(itemtype.equals("level")) { itemtype = "levels"; }
			for(String string : messages.getStringList("mcmmo-voucher.redeem-" + itemtype)) {
				if(string.contains("{XP}")) { string = string.replace("{XP}", formatIntUsingCommas(xp)); }
				if(string.contains("{LEVELS}")) { string = string.replace("{LEVELS}", formatIntUsingCommas(xp)); }
				if(string.contains("{SKILL}")) { string = string.replace("{SKILL}", typee.name()); }
				event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
			}
			if(event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_XP_VOUCHER).getItemMeta().getDisplayName())) {
				ExperienceAPI.addRawXP(event.getPlayer(), typee.name(), xp);
			} else if(event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_LEVEL_VOUCHER).getItemMeta().getDisplayName())) {
				ExperienceAPI.addLevel(event.getPlayer(), typee.name(), xp);
			} else if(event.getItem().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.MCMMO_CREDIT_VOUCHER).getItemMeta().getDisplayName())) {
				return;
			}
			lapi.removeItem(event.getPlayer(), event.getItem(), 1);
			lapi.getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "redeem.mcmmo-xp-voucher", false);
			return;
		}
	}
	private RandomPackageAPI api;
	private RPListeners lapi;
	private FileConfiguration masterconfig, messages;
	@EventHandler
	private void pluginEnableEvent(PluginEnableEvent event) {
		api = RandomPackageAPI.getInstance();
		lapi = RPListeners.getInstance();
		masterconfig = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "master-config.yml"));
		messages = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "messages.yml"));
	}
	private String formatIntUsingCommas(int integer) { return String.format("%,d", integer); }
	private int getRemainingIntFromString(String string) {
		string = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)).replaceAll("\\p{L}", "").replaceAll("\\s", "").replaceAll("\\p{P}", "").replaceAll("\\p{S}", "");
		if(string == null || string.equals("")) { return -1; } else { return Integer.parseInt(string); }
	}
}
