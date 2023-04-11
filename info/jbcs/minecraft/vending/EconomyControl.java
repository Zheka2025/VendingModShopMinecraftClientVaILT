package info.jbcs.minecraft.vending;

import java.util.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.registry.*;
import net.minecraftforge.event.entity.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.common.eventhandler.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.common.*;
import net.minecraft.nbt.*;
import java.io.*;

public class EconomyControl
{
    public static boolean hasChanges;
    public static HashMap<CustomStack, Integer> prices;
    
    public static CustomStack loadFromNBT(final NBTTagCompound tag) {
        final CustomStack stack = new CustomStack(tag.func_74779_i("name"), tag.func_74765_d("damage"));
        if (tag.func_74764_b("tag")) {
            stack.tag = tag.func_74775_l("tag");
        }
        return stack;
    }
    
    public static NBTTagCompound writeToNBT(final CustomStack stack) {
        if (stack == null) {
            return null;
        }
        final NBTTagCompound p_77955_1_ = new NBTTagCompound();
        p_77955_1_.func_74778_a("name", stack.name);
        p_77955_1_.func_74777_a("damage", (short)stack.damage);
        if (stack.tag != null) {
            p_77955_1_.func_74782_a("tag", (NBTBase)stack.tag);
        }
        return p_77955_1_;
    }
    
    public static int getMinPrice(final ItemStack[] soldItems) {
        int res = 0;
        for (final ItemStack i : soldItems) {
            res += getMinPrice(i);
        }
        return res;
    }
    
    public static void setMinPrice(final ItemStack currentEquippedItem, final int price) {
        EconomyControl.prices.put(new CustomStack(currentEquippedItem), price);
    }
    
    public static void setNBTTagCompoud(final NBTTagList prices2) {
        for (int i = 0; i < prices2.func_74745_c(); ++i) {
            final NBTTagCompound tag = prices2.func_150305_b(i);
            final int price = tag.func_74762_e("price");
            EconomyControl.prices.put(loadFromNBT(tag), price);
        }
    }
    
    public static NBTTagCompound getNbtTagCompound() {
        final NBTTagList prices2 = new NBTTagList();
        for (final Map.Entry<CustomStack, Integer> i : EconomyControl.prices.entrySet()) {
            final CustomStack stack = i.getKey();
            final NBTTagCompound tag = writeToNBT(stack);
            tag.func_74768_a("price", (int)i.getValue());
            prices2.func_74742_a((NBTBase)tag);
        }
        final NBTTagCompound root = new NBTTagCompound();
        root.func_74782_a("list", (NBTBase)prices2);
        return root;
    }
    
    public static Item getItem(final String s) {
        String modId = "minecraft";
        if (s.indexOf(":") != -1) {
            modId = s.substring(0, s.indexOf(":"));
        }
        final String name = s.substring(s.indexOf(":") + 1);
        return GameRegistry.findItem(modId, name);
    }
    
    public static int getMinPrice(final ItemStack stack) {
        if (stack != null) {
            final CustomStack st = new CustomStack(stack);
            return EconomyControl.prices.containsKey(st) ? EconomyControl.prices.get(st) : 0;
        }
        return 0;
    }
    
    static {
        EconomyControl.prices = new HashMap<CustomStack, Integer>();
    }
    
    public static class EventLoad
    {
        @SubscribeEvent
        public void onLogin(final EntityJoinWorldEvent e) {
            if (e.entity instanceof EntityPlayer) {}
        }
        
        @SubscribeEvent
        public void onSave(final WorldEvent.Save e) {
            if (!e.world.field_72995_K && e.world.field_73011_w.field_76574_g == 0) {
                try {
                    final String path = DimensionManager.getCurrentSaveRootDirectory().getCanonicalPath() + "/prices/";
                    final File configFolder = new File(path);
                    configFolder.mkdir();
                    final NBTTagCompound root = EconomyControl.getNbtTagCompound();
                    final FileOutputStream fos = new FileOutputStream(path + "prices.data");
                    CompressedStreamTools.func_74799_a(root, (OutputStream)fos);
                    fos.close();
                }
                catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
        
        @SubscribeEvent
        public void onLoad(final WorldEvent.Load e) {
            if (!e.world.field_72995_K && e.world.field_73011_w.field_76574_g == 0) {
                try {
                    final String path = DimensionManager.getCurrentSaveRootDirectory().getCanonicalPath() + "/prices/";
                    final FileInputStream fis = new FileInputStream(path + "prices.data");
                    final NBTTagList prices2 = (NBTTagList)CompressedStreamTools.func_74796_a((InputStream)fis).func_74781_a("list");
                    fis.close();
                    EconomyControl.setNBTTagCompoud(prices2);
                }
                catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                }
                catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
        }
    }
}
