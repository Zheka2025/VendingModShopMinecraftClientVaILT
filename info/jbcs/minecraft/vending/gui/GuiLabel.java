package info.jbcs.minecraft.vending.gui;

public class GuiLabel extends GuiElement
{
    String caption;
    
    public GuiLabel(final int x, final int y, final int w, final int h, final String caption) {
        super(x, y, w, h);
        this.caption = caption;
    }
    
    public GuiLabel(final int x, final int y, final String string) {
        this(x, y, 0, 0, string);
    }
    
    @Override
    public void render() {
        this.gui.drawString(this.caption, this.x, this.y, 4210752);
    }
}
