package info.jbcs.minecraft.vending.gui;

import java.util.*;
import org.lwjgl.opengl.*;

public class GuiElement
{
    public int x;
    public int y;
    public int w;
    public int h;
    public GuiScreenPlus gui;
    public GuiElement parent;
    public GuiElement addedParent;
    private ArrayList<GuiElement> children;
    
    public GuiElement(final int x, final int y, final int w, final int h) {
        this.children = null;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public GuiElement addChild(final GuiElement e) {
        if (this.children == null) {
            this.children = new ArrayList<GuiElement>();
        }
        if (e.parent != null) {
            e.parent.removeChild(e);
        }
        this.children.add(e);
        e.x += this.x;
        e.y += this.y;
        e.parent = this;
        e.gui = this.gui;
        return e;
    }
    
    public GuiElement removeChild(final GuiElement e) {
        if (this.children == null) {
            return e;
        }
        this.children.remove(e);
        e.x -= this.x;
        e.y -= this.y;
        e.parent = null;
        e.gui = null;
        return e;
    }
    
    public void onAdded() {
        if (this.children == null) {
            return;
        }
        for (final GuiElement e : this.children) {
            if (e.parent != e.addedParent) {
                e.onAdded();
            }
            e.addedParent = e.parent;
        }
    }
    
    boolean isMouseOver(final InputMouseEvent ev) {
        return ev.x >= this.x && ev.x < this.x + this.w && ev.y >= this.y && ev.y < this.y + this.h;
    }
    
    public void mouseDown(final InputMouseEvent ev) {
        if (this.children == null) {
            return;
        }
        for (final GuiElement e : this.children) {
            e.mouseDown(ev);
            if (ev.handled) {
                return;
            }
        }
    }
    
    public void mouseUp(final InputMouseEvent ev) {
        if (this.children == null) {
            return;
        }
        for (final GuiElement e : this.children) {
            e.mouseUp(ev);
            if (ev.handled) {
                return;
            }
        }
    }
    
    public void mouseMove(final InputMouseEvent ev) {
        if (this.children == null) {
            return;
        }
        for (final GuiElement e : this.children) {
            e.mouseMove(ev);
            if (ev.handled) {
                return;
            }
        }
    }
    
    public void mouseWheel(final InputMouseEvent ev) {
        if (this.children == null) {
            return;
        }
        for (final GuiElement e : this.children) {
            e.mouseWheel(ev);
            if (ev.handled) {
                return;
            }
        }
    }
    
    public void render() {
        if (this.children == null) {
            return;
        }
        for (final GuiElement e : this.children) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            e.render();
        }
    }
    
    public void keyPressed(final InputKeyboardEvent ev) {
        if (this.children == null) {
            return;
        }
        for (final GuiElement e : this.children) {
            e.keyPressed(ev);
            if (ev.handled) {
                return;
            }
        }
    }
}
