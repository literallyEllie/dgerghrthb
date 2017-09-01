package me.randomHashTags.RandomPackage.listeners;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.api.RandomPackageAPI;
import me.randomHashTags.RandomPackage.api.enums.RPConfigs;
import me.randomHashTags.RandomPackage.api.enums.RPGui;
import me.randomHashTags.RandomPackage.api.enums.RPItem;
import me.randomHashTags.RandomPackage.api.enums.RandomPackageEnchants;
import me.randomHashTags.RandomPackage.api.factions.FactionsAPI;
import me.randomHashTags.RandomPackage.api.other.RP_Vault;
import me.randomHashTags.RandomPackage.api.versions.v1_8;
import me.randomHashTags.RandomPackage.api.versions.v1_9;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class RPListeners implements Listener, CommandExecutor {

    private static RPListeners rplapi;
    public ArrayList<String> apply_soultracker = new ArrayList<>();
    public ArrayList<String> soulenchantments = new ArrayList<>(), legendaryenchantments = new ArrayList<>(), ultimateenchantments = new ArrayList<>(), eliteenchantments = new ArrayList<String>(), uniqueenchantments = new ArrayList<String>(), simpleenchantments = new ArrayList<String>(), disabledenchantments = new ArrayList<String>();
    public String configPrefix, SHOWCASE_SELF, SHOWCASE_OTHER;
    public ItemStack[] fallenheroes = new ItemStack[54], gkitgems = new ItemStack[54];
    public HashMap<String, String> enchants = new HashMap<>();
    private FileConfiguration sounds, messages, enchantments, enchantmentsInfo, gkits, otherGuis, masterconfig, shop, droppackages, vkits, commandsAndFeatures;
    private Random random = new Random();
    private ItemStack item = new ItemStack(Material.APPLE);
    private ItemMeta itemMeta = item.getItemMeta();
    private ArrayList<String> lore = new ArrayList<>(), detonate = new ArrayList<>();
    private String fallenheroprefix, heroicprefix, guardiansname, spiritsname, undeadrusename,
            WHITE_SCROLL_PROTECTED, TRANSMOG,
            ARMOR, HELMET, CHESTPLATE, LEGGINGS, BOOTS, WEAPON, SWORD, AXE, PICKAXE, BOW, TOOL, APPLY_TO_ITEM,
            SUCCESS, DESTROY, FILTERED_ITEM_FALSE, FILTERED_ITEM_TRUE,
            alchemistCurrency, GKIT_PREVIEW, VKIT_PREVIEW;
    private List<String> gkitgemmessage = null, soulgemlore = null;
    private int gkitgemchance = -1;
    private RandomPackageAPI api;
    private FactionsAPI fapi;
    private Inventory alchemistInv, tinkererInv, enchanterInv, gkitInv, vkitInv, shopInv, showcaseAddInv, showcaseDestroyInv, filterMenuInv,
            givedpInv, givedpRarityBooksInv, givedpSoulTrackerInv, givedpDropPackagesInv, givedpFallenHeroesInv, givedpRandomizationScrollInv, givedpGivedpInv,
            godDPInv, legendaryDPInv, ultimateDPInv, eliteDPInv, uniqueDPInv, simpleDPInv;
    private ItemStack alchemistAccept, alchemistExchange, alchemistPreview, alchemistOther, tinkererDivider, tinkererAccept, tinkererAcceptDupe, shopBackToCategory, GKIT_COOLDOWN, dropPackageSelected,
            showcaseConfirmAdd, showcaseCancelAdd, showcaseConfirmDestroy, showcaseCancelDestroy, fallenhero,
            godDPBackgroundItem, godDPDisplayItem, godDPOpenGuiItem, legendaryDPBackgroundItem, legendaryDPDisplayItem, legendaryDPOpenGuiItem, ultimateDPBackgroundItem, ultimateDPDisplayItem, ultimateDPOpenGuiItem,
            eliteDPBackgroundItem, eliteDPDisplayItem, eliteDPOpenGuiItem, uniqueDPBackgroundItem, uniqueDPDisplayItem, uniqueDPOpenGuiItem, simpleDPBackgroundItem, simpleDPDisplayItem, simpleDPOpenGuiItem;
    private ItemStack
            armorenchantmentorb, blackscroll, christmascandy, christmaseggnog,
            bossBroodMother, bossKingSlime, bossPlagueBloater, bossUndeadAssassin, bossYijki,
            experiencebottle, explosivecake, factioncrystal, factionmcmmobooster, factionxpbooster, halloweencandy, itemnametag, mcmmovoucherCredit, mcmmovoucherLevel, mcmmovoucherXP, mysterydust, mysterymobspawner,
            soulgem, spacedrink, spacefirework, transmogscroll, weaponenchantmentorb, whitescroll, banknote,

    randombook, soulbook, legendarybook, ultimatebook, elitebook, uniquebook, simplebook,
            legendarySoulTracker, ultimateSoulTracker, eliteSoulTracker, uniqueSoulTracker, simpleSoulTracker,
            godRandomizationScroll, legendaryRandomizationScroll, ultimateRandomizationScroll, eliteRandomizationScroll, uniqueRandomizationScroll, simpleRandomizationScroll,
            godDropPackage, legendaryDropPackage, ultimateDropPackage, eliteDropPackage, uniqueDropPackage, simpleDropPackage,
            soulFireball, legendaryFireball, ultimateFireball, eliteFireball, uniqueFireball, simpleFireball,
            soulRegularDust, legendaryRegularDust, ultimateRegularDust, eliteRegularDust, uniqueRegularDust, simpleRegularDust,
            soulPrimalDust, legendaryPrimalDust, ultimatePrimalDust, elitePrimalDust, uniquePrimalDust, simplePrimalDust;
    //
    private boolean tinkerer_accept = false, alchemist_accept = false, shootFireworks = false,
            armor_enchantment_orb = false, bank_note = false, black_scroll = false, christmas_candy = false, christmas_eggnog = false,
    // Bosses

    //
    experience_bottle = false, explosive_cake = false, faction_crystal = false, faction_mcmmo_booster = false, faction_xp_booster = false, halloween_candy = false, item_nametag = false, mcmmo_voucher_credit = false, mcmmo_voucher_level = false,
            mcmmo_voucher_xp = false, mystery_dust = false, mystery_mob_spawner = false, soul_gem = false, space_drink = false, space_firework = false, transmog_scroll = false, weapon_enchantment_orb = false, white_scroll = false;
    private HashMap<Player, ItemStack> commanderprotection = new HashMap<Player, ItemStack>(), implants = new HashMap<Player, ItemStack>();
    private ItemStack[] gkititems0 = new ItemStack[54], gkititems1 = new ItemStack[54], gkititems2 = new ItemStack[54], gkititems3 = new ItemStack[54], gkititems4 = new ItemStack[54], gkititems5 = new ItemStack[54], gkititems6 = new ItemStack[54], gkititems7 = new ItemStack[54], gkititems8 = new ItemStack[54], gkititems9 = new ItemStack[54],
            gkititems10 = new ItemStack[54], gkititems11 = new ItemStack[54], gkititems12 = new ItemStack[54], gkititems13 = new ItemStack[54], gkititems14 = new ItemStack[54], gkititems15 = new ItemStack[54], gkititems16 = new ItemStack[54], gkititems17 = new ItemStack[54], gkititems18 = new ItemStack[54], gkititems19 = new ItemStack[54],
            gkititems20 = new ItemStack[54], gkititems21 = new ItemStack[54], gkititems22 = new ItemStack[54], gkititems23 = new ItemStack[54], gkititems24 = new ItemStack[54], gkititems25 = new ItemStack[54], gkititems26 = new ItemStack[54], gkititems27 = new ItemStack[54], gkititems28 = new ItemStack[54], gkititems29 = new ItemStack[54],
            gkititems30 = new ItemStack[54], gkititems31 = new ItemStack[54], gkititems32 = new ItemStack[54], gkititems33 = new ItemStack[54], gkititems34 = new ItemStack[54], gkititems35 = new ItemStack[54], gkititems36 = new ItemStack[54], gkititems37 = new ItemStack[54], gkititems38 = new ItemStack[54], gkititems39 = new ItemStack[54],
            gkititems40 = new ItemStack[54], gkititems41 = new ItemStack[54], gkititems42 = new ItemStack[54], gkititems43 = new ItemStack[54], gkititems44 = new ItemStack[54], gkititems45 = new ItemStack[54], gkititems46 = new ItemStack[54], gkititems47 = new ItemStack[54], gkititems48 = new ItemStack[54], gkititems49 = new ItemStack[54],
            gkititems50 = new ItemStack[54], gkititems51 = new ItemStack[54], gkititems52 = new ItemStack[54], gkititems53 = new ItemStack[54],
            vkititems0 = new ItemStack[54], vkititems1 = new ItemStack[54], vkititems2 = new ItemStack[54], vkititems3 = new ItemStack[54], vkititems4 = new ItemStack[54], vkititems5 = new ItemStack[54], vkititems6 = new ItemStack[54], vkititems7 = new ItemStack[54], vkititems8 = new ItemStack[54], vkititems9 = new ItemStack[54],
            vkititems10 = new ItemStack[54], vkititems11 = new ItemStack[54], vkititems12 = new ItemStack[54], vkititems13 = new ItemStack[54], vkititems14 = new ItemStack[54], vkititems15 = new ItemStack[54], vkititems16 = new ItemStack[54], vkititems17 = new ItemStack[54], vkititems18 = new ItemStack[54], vkititems19 = new ItemStack[54],
            vkititems20 = new ItemStack[54], vkititems21 = new ItemStack[54], vkititems22 = new ItemStack[54], vkititems23 = new ItemStack[54], vkititems24 = new ItemStack[54], vkititems25 = new ItemStack[54], vkititems26 = new ItemStack[54], vkititems27 = new ItemStack[54], vkititems28 = new ItemStack[54], vkititems29 = new ItemStack[54],
            vkititems30 = new ItemStack[54], vkititems31 = new ItemStack[54], vkititems32 = new ItemStack[54], vkititems33 = new ItemStack[54], vkititems34 = new ItemStack[54], vkititems35 = new ItemStack[54], vkititems36 = new ItemStack[54], vkititems37 = new ItemStack[54], vkititems38 = new ItemStack[54], vkititems39 = new ItemStack[54],
            vkititems40 = new ItemStack[54], vkititems41 = new ItemStack[54], vkititems42 = new ItemStack[54], vkititems43 = new ItemStack[54], vkititems44 = new ItemStack[54], vkititems45 = new ItemStack[54], vkititems46 = new ItemStack[54], vkititems47 = new ItemStack[54], vkititems48 = new ItemStack[54], vkititems49 = new ItemStack[54],
            vkititems50 = new ItemStack[54], vkititems51 = new ItemStack[54], vkititems52 = new ItemStack[54], vkititems53 = new ItemStack[54],
            filteritems0 = new ItemStack[54], filteritems1 = new ItemStack[54], filteritems2 = new ItemStack[54], filteritems3 = new ItemStack[54], filteritems4 = new ItemStack[54], filteritems5 = new ItemStack[54], filteritems6 = new ItemStack[54], filteritems7 = new ItemStack[54], filteritems8 = new ItemStack[54], filteritems9 = new ItemStack[54],
            filteritems10 = new ItemStack[54], filteritems11 = new ItemStack[54], filteritems12 = new ItemStack[54], filteritems13 = new ItemStack[54], filteritems14 = new ItemStack[54], filteritems15 = new ItemStack[54], filteritems16 = new ItemStack[54], filteritems17 = new ItemStack[54], filteritems18 = new ItemStack[54], filteritems19 = new ItemStack[54],
            filteritems20 = new ItemStack[54], filteritems21 = new ItemStack[54], filteritems22 = new ItemStack[54], filteritems23 = new ItemStack[54], filteritems24 = new ItemStack[54], filteritems25 = new ItemStack[54], filteritems26 = new ItemStack[54], filteritems27 = new ItemStack[54], filteritems28 = new ItemStack[54], filteritems29 = new ItemStack[54],
            filteritems30 = new ItemStack[54], filteritems31 = new ItemStack[54], filteritems32 = new ItemStack[54], filteritems33 = new ItemStack[54], filteritems34 = new ItemStack[54], filteritems35 = new ItemStack[54], filteritems36 = new ItemStack[54], filteritems37 = new ItemStack[54], filteritems38 = new ItemStack[54], filteritems39 = new ItemStack[54],
            filteritems40 = new ItemStack[54], filteritems41 = new ItemStack[54], filteritems42 = new ItemStack[54], filteritems43 = new ItemStack[54], filteritems44 = new ItemStack[54], filteritems45 = new ItemStack[54], filteritems46 = new ItemStack[54], filteritems47 = new ItemStack[54], filteritems48 = new ItemStack[54], filteritems49 = new ItemStack[54],
            filteritems50 = new ItemStack[54], filteritems51 = new ItemStack[54], filteritems52 = new ItemStack[54], filteritems53 = new ItemStack[54],
            shopitems0 = new ItemStack[54], shopitems1 = new ItemStack[54], shopitems2 = new ItemStack[54], shopitems3 = new ItemStack[54], shopitems4 = new ItemStack[54], shopitems5 = new ItemStack[54], shopitems6 = new ItemStack[54], shopitems7 = new ItemStack[54], shopitems8 = new ItemStack[54], shopitems9 = new ItemStack[54],
            shopitems10 = new ItemStack[54], shopitems11 = new ItemStack[54], shopitems12 = new ItemStack[54], shopitems13 = new ItemStack[54], shopitems14 = new ItemStack[54], shopitems15 = new ItemStack[54], shopitems16 = new ItemStack[54], shopitems17 = new ItemStack[54], shopitems18 = new ItemStack[54], shopitems19 = new ItemStack[54],
            shopitems20 = new ItemStack[54], shopitems21 = new ItemStack[54], shopitems22 = new ItemStack[54], shopitems23 = new ItemStack[54], shopitems24 = new ItemStack[54], shopitems25 = new ItemStack[54], shopitems26 = new ItemStack[54], shopitems27 = new ItemStack[54], shopitems28 = new ItemStack[54], shopitems29 = new ItemStack[54],
            shopitems30 = new ItemStack[54], shopitems31 = new ItemStack[54], shopitems32 = new ItemStack[54], shopitems33 = new ItemStack[54], shopitems34 = new ItemStack[54], shopitems35 = new ItemStack[54], shopitems36 = new ItemStack[54], shopitems37 = new ItemStack[54], shopitems38 = new ItemStack[54], shopitems39 = new ItemStack[54],
            shopitems40 = new ItemStack[54], shopitems41 = new ItemStack[54], shopitems42 = new ItemStack[54], shopitems43 = new ItemStack[54], shopitems44 = new ItemStack[54], shopitems45 = new ItemStack[54], shopitems46 = new ItemStack[54], shopitems47 = new ItemStack[54], shopitems48 = new ItemStack[54], shopitems49 = new ItemStack[54],
            shopitems50 = new ItemStack[54], shopitems51 = new ItemStack[54], shopitems52 = new ItemStack[54], shopitems53 = new ItemStack[54];
    private int maxblackscroll = -1, minblackscroll = -1;
    //
    private ArrayList<Player> soulmode = new ArrayList<>(), phoenix = new ArrayList<>(), Teleblock = new ArrayList<>();
    private ArrayList<Entity> natureswrath = new ArrayList<>();
    private HashMap<Player, ItemStack> teleblock = new HashMap<>();
    //
    private ArrayList<ItemStack> bows = new ArrayList<>();
    private ArrayList<Player> bowsp = new ArrayList<>();
    private ArrayList<String> arrowdeflect = new ArrayList<>();
    private ArrayList<String> playerRage = new ArrayList<>(), mobRage = new ArrayList<>();
    private ArrayList<Player> claimloot = new ArrayList<>(), revealloot = new ArrayList<>();
    private ArrayList<String> selectloot = new ArrayList<>();
    private int[] god = new int[5], legendary = new int[5], ultimate = new int[5], elite = new int[5], unique = new int[5], simple = new int[5];
    //
    private ArrayList<String> itemNameTag = new ArrayList<>();

    public static RPListeners getInstance() {
        if (rplapi == null) {
            rplapi = new RPListeners();
        }
        return rplapi;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    private void pluginEnableEvent(PluginEnableEvent event) {
        if (event.getPlugin().equals(RandomPackage.getPlugin)) {
            api = RandomPackageAPI.getInstance();
            fapi = FactionsAPI.getInstance();
            sounds = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "data" + File.separator, "sounds.yml"));
            messages = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "messages.yml"));
            masterconfig = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "master-config.yml"));
            commandsAndFeatures = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator, "commands-features.yml"));
            enchantments = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "enchantments" + File.separator, "enchantments.yml"));
            enchantmentsInfo = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "enchantments" + File.separator, "info.yml"));
            gkits = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "gkits.yml"));
            vkits = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "vkits.yml"));
            shop = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "shop.yml"));
            otherGuis = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "guis" + File.separator, "other-guis.yml"));
            droppackages = YamlConfiguration.loadConfiguration(new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, "_drop-packages.yml"));
            //
            shootFireworks = masterconfig.getBoolean("global-settings.books.shoot-fireworks");
            //
            SUCCESS = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.lores.success"));
            DESTROY = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.lores.destroy"));
            WHITE_SCROLL_PROTECTED = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.white-scroll.apply"));
            TRANSMOG = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.transmog-scroll.apply"));
            ARMOR = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.armor"));
            HELMET = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.helmet"));
            CHESTPLATE = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.chestplate"));
            LEGGINGS = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.leggings"));
            BOOTS = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.boots"));
            WEAPON = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.weapon"));
            SWORD = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.sword"));
            AXE = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.axe"));
            BOW = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.bow"));
            PICKAXE = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.pickaxe"));
            TOOL = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.enchant-types.tool"));
            APPLY_TO_ITEM = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.lores.apply"));
            FILTERED_ITEM_FALSE = ChatColor.translateAlternateColorCodes('&', otherGuis.getString("filter.colored-names.not-filtered"));
            FILTERED_ITEM_TRUE = ChatColor.translateAlternateColorCodes('&', otherGuis.getString("filter.colored-names.filtered"));
            alchemistCurrency = masterconfig.getString("global-settings.alchemist-currency").toLowerCase();
            heroicprefix = ChatColor.translateAlternateColorCodes('&', gkits.getString("heroic-settings.prefix"));
            SHOWCASE_SELF = ChatColor.translateAlternateColorCodes('&', otherGuis.getString("showcase.view-self"));
            SHOWCASE_OTHER = ChatColor.translateAlternateColorCodes('&', otherGuis.getString("showcase.view-other"));
            GKIT_PREVIEW = ChatColor.translateAlternateColorCodes('&', gkits.getString("preview-title"));
            VKIT_PREVIEW = ChatColor.translateAlternateColorCodes('&', vkits.getString("preview-title"));
            //
            maxblackscroll = masterconfig.getInt("givedp-items.black-scroll.max-percent") + 1;
            minblackscroll = masterconfig.getInt("givedp-items.black-scroll.min-percent");
            soulgemlore = masterconfig.getStringList("givedp-items.soul-gem.lore");
            configPrefix = ChatColor.translateAlternateColorCodes('&', messages.getString("prefix"));
            gkitgemchance = gkits.getInt("gkit-gem.chance-of-obtaining");
            gkitgemmessage = messages.getStringList("fallen-heroes.receive-kit");
            fallenheroprefix = ChatColor.translateAlternateColorCodes('&', gkits.getString("fallen-heroes.name").replace("{FALLEN_HERO_NAME}", ""));
            guardiansname = ChatColor.translateAlternateColorCodes('&', enchantments.getString("Guardians.name"));
            spiritsname = ChatColor.translateAlternateColorCodes('&', enchantments.getString("Spirits.name"));
            undeadrusename = ChatColor.translateAlternateColorCodes('&', enchantments.getString("UndeadRuse.name"));
            for (RandomPackageEnchants enchant : RandomPackageEnchants.values()) {
                enchants.put(enchantmentsInfo.getString(enchant.name() + ".enchant-name"), enchantments.getString("chances." + enchant.name()).replace(" ", ""));
                String enchantname = enchantmentsInfo.getString(enchant.name() + ".enchant-name"), rarity = enchantmentsInfo.getString(enchant.name() + ".rarity").toLowerCase();
                boolean enabled = enchantmentsInfo.getBoolean(enchant.name() + ".enabled");
                int maxlevel = enchantmentsInfo.getInt(enchant.name() + ".max-level");
                ArrayList<String> raritytype;
                if (!enabled) {
                    raritytype = disabledenchantments;
                } else if (rarity.equalsIgnoreCase("soul")) {
                    raritytype = soulenchantments;
                } else if (rarity.equalsIgnoreCase("legendary")) {
                    raritytype = legendaryenchantments;
                } else if (rarity.equalsIgnoreCase("ultimate")) {
                    raritytype = ultimateenchantments;
                } else if (rarity.equalsIgnoreCase("elite")) {
                    raritytype = eliteenchantments;
                } else if (rarity.equalsIgnoreCase("unique")) {
                    raritytype = uniqueenchantments;
                } else if (rarity.equalsIgnoreCase("simple")) {
                    raritytype = simpleenchantments;
                } else {
                    raritytype = null;
                }
                if (!(raritytype == null)) {
                    if (!enabled) {
                        raritytype.add(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.apply-format").replace("{ENCHANT}", enchantname)));
                    } else {
                        for (int i = 1; i <= maxlevel; i++) {
                            raritytype.add(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.name").replace("{ENCHANT}", enchantname + " " + replaceIntWithRomanNumerals(i))));
                        }
                    }
                }
            }
            for (String string : enchantments.getStringList("Detonate.blacklisted-blocks")) {
                detonate.add(string.toUpperCase());
            }
            String type = null, dp = null;
            int[] dptype = null;
            for (int i = 1; i <= 6; i++) {
                if (i == 1) {
                    type = "legendary";
                    dptype = legendary;
                } else if (i == 2) {
                    type = "ultimate";
                    dptype = ultimate;
                } else if (i == 3) {
                    type = "elite";
                    dptype = elite;
                } else if (i == 4) {
                    type = "unique";
                    dptype = unique;
                } else if (i == 5) {
                    type = "simple";
                    dptype = simple;
                } else {
                    type = null;
                    dptype = god;
                }
                if (!(type == null)) {
                    apply_soultracker.add(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + type + ".soul-tracker.apply")));
                }
                for (int o = 0; o <= 4; o++) {
                    if (o == 0) {
                        dp = "legendary";
                    } else if (o == 1) {
                        dp = "ultimate";
                    } else if (o == 2) {
                        dp = "elite";
                    } else if (o == 3) {
                        dp = "unique";
                    } else {
                        dp = "simple";
                    }
                    dptype[o] = masterconfig.getInt("chances.drop-package." + type + "." + dp);
                }
            }
            item = new ItemStack(Material.APPLE);
            for (int i = 0; i < 54; i++) {
                if (!(gkits.get(i + ".gui.item") == null)) {
                    gkitgems[i] = item;
                }
            }
            //
            alchemistInv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', otherGuis.getString("alchemist.title")));
            alchemistAccept = setupItem(otherGuis, "alchemist.accept");
            alchemistExchange = setupItem(otherGuis, "alchemist.exchange");
            alchemistPreview = setupItem(otherGuis, "alchemist.preview");
            alchemistOther = setupItem(otherGuis, "alchemist.other");
            for (int i = 0; i < alchemistInv.getSize(); i++) {
                if (i == 3 || i == 5) {
                    item = new ItemStack(Material.AIR);
                } else if (i == 13) {
                    item = alchemistPreview;
                } else if (i == 22) {
                    item = alchemistExchange;
                } else {
                    item = alchemistOther;
                }
                alchemistInv.setItem(i, item);
            }
            //
            tinkererInv = Bukkit.createInventory(null, otherGuis.getInt("tinkerer.size"), ChatColor.translateAlternateColorCodes('&', otherGuis.getString("tinkerer.title")));
            tinkererDivider = setupItem(otherGuis, "tinkerer.divider");
            tinkererAccept = setupItem(otherGuis, "tinkerer.accept");
            tinkererAcceptDupe = setupItem(otherGuis, "tinkerer.accept-dupe");
            for (int i = 0; i < tinkererInv.getSize(); i++) {
                if (i == 0) {
                    item = tinkererAccept;
                } else if (i == 8) {
                    item = tinkererAcceptDupe;
                } else if (i == 4 || i == 13 || i == 22 || i == 31 || i == 40 || i == 49) {
                    item = tinkererDivider;
                } else {
                    item = new ItemStack(Material.AIR);
                }
                tinkererInv.setItem(i, item);
            }
            //
            enchanterInv = Bukkit.createInventory(null, otherGuis.getInt("enchanter.size"), ChatColor.translateAlternateColorCodes('&', otherGuis.getString("enchanter.title")));
            for (int i = 0; i < enchanterInv.getSize(); i++) {
                if (!(otherGuis.get("enchanter." + i + ".item") == null)) {
                    if (otherGuis.getString("enchanter." + i + ".item").contains(":")) {
                        item = new ItemStack(Material.getMaterial(otherGuis.getString("enchanter." + i + ".item").split(":")[0].toUpperCase()), 1, (byte) Integer.parseInt(otherGuis.getString("enchanter." + i + ".item").split(":")[1]));
                    } else {
                        item = new ItemStack(Material.getMaterial(otherGuis.getString("enchanter." + i + ".item").toUpperCase()), 1, (byte) 0);
                    }
                    itemMeta = item.getItemMeta();
                    if (!(otherGuis.get("enchanter." + i + ".name") == null)) {
                        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', otherGuis.getString("enchanter." + i + ".name")));
                    }
                    lore.clear();
                    for (String string : otherGuis.getStringList("enchanter." + i + ".lore")) {
                        if (string.contains("{COST}")) {
                            string = string.replace("{COST}", "" + otherGuis.getString("enchanter." + i + ".cost"));
                        }
                        lore.add(ChatColor.translateAlternateColorCodes('&', string));
                    }
                    itemMeta.setLore(lore);
                    lore.clear();
                    item.setItemMeta(itemMeta);
                    enchanterInv.setItem(i, item);
                }
            }
            //
            fallenhero = setupItem(gkits, "fallen-heroes");
            this.gkitInv = Bukkit.createInventory(null, gkits.getInt("size"), ChatColor.translateAlternateColorCodes('&', gkits.getString("title")));

            for (int i = 0; i < gkitInv.getSize(); i++) {
                if (gkits.get(i + ".gui.item") != null) {
                    boolean heroic = false;
                    item = setupItem(gkits, i + ".gui");
                    itemMeta = item.getItemMeta();
                    if (!(gkits.get(i + ".heroic") == null) && gkits.getBoolean(i + ".heroic")) {
                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        itemMeta.setDisplayName(heroicprefix.replace("{NAME}", itemMeta.getDisplayName()));
                        heroic = true;
                    }
                    item.setItemMeta(itemMeta);
                    if (heroic) {
                        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                    }
                    gkitInv.setItem(i, item);
                    //
                    String name = itemMeta.getDisplayName();
                    item = fallenhero.clone();
                    itemMeta = item.getItemMeta();
                    lore.clear();
                    for (String string : fallenhero.getItemMeta().getLore()) {
                        if (string.contains("{FALLEN_HERO_NAME}")) {
                            string = string.replace("{FALLEN_HERO_NAME}", name);
                        }
                        lore.add(string);
                    }
                    itemMeta.setLore(lore);
                    lore.clear();
                    item.setItemMeta(itemMeta);
                    fallenheroes[i] = item;
                }
            }
            //
            this.vkitInv = Bukkit.createInventory(null, vkits.getInt("size"), ChatColor.translateAlternateColorCodes('&', vkits.getString("title")));

            for (int i = 0; i < vkitInv.getSize(); i++) {
                if (!(vkits.get(i + ".gui.item") == null)) {
                    vkitInv.setItem(i, setupItem(vkits, i + ".gui"));
                }
            }
            //
            showcaseConfirmAdd = setupItem(otherGuis, "showcase.add-item.confirm");
            showcaseCancelAdd = setupItem(otherGuis, "showcase.add-item.cancel");
            showcaseConfirmDestroy = setupItem(otherGuis, "showcase.remove-item.confirm");
            showcaseCancelDestroy = setupItem(otherGuis, "showcase.remove-item.cancel");
            showcaseAddInv = Bukkit.createInventory(null, otherGuis.getInt("showcase.add-item.size"), ChatColor.translateAlternateColorCodes('&', otherGuis.getString("showcase.add-item.title")));
            showcaseDestroyInv = Bukkit.createInventory(null, otherGuis.getInt("showcase.remove-item.size"), ChatColor.translateAlternateColorCodes('&', otherGuis.getString("showcase.remove-item.title")));
            for (int i = 0; i < showcaseAddInv.getSize(); i++) {
                if (!(otherGuis.get("showcase.add-item." + i + ".item") == null)) {
                    if (otherGuis.getString("showcase.add-item." + i + ".item").equals("{CONFIRM}")) {
                        item = showcaseConfirmAdd;
                    } else if (otherGuis.getString("showcase.add-item." + i + ".item").equals("{CANCEL}")) {
                        item = showcaseCancelAdd;
                    } else if (otherGuis.getString("showcase.add-item." + i + ".item").equals("{ITEM}")) {
                        item = new ItemStack(Material.AIR);
                    } else {
                        item = setupItem(otherGuis, "showcase.add-item." + i);
                    }
                    showcaseAddInv.setItem(i, item);
                }
            }
            for (int i = 0; i < showcaseDestroyInv.getSize(); i++) {
                if (!(otherGuis.get("showcase.remove-item." + i + ".item") == null)) {
                    if (otherGuis.getString("showcase.remove-item." + i + ".item").equals("{CONFIRM}")) {
                        item = showcaseConfirmDestroy;
                    } else if (otherGuis.getString("showcase.remove-item." + i + ".item").equals("{CANCEL}")) {
                        item = showcaseCancelDestroy;
                    } else if (otherGuis.getString("showcase.remove-item." + i + ".item").equals("{ITEM}")) {
                        item = new ItemStack(Material.AIR);
                    } else {
                        item = setupItem(otherGuis, "showcase.remove-item." + i);
                    }
                    showcaseDestroyInv.setItem(i, item);
                }
            }
            //
            Inventory inv = null;
            ItemStack display = null, opengui = null;
            for (int i = 1; i <= 6; i++) {
                if (i == 1) {
                    inv = godDPInv;
                    display = godDPDisplayItem;
                    opengui = godDPOpenGuiItem;
                    type = "god";
                } else if (i == 2) {
                    inv = legendaryDPInv;
                    display = legendaryDPDisplayItem;
                    opengui = legendaryDPOpenGuiItem;
                    type = "legendary";
                } else if (i == 3) {
                    inv = ultimateDPInv;
                    display = ultimateDPDisplayItem;
                    opengui = ultimateDPOpenGuiItem;
                    type = "ultimate";
                } else if (i == 4) {
                    inv = eliteDPInv;
                    display = eliteDPDisplayItem;
                    opengui = eliteDPOpenGuiItem;
                    type = "elite";
                } else if (i == 5) {
                    inv = uniqueDPInv;
                    display = uniqueDPDisplayItem;
                    opengui = uniqueDPOpenGuiItem;
                    type = "unique";
                } else {
                    inv = simpleDPInv;
                    display = simpleDPDisplayItem;
                    opengui = simpleDPOpenGuiItem;
                    type = "simple";
                }
                display = setupItem(droppackages, type + ".display-item");
                opengui = setupItem(droppackages, type + ".open-gui");
                for (int o = 0; o < droppackages.getStringList(type + ".open-gui-format").size(); o++) {
                    for (int z = 0; z + 1 < droppackages.getStringList(type + ".open-gui-format").get(o).length(); z++) {
                        if (droppackages.getStringList(type + ".open-gui-format").get(o).substring(z, z + 1).equals("-")) {

                        }
                    }
                }
            }
            godDPInv = Bukkit.createInventory(null, droppackages.getInt("god.settings.size"), ChatColor.translateAlternateColorCodes('&', droppackages.getString("god.settings.title")));
            for (int i = 0; i < droppackages.getStringList("god.open-gui-format").size(); i++) {
                for (int o = 0; o < droppackages.getStringList("god.open-gui-format").get(i).length(); o++) {

                }
            }
            //
            legendaryDPInv = Bukkit.createInventory(null, droppackages.getInt("legendary.settings.size"), ChatColor.translateAlternateColorCodes('&', droppackages.getString("legendary.settings.title")));
            //
            ultimateDPInv = Bukkit.createInventory(null, droppackages.getInt("ultimate.settings.size"), ChatColor.translateAlternateColorCodes('&', droppackages.getString("ultimate.settings.title")));
            //
            eliteDPInv = Bukkit.createInventory(null, droppackages.getInt("elite.settings.size"), ChatColor.translateAlternateColorCodes('&', droppackages.getString("elite.settings.title")));
            //
            uniqueDPInv = Bukkit.createInventory(null, droppackages.getInt("unique.settings.size"), ChatColor.translateAlternateColorCodes('&', droppackages.getString("unique.settings.title")));
            //
            simpleDPInv = Bukkit.createInventory(null, droppackages.getInt("simple.settings.size"), ChatColor.translateAlternateColorCodes('&', droppackages.getString("simple.settings.title")));
            //
            filterMenuInv = Bukkit.createInventory(null, otherGuis.getInt("filter.menu.size"), ChatColor.translateAlternateColorCodes('&', otherGuis.getString("filter.menu.title")));
            for (int i = 0; i < filterMenuInv.getSize(); i++) {
                if (!(otherGuis.get("filter.menu." + i + ".item") == null)) {
                    filterMenuInv.setItem(i, setupItem(otherGuis, "filter.menu." + i));
                }
                if (!(otherGuis.get("filter." + i + ".title") == null)) {
                    for (int o = 0; o < otherGuis.getInt("filter." + i + ".size"); o++) {
                        if (!(otherGuis.get("filter." + i + "." + o) == null)) {
                            getFilterCategory(i)[o] = setupItem(otherGuis, "filter." + i + "." + o);
                        }
                    }
                }
            }
            //
            shopBackToCategory = setupItem(shop, "items.back-to-categories");
            GKIT_COOLDOWN = setupItem(gkits, "cooldown");
            dropPackageSelected = setupItem(droppackages, "drop-package.selected");
            //
            randombook = setupItem(masterconfig, "givedp-items.random.reveal-item");
            soulbook = setupItem(masterconfig, "givedp-items.soul.reveal-item");
            legendarybook = setupItem(masterconfig, "givedp-items.legendary.reveal-item");
            ultimatebook = setupItem(masterconfig, "givedp-items.ultimate.reveal-item");
            elitebook = setupItem(masterconfig, "givedp-items.elite.reveal-item");
            uniquebook = setupItem(masterconfig, "givedp-items.unique.reveal-item");
            simplebook = setupItem(masterconfig, "givedp-items.simple.reveal-item");
            //
            godDropPackage = setupItem(droppackages, "god.interactable-item");
            legendaryDropPackage = setupItem(droppackages, "legendary.interactable-item");
            ultimateDropPackage = setupItem(droppackages, "ultimate.interactable-item");
            eliteDropPackage = setupItem(droppackages, "elite.interactable-item");
            uniqueDropPackage = setupItem(droppackages, "unique.interactable-item");
            simpleDropPackage = setupItem(droppackages, "simple.interactable-item");
            //
            godRandomizationScroll = setupItem(masterconfig, "givedp-items.god.randomization-scroll");
            legendaryRandomizationScroll = setupItem(masterconfig, "givedp-items.legendary.randomization-scroll");
            ultimateRandomizationScroll = setupItem(masterconfig, "givedp-items.ultimate.randomization-scroll");
            eliteRandomizationScroll = setupItem(masterconfig, "givedp-items.elite.randomization-scroll");
            uniqueRandomizationScroll = setupItem(masterconfig, "givedp-items.unique.randomization-scroll");
            simpleRandomizationScroll = setupItem(masterconfig, "givedp-items.simple.randomization-scroll");
            //
            legendarySoulTracker = setupItem(masterconfig, "givedp-items.legendary.soul-tracker");
            ultimateSoulTracker = setupItem(masterconfig, "givedp-items.ultimate.soul-tracker");
            eliteSoulTracker = setupItem(masterconfig, "givedp-items.elite.soul-tracker");
            uniqueSoulTracker = setupItem(masterconfig, "givedp-items.unique.soul-tracker");
            simpleSoulTracker = setupItem(masterconfig, "givedp-items.simple.soul-tracker");
            //
            soulFireball = setupItem(masterconfig, "givedp-items.soul.fireball");
            legendaryFireball = setupItem(masterconfig, "givedp-items.legendary.fireball");
            ultimateFireball = setupItem(masterconfig, "givedp-items.ultimate.fireball");
            eliteFireball = setupItem(masterconfig, "givedp-items.elite.fireball");
            uniqueFireball = setupItem(masterconfig, "givedp-items.unique.fireball");
            simpleFireball = setupItem(masterconfig, "givedp-items.simple.fireball");
            //
            armorenchantmentorb = setupItem(masterconfig, "givedp-items.armor-enchantment-orb");
            blackscroll = setupItem(masterconfig, "givedp-items.black-scroll");
            christmascandy = setupItem(masterconfig, "givedp-items.christmas-candy");
            christmaseggnog = setupItem(masterconfig, "givedp-items.christmas-eggnog");
            bossBroodMother = setupItem(masterconfig, "givedp-items.bosses.brood-mother");
            bossKingSlime = setupItem(masterconfig, "givedp-items.bosses.king-slime");
            bossPlagueBloater = setupItem(masterconfig, "givedp-items.bosses.plague-bloater");
            bossUndeadAssassin = setupItem(masterconfig, "givedp-items.bosses.undead-assassin");
            bossYijki = setupItem(masterconfig, "givedp-items.bosses.yijki");
            experiencebottle = setupItem(masterconfig, "givedp-items.experience-bottle");
            explosivecake = setupItem(masterconfig, "givedp-items.explosive-cake");
            factioncrystal = setupItem(masterconfig, "givedp-items.faction-crystal");
            factionmcmmobooster = setupItem(masterconfig, "givedp-items.faction-mcmmo-booster");
            factionxpbooster = setupItem(masterconfig, "givedp-items.faction-xp-booster");
            halloweencandy = setupItem(masterconfig, "givedp-items.halloween-candy");
            itemnametag = setupItem(masterconfig, "givedp-items.item-name-tag");
            mcmmovoucherCredit = setupItem(masterconfig, "givedp-items.mcmmo-vouchers.credit");
            mcmmovoucherLevel = setupItem(masterconfig, "givedp-items.mcmmo-vouchers.level");
            mcmmovoucherXP = setupItem(masterconfig, "givedp-items.mcmmo-vouchers.xp");
            mysterydust = setupItem(masterconfig, "givedp-items.mystery-dust");
            mysterymobspawner = setupItem(masterconfig, "givedp-items.mystery-mob-spawner");
            soulgem = setupItem(masterconfig, "givedp-items.soul-gem");
            spacedrink = setupItem(masterconfig, "givedp-items.space-drink");
            spacefirework = setupItem(masterconfig, "givedp-items.space-firework");
            transmogscroll = setupItem(masterconfig, "givedp-items.transmog-scroll");
            weaponenchantmentorb = setupItem(masterconfig, "givedp-items.weapon-enchantment-orb");
            whitescroll = setupItem(masterconfig, "givedp-items.white-scroll");
            banknote = setupItem(masterconfig, "givedp-items.withdraw");
            //
            givedpInv = Bukkit.createInventory(null, 9, "Givedp Categories");
            for (int i = 0; i < givedpInv.getSize(); i++) {
                if (i == 1) {
                    item = new ItemStack(randombook.getType(), 1, randombook.getData().getData());
                    itemMeta.setDisplayName("Rarity Books");
                } else if (i == 2) {
                    item = new ItemStack(legendarySoulTracker.getType(), 1, legendarySoulTracker.getData().getData());
                    itemMeta.setDisplayName("Soul Trackers");
                } else if (i == 3) {
                    item = new ItemStack(godRandomizationScroll.getType(), 1, godRandomizationScroll.getData().getData());
                    itemMeta.setDisplayName("Randomization Scrolls");
                } else if (i == 4) {
                    item = new ItemStack(godDropPackage.getType(), 1, godDropPackage.getData().getData());
                    itemMeta.setDisplayName("Drop Packages");
                } else if (i == 5) {
                    item = new ItemStack(whitescroll.getType(), 1, whitescroll.getData().getData());
                } else if (i == 8) {
                    item = new ItemStack(fallenhero.getType(), 1, fallenhero.getData().getData());
                    itemMeta.setDisplayName("Fallen Heroes");
                } else {
                    item = new ItemStack(Material.AIR);
                }
                if (!(item.getType().equals(Material.AIR))) {
                    itemMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + itemMeta.getDisplayName());
                    item.setItemMeta(itemMeta);
                    givedpInv.setItem(i, item);
                }
            }
            //
            givedpDropPackagesInv = Bukkit.createInventory(null, 9, "Givedp (Drop Packages)");
            givedpDropPackagesInv.addItem(godDropPackage);
            givedpDropPackagesInv.addItem(legendaryDropPackage);
            givedpDropPackagesInv.addItem(ultimateDropPackage);
            givedpDropPackagesInv.addItem(eliteDropPackage);
            givedpDropPackagesInv.addItem(uniqueDropPackage);
            givedpDropPackagesInv.addItem(simpleDropPackage);
            //
            givedpFallenHeroesInv = Bukkit.createInventory(null, 54, "Givedp (Fallen Heroes)");
            //
            givedpGivedpInv = Bukkit.createInventory(null, 27, "Givedp (Givedp Items)");
            //
            givedpRandomizationScrollInv = Bukkit.createInventory(null, 9, "Givedp (Randomization Scrolls)");
            givedpRandomizationScrollInv.addItem(godRandomizationScroll);
            givedpRandomizationScrollInv.addItem(legendaryRandomizationScroll);
            givedpRandomizationScrollInv.addItem(ultimateRandomizationScroll);
            givedpRandomizationScrollInv.addItem(eliteRandomizationScroll);
            givedpRandomizationScrollInv.addItem(uniqueRandomizationScroll);
            givedpRandomizationScrollInv.addItem(simpleRandomizationScroll);
            //
            givedpRarityBooksInv = Bukkit.createInventory(null, 9, "Givedp (Rarity Books)");
            givedpRarityBooksInv.addItem(randombook);
            givedpRarityBooksInv.addItem(soulbook);
            givedpRarityBooksInv.addItem(legendarybook);
            givedpRarityBooksInv.addItem(ultimatebook);
            givedpRarityBooksInv.addItem(elitebook);
            givedpRarityBooksInv.addItem(uniquebook);
            givedpRarityBooksInv.addItem(simplebook);
            //
            givedpSoulTrackerInv = Bukkit.createInventory(null, 9, "Givedp (Soul Trackers)");
            givedpSoulTrackerInv.addItem(legendarySoulTracker);
            givedpSoulTrackerInv.addItem(ultimateSoulTracker);
            givedpSoulTrackerInv.addItem(eliteSoulTracker);
            givedpSoulTrackerInv.addItem(uniqueSoulTracker);
            givedpSoulTrackerInv.addItem(simpleSoulTracker);
            //
            shopInv = Bukkit.createInventory(null, shop.getInt("shop.size"), ChatColor.translateAlternateColorCodes('&', shop.getString("shop.title")));
            for (int i = 0; i < shopInv.getSize(); i++) {
                if (!(shop.get("shop." + i + ".item") == null)) {
                    shopInv.setItem(i, setupItem(shop, "shop." + i));
                }
                if (!(shop.get(i + ".title") == null)) {
                    for (int o = 0; o < 54; o++) {
                        if (!(shop.get(i + "." + o + ".item") == null)) {
                            item = setupItem(shop, i + "." + o);
                            itemMeta = item.getItemMeta();
                            getShopCategory(i)[o] = item;
                        }
                    }
                }
            }
            //
            for (int i = 0; i < gkitInv.getSize(); i++) {
                if (!(gkits.get(i + ".items") == null)) {
                    boolean did = false;
                    for (int o = 0; o <= 53; o++) {
                        if (!(gkits.get(i + ".items." + o + ".item") == null)) {
                            did = true;
                            if (Material.getMaterial(gkits.getString(i + ".items." + o + ".item").split(":")[0].toUpperCase()) == null) {
                                if (!(checkStringForRPItem(gkits.getString(i + ".items." + o + ".item")) == null)) {
                                    item = checkStringForRPItem(gkits.getString(i + ".items." + o + ".item")).clone();
                                } else {
                                    item = new ItemStack(Material.BARRIER);
                                    itemMeta = item.getItemMeta();
                                    itemMeta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Invalid Material");
                                    lore.clear();
                                    lore.add(ChatColor.translateAlternateColorCodes('&', "&6path=&f" + i + "items." + o + ".item"));
                                    lore.add(ChatColor.translateAlternateColorCodes('&', "&6material=&f" + gkits.getString(i + ".items." + o + ".item")));
                                    itemMeta.setLore(lore);
                                    lore.clear();
                                    item.setItemMeta(itemMeta);
                                }
                            } else {
                                item = setupItem(gkits, i + ".items." + o);
                            }
                        } else if (!(gkits.get(i + ".items." + o + ".items") == null)) {

                        }
                        if (did) {
                            getGkitItems(i)[o] = item;
                            did = false;
                        }
                    }
                }
            }
            //
            Bukkit.getScheduler().scheduleSyncRepeatingTask(RandomPackage.getPlugin, () -> {
                for (Player player : commanderprotection.keySet()) {
                    for (String enchant : api.getEnchantmentsOnItem(commanderprotection.get(player))) {
                        String OEN = enchant.split(" ")[0];
                        if (OEN.equals("Commander") || OEN.equals("Protection")) {
                            int level = api.getEnchantmentLevel(enchant), chance = evaluateProckChance(OEN, Integer.parseInt(enchant.split(" ")[1]), player);
                            if (random.nextInt(100) <= chance) {
                                if (OEN.equals("Commander")) {
                                    Commander(player, level);
                                } else if (OEN.equals("Protection")) {
                                    Protection(player, level);
                                }
                            }
                        }
                    }
                }
            }, 0, 20 * 10);
            //
            if (enchantmentsInfo.getBoolean("Implants.enabled")) {
                Bukkit.getScheduler().scheduleSyncRepeatingTask(RandomPackage.getPlugin, new Runnable() {
                    public void run() {
                        for (Player player : implants.keySet()) {
                            for (String enchant : api.getEnchantmentsOnItem(implants.get(player))) {
                                String OEN = enchant.split(" ")[0];
                                if (OEN.equals("Implants")) {
                                    if (random.nextInt(100) <= evaluateProckChance(enchants.get("Implants"), Integer.parseInt(enchant.split(" ")[1]), player)) {
                                        Implants(player);
                                    }
                                }
                            }
                        }
                    }
                }, 0, 20 * 6);
            }
            //
        }
    }

    public ItemStack[] getGkitItems(int rawSlot) {
        if (rawSlot == 0) {
            return gkititems0;
        } else if (rawSlot == 1) {
            return gkititems1;
        } else if (rawSlot == 2) {
            return gkititems2;
        } else if (rawSlot == 3) {
            return gkititems3;
        } else if (rawSlot == 4) {
            return gkititems4;
        } else if (rawSlot == 5) {
            return gkititems5;
        } else if (rawSlot == 6) {
            return gkititems6;
        } else if (rawSlot == 7) {
            return gkititems7;
        } else if (rawSlot == 8) {
            return gkititems8;
        } else if (rawSlot == 9) {
            return gkititems9;
        } else if (rawSlot == 10) {
            return gkititems10;
        } else if (rawSlot == 11) {
            return gkititems11;
        } else if (rawSlot == 12) {
            return gkititems12;
        } else if (rawSlot == 13) {
            return gkititems13;
        } else if (rawSlot == 14) {
            return gkititems14;
        } else if (rawSlot == 15) {
            return gkititems15;
        } else if (rawSlot == 16) {
            return gkititems16;
        } else if (rawSlot == 17) {
            return gkititems17;
        } else if (rawSlot == 18) {
            return gkititems18;
        } else if (rawSlot == 19) {
            return gkititems19;
        } else if (rawSlot == 20) {
            return gkititems20;
        } else if (rawSlot == 21) {
            return gkititems21;
        } else if (rawSlot == 22) {
            return gkititems22;
        } else if (rawSlot == 23) {
            return gkititems23;
        } else if (rawSlot == 24) {
            return gkititems24;
        } else if (rawSlot == 25) {
            return gkititems25;
        } else if (rawSlot == 26) {
            return gkititems26;
        } else if (rawSlot == 27) {
            return gkititems27;
        } else if (rawSlot == 28) {
            return gkititems28;
        } else if (rawSlot == 29) {
            return gkititems29;
        } else if (rawSlot == 30) {
            return gkititems30;
        } else if (rawSlot == 31) {
            return gkititems31;
        } else if (rawSlot == 32) {
            return gkititems32;
        } else if (rawSlot == 33) {
            return gkititems33;
        } else if (rawSlot == 34) {
            return gkititems34;
        } else if (rawSlot == 35) {
            return gkititems35;
        } else if (rawSlot == 36) {
            return gkititems36;
        } else if (rawSlot == 37) {
            return gkititems37;
        } else if (rawSlot == 38) {
            return gkititems38;
        } else if (rawSlot == 39) {
            return gkititems39;
        } else if (rawSlot == 40) {
            return gkititems40;
        } else if (rawSlot == 41) {
            return gkititems41;
        } else if (rawSlot == 42) {
            return gkititems42;
        } else if (rawSlot == 43) {
            return gkititems43;
        } else if (rawSlot == 44) {
            return gkititems44;
        } else if (rawSlot == 45) {
            return gkititems45;
        } else if (rawSlot == 46) {
            return gkititems46;
        } else if (rawSlot == 47) {
            return gkititems47;
        } else if (rawSlot == 48) {
            return gkititems48;
        } else if (rawSlot == 49) {
            return gkititems49;
        } else if (rawSlot == 50) {
            return gkititems50;
        } else if (rawSlot == 51) {
            return gkititems51;
        } else if (rawSlot == 52) {
            return gkititems52;
        } else if (rawSlot == 53) {
            return gkititems53;
        } else {
            return null;
        }
    }

    public ItemStack[] getVkitItems(int rawSlot) {
        if (rawSlot == 0) {
            return vkititems0;
        } else if (rawSlot == 1) {
            return vkititems1;
        } else if (rawSlot == 2) {
            return vkititems2;
        } else if (rawSlot == 3) {
            return vkititems3;
        } else if (rawSlot == 4) {
            return vkititems4;
        } else if (rawSlot == 5) {
            return vkititems5;
        } else if (rawSlot == 6) {
            return vkititems6;
        } else if (rawSlot == 7) {
            return vkititems7;
        } else if (rawSlot == 8) {
            return vkititems8;
        } else if (rawSlot == 9) {
            return vkititems9;
        } else if (rawSlot == 10) {
            return vkititems10;
        } else if (rawSlot == 11) {
            return vkititems11;
        } else if (rawSlot == 12) {
            return vkititems12;
        } else if (rawSlot == 13) {
            return vkititems13;
        } else if (rawSlot == 14) {
            return vkititems14;
        } else if (rawSlot == 15) {
            return vkititems15;
        } else if (rawSlot == 16) {
            return vkititems16;
        } else if (rawSlot == 17) {
            return vkititems17;
        } else if (rawSlot == 18) {
            return vkititems18;
        } else if (rawSlot == 19) {
            return vkititems19;
        } else if (rawSlot == 20) {
            return vkititems20;
        } else if (rawSlot == 21) {
            return vkititems21;
        } else if (rawSlot == 22) {
            return vkititems22;
        } else if (rawSlot == 23) {
            return vkititems23;
        } else if (rawSlot == 24) {
            return vkititems24;
        } else if (rawSlot == 25) {
            return vkititems25;
        } else if (rawSlot == 26) {
            return vkititems26;
        } else if (rawSlot == 27) {
            return vkititems27;
        } else if (rawSlot == 28) {
            return vkititems28;
        } else if (rawSlot == 29) {
            return vkititems29;
        } else if (rawSlot == 30) {
            return vkititems30;
        } else if (rawSlot == 31) {
            return vkititems31;
        } else if (rawSlot == 32) {
            return vkititems32;
        } else if (rawSlot == 33) {
            return vkititems33;
        } else if (rawSlot == 34) {
            return vkititems34;
        } else if (rawSlot == 35) {
            return vkititems35;
        } else if (rawSlot == 36) {
            return vkititems36;
        } else if (rawSlot == 37) {
            return vkititems37;
        } else if (rawSlot == 38) {
            return vkititems38;
        } else if (rawSlot == 39) {
            return vkititems39;
        } else if (rawSlot == 40) {
            return vkititems40;
        } else if (rawSlot == 41) {
            return vkititems41;
        } else if (rawSlot == 42) {
            return vkititems42;
        } else if (rawSlot == 43) {
            return vkititems43;
        } else if (rawSlot == 44) {
            return vkititems44;
        } else if (rawSlot == 45) {
            return vkititems45;
        } else if (rawSlot == 46) {
            return vkititems46;
        } else if (rawSlot == 47) {
            return vkititems47;
        } else if (rawSlot == 48) {
            return vkititems48;
        } else if (rawSlot == 49) {
            return vkititems49;
        } else if (rawSlot == 50) {
            return vkititems50;
        } else if (rawSlot == 51) {
            return vkititems51;
        } else if (rawSlot == 52) {
            return vkititems52;
        } else if (rawSlot == 53) {
            return vkititems53;
        } else {
            return null;
        }
    }

    public ItemStack[] getFilterCategory(int rawSlot) {
        if (rawSlot == 0) {
            return filteritems0;
        } else if (rawSlot == 1) {
            return filteritems1;
        } else if (rawSlot == 2) {
            return filteritems2;
        } else if (rawSlot == 3) {
            return filteritems3;
        } else if (rawSlot == 4) {
            return filteritems4;
        } else if (rawSlot == 5) {
            return filteritems5;
        } else if (rawSlot == 6) {
            return filteritems6;
        } else if (rawSlot == 7) {
            return filteritems7;
        } else if (rawSlot == 8) {
            return filteritems8;
        } else if (rawSlot == 9) {
            return filteritems9;
        } else if (rawSlot == 10) {
            return filteritems10;
        } else if (rawSlot == 11) {
            return filteritems11;
        } else if (rawSlot == 12) {
            return filteritems12;
        } else if (rawSlot == 13) {
            return filteritems13;
        } else if (rawSlot == 14) {
            return filteritems14;
        } else if (rawSlot == 15) {
            return filteritems15;
        } else if (rawSlot == 16) {
            return filteritems16;
        } else if (rawSlot == 17) {
            return filteritems17;
        } else if (rawSlot == 18) {
            return filteritems18;
        } else if (rawSlot == 19) {
            return filteritems19;
        } else if (rawSlot == 20) {
            return filteritems20;
        } else if (rawSlot == 21) {
            return filteritems21;
        } else if (rawSlot == 22) {
            return filteritems22;
        } else if (rawSlot == 23) {
            return filteritems23;
        } else if (rawSlot == 24) {
            return filteritems24;
        } else if (rawSlot == 25) {
            return filteritems25;
        } else if (rawSlot == 26) {
            return filteritems26;
        } else if (rawSlot == 27) {
            return filteritems27;
        } else if (rawSlot == 28) {
            return filteritems28;
        } else if (rawSlot == 29) {
            return filteritems29;
        } else if (rawSlot == 30) {
            return filteritems30;
        } else if (rawSlot == 31) {
            return filteritems31;
        } else if (rawSlot == 32) {
            return filteritems32;
        } else if (rawSlot == 33) {
            return filteritems33;
        } else if (rawSlot == 34) {
            return filteritems34;
        } else if (rawSlot == 35) {
            return filteritems35;
        } else if (rawSlot == 36) {
            return filteritems36;
        } else if (rawSlot == 37) {
            return filteritems37;
        } else if (rawSlot == 38) {
            return filteritems38;
        } else if (rawSlot == 39) {
            return filteritems39;
        } else if (rawSlot == 40) {
            return filteritems40;
        } else if (rawSlot == 41) {
            return filteritems41;
        } else if (rawSlot == 42) {
            return filteritems42;
        } else if (rawSlot == 43) {
            return filteritems43;
        } else if (rawSlot == 44) {
            return filteritems44;
        } else if (rawSlot == 45) {
            return filteritems45;
        } else if (rawSlot == 46) {
            return filteritems46;
        } else if (rawSlot == 47) {
            return filteritems47;
        } else if (rawSlot == 48) {
            return filteritems48;
        } else if (rawSlot == 49) {
            return filteritems49;
        } else if (rawSlot == 50) {
            return filteritems50;
        } else if (rawSlot == 51) {
            return filteritems51;
        } else if (rawSlot == 52) {
            return filteritems52;
        } else if (rawSlot == 53) {
            return filteritems53;
        } else {
            return null;
        }
    }

    public ItemStack[] getShopCategory(int rawSlot) {
        if (rawSlot == 0) {
            return shopitems0;
        } else if (rawSlot == 1) {
            return shopitems1;
        } else if (rawSlot == 2) {
            return shopitems2;
        } else if (rawSlot == 3) {
            return shopitems3;
        } else if (rawSlot == 4) {
            return shopitems4;
        } else if (rawSlot == 5) {
            return shopitems5;
        } else if (rawSlot == 6) {
            return shopitems6;
        } else if (rawSlot == 7) {
            return shopitems7;
        } else if (rawSlot == 8) {
            return shopitems8;
        } else if (rawSlot == 9) {
            return shopitems9;
        } else if (rawSlot == 10) {
            return shopitems10;
        } else if (rawSlot == 11) {
            return shopitems11;
        } else if (rawSlot == 12) {
            return shopitems12;
        } else if (rawSlot == 13) {
            return shopitems13;
        } else if (rawSlot == 14) {
            return shopitems14;
        } else if (rawSlot == 15) {
            return shopitems15;
        } else if (rawSlot == 16) {
            return shopitems16;
        } else if (rawSlot == 17) {
            return shopitems17;
        } else if (rawSlot == 18) {
            return shopitems18;
        } else if (rawSlot == 19) {
            return shopitems19;
        } else if (rawSlot == 20) {
            return shopitems20;
        } else if (rawSlot == 21) {
            return shopitems21;
        } else if (rawSlot == 22) {
            return shopitems22;
        } else if (rawSlot == 23) {
            return shopitems23;
        } else if (rawSlot == 24) {
            return shopitems24;
        } else if (rawSlot == 25) {
            return shopitems25;
        } else if (rawSlot == 26) {
            return shopitems26;
        } else if (rawSlot == 27) {
            return shopitems27;
        } else if (rawSlot == 28) {
            return shopitems28;
        } else if (rawSlot == 29) {
            return shopitems29;
        } else if (rawSlot == 30) {
            return shopitems30;
        } else if (rawSlot == 31) {
            return shopitems31;
        } else if (rawSlot == 32) {
            return shopitems32;
        } else if (rawSlot == 33) {
            return shopitems33;
        } else if (rawSlot == 34) {
            return shopitems34;
        } else if (rawSlot == 35) {
            return shopitems35;
        } else if (rawSlot == 36) {
            return shopitems36;
        } else if (rawSlot == 37) {
            return shopitems37;
        } else if (rawSlot == 38) {
            return shopitems38;
        } else if (rawSlot == 39) {
            return shopitems39;
        } else if (rawSlot == 40) {
            return shopitems40;
        } else if (rawSlot == 41) {
            return shopitems41;
        } else if (rawSlot == 42) {
            return shopitems42;
        } else if (rawSlot == 43) {
            return shopitems43;
        } else if (rawSlot == 44) {
            return shopitems44;
        } else if (rawSlot == 45) {
            return shopitems45;
        } else if (rawSlot == 46) {
            return shopitems46;
        } else if (rawSlot == 47) {
            return shopitems47;
        } else if (rawSlot == 48) {
            return shopitems48;
        } else if (rawSlot == 49) {
            return shopitems49;
        } else if (rawSlot == 50) {
            return shopitems50;
        } else if (rawSlot == 51) {
            return shopitems51;
        } else if (rawSlot == 52) {
            return shopitems52;
        } else if (rawSlot == 53) {
            return shopitems53;
        } else {
            return null;
        }
    }

    private ItemStack checkStringForRPItem(String string) {
        string = string.toLowerCase();
        if (string.equals("armorenchantmentorb")) {
            return armorenchantmentorb.clone();
        } else if (string.equals("banknote")) {
            return banknote.clone();
        } else if (string.equals("blackscroll") && string.contains(":")) {
            return blackscroll.clone();
        } else if (string.equals("christmasc")) {
            return christmascandy.clone();
        } else if (string.equals("christmase")) {
            return christmaseggnog.clone();
        } else if (string.equals("experiencebottle") && string.contains(":")) {
            return experiencebottle.clone();
        } else if (string.equals("explosivecake")) {
            return explosivecake.clone();
        } else if (string.equals("factioncrystal")) {
            return factioncrystal.clone();
        } else if (string.equals("factionmcmmobooster")) {
            return factionmcmmobooster.clone();
        } else if (string.equals("factionxpbooster")) {
            return factionxpbooster.clone();
            //
        } else if (string.equals("randombook")) {
            return randombook.clone();
        } else if (string.equals("godrandomizationscroll")) {
            return godRandomizationScroll.clone();
        } else if (string.equals("goddp")) {
            return godDropPackage.clone();
        } else if (string.equals("soulbook")) {
            return soulbook.clone();
        } else if (string.equals("legendarybook")) {
            return legendarybook.clone();
        } else if (string.equals("legendaryrandomizationscroll")) {
            return legendaryRandomizationScroll.clone();
        } else if (string.equals("legendarydp")) {
            return legendaryDropPackage.clone();
        } else if (string.equals("ultimatebook")) {
            return ultimatebook.clone();
        } else if (string.equals("ultimaterandomizationscroll")) {
            return ultimateRandomizationScroll.clone();
        } else if (string.equals("ultimatedp")) {
            return ultimateDropPackage.clone();
        } else if (string.equals("elitebook")) {
            return elitebook.clone();
        } else if (string.equals("eliteandomizationscroll")) {
            return eliteRandomizationScroll.clone();
        } else if (string.equals("elitedp")) {
            return eliteDropPackage.clone();
        } else if (string.equals("uniquebook")) {
            return uniquebook.clone();
        } else if (string.equals("uniquerandomizationscroll")) {
            return uniqueRandomizationScroll.clone();
        } else if (string.equals("uniquedp")) {
            return uniqueDropPackage.clone();
        } else if (string.equals("simplebook")) {
            return simplebook.clone();
        } else if (string.equals("simplerandomizationscroll")) {
            return simpleRandomizationScroll.clone();
        } else if (string.equals("simpledp")) {
            return simpleDropPackage.clone();
            //
        } else if (string.equals("halloweencandy")) {
            return halloweencandy.clone();
        } else if (string.equals("itemnametag")) {
            return itemnametag.clone();
        } else if (string.equals("mcmmocreditvoucher")) {
            return mcmmovoucherCredit.clone();
        } else if (string.equals("mcmmolevelvoucher")) {
            return mcmmovoucherLevel.clone();
        } else if (string.equals("mcmmoxpvoucher")) {
            return mcmmovoucherXP.clone();
        } else if (string.equals("mysterydust")) {
            return mysterydust.clone();
        } else if (string.equals("mysterymobspawner")) {
            return mysterymobspawner.clone();
        } else if (string.equals("sb") && string.contains(":")) {
            item = new ItemStack(Material.BOOK);
            itemMeta = item.getItemMeta();
            itemMeta.setDisplayName("randomhashtags was here");
            itemMeta.setLore(Arrays.asList(string.split(":")[1], string.split(":")[2], string.split(":")[3], string.split(":")[4]));
            item.setItemMeta(itemMeta);
            return item;
        } else if (string.equals("soulgem") && string.contains(":")) {
            return soulgem.clone();
        } else if (string.equals("spacedrink")) {
            return spacedrink.clone();
        } else if (string.equals("spacefirework")) {
            return spacefirework.clone();
        } else if (string.equals("transmogscroll")) {
            return transmogscroll.clone();
        } else if (string.equals("weaponenchantmentorb")) {
            return weaponenchantmentorb.clone();
        } else if (string.equals("whitescroll")) {
            return whitescroll.clone();
        } else {
            return null;
        }
    }

    @EventHandler
    private void playerJoinEvent(PlayerJoinEvent event) {
        getPlayerConfig(event.getPlayer());
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

    private ItemStack setupItem(FileConfiguration config, String path) {
        String iitem = config.getString(path + ".item").toLowerCase();
        if (Material.getMaterial(iitem.toUpperCase().split(":")[0]) == null) {
            if (iitem.equals("back") && config == shop) {
                return shopBackToCategory;
            } else if (iitem.contains("spawner")) {
                return new ItemStack(Material.MOB_SPAWNER);
            } else {
                Bukkit.broadcastMessage(RandomPackage.prefix + ChatColor.RED + " Invalid material for item \"" + iitem + "\"!");
                return new ItemStack(Material.AIR);
            }
        } else if (config.getString(path + ".item").toLowerCase().startsWith("potion:")) {
            item = convertStringToPotion(config.getString(path + ".item"), true);
            return item;
        } else {
            boolean enchant = false;
            int amount = 1;
            if (!(config.get(path + ".amount") == null)) {
                amount = Integer.parseInt(config.getString(path + ".amount"));
            }
            if (!(config.get(path + ".enchanted") == null) && config.getBoolean(path + ".enchanted")) {
                enchant = true;
            }
            item = new ItemStack(Material.getMaterial(config.getString(path + ".item").toUpperCase().split(":")[0]), amount, (byte) Integer.parseInt(config.getString(path + ".item").split(":")[1]));
            itemMeta = item.getItemMeta();
            lore.clear();
            if (!(config.get(path + ".name") == null)) {
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(path + ".name")));
            }
            if (path.startsWith("givedp-items.bosses.")) {
                for (String string : config.getStringList("givedp-items.bosses.dangerous-lore")) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', string));
                }
            }
            for (String string : config.getStringList(path + ".lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            itemMeta.setLore(lore);
            lore.clear();
            if (enchant) {
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            item.setItemMeta(itemMeta);
            if (enchant) {
                item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            }
            return item;
        }
    }

    public ItemStack getRPItem(RPItem rpi) {
        if (rpi.equals(RPItem.ARMOR_ENCHANTMENT_ORB)) {
            return armorenchantmentorb.clone();
        } else if (rpi.equals(RPItem.BANKNOTE)) {
            return banknote.clone();
        } else if (rpi.equals(RPItem.BLACK_SCROLL)) {
            return blackscroll.clone();
        } else if (rpi.equals(RPItem.CHRISTMAS_CANDY)) {
            return christmascandy.clone();
        } else if (rpi.equals(RPItem.CHRISTMAS_EGGNOG)) {
            return christmaseggnog.clone();
        } else if (rpi.equals(RPItem.EXPERIENCE_BOTTLE)) {
            return experiencebottle.clone();
        } else if (rpi.equals(RPItem.EXPLOSIVE_CAKE)) {
            return explosivecake.clone();
        } else if (rpi.equals(RPItem.FACTION_CRYSTAL)) {
            return factioncrystal.clone();
        } else if (rpi.equals(RPItem.FACTION_MCMMO_BOOSTER)) {
            return factionmcmmobooster.clone();
        } else if (rpi.equals(RPItem.FACTION_XP_BOOSTER)) {
            return factionxpbooster.clone();
            //} else if(rpi.equals(RPItem.GOD_RANDOMIZATION_SCROLL)) {
        } else if (rpi.equals(RPItem.HALLOWEEN_CANDY)) {
            return halloweencandy.clone();
        } else if (rpi.equals(RPItem.ITEM_NAME_TAG)) {
            return itemnametag.clone();
        } else if (rpi.equals(RPItem.MCMMO_CREDIT_VOUCHER)) {
            return mcmmovoucherCredit.clone();
        } else if (rpi.equals(RPItem.MCMMO_LEVEL_VOUCHER)) {
            return mcmmovoucherLevel.clone();
        } else if (rpi.equals(RPItem.MCMMO_XP_VOUCHER)) {
            return mcmmovoucherXP.clone();
        } else if (rpi.equals(RPItem.MYSTERY_DUST)) {
            return mysterydust.clone();
        } else if (rpi.equals(RPItem.MYSTERY_MOB_SPAWNER)) {
            return mysterymobspawner.clone();
            //} else if(rpi.equals(RPItem.RANDOM_BOOK)) { return randombook;
        } else if (rpi.equals(RPItem.SOUL_GEM)) {
            return soulgem.clone();
        } else if (rpi.equals(RPItem.SPACE_DRINK)) {
            return spacedrink.clone();
        } else if (rpi.equals(RPItem.SPACE_FIREWORK)) {
            return spacefirework.clone();
        } else if (rpi.equals(RPItem.TRANSMOG_SCROLL)) {
            return transmogscroll.clone();
        } else if (rpi.equals(RPItem.WEAPON_ENCHANTMENT_ORB)) {
            return weaponenchantmentorb.clone();
        } else if (rpi.equals(RPItem.WHITE_SCROLL)) {
            return whitescroll.clone();
        } else {
            return null;
        }
    }

    //
    @SuppressWarnings("deprecation")
    public void giveItem(Player player, ItemStack is) {
        if (player.getInventory().firstEmpty() < 0 && !(is.hasItemMeta()) && !(player.getInventory().getItem(player.getInventory().first(new ItemStack(is.getType(), player.getInventory().getItem(player.getInventory().first(is.getType())).getAmount(), is.getData().getData()))).getAmount() <= is.getMaxStackSize())) {
            player.getWorld().dropItem(player.getLocation(), is);
        } else if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), is);
        } else {
            player.getInventory().addItem(is);
            player.updateInventory();
        }
    }

    @SuppressWarnings("deprecation")
    public void removeItem(Player player, ItemStack is, int removeAmount) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (!(player.getInventory().getItem(i) == null) && player.getInventory().getItem(i).getData().getData() == is.getData().getData() && player.getInventory().getItem(i).getType().equals(is.getType())) {
                if (!(is.hasItemMeta()) || is.hasItemMeta() && is.getItemMeta().equals(player.getInventory().getItem(i).getItemMeta())) {
                    for (int z = 1; z <= removeAmount; z++) {
                        if (player.getInventory().getItem(i).getAmount() == 1) {
                            player.getInventory().setItem(i, new ItemStack(Material.AIR));
                        } else {
                            player.getInventory().getItem(i).setAmount(player.getInventory().getItem(i).getAmount() - 1);
                        }
                    }
                    return;
                }
            }
        }
        player.updateInventory();
    }

    private void armorSwitch(Player player, ItemStack item) {
        String path = "armor-switch.";
        ItemStack prev = null;
        if (item.getType().name().endsWith("HELMET")) {
            path = path + "helmet";
            prev = player.getInventory().getHelmet();
            player.getInventory().setHelmet(item);
        } else if (item.getType().name().endsWith("CHESTPLATE")) {
            path = path + "chestplate";
            prev = player.getInventory().getChestplate();
            player.getInventory().setChestplate(item);
        } else if (item.getType().name().endsWith("LEGGINGS")) {
            path = path + "leggings";
            prev = player.getInventory().getLeggings();
            player.getInventory().setLeggings(item);
        } else if (item.getType().name().endsWith("BOOTS")) {
            path = path + "boots";
            prev = player.getInventory().getBoots();
            player.getInventory().setBoots(item);
        }
        setItemInHand(player, prev);
        player.updateInventory();
        getAndPlaySound(player, player.getLocation(), path, false);
    }

    public ItemStack getItemInHand(Player player) {
        if (Bukkit.getVersion().contains("1.8")) {
            return v1_8.getItemInHand(player);
        } else {
            return v1_9.getItemInHand(player);
        }
    }

    private void setItemInHand(Player player, ItemStack item) {
        if (Bukkit.getVersion().contains("1.8")) {
            v1_8.setItemInHand(player, item);
        } else {
            v1_9.setItemInHand(player, item);
        }
    }

    public void getAndPlaySound(Player player, Location location, String path, boolean globalSound) {
        if (Bukkit.getVersion().contains("1.8")) {
            player.playSound(player.getLocation(), Sound.valueOf(sounds.getString("1.8.8." + path + ".sound").toUpperCase()), sounds.getInt("1.8.8." + path + ".volume"), sounds.getInt("1.8.8." + path + ".pitch"));
        } else {
            player.playSound(player.getLocation(), Sound.valueOf(sounds.getString("1.9+." + path + ".sound").toUpperCase()), sounds.getInt("1.9+." + path + ".volume"), sounds.getInt("1.9+." + path + ".pitch"));
        }
    }

    public void sendStringListMessage(Player player, String path, double d) {
        for (String string : messages.getStringList(path)) {
            if (string.contains("{PREFIX}")) {
                string = string.replace("{PREFIX}", configPrefix);
            }
            if (string.contains("{AMOUNT}")) {
                string = string.replace("{AMOUNT}", "" + d);
            } else if (string.contains("{BUY_PRICE}") && path.equals("shop.not-enough-balance")) {
                string = string.replace("{BUY_PRICE}", "" + d);
            }
            string = ChatColor.translateAlternateColorCodes('&', string);
            if (player == null) {
                Bukkit.getConsoleSender().sendMessage(string);
            } else {
                player.sendMessage(string);
            }
        }
    }

    //
    private PotionEffectType getPotionEffectType(String string) {
        string = string.toLowerCase();
        if (string.startsWith("f")) {
            return PotionEffectType.FIRE_RESISTANCE;
        } else if (string.startsWith("i")) {
            return PotionEffectType.INVISIBILITY;
        } else if (string.startsWith("st") || string.equalsIgnoreCase("increase_damage")) {
            return PotionEffectType.INCREASE_DAMAGE;
        } else if (string.startsWith("wa")) {
            return PotionEffectType.WATER_BREATHING;
        } else if (string.startsWith("reg")) {
            return PotionEffectType.REGENERATION;
        } else if (string.startsWith("sp")) {
            return PotionEffectType.SPEED;
        } else if (string.startsWith("po")) {
            return PotionEffectType.POISON;
        } else if (string.startsWith("instanth") || string.equals("heal")) {
            return PotionEffectType.HEAL;
        } else if (string.startsWith("n")) {
            return PotionEffectType.NIGHT_VISION;
        } else if (string.startsWith("we")) {
            return PotionEffectType.WEAKNESS;
        } else if (string.startsWith("sl")) {
            return PotionEffectType.SLOW;
        } else if (string.startsWith("m") || string.equals("mining_fatigue")) {
            return PotionEffectType.SLOW_DIGGING;
        } else if (string.startsWith("j")) {
            return PotionEffectType.JUMP;
        } else if (string.startsWith("healt")) {
            return PotionEffectType.HEALTH_BOOST;
        } else if (string.startsWith("dam") || string.startsWith("dmg")) {
            return PotionEffectType.DAMAGE_RESISTANCE;
        } else if (string.startsWith("wi")) {
            return PotionEffectType.WITHER;
        } else if (string.startsWith("bl")) {
            return PotionEffectType.BLINDNESS;
        } else if (string.startsWith("ab")) {
            return PotionEffectType.ABSORPTION;
        } else if (string.startsWith("co")) {
            return PotionEffectType.CONFUSION;
        } else if (string.startsWith("has")) {
            return PotionEffectType.FAST_DIGGING;
        } else if (string.startsWith("har")) {
            return PotionEffectType.HARM;
        } else if (string.startsWith("hu")) {
            return PotionEffectType.HUNGER;
        } else if (string.startsWith("sa")) {
            return PotionEffectType.SATURATION;
        } else {
            Bukkit.broadcastMessage(string);
            return null;
        }
    }

    private void givePotionEffects(LivingEntity damager, Player victim, String OEN, int level) {
        int clarity = -1, metaphysical = -1;
        if (!(victim == null)) {
            for (ItemStack is : victim.getInventory().getArmorContents()) {
                if (!(is == null) && is.hasItemMeta() && is.getItemMeta().hasLore()) {
                    for (String enchant : api.getEnchantmentsOnItem(is)) {
                        String OEn = enchant.split(" ")[0];
                        if (OEn.equals("Clarity")) {
                            if (api.getEnchantmentLevel(enchant) > clarity) {
                                clarity = api.getEnchantmentLevel(enchant);
                            }
                        } else if (OEn.equals("Metaphysical")) {
                            metaphysical = evaluateProckChance(enchants.get(OEn), Integer.parseInt(enchant.split(" ")[1]), victim);
                        }
                    }
                }
            }
        }
        for (int i = 0; i <= 10; i++) {
            if (!(enchantments.get(OEN + ".potion-effects." + i) == null)) {
                String[] string = enchantments.getString(OEN + ".potion-effects." + i).toLowerCase().split("\\:");
                PotionEffect effect = new PotionEffect(getPotionEffectType(string[0]), evaluateProckChance(string[2], level, victim), evaluateProckChance(string[1], level, victim));
                if (!(damager == null)) {
                    damager.addPotionEffect(effect);
                }
                if (!(victim == null)) {
                    if (effect.getType().equals(PotionEffectType.BLINDNESS) && clarity >= (effect.getAmplifier() + 1)) {
                        effect = null;
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + victim.getName() + " ~ ~ ~ particle cloud " + victim.getEyeLocation().getX() + " " + (victim.getEyeLocation().getY() + 0.65) + " " + victim.getEyeLocation().getZ() + " 0.3 0.2 0.3 0 10");
                    }
                    if (effect.getType().equals(PotionEffectType.SLOW) && !(metaphysical == -1) && random.nextInt(100) <= metaphysical) {
                        effect = null;
                        for (int o = 1; o <= 5; o++) {
                            victim.getWorld().playEffect(victim.getEyeLocation(), Effect.BOW_FIRE, 1);
                        }
                    }
                    if (!(effect == null)) {
                        victim.addPotionEffect(effect);
                    }
                }
            }
        }
    }

    public int evaluateProckChance(String string, int level, Player player) {
        if (string.split(" ")[0].equals("Dodge")) {

        } else if (!(api.enchants.get(string.split(" ")[0]) == null)) {
            string = api.enchants.get(string.split(" ")[0]);
        }
        int chance = 0;
        if (string.contains("lucky")) {
            string = string.replace("lucky", api.enchants.get("Lucky"));
        }
        if (string.contains("level")) {
            string = string.replace("level", "" + level);
        }
        // P.E.M.D.A.S.
        String equ = null;
        for (int i = 1; i <= 2; i++) {
            int startP = -1, endP = -1;
            for (int z = 0; z < string.length(); z++) {
                if (string.substring(z, z + 1).equals("(")) {
                    startP = z;
                } else if (string.substring(z, z + 1).equals(")")) {
                    endP = z + 1;
                }
                if (!(startP == -1) && !(endP == -1)) {
                    equ = string.substring(startP, endP);
                    if (equ.contains("*")) {
                        chance = Math.multiplyExact(Integer.parseInt(equ.split("\\*")[0].replace("(", "")), Integer.parseInt(equ.split("\\*")[1].replace(")", "")));
                    } else if (equ.contains("+")) {
                        chance = Math.addExact(Integer.parseInt(equ.split("\\+")[0].replace("(", "")), Integer.parseInt(equ.split("\\+")[1].replace(")", "")));
                    } else if (equ.contains("-")) {
                        chance = Math.subtractExact(Integer.parseInt(equ.split("\\-")[0].replace("(", "")), Integer.parseInt(equ.split("\\-")[1].replace(")", "")));
                    } else if (equ.contains("/")) {
                        chance = Math.floorDiv(Integer.parseInt(equ.split("\\/")[0].replace("(", "")), Integer.parseInt(equ.split("\\/")[1].replace(")", "")));
                    }
                    string = string.replace(equ, "" + chance);
                }
            }
        }
        if (string.contains("+")) {
            chance = Math.addExact(Integer.parseInt(string.split("\\+")[0]), Integer.parseInt(string.split("\\+")[1]));
        } else if (string.contains("-")) {
            chance = Math.subtractExact(Integer.parseInt(string.split("\\-")[0]), Integer.parseInt(string.split("\\-")[1]));
        }
        return chance;
    }

    public int getRemainingIntFromString(String string) {
        string = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)).replaceAll("\\p{L}", "").replaceAll("\\s", "").replaceAll("\\p{P}", "").replaceAll("\\p{S}", "");
        if (string == null || string.equals("")) {
            return -1;
        } else {
            return Integer.parseInt(string);
        }
    }

    public int getRandomBlackScrollPercent() {
        if (masterconfig.getBoolean("givedp-items.black-scroll.randomized-percent")) {
            return random.nextInt(maxblackscroll - minblackscroll) + minblackscroll;
        } else {
            return masterconfig.getInt("givedp-items.black-scroll.default-percent");
        }
    }

    /*
     *
	 * Commands
	 *
	 */
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            if (cmd.getName().equals("givedp") && args.length <= 1 || cmd.getName().equals("givedp") && args.length >= 2 && args[1].equalsIgnoreCase("sb")
                    || cmd.getName().equals("gkit") && args.length == 0
                    || cmd.getName().equals("vkit") && args.length == 0
                    ) {
                Bukkit.getConsoleSender().sendMessage("[RandomPackage] " + ChatColor.RED + "Only players can execute this command!");
                return true;
            }
        }
        if (cmd.getName().equals("disabledenchants")) {
            sender.sendMessage(configPrefix + ChatColor.YELLOW + " Currently disabled enchantments:");
            sender.sendMessage(disabledenchantments.toString());
        } else if (cmd.getName().equals("givedp")) {
            if (args.length == 0) {
                openGui((Player) sender, RPGui.GIVEDP);
                return true;
            } else if (args.length == 1) {

                return true;
            } else if (args.length >= 2 && args.length <= 3
                    || args.length >= 2 && args[1].toLowerCase().equals("sb")
                    || args.length == 3 && args[1].equals("21") || args.length >= 3 && args[1].equalsIgnoreCase("soulgem") && args.length <= 4
                    || args.length == 3 && args[1].equals("15") || args.length == 3 && args[1].equalsIgnoreCase("mcmmocreditvoucher")
                    || args.length == 4 && args[1].equals("15") || args.length == 4 && args[1].equalsIgnoreCase("mcmmocreditvoucher")
                    || args.length == 4 && args[1].equals("16") || args.length == 4 && args[1].equalsIgnoreCase("mcmmolevelvoucher")
                    || args.length == 4 && args[1].equals("17") || args.length == 4 && args[1].equalsIgnoreCase("mcmmoxpvoucher")
                    ) {
                Player target = Bukkit.getPlayer(args[0]);
                String string = args[1].toLowerCase();
                RPItem rpi = null;
                int dp = -1;
                if (string.equals("1") || string.startsWith("armore")) {
                    rpi = RPItem.ARMOR_ENCHANTMENT_ORB;
                } else if (string.equals("2")) {
                    rpi = RPItem.BANKNOTE;
                } else if (string.equals("3") || string.equals("blackscroll")) {
                    rpi = RPItem.BLACK_SCROLL;
                } else if (string.equals("4") || string.equals("christmascandy") || string.equals("christmasc") || string.equals("cc")) {
                    rpi = RPItem.CHRISTMAS_CANDY;
                } else if (string.equals("5") || string.equals("christmaseggnog") || string.equals("christmase") || string.equals("ce")) {
                    rpi = RPItem.CHRISTMAS_EGGNOG;
                } else if (string.equals("6") || string.equals("xpbottle")) {
                    rpi = RPItem.EXPERIENCE_BOTTLE;
                } else if (string.equals("7") || string.equals("explosivecake")) {
                    rpi = RPItem.EXPLOSIVE_CAKE;
                } else if (string.equals("8") || string.equals("factioncrystal") || string.equals("factionc")) {
                    rpi = RPItem.FACTION_CRYSTAL;
                } else if (string.equals("9") || string.equals("factionmcmmobooster") || string.equals("factionmcmmob")) {
                    rpi = RPItem.FACTION_MCMMO_BOOSTER;
                } else if (string.equals("10") || string.equals("factionxpbooster") || string.equals("factionxpb")) {
                    rpi = RPItem.FACTION_XP_BOOSTER;
                } else if (string.equals("12") || string.equals("halloweencandy") | string.equals("haloweenc")) {
                    rpi = RPItem.HALLOWEEN_CANDY;
                } else if (string.equals("13") || string.equals("itemnametag")) {
                    rpi = RPItem.ITEM_NAME_TAG;
                } else if (string.equals("14") || string.equals("mcmmocreditvoucher")) {
                    rpi = RPItem.MCMMO_CREDIT_VOUCHER;
                } else if (string.equals("15") || string.equals("mcmmolevelvoucher")) {
                    rpi = RPItem.MCMMO_LEVEL_VOUCHER;
                } else if (string.equals("16") || string.equals("mcmmoxpvoucher")) {
                    rpi = RPItem.MCMMO_XP_VOUCHER;
                } else if (string.equals("17") || string.equals("mysterydust") || string.equals("mysteryd")) {
                    rpi = RPItem.MYSTERY_DUST;
                } else if (string.equals("18") || string.equals("mysterymobspawner") || string.equals("mms")) {
                    rpi = RPItem.MYSTERY_MOB_SPAWNER;
                } else if (string.equals("19") || string.equals("randombook") || string.equals("randomb")) {
                    rpi = RPItem.RANDOM_BOOK;
                } else if (string.equals("20") || string.equals("soulgem")) {
                    rpi = RPItem.SOUL_GEM;
                } else if (string.equals("21")) {
                    rpi = RPItem.RANDOM_BOOK;
                } else if (string.equals("22")) {
                    rpi = RPItem.SOUL_BOOK;
                } else if (string.equals("22f")) {
                    rpi = RPItem.SOUL_FIREBALL;
                } else if (string.equals("23")) {
                    rpi = RPItem.LEGENDARY_BOOK;
                } else if (string.equals("23f")) {
                    rpi = RPItem.LEGENDARY_FIREBALL;
                } else if (string.equals("23rs")) {
                    rpi = RPItem.LEGENDARY_RANDOMIZATION_SCROLL;
                } else if (string.equals("23st")) {
                    rpi = RPItem.LEGENDARY_SOUL_TRACKER;
                } else if (string.equals("24")) {
                    rpi = RPItem.ULTIMATE_BOOK;
                } else if (string.equals("24f")) {
                    rpi = RPItem.ULTIMATE_FIREBALL;
                } else if (string.equals("24rs")) {
                    rpi = RPItem.ULTIMATE_RANDOMIZATION_SCROLL;
                } else if (string.equals("24st")) {
                    rpi = RPItem.ULTIMATE_SOUL_TRACKER;
                } else if (string.equals("25")) {
                    rpi = RPItem.ELITE_BOOK;
                } else if (string.equals("25f")) {
                    rpi = RPItem.ELITE_FIREBALL;
                } else if (string.equals("25rs")) {
                    rpi = RPItem.ELITE_RANDOMIZATION_SCROLL;
                } else if (string.equals("25st")) {
                    rpi = RPItem.ELITE_SOUL_TRACKER;
                } else if (string.equals("26")) {
                    rpi = RPItem.UNIQUE_BOOK;
                } else if (string.equals("26f")) {
                    rpi = RPItem.UNIQUE_FIREBALL;
                } else if (string.equals("26rs")) {
                    rpi = RPItem.UNIQUE_RANDOMIZATION_SCROLL;
                } else if (string.equals("26st")) {
                    rpi = RPItem.UNIQUE_SOUL_TRACKER;
                } else if (string.equals("27")) {
                    rpi = RPItem.SIMPLE_BOOK;
                } else if (string.equals("27f")) {
                    rpi = RPItem.SIMPLE_FIREBALL;
                } else if (string.equals("27rs")) {
                    rpi = RPItem.SIMPLE_RANDOMIZATION_SCROLL;
                } else if (string.equals("27st")) {
                    rpi = RPItem.SIMPLE_SOUL_TRACKER;
                } else if (string.equals("28")) {
                    rpi = RPItem.GOD_DP;
                } else if (string.equals("29")) {
                    rpi = RPItem.LEGENDARY_DP;
                } else if (string.equals("30")) {
                    rpi = RPItem.ULTIMATE_DP;
                } else if (string.equals("31")) {
                    rpi = RPItem.ELITE_DP;
                } else if (string.equals("32")) {
                    rpi = RPItem.UNIQUE_DP;
                } else if (string.equals("33")) {
                    rpi = RPItem.SIMPLE_DP;
                } else if (string.equals("sb")) { // givedp <player> sb <Original Enchant Name> <level>
                    if (args.length == 2) {

                        return true;
                    } else if (args.length == 3 || RandomPackageAPI.getInstance().getOriginalEnchantmentName(args[2]) == null) {

                        return true;
                    } else {
                        String OEN = RandomPackageAPI.getInstance().getOriginalEnchantmentName(args[2]), enchant = masterconfig.getString(OEN + ".enchant-name") + " " + replaceIntWithRomanNumerals(getRemainingIntFromString(args[3])), rarity = enchantmentsInfo.getString(OEN + ".rarity").toLowerCase();
                        enchant = ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.name").replace("{ENCHANT}", enchant));
                        item = formatBookLore(getConfigItem(masterconfig, "givedp-items." + rarity + ".revealed-item"), enchant, 100, 0);
                    }
                } else if (string.equals("21") || string.equals("spacedrink") || string.equals("spaced")) {
                    rpi = RPItem.SPACE_DRINK;
                } else if (string.equals("22") || string.equals("spacefirework") || string.equals("spacef")) {
                    rpi = RPItem.SPACE_FIREWORK;
                } else if (string.equals("23") || string.equals("transmogscroll") || string.equals("transmog")) {
                    rpi = RPItem.TRANSMOG_SCROLL;
                } else if (string.equals("24") || string.equals("weaponenchantmentorb") || string.equals("weo")) {
                    rpi = RPItem.WEAPON_ENCHANTMENT_ORB;
                } else if (string.equals("25") || string.equals("whitescroll")) {
                    rpi = RPItem.WHITE_SCROLL;
                } else if (string.startsWith("fallenhero")) {
                    if (!(getRemainingIntFromString(string) == -1)) {
                        dp = getRemainingIntFromString(string);
                        if (string.startsWith("fallenhero") && !(fallenheroes[dp] == null)) {
                            item = fallenheroes[dp].clone();
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
                if (!(rpi == null) && !(commandsAndFeatures.getBoolean("features." + rpi.name().toLowerCase().replace("boss", "bosses.").replace("_", "-")))) {
                    return true;
                }
                if (!(string.equals("sb"))) {
                    if (!(rpi == null)) {
                        item = RandomPackageAPI.getInstance().getRPItem(rpi);
                    }
                    itemMeta = item.getItemMeta();
                    lore.clear();
                    if (!(rpi == null) && rpi.equals(RPItem.SOUL_GEM)) {
                        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{SOULS}", "" + getRemainingIntFromString((args[2]))));
                    }
                    if (itemMeta.hasLore()) {
                        for (String s : itemMeta.getLore()) {
                            if (s.contains("{PERCENT}") && !(rpi == null)) {
                                if (rpi.equals(RPItem.BLACK_SCROLL)) {
                                    s = s.replace("{PERCENT}", "" + RandomPackageAPI.getInstance().getRandomBlackScrollPercent());
                                }
                            } else if (s.contains("{CREDITS}") && !(rpi == null)) {
                                if (rpi.equals(RPItem.MCMMO_CREDIT_VOUCHER)) {
                                    s = s.replace("{CREDITS}", formatIntUsingCommas(getRemainingIntFromString(args[2])));
                                }
                            } else if (s.contains("{SKILL}") && !(rpi == null)) {
                                //if(rpi.equals(RPItem.MCMMO_LEVEL_VOUCHER) || rpi.equals(RPItem.MCMMO_XP_VOUCHER)) { s = s.replace("{SKILL}", "" + mcmmoEvents.getInstance().getSkillName(mcmmoEvents.getInstance().getSkill(args[2])));
                                //}
                            } else if (s.contains("{XP}") && !(rpi == null)) {
                                if (rpi.equals(RPItem.MCMMO_XP_VOUCHER)) {
                                    s = s.replace("{XP}", formatIntUsingCommas(getRemainingIntFromString(args[3])));
                                }
                            } else if (s.contains("{LEVEL}") && !(rpi == null)) {
                                if (rpi.equals(RPItem.MCMMO_LEVEL_VOUCHER)) {
                                    s = s.replace("{LEVEL}", formatIntUsingCommas(getRemainingIntFromString(args[3])));
                                }
                            } else if (s.contains("{PLAYER}") && !(rpi == null)) {
                                if (rpi.equals(RPItem.MCMMO_XP_VOUCHER) || rpi.equals(RPItem.MCMMO_LEVEL_VOUCHER)) {
                                    s = s.replace("{PLAYER}", "Givedp");
                                }
                            }
                            lore.add(ChatColor.translateAlternateColorCodes('&', s));
                        }
                        itemMeta.setLore(lore);
                        lore.clear();
                    }
                    item.setItemMeta(itemMeta);
                    if (args.length == 3 && rpi == null || args.length == 3 && !(rpi.equals(RPItem.SOUL_GEM)) && !(rpi.equals(RPItem.MCMMO_CREDIT_VOUCHER))) {
                        item.setAmount(getRemainingIntFromString(args[2]));
                    } else if (args.length == 4) {
                        if (!(rpi == null)) {
                            if (rpi.equals(RPItem.SOUL_GEM) || rpi.equals(RPItem.MCMMO_CREDIT_VOUCHER)) {
                                item.setAmount(getRemainingIntFromString(args[3]));
                            }
                        }
                    }
                }
                giveItem(target, item);
            }
        } else if (cmd.getName().equalsIgnoreCase("gkit")) {
            if (args.length == 0) {
                openGui((Player) sender, RPGui.GKIT);
            } else if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
                if (!(Bukkit.getPlayer(args[1]) == null)) {
                    Player target = Bukkit.getPlayer(args[1]);
                    File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "data" + File.separator, "redeemed-kits.yml");
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    for (int i = 0; i < 54; i++) {
                        config.set("gkits." + target.getUniqueId().toString() + "_" + i, null);
                    }
                    try {
                        config.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (String string : messages.getStringList("gkit.reset")) {
                        if (string.contains("{PREFIX}")) {
                            string = string.replace("{PREFIX}", configPrefix);
                        }
                        if (string.contains("{PLAYER}")) {
                            string = string.replace("{PLAYER}", target.getName());
                        }
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                    }
                }
            }
        } else if (cmd.getName().equals("randompackage")) {
            if (args.length == 0) {
                sender.sendMessage(" ");
                sender.sendMessage(RandomPackage.prefix);
                sender.sendMessage(ChatColor.AQUA + "Author: " + ChatColor.YELLOW + "RandomHashTags");
                sender.sendMessage(ChatColor.AQUA + "Version: " + ChatColor.GREEN + "v" + RandomPackage.getPlugin.getDescription().getVersion());
                sender.sendMessage(ChatColor.AQUA + "Website: " + ChatColor.GREEN + RandomPackage.getPlugin.getDescription().getWebsite());
                sender.sendMessage(" ");
            } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                Bukkit.getPluginManager().disablePlugin(RandomPackage.getPlugin);
                Bukkit.getPluginManager().enablePlugin(RandomPackage.getPlugin);
                sender.sendMessage(RandomPackage.prefix + ChatColor.GREEN + "RandomPackage successfully reloaded.");
            } else {

            }
        } else if (cmd.getName().equalsIgnoreCase("showcase")) {
            if (args.length == 0 || args.length == 1 && !(Bukkit.getPlayer(args[0]) == null) && Bukkit.getPlayer(args[0]) == (Player) sender) {
                RandomPackageAPI.getInstance().openShowcase((Player) sender, (Player) sender);
            } else if (!(Bukkit.getPlayer(args[0]) == null)) {
                RandomPackageAPI.getInstance().openShowcase((Player) sender, Bukkit.getPlayer(args[0]));
            }
        } else if (cmd.getName().equals("vkit")) {
            if (args.length == 0) {
                openGui((Player) sender, RPGui.VKIT);
            } else {
                if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {

                }
            }
        }
        return true;
    }

    @EventHandler
    private void playerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (!(event.isCancelled() == true)) {
            Player player = event.getPlayer();
            String permission = null, command = event.getMessage().substring(1).toLowerCase();
            if (command.startsWith("alchemist")) {
                permission = "RandomPackage.commands.alchemist";
            } else if (command.startsWith("bless")) {
                permission = "RandomPackage.commands.bless";
            } else if (command.startsWith("enchanter")) {
                permission = "RandomPackage.commands.enchanter";
            } else if (command.startsWith("filter")) {
                permission = "RandomPackage.commands.filter";
            } else if (command.startsWith("roll")) {
                permission = "RandomPackage.commands.roll";
            } else if (command.startsWith("shop")) {
                permission = "RandomPackage.commands.shop";
            } else if (command.startsWith("showcase")) {
                permission = "RandomPackage.commands.showcase";
            } else if (command.startsWith("splitsouls") || command.startsWith("ws")) {
                permission = "RandomPackage.commands.splitsouls";
            } else if (command.startsWith("tinkerer")) {
                permission = "RandomPackage.commands.tinkerer";
            } else if (command.startsWith("withdraw")) {
                permission = "RandomPackage.commands.withdraw";
            } else if (command.startsWith("xpbottle")) {
                permission = "Randompackage.commands.xpbottle";
            } else {
                return;
            }
            event.setCancelled(true);
            //
            if (event.getPlayer().hasPermission(permission)) {
                if (command.startsWith("alchemist")) {
                    openGui(event.getPlayer(), RPGui.ALCHEMIST);
                } else if (command.startsWith("enchanter")) {
                    openGui(event.getPlayer(), RPGui.ENCHANTER);
                } else if (command.startsWith("filter")) {
                    if (!(command.startsWith("filter "))) {
                        openGui(event.getPlayer(), RPGui.FILTER);
                    }
                } else if (command.startsWith("shop")) {
                    openGui(event.getPlayer(), RPGui.SHOP);
                } else if (command.startsWith("showcase")) {
                    if (!(command.startsWith("showcase "))) {
                        openShowcase(player, player);
                    } else {

                    }
                } else if (command.startsWith("splitsouls")) {
                    item = getItemInHand(player);
                    int souls = 0, gems = 0, remainingSouls = 0;
                    if (item == null || !(item.hasItemMeta()) || !(item.getItemMeta().hasLore())) {
                        sendStringListMessage(player, "splitsouls.need-item-with-soul-tracker", 0);
                    } else {
                        itemMeta = item.getItemMeta();
                        lore.clear();
                        lore.addAll(itemMeta.getLore());
                        if (lore.equals(soulgemlore) && item.getItemMeta().hasDisplayName()) {
                            souls = getRemainingIntFromString(item.getItemMeta().getDisplayName());
                        } else if (getRemainingIntFromString(lore.get(lore.size() - 1)) <= 0) {
                            sendStringListMessage(player, "splitsouls.need-to-collect-souls", 0);
                        } else {
                            String rarity = null;
                            for (String string : apply_soultracker) {
                                if (lore.get(lore.size() - 1).startsWith(string.replace("{SOULS}", ""))) {
                                    souls = getRemainingIntFromString(lore.get(lore.size() - 1));
                                    rarity = getSoulTrackerRarity(string);
                                    //
                                    gems = souls * getSoulTrackerMultiplyInConfig(string);
                                    if (!(getRemainingIntFromString(command) == -1)) {
                                        if (souls - Integer.parseInt(command) > 0) {
                                            souls = Integer.parseInt(command);
                                            remainingSouls = getRemainingIntFromString(lore.get(lore.size() - 1)) - Integer.parseInt(command);
                                        }
                                    }
                                    lore.set(lore.size() - 1, string.replace("{SOULS}", Integer.toString(remainingSouls)));
                                }
                            }
                            itemMeta.setLore(lore);
                            lore.clear();
                            item.setItemMeta(itemMeta);
                            player.updateInventory();
                            item = soulgem.clone();
                            itemMeta = item.getItemMeta();
                            itemMeta.setDisplayName(item.getItemMeta().getDisplayName().replace("{SOULS}", getSoulGemColors(gems) + gems));
                            item.setItemMeta(itemMeta);
                            giveItem(player, item);
                            for (String string : messages.getStringList("splitsouls.split-" + rarity)) {
                                if (string.contains("{SOULS}")) {
                                    string = string.replace("{SOULS}", Integer.toString(souls));
                                }
                                if (string.contains("{GEMS}")) {
                                    string = string.replace("{GEMS}", Integer.toString(gems));
                                }
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                            }
                        }
                    }
                } else if (command.startsWith("tinkerer")) {
                    openGui(player, RPGui.TINKERER);
                } else if (command.startsWith("withdraw")) {
                    double amount = getRemainingDoubleFromString(command);
                    if (RP_Vault.economy == null) {
                        Bukkit.broadcastMessage(configPrefix + "You need an Economy plugin installed that is compatible with Vault to execute this command!");
                        return;
                    } else if (!(command.startsWith("withdraw "))) {
                        sendStringListMessage(player, "withdraw.argument-0", 0);
                    } else if (amount <= 0.00) {
                        sendStringListMessage(player, "withdraw.cannot-withdraw-zero", 0);
                    } else if (amount > RP_Vault.economy.getBalance(player)) {
                        sendStringListMessage(player, "withdraw.cannot-withdraw-more-than-balance", 0);
                    } else {
                        RP_Vault.economy.withdrawPlayer(player, amount);
                        getAndPlaySound(player, player.getLocation(), "withdraw.withdraw", false);
                        item = RandomPackageAPI.getInstance().getRPItem(RPItem.BANKNOTE).clone();
                        itemMeta = item.getItemMeta();
                        lore.clear();
                        for (String string : itemMeta.getLore()) {
                            if (string.contains("{AMOUNT}")) {
                                string = string.replace("{AMOUNT}", formatDoubleUsingCommas(amount));
                            }
                            if (string.contains("{PLAYER}")) {
                                string = string.replace("{PLAYER}", player.getName());
                            }
                            lore.add(ChatColor.translateAlternateColorCodes('&', string));
                        }
                        itemMeta.setLore(lore);
                        lore.clear();
                        item.setItemMeta(itemMeta);
                        giveItem(player, item);
                        sendStringListMessage(player, "withdraw.success", amount);
                    }
                } else if (command.startsWith("xpbottle")) {
                    if (!(command.startsWith("xpbottle "))) {
                        sendStringListMessage(player, "xpbottle.argument-0", 0);
                    } else if (getRemainingIntFromString(command) <= 0) {
                        sendStringListMessage(player, "xpbottle.invalid-xpbottle-amount", getRemainingIntFromString(command));
                    } else if (getRemainingIntFromString(command) > getTotalExperience(player)) {
                        sendStringListMessage(player, "xpbottle.not-enough-xp-to-bottle", 0);
                    } else {
                        int amount = getRemainingIntFromString(command);
                        setTotalExperience(player, getTotalExperience(player) - amount);
                        item = RandomPackageAPI.getInstance().getRPItem(RPItem.EXPERIENCE_BOTTLE);
                        itemMeta = item.getItemMeta();
                        lore.clear();
                        for (String string : RandomPackage.getConfig(RPConfigs.MASTER).getStringList("givedp-items.experience-bottle.lore")) {
                            if (string.contains("{AMOUNT}")) {
                                string = string.replace("{AMOUNT}", formatIntUsingCommas(amount));
                            }
                            if (string.contains("{BOTTLER_NAME}")) {
                                string = string.replace("{BOTTLER_NAME}", player.getName());
                            }
                            lore.add(ChatColor.translateAlternateColorCodes('&', string));
                        }
                        itemMeta.setLore(lore);
                        lore.clear();
                        item.setItemMeta(itemMeta);
                        giveItem(player, item);
                        sendStringListMessage(player, "xpbottle.withdraw", amount);
                        getAndPlaySound(player, player.getLocation(), "xpbottle", false);
                    }
                }
            } else {

            }
        }
        return;
    }

    private void openShowcase(Player player, Player target) {
        FileConfiguration config = null;
        if (target == null || target == player) {
            config = getPlayerConfig(player);
            player.openInventory(Bukkit.createInventory(player, config.getInt("showcase.size"), SHOWCASE_SELF));
        } else {
            config = getPlayerConfig(target);
            player.openInventory(Bukkit.createInventory(player, config.getInt("showcase.size"), SHOWCASE_OTHER.replace("{PLAYER}", target.getName())));
        }
        for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
            if (!(config.get("showcase.items." + i) == null)) {
                player.getOpenInventory().getTopInventory().setItem(i, config.getItemStack("showcase.items." + i));
            }
        }
    }

    private double getRemainingDoubleFromString(String string) {
        string = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', string)).replaceAll("\\p{M}", "").replaceAll("\\p{L}", "").replaceAll("\\s", "").replace(".", "period").replaceAll("\\p{P}", "").replaceAll("period", ".").replaceAll("\\p{S}", "");
        if (string == null || string.equals("")) {
            return -1.00;
        } else {
            return Double.parseDouble(string);
        }
    }

    private String formatDoubleUsingCommas(double d) {
        String decimals = Double.toString(d).split("\\.")[1];
        if (decimals.equals("0")) {
            decimals = "";
        } else {
            decimals = "." + decimals;
        }
        return formatIntUsingCommas((int) d) + decimals;
    }

    private String formatIntUsingCommas(int integer) {
        return String.format("%,d", integer);
    }

    private void openGui(Player player, RPGui gui) {
        Inventory inv;
        if (gui.equals(RPGui.ALCHEMIST)) {
            inv = alchemistInv;
        } else if (gui.equals(RPGui.ENCHANTER)) {
            inv = enchanterInv;
        } else if (gui.equals(RPGui.FILTER)) {
            inv = filterMenuInv;
        } else if (gui.equals(RPGui.GIVEDP)) {
            inv = givedpInv;
        } else if (gui.equals(RPGui.GKIT)) {
            inv = gkitInv;
        } else if (gui.equals(RPGui.SHOP)) {
            inv = shopInv;
        } else if (gui.equals(RPGui.TINKERER)) {
            inv = tinkererInv;
        } else if (gui.equals(RPGui.VKIT)) {
            inv = vkitInv;
        } else return;

        if (inv == null) {
            Bukkit.broadcastMessage("NULL ABC " + gui.name());
        } else {
            player.openInventory(Bukkit.createInventory(player,
                    inv.getSize(),
                    inv.getTitle()));
            player.getOpenInventory().getTopInventory().setContents(inv.getContents());
            if (gui.equals(RPGui.GKIT)) {

            }
        }
    }

    private int getSoulTrackerMultiplyInConfig(String string) {
        if (string.equals(apply_soultracker.get(0))) {
            string = "legendary";
        } else if (string.equals(apply_soultracker.get(1))) {
            string = "ultimate";
        } else if (string.equals(apply_soultracker.get(2))) {
            string = "elite";
        } else if (string.equals(apply_soultracker.get(3))) {
            string = "unique";
        } else if (string.equals(apply_soultracker.get(4))) {
            string = "simple";
        } else {
            return 1;
        }
        return masterconfig.getInt("givedp-items." + string + ".soul-tracker.splitsouls-multply");
    }

    private String getSoulTrackerRarity(String string) {
        if (string.equals(apply_soultracker.get(0))) {
            return "legendary";
        } else if (string.equals(apply_soultracker.get(1))) {
            return "ultimate";
        } else if (string.equals(apply_soultracker.get(2))) {
            return "elite";
        } else if (string.equals(apply_soultracker.get(3))) {
            return "unique";
        } else if (string.equals(apply_soultracker.get(4))) {
            return "simple";
        } else {
            return null;
        }
    }

    private String getSoulTrackerItemStackRarity(ItemMeta itemmeta) {
        if (itemmeta.equals(legendarySoulTracker.getItemMeta())) {
            return "legendary";
        } else if (itemmeta.equals(ultimateSoulTracker.getItemMeta())) {
            return "ultimate";
        } else if (itemmeta.equals(eliteSoulTracker.getItemMeta())) {
            return "elite";
        } else if (itemmeta.equals(uniqueSoulTracker.getItemMeta())) {
            return "unique";
        } else if (itemmeta.equals(simpleSoulTracker.getItemMeta())) {
            return "simple";
        } else {
            return null;
        }
    }

    /*
     *
	 * Enchantments
	 *
	 */
    @EventHandler
    private void removePotionEffect(InventoryClickEvent event) {
        if (event.isCancelled() || event.getClick().equals(ClickType.DOUBLE_CLICK)) return;

        else if (!(event.getCurrentItem() == null) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getClickedInventory().getType().equals(InventoryType.PLAYER) && event.getWhoClicked().getOpenInventory().getTopInventory().getType().equals(InventoryType.CRAFTING) && event.isShiftClick() && !(event.getSlotType().equals(SlotType.ARMOR))) {
            if (event.getCurrentItem().getType().name().endsWith("HELMET") && event.getWhoClicked().getInventory().getHelmet() == null || event.getCurrentItem().getType().name().endsWith("CHESTPLATE") && event.getWhoClicked().getInventory().getChestplate() == null
                    || event.getCurrentItem().getType().name().endsWith("LEGGINGS") && event.getWhoClicked().getInventory().getLeggings() == null || event.getCurrentItem().getType().name().endsWith("BOOTS") && event.getWhoClicked().getInventory().getBoots() == null) {
                for (String enchant : api.getEnchantmentsOnItem(event.getCurrentItem())) {
                    String OEN = enchant.split(" ")[0];
                    if (OEN.equals("AntiGravity") || OEN.equals("Aquatic") || OEN.equals("Drunk") || OEN.equals("Gears") || OEN.equals("Glowing") || OEN.equals("Obsidianshield") || OEN.equals("Overload") || OEN.equals("Springs")) {
                        potionEffects((Player) event.getWhoClicked(), enchant, enchant.split(" ")[0], false);
                    } else if (OEN.equals("EnderWalker")) {
                        getAndPlaySound((Player) event.getWhoClicked(), event.getWhoClicked().getLocation(), "enchants.EnderWalker", false);
                    } else if (OEN.equals("Commander") || OEN.equals("Protection")) {
                        commanderprotection.put((Player) event.getWhoClicked(), event.getCurrentItem());
                    }
                }
            }
        } else if (event.getSlotType().equals(SlotType.ARMOR)) {
            if (!(event.getCurrentItem() == null) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore()) {
                if (event.getRawSlot() == 5 && !(event.getCurrentItem().getType().name().endsWith("HELMET")) || event.getRawSlot() == 6 && !(event.getCurrentItem().getType().name().endsWith("CHESTPLATE")) || event.getRawSlot() == 7 && !(event.getCurrentItem().getType().name().endsWith("LEGGINGS")) || event.getRawSlot() == 8 && !(event.getCurrentItem().getType().name().endsWith("BOOTS"))) {
                    return;
                }
                for (String enchant : api.getEnchantmentsOnItem(event.getCurrentItem())) {
                    potionEffects((Player) event.getWhoClicked(), enchant, enchant.split(" ")[0], true);
                }
            }
            if (!(event.getCursor() == null) && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasLore()) {
                if (event.getRawSlot() == 5 && !(event.getCursor().getType().name().endsWith("HELMET")) || event.getRawSlot() == 6 && !(event.getCursor().getType().name().endsWith("CHESTPLATE")) || event.getRawSlot() == 7 && !(event.getCursor().getType().name().endsWith("LEGGINGS")) || event.getRawSlot() == 8 && !(event.getCursor().getType().name().endsWith("BOOTS"))) {
                    return;
                }
                for (String enchant : api.getEnchantmentsOnItem(event.getCursor())) {
                    String OEN = enchant.split(" ")[0];
                    if (OEN.equals("AntiGravity") || OEN.equals("Aquatic") || OEN.equals("Drunk") || OEN.equals("Gears") || OEN.equals("Glowing") || OEN.equals("Obsidianshield") || OEN.equals("Overload") || OEN.equals("Springs")) {
                        potionEffects((Player) event.getWhoClicked(), enchant, OEN, false);
                    } else if (OEN.equals("EnderWalker")) {
                        getAndPlaySound((Player) event.getWhoClicked(), event.getWhoClicked().getLocation(), "enchants.EnderWalker", false);
                    } else if (OEN.equals("Commander") || OEN.equals("Protection")) {
                        commanderprotection.put((Player) event.getWhoClicked(), event.getCurrentItem());
                    }
                }
            }
        }
    }

    private void potionEffects(Player player, String enchant, String OEN, boolean removePotionEffects) {
        for (int i = 1; i <= 10; i++) {
            if (!(enchantments.get(OEN + ".potion-effects." + i) == null)) {
                String[] string = enchantments.getString(OEN + ".potion-effects." + i).split("\\:");
                int level = api.getEnchantmentLevel(enchant);
                if (removePotionEffects) {
                    player.removePotionEffect(getPotionEffectType(string[0].toUpperCase()));
                } else {
                    player.addPotionEffect(new PotionEffect(getPotionEffectType(string[0].toUpperCase()), evaluateProckChance(string[2], level, player), evaluateProckChance(string[1], level, player)));
                }
            }
        }
    }

    private void DivineImmolation(EntityDamageByEntityEvent event, int level) {
        event.getEntity().setFireTicks(level * 20 + 40);
        if (event.getEntity() instanceof Player) {
            ((Damageable) event.getEntity()).damage(level * 1.5);
        } else {
            ((Damageable) event.getEntity()).damage(level * 3.5);
        }
        for (Entity entity : event.getEntity().getNearbyEntities(level * 1.5, level * 1.5, level * 1.5)) {
            if (!(entity instanceof Player) || FactionsAPI.getInstance().relationIsEnemyOrNull((Player) event.getDamager(), (Player) entity)) {
                entity.setFireTicks(level * 20 + 40);
                if (entity instanceof Player) {
                    ((Damageable) entity).damage(level * 1.5);
                } else if (entity instanceof Damageable) {
                    ((Damageable) entity).damage(level * 3.5);
                }
            }
        }
    }

    private void Immortal(Player victim, ItemStack is) {
        is.setDurability((short) (is.getDurability() - 1));
    }

    private void NaturesWrath(EntityDamageByEntityEvent event, Player victim, int level) {
        for (Entity entity : victim.getNearbyEntities(45, 45, 45)) {
            if (entity instanceof Player && FactionsAPI.getInstance().relationIsEnemyOrNull(victim, (Player) entity)
                    || !(entity instanceof Player) && entity instanceof Damageable) {
                for (int i = 1; i <= 5; i++) {
                    victim.getWorld().strikeLightning(entity.getLocation());
                }
                natureswrath.add(entity);
                Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                    public void run() {
                        natureswrath.remove(entity);
                    }
                }, 20 * 10);
            }
        }
    }

    @EventHandler
    private void playerMoveEvent(PlayerMoveEvent event) {
        if (natureswrath.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    private void Phoenix(EntityDamageByEntityEvent event, Player victim, int level) {
        if (!(phoenix.contains(victim))) {
            phoenix.add(victim);
            event.setCancelled(true);
            victim.setHealth(victim.getMaxHealth());
            getAndPlaySound(victim, victim.getLocation(), "enchants.Phoenix", true);
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                public void run() {
                    phoenix.remove(victim);
                }
            }, 20 * 60 * 10 - (20 * 60 * level));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void projectileLaunchEvent(ProjectileLaunchEvent event) {
        if (event.getEntityType().equals(EntityType.ENDER_PEARL) && event.getEntity().getShooter() instanceof Player && Teleblock.contains(event.getEntity().getShooter())) {
            event.setCancelled(true);
            giveItem((Player) event.getEntity().getShooter(), new ItemStack(Material.ARROW, 1));
            ((Player) event.getEntity().getShooter()).updateInventory();
        }
    }

    private void Teleblock(Player victim, int level) {
        if (!(Teleblock.contains(victim))) {
            Teleblock.add(victim);
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                public void run() {
                    Teleblock.remove(victim);
                }
            }, 20 * level + 100);
        }
        if (!(victim.getInventory().first(Material.ENDER_PEARL) == -1)) {
            removeItem(victim, victim.getInventory().getItem(victim.getInventory().first(Material.ENDER_PEARL)), random.nextInt(12) + level);
        }
    }

    //
    private void depleteSouls(Player player, ItemStack is, int currentSouls, int depleteBy) {
        if (currentSouls - depleteBy <= 0) {
            sendStringListMessage(player, "soul-mode.out-of-souls", 0);
            depleteBy = currentSouls;
            soulmode.remove(player);
        }
        itemMeta = is.getItemMeta();
        itemMeta.setDisplayName(itemMeta.getDisplayName().replace("" + currentSouls, "" + (currentSouls - depleteBy)));
        is.setItemMeta(itemMeta);
    }

    private ItemStack getSoulGemInPlayerInventory(Player player, boolean checkWholeInv) {
        int size = 0;
        if (checkWholeInv) {
            size = player.getInventory().getSize();
        } else {
            size = 9;
        }
        for (int i = 0; i < size; i++) {
            item = player.getInventory().getItem(i);
            if (!(item == null) && !(item.getType().equals(Material.AIR))
                    && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore() && item.getItemMeta().getLore().equals(soulgemlore)) {
                return item;
            }
        }
        return null;
    }

    @EventHandler
    private void entityShootBowEvent(EntityShootBowEvent event) {
        if (event.isCancelled()) return;
        else if (event.getEntity() instanceof Player && event.getBow().hasItemMeta() && event.getBow().getItemMeta().hasLore()) {
            bows.add(event.getBow());
            bowsp.add((Player) event.getEntity());
        }
    }

    @EventHandler
    private void projectileHitEvent(ProjectileHitEvent event) {
        if (event.getEntity().getType().name().contains("ARROW") && event.getEntity().getShooter() instanceof Player && bowsp.contains(event.getEntity().getShooter())) {
            for (int i = 0; i < bowsp.size(); i++) {
                if (bowsp.get(i).equals(event.getEntity().getShooter())) {
                    for (String enchant : api.getEnchantmentsOnItem(bows.get(i))) {
                        if (random.nextInt(100) <= evaluateProckChance(enchant.split(" ")[0], Integer.parseInt(enchant.split(" ")[1]), (Player) event.getEntity().getShooter())) {
                            if (enchant.startsWith("Explosive")) {
                                Explosive(event.getEntity().getLocation(), event.getEntity());
                            } else if (enchant.startsWith("Lightning")) {
                                Lightning(event.getEntity().getLocation(), event.getEntity());
                            }
                        }
                    }
                    bowsp.remove(i);
                    bows.remove(i);
                    return;
                }
            }
        }
    }

    @EventHandler
    private void playerQuitEvent(PlayerQuitEvent event) {
        if (bowsp.contains(event.getPlayer())) {
            for (int i = 0; i < bowsp.size(); i++) {
                if (bowsp.get(i).equals(event.getPlayer())) {
                    bowsp.remove(i);
                    bows.remove(i);
                }
            }
        }
        //
        if (claimloot.contains(event.getPlayer())) {
            claimloot.remove(event.getPlayer());
        }
        if (revealloot.contains(event.getPlayer())) {
            revealloot.remove(event.getPlayer());
        }
        for (int i = 0; i < 54; i++) {
            if (selectloot.contains(event.getPlayer().getName() + i)) {
                selectloot.remove(event.getPlayer().getName() + i);
            }
        }
    }

    private void Angelic(Player victim, int level) {
        givePotionEffects(null, victim, "Angelic", level);
    }

    private void ArrowBreak(EntityDamageByEntityEvent event, Player victim) {
        event.setCancelled(true);
        event.getDamager().remove();
        sendStringListMessage(victim, "enchants.arrowbreak", 0);
        getAndPlaySound(victim, victim.getLocation(), "enchants.ArrowBreak", true);
    }

    private void ArrowDeflect(EntityDamageByEntityEvent event, Player victim, int level) {
        if (arrowdeflect.contains(victim.getName() + level)) {
            event.setCancelled(true);
        } else {
            arrowdeflect.add(victim.getName() + level);
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                public void run() {
                    arrowdeflect.remove(victim.getName() + level);
                }
            }, 40 - (int) (0.4 * level));
        }
    }

    private void ArrowLifesteal(Player victim, Player damager) {
        if (victim.getHealth() - 1 <= 0.00) {
            victim.setHealth(0);
        } else {
            victim.setHealth(victim.getHealth() - 1);
        }
        damager.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 2));
        getAndPlaySound(damager, damager.getLocation(), "enchants.ArrowLifesteal", false);
    }

    private void Armored(Player victim, Player damager, int level, EntityDamageByEntityEvent event) {
        if (getItemInHand(damager) == null || !(getItemInHand(damager).getType().name().endsWith("_SWORD"))) {
            return;
        }
        double dividedDamage = 0.0;
        for (ItemStack item : victim.getInventory().getArmorContents()) {
            for (String enchant : api.getEnchantmentsOnItem(item)) {
                if (enchant.split(" ")[0].equals("Armored")) {
                    dividedDamage = dividedDamage + 1.85 * level;
                }
            }
        }
        event.setDamage(event.getDamage() / dividedDamage);
    }

    private void AutoSmelt(BlockBreakEvent event, Player player, Material material, int level) {
        if (material.equals(Material.GOLD_ORE) || material.equals(Material.IRON_ORE)) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            for (Enchantment enchant : getItemInHand(player).getEnchantments().keySet()) {
                if (enchant.equals(Enchantment.LOOT_BONUS_BLOCKS)) {
                    int fortune = random.nextInt(getItemInHand(player).getEnchantmentLevel(enchant) + 1);
                    if (fortune == 0) {
                        fortune = 1;
                    }
                    level = level * fortune;
                }
            }
            giveItem(player, new ItemStack(Material.getMaterial(material.name().replace("ORE", "INGOT")), level, (byte) 0));
            getAndPlaySound(player, event.getBlock().getLocation(), "enchants.AutoSmelt", true);
        }
    }

    private void Barbarian(EntityDamageByEntityEvent event, int level, Player player) {
        if (event.getEntity() instanceof Player && !(getItemInHand((Player) event.getEntity()) == null) && getItemInHand((Player) event.getEntity()).getType().name().endsWith("_AXE")) {
            event.setDamage(event.getDamage() * (1.10 + (level * 0.05)));
            getAndPlaySound(player, player.getLocation(), "enchants.Barbarian", true);
        }
    }

    private void Berserk(Player player, int level) {
        givePotionEffects(null, player, "Berserk", level);
    }

    private void Blacksmith(EntityDamageByEntityEvent event, Player damager, int level) {
        event.setDamage(event.getDamage() * 0.5);
        int amount = random.nextInt(2) + 1;
        ItemStack piece = new ItemStack(Material.LEATHER_BOOTS, 1);
        for (ItemStack is : damager.getInventory().getArmorContents()) {
            if (!(is == null) && is.getDurability() > piece.getDurability()) {
                piece = is;
            }
        }
        if (piece.getDurability() - amount < 0) {
            piece.setDurability((short) 0);
        } else {
            piece.setDurability((short) (piece.getDurability() - amount));
        }
        sendStringListMessage(damager, "enchants.blacksmith", 0);
        getAndPlaySound(damager, damager.getLocation(), "enchants.Blacksmith", true);
        damager.updateInventory();
    }

    private void Blessed(EntityDamageByEntityEvent event, Player player) {
        for (PotionEffect pe : player.getActivePotionEffects()) {
            PotionEffectType type = pe.getType();
            if (type.equals(PotionEffectType.BLINDNESS) || type.equals(PotionEffectType.CONFUSION) || type.equals(PotionEffectType.HARM) || type.equals(PotionEffectType.HUNGER) || type.equals(PotionEffectType.POISON) || type.equals(PotionEffectType.SLOW) || type.equals(PotionEffectType.SLOW_DIGGING) || type.equals(PotionEffectType.WEAKNESS) || type.equals(PotionEffectType.WITHER)
                    || !(Bukkit.getVersion().contains("1.8")) && type.equals(PotionEffectType.getByName("UNLUCK"))
                    || !(Bukkit.getVersion().contains("1.8")) && type.equals(PotionEffectType.getByName("LEVITATION"))
                    || !(Bukkit.getVersion().contains("1.8")) && type.equals(PotionEffectType.getByName("GLOWING"))) {
                player.removePotionEffect(type);
            }
        }
        sendStringListMessage(player, "enchants.blessed", 0);
    }

    private void Blind(EntityDamageByEntityEvent event, int level) {
        ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * level + 40, level - 1));
    }

    private void BloodLink(Player player) {
        int randomhealth = random.nextInt(2) + 1;
        if (player.getHealth() + randomhealth <= player.getMaxHealth()) {
            player.setHealth(player.getHealth() + randomhealth);
        }
        sendStringListMessage(player, "enchants.bloodlink", randomhealth);
    }

    private void Cactus(LivingEntity damager, int level) {
        damager.damage(level);
    }

    private void Cleave(Player damager, LivingEntity victim, int level, EntityDamageByEntityEvent event) {
        getAndPlaySound(damager, damager.getLocation(), "enchants.Cleave", true);
        String area = enchantments.getString("Cleave.area").replace("level", "" + level).replace("\\s", "");
        for (Entity entity : victim.getNearbyEntities(evaluateProckChance(area.split(":")[0], level, damager), evaluateProckChance(area.split(":")[1], level, damager), evaluateProckChance(area.split(":")[2], level, damager))) {
            if (!(entity instanceof Player) && entity instanceof Damageable || entity instanceof Player && fapi.relationIsEnemyOrNull(damager, (Player) entity) == true) {
                ((Damageable) entity).damage(event.getDamage() * 0.25);
                if (getItemInHand(damager).getItemMeta().hasEnchant(Enchantment.FIRE_ASPECT) && random.nextInt(100) <= enchantments.getInt("Cleave.fire-aspect")) {
                    entity.setFireTicks(10 * level);
                }
            }
        }
    }

    private void Commander(Player player, int level) {
        player.getWorld().playEffect(new Location(player.getWorld(), player.getEyeLocation().getX(), player.getEyeLocation().getY() + 0.5, player.getEyeLocation().getZ()), Effect.STEP_SOUND, Material.DIAMOND_BLOCK);
        String[] string = enchantments.getString("Commander.area").toLowerCase().replace("level", "" + level).replace(" ", "").split("\\:");
        for (Entity entity : player.getNearbyEntities(evaluateProckChance(string[0], level, player), evaluateProckChance(string[1], level, player), evaluateProckChance(string[2], level, player))) {
            if (entity instanceof Player && FactionsAPI.getInstance().relationIsAlly(player, (Player) entity) == true) {
                givePotionEffects(null, (Player) entity, "Commander", level);
            }
        }
    }

    private void Confusion(Player victim, int level) {
        givePotionEffects(null, victim, "Confusion", level);
    }

    private void Cowification(EntityShootBowEvent event) {
        if (event.getForce() == (float) 1.0 && event.getEntity() instanceof Player) {
            event.getProjectile().setCustomName("CowificationArrow");
            event.getProjectile().setCustomNameVisible(false);
            Cow cow = event.getProjectile().getWorld().spawn(event.getProjectile().getLocation().add(0.0, 1.25, 0.0), Cow.class);
            cow.setAgeLock(true);
            cow.setVelocity(event.getProjectile().getVelocity());
            cow.setCustomName("CowificationCow");
            cow.setCustomNameVisible(false);
        }
    }

    private void CreeperArmor(EntityDamageByEntityEvent event, Player victim, int level) {
        event.setDamage(event.getDamage() / level);
        event.setCancelled(true);
    }

    private void Curse(LivingEntity damager, int level) {
        givePotionEffects(damager, null, "Curse", level);
    }

    private void Deathbringer(Player victim, int level) {
        givePotionEffects(null, victim, "Deathbringer", level);
    }

    @EventHandler
    private void playerDeathEvent(PlayerDeathEvent event) {
        if (bowsp.contains(event.getEntity())) {
            for (int i = 0; i < bowsp.size(); i++) {
                if (bowsp.get(i).equals(event.getEntity())) {
                    bowsp.remove(i);
                    bows.remove(i);
                }
            }
        }
        if (soulmode.contains(event.getEntity())) {
            soulmode.remove(event.getEntity());
        }
        if (event.getEntity().getKiller() instanceof Player && !(getItemInHand(event.getEntity().getKiller()) == null) && getItemInHand(event.getEntity().getKiller()).hasItemMeta() && getItemInHand(event.getEntity().getKiller()).getItemMeta().hasLore()) {
            item = getItemInHand(event.getEntity().getKiller());
            // Soul Trackers
            for (String string : apply_soultracker) {
                if (!(string == null) && item.getItemMeta().getLore().get(item.getItemMeta().getLore().size() - 1).startsWith(string.replace("{SOULS}", ""))) {
                    itemMeta = item.getItemMeta();
                    lore.clear();
                    lore.addAll(itemMeta.getLore());
                    int harvested = getRemainingIntFromString(lore.get(lore.size() - 1)) + 1;
                    lore.set(lore.size() - 1, string.replace("{SOULS}", Integer.toString(harvested)));
                    itemMeta.setLore(lore);
                    lore.clear();
                    item.setItemMeta(itemMeta);
                    event.getEntity().getKiller().updateInventory();
                    return;
                }
            }
            for (String enchant : api.getEnchantmentsOnItem(item)) {
                String OEN = enchant.split(" ")[0];
                int level = Integer.parseInt(enchant.split(" ")[1]), chance = evaluateProckChance(enchants.get(OEN), level, event.getEntity());
                if (random.nextInt(100) <= chance) {
                    if (OEN.equals("Decapitation") || OEN.equals("Headless")) {
                        DecapitationHeadless(event.getEntity());
                    } else if (OEN.equals("Lifebloom")) {
                        Lifebloom(event.getEntity(), level);
                    }
                }
            }
        }
    }

    private void DecapitationHeadless(Player victim) {
        item = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        skullMeta.setOwner(victim.getName());
        item.setItemMeta(skullMeta);
        victim.getWorld().dropItem(victim.getLocation(), item);
    }

    private void DeepWounds(EntityDamageByEntityEvent event, Player victim, int level) {
        givePotionEffects(null, victim, "DeepWounds", level);
    }

    private void Demonforged(Player victim, int level, int chance) {
        for (ItemStack item : victim.getInventory().getArmorContents()) {
            if (!(item == null)) {
                item.setDurability((short) (item.getDurability() - Math.subtractExact(chance, 9)));
            }
        }
        getAndPlaySound(victim, victim.getLocation(), "enchants.Demonforged", false);
    }

    private void Detonate(BlockBreakEvent event, Player player, Material material) {
        for (int i = 1; i <= 7; i++) {
            Location loc = new Location(event.getPlayer().getWorld(), 0.0, 0.0, 0.0);
            if (i == 1) {
                material = event.getBlock().getType();
                loc = event.getBlock().getLocation();
            } else if (i == 2) {
                material = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY() + 1, event.getBlock().getLocation().getBlockZ()).getType();
                loc = new Location(event.getBlock().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY() + 1, event.getBlock().getLocation().getBlockZ());
            } else if (i == 3) {
                material = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY() - 1, event.getBlock().getLocation().getBlockZ()).getType();
                loc = new Location(event.getBlock().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY() - 1, event.getBlock().getLocation().getBlockZ());
            } else if (i == 4) {
                material = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX() + 1, event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ()).getType();
                loc = new Location(event.getBlock().getWorld(), event.getBlock().getLocation().getBlockX() + 1, event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ());
            } else if (i == 5) {
                material = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX() - 1, event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ()).getType();
                loc = new Location(event.getBlock().getWorld(), event.getBlock().getLocation().getBlockX() - 1, event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ());
            } else if (i == 6) {
                material = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ() + 1).getType();
                loc = new Location(event.getBlock().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ() + 1);
            } else if (i == 7) {
                material = event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ() - 1).getType();
                loc = new Location(event.getBlock().getWorld(), event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY(), event.getBlock().getLocation().getBlockZ() - 1);
            }
            if (detonate.contains(material.name())) {
                return;
            }
            loc.getWorld().getBlockAt(loc).breakNaturally();
            loc.getWorld().playEffect(event.getBlock().getLocation(), Effect.EXPLOSION_LARGE, 1);
            getAndPlaySound(player, event.getBlock().getLocation(), "enchants.Detonate", true);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + event.getPlayer().getName() + " ~ ~ ~ particle flame " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " 0.25 0.5 0.25 0 25");
        }
    }

    private void Disarmor(Player victim) {
        int randomarmor = random.nextInt(4);
        if (!(victim.getInventory().getHelmet() == null) && !(victim.getInventory().getHelmet().getType().equals(Material.AIR)) && randomarmor == 0) {
            item = victim.getInventory().getHelmet();
            victim.getInventory().setHelmet(new ItemStack(Material.AIR));
        } else if (!(victim.getInventory().getChestplate() == null) && !(victim.getInventory().getChestplate().getType().equals(Material.AIR)) && randomarmor == 1) {
            item = victim.getInventory().getChestplate();
            victim.getInventory().setChestplate(new ItemStack(Material.AIR));
        } else if (!(victim.getInventory().getLeggings() == null) && !(victim.getInventory().getLeggings().getType().equals(Material.AIR)) && randomarmor == 2) {
            item = victim.getInventory().getLeggings();
            victim.getInventory().setLeggings(new ItemStack(Material.AIR));
        } else if (!(victim.getInventory().getBoots() == null) && !(victim.getInventory().getBoots().getType().equals(Material.AIR)) && randomarmor == 3) {
            item = victim.getInventory().getBoots();
            victim.getInventory().setBoots(new ItemStack(Material.AIR));
        } else {
            return;
        }
        getAndPlaySound(victim, victim.getLocation(), "enchants.Disarmor", false);
        sendStringListMessage(victim, "enchants.disarmor", 0);
        giveItem(victim, item);
    }

    private void Dodge(Player victim, EntityDamageByEntityEvent event) {
        event.setCancelled(true);
        sendStringListMessage(victim, "enchants.dodge", 0);
        getAndPlaySound(victim, victim.getLocation(), "enchants.Dodge", true);
    }

    private void DoubleStrike(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage() * 2);
    }

    private void EnderShift(Player victim, int level) {
        givePotionEffects(null, victim, "EnderShift", level);
    }

    private void Enlighted(Player victim) {
        if (!(victim.getHealth() + 2 > victim.getMaxHealth())) {
            victim.setHealth(victim.getHealth() + 2);
        }
    }

    @EventHandler
    private void EnderWalker(EntityDamageEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Player) || !(event.getCause().equals(DamageCause.POISON)) && !(event.getCause().equals(DamageCause.WITHER))) {
            return;
        } else {
            Player player = (Player) event.getEntity();
            if (!(player.getInventory().getBoots() == null)) {
                for (String enchant : api.getEnchantmentsOnItem(player.getInventory().getBoots())) {
                    String OEN = enchant.split(" ")[0];
                    int level = Integer.parseInt(enchant.split(" ")[1]), chance = evaluateProckChance(enchants.get(enchant), level, player);
                    if (OEN.equals("EnderWalker")) {
                        event.setCancelled(true);
                        if (random.nextInt(100) <= chance) {
                            if (player.getHealth() + level <= player.getMaxHealth()) {
                                player.setHealth(player.getHealth() + level);
                            }
                        }
                    }
                }
            }
        }
    }

    private void Enrage(EntityDamageByEntityEvent event, Player damager, int level) {
        double multiplier = (damager.getMaxHealth() - damager.getHealth()) / 3;
        if (multiplier == 0.0) {
            return;
        }
        if (multiplier < 1.0) {
            event.setDamage(event.getDamage() * (1.0 + multiplier));
        } else {
            event.setDamage(event.getDamage() * multiplier / 2);
        }
    }

    private void Epicness(Player victim, int level) {
        if (level == 1) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + victim.getName() + " ~ ~ ~ particle largesmoke " + victim.getLocation().getX() + " " + victim.getLocation().getY() + " " + victim.getLocation().getZ() + " 0.5 1 0.5 1 25 1");
        } else if (level == 2) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + victim.getName() + " ~ ~ ~ particle magicCrit " + victim.getLocation().getX() + " " + victim.getLocation().getY() + " " + victim.getLocation().getZ() + " 0.5 1 0.5 1 25 1");
        } else if (level == 3) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + victim.getName() + " ~ ~ ~ particle cloud " + victim.getLocation().getX() + " " + victim.getLocation().getY() + " " + victim.getLocation().getZ() + " 0.5 1 0.5 1 25 1");
        }
    }

    private void Execute(Player player, int level) {
        givePotionEffects(player, null, "Execute", level);
    }

    private void Experience(BlockBreakEvent event, Player player, int level) {
        int mulitplier = random.nextInt(level + 1);
        if (mulitplier == 0) {
            mulitplier = 1;
        }
        event.setExpToDrop(event.getExpToDrop() * mulitplier);
    }

    private void Explosive(Location location, Projectile proj) {
        location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 2, false, false);
        proj.remove();
    }

    private void Farcast(LivingEntity damager, int level) {
        double knockback = level * 0.25;
        damager.setVelocity(new Vector(-damager.getEyeLocation().getDirection().getX() + knockback, 0.0, -damager.getEyeLocation().getDirection().getZ() + knockback));
    }

    private void Featherweight(Player player, int level) {
        givePotionEffects(player, null, "Featherweight", level);
    }

    private void Frozen(Player victim, int level) {
        givePotionEffects(null, victim, "Frozen", level);
    }

    private void Greatsword(EntityDamageByEntityEvent event, Player victim, int level) {
        if (!(getItemInHand(victim) == null) && !(getItemInHand(victim).getType().equals(Material.AIR)) && getItemInHand(victim).getType().equals(Material.BOW)) {
            if (victim.getHealth() - (event.getDamage() * level) > 0) {
                victim.damage(event.getDamage() * level);
            } else {
                victim.setHealth(0.0);
            }
        }
        return;
    }

    private void Guardians(EntityDamageByEntityEvent event, Player victim, int level) {
        IronGolem ig = (IronGolem) victim.getWorld().spawnEntity(victim.getLocation(), EntityType.IRON_GOLEM);
        ig.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 4, false));
        ig.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 4, false));
        ig.setCustomName(guardiansname.replace("{PLAYER}", victim.getName()));
        if (event.getDamager() instanceof LivingEntity) {
            ig.setTarget((LivingEntity) event.getDamager());
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
            public void run() {
                if (!(ig.isDead() == true)) {
                    ig.remove();
                }
            }
        }, 20 * 20);
    }

    @EventHandler
    private void entityTargetEvent(EntityTargetEvent event) {
        if (!(event.getEntity().getCustomName() == null) && event.getTarget() instanceof Player
                || !(event.getEntity().getCustomName() == null) && event.getTarget() instanceof LivingEntity && !(event.getTarget().getCustomName() == null)) {
            if (event.getEntityType().equals(EntityType.IRON_GOLEM) || event.getEntityType().equals(EntityType.BLAZE) || event.getEntityType().equals(EntityType.ZOMBIE)) {
                if (event.getEntity().getCustomName().equals(guardiansname.replace("{PLAYER}", event.getTarget().getName()))
                        || event.getEntity().getCustomName().equals(undeadrusename.replace("{PLAYER}", event.getTarget().getName()))
                        || event.getEntity().getCustomName().equals(spiritsname.replace("{PLAYER}", event.getTarget().getName()))
                        || event.getEntity().getCustomName().substring(0, 3).startsWith(event.getTarget().getCustomName().substring(0, 3))) {
                    event.setCancelled(true);
                }
            }
        }
    }

    // Hardened & Reforged
    @EventHandler
    private void HardenedReforged(PlayerItemDamageEvent event) {
        if (!(event.isCancelled()) && !(event.getItem() == null) && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLore()) {
            for (String enchant : api.getEnchantmentsOnItem(event.getItem())) {
                String OEN = enchant.split(" ")[0];
                if (OEN.equals("Hardened") || OEN.equals("Reforged")) {
                    int chance = evaluateProckChance(enchants.get(OEN), Integer.parseInt(enchant.split(" ")[1]), event.getPlayer());
                    if (random.nextInt(100) <= chance) {
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            return;
        }
    }

    private void Haste(PlayerInteractEvent event, int level) {
        givePotionEffects(event.getPlayer(), null, "Haste", level);
    }

    private void Heavy(EntityDamageByEntityEvent event, Player victim) {
        double percent = 0.02;
        for (ItemStack item : victim.getInventory().getArmorContents()) {
            for (String enchant : api.getEnchantmentsOnItem(item)) {
                if (enchant.split(" ")[0].equals("Heavy")) {
                    percent = percent + (0.02 * Integer.parseInt(enchant.split(" ")[1]));
                }
            }
        }
        event.setDamage(event.getDamage() * (1.00 - percent));
        getAndPlaySound(victim, victim.getLocation(), "enchants.Heavy", true);
    }

    private void Hijack(IronGolem g, Player player, Player gOwner) {
        if (player.getName().equalsIgnoreCase(gOwner.getName())) {
            return;
        }
        IronGolem ig = g.getWorld().spawn(g.getLocation(), IronGolem.class);
        g.remove();
        ig.setTarget(gOwner);
        ig.setCustomName(guardiansname.replace("{PLAYER}", player.getName()));
        ig.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, 1, false));
        ig.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 1, false));
    }

    private void Hellfire(Projectile proj, Player player, int level) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + player.getName() + " " + proj.getLocation().getX() + " " + proj.getLocation().getY() + " " + proj.getLocation().getZ() + " particle flame " + proj.getLocation().getX() + " " + (proj.getLocation().getY() + 1) + " " + proj.getLocation().getZ() + " 2.5 1 2.5 0 400 0");
        proj.remove();
        for (Entity entity : player.getNearbyEntities(5, 4, 5)) {
            if (!(entity instanceof Player) || entity instanceof Player && FactionsAPI.getInstance().relationIsEnemyOrNull(player, (Player) entity)) {
                entity.setFireTicks(level * 40);
            }
        }
    }

    private void IceAspect(Player victim, int level) {
        givePotionEffects(null, victim, "IceAspect", level);
        victim.getWorld().playEffect(victim.getEyeLocation(), Effect.STEP_SOUND, Material.ICE);
    }

    private void Implants(Player player) {
        if (!(player.getFoodLevel() == 20)) {
            player.setFoodLevel(player.getFoodLevel() + 1);
        }
        if (random.nextInt(100) <= 2 && !(player.getHealth() + 1 > player.getMaxHealth())) {
            player.setHealth(player.getHealth() + 1);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void Inquisitive(EntityDeathEvent event) {
        if (natureswrath.contains(event.getEntity())) {
            natureswrath.remove(event.getEntity());
        }
        if (event.getEntity() != null && !(event.getEntity() instanceof Player)) {
            if (event.getEntity().getKiller() != null && getItemInHand(event.getEntity().getKiller()) != null) {
                item = getItemInHand(event.getEntity().getKiller());
                if (!(event.getEntity().getCustomName() == null)) {
                    if (event.getEntityType().equals(EntityType.IRON_GOLEM) && ChatColor.stripColor(event.getEntity().getCustomName()).endsWith(ChatColor.stripColor(guardiansname.replace("{PLAYER}", "")))
                            || event.getEntityType().equals(EntityType.ZOMBIE) && ChatColor.stripColor(event.getEntity().getCustomName()).endsWith(ChatColor.stripColor(undeadrusename.replace("{PLAYER}", "")))
                            || event.getEntityType().equals(EntityType.BLAZE) && ChatColor.stripColor(event.getEntity().getCustomName()).endsWith(ChatColor.stripColor(spiritsname.replace("{PLAYER}", "")))) {
                        event.setDroppedExp(0);
                        event.getDrops().clear();
                        return;
                    }
                }
                if (event.getEntity().getKiller() != null && !(item == null) && mobRage.contains(event.getEntity().getKiller().getName())) {
                    event.getEntity().setVelocity(new Vector(event.getEntity().getKiller().getLocation().getDirection().getX() * .45, 0.25, event.getEntity().getKiller().getLocation().getDirection().getZ() * .45));
                    if (event.getEntity().getNearbyEntities(0.0, 0.0, 0.0).size() >= 1) {
                        for (Entity entity : event.getEntity().getNearbyEntities(0.0, 0.0, 0.0)) {
                            if (entity.getType().equals(event.getEntity().getType())) {
                                entity.setVelocity(new Vector(-(event.getEntity().getKiller().getLocation().getDirection().getX()) * .25, 0.0, -(event.getEntity().getKiller().getLocation().getDirection().getZ()) * .25));
                            }
                        }
                    }
                }
                //
                if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                    for (String enchant : api.getEnchantmentsOnItem(item)) {
                        String OEN = enchant.split(" ")[0];
                        if (OEN.equals("Inquisitive")) {
                            int level = Integer.parseInt(enchant.split(" ")[1]), chance = evaluateProckChance(enchants.get(OEN), level, event.getEntity().getKiller());
                            if (random.nextInt(100) <= chance) {
                                String equ = null;
                                if (level == 1) {
                                    equ = enchantments.getString("Inquisitive.level-one");
                                } else {
                                    equ = enchantments.getString("Inquisitive.levels");
                                }
                                equ = equ.toLowerCase().replace(" ", "").replace("xp", "" + event.getDroppedExp()).replace("level", "" + level);
                                event.setDroppedExp(evaluateProckChance(equ, level, event.getEntity().getKiller()));
                            }
                        }
                    }
                }
            }
            if (event.getEntity().getKiller() instanceof Player && !(event.getEntity().getCustomName() == null)) {
                for (int i = 0; i < fallenheroes.length; i++) {
                    if (!(fallenheroes[i] == null) && event.getEntity().getCustomName().equals(fallenheroes[i].getItemMeta().getDisplayName())) {
                        event.setDroppedExp(0);
                        event.getDrops().clear();
                        if (random.nextInt(100) <= gkitgemchance) {
                            giveItem(event.getEntity().getKiller(), gkitgems[i]);
                            for (String string : gkitgemmessage) {
                                if (string.contains("{PLAYER}")) {
                                    string = string.replace("{PLAYER}", event.getEntity().getKiller().getName());
                                }
                                if (string.contains("{FALLEN_HERO_NAME}")) {
                                    string = string.replace("{FALLEN_HERO_NAME}", event.getEntity().getCustomName().replace(fallenheroprefix.replace("{NAME}", ""), ""));
                                }
                                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', string));
                            }
                        } else {
                            for (ItemStack is : event.getEntity().getEquipment().getArmorContents()) {
                                if (random.nextInt(100) < 50) {
                                    is.setDurability((short) random.nextInt(is.getType().getMaxDurability()));
                                    event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), is);
                                }
                            }
                            if (random.nextInt(100) < 50) {
                                item = event.getEntity().getEquipment().getItemInHand();
                                item.setDurability((short) random.nextInt(item.getType().getMaxDurability()));
                                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), item);
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    private void Insanity(Player damager, int level, Player victim, EntityDamageByEntityEvent event) {
        if (!(getItemInHand(victim) == null) && getItemInHand(victim).getType().name().endsWith("_SWORD")) {
            String damage = enchantments.getString("Insanity.damage").replace("damage", "" + event.getDamage()).replace("level", "" + level);
            event.setDamage(evaluateProckChance(damage, level, damager));
            getAndPlaySound(damager, damager.getLocation(), "enchants.Insanity", false);
        }
    }

    private void Insomnia(Player player, int level, EntityDamageByEntityEvent event) {
        givePotionEffects(player, null, "Insomnia", level);
        if (random.nextInt(101) <= 25) {
            event.setDamage(event.getDamage() * 2);
        }
    }

    private void Inversion(EntityDamageByEntityEvent event, Player victim, int level) {
        event.setDamage(0);
        int amount = random.nextInt(3) + 1;
        if (!(victim.getHealth() + amount > victim.getMaxHealth())) {
            victim.setHealth(victim.getHealth() + amount);
        }
        for (String string : messages.getStringList("enchants.inversion")) {
            victim.sendMessage(ChatColor.translateAlternateColorCodes('&', string.replace("{AMOUNT}", "" + amount)));
        }
    }

    private void Lifebloom(Player victim, int level) {
        victim.playEffect(victim.getLocation(), Effect.STEP_SOUND, Material.EMERALD_BLOCK);
        getAndPlaySound(victim, victim.getLocation(), "enchants.Lifebloom", true);
        int area = evaluateProckChance(enchantments.getString("Lifebloom.area"), level, victim);
        for (Entity entity : victim.getNearbyEntities(area, area, area)) {
            if (entity instanceof Player && FactionsAPI.getInstance().relationIsAlly(victim, (Player) entity)
                    || entity instanceof Player && FactionsAPI.getInstance().relationIsMember(victim, (Player) entity)) {
                ((Player) entity).setHealth(((Player) entity).getMaxHealth());
            }
        }
    }

    private void Lifesteal(EntityDamageByEntityEvent event, Player player) {
        if (!(player.getHealth() + 1 > 20.0)) {
            player.setHealth(player.getHealth() + 1);
        }
        if (!(((LivingEntity) event.getEntity()).getHealth() - 1 <= 0)) {
            ((LivingEntity) event.getEntity()).setHealth(((LivingEntity) event.getEntity()).getHealth() - 1);
        }
        getAndPlaySound(player, player.getLocation(), "enchants.Lifesteal", true);
    }

    private void Lightning(Location location, Projectile proj) {
        proj.getWorld().strikeLightning(location);
        proj.remove();
    }

    private void Marksman(EntityDamageByEntityEvent event, double damage) {
        event.setDamage(event.getDamage() + damage);
        Bukkit.broadcastMessage("marksman=" + event.getDamage());
    }

    private void Molten(LivingEntity damager, int level) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, () -> damager.setFireTicks(40 * level + 60), 1);
    }

    private void Obliterate(EntityDamageByEntityEvent event, int level) {
        double multiplier = level + 0.25;
        event.getEntity().setVelocity(new Vector(-event.getEntity().getLocation().getDirection().getX() * multiplier, event.getEntity().getVelocity().getY(), -event.getEntity().getLocation().getDirection().getZ() * multiplier));
    }

    private void ObsidianDestroyer(PlayerInteractEvent event, int level) {
        if (event.getClickedBlock().getType().equals(Material.OBSIDIAN)) {
            event.getClickedBlock().breakNaturally();
            event.getClickedBlock().getWorld().playEffect(event.getClickedBlock().getLocation(), Effect.STEP_SOUND, Material.OBSIDIAN);
        }
    }

    private void Oxygenate(Player player) {
        if (player.getRemainingAir() + 40 <= 300) {
            player.setRemainingAir(player.getRemainingAir() + 40);
        }
    }

    private void Paralyze(Player victim, int level) {
        victim.getWorld().strikeLightning(victim.getLocation());
        if (random.nextInt(100) <= level * 15) {
            givePotionEffects(null, victim, "Paralyze", level);
        }
    }

    private void Piercing(EntityDamageByEntityEvent event, int level) {
        event.setDamage(event.getFinalDamage() * (1 + (level * .25)));
    }

    private void Poison(LivingEntity victim, int level) {
        victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40 * level + 40, level - 1));
    }

    private void Poisoned(LivingEntity damager, int level) {
        damager.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40 * level + 40, level - 1));
    }

    private void Protection(Player player, int level) {
        String[] string = enchantments.getString("Protection.area").toLowerCase().replace(" ", "").replace("level", "" + level).split("\\:");
        for (Entity entity : player.getNearbyEntities(evaluateProckChance(string[0], level, player), evaluateProckChance(string[1], level, player), evaluateProckChance(string[2], level, player))) {
            if (entity instanceof Player && FactionsAPI.getInstance().relationIsAlly(player, (Player) entity) == true) {
                ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 40 * level, level / 2));
                ((Player) entity).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 10 * level, 1));
            }
        }
    }

    private void Pummel(Player damager, Player victim, int level) {
        givePotionEffects(null, victim, "Pummel", level);
        String[] string = enchantments.getString("Pummel.area").replace(" ", "").toLowerCase().split("\\:");
        for (Entity entity : victim.getNearbyEntities(evaluateProckChance(string[0], level, victim), evaluateProckChance(string[1], level, victim), evaluateProckChance(string[2], level, victim))) {
            if (entity instanceof Player && FactionsAPI.getInstance().relationIsEnemyOrNull(damager, victim) == true) {
                givePotionEffects(null, ((Player) entity), "Pummel", level);
            }
        }
        return;
    }

    private void Ragdoll(Player victim) {
        victim.setVelocity(victim.getLocation().getDirection().multiply(-random.nextDouble() - 0.5));
    }

    private void Rage(EntityDamageByEntityEvent event, Player damager, int level) {
        LivingEntity entity = (LivingEntity) event.getEntity();
        double multiplier = 1.000;
        if (entity instanceof Player && enchantments.getBoolean("Rage.player-particles")
                || !(entity instanceof Player) && enchantments.getBoolean("Rage.mob-particles")) {
            entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
            entity.getWorld().playEffect(entity.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        }
        ArrayList<String> type;
        if (entity instanceof Player) {
            type = playerRage;
        } else {
            type = mobRage;
        }
        type.add(damager.getName());
        for (String string : type) {
            if (string.equals(damager.getName())) {
                if (!(multiplier + 0.111 > 1.000 + (level * 0.111))) {
                    multiplier = multiplier + 0.111;
                }
            }
        }
        entity.setVelocity(new Vector(-(event.getDamager().getLocation().getDirection().getX()) * 0.35, 0.0, -(event.getDamager().getLocation().getDirection().getZ()) * .35));
        event.setDamage(event.getDamage() * multiplier);
        Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, () -> {
            playerRage.remove(damager.getName());
            mobRage.remove(damager.getName());
        }, 20 * 4);
    }

    private void Ravenous(Player player, int level) {
        givePotionEffects(null, player, "Ravenous", level);
    }

    private void RocketEscape(Player victim, int level) {
        if (victim.getHealth() <= (level * 2) + 4) {
            givePotionEffects(null, victim, "RocketEscape", level);
        }
    }

    private void Shackle(EntityDamageByEntityEvent event, int level) {
        if (level <= 3 || level == 2 && !(event.getEntity().getType().equals(EntityType.MAGMA_CUBE)) || level == 1 && !(event.getEntity().getType().equals(EntityType.BLAZE)) && !(event.getEntity().getType().equals(EntityType.MAGMA_CUBE))) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                public void run() {
                    event.getEntity().setVelocity(new Vector(-(event.getDamager().getLocation().getDirection().getX() * .25), 0.0, -(event.getDamager().getLocation().getDirection().getZ() * .25)));
                }
            }, 1);
        }
    }

    private void Shockwave(Player damager, int level) {
        damager.setVelocity(new Vector(-damager.getLocation().getDirection().getX() * 3, damager.getVelocity().getY(), -damager.getLocation().getDirection().getZ() * 3));
    }

    private void Silence(Player victim, int level) {
        Collection<PotionEffect> pes = victim.getActivePotionEffects(), potioneffects = victim.getActivePotionEffects();
        for (PotionEffect pe : pes) {
            victim.removePotionEffect(pe.getType());
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
            public void run() {
                for (PotionEffect pe : potioneffects) {
                    victim.addPotionEffect(pe);
                }
            }
        }, 20 * level);
    }

    private void SkillSwipe(Player victim, Player damager, int level) {
        int taken_xp = (random.nextInt(10) + 1) * level;
        if (victim.getTotalExperience() >= taken_xp) {
            int remaining_xp = getTotalExperience(victim) - taken_xp;
            setTotalExperience(victim, remaining_xp);
            setTotalExperience(damager, getTotalExperience(damager) + taken_xp);
        }
    }

    private void SmokeBomb(LivingEntity damager, int level) {
        damager.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * level + 100, level - 1));
    }

    private void Snare(Player victim, int level) {
        givePotionEffects(null, victim, "Snare", level);
    }

    private void Sniper(EntityDamageByEntityEvent event, Player damager, Player victim, int level) {
        if (event.getDamager().getLocation().getY() > victim.getLocation().getY()) {
            double multiplier = 1.0;
            for (int i = 1; i <= level; i++) {
                multiplier = multiplier + 0.5;
            }
            event.setDamage(event.getDamage() * multiplier);
            getAndPlaySound(damager, damager.getLocation(), "enchants.Sniper", false);
            sendStringListMessage(damager, "enchants.sniper", 0);
        }
    }

    private void SpiritLink(Player victim, int level) {
        String[] string = enchantments.getString("SpiritLink.area").toLowerCase().replace(" ", "").replace("level", "" + level).split("\\:");
        for (Entity entity : victim.getNearbyEntities(evaluateProckChance(string[0], level, victim), evaluateProckChance(string[1], level, victim), evaluateProckChance(string[2], level, victim))) {
            if (entity instanceof Player && FactionsAPI.getInstance().relationIsAlly(victim, (Player) entity)
                    || entity instanceof Player && FactionsAPI.getInstance().relationIsMember(victim, (Player) entity)) {
                Player nearbyplayer = (Player) entity;
                if (!(nearbyplayer.getHealth() + 1 > nearbyplayer.getMaxHealth())) {
                    nearbyplayer.setHealth(nearbyplayer.getHealth() + 1);
                }
            }
        }
    }

    private void Spirits(Player victim, int level) {
        getAndPlaySound(victim, victim.getLocation(), "enchants.Spirits", true);
        for (Entity entity : victim.getNearbyEntities(level + (level / 2), level + (level / 2), level + (level / 2))) {
            if (entity instanceof Player && FactionsAPI.getInstance().relationIsAlly(victim, (Player) entity)) {
                givePotionEffects(null, ((Player) entity), "Spirits", level);
            }
        }
        for (int i = 1; i <= level; i++) {
            Blaze blaze = (Blaze) victim.getWorld().spawnEntity(victim.getLocation(), EntityType.BLAZE);
            blaze.setCustomName(spiritsname.replace("{PLAYER}", victim.getName()));
            blaze.setCanPickupItems(false);
            blaze.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 99999, level));
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, blaze::remove, 300);
        }
    }

    private void Stormcaller(LivingEntity damager) {
        damager.getWorld().strikeLightning(damager.getLocation());
    }

    private void Tank(Player victim, Player damager, int level, EntityDamageByEntityEvent event) {
        if (getItemInHand(damager) == null || !(getItemInHand(damager).getType().name().endsWith("_AXE"))) {
            return;
        }
        double dividedDamage = 0.0;
        for (ItemStack item : victim.getInventory().getArmorContents()) {
            for (String enchant : api.getEnchantmentsOnItem(item)) {
                if (enchant.split(" ")[0].equals("Tank")) {
                    dividedDamage = dividedDamage + 1.85 * level;
                }
            }
        }
        event.setDamage(event.getDamage() / dividedDamage);
    }

    private void ThunderingBlow(Player victim, EntityDamageByEntityEvent event, int level) {
        for (int o = 0; o <= 4; o++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                public void run() {
                    for (int i = 1; i <= 3; i++) {
                        victim.getWorld().strikeLightning(victim.getLocation());
                    }
                }
            }, o * 10);
        }
    }

    private void Trap(Player victim, int level) {
        givePotionEffects(null, victim, "Trap", level);
    }

    private void Trickster(Player victim) {
        Location location = victim.getLocation();
        if (victim.getLocation().getYaw() >= 0) {
            location.setYaw(victim.getLocation().getYaw() - 180);
        } else {
            location.setYaw(victim.getLocation().getYaw() + 180);
        }
        victim.teleport(location);
    }

    private void UndeadRuse(Player victim, int level, LivingEntity damager) {
        for (int i = 1; i <= level; i++) {
            Zombie zombie = (Zombie) victim.getWorld().spawnEntity(victim.getLocation(), EntityType.ZOMBIE);
            zombie.setCustomName(undeadrusename.replace("{PLAYER}", victim.getName()));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 3));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 1));
            zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
            zombie.setCanPickupItems(false);
            zombie.setTarget(damager);
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                public void run() {
                    if (!(zombie.isDead())) {
                        zombie.remove();
                    }
                }
            }, 20 * 20);
        }
    }

    private void Valor(Player victim, int level) {
        givePotionEffects(null, victim, "Valor", level);
    }

    private void Vampire(EntityDamageByEntityEvent event, Player damager) {
        if (damager.getHealth() + event.getDamage() / 2 > damager.getMaxHealth()) {
            damager.setHealth(damager.getMaxHealth());
        } else {
            damager.setHealth(damager.getHealth() + event.getDamage() / 2);
            return;
        }
    }

    private void Voodoo(LivingEntity damager, int level) {
        damager.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40 * level + 40, level - 1));
    }

    private void Wither(LivingEntity damager, int level) {
        damager.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 40 * level + 40, level - 1));
    }

    /*
     *
	 * InventoryClickEvents
	 *
	 */
    @SuppressWarnings("deprecation")
    @EventHandler
    private void inventoryClickEvent(InventoryClickEvent event) {
        if (event.isCancelled() || event.getCurrentItem() == null || event.getCurrentItem().getType().equals(Material.AIR)) {
            return;
        } else if (event.getInventory().getType().equals(InventoryType.CRAFTING) || event.getInventory().getType().equals(InventoryType.PLAYER)) {
            boolean success = false;
            if (event.getCursor().getItemMeta().equals(whitescroll.getItemMeta())) {
                if (event.getCurrentItem().getType().name().endsWith("AXE") || event.getCurrentItem().getType().name().endsWith("SWORD") || event.getCurrentItem().getType().name().endsWith("SPADE") || event.getCurrentItem().getType().equals(Material.BOW) || event.getCurrentItem().getType().equals(Material.FISHING_ROD)
                        || event.getCurrentItem().getType().name().endsWith("HELMET") || event.getCurrentItem().getType().name().endsWith("CHESTPLATE") || event.getCurrentItem().getType().name().endsWith("LEGGINGS") || event.getCurrentItem().getType().name().endsWith("BOOTS")) {
                    if (!(event.getCurrentItem().hasItemMeta()) || event.getCurrentItem().hasItemMeta() && !(event.getCurrentItem().getItemMeta().hasLore()) || event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && !(event.getCurrentItem().getItemMeta().getLore().contains(WHITE_SCROLL_PROTECTED))) {
                        item = event.getCurrentItem();
                        itemMeta = item.getItemMeta();
                        lore.clear();
                        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                            lore.addAll(itemMeta.getLore());
                        }
                        lore.add(WHITE_SCROLL_PROTECTED);
                        itemMeta.setLore(lore);
                        lore.clear();
                        success = true;
                    }
                }
            } else if (event.getCursor().getItemMeta().getLore().equals(api.getRPItem(RPItem.SOUL_GEM).getItemMeta().getLore()) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore() && event.getCurrentItem().getItemMeta().getLore().equals(event.getCursor().getItemMeta().getLore())) {
                item = api.getRPItem(RPItem.SOUL_GEM);
                itemMeta = item.getItemMeta();
                int gems = getRemainingIntFromString(event.getCurrentItem().getItemMeta().getDisplayName()) + getRemainingIntFromString(event.getCursor().getItemMeta().getDisplayName());
                itemMeta.setDisplayName(item.getItemMeta().getDisplayName().replace("{SOULS}", getSoulGemColors(gems) + gems));
                success = true;
            } else if (event.getCursor().getItemMeta().equals(api.getRPItem(RPItem.TRANSMOG_SCROLL).getItemMeta())) {
                if (event.getCurrentItem().getType().name().endsWith("AXE") || event.getCurrentItem().getType().name().endsWith("SWORD") || event.getCurrentItem().getType().name().endsWith("SPADE") || event.getCurrentItem().getType().equals(Material.BOW) || event.getCurrentItem().getType().equals(Material.FISHING_ROD)
                        || event.getCurrentItem().getType().name().endsWith("HELMET") || event.getCurrentItem().getType().name().endsWith("CHESTPLATE") || event.getCurrentItem().getType().name().endsWith("LEGGINGS") || event.getCurrentItem().getType().name().endsWith("BOOTS")) {
                    item = event.getCurrentItem();
                    itemMeta = item.getItemMeta();
                    String name = null;
                    if (!(item.hasItemMeta()) || !(itemMeta.hasDisplayName()) || item.hasItemMeta() && itemMeta.hasDisplayName()) {
                        if (itemMeta.hasDisplayName()) {
                            name = itemMeta.getDisplayName();
                        } else {
                            if (item.getType().name().split("_").length == 1) {
                                name = item.getType().name().substring(0, 1) + item.getType().name().split("_")[0].substring(1).toLowerCase();
                            } else if (item.getType().name().split("_").length == 2) {
                                name = item.getType().name().substring(0, 1) + item.getType().name().split("_")[0].substring(1).toLowerCase() + " " + item.getType().name().split("_")[1].substring(0, 1) + item.getType().name().split("_")[1].substring(1).toLowerCase();
                            }
                        }
                        int size = api.getEnchantmentsOnItem(item).size();
                        if (itemMeta.hasEnchants()) {
                            itemMeta.setDisplayName(ChatColor.AQUA + (name.replace(" " + TRANSMOG.replace("{LORE_COUNT}", "" + size), "")) + " " + TRANSMOG.replace("{LORE_COUNT}", "" + size));
                        } else {
                            itemMeta.setDisplayName(ChatColor.RESET + (name.replace(" " + TRANSMOG.replace("{LORE_COUNT}", "" + size), "")) + " " + TRANSMOG.replace("{LORE_COUNT}", "" + size));
                        }
                        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                            lore.clear();
                            boolean whitescroll = false;
                            for (int i = 1; i <= 6; i++) {
                                for (String string : item.getItemMeta().getLore()) {
                                    if (soulenchantments.contains(string) && i == 1
                                            || legendaryenchantments.contains(string) && i == 2
                                            || ultimateenchantments.contains(string) && i == 3
                                            || eliteenchantments.contains(string) && i == 4
                                            || uniqueenchantments.contains(string) && i == 5
                                            || simpleenchantments.contains(string) && i == 6) {
                                        lore.add(string);
                                    } else if (string.equals(WHITE_SCROLL_PROTECTED)) {
                                        whitescroll = true;
                                    }
                                }
                            }
                            String soultracker = null;
                            for (String string : item.getItemMeta().getLore()) {
                                if (string.startsWith("")) {
                                    soultracker = string;
                                }
                            }
                            for (String string : item.getItemMeta().getLore()) {
                                for (int i = 0; i <= 53; i++) {
                                    if (!(lore.contains(string))) {
                                        lore.add(string);
                                    }
                                }
                            }
                            if (whitescroll) {
                                lore.remove(WHITE_SCROLL_PROTECTED);
                                lore.add(WHITE_SCROLL_PROTECTED);
                            }
                            if (!(soultracker == null)) {
                                lore.remove(soultracker);
                                lore.add(soultracker);
                            }
                            itemMeta.setLore(lore);
                            lore.clear();
                        }
                        success = true;
                    } else {
                        return;
                    }
                }
            } else if (event.getCursor().getItemMeta().equals(godRandomizationScroll.getItemMeta()) && event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
                String OEN = getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName());
                if (!(OEN == null)) {
                    item = event.getCurrentItem();
                    itemMeta = item.getItemMeta();
                    lore.clear();
                    for (String string : itemMeta.getLore()) {
                        if (string.equals(SUCCESS.replace("{PERCENT}", "" + getRemainingIntFromString(string)))) {
                            string = SUCCESS.replace("{PERCENT}", "" + random.nextInt(101));
                        } else if (string.equals(DESTROY.replace("{PERCENT}", "" + getRemainingIntFromString(string)))) {
                            string = DESTROY.replace("{PERCENT}", "" + random.nextInt(101));
                        }
                        lore.add(ChatColor.translateAlternateColorCodes('&', string));
                    }
                    itemMeta.setLore(lore);
                    lore.clear();
                    success = true;
                }
            } else if (event.getCursor().getItemMeta().hasDisplayName() && event.getCursor().getItemMeta().hasLore() && event.getCursor().getItemMeta().getDisplayName().equals(api.getRPItem(RPItem.BLACK_SCROLL).getItemMeta().getDisplayName()) && api.getEnchantmentsOnItem(event.getCurrentItem()).size() >= 1) {
                int successPercent = -1;
                lore.clear();
                lore.addAll(api.getEnchantmentsOnItem(event.getCurrentItem()));
                int randome = random.nextInt(lore.size()), enchantcount = api.getEnchantmentsOnItem(event.getCurrentItem()).size();
                String randomenchant = lore.get(randome), rarity = getEnchantmentRarity(randomenchant);
                lore.clear();
                for (String string : event.getCursor().getItemMeta().getLore()) {
                    if (!(getRemainingIntFromString(string) == -1)) {
                        successPercent = getRemainingIntFromString(string);
                    }
                }
                item = formatBookLore(getConfigItem(masterconfig, "givedp-items." + rarity + ".revealed-item"), randomenchant, successPercent, 100);
                itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.name").replace("{ENCHANT}", randomenchant)));
                item.setItemMeta(itemMeta);
                giveItem((Player) event.getWhoClicked(), item);
                item = event.getCurrentItem();
                itemMeta = event.getCurrentItem().getItemMeta();
                if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().contains(TRANSMOG.replace("{LORE_COUNT}", "" + enchantcount))) {
                    itemMeta.setDisplayName(itemMeta.getDisplayName().replace(TRANSMOG.replace("{LORE_COUNT}", "" + enchantcount), TRANSMOG.replace("{LORE_COUNT}", "" + (enchantcount - 1))));
                }
                lore.addAll(event.getCurrentItem().getItemMeta().getLore());
                lore.remove(randome);
                itemMeta.setLore(lore);
                lore.clear();
                success = true;
            }
            if (success) {
                playSuccess((Player) event.getWhoClicked());
                item.setItemMeta(itemMeta);
                event.setCancelled(true);
                event.setCurrentItem(item);
                if (event.getCursor().getAmount() == 1) {
                    event.setCursor(new ItemStack(Material.AIR));
                } else {
                    event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                }
                ((Player) event.getWhoClicked()).updateInventory();
            }
            //
        } else if (event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasDisplayName() && event.getCursor().getItemMeta().hasLore()
                && !(getOriginalEnchantmentName(event.getCursor().getItemMeta().getDisplayName()) == null)
                ) {
            List<String> cursorLore = event.getCursor().getItemMeta().getLore();
            if (cursorLore.contains(ARMOR) && event.getCurrentItem().getType().name().endsWith("HELMET") || cursorLore.contains(ARMOR) && event.getCurrentItem().getType().name().endsWith("CHESTPLATE") || cursorLore.contains(ARMOR) && event.getCurrentItem().getType().name().endsWith("LEGGINGS") || cursorLore.contains(ARMOR) && event.getCurrentItem().getType().name().endsWith("BOOTS")
                    || cursorLore.contains(HELMET) && event.getCurrentItem().getType().name().endsWith("HELMET") || cursorLore.contains(CHESTPLATE) && event.getCurrentItem().getType().name().endsWith("CHESTPLATE") || cursorLore.contains(LEGGINGS) && event.getCurrentItem().getType().name().endsWith("LEGGINGS") || cursorLore.contains(BOOTS) && event.getCurrentItem().getType().name().endsWith("BOOTS")
                    || cursorLore.contains(AXE) && event.getCurrentItem().getType().name().endsWith("_AXE")
                    || cursorLore.contains(BOW) && event.getCurrentItem().getType().name().endsWith("BOW")
                    || getOriginalEnchantmentName(event.getCursor().getItemMeta().getDisplayName()).equals("Rage") && event.getCurrentItem().getType().name().endsWith("BOW")
                    || cursorLore.contains(PICKAXE) && event.getCurrentItem().getType().name().endsWith("PICKAXE")
                    || cursorLore.contains(SWORD) && event.getCurrentItem().getType().name().endsWith("SWORD")
                    || cursorLore.contains(TOOL) && event.getCurrentItem().getType().name().endsWith("PICKAXE") || cursorLore.contains(TOOL) && event.getCurrentItem().getType().name().endsWith("SPADE") || cursorLore.contains(ARMOR) && event.getCurrentItem().getType().name().endsWith("_AXE")
                    || cursorLore.contains(WEAPON) && event.getCurrentItem().getType().name().endsWith("_AXE") || cursorLore.contains(WEAPON) && event.getCurrentItem().getType().name().endsWith("SWORD")) {
                item = event.getCurrentItem();
                itemMeta = event.getCurrentItem().getItemMeta();
                lore.clear();
                int playerlevelcap = 0, enchantsize = api.getEnchantmentsOnItem(item).size(), success = 0, destroy = 0, bookEnchantLevel = api.getEnchantmentLevel(event.getCursor().getItemMeta().getDisplayName()), currentEnchantLevel = 0;
                String OEN = getOriginalEnchantmentName(event.getCursor().getItemMeta().getDisplayName()), enchantName = enchantmentsInfo.getString(OEN + ".enchant-name"), rarity = getEnchantmentRarity(OEN);
                int level = -1;
                boolean addnumber = false;
                if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore()) {
                    lore.addAll(event.getCurrentItem().getItemMeta().getLore());
                    for (String string : api.getEnchantmentsOnItem(event.getCurrentItem())) {
                        if (level == -1 && string.split(" ")[0].equals(OEN)) {
                            currentEnchantLevel = api.getEnchantmentLevel(string);
                            if (currentEnchantLevel + 1 > enchantmentsInfo.getInt(getOriginalEnchantmentName(string) + ".max-level")) {
                                return;
                            }
                            if (bookEnchantLevel > currentEnchantLevel) {
                                level = bookEnchantLevel;
                            } else {
                                level = (currentEnchantLevel + 1);
                            }
                            addnumber = true;
                        }
                    }
                }
                for (int i = 0; i <= 100; i++) {
                    if (event.getCursor().getItemMeta().getLore().contains(SUCCESS.replace("{PERCENT}", "" + i))) {
                        success = i;
                    }
                    if (event.getCursor().getItemMeta().getLore().contains(DESTROY.replace("{PERCENT}", "" + i))) {
                        destroy = i;
                    }
                }
                for (int i = 1; i <= 50; i++) {
                    if (event.getWhoClicked().hasPermission("RandomPackage.levelcap." + i)) {
                        playerlevelcap = i;
                    }
                }
                if (enchantsize > playerlevelcap) {
                    return;
                }
                //
                if (random.nextInt(101) <= success) {
                    if (!(level == -1)) {
                        lore.remove(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.apply-format").replace("{ENCHANT}", enchantName + " " + replaceIntWithRomanNumerals(currentEnchantLevel))));
                        lore.add(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.apply-format").replace("{ENCHANT}", enchantName + " " + replaceIntWithRomanNumerals(level))));
                    } else {
                        lore.add(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.apply-format").replace("{ENCHANT}", enchantName + " " + replaceIntWithRomanNumerals(bookEnchantLevel))));
                    }
                    for (int i = 0; i < lore.size(); i++) {
                        if (getOriginalEnchantmentName(lore.get(i)) == null) {
                            String string = lore.get(i);
                            lore.remove(i);
                            lore.add(string);
                        }
                    }
                    if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore()) {
                        if (event.getCurrentItem().getItemMeta().getLore().contains(WHITE_SCROLL_PROTECTED)) {
                            lore.remove(WHITE_SCROLL_PROTECTED);
                            lore.add(WHITE_SCROLL_PROTECTED);
                        }
                        for (String string : apply_soultracker) {
                            if (event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size() - 1).startsWith(string.replace("{SOULS}", ""))) {
                                lore.remove(string);
                                lore.add(string);
                            }
                        }
                    }
                    playSuccess((Player) event.getWhoClicked());
                } else if (random.nextInt(101) <= destroy) {
                    playDestroy((Player) event.getWhoClicked());
                    if (!(lore.contains(WHITE_SCROLL_PROTECTED))) {
                        item = new ItemStack(Material.AIR);
                    } else {
                        lore.remove(WHITE_SCROLL_PROTECTED);
                        addnumber = false;
                    }
                } else {
                    playDestroy((Player) event.getWhoClicked());
                }
                //
                if (!(item.getType().equals(Material.AIR))) {
                    if (addnumber && itemMeta.hasDisplayName() && !(itemMeta.getDisplayName().equals(itemMeta.getDisplayName().replace(TRANSMOG.replace("{LORE_COUNT}", "" + enchantsize), "")))) {
                        itemMeta.setDisplayName(itemMeta.getDisplayName().replace(TRANSMOG.replace("{LORE_COUNT}", "" + enchantsize), TRANSMOG.replace("{LORE_COUNT}", Integer.toString(enchantsize + 1))));
                    }
                    itemMeta.setLore(lore);
                    lore.clear();
                    item.setItemMeta(itemMeta);
                }
                //
                event.setCancelled(true);
                event.setCurrentItem(item);
                if (event.getCursor().getAmount() == 1) {
                    event.setCursor(new ItemStack(Material.AIR));
                } else {
                    event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                }
                ((Player) event.getWhoClicked()).updateInventory();
            }
        } else if (!(event.getCursor() == null) && event.getCursor().hasItemMeta() && event.getCursor().getItemMeta().hasLore() && event.getCursor().getItemMeta().hasDisplayName() && !(event.getCurrentItem() == null)) {
            boolean success = false;
            String OEN = null, enchantrarity = null;
            if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName()) {
                OEN = getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName());
            }
            if (!(OEN == null)) {
                item = event.getCurrentItem();
                itemMeta = item.getItemMeta();
                lore.clear();
                enchantrarity = enchantmentsInfo.getString(OEN + ".rarity").toLowerCase();
                if (!(getRandomizationScrollRarity(event.getCursor().getItemMeta()) == null)) {
                    String randomizationScrollRarity = getRandomizationScrollRarity(event.getCursor().getItemMeta());
                    if (event.getCursor().getItemMeta().equals(godRandomizationScroll.getItemMeta()) || enchantrarity.equals(randomizationScrollRarity)) {
                        for (String string : itemMeta.getLore()) {
                            if (string.equals(SUCCESS.replace("{PERCENT}", "" + getRemainingIntFromString(string)))) {
                                string = SUCCESS.replace("{PERCENT}", "" + random.nextInt(101));
                            } else if (string.equals(DESTROY.replace("{PERCENT}", "" + getRemainingIntFromString(string)))) {
                                string = DESTROY.replace("{PERCENT}", "" + random.nextInt(101));
                            }
                            lore.add(ChatColor.translateAlternateColorCodes('&', string));
                            success = true;
                        }
                    }
                } else if (!(getDustRarity(event.getCursor()) == null)) {
                    String dustRarity = getDustRarity(event.getCursor());
                    ItemStack dust = getDust(dustRarity);
                    if (dustRarity.startsWith(enchantrarity)) {
                        int percent = -1;
                        for (int z = 0; z < dust.getItemMeta().getLore().size(); z++) {
                            if (dust.getItemMeta().getLore().get(z).contains("{PERCENT}")) {
                                percent = getRemainingIntFromString(event.getCursor().getItemMeta().getLore().get(z));
                            }
                        }
                        if (percent == -1) {
                            return;
                        }
                        for (String string : itemMeta.getLore()) {
                            if (string.equals(SUCCESS.replace("{PERCENT}", "" + getRemainingIntFromString(string)))) {
                                int bookpercent = getRemainingIntFromString(string);
                                if (bookpercent + percent > 100) {
                                    bookpercent = 50;
                                    percent = 50;
                                }
                                string = SUCCESS.replace("{PERCENT}", "" + (bookpercent + percent));
                            }
                            lore.add(ChatColor.translateAlternateColorCodes('&', string));
                        }
                        success = true;
                    } else {
                        return;
                    }
                }
            } else if (!(getSoulTrackerItemStackRarity(event.getCursor().getItemMeta()) == null)) {
                String rarity = getSoulTrackerItemStackRarity(event.getCursor().getItemMeta());
                boolean did = false;
                if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasLore()) {
                    lore.addAll(event.getCurrentItem().getItemMeta().getLore());
                    for (String string : apply_soultracker) {
                        if (did == false && !(string == null)
                                && event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size() - 1).startsWith(string.replace("{SOULS}", ""))) {
                            did = true;
                            lore.set(lore.size() - 1, string.replace("{SOULS}", "0"));
                        }
                    }
                } else {
                    String soultracker = null;
                    if (rarity.equals("legendary")) {
                        soultracker = apply_soultracker.get(0);
                    } else if (rarity.equals("ultimate")) {
                        soultracker = apply_soultracker.get(1);
                    } else if (rarity.equals("elite")) {
                        soultracker = apply_soultracker.get(2);
                    } else if (rarity.equals("unique")) {
                        soultracker = apply_soultracker.get(3);
                    } else if (rarity.equals("simple")) {
                        soultracker = apply_soultracker.get(4);
                    }
                    lore.add(soultracker.replace("{SOULS}", "0"));
                }
                for (String string : messages.getStringList("soul-trackers.apply." + rarity.toLowerCase())) {
                    if (string.contains("{ITEM}")) {
                        string = string.replace("{ITEM}", event.getCurrentItem().getType().name());
                    }
                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                }
                success = true;
            }
            if (success == true) {
                itemMeta.setLore(lore);
                lore.clear();
                item.setItemMeta(itemMeta);
                event.setCancelled(true);
                event.setCurrentItem(item);
                if (event.getCursor().getAmount() == 1) {
                    event.setCursor(new ItemStack(Material.AIR));
                } else {
                    event.getCursor().setAmount(event.getCursor().getAmount() - 1);
                }
                ((Player) event.getWhoClicked()).updateInventory();
                playSuccess((Player) event.getWhoClicked());
                return;
            }
        } else if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(SHOWCASE_SELF)) {
            event.setCancelled(true);
            item = event.getCurrentItem();
            Inventory inv = null;
            if (event.getRawSlot() >= event.getWhoClicked().getOpenInventory().getTopInventory().getSize()) {
                inv = showcaseAddInv;
            } else {
                inv = showcaseDestroyInv;
            }
            event.getWhoClicked().openInventory(inv);
            event.getWhoClicked().getOpenInventory().getTopInventory().setContents(inv.getContents());
            //
            if (!(event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() == -1)) {
                event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty(), event.getCurrentItem());
            }
            ((Player) event.getWhoClicked()).updateInventory();
        } else if (!(checkShopTitle(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle()) == -1)) {
            event.setCancelled(true);
            if (event.getCurrentItem().equals(shopBackToCategory)) {
                openGui((Player) event.getWhoClicked(), RPGui.SHOP);
            } else {
                int slot = checkShopTitle(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle());
                if (event.getClick().name().endsWith("LEFT") && !(shop.get(slot + "." + event.getRawSlot() + ".buy-price") == null)) {
                    double cost = shop.getDouble(slot + "." + event.getRawSlot() + ".buy-price");
                    int amountPurchased = 0;
                    if (event.getClick().equals(ClickType.LEFT)) {
                        amountPurchased = shop.getInt("click-options.left");
                    } else {
                        amountPurchased = shop.getInt("click-options.shift-left");
                    }
                    item = new ItemStack(event.getCurrentItem().getType(), amountPurchased, event.getCurrentItem().getData().getData());
                    if (item.getType().name().contains("POTION")) {
                        PotionMeta meta = (PotionMeta) event.getCurrentItem().getItemMeta();
                        lore.clear();
                        meta.setLore(lore);
                        meta.setDisplayName(null);
                        if (Bukkit.getVersion().contains("1.8")) {
                            item = Potion.fromItemStack(event.getCurrentItem()).toItemStack(amountPurchased);
                        }
                        if (item.getType().name().contains("SPLASH")) {
                            item.setType(Material.getMaterial("SPLASH_POTION"));
                        }
                        item.setItemMeta(meta);
                    }
                    if (RP_Vault.economy.withdrawPlayer((Player) event.getWhoClicked(), cost * amountPurchased).transactionSuccess()) {
                        if (item.getType().equals(Material.MOB_SPAWNER)) {
                            giveSpawner((Player) event.getWhoClicked(), slot + "." + event.getRawSlot() + ".item", shop);
                        } else {
                            giveItem((Player) event.getWhoClicked(), item);
                        }
                        getAndPlaySound((Player) event.getWhoClicked(), event.getWhoClicked().getLocation(), "shop.buy", false);
                        for (String string : messages.getStringList("shop.buy")) {
                            if (string.contains("{TOTAL_COST}")) {
                                string = string.replace("{TOTAL_COST}", formatDoubleUsingCommas((amountPurchased * cost)));
                            }
                            if (string.contains("{AMOUNT}")) {
                                string = string.replace("{AMOUNT}", "" + amountPurchased);
                            }
                            if (string.contains("{ITEM}")) {
                                string = string.replace("{ITEM}", item.getType().name());
                            }
                            event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                        }
                    } else {
                        sendStringListMessage((Player) event.getWhoClicked(), "shop.not-enough-balance", cost);
                        getAndPlaySound((Player) event.getWhoClicked(), event.getWhoClicked().getLocation(), "shop.not-enough-balance", false);
                    }
                } else if (event.getClick().name().endsWith("RIGHT") && !(shop.get(slot + "." + event.getRawSlot() + ".sell-price") == null)) {
                    item = new ItemStack(event.getCurrentItem().getType(), 1, event.getCurrentItem().getData().getData());
                    if (!(event.getWhoClicked().getInventory().containsAtLeast(item, 1))) {
                        sendStringListMessage((Player) event.getWhoClicked(), "shop.not-have-any-to-sell", 0);
                        getAndPlaySound((Player) event.getWhoClicked(), event.getWhoClicked().getLocation(), "shop.not-enough-to-sell", false);
                        return;
                    }
                    double price = shop.getDouble(slot + "." + event.getRawSlot() + ".sell-price");
                    int amountSold = getItemAmountInPlayerInventory((Player) event.getWhoClicked(), item.getType());
                    if (event.getClick().equals(ClickType.RIGHT)) {
                        amountSold = shop.getInt("click-options.right");
                    } else {
                        if (amountSold > shop.getInt("click-options.shift-right")) {
                            amountSold = shop.getInt("click-options.shift-right");
                        }
                    }
                    RP_Vault.economy.depositPlayer((Player) event.getWhoClicked(), price * amountSold);
                    removeItem((Player) event.getWhoClicked(), item, amountSold);
                    for (String string : messages.getStringList("shop.sell")) {
                        if (string.contains("{TOTAL_REVENUE}")) {
                            string = string.replace("{TOTAL_REVENUE}", formatDoubleUsingCommas((amountSold * price)));
                        }
                        if (string.contains("{AMOUNT}")) {
                            string = string.replace("{AMOUNT}", "" + amountSold);
                        }
                        if (string.contains("{SELL_PRICE}")) {
                            string = string.replace("{SELL_PRICE}", "" + price);
                        }
                        if (string.contains("{ITEM}")) {
                            string = string.replace("{ITEM}", item.getType().name());
                        }
                        event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                    }
                }
            }
            return;
        } else if (!(checkFilterTitle(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle()) == -1)) {
            event.setCancelled(true);
            boolean filtered = false;
            File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "player-data" + File.separator, event.getWhoClicked().getUniqueId().toString() + ".yml");
            FileConfiguration config = getPlayerConfig((Player) event.getWhoClicked());
            lore.clear();
            lore.addAll(config.getStringList("filter.items"));
            item = event.getCurrentItem();
            itemMeta = item.getItemMeta();
            if (lore.toString().contains(event.getCurrentItem().getType().name())) {
                itemMeta.setDisplayName(FILTERED_ITEM_FALSE + ChatColor.stripColor(itemMeta.getDisplayName()));
                itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
                itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                lore.remove(event.getCurrentItem().getType().name());
            } else {
                filtered = true;
                itemMeta.setDisplayName(FILTERED_ITEM_TRUE + ChatColor.stripColor(itemMeta.getDisplayName()));
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                lore.add(event.getCurrentItem().getType().name());
            }
            item.setItemMeta(itemMeta);
            if (filtered == true) {
                item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
            }
            event.setCurrentItem(item);
            config.set("filter.items", lore.toArray());
            lore.clear();
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ((Player) event.getWhoClicked()).updateInventory();
            return;
        } else if (!(checkDropPackageTitle(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle()) == null)) {
            event.setCancelled(true);
            if (event.getRawSlot() >= event.getWhoClicked().getOpenInventory().getTopInventory().getSize()) {
                return;
            }
            ItemStack dp = checkDropPackageTitle(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle());
            String rarity = null;
            if (dp.equals(godDropPackage)) {
                rarity = "god";
            } else if (dp.equals(legendaryDropPackage)) {
                rarity = "legendary";
            } else if (dp.equals(ultimateDropPackage)) {
                rarity = "ultimate";
            } else if (dp.equals(eliteDropPackage)) {
                rarity = "elite";
            } else if (dp.equals(uniqueDropPackage)) {
                rarity = "unique";
            } else if (dp.equals(simpleDropPackage)) {
                rarity = "simple";
            }
            int itemsneeded = getDPRedeemableItems(dp), selectedAmount = 0;
            useoldversion(event, rarity, selectedAmount, itemsneeded);
			/*if(api.droppackage_UseOldVersion[dp] == true) {

			} else {
				//
			}*/
            ((Player) event.getWhoClicked()).updateInventory();
            return;
        } else if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle() == null
                || !(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(alchemistInv.getTitle()))
                && !(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(enchanterInv.getTitle()))
                && !(event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(tinkererInv.getTitle()))) {
            return;
        } else {
            String title = event.getWhoClicked().getOpenInventory().getTopInventory().getTitle();
            if (event.getRawSlot() >= event.getWhoClicked().getOpenInventory().getTopInventory().getSize()) {
                if (title.equals(tinkererInv.getTitle())) {
                    event.setCancelled(true);
                    if (event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() == -1 || !(event.getCurrentItem().hasItemMeta())) {
                        return;
                    }
                    //
                    if (event.getCurrentItem().getType().name().endsWith("HELMET") || event.getCurrentItem().getType().name().endsWith("CHESTPLATE") || event.getCurrentItem().getType().name().endsWith("LEGGINGS") || event.getCurrentItem().getType().name().endsWith("BOOTS")
                            || event.getCurrentItem().getType().name().endsWith("SWORD") || event.getCurrentItem().getType().name().endsWith("AXE") || event.getCurrentItem().getType().name().endsWith("SPADE") || event.getCurrentItem().getType().name().endsWith("BOW")) {
                        int xp = 0, slot = 0;
                        for (Enchantment enchant : event.getCurrentItem().getEnchantments().keySet()) {
                            xp = xp + getEnchantTinkererXp(enchant, event.getCurrentItem().getEnchantmentLevel(enchant));
                        }
                        if (!(xp == 0)) {
                            lore.clear();
                            item = api.getRPItem(RPItem.EXPERIENCE_BOTTLE).clone();
                            itemMeta = item.getItemMeta();
                            lore.addAll(itemMeta.getLore());
                            for (int i = 0; i < lore.size(); i++) {
                                if (lore.get(i).contains("{AMOUNT}")) {
                                    lore.set(i, lore.get(i).replace("{AMOUNT}", formatIntUsingCommas(xp)));
                                } else if (lore.get(i).contains("{BOTTLER_NAME}")) {
                                    lore.set(i, lore.get(i).replace("{BOTTLER_NAME}", "Tinkerer"));
                                }
                            }
                            itemMeta.setLore(lore);
                            lore.clear();
                            item.setItemMeta(itemMeta);
                            //
                            if (event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() <= 8) {
                                slot = 4;
                            } else {
                                slot = 5;
                            }
                            event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() + slot, item);
                            event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty(), event.getCurrentItem());
                            event.setCurrentItem(new ItemStack(Material.AIR));
                            ((Player) event.getWhoClicked()).updateInventory();
                        }
                        return;
                    } else if (!(getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName()) == null)) {
                        String rarity = getEnchantmentRarity(getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName()));
                        ItemStack fireball = null;
                        if (rarity.equals("legendary")) {
                            fireball = legendaryFireball.clone();
                        } else if (rarity.equals("ultimate")) {
                            fireball = ultimateFireball.clone();
                        } else if (rarity.equals("elite")) {
                            fireball = eliteFireball.clone();
                        } else if (rarity.equals("unique")) {
                            fireball = uniqueFireball.clone();
                        } else if (rarity.equals("simple")) {
                            fireball = simpleFireball.clone();
                        } else {
                            return;
                        }
                        int slot = 0;
                        if (event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() <= 8) {
                            slot = 4;
                        } else {
                            slot = 5;
                        }
                        event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() + slot, fireball);
                        event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty(), event.getCurrentItem());
                        event.setCurrentItem(new ItemStack(Material.AIR));
                        ((Player) event.getWhoClicked()).updateInventory();
                        return;
                    } else {
                        return;
                    }
                    //
                } else if (title.equals(alchemistInv.getTitle())) {
                    event.setCancelled(true);
                    if (event.getCurrentItem().hasItemMeta() && event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
                        String success = null;
                        for (int i = 1; i <= 3; i++) {
                            if (success == null) {
                                if (i == 1) {
                                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(soulPrimalDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(legendaryPrimalDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(ultimatePrimalDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(elitePrimalDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(uniquePrimalDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(simplePrimalDust.getItemMeta().getDisplayName())) {
                                        success = "primal";
                                    }
                                } else if (i == 2) {
                                    if (event.getCurrentItem().getItemMeta().getDisplayName().equals(soulRegularDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(legendaryRegularDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(ultimateRegularDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(eliteRegularDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(uniqueRegularDust.getItemMeta().getDisplayName())
                                            || event.getCurrentItem().getItemMeta().getDisplayName().equals(simpleRegularDust.getItemMeta().getDisplayName())) {
                                        success = "regular";
                                    }
                                } else if (i == 3 && !(getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName()) == null)) {
                                    success = "enchant";
                                }
                            }
                        }
                        if (!(success == null)) {
                            boolean upgrade = false;
                            int cost = -1;
                            if (event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() == 5 && !(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(3).getItemMeta().getDisplayName().equals(event.getCurrentItem().getItemMeta().getDisplayName()))
                                    || event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() == 3
                                    && !(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(5) == null)
                                    && !(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(5).getItemMeta().getDisplayName().equals(event.getCurrentItem().getItemMeta().getDisplayName()))
                                    || event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() < 0
                                    ) {
                                return;
                            } else if (event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() == 3 && event.getWhoClicked().getOpenInventory().getTopInventory().getItem(5) == null
                                    || event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() == 5 && event.getWhoClicked().getOpenInventory().getTopInventory().getItem(3) == null) {
                                // This is meant to be here :)
                            } else {
                                lore.clear();
                                int slot = 0;
                                if (event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty() == 3) {
                                    slot = 5;
                                } else {
                                    slot = 3;
                                }
                                item = event.getWhoClicked().getOpenInventory().getTopInventory().getItem(slot).clone();
                                itemMeta = item.getItemMeta();
                                if (success.equals("primal") || success.equals("regular")) {
                                    String leveleduprarity = getLeveledUpDustRarityString(item);
                                    boolean did = false;
                                    cost = getDustCost(leveleduprarity.split("-")[1], leveleduprarity.split("-")[0]);
                                    for (int i = 0; i < itemMeta.getLore().size(); i++) {
                                        if (!(getRemainingIntFromString(itemMeta.getLore().get(i)) == -1) && did == false) {
                                            did = true;
                                            item = getLeveledUpDust(item, success, ((getRemainingIntFromString(itemMeta.getLore().get(i)) + getRemainingIntFromString(event.getCurrentItem().getItemMeta().getLore().get(i))) / 2)).clone();
                                            if (item == null) {
                                                return;
                                            }
                                        }
                                    }
                                } else if (success.equals("enchant")) {
                                    String OEN = getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName()), rarity = getEnchantmentRarity(OEN);
                                    int level = api.getEnchantmentLevel(event.getCurrentItem().getItemMeta().getDisplayName());
                                    if (level >= enchantmentsInfo.getInt(OEN + ".max-level")) {
                                        return;
                                    } else {
                                        cost = Integer.parseInt(enchantmentsInfo.getString(OEN + ".alchemist").split(":")[level - 1]);
                                    }
                                    item = new ItemStack(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(slot).getType(), 1, event.getWhoClicked().getOpenInventory().getTopInventory().getItem(slot).getData().getData());
                                    itemMeta = item.getItemMeta();
                                    itemMeta.setDisplayName("randomhashtags was here");
                                    int successPercent = 0, destroyPercent = 0, higherDestroyPercent = -1;
                                    for (int i = 0; i <= 100; i++) {
                                        if (event.getWhoClicked().getOpenInventory().getTopInventory().getItem(slot).getItemMeta().getLore().contains(SUCCESS.replace("{PERCENT}", "" + i))
                                                || event.getCurrentItem().getItemMeta().getLore().contains(SUCCESS.replace("{PERCENT}", "" + i))) {
                                            successPercent = successPercent + i;
                                        }
                                        if (event.getWhoClicked().getOpenInventory().getTopInventory().getItem(slot).getItemMeta().getLore().contains(DESTROY.replace("{PERCENT}", "" + i))
                                                || event.getCurrentItem().getItemMeta().getLore().contains(DESTROY.replace("{PERCENT}", "" + i))) {
                                            if (i > higherDestroyPercent) {
                                                higherDestroyPercent = i;
                                            }
                                            destroyPercent = destroyPercent + i;
                                        }
                                    }
                                    destroyPercent = higherDestroyPercent + (destroyPercent / 4);
                                    if (destroyPercent > 100) {
                                        destroyPercent = 100;
                                    }
                                    itemMeta.setLore(masterconfig.getStringList("givedp-items." + rarity + ".revealed-item.lore"));
                                    item.setItemMeta(itemMeta);
                                    item = formatBookLore(item, event.getCurrentItem().getItemMeta().getDisplayName(), successPercent / 4, destroyPercent);
                                    itemMeta = item.getItemMeta();
                                    itemMeta.setDisplayName(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(slot).getItemMeta().getDisplayName().replace(replaceIntWithRomanNumerals(level), replaceIntWithRomanNumerals(level + 1)));
                                } else {
                                    return;
                                }
                                upgrade = true;
                            }
                            if (upgrade == true) {
                                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                item.setItemMeta(itemMeta);
                                item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                                event.getWhoClicked().getOpenInventory().getTopInventory().setItem(13, item);
                                item = alchemistAccept.clone();
                                itemMeta = item.getItemMeta();
                                for (String string : itemMeta.getLore()) {
                                    if (string.contains("{COST}")) {
                                        string = string.replace("{COST}", formatIntUsingCommas(cost));
                                    }
                                    lore.add(string);
                                }
                                itemMeta.setLore(lore);
                                lore.clear();
                                item.setItemMeta(itemMeta);
                                event.getWhoClicked().getOpenInventory().getTopInventory().setItem(22, item);
                            }
                            event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getWhoClicked().getOpenInventory().getTopInventory().firstEmpty(), event.getCurrentItem());
                            event.setCurrentItem(new ItemStack(Material.AIR));
                        }
                    }
                    ((Player) event.getWhoClicked()).updateInventory();
                } else {
                    event.setCancelled(true);
                }
                return;
            }
            event.setCancelled(true);
            ((Player) event.getWhoClicked()).updateInventory();
            //
            if (title.equals(givedpDropPackagesInv.getTitle()) || title.equals(givedpFallenHeroesInv.getTitle()) || title.equals(givedpGivedpInv.getTitle()) || title.equals(givedpRarityBooksInv.getTitle()) || title.equals(givedpSoulTrackerInv.getTitle()) || title.equals(givedpRandomizationScrollInv.getTitle())) {
                item = event.getCurrentItem().clone();
                itemMeta = item.getItemMeta();
                lore.clear();
                boolean set = false;
                for (String string : itemMeta.getLore()) {
                    if (string.contains("{PERCENT}") && item.getItemMeta().getDisplayName().equals(blackscroll.getItemMeta().getDisplayName())) {
                        string = string.replace("{PERCENT}", "" + api.getRandomBlackScrollPercent());
                        set = true;
                    }
                    lore.add(ChatColor.translateAlternateColorCodes('&', string));
                }
                if (set == true) {
                    itemMeta.setLore(lore);
                    item.setItemMeta(itemMeta);
                }
                giveItem((Player) event.getWhoClicked(), item);
            } else if (title.equals(vkitInv.getTitle())) {
                if (event.getClick().name().contains("RIGHT")) {
                    giveVkit((Player) event.getWhoClicked(), event.getRawSlot(), true);
                } else {
                    giveVkit((Player) event.getWhoClicked(), event.getRawSlot(), false);
                }
                ((Player) event.getWhoClicked()).updateInventory();
            } else if (title.equals(showcaseAddInv.getTitle()) || title.equals(showcaseDestroyInv.getTitle())) {
                if (event.getCurrentItem().equals(showcaseConfirmAdd)) {
                    editShowcaseItem(event, (Player) event.getWhoClicked(), true);
                } else if (event.getCurrentItem().equals(showcaseConfirmDestroy)) {
                    editShowcaseItem(event, (Player) event.getWhoClicked(), false);
                } else if (event.getCurrentItem().equals(showcaseCancelAdd) || event.getCurrentItem().equals(showcaseCancelDestroy)) {
                    api.openShowcase((Player) event.getWhoClicked(), null);
                }
            } else if (title.equals(gkitInv.getTitle())) {
                FileConfiguration kitdata = RandomPackage.getConfig(RPConfigs.KIT_DATA);
                if (event.getClick().equals(ClickType.LEFT)) {
                    if (event.getWhoClicked().hasPermission("RandomPackage.gkits." + event.getRawSlot()) || getPlayerConfig((Player) event.getWhoClicked()).getStringList("unlocked-kits.gkits").toString().contains("" + event.getRawSlot())) {
                        int sec = 0;
                        if (kitdata.get("gkits." + event.getWhoClicked().getUniqueId().toString() + "_" + event.getRawSlot()) == null) {
                            sec = gkits.getInt(event.getRawSlot() + ".cooldown");
                        } else {
                            sec = gkits.getInt(event.getRawSlot() + ".cooldown") - (int) ((System.currentTimeMillis() - kitdata.getLong("gkits." + event.getWhoClicked().getUniqueId().toString() + "_" + event.getRawSlot())) / 1000);
                        }
                        int days = sec / (60 * 60 * 24);
                        sec -= days * (60 * 60 * 24);
                        int hours = sec / (60 * 60);
                        sec -= hours * (60 * 60);
                        int min = sec / 60;
                        sec = sec - (min * 60);
                        if (kitdata.get("gkits." + event.getWhoClicked().getUniqueId().toString() + "_" + event.getRawSlot()) == null) {
                            File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "data" + File.separator, "redeemed-kits.yml");
                            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                            config.set("gkits." + event.getWhoClicked().getUniqueId().toString() + "_" + event.getRawSlot(), System.currentTimeMillis());
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            item = GKIT_COOLDOWN.clone();
                            itemMeta = item.getItemMeta();
                            lore.clear();
                            if (itemMeta.hasLore()) {
                                for (String string : itemMeta.getLore()) {
                                    if (string.contains("{PREFIX}")) {
                                        string = string.replace("{PREFIX}", configPrefix);
                                    }
                                    if (string.contains("{SECONDS}")) {
                                        string = string.replace("{SECONDS}", "" + sec);
                                    }
                                    if (string.contains("{MINUTES}")) {
                                        string = string.replace("{MINUTES}", "" + min);
                                    }
                                    if (string.contains("{HOURS}")) {
                                        string = string.replace("{HOURS}", "" + hours);
                                    }
                                    if (string.contains("{DAYS")) {
                                        string = string.replace("{DAYS}", "" + days);
                                    }
                                    lore.add(string);
                                }
                            }
                            itemMeta.setLore(lore);
                            item.setItemMeta(itemMeta);
                            event.setCurrentItem(item);
                            giveGkit((Player) event.getWhoClicked(), event.getRawSlot(), false);
                        } else {
                            if (!(kitdata.get("gkits." + event.getWhoClicked().getUniqueId().toString() + "_" + event.getRawSlot()) == null)
                                    && System.currentTimeMillis() < Math.addExact(kitdata.getLong("gkits." + event.getWhoClicked().getUniqueId().toString() + "_" + event.getRawSlot()), (gkits.getInt(event.getRawSlot() + ".cooldown") * 1000))) {
                                getAndPlaySound((Player) event.getWhoClicked(), event.getWhoClicked().getLocation(), "gkit.need-to-wait-for-cooldown", false);
                                for (String string : gkits.getStringList("cooldown.lore")) {
                                    if (string.contains("{SECONDS}")) {
                                        string = string.replace("{SECONDS}", "" + sec);
                                    }
                                    if (string.contains("{MINUTES}")) {
                                        string = string.replace("{MINUTES}", "" + min);
                                    }
                                    if (string.contains("{HOURS}")) {
                                        string = string.replace("{HOURS}", "" + hours);
                                    }
                                    if (string.contains("{DAYS")) {
                                        string = string.replace("{DAYS}", "" + days);
                                    }
                                }
                                for (String string : messages.getStringList("gkit.message")) {
                                    if (string.contains("{PREFIX}")) {
                                        string = string.replace("{PREFIX}", configPrefix);
                                    }
                                    if (string.contains("{SECONDS}")) {
                                        string = string.replace("{SECONDS}", "" + sec);
                                    }
                                    if (string.contains("{MINUTES}")) {
                                        string = string.replace("{MINUTES}", "" + min);
                                    }
                                    if (string.contains("{HOURS}")) {
                                        string = string.replace("{HOURS}", "" + hours);
                                    }
                                    if (string.contains("{DAYS")) {
                                        string = string.replace("{DAYS}", "" + days);
                                    }
                                    event.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                                }
                                event.getWhoClicked().closeInventory();
                            } else {

                            }
                        }
                    }
                } else if (event.getClick().equals(ClickType.RIGHT)) {
                    giveGkit((Player) event.getWhoClicked(), event.getRawSlot(), true);
                }
            } else if (title.equals(givedpInv.getTitle())) {
                Inventory inv;
                if (event.getRawSlot() == 1) {
                    inv = givedpRarityBooksInv;
                } else if (event.getRawSlot() == 2) {
                    inv = givedpSoulTrackerInv;
                } else if (event.getRawSlot() == 3) {
                    inv = givedpRandomizationScrollInv;
                } else if (event.getRawSlot() == 4) {
                    inv = givedpDropPackagesInv;
                } else if (event.getRawSlot() == 6) {
                    inv = givedpGivedpInv;
                } else if (event.getRawSlot() == 8) {
                    inv = givedpFallenHeroesInv;
                } else {
                    return;
                }
                //
                event.getWhoClicked().openInventory(Bukkit.createInventory(event.getWhoClicked(), inv.getSize(), inv.getTitle()));
                event.getWhoClicked().getOpenInventory().getTopInventory().setContents(inv.getContents());
                //
            } else if (title.equals(tinkererInv.getTitle())) {
                String OEN = null;
                if (event.getCurrentItem().getItemMeta().hasDisplayName() && !(getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName()) == null)) {
                    OEN = getOriginalEnchantmentName(event.getCurrentItem().getItemMeta().getDisplayName());
                }
                if (event.getCurrentItem().equals(tinkererAccept)) {
                    for (ItemStack itemstack : event.getWhoClicked().getOpenInventory().getTopInventory().getContents()) {
                        if (!(itemstack == null) && !(itemstack.getType().name().endsWith("HELMET")) && !(itemstack.getType().name().endsWith("CHESTPLATE")) && !(itemstack.getType().name().endsWith("LEGGINGS")) && !(itemstack.getType().name().endsWith("BOOTS"))
                                && !(itemstack.getType().name().equals("BOW")) && !(itemstack.getType().name().endsWith(randombook.getType().name())) && !(itemstack.getType().name().endsWith("SWORD")) && !(itemstack.getType().name().endsWith("AXE")) && !(itemstack.getType().name().endsWith("SPADE"))
                                && !(itemstack.getType().name().contains("GLASS"))) {
                            giveItem((Player) event.getWhoClicked(), itemstack);
                        }
                    }
                    tinkerer_accept = true;
                    event.getWhoClicked().closeInventory();
                } else if (event.getCurrentItem().equals(tinkererDivider)
                        || !(event.getCurrentItem().getType().name().endsWith("HELMET")) && !(event.getCurrentItem().getType().name().endsWith("CHESTPLATE")) && !(event.getCurrentItem().getType().name().endsWith("LEGGINGS")) && !(event.getCurrentItem().getType().name().endsWith("BOOTS"))
                        && !(event.getCurrentItem().getType().name().endsWith("SWORD")) && !(event.getCurrentItem().getType().name().endsWith("AXE")) && !(event.getCurrentItem().getType().name().endsWith("BOW")) && !(event.getCurrentItem().getType().name().endsWith("SPADE"))
                        && OEN == null) {
                    return;
                } else {
                    int slot = 0;
                    if (event.getRawSlot() <= 8) {
                        slot = 4;
                    } else {
                        slot = 5;
                    }
                    giveItem((Player) event.getWhoClicked(), event.getCurrentItem());
                    event.setCurrentItem(new ItemStack(Material.AIR));
                    event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getRawSlot() + slot, new ItemStack(Material.AIR));
                }
            } else if (title.equals(enchanterInv.getTitle())) {
                item = getEnchanterPurchase(event.getRawSlot());
                if (!(item == null)) {
                    String type = null;
                    int cost = otherGuis.getInt("enchanter." + event.getRawSlot() + ".cost");
                    if (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
                        type = "xp-purchase-success";
                    } else if (otherGuis.getString("enchanter.currency").equalsIgnoreCase("exp")) {
                        if (((Player) event.getWhoClicked()).getTotalExperience() >= cost) {
                            int remaining = ((Player) event.getWhoClicked()).getTotalExperience() - cost;
                            setTotalExperience((Player) event.getWhoClicked(), remaining);
                            type = "xp-purchase-success";
                        } else {
                            type = "need-more-xp";
                        }
                    } else if (otherGuis.getString("enchanter.currency").equalsIgnoreCase("balance")) {
                        if (RP_Vault.economy.withdrawPlayer((Player) event.getWhoClicked(), cost).transactionSuccess()) {
                            type = "cash-purchase-success";
                        } else {
                            type = "need-more-cash";
                        }
                    } else {
                        return;
                    }
                    if (!(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
                        sendStringListMessage((Player) event.getWhoClicked(), "enchanter." + type, cost);
                    }
                    getAndPlaySound((Player) event.getWhoClicked(), event.getWhoClicked().getLocation(), "enchanter." + type, false);
                    if (type.contains("success")) {
                        giveItem((Player) event.getWhoClicked(), item);
                    }
                }
            } else if (title.equals(shopInv.getTitle())) {
                event.getWhoClicked().openInventory(Bukkit.createInventory(event.getWhoClicked(), shop.getInt(event.getRawSlot() + ".size"), ChatColor.translateAlternateColorCodes('&', shop.getString(event.getRawSlot() + ".title"))));
                event.getWhoClicked().getOpenInventory().getTopInventory().setContents(getShopCategory(event.getRawSlot()));
            } else if (title.equals(filterMenuInv.getTitle())) {
                event.getWhoClicked().openInventory(Bukkit.createInventory(event.getWhoClicked(), otherGuis.getInt("filter." + event.getRawSlot() + ".size"), ChatColor.translateAlternateColorCodes('&', otherGuis.getString("filter." + event.getRawSlot() + ".title"))));
                event.getWhoClicked().getOpenInventory().getTopInventory().setContents(getFilterCategory(event.getRawSlot()));
                ItemStack[] gui = event.getWhoClicked().getOpenInventory().getTopInventory().getContents();
                FileConfiguration config = getPlayerConfig((Player) event.getWhoClicked());
                for (int i = 0; i < event.getWhoClicked().getOpenInventory().getTopInventory().getSize(); i++) {
                    if (!(gui[i] == null) && !(gui[i].getType().equals(Material.AIR))) {
                        item = gui[i];
                        itemMeta = item.getItemMeta();
                        if (config.getStringList("filter.items").toString().contains(item.getType().name())) {
                            itemMeta.setDisplayName(FILTERED_ITEM_TRUE + itemMeta.getDisplayName());
                            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        } else {
                            item.removeEnchantment(Enchantment.ARROW_DAMAGE);
                            itemMeta.setDisplayName(FILTERED_ITEM_FALSE + itemMeta.getDisplayName());
                            itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                        item.setItemMeta(itemMeta);
                        if (config.getStringList("filter.items").toString().contains(item.getType().name())) {
                            item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                        }
                        event.getWhoClicked().getOpenInventory().getTopInventory().setItem(i, item);
                    }
                }
            } else if (title.equals(alchemistInv.getTitle())) {
                event.setCancelled(true);
                alchemist_accept = false;
                if (event.getRawSlot() == 3 || event.getRawSlot() == 5) {
                    giveItem((Player) event.getWhoClicked(), event.getCurrentItem());
                    event.getWhoClicked().getOpenInventory().getTopInventory().setItem(event.getRawSlot(), new ItemStack(Material.AIR));
                    item = alchemistPreview.clone();
                    if (!(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(13).equals(item))) {
                        event.getWhoClicked().getOpenInventory().getTopInventory().setItem(13, item);
                    }
                    item = alchemistExchange.clone();
                    if (!(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(22).equals(item))) {
                        event.getWhoClicked().getOpenInventory().getTopInventory().setItem(22, item);
                    }
                } else if (event.getRawSlot() == 22 && !(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(3) == null) && !(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(5) == null)
                        && !(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(13).equals(alchemistPreview))) {
                    Player player = (Player) event.getWhoClicked();
                    int slot = 0;
                    for (int i = 0; i < alchemistAccept.getItemMeta().getLore().size(); i++) {
                        if (alchemistAccept.getItemMeta().getLore().get(i).contains("{COST}")) {
                            slot = i;
                        }
                    }
                    int cost = getRemainingIntFromString(event.getWhoClicked().getOpenInventory().getTopInventory().getItem(22).getItemMeta().getLore().get(slot));
                    if (!(event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE))) {
                        boolean notenough = false;
                        if (alchemistCurrency.equals("xp")) {
                            if (getTotalExperience(player) < cost) {
                                notenough = true;
                                getAndPlaySound(player, player.getLocation(), "alchemist.not-enough-xp", false);
                                sendStringListMessage(player, "alchemist.not-enough-xp", 0);
                            } else {
                                setTotalExperience(player, getTotalExperience(player) - cost);
                                getAndPlaySound(player, player.getLocation(), "alchemist.upgrade-via-xp", false);
                            }
                        } else if (!(RP_Vault.economy == null)) {
                            if (!(RP_Vault.economy.withdrawPlayer(player, cost).transactionSuccess())) {
                                notenough = true;
                                getAndPlaySound(player, player.getLocation(), "alchemist.need-more-cash", false);
                                sendStringListMessage(player, "alchemist.need-more-cash", 0);
                            } else {
                                getAndPlaySound(player, player.getLocation(), "alchemist.upgrade-via-cash", false);
                            }
                        } else {
                            return;
                        }
                        if (notenough == true) {
                            event.getWhoClicked().closeInventory();
                            return;
                        }
                    } else {
                        getAndPlaySound(player, player.getLocation(), "alchemist.upgrade-via-creative", false);
                    }
                    item = event.getWhoClicked().getOpenInventory().getTopInventory().getItem(13).clone();
                    itemMeta = item.getItemMeta();
                    itemMeta.removeEnchant(Enchantment.ARROW_DAMAGE);
                    itemMeta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(itemMeta);
                    giveItem((Player) event.getWhoClicked(), item);
                    alchemist_accept = true;
                    event.getWhoClicked().closeInventory();
                }
            }
            ((Player) event.getWhoClicked()).updateInventory();
            return;
        }
    }

    private int getDPRedeemableItems(ItemStack dp) {
        String type = null;
        if (dp.getItemMeta().equals(godDropPackage.getItemMeta())) {
            type = "god";
        } else if (dp.getItemMeta().equals(legendaryDropPackage.getItemMeta())) {
            type = "legendary";
        } else if (dp.getItemMeta().equals(ultimateDropPackage.getItemMeta())) {
            type = "ultimate";
        } else if (dp.getItemMeta().equals(eliteDropPackage.getItemMeta())) {
            type = "elite";
        } else if (dp.getItemMeta().equals(uniqueDropPackage.getItemMeta())) {
            type = "unique";
        } else if (dp.getItemMeta().equals(simpleDropPackage.getItemMeta())) {
            type = "simple";
        } else {
            return -1;
        }
        return droppackages.getInt(type + ".settings.redeemable-items");
    }

    //
    private String getRandomizationScrollRarity(ItemMeta itemmeta) {
        if (itemmeta.equals(godRandomizationScroll.getItemMeta())) {
            return "god";
        } else if (itemmeta.equals(legendaryRandomizationScroll.getItemMeta())) {
            return "legendary";
        } else if (itemmeta.equals(ultimateRandomizationScroll.getItemMeta())) {
            return "ultimate";
        } else if (itemmeta.equals(eliteRandomizationScroll.getItemMeta())) {
            return "elite";
        } else if (itemmeta.equals(uniqueRandomizationScroll.getItemMeta())) {
            return "unique";
        } else if (itemmeta.equals(simpleRandomizationScroll.getItemMeta())) {
            return "simple";
        } else {
            return null;
        }
    }

    private String getDustRarity(ItemStack itemstack) {
        if (!(itemstack == null) && itemstack.hasItemMeta() && itemstack.getItemMeta().hasDisplayName() && itemstack.getItemMeta().hasLore()) {
            if (itemstack.getItemMeta().getDisplayName().equals(legendaryPrimalDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(legendaryPrimalDust.getType()) && itemstack.getData().getData() == legendaryPrimalDust.getData().getData()) {
                return "legendary-primal";
            } else if (itemstack.getItemMeta().getDisplayName().equals(legendaryRegularDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(legendaryRegularDust.getType()) && itemstack.getData().getData() == legendaryRegularDust.getData().getData()) {
                return "legendary-regular";
            } else if (itemstack.getItemMeta().getDisplayName().equals(ultimatePrimalDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(ultimatePrimalDust.getType()) && itemstack.getData().getData() == ultimatePrimalDust.getData().getData()) {
                return "ultimate-primal";
            } else if (itemstack.getItemMeta().getDisplayName().equals(ultimateRegularDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(ultimateRegularDust.getType()) && itemstack.getData().getData() == ultimateRegularDust.getData().getData()) {
                return "ultimate-regular";
            } else if (itemstack.getItemMeta().getDisplayName().equals(elitePrimalDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(elitePrimalDust.getType()) && itemstack.getData().getData() == elitePrimalDust.getData().getData()) {
                return "elite-primal";
            } else if (itemstack.getItemMeta().getDisplayName().equals(eliteRegularDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(eliteRegularDust.getType()) && itemstack.getData().getData() == eliteRegularDust.getData().getData()) {
                return "elite-regular";
            } else if (itemstack.getItemMeta().getDisplayName().equals(uniquePrimalDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(uniquePrimalDust.getType()) && itemstack.getData().getData() == uniquePrimalDust.getData().getData()) {
                return "unique-primal";
            } else if (itemstack.getItemMeta().getDisplayName().equals(uniqueRegularDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(uniqueRegularDust.getType()) && itemstack.getData().getData() == uniqueRegularDust.getData().getData()) {
                return "unique-regular";
            } else if (itemstack.getItemMeta().getDisplayName().equals(simplePrimalDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(simplePrimalDust.getType()) && itemstack.getData().getData() == simplePrimalDust.getData().getData()) {
                return "simple-primal";
            } else if (itemstack.getItemMeta().getDisplayName().equals(simpleRegularDust.getItemMeta().getDisplayName()) && itemstack.getType().equals(simpleRegularDust.getType()) && itemstack.getData().getData() == simpleRegularDust.getData().getData()) {
                return "simple-regular";
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private ItemStack getDust(String string) {
        string = string.toLowerCase();
        if (string.contains("primal")) {
            if (string.contains("legendary")) {
                return legendaryPrimalDust.clone();
            } else if (string.contains("ultimate")) {
                return ultimatePrimalDust.clone();
            } else if (string.contains("elite")) {
                return elitePrimalDust.clone();
            } else if (string.contains("unique")) {
                return uniquePrimalDust.clone();
            } else if (string.contains("simple")) {
                return simplePrimalDust.clone();
            } else {
                return null;
            }
        } else {
            if (string.contains("legendary")) {
                return legendaryRegularDust.clone();
            } else if (string.contains("ultimate")) {
                return ultimateRegularDust.clone();
            } else if (string.contains("elite")) {
                return eliteRegularDust.clone();
            } else if (string.contains("unique")) {
                return uniqueRegularDust.clone();
            } else if (string.contains("simple")) {
                return simpleRegularDust.clone();
            } else {
                return null;
            }
        }
    }

    private ItemStack checkDropPackageTitle(String string) {
        if (string.equals(godDPInv.getTitle())) {
            return godDropPackage.clone();
        } else if (string.equals(legendaryDPInv.getTitle())) {
            return legendaryDropPackage.clone();
        } else if (string.equals(legendaryDPInv.getTitle())) {
            return ultimateDropPackage.clone();
        } else if (string.equals(legendaryDPInv.getTitle())) {
            return legendaryDropPackage.clone();
        } else if (string.equals(legendaryDPInv.getTitle())) {
            return legendaryDropPackage.clone();
        } else if (string.equals(legendaryDPInv.getTitle())) {
            return legendaryDropPackage.clone();
        } else {
            return null;
        }
    }

    private int checkShopTitle(String string) {
        for (int i = 0; i < 54; i++) {
            if (!(shop.get(i + ".title") == null) && ChatColor.translateAlternateColorCodes('&', shop.getString(i + ".title")).equals(string)) {
                return i;
            }
        }
        return -1;
    }

    private int checkFilterTitle(String string) {
        for (int i = 0; i < 54; i++) {
            if (!(otherGuis.get("filter." + i + ".title") == null) && ChatColor.translateAlternateColorCodes('&', otherGuis.getString("filter." + i + ".title")).equals(string)) {
                return i;
            }
        }
        return -1;
    }

    private ItemStack getEnchanterPurchase(int rawSlot) {
        String enchanterpurchase = otherGuis.getString("enchanter." + rawSlot + ".purchase").toLowerCase();
        if (!(checkStringForRPItem(enchanterpurchase) == null)) {
            item = checkStringForRPItem(enchanterpurchase);
            itemMeta = item.getItemMeta();
            lore.clear();
            return item;
        }
        return null;
    }

    private ItemStack getLeveledUpDust(ItemStack is, String type, int percent) {
        if (type.equals("primal")) {
            if (is.getItemMeta().getDisplayName().equals(ultimatePrimalDust.getItemMeta().getDisplayName())) {
                item = legendaryPrimalDust.clone();
            } else if (is.getItemMeta().getDisplayName().equals(elitePrimalDust.getItemMeta().getDisplayName())) {
                item = ultimatePrimalDust.clone();
            } else if (is.getItemMeta().getDisplayName().equals(uniquePrimalDust.getItemMeta().getDisplayName())) {
                item = elitePrimalDust.clone();
            } else if (is.getItemMeta().getDisplayName().equals(simplePrimalDust.getItemMeta().getDisplayName())) {
                item = uniquePrimalDust.clone();
            } else {
                return null;
            }
        } else {
            if (is.getItemMeta().getDisplayName().equals(ultimateRegularDust.getItemMeta().getDisplayName())) {
                item = legendaryRegularDust.clone();
            } else if (is.getItemMeta().getDisplayName().equals(eliteRegularDust.getItemMeta().getDisplayName())) {
                item = ultimateRegularDust.clone();
            } else if (is.getItemMeta().getDisplayName().equals(uniqueRegularDust.getItemMeta().getDisplayName())) {
                item = eliteRegularDust.clone();
            } else if (is.getItemMeta().getDisplayName().equals(simpleRegularDust.getItemMeta().getDisplayName())) {
                item = uniqueRegularDust.clone();
            } else {
                return null;
            }
        }
        itemMeta = item.getItemMeta();
        lore.clear();
        for (String string : itemMeta.getLore()) {
            if (string.contains("{PERCENT}")) {
                string = string.replace("{PERCENT}", "" + percent);
            }
            lore.add(string);
        }
        itemMeta.setLore(lore);
        lore.clear();
        item.setItemMeta(itemMeta);
        return item;
    }

    private String getLeveledUpDustRarityString(ItemStack is) {
        if (is.getItemMeta().equals(ultimatePrimalDust.getItemMeta())) {
            return "legendary-primal";
        } else if (is.getItemMeta().equals(ultimateRegularDust.getItemMeta())) {
            return "legendary-regular";
        } else if (is.getItemMeta().equals(elitePrimalDust.getItemMeta())) {
            return "ultimate-primal";
        } else if (is.getItemMeta().equals(eliteRegularDust.getItemMeta())) {
            return "ultimate-regular";
        } else if (is.getItemMeta().equals(uniquePrimalDust.getItemMeta())) {
            return "elite-primal";
        } else if (is.getItemMeta().equals(uniqueRegularDust.getItemMeta())) {
            return "elite-regular";
        } else if (is.getItemMeta().equals(simplePrimalDust.getItemMeta())) {
            return "unique-primal";
        } else if (is.getItemMeta().equals(simpleRegularDust.getItemMeta())) {
            return "unique-regular";
        } else {
            return null;
        }
    }

    private int getDustCost(String type, String rarity) {
        if (type.contains("primal")) {
            return masterconfig.getInt("givedp-items." + rarity + ".primal.upgrade-cost");
        } else {
            return masterconfig.getInt("givedp-items." + rarity + ".regular.upgrade-cost");
        }
    }

    //
    @EventHandler
    private void inventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals(givedpDropPackagesInv.getTitle())
                || event.getInventory().getTitle().equals(givedpFallenHeroesInv.getTitle())
                || event.getInventory().getTitle().equals(givedpGivedpInv.getTitle())
                || event.getInventory().getTitle().equals(givedpRarityBooksInv.getTitle())
                || event.getInventory().getTitle().equals(givedpSoulTrackerInv.getTitle())
                ) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                public void run() {
                    if (((Player) event.getPlayer()).isOnline()) {
                        openGui((Player) event.getPlayer(), RPGui.GIVEDP);
                    }
                }
            }, 2);
        } else if (event.getInventory().getTitle().equals(tinkererInv.getTitle())) {
            String type = null;
            if (tinkerer_accept == true) {
                type = "accepted";
            } else {
                type = "cancelled";
                item = tinkererAccept.clone();
                for (int i = 0; i < event.getInventory().getSize(); i++) {
                    if (!(event.getInventory().getItem(i) == null) && event.getInventory().getItem(i).hasItemMeta()) {
                        if (event.getInventory().getItem(i).getItemMeta().hasDisplayName() && !(getOriginalEnchantmentName(event.getInventory().getItem(i).getItemMeta().getDisplayName()) == null)
                                || event.getInventory().getItem(i).getType().name().endsWith("HELMET") || event.getInventory().getType().name().endsWith("CHESTPLATE") || event.getInventory().getType().name().endsWith("LEGGINGS") || event.getInventory().getItem(i).getType().name().endsWith("BOOTS")
                                || event.getInventory().getItem(i).getType().name().endsWith("AXE") || event.getInventory().getItem(i).getType().name().endsWith("SWORD") || event.getInventory().getItem(i).getType().name().endsWith("SPADE") || event.getInventory().getItem(i).getType().equals(Material.BOW)
                                || event.getInventory().getItem(i).getType().name().endsWith("HOE")) {
                            giveItem((Player) event.getPlayer(), event.getInventory().getItem(i));
                        }
                    }
                }
            }
            sendStringListMessage((Player) event.getPlayer(), "tinkerer." + type, 0);
            getAndPlaySound((Player) event.getPlayer(), event.getPlayer().getLocation(), "tinkerer." + type, false);
            tinkerer_accept = false;
        } else if (event.getInventory().getTitle().equals(alchemistInv.getTitle()) && alchemist_accept == false) {
            if (!(event.getInventory().getItem(3) == null)) {
                giveItem((Player) event.getPlayer(), event.getInventory().getItem(3));
            }
            if (!(event.getInventory().getItem(5) == null)) {
                giveItem((Player) event.getPlayer(), event.getInventory().getItem(5));
            }
        } else if (!(checkDropPackageTitle(event.getInventory().getTitle()) == null)) {
            if (revealloot.contains(event.getPlayer())) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                    public void run() {
                        if (((Player) event.getPlayer()).isOnline() == true) {
                            event.getPlayer().openInventory(event.getInventory());
                        }
                    }
                }, 2);
            } else if (claimloot.contains(event.getPlayer())) {
                removeItem((Player) event.getPlayer(), checkDropPackageTitle(event.getInventory().getTitle()), 1);
                for (int i = 0; i < 54; i++) {
                    if (selectloot.contains(event.getPlayer().getName() + i)) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
                            public void run() {
                                if (((Player) event.getPlayer()).isOnline() == true) {
                                    event.getPlayer().openInventory(event.getInventory());
                                }
                            }
                        }, 2);
                        return;
                    }
                }
                claimloot.remove(event.getPlayer());
                for (ItemStack is : event.getInventory().getContents()) {
                    if (!(is == null) && !(is.getType().equals(Material.AIR))) {
                        giveItem((Player) event.getPlayer(), is);
                    }
                }
            }
        }
        ((Player) event.getPlayer()).updateInventory();
        return;
    }

    //
    @SuppressWarnings("deprecation")
    private void playSuccess(Player player) {
        getAndPlaySound(player, player.getLocation(), "success-and-destroy.success", false);
        for (int i = 1; i <= 15; i++) {
            player.getWorld().playEffect(player.getEyeLocation(), Effect.SPELL, 1);
        }
    }

    @SuppressWarnings("deprecation")
    private void playDestroy(Player player) {
        getAndPlaySound(player, player.getLocation(), "success-and-destroy.destroy", false);
        for (int i = 1; i <= 15; i++) {
            player.getWorld().playEffect(player.getEyeLocation(), Effect.LAVA_POP, 1);
        }
    }

    /*
	 *
	 */
    private void editShowcaseItem(InventoryClickEvent event, Player player, boolean addItem) {
        String type = null;
        lore.clear();
        if (addItem == true) {
            type = "add-item";
        } else {
            type = "remove-item";
        }
        File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "player-data" + File.separator, player.getUniqueId().toString() + ".yml");
        YamlConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
        int slot = 0;
        for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
            if (!(otherGuis.get("showcase." + type + "." + i + ".item") == null)
                    && otherGuis.getString("showcase." + type + "." + i + ".item").equalsIgnoreCase("{ITEM}")) {
                slot = i;
            }
        }
        item = event.getWhoClicked().getOpenInventory().getTopInventory().getItem(slot);
        itemMeta = item.getItemMeta();
        if (item.hasItemMeta() && itemMeta.hasLore()) {
            lore.addAll(itemMeta.getLore());
        }
        if (addItem == true) {
            player.getInventory().remove(item);
            DateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
            Date date = new Date();
            lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + df.format(date));
            itemMeta.setLore(lore);
            lore.clear();
            item.setItemMeta(itemMeta);
        }
        for (int i = 0; i < fileConfig.getInt("showcase.permission-size"); i++) {
            if (type.equals("remove-item") && !(fileConfig.get("showcase.items." + i) == null) && fileConfig.getItemStack("showcase.items." + i).equals(item)
                    || type.equals("add-item") && fileConfig.get("showcase.items." + i) == null
                    || type.equals("add-item") && !(fileConfig.get("showcase.items." + i) == null) && fileConfig.getItemStack("showcase.items." + i).getType().equals(Material.AIR)) {
                if (type.equals("remove-item")) {
                    item = new ItemStack(Material.AIR);
                }
                fileConfig.set("showcase.items." + i, item);
                try {
                    fileConfig.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                api.openShowcase(player, null);
                return;
            }
        }
    }

    //
    @EventHandler
    private void pluginDisableEvent(PluginDisableEvent event) {
        if (event.getPlugin().equals(RandomPackage.getPlugin) && !(selectloot.isEmpty())
                || event.getPlugin().equals(RandomPackage.getPlugin) && !(claimloot.isEmpty())) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (claimloot.contains(player)) {
                    claimloot.remove(player);
                    revealloot.remove(player);
                    player.closeInventory();
                    player.updateInventory();
                    player.sendMessage(RandomPackage.prefix + ChatColor.YELLOW + "You have exited the drop package due to the server reloading.");
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void useoldversion(InventoryClickEvent event, String rarity, int selectedAmount, int itemsneeded) {
        if (claimloot.contains(event.getWhoClicked())) {
            File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "drop packages" + File.separator, rarity + ".yml");
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            if (selectloot.contains(event.getWhoClicked().getName() + event.getRawSlot())) {
                int amountofitems = -1;
                for (int i = 1; i <= 300; i++) {
                    if (!(config.get(rarity + "." + i + ".item") == null)) {
                        amountofitems = i;
                    }
                }
                if (random.nextInt(amountofitems) < 0) {
                    return;
                }
                int randomitem = random.nextInt(amountofitems) + 1;
                String configitem = config.getString(rarity + "." + randomitem + ".item").toLowerCase();
                if (!(convertStringToRPItem(configitem) == null)) {
                    item = convertStringToRPItem(configitem).clone();
                } else {
                    item = getConfigItem(config, rarity + "." + randomitem).clone();
                }
                event.setCurrentItem(item);
                selectloot.remove(event.getWhoClicked().getName() + event.getRawSlot());
            }
        } else if (event.getCurrentItem().getItemMeta().equals(dropPackageSelected.getItemMeta())) {
            selectloot.remove(event.getWhoClicked().getName() + event.getRawSlot());
            if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(godDPInv.getTitle())) {
                item = godDPOpenGuiItem.clone();
            } else if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(legendaryDPInv.getTitle())) {
                item = legendaryDPOpenGuiItem.clone();
            } else if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(ultimateDPInv.getTitle())) {
                item = ultimateDPOpenGuiItem.clone();
            } else if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(eliteDPInv.getTitle())) {
                item = eliteDPOpenGuiItem.clone();
            } else if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(uniqueDPInv.getTitle())) {
                item = uniqueDPOpenGuiItem.clone();
            } else if (event.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals(simpleDPInv.getTitle())) {
                item = simpleDPOpenGuiItem.clone();
            } else {
                return;
            }
            item.setAmount(event.getRawSlot() + 1);
            event.setCurrentItem(item);
        } else if (!(selectloot.contains(event.getWhoClicked().getName() + event.getRawSlot()))) {
            selectloot.add(event.getWhoClicked().getName() + event.getRawSlot());
            for (ItemStack is : event.getWhoClicked().getOpenInventory().getTopInventory()) {
                if (!(is == null) && is.hasItemMeta() && is.getItemMeta().hasDisplayName() && is.getItemMeta().hasLore()) {
                    if (is.getItemMeta().equals(dropPackageSelected.getItemMeta())) {
                        selectedAmount++;
                    }
                }
            }
            boolean randomizeloot = false;
            if (selectedAmount + 1 >= itemsneeded) {
                randomizeloot = true;
                revealloot.add((Player) event.getWhoClicked());
            }
            if (selectedAmount + 1 <= itemsneeded) {
                item = dropPackageSelected.clone();
                item.setAmount(event.getRawSlot() + 1);
                event.setCurrentItem(item);
            }
            if (randomizeloot == true) {
                for (int i = 1; i <= 20; i++) {
                    removeAfew((Player) event.getWhoClicked(), rarity, i);
                }
            }
        }
    }

    private ItemStack convertStringToRPItem(String string) {
        int number = 0;
        string = string.toLowerCase();
        if (string.equals("armorenchantmentorb")) {
            return armorenchantmentorb.clone();
        } else if (string.equals("banknote")) {
            return banknote.clone();
        } else if (string.equals("blackscroll") && string.contains(":")) {
            item = blackscroll.clone();
            if (string.contains(":")) {
                number = Integer.parseInt(string.split(":")[1]);
            } else {
                number = getRandomBlackScrollPercent();
            }
        } else if (string.equals("christmascandy")) {
            return christmascandy.clone();
        } else if (string.equals("christmaseggnog")) {
            return christmaseggnog.clone();
        } else if (string.equals("experiencebottle") && string.contains(":")) {
            String thing = string.split(":")[1];
            item = experiencebottle.clone();
            if (string.contains("-")) {
                number = (Integer.parseInt(thing.split("-")[0]) + random.nextInt(1 + Integer.parseInt(thing.split("-")[1]) - Integer.parseInt(thing.split("-")[0])));
            } else {
                number = Integer.parseInt(thing);
            }
        } else if (string.equals("explosivecake")) {
            return explosivecake.clone();
        } else if (string.equals("factioncrystal")) {
            return factioncrystal.clone();
        } else if (string.equals("factionmcmmobooster")) {
            item = factionmcmmobooster.clone();
        } else if (string.equals("factionxpbooster")) {
            item = factionxpbooster.clone();
        } else if (string.equals("godrandomizationscroll")) {
            return godRandomizationScroll.clone();
        } else if (string.equals("halloweencandy")) {
            return halloweencandy.clone();
        } else if (string.equals("itemnametag")) {
            return itemnametag.clone();
        } else if (string.startsWith("mcmmocreditvoucher") && string.contains(":")) {
            item = mcmmovoucherCredit.clone();
        } else if (string.startsWith("mcmmolevelvoucher") && string.contains(":")) {
            item = mcmmovoucherLevel.clone();
        } else if (string.startsWith("mcmmoxpvoucher") && string.contains(":")) {
            item = mcmmovoucherXP.clone();
        } else if (string.equals("mysterydust")) {
            return mysterydust.clone();
        } else if (string.equals("mysterymobspawner")) {
            return mysterymobspawner.clone();
        } else if (string.equals("randombook")) {
            return randombook.clone();
        } else if (string.equals("sb") && string.contains(":")) {
            int level = -1, success = -1, destroy = -1;
            String OEN = getOriginalEnchantmentName(string.split(":")[1]), rarity = masterconfig.getString(OEN + ".rarity").toLowerCase();
            if (string.split(":")[2].equalsIgnoreCase("random")) {
                level = 1 + random.nextInt(enchantmentsInfo.getInt(OEN + ".max-level"));
            } else {
                level = Integer.parseInt(string.split(":")[2]);
            }
            if (string.split(":")[3].equalsIgnoreCase("random")) {
                success = random.nextInt(101);
            } else {
                success = Integer.parseInt(string.split(":")[3]);
            }
            if (string.split(":")[4].equalsIgnoreCase("random")) {
                destroy = random.nextInt(101);
            } else {
                destroy = Integer.parseInt(string.split(":")[4]);
            }
            itemMeta.setLore(masterconfig.getStringList("givedp-items." + rarity + ".revealed-item.lore"));
            item.setItemMeta(itemMeta);
            item = formatBookLore(item, OEN, success, destroy);
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.name").replace("{ENCHANT}", enchantmentsInfo.getString(OEN + ".enchant-name") + " " + replaceIntWithRomanNumerals(level))));
            item.setItemMeta(itemMeta);
        } else if (string.startsWith("soulgem") && string.contains(":")) {
            item = soulgem.clone();
            number = Integer.parseInt(string.split(":")[1]);
        } else if (string.startsWith("spacedrink")) {
            return spacedrink.clone();
        } else if (string.startsWith("spacefirework")) {
            return spacefirework.clone();
        } else if (string.startsWith("transmogscroll")) {
            return transmogscroll.clone();
        } else if (string.startsWith("weaponenchantmentorb")) {
            item = weaponenchantmentorb.clone();
        } else if (string.equals("whitescroll")) {
            return whitescroll.clone();
        } else {
            return null;
        }
        //
        itemMeta = item.getItemMeta();
        lore.clear();
        if (item.equals(soulgem)) {
            itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{SOULS}", Integer.toString(number)));
        }
        for (String strinG : itemMeta.getLore()) {
            if (string.startsWith("faction") && strinG.contains("{MULTIPLIER}")) {
                strinG = strinG.replace("{MULTIPLIER}", "" + getRemainingDoubleFromString(string.split(":")[1]));
            } else if (string.startsWith("faction") && strinG.contains("{MINUTES}")) {
                strinG = strinG.replace("{MINUTES}", "" + getRemainingDoubleFromString(string.split(":")[2]));
            } else if (string.startsWith("blackscroll") && strinG.contains("{PERCENT}")) {
                strinG = strinG.replace("{PERCENT}", "" + number);
            } else if (string.startsWith("experiencebottle") && strinG.contains("{AMOUNT}")) {
                strinG = strinG.replace("{AMOUNT}", "" + number);
            } else if (string.startsWith("mcmmoc") && strinG.contains("{CREDITS}")) {
                strinG = strinG.replace("{CREDITS}", "" + Integer.parseInt(string.split(":")[1]));
            } else if (string.startsWith("mcmmol") && strinG.contains("{SKILL}") && !(RandomPackage.mcmmo == "&cMCMMO") || string.startsWith("mcmmox") && strinG.contains("{SKILL}") && !(RandomPackage.mcmmo == "&cMCMMO")) {
                strinG = strinG.replace("{SKILL}", mcMMOEvents.getInstance().getSkillName(mcMMOEvents.getInstance().getSkill(string.split(":")[1])));
            } else if (string.startsWith("mcmmol") && strinG.contains("{LEVELS}")) {
                strinG = strinG.replace("{SKILL}", "" + Integer.parseInt(string.split(":")[2]));
            } else if (string.startsWith("mcmmox") && strinG.contains("{XP}")) {
                strinG = strinG.replace("{XP}", "" + Integer.parseInt(string.split(":")[2]));
            } else if (string.startsWith("mcmmo") && strinG.contains("{PLAYER}")) {
                strinG = strinG.replace("{PLAYER}", "Drop Package");
            }
            lore.add(ChatColor.translateAlternateColorCodes('&', strinG));
        }
        itemMeta.setLore(lore);
        lore.clear();
        item.setItemMeta(itemMeta);
        return item;
    }

    private void usenewversion(InventoryClickEvent event, int dp, int selectedAmount, int itemsneeded) {

    }

    private int[] getDropPackageLootChances(String rarity) {
        if (rarity.equals("god")) {
            return god;
        } else if (rarity.equals("legendary")) {
            return legendary;
        } else if (rarity.equals("ultimate")) {
            return ultimate;
        } else if (rarity.equals("elite")) {
            return elite;
        } else if (rarity.equals("unique")) {
            return unique;
        } else {
            return simple;
        }
    }

    private ItemStack getDropPackageDisplayItem(int rarity) {
        if (rarity == 0) {
            return godDPDisplayItem.clone();
        } else if (rarity == 1) {
            return legendaryDPDisplayItem.clone();
        } else if (rarity == 2) {
            return ultimateDPDisplayItem.clone();
        } else if (rarity == 3) {
            return eliteDPDisplayItem.clone();
        } else if (rarity == 4) {
            return uniqueDPDisplayItem.clone();
        } else {
            return simpleDPDisplayItem.clone();
        }
    }

    private void removeAfew(Player player, String rarity, int delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(RandomPackage.getPlugin, new Runnable() {
            public void run() {
                if (delay == 20) {
                    claimloot.add(player);
                    revealloot.remove(player);
                    for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
                        if (!(player.getOpenInventory().getTopInventory().getItem(i) == null) && !(player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.AIR)) && !(selectloot.contains(player.getName() + i))) {
                            player.getOpenInventory().getTopInventory().setItem(i, new ItemStack(Material.AIR));
                        }
                    }
                }
                if (!(player.isOnline()) || !(revealloot.contains(player))) {
                    return;
                }
                getAndPlaySound(player, player.getLocation(), "drop-packages.randomize-loot", false);
                int[] chances = getDropPackageLootChances(rarity);

                int last = -1;
                for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
                    if (!(player.getOpenInventory().getTopInventory().getItem(i) == null) && !(player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.AIR))) {
                        item = null;
                        for (int z = 0; z <= 5; z++) {
                            if (item == null) {
                                if (random.nextInt(100) <= chances[z]) {
                                    item = getDropPackageDisplayItem(z).clone();
                                } else if (z == 5) {
                                    item = simpleDPDisplayItem.clone();
                                }
                            }
                        }
                        player.getOpenInventory().getTopInventory().setItem(i, item);
                    }
                }
                for (int i = 0; i < player.getOpenInventory().getTopInventory().getSize(); i++) {
                    if (!(player.getOpenInventory().getTopInventory().getItem(i) == null) && !(player.getOpenInventory().getTopInventory().getItem(i).getType().equals(Material.AIR))) {
                        if (random.nextInt(100) <= 24 && !(selectloot.contains(player.getName() + i))) {
                            player.getOpenInventory().getTopInventory().setItem(i, new ItemStack(Material.AIR));
                        } else if (selectloot.contains(player.getName() + i)) {
                            item = null;
                            for (int z = 0; z <= 4; z++) {
                                if (item == null) {
                                    if (random.nextInt(100) <= chances[z]) {
                                        item = getDropPackageDisplayItem(z).clone();
                                    } else if (z == 4) {
                                        item = simpleDPDisplayItem.clone();
                                    }
                                }
                            }
                            itemMeta = item.getItemMeta();
                            lore.clear();
                            if (itemMeta.hasLore()) {
                                for (String string : itemMeta.getLore()) {
                                    if (string.contains("{DP_TYPE}")) {
                                        string = string.replace("{DP_TYPE}", droppackages.getString(rarity + ".display-item.dp-type"));
                                    }
                                    lore.add(ChatColor.translateAlternateColorCodes('&', string));
                                }
                            }
                            itemMeta.setLore(lore);
                            lore.clear();
                            item.setItemMeta(itemMeta);
                            player.getOpenInventory().getTopInventory().setItem(i, item);
                        }
                    }
                }
                player.updateInventory();
            }
        }, delay * 3);
    }

    private String getSoulGemColors(int soulgemAmount) {
        if (soulgemAmount < 100) {
            return ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.soul-gem.colors.less-than-100"));
        } else {
            int last = 0;
            for (int i = 0; i <= 60000000; i += 100) {
                if (!(masterconfig.get("givedp-items.soul-gem.colors." + i + "s") == null)) {
                    last = i;
                }
                if (soulgemAmount >= last && soulgemAmount <= (i + 100)) {
                    return ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.soul-gem.colors." + last + "s"));
                }
            }
        }
        return ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.soul-gem.colors.else"));
    }

    private String getOriginalEnchantmentName(String string) {
        if (!(enchantmentsInfo.get(string + ".enchant-name") == null)) {
            return string;
        } else {
            string = ChatColor.stripColor(string);
            for (RandomPackageEnchants enchant : RandomPackageEnchants.values()) {
                if (string.toLowerCase().replace(" ", "").startsWith(enchant.name().toLowerCase())
                        || string.toLowerCase().replace(" ", "").startsWith(enchantmentsInfo.getString(enchant.name() + ".enchant-name").toLowerCase().replace(" ", ""))) {
                    return enchant.name();
                }
            }
        }
        return null;
    }

    public int getEnchantTinkererXp(Enchantment enchant, int level) {
        return enchantmentsInfo.getInt("vanilla." + enchant.getName().toLowerCase() + "." + level);
    }

    public String getEnchantmentType(String OEN) {
        return enchantmentsInfo.getString(OEN + ".enchant-type").toLowerCase();
    }

    public String getEnchantmentRarity(String string) {
        if (!(getOriginalEnchantmentName(string) == null)) {
            return enchantmentsInfo.getString(getOriginalEnchantmentName(string) + ".rarity").toLowerCase();
        } else {
            return null;
        }
    }

    public List<String> getBookLore(String OEN) {
        return enchantmentsInfo.getStringList(OEN + ".book-lore");
    }

    public ItemStack getConfigItem(FileConfiguration config, String path) {
        if (!(config.get(path + ".item") == null)) {
            if (Material.getMaterial(config.getString(path + ".item").split(":")[0].toUpperCase()) == null) {
                Bukkit.getConsoleSender().sendMessage("[RandomPackage] &cInvalid item name in config " + config.getName() + ", at " + path + ".item!");
            } else if (config.getString(path + ".item").contains(":")) {
                if (config.getString(path + ".item").toLowerCase().startsWith("potion:")) {
                    item = convertStringToPotion(config.getString(path + ".item"), false);
                } else {
                    item = new ItemStack(Material.getMaterial(config.getString(path + ".item").split(":")[0].toUpperCase()), 1, (byte) Integer.parseInt(config.getString(path + ".item").split(":")[1]));
                }
            } else {
                item = new ItemStack(Material.getMaterial(config.getString(path + ".item").toUpperCase()), 1, (byte) 0);
            }
        }
        itemMeta = item.getItemMeta();
        lore.clear();
        if (!(config.get(path + ".name") == null)) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString(path + ".name")));
        }
        if (!(config.get(path + ".lore") == null)) {
            for (String string : config.getStringList(path + ".lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', string));
            }
            itemMeta.setLore(lore);
            lore.clear();
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack convertStringToPotion(String string, boolean hidePotionEffects) {
        String pathh = string.toLowerCase();
        if (pathh.startsWith("potion:")) {
            pathh = pathh.replace("potion:", "");
            PotionType type = null;
            boolean splash = false, extended = false, upgraded = false;
            int level = 0;
            if (pathh.startsWith("splash")) {
                pathh = pathh.replace("splash", "");
                splash = true;
            }
            if (pathh.endsWith("extended")) {
                pathh = pathh.replace("extended", "");
                extended = true;
            }
            if (Bukkit.getVersion().contains("1.8")) {
                level = getRemainingIntFromString(pathh);
                if (!(level == 1) && !(level == 2)) {
                    level = 1;
                }
            }
            if (getRemainingIntFromString(pathh) == 2) {
                upgraded = true;
            }
            pathh = pathh.replace("1", "").replace("2", "");
            if (pathh.equals("fireresistance")) {
                type = PotionType.FIRE_RESISTANCE;
            } else if (pathh.equals("instantdamage")) {
                type = PotionType.INSTANT_DAMAGE;
            } else if (pathh.equals("instanthealth")) {
                type = PotionType.INSTANT_HEAL;
            } else if (pathh.equals("invisibility")) {
                type = PotionType.INVISIBILITY;
            } else if (pathh.equals("leaping")) {
                type = PotionType.JUMP;
            } else if (pathh.equals("nightvision")) {
                type = PotionType.NIGHT_VISION;
            } else if (pathh.equals("poison")) {
                type = PotionType.POISON;
            } else if (pathh.equals("regeneration")) {
                type = PotionType.REGEN;
            } else if (pathh.equals("slowness")) {
                type = PotionType.SLOWNESS;
            } else if (pathh.equals("swiftness")) {
                type = PotionType.SPEED;
            } else if (pathh.equals("strength")) {
                type = PotionType.STRENGTH;
            } else if (pathh.equals("water") || pathh.equals("0")) {
                type = PotionType.WATER;
            } else if (pathh.equals("waterbreathing")) {
                type = PotionType.WATER_BREATHING;
            } else if (pathh.equals("weakness")) {
                type = PotionType.WEAKNESS;
            } else {
                Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[GlobalAPI]" + ChatColor.RESET + " Invalid potion name: " + pathh);
                return new ItemStack(Material.AIR);
            }
            if (!(Bukkit.getVersion().contains("1.8")) && splash == true) {
                item = new ItemStack(Material.valueOf("SPLASH_POTION"), 1);
            } else {
                item = new ItemStack(Material.POTION, 1);
            }
            if (Bukkit.getVersion().contains("1.8")) {
                Potion potion = null;
                if (!(type.equals(PotionType.WATER))) {
                    potion = new Potion(type, level, splash, extended);
                } else {
                    potion = new Potion(type);
                }
                item = potion.toItemStack(1);
            }
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            if (!(Bukkit.getVersion().contains("1.8"))) {
                v1_9.setupPotion(meta, type, extended, upgraded);
            }
            if (hidePotionEffects == true) {
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            }
            item.setItemMeta(meta);
        } else {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "[GlobalAPI]" + ChatColor.RED + " Invalid string potion format: \"" + pathh + "\"");
            return new ItemStack(Material.AIR);
        }
        return item;
    }

    public ItemStack formatBookLore(ItemStack is, String enchant, int successPercent, int destroyPercent) {
        if (!(is == null) && is.hasItemMeta() && is.getItemMeta().hasLore() && is.getItemMeta().hasDisplayName() && !(enchant == null)) {
            itemMeta = is.getItemMeta();
            lore.clear();
            itemMeta.setDisplayName(enchant);
            for (String string : itemMeta.getLore()) {
                if (string.equals("{SUCCESS}")) {
                    string = SUCCESS.replace("{PERCENT}", "" + successPercent);
                } else if (string.equals("{DESTROY}")) {
                    string = DESTROY.replace("{PERCENT}", "" + destroyPercent);
                } else if (string.equals("{APPLY}")) {
                    string = APPLY_TO_ITEM;
                } else if (string.equals("{BOOK_LORE}")) {
                    for (String booklore : getBookLore(getOriginalEnchantmentName(enchant))) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', booklore));
                    }
                } else if (string.equals("{ENCHANT_TYPE}")) {
                    string = masterconfig.getString("givedp-items.enchant-types." + getEnchantmentType(enchant));
                }
                //
                if (!(string.equals("{BOOK_LORE}"))) {
                    lore.add(ChatColor.translateAlternateColorCodes('&', string));
                }
            }
            itemMeta.setLore(lore);
            lore.clear();
            is.setItemMeta(itemMeta);
            return is;
        } else {
            return null;
        }
    }

    private void giveSpawner(Player player, String directory, FileConfiguration config) {
        String pi = null, path = null, type = null;
        if (!(config == null)) {
            pi = config.getString(directory);
        } else {
            pi = directory;
        }
        if (pi.equalsIgnoreCase("igspawner")) {
            path = "iron-golem";
            type = "VillagerGolem";
        } else if (pi.equalsIgnoreCase("blazespawner")) {
            path = "blaze";
        } else if (pi.equalsIgnoreCase("creeperspawner")) {
            path = "creeper";
        } else if (pi.equalsIgnoreCase("endermanspawner")) {
            path = "enderman";
        } else if (pi.equalsIgnoreCase("zombiepigmanspawner")) {
            path = "zombie-pigman";
            type = "PigZombie";
        } else if (pi.equalsIgnoreCase("magmacubespawner")) {
            path = "magma-cube";
            type = "LavaSlime";
        } else if (pi.equalsIgnoreCase("mushroomcowspawner")) {
            path = "mushroom-cow";
            type = "MushroomCow";
        } else if (pi.equalsIgnoreCase("slimespawner")) {
            path = "slime";
        } else if (pi.equalsIgnoreCase("cavespiderspawner")) {
            path = "cave-spider";
            type = "CaveSpider";
        } else if (pi.equalsIgnoreCase("spiderspawner")) {
            path = "spider";
        } else if (pi.equalsIgnoreCase("ghastspawner")) {
            path = "ghast";
        } else if (pi.equalsIgnoreCase("skeletonspawner")) {
            path = "skeleton";
        } else if (pi.equalsIgnoreCase("zombiespawner")) {
            path = "zombie";
        } else if (pi.equalsIgnoreCase("cowspawner")) {
            path = "cow";
        } else if (pi.equalsIgnoreCase("sheepspawner")) {
            path = "sheep";
        } else if (pi.equalsIgnoreCase("chickenspawner")) {
            path = "chicken";
        } else if (pi.equalsIgnoreCase("pigspawner")) {
            path = "pig";
        } else if (pi.equalsIgnoreCase("wolfspawner")) {
            path = "wolf";
        } else if (pi.equalsIgnoreCase("mysterymobspawner")) {
            giveItem(player, mysterymobspawner.clone());
            return;
        }
        if (type == null) {
            type = path.substring(0, 1).toUpperCase() + path.substring(1, path.length()).toLowerCase();
        }
        String lore = masterconfig.getStringList("givedp-items.mystery-mob-spawner.revealed-spawner-lore").toString().substring(1, masterconfig.getStringList("givedp-items.mystery-mob-spawner.revealed-spawner-lore").toString().length() - 1), formattedSpawnerName = path;
        if (path.contains("-")) {
            int hyphen = 0;
            for (int i = 0; i < path.length(); i++) {
                if (path.substring(i, i + 1).equals("-")) {
                    hyphen = i;
                }
            }
            formattedSpawnerName = path.substring(0, 1).toUpperCase() + path.substring(1, hyphen) + " " + path.substring(hyphen + 1, hyphen + 2).toUpperCase() + path.substring(hyphen + 2, path.length());
        } else {
            formattedSpawnerName = path.substring(0, 1).toUpperCase() + path.substring(1);
        }
        if (lore.contains("{SPAWNER_TYPE}")) {
            lore = lore.replace("{SPAWNER_TYPE}", formattedSpawnerName);
        }
        lore = ChatColor.translateAlternateColorCodes('&', lore);
        if (!(Bukkit.getVersion().contains("1.8"))) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:" + type + "}}, display:{Name:" + ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.mystery-mob-spawner.spawner-names." + path)) + ",Lore:[\"" + lore + "\"]}}");
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " mob_spawner 1 0 {BlockEntityTag:{EntityId:" + type + "}, display:{Name:" + ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.mystery-mob-spawner.spawner-names." + path)) + "}}");
        }
        return;
    }

    private int getItemAmountInPlayerInventory(Player player, Material material) {
        int amount = 0;
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            if (!(player.getInventory().getItem(i) == null) && player.getInventory().getItem(i).getType().equals(material)) {
                amount = amount + player.getInventory().getItem(i).getAmount();
            }
        }
        return amount;
    }

    public void giveGkit(Player player, int slot, boolean preview) {
        if (preview) {
            player.openInventory(Bukkit.createInventory(player, 18, GKIT_PREVIEW));
        }
        ItemStack[] gkit = getGkitItems(slot).clone();
        for (int i = 0; i < gkit.length; i++) {
            if (!(gkit[i] == null) && !(gkit[i].getType().equals(Material.AIR))) {
                item = gkit[i].clone();
                itemMeta = item.getItemMeta();
                lore.clear();
                if (!(checkGkitVkit(item, slot, i) == null)) {
                    item = checkGkitVkit(item, slot, i);
                } else if (item.getType().name().endsWith("HELMET") || item.getType().name().endsWith("CHESTPLATE") || item.getType().name().endsWith("LEGGINGS") || item.getType().name().endsWith("BOOTS")
                        || item.getType().name().endsWith("SWORD") || item.getType().name().endsWith("AXE") || item.getType().name().endsWith("SPADE") || item.getType().name().endsWith("HOE") || item.getType().name().endsWith("BOW")) {
                    if (preview) {
                        addVanillaAndRandomPackageEnchants(player, item, true, -1);
                    } else {
                        addVanillaAndRandomPackageEnchants(player, item, false, -1);
                    }
                }
                if (!(gkits.get(slot + ".items." + i + ".amount") == null)) {
                    item.setAmount(gkits.getInt(slot + ".items." + i + ".amount"));
                }
                if (preview) {
                    player.getOpenInventory().getTopInventory().addItem(item);
                } else {
                    giveItem(player, item);
                }
            }
        }
        player.updateInventory();
    }

    public void giveVkit(Player player, int slot, boolean preview) {
        if (preview) {
            player.openInventory(Bukkit.createInventory(player, 18, VKIT_PREVIEW));
        }
        ItemStack[] vkit = getVkitItems(slot).clone();
        for (int i = 0; i < vkit.length; i++) {
            if (!(vkit[i] == null)) {
                item = vkit[i].clone();
                itemMeta = item.getItemMeta();
                lore.clear();
                if (!(checkGkitVkit(item, slot, i) == null)) {
                    item = checkGkitVkit(item, slot, i);
                } else if (item.getType().name().endsWith("HELMET") || item.getType().name().endsWith("CHESTPLATE") || item.getType().name().endsWith("LEGGINGS") || item.getType().name().endsWith("BOOTS")
                        || item.getType().name().endsWith("SWORD") || item.getType().name().endsWith("AXE") || item.getType().name().endsWith("SPADE") || item.getType().name().endsWith("HOE") || item.getType().name().endsWith("BOW")) {
                    if (preview == true) {
                        addVanillaAndRandomPackageEnchants(player, item, true, slot);
                    } else {
                        addVanillaAndRandomPackageEnchants(player, item, false, slot);
                    }
                }
                if (preview == true) {
                    player.getOpenInventory().getTopInventory().setItem(player.getOpenInventory().getTopInventory().firstEmpty(), item);
                } else {
                    giveItem(player, item);
                }
            }
        }
        player.updateInventory();
        //
    }

    public void addVanillaAndRandomPackageEnchants(Player player, ItemStack is, boolean maxlevel, int rawSlot) {
        if (!(is == null) && is.hasItemMeta() && is.getItemMeta().hasLore()) {
            itemMeta = is.getItemMeta();
            lore.clear();
            HashMap<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
            for (String string : is.getItemMeta().getLore()) {
                if (string.toLowerCase().startsWith("e:") && !(getEnchantmentType(string.substring(2)) == null)) {
                    enchants.put(getEnchantment(string.substring(2)), getRemainingIntFromString(string));
                } else if (string.startsWith("{") && string.endsWith("}") && !(string.contains(":"))) {
                    string = string.substring(1, string.length() - 1);
                    if (!(getOriginalEnchantmentName(string) == null)) {
                        String OEN = getOriginalEnchantmentName(string);
                        int enchantlevel = enchantmentsInfo.getInt(OEN + ".max-level");
                        if (!maxlevel) {
                            enchantlevel = random.nextInt(enchantlevel) + 1;
                        }
                        lore.add(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + getEnchantmentRarity(OEN) + ".revealed-item.apply-format").replace("{ENCHANT}", enchantmentsInfo.getString(OEN + ".enchant-name") + " " + replaceIntWithRomanNumerals(enchantlevel))));
                    } else {
                        Bukkit.broadcastMessage(configPrefix + "Invalid gkit enchantment name: " + ChatColor.RED + string);
                    }
                } else if (string.startsWith("{") && string.contains("}") && string.contains(":")) {
                    String enchant = null;
                    if (string.endsWith("}")) {
                        if (random.nextInt(100) <= 50) {
                            enchant = getOriginalEnchantmentName(string.split(":")[1].substring(1, string.split(":")[1].length() - 1));
                        } else {
                            enchant = getOriginalEnchantmentName(string.split(":")[0].substring(1, string.split(":")[0].length() - 1));
                        }
                    } else {
                        int reqlevel = -1, chance = -1, level = -1;
                        for (int i = 2; i <= 4; i++) {
                            if (string.split(":").length == i) {
                                for (int z = 1; z < i; z++) {
                                    if (string.split(":")[z].toLowerCase().startsWith("reqlevel=")) {
                                        reqlevel = Integer.parseInt(string.split(":")[z].substring(9));
                                    } else if (string.split(":")[z].toLowerCase().startsWith("chance=")) {
                                        chance = Integer.parseInt(string.split(":")[z].substring(7));
                                    } else if (string.split(":")[z].toLowerCase().startsWith("level=")) {
                                        level = Integer.parseInt(string.split(":")[z].substring(6));
                                    }
                                }
                            }
                        }
                        if (!(chance == -1) && random.nextInt(100) <= chance || chance == -1) {
                            if (!(reqlevel == -1) && !(getPlayerVkitLevel(player, rawSlot) == -1) && getPlayerVkitLevel(player, rawSlot) >= reqlevel) {
                                enchant = getOriginalEnchantmentName(string.split(":")[0].replace("{", "").replace("}", ""));
                            } else {
                                enchant = getOriginalEnchantmentName(string.split(":")[0].substring(1, string.split(":")[0].length() - 1));
                            }
                            if (level == -1) {
                                level = enchantmentsInfo.getInt(enchant + ".max-level");
                                if (maxlevel == false) {
                                    level = random.nextInt(level) + 1;
                                }
                            }
                        }
                        if (!(enchant == null)) {
                            lore.add(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + getEnchantmentRarity(enchant) + ".revealed-item.apply-format").replace("{ENCHANT}", enchantmentsInfo.getString(enchant + ".enchant-name") + " " + replaceIntWithRomanNumerals(level))));
                        }
                    }
                } else {
                    lore.add(ChatColor.translateAlternateColorCodes('&', string));
                }
            }
            itemMeta.setLore(lore);
            lore.clear();
            is.setItemMeta(itemMeta);
            if (itemMeta.hasDisplayName()) {
                if (itemMeta.getDisplayName().contains("{ENCHANT_SIZE}")) {
                    itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{ENCHANT_SIZE}", TRANSMOG.replace("{LORE_COUNT}", "" + getEnchantmentsOnItem(is).size())));
                }
                if (itemMeta.getDisplayName().contains("{PLAYER}")) {
                    itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{PLAYER}", player.getName()));
                }
                is.setItemMeta(itemMeta);
            }
            for (Enchantment enchant : enchants.keySet()) {
                is.addUnsafeEnchantment(enchant, enchants.get(enchant));
            }
            enchants.clear();
        }
        return;
    }

    private Enchantment getEnchantment(String string) {
        string = string.toLowerCase().replace("_", "");
        if (string.startsWith("po")) {
            return Enchantment.ARROW_DAMAGE; // Power
        } else if (string.startsWith("fl")) {
            return Enchantment.ARROW_FIRE; // Flame
        } else if (string.startsWith("i")) {
            return Enchantment.ARROW_INFINITE; // Infinity
        } else if (string.startsWith("pu")) {
            return Enchantment.ARROW_KNOCKBACK; // Punch
        } else if (string.startsWith("bi") && !(Bukkit.getVersion().contains("1.8")) && !(Bukkit.getVersion().contains("1.9")) && !(Bukkit.getVersion().contains("1.10"))) {
            return Enchantment.getByName("BINDING_CURSE"); // Blinding Curse
        } else if (string.startsWith("sh")) {
            return Enchantment.DAMAGE_ALL; // Sharpness
        } else if (string.startsWith("ba")) {
            return Enchantment.DAMAGE_ARTHROPODS; // Bane of Arthropods
        } else if (string.startsWith("sm")) {
            return Enchantment.DAMAGE_UNDEAD; // Smite
        } else if (string.startsWith("de")) {
            return Enchantment.DEPTH_STRIDER; // Depth Strider
        } else if (string.startsWith("e")) {
            return Enchantment.DIG_SPEED; // Efficiency
        } else if (string.startsWith("u")) {
            return Enchantment.DURABILITY; // Unbreaking
        } else if (string.startsWith("firea")) {
            return Enchantment.FIRE_ASPECT; // Fire Aspect
        } else if (string.startsWith("fr") && !(Bukkit.getVersion().contains("1.8"))) {
            return Enchantment.getByName("FROST_WALKER"); // Frost Walker
        } else if (string.startsWith("k")) {
            return Enchantment.KNOCKBACK; // Knockback
        } else if (string.startsWith("fo")) {
            return Enchantment.LOOT_BONUS_BLOCKS; // Fortune
        } else if (string.startsWith("lo")) {
            return Enchantment.LOOT_BONUS_MOBS; // Looting
        } else if (string.startsWith("luc")) {
            return Enchantment.LUCK; // Luck
        } else if (string.startsWith("lur")) {
            return Enchantment.LURE; // Lure
        } else if (string.startsWith("m") && !(Bukkit.getVersion().contains("1.8"))) {
            return Enchantment.getByName("MENDING"); // Mending
        } else if (string.startsWith("r")) {
            return Enchantment.OXYGEN; // Respiration
        } else if (string.startsWith("prot")) {
            return Enchantment.PROTECTION_ENVIRONMENTAL; // Protection
        } else if (string.startsWith("bl") || string.startsWith("bp")) {
            return Enchantment.PROTECTION_EXPLOSIONS; // Blast Protection
        } else if (string.startsWith("ff") || string.startsWith("fe")) {
            return Enchantment.PROTECTION_FALL; // Feather Falling
        } else if (string.startsWith("fp") || string.startsWith("firep")) {
            return Enchantment.PROTECTION_FIRE; // Fire Protection
        } else if (string.startsWith("pp") || string.startsWith("proj")) {
            return Enchantment.PROTECTION_PROJECTILE; // Projectile Protection
        } else if (string.startsWith("si")) {
            return Enchantment.SILK_TOUCH; // Silk Touch
        } else if (string.startsWith("th")) {
            return Enchantment.THORNS; // Thorns
        } else if (string.startsWith("v") && !(Bukkit.getVersion().contains("1.8")) && !(Bukkit.getVersion().contains("1.9")) && !(Bukkit.getVersion().contains("1.10"))) {
            return Enchantment.getByName("VANISHING_CURSE"); // Vanishing Curse
        } else if (string.startsWith("aa") || string.startsWith("aq")) {
            return Enchantment.WATER_WORKER; // Aqua Affinity
        } else {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    private ItemStack checkGkitVkit(ItemStack is, int slot, int i) {
        item = is.clone();
        itemMeta = item.getItemMeta();
        lore.clear();
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().hasLore()) {
            boolean did = false;
            if (item.getItemMeta().getDisplayName().equals(experiencebottle.getItemMeta().getDisplayName()) && item.getType().equals(experiencebottle.getType()) && item.getData().getData() == experiencebottle.getData().getData() && gkits.getString(slot + ".items." + i + ".item").contains(":")) {
                for (String string : itemMeta.getLore()) {
                    if (string.contains("{AMOUNT}")) {
                        String thing = gkits.getString(slot + ".items." + i + ".item").split(":")[1];
                        if (thing.contains("-")) {
                            thing = "" + (Integer.parseInt(thing.split("-")[0]) + random.nextInt(1 + Integer.parseInt(thing.split("-")[1]) - Integer.parseInt(thing.split("-")[0])));
                        }
                        string = string.replace("{AMOUNT}", formatIntUsingCommas(Integer.parseInt(thing)));
                    }
                    if (string.contains("{BOTTLER_NAME}")) {
                        string = string.replace("{BOTTLER_NAME}", "Gkit");
                    }
                    lore.add(string);
                }
                did = true;
            } else if (item.getItemMeta().getDisplayName().equals(blackscroll.getItemMeta().getDisplayName()) && item.getType().equals(blackscroll.getType()) && item.getData().getData() == blackscroll.getData().getData()) {
                for (String string : itemMeta.getLore()) {
                    if (string.contains("{PERCENT}")) {
                        string = string.replace("{PERCENT}", "" + getRandomBlackScrollPercent());
                    }
                    lore.add(string);
                }
                did = true;
            }
            if (did == true) {
                itemMeta.setLore(lore);
                lore.clear();
                item.setItemMeta(itemMeta);
            }
        } else if (item.getType().equals(Material.BOOK) && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("randomhashtags was here") && item.getItemMeta().hasLore() && item.getItemMeta().getLore().size() == 4) {
            int level = -1, success = -1, destroy = -1;
            String OEN = getOriginalEnchantmentName(item.getItemMeta().getLore().get(0)), rarity = getEnchantmentRarity(OEN);
            if (item.getItemMeta().getLore().get(1).equalsIgnoreCase("random")) {
                level = 1 + random.nextInt(enchantmentsInfo.getInt(OEN + ".max-level"));
            } else {
                level = Integer.parseInt(item.getItemMeta().getLore().get(1));
            }
            if (item.getItemMeta().getLore().get(2).equalsIgnoreCase("random")) {
                success = random.nextInt(101);
            } else {
                success = Integer.parseInt(item.getItemMeta().getLore().get(2));
            }
            if (item.getItemMeta().getLore().get(3).equalsIgnoreCase("random")) {
                destroy = random.nextInt(101);
            } else {
                destroy = Integer.parseInt(item.getItemMeta().getLore().get(3));
            }
            itemMeta.setLore(masterconfig.getStringList("givedp-items." + rarity + ".revealed-item.lore"));
            item.setItemMeta(itemMeta);
            item = formatBookLore(item, OEN, success, destroy);
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items." + rarity + ".revealed-item.name").replace("{ENCHANT}", enchantmentsInfo.getString(OEN + ".enchant-name") + " " + replaceIntWithRomanNumerals(level))));
            item.setItemMeta(itemMeta);
        } else {
            return null;
        }
        return item;
    }

    public String replaceIntWithRomanNumerals(int integer) {
        if (integer == 1) {
            return "I";
        } else if (integer == 2) {
            return "II";
        } else if (integer == 3) {
            return "III";
        } else if (integer == 4) {
            return "IV";
        } else if (integer == 5) {
            return "V";
        } else if (integer == 6) {
            return "VI";
        } else if (integer == 7) {
            return "VII";
        } else if (integer == 8) {
            return "VIII";
        } else if (integer == 9) {
            return "IX";
        } else if (integer == 10) {
            return "X";
        } else if (integer == 11) {
            return "XI";
        } else if (integer == 12) {
            return "XII";
        } else if (integer == 13) {
            return "XIII";
        } else if (integer == 14) {
            return "XIV";
        } else if (integer == 15) {
            return "XV";
        } else if (integer == 16) {
            return "XVI";
        } else if (integer == 17) {
            return "XVII";
        } else if (integer == 18) {
            return "XVIII";
        } else if (integer == 19) {
            return "XIX";
        } else if (integer == 20) {
            return "XX";
        } else {
            return null;
        }
    }

    private int getPlayerVkitLevel(Player player, int vkitSlot) {
        for (String string : getPlayerConfig(player).getStringList("unlocked-kits.vkits")) {
            if (string.startsWith(vkitSlot + ":")) {
                return Integer.parseInt(string.split(":")[1]);
            }
        }
        return -1;
    }

    private int getTotalExperience(Player player) {
        int experience = 0;
        int level = player.getLevel();
        if (level >= 0 && level <= 15) {
            experience = (int) Math.ceil(Math.pow(level, 2) + (6 * level));
            int requiredExperience = 2 * level + 7;
            double currentExp = Double.parseDouble(Float.toString(player.getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        } else if (level > 15 && level <= 30) {
            experience = (int) Math.ceil((2.5 * Math.pow(level, 2) - (40.5 * level) + 360));
            int requiredExperience = 5 * level - 38;
            double currentExp = Double.parseDouble(Float.toString(player.getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        } else {
            experience = (int) Math.ceil(((4.5 * Math.pow(level, 2) - (162.5 * level) + 2220)));
            int requiredExperience = 9 * level - 158;
            double currentExp = Double.parseDouble(Float.toString(player.getExp()));
            experience += Math.ceil(currentExp * requiredExperience);
            return experience;
        }
    }

    private void setTotalExperience(Player player, int xp) {
        //Levels 0 through 15
        if (xp >= 0 && xp < 351) {
            //Calculate Everything
            int a = 1;
            int b = 6;
            int c = -xp;
            int level = (int) (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            int xpForLevel = (int) (Math.pow(level, 2) + (6 * level));
            int remainder = xp - xpForLevel;
            int experienceNeeded = (2 * level) + 7;
            float experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);
            //Set Everything
            player.setLevel(level);
            player.setExp(experience);
            //Levels 16 through 30
        } else if (xp >= 352 && xp < 1507) {
            //Calculate Everything
            double a = 2.5;
            double b = -40.5;
            int c = -xp + 360;
            double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            int level = (int) Math.floor(dLevel);
            int xpForLevel = (int) (2.5 * Math.pow(level, 2) - (40.5 * level) + 360);
            int remainder = xp - xpForLevel;
            int experienceNeeded = (5 * level) - 38;
            float experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);
            //Set Everything
            player.setLevel(level);
            player.setExp(experience);
            //Level 31 and greater
        } else {
            //Calculate Everything
            double a = 4.5;
            double b = -162.5;
            int c = -xp + 2220;
            double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            int level = (int) Math.floor(dLevel);
            int xpForLevel = (int) (4.5 * Math.pow(level, 2) - (162.5 * level) + 2220);
            int remainder = xp - xpForLevel;
            int experienceNeeded = (9 * level) - 158;
            float experience = (float) remainder / (float) experienceNeeded;
            experience = round(experience, 2);
            //Set Everything
            player.setLevel(level);
            player.setExp(experience);
        }
    }

    private float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);
        return bd.floatValue();
    }

    private ArrayList<String> getEnchantmentsOnItem(ItemStack is) {
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

    /*
	 *
	 *
	 *
	 */
    @EventHandler
    private void playerInteractAtEntityEvent(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() == null) && !(event.getRightClicked().getCustomName() == null)) {
            if (event.getRightClicked().getCustomName().equalsIgnoreCase("alchemist")) {
                openGui(event.getPlayer(), RPGui.ALCHEMIST);
            } else if (event.getRightClicked().getCustomName().equalsIgnoreCase("enchanter")) {
                openGui(event.getPlayer(), RPGui.ENCHANTER);
            } else if (event.getRightClicked().getCustomName().equalsIgnoreCase("filter")) {
                openGui(event.getPlayer(), RPGui.FILTER);
            } else if (event.getRightClicked().getCustomName().equalsIgnoreCase("gkit")) {
                openGui(event.getPlayer(), RPGui.GKIT);
            } else if (event.getRightClicked().getCustomName().equalsIgnoreCase("shop")) {
                openGui(event.getPlayer(), RPGui.SHOP);
            } else if (event.getRightClicked().getCustomName().equalsIgnoreCase("tinkerer")) {
                openGui(event.getPlayer(), RPGui.TINKERER);
            } else if (event.getRightClicked().getCustomName().equalsIgnoreCase("vkit")) {
                openGui(event.getPlayer(), RPGui.VKIT);
            }
        }
    }

    //
    @EventHandler(priority = EventPriority.LOWEST)
    private void playerChatEvent(AsyncPlayerChatEvent event) {
        if (itemNameTag.contains(event.getPlayer().getName())) {
            item = getItemInHand(event.getPlayer());
            event.setCancelled(true);
            itemNameTag.remove(event.getPlayer().getName());
            if (item == null || item.getType().equals(Material.AIR)) {
                sendStringListMessage(event.getPlayer(), "ItemNameTag.cannot-rename-air", 0);
                giveItem(event.getPlayer(), itemnametag);
            } else if (item.getType().name().endsWith("BOW") || item.getType().name().endsWith("_AXE") || item.getType().name().endsWith("SWORD") || item.getType().name().endsWith("HELMET") || item.getType().name().endsWith("CHESTPLATE") || item.getType().name().endsWith("LEGGINGS") || item.getType().name().endsWith("BOOTS")) {
                itemMeta = item.getItemMeta();
                lore.clear();
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
                item.setItemMeta(itemMeta);
                event.getPlayer().updateInventory();
                getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "itemnametag.rename-item", false);
                for (String string : messages.getStringList("ItemNameTag.rename-item")) {
                    if (string.contains("{NAME}")) {
                        string = string.replace("{NAME}", itemMeta.getDisplayName());
                    }
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                }
            } else {
                sendStringListMessage(event.getPlayer(), "ItemNameTag.cannot-rename-item", 0);
                giveItem(event.getPlayer(), itemnametag);
            }
        }
    }

    //
    private void interactrpitem(PlayerInteractEvent event, ItemStack is) {
        item = event.getItem().clone();
        item.setAmount(1);
        event.setCancelled(true);
        if (is.equals(banknote) && !(RP_Vault.economy == null)) {
            for (int i = 0; i < masterconfig.getStringList("givedp-items.withdraw.lore").size(); i++) {
                if (masterconfig.getStringList("givedp-items.withdraw.lore").get(i).contains("{AMOUNT}")) {
                    RP_Vault.economy.depositPlayer(event.getPlayer(), getRemainingDoubleFromString(event.getItem().getItemMeta().getLore().get(i)));
                    sendStringListMessage(event.getPlayer(), "withdraw.deposit", getRemainingDoubleFromString(event.getItem().getItemMeta().getLore().get(i)));
                    getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "withdraw.deposit", false);
                }
            }
        } else if (is.equals(experiencebottle)) {
            for (int i = 0; i < masterconfig.getStringList("givedp-items.withdraw.lore").size(); i++) {
                if (masterconfig.getStringList("givedp-items.experience-bottle.lore").get(i).contains("{AMOUNT}")) {
                    setTotalExperience(event.getPlayer(), getTotalExperience(event.getPlayer()) + getRemainingIntFromString(event.getItem().getItemMeta().getLore().get(i)));
                    sendStringListMessage(event.getPlayer(), "xpbottle.deposit", getRemainingIntFromString(event.getItem().getItemMeta().getLore().get(i)));
                    getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "xpbottle", false);
                }
            }
        } else if (item.equals(explosivecake)) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute " + event.getPlayer().getName() + " " + event.getClickedBlock().getLocation().getBlockX() + " " + event.getClickedBlock().getLocation().getBlockY() + " " + event.getClickedBlock().getLocation().getBlockZ() + " particle cloud " + event.getClickedBlock().getLocation().getBlockX() + " " + event.getClickedBlock().getLocation().getBlockY() + " " + event.getClickedBlock().getLocation().getBlockZ() + " 1 1 1 1 45");
            getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "givedp-items.explosive-cake", false);
        } else if (item.equals(mysterymobspawner)) {
            giveSpawner(event.getPlayer(), masterconfig.getStringList("givedp-items.mystery-mob-spawner.obtainable-spawners").get(random.nextInt(masterconfig.getStringList("givedp-items.mystery-mob-spawner.obtainable-spawners").size())).toLowerCase());
        } else if (item.equals(itemnametag)) {
            if (itemNameTag.contains(event.getPlayer().getName())) {
                sendStringListMessage(event.getPlayer(), "ItemNameTag.already-in-rename-process", 0);
                return;
            } else {
                sendStringListMessage(event.getPlayer(), "ItemNameTag.enter-rename", 0);
                itemNameTag.add(event.getPlayer().getName());
            }
        } else {
            return;
        }
        item = event.getItem();
        if (item.getAmount() == 1) {
            item = new ItemStack(Material.AIR);
        } else {
            item.setAmount(item.getAmount() - 1);
        }
        setItemInHand(event.getPlayer(), item);
        event.getPlayer().updateInventory();
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    private void playerInteractEvent(PlayerInteractEvent event) {
        if (!(event.getItem() == null) && !(event.getItem().getType().equals(Material.AIR))) {
            if (event.getItem().getType().name().endsWith("HELMET") && !(event.getPlayer().getInventory().getHelmet() == null) || event.getItem().getType().name().endsWith("CHESTPLATE") || event.getItem().getType().name().endsWith("LEGGINGS") || event.getItem().getType().name().endsWith("BOOTS")) {
                armorSwitch(event.getPlayer(), event.getItem());
            } else if (!(event.getItem() == null) && !(event.getItem().getType().equals(Material.AIR)) && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName() && event.getItem().getItemMeta().hasLore() && !(event.getItem().getItemMeta().getLore().equals(soulgemlore)) && event.getAction().name().contains("RIGHT")) {
                if (armor_enchantment_orb == true && event.getItem().getItemMeta().equals(armorenchantmentorb.getItemMeta())) {
                    interactrpitem(event, armorenchantmentorb);
                } else if (weapon_enchantment_orb == true && event.getItem().getItemMeta().equals(weaponenchantmentorb.getItemMeta())) {
                    interactrpitem(event, weaponenchantmentorb);
                } else if (white_scroll == true && event.getItem().getItemMeta().equals(whitescroll.getItemMeta())) {
                    interactrpitem(event, whitescroll);
                } else if (explosive_cake == true && event.getItem().getItemMeta().equals(explosivecake.getItemMeta()) && !(event.getClickedBlock() == null) && !(event.getClickedBlock().getType().equals(Material.AIR))) {
                    interactrpitem(event, explosivecake);
                } else if (mystery_mob_spawner == true && event.getItem().getItemMeta().equals(mysterymobspawner.getItemMeta())) {
                    interactrpitem(event, mysterymobspawner);
                } else if (item_nametag == true && event.getItem().getItemMeta().equals(itemnametag.getItemMeta())) {
                    interactrpitem(event, itemnametag);
                } else if (bank_note == true && event.getItem().getItemMeta().getDisplayName().equals(banknote.getItemMeta().getDisplayName())) {
                    interactrpitem(event, banknote);
                } else if (experience_bottle == true && event.getItem().getItemMeta().getDisplayName().equals(experiencebottle.getItemMeta().getDisplayName())) {
                    interactrpitem(event, experiencebottle);
                } else if (event.getItem().getItemMeta().equals(randombook.getItemMeta()) || event.getItem().getItemMeta().equals(soulbook.getItemMeta()) || event.getItem().getItemMeta().equals(legendarybook.getItemMeta()) || event.getItem().getItemMeta().equals(ultimatebook.getItemMeta())
                        || event.getItem().getItemMeta().equals(elitebook.getItemMeta()) || event.getItem().getItemMeta().equals(uniquebook.getItemMeta()) || event.getItem().getItemMeta().equals(simplebook.getItemMeta())) {
                    ArrayList<String> enchantments = null;
                    String enchant = null, rarity = null;
                    if (!(event.getItem().getItemMeta().equals(randombook.getItemMeta()))) {
                        if (event.getItem().getItemMeta().equals(soulbook.getItemMeta())) {
                            enchantments = soulenchantments;
                        } else if (event.getItem().getItemMeta().equals(legendarybook.getItemMeta())) {
                            enchantments = legendaryenchantments;
                        } else if (event.getItem().getItemMeta().equals(ultimatebook.getItemMeta())) {
                            enchantments = ultimateenchantments;
                        } else if (event.getItem().getItemMeta().equals(elitebook.getItemMeta())) {
                            enchantments = eliteenchantments;
                        } else if (event.getItem().getItemMeta().equals(uniquebook.getItemMeta())) {
                            enchantments = uniqueenchantments;
                        } else if (event.getItem().getItemMeta().equals(simplebook.getItemMeta())) {
                            enchantments = simpleenchantments;
                        } else {
                            return;
                        }
                    } else {
                        int randomrarity = random.nextInt(6);
                        if (randomrarity == 0) {
                            enchantments = soulenchantments;
                        } else if (randomrarity == 1) {
                            enchantments = legendaryenchantments;
                        } else if (randomrarity == 2) {
                            enchantments = ultimateenchantments;
                        } else if (randomrarity == 3) {
                            enchantments = eliteenchantments;
                        } else if (randomrarity == 4) {
                            enchantments = uniqueenchantments;
                        } else if (randomrarity == 5) {
                            enchantments = simpleenchantments;
                        }
                        rarity = "random";
                    }
                    if (enchantments.size() == 0) {
                        return;
                    }
                    enchant = enchantments.get(random.nextInt(enchantments.size()));
                    rarity = getEnchantmentRarity(enchant);
                    item = formatBookLore(getConfigItem(masterconfig, "givedp-items." + rarity + ".revealed-item"), enchant, random.nextInt(101), random.nextInt(101));
                    removeItem(event.getPlayer(), event.getItem(), 1);
                    giveItem(event.getPlayer(), item);
                    for (String string : messages.getStringList("books." + rarity)) {
                        if (string.contains("{BOOK_NAME}")) {
                            string = string.replace("{BOOK_NAME}", enchant);
                        }
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                    }
                    if (shootFireworks == true) {
                        Firework fw = event.getPlayer().getWorld().spawn(event.getPlayer().getLocation(), Firework.class);
                        FireworkMeta fwm = fw.getFireworkMeta();
                        String[] string = null;
                        if (rarity.equals("random")) {
                            string = masterconfig.getString("givedp-items.random.revealed-item.firework").toLowerCase().replace(" ", "").split("\\:");
                        } else {
                            string = masterconfig.getString("givedp-items." + rarity + ".revealed-item.firework").toLowerCase().replace(" ", "").split("\\:");
                        }
                        fwm.addEffects(FireworkEffect.builder().trail(true).flicker(true)
                                .with(Type.valueOf(string[0].toUpperCase()))
                                .withColor(getColor(string[1]))
                                .withFade(getColor(string[2]))
                                .withFlicker().withTrail().build());
                        fwm.setPower(Integer.parseInt(string[3]));
                        fw.setFireworkMeta(fwm);
                    }
                } else if (event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName() && !(getOriginalEnchantmentName(event.getItem().getItemMeta().getDisplayName()) == null)) {
                    sendStringListMessage(event.getPlayer(), "books.apply-info", 0);
                } else if (event.getItem().getItemMeta().equals(godDropPackage.getItemMeta())) {
                    openDropPackage(event, godDPInv);
                } else if (event.getItem().getItemMeta().equals(legendaryDropPackage.getItemMeta())) {
                    openDropPackage(event, legendaryDPInv);
                } else if (event.getItem().getItemMeta().equals(ultimateDropPackage.getItemMeta())) {
                    openDropPackage(event, ultimateDPInv);
                } else if (event.getItem().getItemMeta().equals(eliteDropPackage.getItemMeta())) {
                    openDropPackage(event, eliteDPInv);
                } else if (event.getItem().getItemMeta().equals(uniqueDropPackage.getItemMeta())) {
                    openDropPackage(event, uniqueDPInv);
                } else if (event.getItem().getItemMeta().equals(simpleDropPackage.getItemMeta())) {
                    openDropPackage(event, simpleDPInv);
                } else if (event.getItem().getItemMeta().equals(soulFireball.getItemMeta())) {
                    giveDust(event, "soul");
                } else if (event.getItem().getItemMeta().equals(legendaryFireball.getItemMeta())) {
                    giveDust(event, "legendary");
                } else if (event.getItem().getItemMeta().equals(ultimateFireball.getItemMeta())) {
                    giveDust(event, "ultimate");
                } else if (event.getItem().getItemMeta().equals(eliteFireball.getItemMeta())) {
                    giveDust(event, "elite");
                } else if (event.getItem().getItemMeta().equals(uniqueFireball.getItemMeta())) {
                    giveDust(event, "unique");
                } else if (event.getItem().getItemMeta().equals(simpleFireball.getItemMeta())) {
                    giveDust(event, "simple");
                } else {
                    for (int i = 0; i < 54; i++) {
                        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !(fallenheroes[i] == null) && event.getItem().getItemMeta().equals(fallenheroes[i].getItemMeta())) {
                            event.setCancelled(true);
                            if (FactionsAPI.getInstance().locationIsWarZone(event.getClickedBlock()) == true) {
                                removeItem(event.getPlayer(), event.getItem(), 1);
                                FactionsAPI.spawnFallenHero(event, i);
                            } else {
                                sendStringListMessage(event.getPlayer(), "fallen-heros.not-in-warzone", 0);
                            }
                            return;
                        } else if (event.getAction().name().contains("RIGHT_CLICK") && !(gkitgems[i] == null) && event.getItem().getItemMeta().equals(gkitgems[i].getItemMeta())) {
                            event.setCancelled(true);
                            File file = new File(RandomPackage.getPlugin.getDataFolder() + File.separator + "player-data" + File.separator, event.getPlayer().getUniqueId().toString() + ".yml");
                            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                            for (String string : config.getStringList("unlocked-kits.gkits")) {
                                if (string.equals("" + i)) {
                                    sendStringListMessage(event.getPlayer(), "fallen-heros.already-have-kit", 0);
                                    getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "redeem.gkit-gem.already-have", false);
                                    return;
                                }
                            }
                            lore.clear();
                            lore.addAll(config.getStringList("unlocked-kits.gkits"));
                            lore.add("" + i);
                            config.set("unlocked-kits.gkits", lore.toArray());
                            try {
                                config.save(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            removeItem(event.getPlayer(), event.getItem(), 1);
                            getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "redeem.gkit-gem.success", false);
                            for (String string : messages.getStringList("fallen-heros.redeem-kit")) {
                                if (string.contains("{FALLEN_HERO_NAME}")) {
                                    string = string.replace("{FALLEN_HERO_NAME}", masterconfig.getString("fallen-heros." + i + ".name"));
                                }
                                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
                            }
                            return;
                        }
                    }
                }
                return;
            } else {
                return;
            }
        } else {
            return;
        }
    }

    private void openDropPackage(PlayerInteractEvent event, Inventory dp) {
        event.setCancelled(true);
        event.getPlayer().openInventory(Bukkit.createInventory(event.getPlayer(), dp.getSize(), dp.getTitle()));
        event.getPlayer().getOpenInventory().getTopInventory().setContents(dp.getContents());
        event.getPlayer().updateInventory();
    }

    private void giveDust(PlayerInteractEvent event, String rarity) {
        event.setCancelled(true);
        removeItem(event.getPlayer(), event.getItem(), 1);
        int percent = 0;
        if (random.nextInt(100) <= masterconfig.getInt("givedp-items." + rarity + ".primal.chance")) {
            if (rarity.equals("soul")) {
                item = soulPrimalDust.clone();
            } else if (rarity.equals("legendary")) {
                item = legendaryPrimalDust.clone();
            } else if (rarity.equals("ultimate")) {
                item = ultimatePrimalDust.clone();
            } else if (rarity.equals("elite")) {
                item = elitePrimalDust.clone();
            } else if (rarity.equals("unique")) {
                item = uniquePrimalDust.clone();
            } else if (rarity.equals("simple")) {
                item = simplePrimalDust.clone();
            }
            percent = masterconfig.getInt("givedp-items." + rarity + ".primal.min-percent") + (random.nextInt((masterconfig.getInt("givedp-items." + rarity + ".primal.max-percent") - masterconfig.getInt("givedp-items." + rarity + ".primal.min-percent") + 1)));
        } else if (random.nextInt(100) <= masterconfig.getInt("givedp-items." + rarity + ".regular.chance")) {
            if (rarity.equals("soul")) {
                item = soulRegularDust.clone();
            } else if (rarity.equals("legendary")) {
                item = legendaryRegularDust.clone();
            } else if (rarity.equals("ultimate")) {
                item = ultimateRegularDust.clone();
            } else if (rarity.equals("elite")) {
                item = eliteRegularDust.clone();
            } else if (rarity.equals("unique")) {
                item = uniqueRegularDust.clone();
            } else if (rarity.equals("simple")) {
                item = simpleRegularDust.clone();
            }
            percent = masterconfig.getInt("givedp-items." + rarity + ".regular.min-percent") + (random.nextInt((masterconfig.getInt("givedp-items." + rarity + ".regular.max-percent") - masterconfig.getInt("givedp-items." + rarity + ".regular.min-percent") + 1)));
        } else {
            for (int q = 1; q <= 15; q++) {
                event.getPlayer().getWorld().playEffect(event.getPlayer().getEyeLocation(), Effect.LAVA_POP, 1);
            }
            getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "dust.reveal-mystery-dust", false);
            giveItem(event.getPlayer(), mysterydust.clone());
            return;
        }
        itemMeta = item.getItemMeta();
        lore.clear();
        lore.addAll(itemMeta.getLore());
        for (int z = 0; z < lore.size(); z++) {
            if (lore.get(z).contains("{PERCENT}")) {
                lore.set(z, lore.get(z).replace("{PERCENT}", "" + percent));
            }
        }
        itemMeta.setLore(lore);
        lore.clear();
        item.setItemMeta(itemMeta);
        giveItem(event.getPlayer(), item);
        event.getPlayer().updateInventory();
        getAndPlaySound(event.getPlayer(), event.getPlayer().getLocation(), "dust.reveal-dust", false);
        for (int q = 1; q <= 15; q++) {
            event.getPlayer().playEffect(event.getPlayer().getEyeLocation(), Effect.SPELL, 1);
        }
    }

    private void giveSpawner(Player player, String entitytype) {
        String path = null, type = null;
        if (entitytype.equalsIgnoreCase("iron-golem")) {
            path = "iron-golem";
            type = "VillagerGolem";
        } else if (entitytype.equalsIgnoreCase("endermen")) {
            path = "enderman";
        } else if (entitytype.equalsIgnoreCase("zombie-pigman")) {
            path = "zombie-pigman";
            type = "PigZombie";
        } else if (entitytype.equalsIgnoreCase("magma-cube")) {
            path = "magma-cube";
            type = "LavaSlime";
        } else if (entitytype.equalsIgnoreCase("mushroom-cow")) {
            path = "mushroom-cow";
            type = "MushroomCow";
        } else if (entitytype.equalsIgnoreCase("slime")) {
            path = "slime";
        } else if (entitytype.equalsIgnoreCase("cave-spider")) {
            path = "cave-spider";
            type = "CaveSpider";
        } else if (entitytype.equalsIgnoreCase("spider")) {
            path = "spider";
        } else if (entitytype.equalsIgnoreCase("ghast")) {
            path = "ghast";
        } else if (entitytype.equalsIgnoreCase("skeleton")) {
            path = "skeleton";
        } else if (entitytype.equalsIgnoreCase("zombie")) {
            path = "zombie";
        } else if (entitytype.equalsIgnoreCase("cow")) {
            path = "cow";
        } else if (entitytype.equalsIgnoreCase("sheep")) {
            path = "sheep";
        } else if (entitytype.equalsIgnoreCase("chicken")) {
            path = "chicken";
        } else if (entitytype.equalsIgnoreCase("pig")) {
            path = "pig";
        } else if (entitytype.equalsIgnoreCase("wolf")) {
            path = "wolf";
        } else if (entitytype.equalsIgnoreCase("zombie")) {
            path = "zombie";
        }
        if (path == null) {
            path = entitytype.toLowerCase();
        }
        if (type == null) {
            type = path.substring(0, 1).toUpperCase() + path.substring(1).toLowerCase();
        }
        String lore = masterconfig.getStringList("givedp-items.mystery-mob-spawner.revealed-spawner-lore").toString().substring(1, masterconfig.getStringList("givedp-items.mystery-mob-spawner.revealed-spawner-lore").toString().length() - 1), formattedSpawnerName = path;
        if (path.contains("-")) {
            int hyphen = 0;
            for (int i = 0; i < path.length(); i++) {
                if (path.substring(i, i + 1).equals("-")) {
                    hyphen = i;
                }
            }
            formattedSpawnerName = path.substring(0, 1).toUpperCase() + path.substring(1, hyphen) + " " + path.substring(hyphen + 1, hyphen + 2).toUpperCase() + path.substring(hyphen + 2, path.length());
        } else {
            formattedSpawnerName = path.substring(0, 1).toUpperCase() + path.substring(1);
        }
        if (lore.contains("{SPAWNER_TYPE}")) {
            lore = lore.replace("{SPAWNER_TYPE}", formattedSpawnerName);
        }
        lore = ChatColor.translateAlternateColorCodes('&', lore);
        if (!(Bukkit.getVersion().contains("1.8"))) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " mob_spawner 1 0 {BlockEntityTag:{SpawnData:{id:" + type + "}}, display:{Name:" + ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.mystery-mob-spawner.spawner-names." + path)) + ",Lore:[\"" + lore + "\"]}}");
        } else if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "minecraft:give " + player.getName() + " mob_spawner 1 0 {BlockEntityTag:{EntityId:" + type + "}, display:{Name:" + ChatColor.translateAlternateColorCodes('&', masterconfig.getString("givedp-items.mystery-mob-spawner.spawner-names." + path)) + ",Lore:[\"" + lore + "\"]}}");
        }
    }

    @EventHandler
    private void playerItemPickupEvent(PlayerPickupItemEvent event) {
        FileConfiguration config = getPlayerConfig(event.getPlayer());
        if (config.getBoolean("filter.activated") == true) {
            for (String string : config.getStringList("filter.items")) {
                if (event.getItem().getType().name().equals(string.toUpperCase())) {
                    return;
                }
            }
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
        return;
    }

    private Color getColor(String path) {
        if (path == null) {
            return null;
        } else if (path.equalsIgnoreCase("aqua")) {
            return Color.AQUA;
        } else if (path.equalsIgnoreCase("black")) {
            return Color.BLACK;
        } else if (path.equalsIgnoreCase("blue")) {
            return Color.BLUE;
        } else if (path.equalsIgnoreCase("fuchsia")) {
            return Color.FUCHSIA;
        } else if (path.equalsIgnoreCase("gray")) {
            return Color.GRAY;
        } else if (path.equalsIgnoreCase("green")) {
            return Color.GREEN;
        } else if (path.equalsIgnoreCase("lime")) {
            return Color.LIME;
        } else if (path.equalsIgnoreCase("maroon")) {
            return Color.MAROON;
        } else if (path.equalsIgnoreCase("navy")) {
            return Color.NAVY;
        } else if (path.equalsIgnoreCase("olive")) {
            return Color.OLIVE;
        } else if (path.equalsIgnoreCase("orange")) {
            return Color.ORANGE;
        } else if (path.equalsIgnoreCase("purple")) {
            return Color.PURPLE;
        } else if (path.equalsIgnoreCase("red")) {
            return Color.RED;
        } else if (path.equalsIgnoreCase("silver")) {
            return Color.SILVER;
        } else if (path.equalsIgnoreCase("teal")) {
            return Color.TEAL;
        } else if (path.equalsIgnoreCase("white")) {
            return Color.WHITE;
        } else if (path.equalsIgnoreCase("yellow")) {
            return Color.YELLOW;
        } else {
            return null;
        }
    }
	/*
	 * 
	 * 
	 * 
	 */
}
