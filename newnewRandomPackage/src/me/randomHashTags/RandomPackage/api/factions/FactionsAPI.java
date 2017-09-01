package me.randomHashTags.RandomPackage.api.factions;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import me.randomHashTags.RandomPackage.RandomPackage;
import me.randomHashTags.RandomPackage.api.versions.v1_12;
import me.randomHashTags.RandomPackage.api.versions.v1_8;
import me.randomHashTags.RandomPackage.api.factions.factionsUUID;
import me.randomHashTags.RandomPackage.api.factions.legacyFactions;
import me.randomHashTags.RandomPackage.api.factions.regularFactions;

public class FactionsAPI {
	
	
	private static FactionsAPI instance;
	public static FactionsAPI getInstance() {
		if(instance == null) { instance = new FactionsAPI(); }
		return instance;
	}
	//
	public boolean locationIsWarZone(Block block) {
		if(RandomPackage.factionPlugin.equals("LegacyFactions")) { return legacyFactions.getInstance().locationIsWarZone(block);
		} else if(RandomPackage.factionPlugin.equals("FactionsUUID")) { return factionsUUID.getInstance().locationIsWarZone(block);
		} else if(RandomPackage.factionPlugin.equals("Factions")) { return regularFactions.getInstance().locationIsWarZone(block);
		} else { return true; }
	}
	public boolean blockIsProtected(Player player, Block block) {
		if(RandomPackage.factionPlugin.equals("LegacyFactions")) { return legacyFactions.getInstance().blockIsProtected(player, block);
		} else if(RandomPackage.factionPlugin.equals("FactionsUUID")) { return factionsUUID.getInstance().blockIsProtected(player, block);
		} else if(RandomPackage.factionPlugin.equals("Factions")) { return regularFactions.getInstance().blockIsProtected(player, block);
		} else { return false; }
	}
	public boolean relationIsEnemyOrNull(Player player1, Player player2) {
		if(RandomPackage.factionPlugin.equals("LegacyFactions")) { return legacyFactions.getInstance().relationIsEnemyOrNull(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("FactionsUUID")) { return factionsUUID.getInstance().relationIsEnemyOrNull(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("Factions")) { return regularFactions.getInstance().relationIsEnemyOrNull(player1, player2);
		} else { return true; }
	}
	public boolean relationIsAlly(Player player1, Player player2) {
		if(RandomPackage.factionPlugin.equals("LegacyFactions")) { return legacyFactions.getInstance().relationIsAlly(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("FactionsUUID")) { return factionsUUID.getInstance().relationIsAlly(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("Factions")) { return regularFactions.getInstance().relationIsAlly(player1, player2);
		} else { return false; }
	}
	public boolean relationIsTruce(Player player1, Player player2) {
		if(RandomPackage.factionPlugin.equals("LegacyFactions")) { return legacyFactions.getInstance().relationIsTruce(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("FactionsUUID")) { return factionsUUID.getInstance().relationIsTruce(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("Factions")) { return regularFactions.getInstance().relationIsTruce(player1, player2);
		} else { return false; }
	}
	public boolean relationIsMember(Player player1, Player player2) {
		if(RandomPackage.factionPlugin.equals("LegacyFactions")) { return legacyFactions.getInstance().relationIsMember(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("FactionsUUID")) { return factionsUUID.getInstance().relationIsMember(player1, player2);
		} else if(RandomPackage.factionPlugin.equals("Factions")) { return regularFactions.getInstance().relationIsMember(player1, player2);
		} else { return false; }
	}
	public static void spawnFallenHero(PlayerInteractEvent event, int gkit) {
		if(!(Bukkit.getVersion().contains("1.12")) && !(Bukkit.getVersion().contains("1.11"))) { v1_8.spawnFallenHero(event, gkit);
		} else { v1_12.spawnFallenHero(event, gkit); }
	}
	
}
