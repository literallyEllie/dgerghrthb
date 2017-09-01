package me.randomHashTags.RandomPackage.api.other;

import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class RP_WorldEditWorldGuard {
	
	private static RP_WorldEditWorldGuard instance;
	public static RP_WorldEditWorldGuard getInstance() {
		if(instance == null) { instance = new RP_WorldEditWorldGuard(); }
		return instance;
	}
	
	@SuppressWarnings("deprecation")
	public boolean locationHasPvPAllowed(World world, Location location) {
		com.sk89q.worldedit.Vector vec = new com.sk89q.worldedit.Vector(location.toVector().getX(), location.toVector().getY(), location.toVector().getZ());
		WorldGuardPlugin wg = WGBukkit.getPlugin();
		RegionManager rm = wg.getRegionManager(world);
		ApplicableRegionSet set = rm.getApplicableRegions(vec);
		return set.allows(DefaultFlag.PVP);
	}
	
}
