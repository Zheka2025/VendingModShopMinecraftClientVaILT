package info.jbcs.minecraft.vending.inventory;

import info.jbcs.minecraft.vending.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ContainerEcoVendingMachine extends ContainerTileEntity<TileEntityVendingMachine>
{
    public TileEntityVendingMachine tile;
    
    public ContainerEcoVendingMachine(final InventoryPlayer inventoryplayer, final TileEntityVendingMachine machine) {
        super((IInventory)inventoryplayer, machine, 8, 84);
        this.tile = machine;
        this.func_75146_a(new Slot((IInventory)machine, 9, 120, 48));
    }
}
