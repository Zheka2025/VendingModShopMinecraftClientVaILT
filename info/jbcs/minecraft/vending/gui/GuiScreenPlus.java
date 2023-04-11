package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import info.jbcs.minecraft.vending.inventory.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import info.jbcs.minecraft.vending.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

public class GuiScreenPlus extends GuiContainer
{
    public int screenW;
    public int screenH;
    public int screenX;
    public int screenY;
    public GuiElement root;
    String backgroundTexture;
    InputMouseEvent mouseEvent;
    int oldX;
    int oldY;
    boolean[] downButtons;
    InputKeyboardEvent keyboardEvent;
    
    public GuiScreenPlus(final Container container, final int w, final int h, final String backgroundTexture) {
        super(container);
        this.mouseEvent = new InputMouseEvent();
        this.oldX = -1;
        this.oldY = -1;
        this.downButtons = new boolean[12];
        this.keyboardEvent = new InputKeyboardEvent();
        this.root = new GuiElement(0, 0, w, h);
        this.root.gui = this;
        this.screenW = w;
        this.screenH = h;
        this.backgroundTexture = backgroundTexture;
    }
    
    public GuiScreenPlus(final int w, final int h, final String backgroundTexture) {
        this(new DummyContainer(), w, h, backgroundTexture);
    }
    
    public void func_73866_w_() {
        this.field_146999_f = this.screenW;
        this.field_147000_g = this.screenH;
        super.func_73866_w_();
        this.screenX = this.field_147003_i;
        this.screenY = this.field_147009_r;
        this.root.onAdded();
        Keyboard.enableRepeatEvents(true);
    }
    
    public void func_146269_k() {
        while (Mouse.next()) {
            this.func_146274_d();
        }
        while (Keyboard.next()) {
            this.func_146282_l();
        }
    }
    
    public void func_146274_d() {
        this.mouseEvent.handled = false;
        this.mouseEvent.x = Mouse.getEventX() * this.field_146294_l / this.field_146297_k.field_71443_c - this.screenX;
        this.mouseEvent.y = this.field_146295_m - Mouse.getEventY() * this.field_146295_m / this.field_146297_k.field_71440_d - 1 - this.screenY;
        if (this.oldX == -1) {
            this.oldX = this.mouseEvent.x;
            this.oldY = this.mouseEvent.y;
        }
        this.mouseEvent.dx = this.mouseEvent.x - this.oldX;
        this.mouseEvent.dy = this.mouseEvent.y - this.oldY;
        this.oldX = this.mouseEvent.x;
        this.oldY = this.mouseEvent.y;
        this.mouseEvent.down = Mouse.getEventButtonState();
        this.mouseEvent.button = Mouse.getEventButton();
        this.mouseEvent.wheel = Mouse.getEventDWheel();
        if (this.mouseEvent.wheel != 0) {
            if (this.mouseEvent.wheel < 0) {
                this.mouseEvent.wheel = -1;
            }
            else {
                this.mouseEvent.wheel = 1;
            }
            this.root.mouseWheel(this.mouseEvent);
        }
        else if (this.mouseEvent.button >= 0 && this.mouseEvent.button < this.downButtons.length) {
            if (this.downButtons[this.mouseEvent.button] != this.mouseEvent.down) {
                this.downButtons[this.mouseEvent.button] = this.mouseEvent.down;
                if (this.mouseEvent.down) {
                    this.root.mouseDown(this.mouseEvent);
                }
                else {
                    this.root.mouseUp(this.mouseEvent);
                }
            }
            else if (this.mouseEvent.dx != 0 || this.mouseEvent.dy != 0) {
                this.root.mouseMove(this.mouseEvent);
            }
        }
        else if (this.mouseEvent.dx != 0 || this.mouseEvent.dy != 0) {
            this.root.mouseMove(this.mouseEvent);
        }
        if (!this.mouseEvent.handled) {
            super.func_146274_d();
        }
    }
    
