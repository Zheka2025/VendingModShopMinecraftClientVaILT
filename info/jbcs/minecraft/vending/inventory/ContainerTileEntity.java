package info.jbcs.minecraft.vending.inventory;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class ContainerTileEntity<T extends TileEntity> extends Container
{
    public final IInventory playerInventory;
    public final T entity;
    public int playerSlotsCount;
    
    public ContainerTileEntity(final IInventory playerInv, final T tileEntity, final int startX, final int startY) {
        this.playerInventory = playerInv;
        this.entity = tileEntity;
        for (int k = 0; k < 3; ++k) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.func_75146_a(new Slot(playerInv, j1 + k * 9 + 9, startX + j1 * 18, startY + k * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.func_75146_a(new Slot(playerInv, l, startX + l * 18, startY + 142 - 84));
        }
        this.playerSlotsCount = this.field_75151_b.size();
    }
    
    public boolean func_75145_c(final EntityPlayer entityplayer) {
        return ((IInventory)this.entity).func_70300_a(entityplayer);
    }
    
    public ItemStack func_82846_b(final EntityPlayer entityplayer, final int i) {
        ItemStack itemstack = null;
        final Slot slot = this.field_75151_b.get(i);
        if (slot != null && slot.func_75216_d()) {
            final ItemStack itemstack2 = slot.func_75211_c();
            itemstack = itemstack2.func_77946_l();
            if (i < this.playerSlotsCount) {
                if (!this.func_75135_a(itemstack2, this.playerSlotsCount, this.field_75151_b.size(), true)) {
                    return null;
                }
            }
            else if (!this.func_75135_a(itemstack2, 0, this.playerSlotsCount, false)) {
                return null;
            }
            if (itemstack2.field_77994_a == 0) {
                slot.func_75215_d((ItemStack)null);
            }
            else {
                slot.func_75218_e();
            }
        }
        return itemstack;
    }
}
