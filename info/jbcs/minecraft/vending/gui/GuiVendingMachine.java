package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.gui.inventory.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import info.jbcs.minecraft.vending.tileentity.*;
import info.jbcs.minecraft.vending.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import info.jbcs.minecraft.vending.*;

public class GuiVendingMachine extends GuiContainer
{
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.clear();
    }
    
    public void func_73863_a(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        super.func_73863_a(p_73863_1_, p_73863_2_, p_73863_3_);
        for (int i1 = 0; i1 < this.field_147002_h.field_75151_b.size(); ++i1) {
            final Slot slot = this.field_147002_h.field_75151_b.get(i1);
            if (this.isMouseOverSlot1(slot, p_73863_1_, p_73863_2_) && slot.func_111238_b()) {
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                final int j1 = slot.field_75223_e;
                final int k1 = slot.field_75221_f;
                GL11.glColorMask(true, true, true, false);
                this.func_73733_a(j1, k1, j1 + 16, k1 + 16, 16711935, 16711680);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
            }
        }
    }
    
    private boolean isMouseOverSlot1(final Slot p_146981_1_, final int p_146981_2_, final int p_146981_3_) {
        return this.func_146978_c(p_146981_1_.field_75223_e, p_146981_1_.field_75221_f, 16, 16, p_146981_2_, p_146981_3_);
    }
    
    public GuiVendingMachine(final InventoryPlayer inventoryplayer, final TileEntityVendingMachine machine) {
        super((Container)new ContainerVendingMachine((IInventory)inventoryplayer, machine));
    }
    
    public GuiVendingMachine(final Container c) {
        super(c);
    }
    
    protected void func_146979_b(final int a, final int b) {
        final FontRenderer fontRenderer = this.field_146289_q;
        final String title = StatCollector.func_74838_a("gui.vendingBlock.storage");
        fontRenderer.func_78276_b(title, 87 - fontRenderer.func_78256_a(title) / 2, 6, 4210752);
        fontRenderer.func_78276_b(StatCollector.func_74838_a("gui.vendingBlock.selling"), 107, 19, 4210752);
        fontRenderer.func_78276_b(StatCollector.func_74838_a("gui.vendingBlock.buying"), 29, 19, 4210752);
    }
    
    protected void func_146976_a(final float f, final int a, final int b) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GeneralClient.bind("vending:textures/vending-gui.png");
        this.func_73729_b((this.field_146294_l - this.field_146999_f) / 2, (this.field_146295_m - this.field_147000_g) / 2, 0, 0, this.field_146999_f, this.field_147000_g);
    }
    
    public void func_146281_b() {
    }
}
