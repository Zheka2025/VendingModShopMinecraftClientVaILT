package net.milkbowl.vault.economy;

import org.bukkit.*;
import java.util.*;

public interface Economy
{
    boolean isEnabled();
    
    String getName();
    
    boolean hasBankSupport();
    
    int fractionalDigits();
    
    String format(final double p0);
    
    String currencyNamePlural();
    
    String currencyNameSingular();
    
    @Deprecated
    boolean hasAccount(final String p0);
    
    boolean hasAccount(final OfflinePlayer p0);
    
    @Deprecated
    boolean hasAccount(final String p0, final String p1);
    
    boolean hasAccount(final OfflinePlayer p0, final String p1);
    
    @Deprecated
    double getBalance(final String p0);
    
    double getBalance(final OfflinePlayer p0);
    
    @Deprecated
    double getBalance(final String p0, final String p1);
    
    double getBalance(final OfflinePlayer p0, final String p1);
    
    @Deprecated
    boolean has(final String p0, final double p1);
    
    boolean has(final OfflinePlayer p0, final double p1);
    
    @Deprecated
    boolean has(final String p0, final String p1, final double p2);
    
    boolean has(final OfflinePlayer p0, final String p1, final double p2);
    
    @Deprecated
    EconomyResponse withdrawPlayer(final String p0, final double p1);
    
    EconomyResponse withdrawPlayer(final OfflinePlayer p0, final double p1);
    
    @Deprecated
    EconomyResponse withdrawPlayer(final String p0, final String p1, final double p2);
    
    EconomyResponse withdrawPlayer(final OfflinePlayer p0, final String p1, final double p2);
    
    @Deprecated
    EconomyResponse depositPlayer(final String p0, final double p1);
    
    EconomyResponse depositPlayer(final OfflinePlayer p0, final double p1);
    
    @Deprecated
    EconomyResponse depositPlayer(final String p0, final String p1, final double p2);
    
    EconomyResponse depositPlayer(final OfflinePlayer p0, final String p1, final double p2);
    
    @Deprecated
    EconomyResponse createBank(final String p0, final String p1);
    
    EconomyResponse createBank(final String p0, final OfflinePlayer p1);
    
    EconomyResponse deleteBank(final String p0);
    
    EconomyResponse bankBalance(final String p0);
    
    EconomyResponse bankHas(final String p0, final double p1);
    
    EconomyResponse bankWithdraw(final String p0, final double p1);
    
    EconomyResponse bankDeposit(final String p0, final double p1);
    
    @Deprecated
    EconomyResponse isBankOwner(final String p0, final String p1);
    
    EconomyResponse isBankOwner(final String p0, final OfflinePlayer p1);
    
    @Deprecated
    EconomyResponse isBankMember(final String p0, final String p1);
    
    EconomyResponse isBankMember(final String p0, final OfflinePlayer p1);
    
    List<String> getBanks();
    
    @Deprecated
    boolean createPlayerAccount(final String p0);
    
    boolean createPlayerAccount(final OfflinePlayer p0);
    
    @Deprecated
    boolean createPlayerAccount(final String p0, final String p1);
    
    boolean createPlayerAccount(final OfflinePlayer p0, final String p1);
}
