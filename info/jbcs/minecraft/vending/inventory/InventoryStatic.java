package info.jbcs.minecraft.vending.inventory;

import net.minecraft.inventory.*;
import info.jbcs.minecraft.vending.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import cpw.mods.ironchest.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public abstract class InventoryStatic implements IInventory
{
    public final ItemStack[] items;
    TileEntityVendingMachine tile;
    
    public InventoryStatic(final int size) {
        this.items = new ItemStack[size];
    }
    
    public InventoryStatic(final int size, final TileEntityVendingMachine tile) {
        this.items = new ItemStack[size];
        this.tile = tile;
    }
    
    public String func_145825_b() {
        return null;
    }
    
    public boolean func_70300_a(final EntityPlayer entityplayer) {
        return false;
    }
    
    public void onInventoryChanged(final int slot) {
    }
    
    public int func_70302_i_() {
        return this.items.length;
    }
    
    public ItemStack func_70301_a(final int i) {
        return this.items[i];
    }
    
    public ItemStack func_70298_a(final int i, final int j) {
        if (this.items[i] == null) {
            return null;
        }
        if (this.items[i].field_77994_a <= j) {
            final ItemStack itemstack = this.items[i];
            this.items[i] = null;
            this.onInventoryChanged();
            this.onInventoryChanged(i);
            return itemstack;
        }
        final ItemStack itemstack2 = this.items[i].func_77979_a(j);
        if (this.items[i].field_77994_a == 0) {
            this.items[i] = null;
        }
        this.onInventoryChanged();
        this.onInventoryChanged(i);
        return itemstack2;
    }
    
    public void func_70299_a(final int i, final ItemStack itemstack) {
        this.items[i] = itemstack;
        if (itemstack != null && itemstack.field_77994_a > this.func_70297_j_()) {
            itemstack.field_77994_a = this.func_70297_j_();
        }
        this.onInventoryChanged();
        this.onInventoryChanged(i);
    }
    
    public int func_70297_j_() {
        return 64;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        final NBTTagList nbtTagList = nbtTagCompound.func_150295_c("Items", 10);
        for (int i = 0; i < nbtTagList.func_74745_c(); ++i) {
            final NBTTagCompound nbtTagCompound2 = nbtTagList.func_150305_b(i);
            final int j = nbtTagCompound2.func_74771_c("slot") & 0xFF;
            this.items[j] = ItemStack.func_77949_a(nbtTagCompound2);
        }
        this.onInventoryChanged();
    }
    
    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        final NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != null) {
                final NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound2.func_74774_a("slot", (byte)i);
                this.items[i].func_77955_b(nbttagcompound2);
                nbttaglist.func_74742_a((NBTBase)nbttagcompound2);
            }
        }
        nbttagcompound.func_74782_a("Items", (NBTBase)nbttaglist);
    }
    
    private int getFirstEmptyStack(final int start, final int end) {
        for (int i = start; i <= end; ++i) {
            if (this.items[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    private int storeItemStack(final ItemStack itemstack, final int start, final int end) {
        for (int i = start; i <= end; ++i) {
            if (this.items[i] != null && this.items[i].func_77973_b() == itemstack.func_77973_b() && this.items[i].func_77985_e() && this.items[i].field_77994_a < this.items[i].func_77976_d() && this.items[i].field_77994_a < this.func_70297_j_() && (!this.items[i].func_77981_g() || this.items[i].func_77960_j() == itemstack.func_77960_j())) {
                if (!this.items[i].func_77942_o() && !itemstack.func_77942_o()) {
                    return i;
                }
                if (this.items[i].func_77942_o() && itemstack.func_77942_o() && this.items[i].func_77978_p().equals((Object)itemstack.func_77978_p())) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private int storePartialItemStack(final ItemStack itemstack, final int start, final int end) {
        final Item i = itemstack.func_77973_b();
        int j = itemstack.field_77994_a;
        int k = this.storeItemStack(itemstack, start, end);
        if (k < 0) {
            k = this.getFirstEmptyStack(start, end);
        }
        if (k < 0) {
            return j;
        }
        if (this.items[k] == null) {
            this.items[k] = new ItemStack(i, 0, itemstack.func_77960_j());
            if (itemstack.func_77942_o()) {
                this.items[k].func_77982_d(itemstack.func_77978_p());
            }
        }
        int l = j;
        if (l > this.items[k].func_77976_d() - this.items[k].field_77994_a) {
            l = this.items[k].func_77976_d() - this.items[k].field_77994_a;
        }
        if (l > this.func_70297_j_() - this.items[k].field_77994_a) {
            l = this.func_70297_j_() - this.items[k].field_77994_a;
        }
        if (l == 0) {
            return j;
        }
        j -= l;
        final ItemStack itemStack = this.items[k];
        itemStack.field_77994_a += l;
        this.items[k].field_77992_b = 5;
        this.onInventoryChanged();
        this.onInventoryChanged(k);
        return j;
    }
    
    public boolean addItemStackToInventory(final ItemStack itemstack, final int start, final int end) {
        if (itemstack == null) {
            return true;
        }
        if (!itemstack.func_77951_h()) {
            int i;
            do {
                i = itemstack.field_77994_a;
                itemstack.field_77994_a = this.storePartialItemStack(itemstack, start, end);
            } while (itemstack.field_77994_a > 0 && itemstack.field_77994_a < i);
            return itemstack.field_77994_a < i;
        }
        final int j = this.getFirstEmptyStack(start, end);
        if (j >= 0) {
            this.items[j] = ItemStack.func_77944_b(itemstack);
            if (itemstack.func_77942_o()) {
                this.items[j].func_77982_d(itemstack.func_77978_p());
            }
            this.items[j].field_77992_b = 5;
            itemstack.field_77994_a = 0;
            this.onInventoryChanged();
            this.onInventoryChanged(j);
            return true;
        }
        return false;
    }
    
    public boolean addItemStackToInventory(final ItemStack itemstack) {
        return this.addItemStackToInventory(itemstack, 0, this.items.length - 1);
    }
    
    public boolean takeItemsFromChest(final ItemStack itemStack, final int damage, final int count) {
        ItemStack res = null;
        final ItemStack[] chest = this.getChest();
        if (chest == null) {
            return false;
        }
        for (int i = 0; i < chest.length; ++i) {
            if (chest[i] != null && chest[i].func_77973_b() == itemStack.func_77973_b()) {
                if (chest[i].func_77960_j() == damage) {
                    if (!itemStack.func_77942_o() || itemStack.func_77978_p().equals((Object)chest[i].func_77978_p())) {
                        if (res == null) {
                            res = new ItemStack(itemStack.func_77973_b(), 0, damage);
                        }
                        while (chest[i] != null && res.field_77994_a < count && chest[i].field_77994_a > 0) {
                            final ItemStack itemStack2 = res;
                            ++itemStack2.field_77994_a;
                            final ItemStack itemStack3 = chest[i];
                            --itemStack3.field_77994_a;
                            if (chest[i].field_77994_a == 0) {
                                chest[i] = null;
                            }
                        }
                        if (res.field_77994_a >= count) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private ItemStack[] getChest() {
        if (this.tile == null) {
            return null;
        }
        final TileEntity tileEntity = this.tile.func_145831_w().func_147438_o(this.tile.field_145851_c, this.tile.field_145848_d - 1, this.tile.field_145849_e);
        if (tileEntity instanceof TileEntityIronChest) {
            return ((TileEntityIronChest)tileEntity).getContents();
        }
        return null;
    }
    
    public ItemStack func_70304_b(final int i) {
        return null;
    }
    
    public boolean func_145818_k_() {
        return true;
    }
    
    public void onInventoryChanged() {
    }
    
    public void func_70295_k_() {
    }
    
    public void func_70305_f() {
    }
    
    public boolean func_94041_b(final int i, final ItemStack itemstack) {
        return true;
    }
    
    public boolean isEmpty() {
        for (final ItemStack item : this.items) {
            if (item != null) {
                return false;
            }
        }
        return true;
    }
    
    public void clear() {
        for (int i = 0; i < this.items.length; ++i) {
            this.items[i] = null;
        }
    }
    
    public void throwItems(final World world, final int x, final int y, final int z) {
        if (world.field_72995_K) {
            return;
        }
        for (int i = 0; i < this.items.length; ++i) {
            final ItemStack itemstack = this.items[i];
            if (itemstack != null) {
                this.items[i] = null;
                final float xx = world.field_73012_v.nextFloat() * 0.8f + 0.1f;
                final float yy = world.field_73012_v.nextFloat() * 0.8f + 0.1f;
                final float zz = world.field_73012_v.nextFloat() * 0.8f + 0.1f;
                while (itemstack.field_77994_a > 0) {
                    int c = world.field_73012_v.nextInt(21) + 10;
                    if (c > itemstack.field_77994_a) {
                        c = itemstack.field_77994_a;
                    }
                    final ItemStack itemStack = itemstack;
                    itemStack.field_77994_a -= c;
                    final EntityItem entityitem = new EntityItem(world, (double)(x + xx), (double)(y + yy), (double)(z + zz), new ItemStack(itemstack.func_77973_b(), c, itemstack.func_77960_j()));
                    final float f3 = 0.05f;
                    entityitem.field_70159_w = (float)world.field_73012_v.nextGaussian() * f3;
                    entityitem.field_70181_x = (float)world.field_73012_v.nextGaussian() * f3 + 0.2f;
                    entityitem.field_70179_y = (float)world.field_73012_v.nextGaussian() * f3;
                    world.func_72838_d((Entity)entityitem);
                }
            }
        }
        this.onInventoryChanged();
    }
}
