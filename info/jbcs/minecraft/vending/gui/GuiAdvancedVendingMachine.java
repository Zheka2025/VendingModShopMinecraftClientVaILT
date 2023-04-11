package info.jbcs.minecraft.vending.gui;

import info.jbcs.minecraft.vending.inventory.*;
import net.minecraft.entity.player.*;
import info.jbcs.minecraft.vending.tileentity.*;
import net.minecraft.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import info.jbcs.minecraft.vending.network.*;
import info.jbcs.minecraft.vending.*;
import cpw.mods.fml.common.network.simpleimpl.*;

public class GuiAdvancedVendingMachine extends GuiVendingMachine implements IPickBlockHandler
{
    ContainerAdvancedVendingMachine container;
    EntityPlayer player;
    
    public GuiAdvancedVendingMachine(final InventoryPlayer inventoryplayer, final TileEntityVendingMachine machine) {
        super(new ContainerAdvancedVendingMachine((IInventory)inventoryplayer, machine));
        this.container = (ContainerAdvancedVendingMachine)this.field_147002_h;
        this.player = inventoryplayer.field_70458_d;
    }
    
    @Override
    public void func_146281_b() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        Keyboard.enableRepeatEvents(true);
        this.field_146292_n.clear();
        this.field_146292_n.add(new GuiButton(100, this.field_147003_i + 30, this.field_147009_r + 67, 41, 15, StatCollector.func_74838_a("gui.vendingBlock.select")));
    }
    
    protected void func_146284_a(final GuiButton button) {
        if (button.field_146127_k == 100) {
            Minecraft.func_71410_x().func_147108_a((GuiScreen)new GuiPickBlock(this.player, ((TileEntityVendingMachine)this.container.entity).getBoughtItems()[0], (GuiScreen)this));
        }
    }
    
    @Override
    public void blockPicked(final ItemStack stack) {
        MsgAdvVenSetItem msg;
        if (stack == null) {
            msg = new MsgAdvVenSetItem(0, 0, 0);
        }
        else {
            msg = new MsgAdvVenSetItem(General.getItemId(stack.func_77973_b()), stack.field_77994_a, stack.func_77960_j());
        }
        Vending.instance.messagePipeline.sendToServer((IMessage)msg);
    }
    
    @Override
    protected void func_146979_b(final int a, final int b) {
        super.func_146979_b(a, b);
    }
}
