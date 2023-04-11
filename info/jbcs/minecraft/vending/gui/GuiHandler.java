package info.jbcs.minecraft.vending.gui;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import cpw.mods.fml.common.network.*;
import java.util.*;

public abstract class GuiHandler implements Comparable
{
    static ArrayList<GuiHandler> items;
    int index;
    Object mod;
    String name;
    
    public GuiHandler(final String n) {
        GuiHandler.items.add(this);
        this.name = n;
    }
    
    public void open(final EntityPlayer player, final World world, final int x, final int y, final int z) {
        player.openGui(this.mod, this.index, world, x, y, z);
    }
    
    @Override
    public int compareTo(final Object a) {
        return this.name.compareTo(((GuiHandler)a).name);
    }
    
    public static void register(final Object mod) {
        Collections.sort(GuiHandler.items);
        int index = 0;
        for (final GuiHandler h : GuiHandler.items) {
            h.mod = mod;
            h.index = index++;
        }
        NetworkRegistry.INSTANCE.registerGuiHandler(mod, (IGuiHandler)new IGuiHandler() {
            public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
                if (id < 0 || id >= GuiHandler.items.size()) {
                    return null;
                }
                return GuiHandler.items.get(id).getServerGuiElement(id, player, world, x, y, z);
            }
            
            public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
                if (id < 0 || id >= GuiHandler.items.size()) {
                    return null;
                }
                return GuiHandler.items.get(id).getClientGuiElement(id, player, world, x, y, z);
            }
        });
    }
    
    public abstract Object getServerGuiElement(final int p0, final EntityPlayer p1, final World p2, final int p3, final int p4, final int p5);
    
    public abstract Object getClientGuiElement(final int p0, final EntityPlayer p1, final World p2, final int p3, final int p4, final int p5);
    
    static {
        GuiHandler.items = new ArrayList<GuiHandler>();
    }
}
