package info.jbcs.minecraft.vending.tileentity;

import net.minecraft.tileentity.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import info.jbcs.minecraft.vending.inventory.*;
import net.minecraft.entity.player.*;
import java.util.*;
import cpw.mods.ironchest.*;
import info.jbcs.minecraft.vending.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.world.*;

public class TileEntityVendingMachine extends TileEntity implements IInventory, ISidedInventory
{
    public int mode;
    public String ownerName;
    public ItemStack[] sold;
    public ItemStack[] bought;
    public boolean advanced;
    public boolean infinite;
    public boolean eco;
    private static final int[] side0;
    public InventoryStatic inventory;
    public int money;
    public boolean isMoney;
    public int soldPrice;
    public int boughtPrice;
    public int count;
    
    public TileEntityVendingMachine() {
        this.ownerName = "";
        this.sold = new ItemStack[] { null, null, null, null };
        this.bought = new ItemStack[] { null, null, null, null };
        this.advanced = false;
        this.infinite = false;
        this.eco = false;
        this.inventory = new InventoryStatic(14, this) {
            @Override
            public String func_145825_b() {
                return "Vending Machine";
            }
            
            @Override
            public void onInventoryChanged() {
                if (TileEntityVendingMachine.this.field_145850_b == null) {
                    return;
                }
                for (int i = 0; i < TileEntityVendingMachine.this.getSoldItems().length; ++i) {
                    if (!ItemStack.func_77989_b(TileEntityVendingMachine.this.sold[i], TileEntityVendingMachine.this.getSoldItems()[i])) {
                        TileEntityVendingMachine.this.sold[i] = TileEntityVendingMachine.this.getSoldItems()[i];
                        if (TileEntityVendingMachine.this.sold[i] != null) {
                            TileEntityVendingMachine.this.sold[i] = TileEntityVendingMachine.this.sold[i].func_77946_l();
                        }
                        TileEntityVendingMachine.this.field_145850_b.func_147471_g(TileEntityVendingMachine.this.field_145851_c, TileEntityVendingMachine.this.field_145848_d, TileEntityVendingMachine.this.field_145849_e);
                    }
                }
                for (int i = 0; i < TileEntityVendingMachine.this.getBoughtItems().length; ++i) {
                    if (!ItemStack.func_77989_b(TileEntityVendingMachine.this.sold[i], TileEntityVendingMachine.this.getBoughtItems()[i])) {
                        TileEntityVendingMachine.this.bought[i] = TileEntityVendingMachine.this.getBoughtItems()[i];
                        if (TileEntityVendingMachine.this.bought[i] != null) {
                            TileEntityVendingMachine.this.bought[i] = TileEntityVendingMachine.this.bought[i].func_77946_l();
                        }
                        TileEntityVendingMachine.this.field_145850_b.func_147471_g(TileEntityVendingMachine.this.field_145851_c, TileEntityVendingMachine.this.field_145848_d, TileEntityVendingMachine.this.field_145849_e);
                    }
                }
                this.func_70296_d();
            }
            
            public void func_70296_d() {
            }
            
            @Override
            public boolean func_70300_a(final EntityPlayer entityplayer) {
                return TileEntityVendingMachine.this.field_145850_b.func_147438_o(TileEntityVendingMachine.this.field_145851_c, TileEntityVendingMachine.this.field_145848_d, TileEntityVendingMachine.this.field_145849_e) == TileEntityVendingMachine.this && entityplayer.func_70092_e(TileEntityVendingMachine.this.field_145851_c + 0.5, TileEntityVendingMachine.this.field_145848_d + 0.5, TileEntityVendingMachine.this.field_145849_e + 0.5) <= 64.0;
            }
        };
        this.money = 0;
        this.soldPrice = 0;
        this.boughtPrice = 0;
    }
    
    public int findCount(final TileEntity source, final ItemStack itemStack, final boolean simulate) {
        final TileEntity chestTile = this.field_145850_b.func_147438_o(source.field_145851_c, source.field_145848_d - 1, source.field_145849_e);
        if (chestTile == null || !(chestTile instanceof TileEntityIronChest)) {
            return 0;
        }
        final TileEntityIronChest chest = (TileEntityIronChest)chestTile;
        int c = 0;
        for (int i = 0; i < ((IInventory)chest).func_70302_i_(); ++i) {
            final ItemStack stackInSlot = ((IInventory)chest).func_70301_a(i);
            if (stackInSlot == null) {
                return c;
            }
            if (stackInSlot.func_77973_b() == itemStack.func_77973_b() && stackInSlot.func_77960_j() == itemStack.func_77960_j()) {
                c += stackInSlot.field_77994_a;
            }
        }
        return c;
    }
    
    public void func_145845_h() {
        if (this.field_145850_b.field_72995_K && !this.infinite && ClientTicks.ticks == 20) {
            this.updateCount();
        }
    }
    
    public void updateCount() {
        int newCount = 2147483646;
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (final ItemStack itemStack : this.getSoldItems()) {
            if (itemStack != null) {
                list.add(itemStack);
            }
        }
        for (final ItemStack stack : list) {
            final int available = this.findCount(this, stack, true);
            newCount = Math.min(available, newCount);
        }
        this.count = newCount;
    }
    
    public int getCount() {
        if (this.infinite) {
            return -1;
        }
        return this.count;
    }
    
