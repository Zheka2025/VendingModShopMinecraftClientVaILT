package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;

public class GuiExButton extends GuiElement
{
    protected String caption;
    int u;
    int v;
    int texw;
    int texh;
    int borderTop;
    int borderRight;
    int borderBottom;
    int borderLeft;
    boolean over;
    public boolean disabled;
    public TexturedBox boxDisabled;
    public TexturedBox boxNormal;
    public TexturedBox boxOver;
    
    public GuiExButton(final int x, final int y, final int w, final int h, final String caption) {
        this(x, y, w, h, caption, "textures/gui/widgets.png");
    }
    
    public GuiExButton(final int x, final int y, final int w, final int h, final String caption, final String texture) {
        super(x, y, w, h);
        this.caption = caption;
        this.disabled = false;
        this.u = 0;
        this.v = 46;
        this.texw = 200;
        this.texh = 20;
        this.borderTop = 2;
        this.borderRight = 2;
        this.borderBottom = 3;
        this.borderLeft = 2;
        this.boxDisabled = new TexturedBox(texture, 0, 46, 200, 20, 2, 2, 3, 2);
        this.boxNormal = new TexturedBox(texture, 0, 66, 200, 20, 2, 2, 3, 2);
        this.boxOver = new TexturedBox(texture, 0, 86, 200, 20, 2, 2, 3, 2);
    }
    
    public void onClick() {
    }
    
    @Override
    public void render() {
        int color = -2039584;
        TexturedBox box = this.boxDisabled;
        if (!this.disabled && !this.over) {
            box = this.boxNormal;
            color = -1;
        }
        else if (!this.disabled && this.over) {
            box = this.boxOver;
            color = -144;
        }
        final FontRenderer fontRenderer = this.gui.fontRenderer();
        box.render(this.gui, this.x, this.y, this.w, this.h);
        this.gui.func_73732_a(fontRenderer, this.caption, this.x + this.w / 2, this.y + this.h / 4 + 1, color);
    }
    
    @Override
    public void mouseMove(final InputMouseEvent ev) {
        this.over = this.isMouseOver(ev);
    }
    
    @Override
    public void mouseDown(final InputMouseEvent ev) {
        if (!this.isMouseOver(ev)) {
            return;
        }
        final Minecraft mc = Minecraft.func_71410_x();
        final SoundHandler soundHandler = new SoundHandler(mc.func_110442_L(), mc.field_71474_y);
        soundHandler.func_147682_a((ISound)PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
        this.onClick();
    }
}
