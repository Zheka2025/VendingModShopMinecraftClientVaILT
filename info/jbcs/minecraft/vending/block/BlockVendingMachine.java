package info.jbcs.minecraft.vending.block;

import net.minecraft.block.*;
import net.minecraft.world.*;
import info.jbcs.minecraft.vending.tileentity.*;
import info.jbcs.minecraft.vending.*;
import org.lwjgl.opengl.*;
import cpw.mods.fml.relauncher.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import cpw.mods.ironchest.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import info.jbcs.minecraft.vending.renderer.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockVendingMachine extends BlockContainer
{
    Block[] supportBlocks;
    boolean isAdvanced;
    boolean isEco;
    IIcon IIconTop;
    IIcon IIconSide;
    
    @SideOnly(Side.CLIENT)
    public void func_149734_b(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (p_149734_1_.field_72995_K) {
            final TileEntityVendingMachine machine = (TileEntityVendingMachine)p_149734_1_.func_147438_o(p_149734_2_, p_149734_3_, p_149734_4_);
            final int minPriceSold = EconomyControl.getMinPrice(machine.getSoldItems());
            final int minPriceBought = (p_149734_1_.func_72805_g(p_149734_2_, p_149734_3_, p_149734_4_) == 8) ? machine.getMoney() : EconomyControl.getMinPrice(machine.getBoughtItems()[0]);
            if (minPriceSold > minPriceBought * Vending.priceMultiplier) {
                GL11.glPushMatrix();
                GL11.glColor3d(255.0, 0.0, 0.0);
                p_149734_1_.func_72869_a("reddust", p_149734_2_ + 0.5, (double)(p_149734_3_ + 1), p_149734_4_ + 0.5, 0.0, 1.0, 0.0);
                GL11.glPopMatrix();
            }
        }
    }
    
    public BlockVendingMachine(final Block[] supports, final boolean advanced, final boolean eco) {
        super(Material.field_151592_s);
        this.func_149663_c("vendingMachine");
        this.supportBlocks = supports;
        this.func_149672_a(Block.field_149778_k);
        this.func_149647_a(Vending.tabVending);
        this.func_149711_c(0.3f);
        this.func_149752_b(6000000.0f);
        this.func_149722_s();
        this.func_149672_a(Block.field_149778_k);
        this.func_149676_a(0.0625f, 0.125f, 0.0625f, 0.9375f, 0.9375f, 0.9375f);
        this.isAdvanced = advanced;
        this.isEco = eco;
    }
    
    void vend(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer) {
        final TileEntityVendingMachine tileEntity = (TileEntityVendingMachine)world.func_147438_o(i, j, k);
        if (tileEntity == null) {
            return;
        }
        final TileEntity chestTile = world.func_147438_o(tileEntity.field_145851_c, tileEntity.field_145848_d - 1, tileEntity.field_145849_e);
        if (chestTile == null || !(chestTile instanceof TileEntityIronChest)) {
            return;
        }
        final TileEntityIronChest chest = (TileEntityIronChest)chestTile;
        final ItemStack soldItems = tileEntity.getSoldItems()[0];
        final int minPriceSold = EconomyControl.getMinPrice(soldItems);
        if (tileEntity.eco) {}
        final ItemStack bought = tileEntity.getBoughtItems()[0];
        final int minPriceBought = EconomyControl.getMinPrice(bought);
        final ItemStack offered = entityplayer.field_71071_by.func_70448_g();
        boolean fits = true;
        if (bought == null || soldItems == null) {
            fits = false;
        }
        else {
            tileEntity.inventory.onInventoryChanged();
            if (offered == null) {
                fits = false;
            }
            else if (bought.func_77973_b() != offered.func_77973_b()) {
                fits = false;
            }
            else if (bought.func_77942_o() || offered.func_77942_o()) {
                if (bought.func_77942_o() && offered.func_77942_o()) {
                    if (!bought.func_77978_p().equals((Object)offered.func_77978_p())) {
                        fits = false;
                    }
                }
                else {
                    fits = false;
                }
            }
            else if (bought.func_77960_j() != offered.func_77960_j()) {
                fits = false;
            }
            else if (offered.field_77994_a < bought.field_77994_a) {
                fits = false;
            }
            if (!fits && !world.field_72995_K) {
                entityplayer.func_146105_b((IChatComponent)new ChatComponentText("\u0427\u0442\u043e\u0431\u044b \u043a\u0443\u043f\u0438\u0442\u044c \u044d\u0442\u043e\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442, \u0432\u043e\u0437\u044c\u043c\u0438\u0442\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442 \u0432 \u0440\u0443\u043a\u0443!"));
            }
            if (!tileEntity.doesStackFit(bought)) {
                fits = false;
            }
        }
        if (fits && minPriceSold > minPriceBought * Vending.priceMultiplier) {
            fits = false;
            entityplayer.func_146105_b((IChatComponent)new ChatComponentText("\u0421\u043b\u0438\u0448\u043a\u043e\u043c \u043d\u0438\u0437\u043a\u0430\u044f \u0446\u0435\u043d\u0430"));
        }
        if (!world.field_72995_K) {
            if (fits) {
                final boolean canGive = this.givestack(world, i, j, k, entityplayer, tileEntity, soldItems);
                world.func_72908_a((double)i, (double)j, (double)k, "vending:cha-ching", 0.3f, 0.6f);
                if (canGive && offered != null) {
                    final ItemStack paid = offered.func_77979_a(bought.field_77994_a);
                    if (offered.field_77994_a == 0) {
                        entityplayer.field_71071_by.field_70462_a[entityplayer.field_71071_by.field_70461_c] = null;
                    }
                    if (!tileEntity.infinite) {
                        putInInventory((IInventory)chest, paid, false);
                    }
                    entityplayer.func_146105_b((IChatComponent)new ChatComponentText("\u0421\u043f\u0430\u0441\u0438\u0431\u043e \u0437\u0430 \u043f\u043e\u043a\u0443\u043f\u043a\u0443"));
                }
                if (!tileEntity.infinite) {
                    tileEntity.inventory.onInventoryChanged();
                }
            }
            else {
                world.func_72908_a((double)i, (double)j, (double)k, "vending:forbidden", 1.0f, 1.0f);
            }
        }
        tileEntity.updateCount();
    }
    
    public static int putInInventory(final IInventory inv, final ItemStack itemStackSource, final boolean simulate) {
        if (itemStackSource == null) {
            return 0;
        }
        int toTransfer = itemStackSource.field_77994_a;
        for (int i = 0; i < inv.func_70302_i_() && toTransfer > 0; ++i) {
            if (inv.func_94041_b(i, itemStackSource) && (!(inv instanceof ISidedInventory) || ((ISidedInventory)inv).func_102007_a(i, itemStackSource, 0))) {
                final ItemStack itemStack = inv.func_70301_a(i);
                if (itemStack != null && equalsStack(itemStack, itemStackSource)) {
                    final int transfer = Math.min(toTransfer, Math.min(inv.func_70297_j_(), itemStack.func_77976_d()) - itemStack.field_77994_a);
                    if (!simulate) {
                        final ItemStack itemStack2 = itemStack;
                        itemStack2.field_77994_a += transfer;
                    }
                    toTransfer -= transfer;
                }
            }
        }
        for (int i = 0; i < inv.func_70302_i_() && toTransfer > 0; ++i) {
            if (inv.func_94041_b(i, itemStackSource) && (!(inv instanceof ISidedInventory) || ((ISidedInventory)inv).func_102007_a(i, itemStackSource, 0))) {
                final ItemStack itemStack = inv.func_70301_a(i);
                if (itemStack == null) {
                    final int transfer = Math.min(toTransfer, Math.min(inv.func_70297_j_(), itemStackSource.func_77976_d()));
                    if (!simulate) {
                        final ItemStack dest = copyWithSize(itemStackSource, transfer);
                        inv.func_70299_a(i, dest);
                    }
                    toTransfer -= transfer;
                }
            }
        }
        if (!simulate && toTransfer != itemStackSource.field_77994_a) {
            inv.func_70296_d();
        }
        return itemStackSource.field_77994_a - toTransfer;
    }
    
    public static ItemStack copyWithSize(final ItemStack itemStack, final int newSize) {
        final ItemStack ret = itemStack.func_77946_l();
        ret.field_77994_a = newSize;
        return ret;
    }
    
    private static boolean equalsStack(final ItemStack itemStack, final ItemStack itemStackSource) {
        return itemStack.func_77973_b() == itemStackSource.func_77973_b() && itemStack.func_77960_j() == itemStackSource.func_77960_j() && (!itemStack.func_77942_o() || !itemStackSource.func_77942_o() || itemStack.func_77978_p().equals((Object)itemStackSource.func_77978_p()));
    }
    
    private boolean givestack(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final TileEntityVendingMachine tileEntity, final ItemStack soldItems) {
        if (soldItems != null) {
            boolean res = true;
            if (!tileEntity.infinite) {
                res = tileEntity.inventory.takeItemsFromChest(soldItems, soldItems.func_77960_j(), soldItems.field_77994_a);
            }
            if (res) {
                final NBTTagCompound tag = new NBTTagCompound();
                soldItems.func_77955_b(tag);
                final ItemStack vended = ItemStack.func_77949_a(tag);
                if (!entityplayer.field_71071_by.func_70441_a(vended)) {
                    entityplayer.func_146105_b((IChatComponent)new ChatComponentText("\u0412\u0430\u0448 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044c \u0437\u0430\u043f\u043e\u043b\u043d\u0435\u043d!"));
                    final TileEntity chestTile = world.func_147438_o(tileEntity.field_145851_c, tileEntity.field_145848_d - 1, tileEntity.field_145849_e);
                    final TileEntityIronChest chest = (TileEntityIronChest)chestTile;
                    putInInventory((IInventory)chest, soldItems, false);
                    return false;
                }
            }
            else {
                entityplayer.func_146105_b((IChatComponent)new ChatComponentText("\u0423\u043f\u0441\u0441\u0441, \u0435\u0442 \u0432 \u043d\u0430\u043b\u0438\u0447\u0438\u0438"));
            }
            return res;
        }
        return false;
    }
    
    public void func_149699_a(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer) {
        final TileEntityVendingMachine tileEntity = (TileEntityVendingMachine)world.func_147438_o(i, j, k);
        if (tileEntity == null) {
            return;
        }
        if (!entityplayer.getDisplayName().equals(tileEntity.ownerName) || !tileEntity.inventory.isEmpty() || this.findAroundChest(world, i, j, k)) {
            this.vend(world, i, j, k, entityplayer);
            return;
        }
        this.func_149697_b(world, i, j, k, world.func_72805_g(i, j, k), 0);
        world.func_147449_b(i, j, k, Blocks.field_150350_a);
        world.func_72908_a((double)i, (double)j, (double)k, "vending:cha-ching", 0.3f, 0.6f);
    }
    
    private boolean findAroundChest(final World world, final int x, final int y, final int z) {
        if (world.func_147439_a(x, y - 1, z) instanceof BlockIronChest) {
            final TileEntityIronChest tile = (TileEntityIronChest)world.func_147438_o(x, y - 1, z);
            final ItemStack[] contents2;
            final ItemStack[] contents = contents2 = tile.getContents();
            for (final ItemStack content : contents2) {
                if (content != null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean func_149727_a(final World world, final int i, final int j, final int k, final EntityPlayer entityplayer, final int a, final float b, final float x, final float y) {
        final TileEntityVendingMachine tileEntity = (TileEntityVendingMachine)world.func_147438_o(i, j, k);
        if (tileEntity == null) {
            return false;
        }
        if (entityplayer.field_71071_by.func_70448_g() != null && entityplayer.field_71071_by.func_70448_g().func_77973_b() == Vending.itemWrench) {
            Vending.guiWrench.open(entityplayer, world, i, j, k);
            return true;
        }
        if (entityplayer.getDisplayName().equals(tileEntity.ownerName) && !entityplayer.func_70093_af()) {
            Vending.guiVending.open(entityplayer, world, i, j, k);
            return true;
        }
        if (entityplayer.field_71075_bZ.field_75098_d && !entityplayer.func_70093_af()) {
            Vending.guiVending.open(entityplayer, world, i, j, k);
            return true;
        }
        if (world.field_72995_K) {
            entityplayer.func_146105_b((IChatComponent)new ChatComponentText("\u0427\u0442\u043e\u0431\u044b \u043a\u0443\u043f\u0438\u0442\u044c \u044d\u0442\u043e\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442, \u043d\u0430\u0436\u043c\u0438\u0442\u0435 \u041b\u041a\u041c \u0434\u0435\u0440\u0436\u0430 \u043f\u0440\u043e\u0434\u0430\u0432\u0430\u0435\u043c\u044b\u0439 \u043f\u0440\u0435\u0434\u043c\u0435\u0442 \u0432 \u0440\u0443\u043a\u0435!"));
        }
        return true;
    }
    
    public void func_149689_a(final World world, final int i, final int j, final int k, final EntityLivingBase entityliving, final ItemStack stack) {
        final TileEntityVendingMachine e = new TileEntityVendingMachine();
        if (world.func_72805_g(i, j, k) == 8) {
            e.isMoney = true;
        }
        e.advanced = this.isAdvanced;
        e.eco = this.isEco;
        if (entityliving != null) {
            final EntityPlayer player = (EntityPlayer)entityliving;
            e.ownerName = player.getDisplayName();
            world.func_147455_a(i, j, k, (TileEntity)e);
        }
    }
    
    public int func_149701_w() {
        return 0;
    }
    
    public IIcon func_149691_a(final int side, final int meta) {
        return (side < 2) ? this.IIconTop : this.IIconSide;
    }
    
    public TileEntity func_149915_a(final World var1, final int metadata) {
        final TileEntityVendingMachine e = new TileEntityVendingMachine();
        if (metadata == 8) {
            e.isMoney = true;
        }
        e.advanced = this.isAdvanced;
        e.eco = this.isEco;
        return e;
    }
    
    public void func_149749_a(final World world, final int i, final int j, final int k, final Block a, final int b) {
        final TileEntityVendingMachine tileentitychest = (TileEntityVendingMachine)world.func_147438_o(i, j, k);
        if (tileentitychest == null) {
            return;
        }
        for (int l = 0; l < tileentitychest.func_70302_i_(); ++l) {
            final ItemStack itemstack = tileentitychest.func_70301_a(l);
            if (itemstack != null) {
                final float f = world.field_73012_v.nextFloat() * 0.8f + 0.1f;
                final float f2 = world.field_73012_v.nextFloat() * 0.8f + 0.1f;
                final float f3 = world.field_73012_v.nextFloat() * 0.8f + 0.1f;
                while (itemstack.field_77994_a > 0) {
                    int i2 = world.field_73012_v.nextInt(21) + 10;
                    if (i2 > itemstack.field_77994_a) {
                        i2 = itemstack.field_77994_a;
                    }
                    final ItemStack itemStack = itemstack;
                    itemStack.field_77994_a -= i2;
                    final EntityItem entityitem = new EntityItem(world, (double)(i + f), (double)(j + f2), (double)(k + f3), new ItemStack(itemstack.func_77973_b(), i2, itemstack.func_77960_j()));
                    final float f4 = 0.05f;
                    entityitem.field_70159_w = (float)world.field_73012_v.nextGaussian() * f4;
                    entityitem.field_70181_x = (float)world.field_73012_v.nextGaussian() * f4 + 0.2f;
                    entityitem.field_70179_y = (float)world.field_73012_v.nextGaussian() * f4;
                    world.func_72838_d((Entity)entityitem);
                }
            }
        }
        super.func_149749_a(world, i, j, k, a, b);
    }
    
    public boolean func_149662_c() {
        return false;
    }
    
    public int func_149692_a(final int i) {
        return i;
    }
    
    public void func_149651_a(final IIconRegister register) {
        this.IIconTop = register.func_94245_a("Vending:vendor-top");
        this.IIconSide = register.func_94245_a("Vending:vendor-side");
    }
    
    public int func_149645_b() {
        return BlockVendingMachineRenderer.id;
    }
    
    public void func_149666_a(final Item item, final CreativeTabs par2CreativeTabs, final List list) {
        for (int i = 0; i < this.supportBlocks.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
