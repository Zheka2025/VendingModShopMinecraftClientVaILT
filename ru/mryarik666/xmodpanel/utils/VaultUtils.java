package ru.mryarik666.xmodpanel.utils;

import cpw.mods.fml.relauncher.*;
import net.milkbowl.vault.economy.*;
import net.minecraft.entity.player.*;

@SideOnly(Side.SERVER)
public final class VaultUtils
{
    private static Economy economy;
    
    public static boolean hasMoney(final String player, final double amount) {
        return false;
    }
    
    public static boolean hasMoney(final EntityPlayer player, final double amount) {
        return false;
    }
    
    public static boolean withdrawPlayer(final String player, final double amount) {
        return false;
    }
    
    public static boolean withdrawPlayer(final EntityPlayer player, final double amount) {
        return false;
    }
    
    public static boolean depositPlayer(final EntityPlayer player, final double amount) {
        return false;
    }
    
    public static boolean depositPlayer(final String playerName, final double amount) {
        return false;
    }
    
    private static Economy getEconomy() {
        return VaultUtils.economy;
    }
}
