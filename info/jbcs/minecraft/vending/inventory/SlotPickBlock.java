package info.jbcs.minecraft.vending.inventory;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class SlotPickBlock extends Slot
{
    ContainerPickBlock container;
    
    public SlotPickBlock(final ContainerPickBlock c, final int index, final int x, final int y) {
        super((IInventory)c.inventory, index, x, y);
        this.container = c;
    }
    
    void click(final EntityPlayer player, final ItemStack itemstack, final int count) {
        player.field_71071_by.func_70437_b((ItemStack)null);
        if (itemstack == null) {
            return;
        }
        if (this.container.gui == null) {
            return;
        }
        this.func_75215_d(new ItemStack(itemstack.func_77973_b(), itemstack.field_77994_a, itemstack.func_77960_j()));
        int newSize;
        if (this.container.resultSlot == this) {
            newSize = itemstack.field_77994_a - count;
        }
        else {
            newSize = itemstack.field_77994_a;
            final ItemStack otherstack = this.container.resultSlot.func_75211_c();
            if (otherstack != null && otherstack.func_77973_b() == itemstack.func_77973_b() && otherstack.func_77960_j() == itemstack.func_77960_j()) {
                newSize = otherstack.field_77994_a + count;
            }
            else {
                newSize = count;
            }
        }
        if (newSize > 64) {
            newSize = 64;
        }
        this.container.resultSlot.func_75215_d((newSize <= 0) ? null : new ItemStack(itemstack.func_77973_b(), newSize, itemstack.func_77960_j()));
    }
    
    public void func_82870_a(final EntityPlayer player, final ItemStack itemstack) {
        super.func_82870_a(player, itemstack);
        this.click(player, itemstack, 1);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer player) {
        this.click(player, this.func_75211_c(), 64);
        return null;
    }
}
