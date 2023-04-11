package info.jbcs.minecraft.vending;

import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import cpw.mods.fml.common.registry.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class General
{
    public static Random rand;
    static HashMap<String, Block> blockMapping;
    
    public static void propelTowards(final Entity what, final Entity whereTo, final double force) {
        final double dx = whereTo.field_70165_t - what.field_70165_t;
        final double dy = whereTo.field_70163_u - what.field_70163_u;
        final double dz = whereTo.field_70161_v - what.field_70161_v;
        final double total = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (total == 0.0) {
            what.field_70159_w = 0.0;
            what.field_70181_x = 0.0;
            what.field_70179_y = 0.0;
        }
        else {
            what.field_70159_w = dx / total * force;
            what.field_70181_x = dy / total * force;
            what.field_70179_y = dz / total * force;
        }
    }
    
    public static boolean isInRange(final double distance, final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double x3 = x1 - x2;
        final double y3 = y1 - y2;
        final double z3 = z1 - z2;
        return x3 * x3 + y3 * y3 + z3 * z3 < distance * distance;
    }
    
    public static Item getItem(final ItemStack stack) {
        if (stack == null) {
            return null;
        }
        return stack.func_77973_b();
    }
    
    public static Item getItem(final int itemId) {
        final Item item = (Item)GameData.getItemRegistry().func_148754_a(itemId);
        return item;
    }
    
    public static Integer getItemId(final Item item) {
        return GameData.getItemRegistry().getId((Object)item);
    }
    
    public static String getUnlocalizedName(final Block block) {
        String name = block.func_149739_a();
        if (name.startsWith("tile.")) {
            name = name.substring(5);
        }
        return name;
    }
    
    public static Block getBlock(final String s, final Block fallback) {
        final Set blockReg = GameData.getBlockRegistry().func_148742_b();
        final List<String> blockList = new ArrayList<String>();
        blockList.addAll(blockReg);
        final String[] blockNames = blockList.toArray(new String[0]);
        if (General.blockMapping == null) {
            General.blockMapping = new HashMap<String, Block>();
            for (int i = 0; i < blockList.size(); ++i) {
                final Block block = Block.func_149684_b(blockNames[i]);
                if (block != null) {
                    String name = block.func_149739_a();
                    if (name.startsWith("tile.")) {
                        name = name.substring(5);
                    }
                    General.blockMapping.put(name.toLowerCase(), block);
                }
            }
        }
        final Block res = General.blockMapping.get(s.toLowerCase());
        if (res == null) {
            return fallback;
        }
        return res;
    }
    
    public static Block getBlock(final String s) {
        return getBlock(s, Blocks.field_150348_b);
    }
    
    public static String getName(final Block block) {
        final String res = block.func_149739_a();
        return res.substring(5);
    }
    
    public static MovingObjectPosition getMovingObjectPositionFromPlayer(final World par1World, final EntityPlayer par2EntityPlayer, final boolean par3) {
        final float var4 = 1.0f;
        final float var5 = par2EntityPlayer.field_70127_C + (par2EntityPlayer.field_70125_A - par2EntityPlayer.field_70127_C) * var4;
        final float var6 = par2EntityPlayer.field_70126_B + (par2EntityPlayer.field_70177_z - par2EntityPlayer.field_70126_B) * var4;
        final double var7 = par2EntityPlayer.field_70169_q + (par2EntityPlayer.field_70165_t - par2EntityPlayer.field_70169_q) * var4;
        final double var8 = par2EntityPlayer.field_70167_r + (par2EntityPlayer.field_70163_u - par2EntityPlayer.field_70167_r) * var4 + 1.62 - par2EntityPlayer.field_70129_M;
        final double var9 = par2EntityPlayer.field_70166_s + (par2EntityPlayer.field_70161_v - par2EntityPlayer.field_70166_s) * var4;
        final Vec3 var10 = Vec3.func_72443_a(var7, var8, var9);
        final float var11 = MathHelper.func_76134_b(-var6 * 0.017453292f - 3.1415927f);
        final float var12 = MathHelper.func_76126_a(-var6 * 0.017453292f - 3.1415927f);
        final float var13 = -MathHelper.func_76134_b(-var5 * 0.017453292f);
        final float var14 = MathHelper.func_76126_a(-var5 * 0.017453292f);
        final float var15 = var12 * var13;
        final float var16 = var11 * var13;
        double var17 = 5.0;
        if (par2EntityPlayer instanceof EntityPlayerMP) {
            var17 = ((EntityPlayerMP)par2EntityPlayer).field_71134_c.getBlockReachDistance();
        }
        final Vec3 var18 = var10.func_72441_c(var15 * var17, var14 * var17, var16 * var17);
        return par1World.func_72901_a(var10, var18, par3);
    }
    
    public static int countNotNull(final ItemStack[] itemStacks) {
        int counter = 0;
        for (final ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                ++counter;
            }
        }
        for (final ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                ++counter;
            }
        }
        return counter;
    }
    
    public static ItemStack getNotNull(final ItemStack[] itemStacks, final int num) {
        if (itemStacks.length == 1) {
            return itemStacks[0];
        }
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (final ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                list.add(itemStack);
            }
        }
        return (num < list.size()) ? list.get(num) : null;
    }
    
    static {
        General.rand = new Random();
    }
}
