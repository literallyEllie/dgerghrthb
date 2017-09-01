package me.randomHashTags.RandomPackage.api.factions;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class regularFactions {
	
	private static regularFactions factions = null;
	public static regularFactions getInstance() {
		if(factions == null) { factions = new regularFactions(); }
		return factions;
	}
	public boolean locationIsWarZone(Block block) {
		if(BoardColl.get().getFactionAt(PS.valueOf(new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ()))).getName().equalsIgnoreCase("WarZone")) {
			return true;
		} else { return false; }
	}
	@SuppressWarnings("deprecation")
	public boolean blockIsProtected(Player player, Block block) {
		Vector vec = new Vector(block.getLocation().toVector().getX(), block.getLocation().toVector().getY(), block.getLocation().toVector().getZ());
		ApplicableRegionSet set = WGBukkit.getPlugin().getRegionManager(block.getWorld()).getApplicableRegions(vec);
		if(set.allows(DefaultFlag.BLOCK_BREAK) == true && !(BoardColl.get().getFactionAt(PS.valueOf(new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ()))).getName().equalsIgnoreCase("WarZone"))
				&& !(BoardColl.get().getFactionAt(PS.valueOf(new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ()))).getName().equalsIgnoreCase("SafeZone"))
				|| BoardColl.get().getFactionAt(PS.valueOf(new Location(block.getWorld(), block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ()))).getName().equalsIgnoreCase(MPlayer.get(player).getFactionName())) {
			return false;
		} else { return true; }
	}
	public boolean relationIsEnemyOrNull(Player player1, Player player2) {
		if(MPlayer.get(player1).getRelationTo(MPlayer.get(player2)) == null
				|| MPlayer.get(player1).getRelationTo(MPlayer.get(player2)).equals(Rel.ENEMY)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsAlly(Player player1, Player player2) {
		if(MPlayer.get(player1).getRelationTo(MPlayer.get(player2)).equals(Rel.ALLY)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsTruce(Player player1, Player player2) {
		if(MPlayer.get(player1).getRelationTo(MPlayer.get(player2)).equals(Rel.TRUCE)) {
			return true;
		} else { return false; }
	}
	public boolean relationIsMember(Player player1, Player player2) {
		if(MPlayer.get(player1).getRelationTo(MPlayer.get(player2)).equals(Rel.MEMBER)) {
			return true;
		} else { return false; }
	}
	/*
	 * 
	 */
}
