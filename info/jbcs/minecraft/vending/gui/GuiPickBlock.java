package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.gui.inventory.*;
import info.jbcs.minecraft.vending.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import info.jbcs.minecraft.vending.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.*;

public class GuiPickBlock extends GuiContainer
{
    Scrollbar scrollbar;
    ContainerPickBlock container;
    GuiScreen parent;
    
    public GuiPickBlock(final EntityPlayer player, final ItemStack stack, final GuiScreen screen) {
        super((Container)new ContainerPickBlock(player));
        this.field_147000_g = 185;
        this.field_146999_f = 195;
        this.container = (ContainerPickBlock)this.field_147002_h;
        this.container.gui = this;
        this.parent = screen;
        this.container.resultSlot.func_75215_d(stack);
    }
    
    protected void func_146976_a(final float f, final int x, final int y) {
        GeneralClient.bind("vending:textures/list_items.png");
        this.func_73729_b(this.field_147003_i, this.field_147009_r, 0, 0, this.field_146999_f, this.field_147000_g);
        this.scrollbar.func_146112_a(this.field_146297_k, x, y);
    }
    
    public void func_146274_d() {
        super.func_146274_d();
        this.scrollbar.handleMouseInput();
    }
    
    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_146292_n.add(this.scrollbar = new Scrollbar(200, this.field_147003_i + 175, this.field_147009_r + 18, 12, 124, "") {
            @Override
            public void onScrolled(final float off) {
                int columnsNotFitting = GuiPickBlock.this.container.items.size() / GuiPickBlock.this.container.width - GuiPickBlock.this.container.height + 1;
                if (columnsNotFitting < 0) {
                    columnsNotFitting = 0;
                }
                if (columnsNotFitting == 0) {
                    GuiPickBlock.this.scrollbar.active = false;
                    GuiPickBlock.this.scrollbar.offset = 0.0f;
                }
                else {
                    GuiPickBlock.this.scrollbar.active = true;
                    GuiPickBlock.this.scrollbar.step = 1.0f / columnsNotFitting;
                }
                GuiPickBlock.this.container.scrollTo(off);
            }
        });
        this.field_146292_n.add(new GuiButton(100, this.field_147003_i + 44, this.field_147009_r + 151, 70, 20, "\u0412\u044b\u0431\u0440\u0430\u0442\u044c"));
    }
    
    public void picked(final ItemStack stack) {
        if (this.parent == null) {
            return;
        }
        if (this.parent instanceof IPickBlockHandler) {
            ((IPickBlockHandler)this.parent).blockPicked(stack);
        }
    }
    
    protected void func_146284_a(final GuiButton button) {
        switch (button.field_146127_k) {
            case 100: {
                final ItemStack stack = this.container.resultSlot.func_75211_c();
                if (this.parent instanceof IPickBlockHandler) {
                    ((IPickBlockHandler)this.parent).blockPicked(stack);
                }
                Minecraft.func_71410_x().func_147108_a(this.parent);
                break;
            }
        }
    }
}
