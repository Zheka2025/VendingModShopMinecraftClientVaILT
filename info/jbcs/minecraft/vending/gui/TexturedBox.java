package info.jbcs.minecraft.vending.gui;

public class TexturedBox
{
    String texture;
    int u;
    int v;
    int texw;
    int texh;
    int borderTop;
    int borderRight;
    int borderBottom;
    int borderLeft;
    
    public TexturedBox(final String texture, final int u, final int v, final int texw, final int texh, final int borderTop, final int borderRight, final int borderBottom, final int borderLeft) {
        this.texture = texture;
        this.u = u;
        this.v = v;
        this.texw = texw;
        this.texh = texh;
        this.borderTop = borderTop;
        this.borderRight = borderRight;
        this.borderBottom = borderBottom;
        this.borderLeft = borderLeft;
    }
    
    public void render(final GuiScreenPlus gui, final int x, final int y, final int w, final int h) {
        final int x2 = x;
        final int x3 = x + this.borderLeft;
        final int x4 = x + w - this.borderRight;
        final int y2 = y;
        final int y3 = y + this.borderTop;
        final int y4 = y + h - this.borderBottom;
        final int w2 = this.borderLeft;
        final int w3 = w - this.borderLeft - this.borderRight;
        final int w4 = this.borderRight;
        final int h2 = this.borderTop;
        final int h3 = h - this.borderTop - this.borderBottom;
        final int h4 = this.borderBottom;
        final int u1 = this.u;
        final int v1 = this.v;
        int tw = this.texw;
        if (tw > w) {
            tw = w;
        }
        int th = this.texh;
        if (th > h) {
            th = h;
        }
        final int u2 = u1 + this.borderLeft;
        final int u3 = u1 + this.texw - this.borderRight;
        final int v2 = v1 + this.borderTop;
        final int v3 = v1 + this.texh - this.borderBottom;
        final int texw1 = this.borderLeft;
        final int texw2 = tw - this.borderLeft - this.borderRight;
        final int texw3 = this.borderRight;
        final int texh1 = this.borderTop;
        final int texh2 = th - this.borderTop - this.borderBottom;
        final int texh3 = this.borderBottom;
        gui.bindTexture(this.texture);
        gui.func_73729_b(x2, y2, u1, v1, w2, h2);
        gui.drawTiledRect(x3, y2, w3, h2, u2, v1, texw2, texh1);
        gui.func_73729_b(x4, y2, u3, v1, w4, h2);
        gui.drawTiledRect(x2, y3, w2, h3, u1, v2, w2, texh2);
        gui.drawTiledRect(x3, y3, w3, h3, u2, v2, texw2, texh2);
        gui.drawTiledRect(x4, y3, w4, h3, u3, v2, w4, texh2);
        gui.func_73729_b(x2, y4, u1, v3, w2, h4);
        gui.drawTiledRect(x3, y4, w3, h4, u2, v3, texw2, texh3);
        gui.func_73729_b(x4, y4, u3, v3, w4, h4);
    }
}
