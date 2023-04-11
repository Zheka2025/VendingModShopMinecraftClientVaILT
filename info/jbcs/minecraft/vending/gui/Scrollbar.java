package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;

public abstract class Scrollbar extends GuiButton
{
    public boolean active;
    public float offset;
    public float step;
    boolean dragged;
    int elementHeight;
    
    public Scrollbar(final int id, final int x, final int y, final int w, final int h, final String string) {
        super(id, x, y, w, h, string);
        this.elementHeight = 15;
        this.offset = 0.0f;
        this.step = 0.025f;
        this.field_146121_g = h;
        this.active = true;
        this.dragged = false;
    }
    
    public boolean func_146116_c(final Minecraft mc, final int x, final int y) {
        if (x < this.field_146128_h || x >= this.field_146128_h + this.field_146120_f) {
            return false;
        }
        if (y < this.field_146129_i || y >= this.field_146129_i + this.field_146121_g) {
            return false;
        }
        if (this.active) {
            this.dragged = true;
        }
        return true;
    }
    
    public void func_146112_a(final Minecraft mc, final int x, final int y) {
        if (this.dragged) {
            final float initialOffset = this.offset;
            final int off = y - this.field_146129_i - this.elementHeight / 2;
            this.offset = 1.0f * off / (this.field_146121_g - this.elementHeight);
            if (this.offset < 0.0f) {
                this.offset = 0.0f;
            }
            if (this.offset > 1.0f) {
                this.offset = 1.0f;
            }
            if (initialOffset != this.offset) {
                this.onScrolled(this.offset);
            }
        }
        final int bottom = this.field_146129_i + this.field_146121_g;
        this.func_73729_b(this.field_146128_h, this.field_146129_i + (int)((this.field_146121_g - this.elementHeight) * this.offset), this.active ? 232 : 244, 0, 12, this.elementHeight);
    }
    
    public void handleMouseInput() {
        if (Mouse.getEventButton() == 0 && !Mouse.getEventButtonState()) {
            this.dragged = false;
        }
        if (!this.active) {
            return;
        }
        final float initialOffset = this.offset;
        final int direction = Mouse.getEventDWheel();
        if (direction != 0) {
            this.offset += ((direction > 0) ? (-this.step) : this.step);
        }
        if (this.offset < 0.0f) {
            this.offset = 0.0f;
        }
        if (this.offset > 1.0f) {
            this.offset = 1.0f;
        }
        if (initialOffset != this.offset) {
            this.onScrolled(this.offset);
        }
    }
    
    public abstract void onScrolled(final float p0);
}
