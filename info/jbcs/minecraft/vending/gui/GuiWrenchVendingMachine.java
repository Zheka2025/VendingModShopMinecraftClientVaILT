package info.jbcs.minecraft.vending.gui;

import info.jbcs.minecraft.vending.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import info.jbcs.minecraft.vending.network.*;
import net.minecraft.tileentity.*;
import info.jbcs.minecraft.vending.*;
import cpw.mods.fml.common.network.simpleimpl.*;

public class GuiWrenchVendingMachine extends GuiScreenPlus
{
    GuiEdit ownerNameEdit;
    GuiExButton infiniteButton;
    TileEntityVendingMachine entity;
    boolean infinite;
    
    public GuiWrenchVendingMachine(final World world, final int x, final int y, final int z, final EntityPlayer entityplayer) {
        super(166, 120, "vending:textures/wrench-gui.png");
        this.addChild(new GuiLabel(9, 9, StatCollector.func_74838_a("gui.vendingBlock.settings")));
        this.addChild(new GuiLabel(9, 29, StatCollector.func_74838_a("gui.vendingBlock.owner")));
        this.addChild(this.ownerNameEdit = new GuiEdit(16, 43, 138, 13));
        this.addChild(this.infiniteButton = new GuiExButton(9, 64, 148, 20, "") {
            @Override
            public void onClick() {
                GuiWrenchVendingMachine.this.infinite = !GuiWrenchVendingMachine.this.infinite;
                this.caption = StatCollector.func_74838_a("gui.vendingBlock.infinite") + ": " + (GuiWrenchVendingMachine.this.infinite ? StatCollector.func_74838_a("gui.vendingBlock.yes") : StatCollector.func_74838_a("gui.vendingBlock.no"));
            }
        });
        this.addChild(new GuiExButton(9, 91, 148, 20, StatCollector.func_74838_a("gui.vendingBlock.apply")) {
            @Override
            public void onClick() {
                final MsgWrench msg = new MsgWrench(GuiWrenchVendingMachine.this.entity, GuiWrenchVendingMachine.this.infinite, GuiWrenchVendingMachine.this.ownerNameEdit.getText());
                Vending.instance.messagePipeline.sendToServer((IMessage)msg);
                GuiWrenchVendingMachine.this.field_146297_k.field_71439_g.func_71053_j();
            }
        });
        final TileEntity tileEntity = world.func_147438_o(x, y, z);
        if (!(tileEntity instanceof TileEntityVendingMachine)) {
            return;
        }
        this.entity = (TileEntityVendingMachine)tileEntity;
        this.ownerNameEdit.setText(this.entity.ownerName);
        this.infinite = !this.entity.infinite;
        this.infiniteButton.onClick();
    }
}
