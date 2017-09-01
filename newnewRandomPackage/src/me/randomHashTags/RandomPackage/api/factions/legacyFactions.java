package me.randomHashTags.RandomPackage.api.factions;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import net.redstoneore.legacyfactions.Relation;
import net.redstoneore.legacyfactions.entity.FPlayerColl;
import net.redstoneore.legacyfactions.entity.FactionColl;

public class legacyFactions {
	
	private static legacyFactions factions = null;
	public static legacyFactions getInstance() {
		if(factions == null) { factions = new legacyFactions(); }
		return factions;
	}
	
	public boolean locationIsWarZone(Block block) {
		if(FactionColl.get().getWarZone().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]")) {
			return true;
		} else { return false; }
	}
	@SuppressWarnings("deprecation")
	public boolean blockIsProtected(Player player, Block block) {
		Vector vec = new Vector(block.getLocation().toVector().getX(), block.getLocation().toVector().getY(), block.getLocation().toVector().getZ());
		ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(block.getWorld()).getApplicableRegions(vec);
		if(set.allows(DefaultFlag.BLOCK_BREAK) == true && !(FactionColl.get().getWarZone().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]")) && !(FactionColl.get().getSafeZone().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]"))
				|| FPlayerColl.get(player).getFaction().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]")) {
			return false;
		} else { return true; }
	}
	public boolean relationIsEnemyOrNull(Player player1, Player player2) {
		if(FactionColl.get(player1).getRelationTo(FactionColl.get(player2)) == null
				|| FactionColl.get(player1).getRelationTo(FactionColl.get(player2)).equals(Relation.ENEMY)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsAlly(Player player1, Player player2) {
		if(FactionColl.get(player1).getRelationTo(FactionColl.get(player2)).equals(Relation.ALLY)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsTruce(Player player1, Player player2) {
		if(FactionColl.get(player1).getRelationTo(FactionColl.get(player2)).equals(Relation.TRUCE)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsMember(Player player1, Player player2) {
		if(FactionColl.get(player1).getRelationTo(FactionColl.get(player2)).equals(Relation.MEMBER)) {
			return true;
		} else { return false; }
	}
	/*
	 * 
	 */
	
}
