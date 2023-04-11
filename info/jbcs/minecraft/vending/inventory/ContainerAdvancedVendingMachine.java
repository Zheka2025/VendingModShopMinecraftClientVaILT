package info.jbcs.minecraft.vending.inventory;

import info.jbcs.minecraft.vending.tileentity.*;
import net.minecraft.inventory.*;

public class ContainerAdvancedVendingMachine extends ContainerTileEntity<TileEntityVendingMachine>
{
    SlotAdvancedVendingMachine advSlot;
    
    public ContainerAdvancedVendingMachine(final IInventory playerInv, final TileEntityVendingMachine machine) {
        super(playerInv, machine, 8, 84);
        this.func_75146_a(new Slot((IInventory)machine, 9, 120, 48));
        this.func_75146_a((Slot)(this.advSlot = new SlotAdvancedVendingMachine((IInventory)machine, 10, 42, 48)));
    }
}
