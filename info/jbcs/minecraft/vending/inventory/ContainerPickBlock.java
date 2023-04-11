package info.jbcs.minecraft.vending.inventory;

import info.jbcs.minecraft.vending.gui.*;
import net.minecraft.entity.player.*;
import cpw.mods.fml.common.registry.*;
import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.inventory.*;
import java.util.*;

public class ContainerPickBlock extends Container
{
    public ArrayList<ItemStack> items;
    public GuiPickBlock gui;
    EntityPlayer player;
    public int width;
    public int height;
    public SlotPickBlock resultSlot;
    public InventoryStatic inventory;
    
    public ContainerPickBlock(final EntityPlayer p) {
        this.items = new ArrayList<ItemStack>();
        this.width = 9;
        this.height = 7;
        this.inventory = new InventoryStatic(this.width * this.height + 1) {
            public void func_70296_d() {
            }
            
            @Override
            public boolean func_94041_b(final int i, final ItemStack itemstack) {
                return false;
            }
        };
        final Set itemReg = GameData.getItemRegistry().func_148742_b();
        final List<String> itemList = new ArrayList<String>();
        itemList.addAll(itemReg);
        final String[] itemNames = itemList.toArray(new String[0]);
        for (int i = 0; i < itemList.size(); ++i) {
            final Item item = (Item)GameData.getItemRegistry().func_82594_a(itemNames[i]);
            if (item != null && item.func_77640_w() != null) {
                item.func_150895_a(item, (CreativeTabs)null, (List)this.items);
            }
        }
        int index = 0;
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                this.func_75146_a((Slot)new SlotPickBlock(this, index++, 9 + x * 18, 18 + y * 18));
            }
        }
        this.func_75146_a((Slot)(this.resultSlot = new SlotPickBlock(this, index++, 18, 153)));
        this.player = p;
        this.scrollTo(0.0f);
    }
    
    public void scrollTo(final float offset) {
        int columnsNotFitting = this.items.size() / this.width - this.height + 1;
        if (columnsNotFitting < 0) {
            columnsNotFitting = 0;
        }
        final int columnOffset = (int)(offset * columnsNotFitting + 0.5);
        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                final int index = x + (y + columnOffset) * this.width;
                if (index >= 0 && index < this.items.size()) {
                    this.inventory.func_70299_a(x + y * this.width, this.items.get(index));
                }
                else {
                    this.inventory.func_70299_a(x + y * this.width, null);
                }
            }
        }
    }
    
    public ItemStack func_82846_b(final EntityPlayer player, final int index) {
        final SlotPickBlock slot = this.field_75151_b.get(index);
        return slot.transferStackInSlot(player);
    }
    
    public boolean func_75145_c(final EntityPlayer entityplayer) {
        return true;
    }
}
