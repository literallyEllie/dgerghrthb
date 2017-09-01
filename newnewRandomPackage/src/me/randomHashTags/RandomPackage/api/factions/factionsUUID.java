package me.randomHashTags.RandomPackage.api.factions;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.struct.Relation;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class factionsUUID {
	private static factionsUUID factions = null;
	public static factionsUUID getInstance() {
		if(factions == null) { factions = new factionsUUID(); }
		return factions;
	}
	public boolean locationIsWarZone(Block block) {
		if(Factions.getInstance().getWarZone().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]")) {
			return true;
		} else { return false; }
	}
	@SuppressWarnings("deprecation")
	public boolean blockIsProtected(Player player, Block block) {
		Vector vec = new Vector(block.getLocation().toVector().getX(), block.getLocation().toVector().getY(), block.getLocation().toVector().getZ());
		ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(block.getWorld()).getApplicableRegions(vec);
		if(set.allows(DefaultFlag.BLOCK_BREAK) == true && !(Factions.getInstance().getWarZone().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]")) && !(Factions.getInstance().getSafeZone().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]"))
				|| FPlayers.getInstance().getByPlayer(player).getFaction().getAllClaims().toString().contains("[" + block.getWorld().getName() + "," + block.getLocation().getChunk().getX() + "," + block.getLocation().getChunk().getZ() + "]")) {
			return false;
		} else { return true; }
	}
	public boolean relationIsEnemyOrNull(Player player1, Player player2) {
		if(FPlayers.getInstance().getByPlayer(player1).getRelationTo(FPlayers.getInstance().getByPlayer(player2)) == null
				|| FPlayers.getInstance().getByPlayer(player1).getRelationTo(FPlayers.getInstance().getByPlayer(player2)).equals(Relation.ENEMY)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsAlly(Player player1, Player player2) {
		if(FPlayers.getInstance().getByPlayer(player1).getRelationTo(FPlayers.getInstance().getByPlayer(player2)).equals(Relation.ALLY)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsTruce(Player player1, Player player2) {
		if(FPlayers.getInstance().getByPlayer(player1).getRelationTo(FPlayers.getInstance().getByPlayer(player2)).equals(Relation.TRUCE)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsMember(Player player1, Player player2) {
		if(FPlayers.getInstance().getByPlayer(player1).getRelationTo(FPlayers.getInstance().getByPlayer(player2)).equals(Relation.MEMBER)) {
			return true;
		} else { return false; }
	}
	/*
	 * 
	 */
}
