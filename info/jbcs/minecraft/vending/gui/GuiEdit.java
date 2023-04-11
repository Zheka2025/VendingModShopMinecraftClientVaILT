package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.gui.*;

public class GuiEdit extends GuiElement
{
    GuiTextField field;
    String tempString;
    
    public GuiEdit(final int x, final int y, final int w, final int h) {
        super(x, y, w, h);
        this.tempString = "";
    }
    
    @Override
    public void onAdded() {
        this.field = new GuiTextField(this.gui.fontRenderer(), this.x, this.y, this.w, this.h);
        this.setText(this.tempString);
    }
    
    public String getText() {
        if (this.field == null) {
            return this.tempString;
        }
        return this.field.func_146179_b();
    }
    
    public void setText(final String text) {
        if (this.field == null) {
            this.tempString = text;
        }
        else {
            this.field.func_146180_a(text);
        }
    }
    
    @Override
    public void render() {
        this.field.func_146194_f();
    }
    
    @Override
    public void mouseDown(final InputMouseEvent ev) {
        this.field.func_146192_a(ev.x, ev.y, ev.button);
        if (this.isMouseOver(ev)) {
            ev.handled = true;
        }
    }
    
    @Override
    public void keyPressed(final InputKeyboardEvent ev) {
        this.field.func_146201_a(ev.character, ev.key);
        if (this.field.func_146206_l()) {
            ev.handled = true;
        }
    }
}