    public int func_70302_i_() {
        return this.inventory.func_70302_i_() + (this.advanced ? -1 : 0);
    }
    
    public ItemStack func_70301_a(final int i) {
        return this.inventory.func_70301_a(i);
    }
    
    public ItemStack[] getSoldItems() {
        if (this.eco) {
            return new ItemStack[] { this.inventory.func_70301_a(9), this.inventory.func_70301_a(10), this.inventory.func_70301_a(11), this.inventory.func_70301_a(12) };
        }
        return new ItemStack[] { this.inventory.func_70301_a(9) };
    }
    
    public ItemStack[] getBoughtItems() {
        return new ItemStack[] { this.inventory.func_70301_a(this.eco ? 13 : 10) };
    }
    
    public void setBoughtItem(final ItemStack stack) {
        this.boughtPrice = EconomyControl.getMinPrice(stack);
        this.inventory.func_70299_a(this.eco ? 13 : 10, stack);
    }
    
    private boolean findAroundChestFit() {
        if (this.field_145850_b.func_147439_a(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e) instanceof BlockIronChest) {
            System.out.println("[debug]its chest");
            final TileEntityIronChest tile = (TileEntityIronChest)this.field_145850_b.func_147438_o(this.field_145851_c, this.field_145848_d - 1, this.field_145849_e);
            for (int content = 0; content < ((IInventory)tile).func_70302_i_(); ++content) {
                System.out.println("[debug]content chest: " + ((IInventory)tile).func_70301_a(content));
                if (((IInventory)tile).func_70301_a(content) == null) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean doesStackFit(final ItemStack itemstack) {
        return (General.countNotNull(this.bought) != 0 || this.eco) && General.countNotNull(this.sold) != 0 && this.findAroundChestFit();
    }
    
    public ItemStack func_70298_a(final int i, final int j) {
        return this.inventory.func_70298_a(i, j);
    }
    
    public void func_70299_a(final int i, final ItemStack itemstack) {
        if ((this.advanced && i == 10) || (this.advanced && this.eco && i == 13)) {
            return;
        }
        this.inventory.func_70299_a(i, itemstack);
    }
    
    public String func_145825_b() {
        return this.inventory.func_145825_b();
    }
    
    public int func_70297_j_() {
        return this.inventory.func_70297_j_();
    }
    
    public boolean func_70300_a(final EntityPlayer entityplayer) {
        return this.inventory.func_70300_a(entityplayer);
    }
    
    public void func_145839_a(final NBTTagCompound nbttagcompound) {
        super.func_145839_a(nbttagcompound);
        this.inventory.clear();
        this.inventory.readFromNBT(nbttagcompound);
        this.ownerName = nbttagcompound.func_74779_i("owner");
        this.advanced = nbttagcompound.func_74767_n("advanced");
        this.infinite = nbttagcompound.func_74767_n("infinite");
        this.eco = nbttagcompound.func_74767_n("eco");
        this.money = nbttagcompound.func_74762_e("money");
        this.soldPrice = nbttagcompound.func_74762_e("soldPrice");
        this.boughtPrice = nbttagcompound.func_74762_e("boughtPrice");
        this.mode = nbttagcompound.func_74762_e("mode");
    }
    
    public void func_145841_b(final NBTTagCompound nbttagcompound) {
        super.func_145841_b(nbttagcompound);
        this.inventory.writeToNBT(nbttagcompound);
        nbttagcompound.func_74778_a("owner", this.ownerName);
        nbttagcompound.func_74757_a("advanced", this.advanced);
        nbttagcompound.func_74757_a("infinite", this.infinite);
        nbttagcompound.func_74757_a("eco", this.eco);
        nbttagcompound.func_74768_a("money", this.money);
        nbttagcompound.func_74768_a("soldPrice", EconomyControl.getMinPrice(this.getSoldItems()));
        nbttagcompound.func_74768_a("boughtPrice", EconomyControl.getMinPrice(this.getBoughtItems()[0]));
        nbttagcompound.func_74768_a("mode", this.mode);
    }
    
    public Packet func_145844_m() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.func_145841_b(var1);
        return (Packet)new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 1, var1);
    }
    
    public void onDataPacket(final NetworkManager net, final S35PacketUpdateTileEntity pkt) {
        this.func_145839_a(pkt.func_148857_g());
        this.updateCount();
    }
    
    public ItemStack func_70304_b(final int var1) {
        return null;
    }
    
    public void func_70295_k_() {
    }
    
    public void func_70305_f() {
    }
    
    public boolean func_145818_k_() {
        return false;
    }
    
    public boolean func_94041_b(final int i, final ItemStack itemstack) {
        return (this.eco || i != 100) && (!this.advanced || !this.eco || i != 13);
    }
    
    public boolean func_102007_a(final int index, final ItemStack stack, final int par3) {
        return false;
    }
    
    public boolean func_102008_b(final int index, final ItemStack stack, final int side) {
        return false;
    }
    
    public int[] func_94128_d(final int side) {
        return TileEntityVendingMachine.side0;
    }
    
    public int getMoney() {
        return this.money;
    }
    
    public void setMoney(final int money) {
        int newMoney = Math.max(0, money);
        if ((int)(Math.log10(newMoney) + 1.0) > 7) {
            newMoney = this.money;
        }
        this.money = newMoney;
    }
    
    static {
        side0 = new int[0];
    }
}
