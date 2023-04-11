package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class CustomButton extends GuiButton
{
    private int additionHeight;
    
    public void func_146112_a(final Minecraft p_146112_1_, final int p_146112_2_, final int p_146112_3_) {
        if (this.field_146125_m) {
            final FontRenderer fontrenderer = p_146112_1_.field_71466_p;
            p_146112_1_.func_110434_K().func_110577_a(CustomButton.field_146122_a);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = (p_146112_2_ >= this.field_146128_h && p_146112_3_ >= this.field_146129_i && p_146112_2_ < this.field_146128_h + this.field_146120_f && p_146112_3_ < this.field_146129_i + this.field_146121_g);
            final int k = this.func_146114_a(this.field_146123_n);
            GL11.glEnable(3042);
            OpenGlHelper.func_148821_a(770, 771, 1, 0);
            GL11.glBlendFunc(770, 771);
            this.func_73729_b(this.field_146128_h, this.field_146129_i, 0, 46 + k * 20, this.field_146120_f / 2, this.field_146121_g);
            this.func_73729_b(this.field_146128_h + this.field_146120_f / 2, this.field_146129_i, 200 - this.field_146120_f / 2, 46 + k * 20, this.field_146120_f / 2, this.field_146121_g);
            this.func_146119_b(p_146112_1_, p_146112_2_, p_146112_3_);
            int l = 14737632;
            if (this.packedFGColour != 0) {
                l = this.packedFGColour;
            }
            else if (!this.field_146124_l) {
                l = 10526880;
            }
            else if (this.field_146123_n) {
                l = 16777120;
            }
            this.func_73732_a(fontrenderer, this.field_146126_j, this.field_146128_h + this.field_146120_f / 2, this.field_146129_i + (this.field_146121_g - 7) / 2 + this.additionHeight, l);
        }
    }
    
    public CustomButton(final int p_i1021_1_, final int p_i1021_2_, final int p_i1021_3_, final int p_i1021_4_, final int p_i1021_5_, final String p_i1021_6_, final int additionHeight) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        this.additionHeight = 0;
        this.additionHeight = additionHeight;
    }
    
    public CustomButton(final int p_i1021_1_, final int p_i1021_2_, final int p_i1021_3_, final int p_i1021_4_, final int p_i1021_5_, final String p_i1021_6_) {
        super(p_i1021_1_, p_i1021_2_, p_i1021_3_, p_i1021_4_, p_i1021_5_, p_i1021_6_);
        this.additionHeight = 0;
    }
}
