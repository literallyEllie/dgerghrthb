package me.randomHashTags.RandomPackage.api.other;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class RP_Vault {
    public static Economy economy = null;

    private static RP_Vault instance;

    public static RP_Vault getInstance() {
        if (instance == null) {
            instance = new RP_Vault();
        }
        return instance;
    }

    public boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }
        return (economy != null);
    }
}
