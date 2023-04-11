package info.jbcs.minecraft.vending.inventory;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class SlotAdvancedVendingMachine extends Slot
{
    public SlotAdvancedVendingMachine(final IInventory inventory, final int index, final int x, final int y) {
        super(inventory, index, x, y);
    }
    
    public void func_82870_a(final EntityPlayer player, final ItemStack itemstack) {
        super.func_82870_a(player, itemstack);
        player.field_71071_by.func_70437_b((ItemStack)null);
        this.func_75215_d(new ItemStack(itemstack.func_77973_b(), itemstack.field_77994_a, itemstack.func_77960_j()));
    }
    
    public IIcon func_75212_b() {
        if (this.field_75224_c.func_70301_a(10) != null) {
            return this.field_75224_c.func_70301_a(10).func_77954_c();
        }
        return null;
    }
    
    public boolean func_82869_a(final EntityPlayer par1EntityPlayer) {
        return false;
    }
    
    public boolean func_75214_a(final ItemStack par1ItemStack) {
        return false;
    }
}