    public void func_146282_l() {
        this.keyboardEvent.handled = false;
        if (Keyboard.getEventKeyState()) {
            this.keyboardEvent.key = Keyboard.getEventKey();
            this.keyboardEvent.character = Keyboard.getEventCharacter();
            switch (this.keyboardEvent.key) {
                case 1: {
                    break;
                }
                default: {
                    this.root.keyPressed(this.keyboardEvent);
                    break;
                }
            }
        }
        if (!this.keyboardEvent.handled) {
            super.func_146282_l();
        }
    }
    
    public void close() {
        this.field_146297_k.func_147108_a((GuiScreen)null);
        this.field_146297_k.func_71381_h();
    }
    
    protected void addChild(final GuiElement e) {
        this.root.addChild(e);
    }
    
    protected void func_146976_a(final float f, final int bx, final int by) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GeneralClient.bind(this.backgroundTexture);
        this.func_73729_b(this.screenX, this.screenY, 0, 0, this.screenW, this.screenH);
    }
    
    protected void func_146979_b(final int fx, final int fy) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.root.render();
    }
    
    public void drawString(final String text, final int sx, final int sy, final int color) {
        final FontRenderer fontRenderer = this.field_146289_q;
        fontRenderer.func_78276_b(text, sx, sy, color);
    }
    
    public void drawCenteredString(final String text, final int sx, final int sy, final int color) {
        final FontRenderer fontRenderer = this.field_146289_q;
        fontRenderer.func_78276_b(text, sx - fontRenderer.func_78256_a(text) / 2, sy - fontRenderer.field_78288_b / 2, color);
    }
    
    public void drawStringWithShadow(final String text, final int sx, final int sy, final int color) {
        final FontRenderer fontRenderer = this.field_146289_q;
        fontRenderer.func_78261_a(text, sx, sy, color);
    }
    
    public void drawCenteredStringWithShadow(final String text, final int sx, final int sy, final int color) {
        final FontRenderer fontRenderer = this.field_146289_q;
        fontRenderer.func_78261_a(text, sx - fontRenderer.func_78256_a(text) / 2, sy - fontRenderer.field_78288_b / 2, color);
    }
    
    public FontRenderer fontRenderer() {
        return this.field_146297_k.field_71466_p;
    }
    
    protected void drawRect(final int gx, final int gy, final int gw, final int gh, final int c1, final int c2) {
        this.func_73733_a(gx, gy, gx + gw, gy + gh, c1, c2);
    }
    
    public void drawTiledRect(final int rx, final int ry, final int rw, final int rh, final int u, final int v, final int tw, final int th) {
        if (rw == 0 || rh == 0 || tw == 0 || th == 0) {
            return;
        }
        final float pixel = 0.00390625f;
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        for (int y = 0; y < rh; y += th) {
            for (int x = 0; x < rw; x += tw) {
                int qw = tw;
                if (x + qw > rw) {
                    qw = rw - x;
                }
                int qh = th;
                if (y + qh > rh) {
                    qh = rh - y;
                }
                final double x2 = rx + x;
                final double x3 = rx + x + qw;
                final double y2 = ry + y;
                final double y3 = ry + y + qh;
                final double u2 = pixel * u;
                final double u3 = pixel * (u + tw);
                final double v2 = pixel * v;
                final double v3 = pixel * (v + th);
                tessellator.func_78374_a(x2, y3, (double)this.field_73735_i, u2, v3);
                tessellator.func_78374_a(x3, y3, (double)this.field_73735_i, u3, v3);
                tessellator.func_78374_a(x3, y2, (double)this.field_73735_i, u3, v2);
                tessellator.func_78374_a(x2, y2, (double)this.field_73735_i, u2, v2);
            }
        }
        tessellator.func_78381_a();
    }
    
    public void bindTexture(final String tex) {
        GeneralClient.bind(tex);
    }
}
