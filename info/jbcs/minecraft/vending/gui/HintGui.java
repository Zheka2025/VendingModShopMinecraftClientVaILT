package info.jbcs.minecraft.vending.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraftforge.client.event.*;
import info.jbcs.minecraft.vending.tileentity.*;
import info.jbcs.minecraft.vending.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import cpw.mods.fml.common.eventhandler.*;
import net.minecraft.item.*;
import org.lwjgl.opengl.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;

public class HintGui extends Gui
{
    int xx;
    int yy;
    private Minecraft mc;
    RenderItem render;
    
    public HintGui(final Minecraft mc) {
        this.render = new RenderItem();
        this.mc = mc;
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onRenderInfo(final RenderGameOverlayEvent.Post event) {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if (this.mc == null || this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)this.mc.field_71439_g;
        final World world = (World)this.mc.field_71441_e;
        final MovingObjectPosition mop = General.getMovingObjectPositionFromPlayer(world, player, false);
        if (mop == null) {
            return;
        }
        final TileEntity te = world.func_147438_o(mop.field_72311_b, mop.field_72312_c, mop.field_72309_d);
        if (te == null) {
            return;
        }
        if (!(te instanceof TileEntityVendingMachine)) {
            return;
        }
        final TileEntityVendingMachine tileEntity = (TileEntityVendingMachine)te;
        this.draw(tileEntity.ownerName, tileEntity.getSoldItems(), tileEntity.getBoughtItems(), tileEntity);
        GeneralClient.bind("textures/gui/icons.png");
    }
    
    void drawNumberForItem(final FontRenderer fontRenderer, final ItemStack stack, final int ux, final int uy) {
        if (stack == null || stack.field_77994_a < 2) {
            return;
        }
        final String line = "" + stack.field_77994_a;
        final int x = ux + 19 - 2 - fontRenderer.func_78256_a(line);
        final int y = uy + 6 + 3;
        GL11.glTranslatef(0.0f, 0.0f, 50.0f);
        this.func_73731_b(fontRenderer, line, x + 1, y + 1, 8947848);
        this.func_73731_b(fontRenderer, line, x, y, 16777215);
        GL11.glTranslatef(0.0f, 0.0f, -50.0f);
    }
    
    void drawLabel(final FontRenderer fontRenderer, final String label, int x, final int y, final int colour) {
        final int witdth;
        final int w = witdth = fontRenderer.func_78256_a(StatCollector.func_74838_a(label)) + 2;
        x -= witdth / 2 - 20;
        this.func_73731_b(fontRenderer, label, x, y, colour);
    }
    
    void drawItems(final FontRenderer fontRenderer, final int x, int y, final int colour, final ItemStack[] itemStacks, final boolean drawDescription, final int descWidth) {
        final ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (final ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                list.add(itemStack);
            }
        }
        final int numOfItems = list.size();
        int w = 0;
        for (final ItemStack itemStack : list) {
            this.drawNumberForItem(fontRenderer, itemStack, x + w, y - 4);
            this.render.func_77015_a(fontRenderer, this.mc.field_71446_o, itemStack, x + w, y - 4);
            GL11.glDisable(2896);
            w += 18;
        }
        y += 20;
        if (drawDescription) {
            final ItemStack itemStack2 = this.getFromList(list, (int)this.mc.field_71439_g.field_70170_p.func_72820_D() / 50 % numOfItems);
            if (itemStack2 != null) {
                for (final Object object : itemStack2.func_82840_a((EntityPlayer)this.mc.field_71439_g, false).toArray()) {
                    final String line = object.toString();
                    if (!line.isEmpty()) {
                        this.func_73731_b(fontRenderer, line, x, y, 10526880);
                        y += 16;
                    }
                }
            }
        }
    }
    
    private ItemStack getFromList(final ArrayList<ItemStack> list, final int i) {
        return (i < list.size()) ? list.get(i) : null;
    }
    
    void drawItemsWithLabel(final FontRenderer fontRenderer, final String label, int x, int y, final int colour, final ItemStack[] itemStacks, final boolean drawDescription, final int descWidth) {
        int w = fontRenderer.func_78256_a(StatCollector.func_74838_a(label)) + 2;
        final int numOfItems = General.countNotNull(itemStacks);
        final int witdth = drawDescription ? Math.max(w + 18 * numOfItems, descWidth) : (w + 18 * numOfItems);
        x -= witdth / 2;
        this.func_73731_b(fontRenderer, StatCollector.func_74838_a(label), x, y, colour);
        for (final ItemStack itemStack : itemStacks) {
            if (itemStack != null) {
                this.drawNumberForItem(fontRenderer, itemStack, x + w, y - 4);
                this.render.func_77015_a(fontRenderer, this.mc.field_71446_o, itemStack, x + w, y - 4);
                GL11.glDisable(2896);
                w += 18;
            }
        }
        y += 20;
        if (drawDescription) {
            final ItemStack itemStack2 = General.getNotNull(itemStacks, (int)this.mc.field_71439_g.field_70170_p.func_72820_D() / 50 % numOfItems);
            if (itemStack2 != null) {
                for (final Object object : itemStack2.func_82840_a((EntityPlayer)this.mc.field_71439_g, false).toArray()) {
                    final String line = object.toString();
                    if (!line.isEmpty()) {
                        this.func_73731_b(fontRenderer, line, x, y, 10526880);
                        y += 16;
                    }
                }
            }
        }
    }
    
    public static void drawTextureCustomSize(final double posX, final double posY, final double startPixX, final double startPixY, final double pieceSizeX, final double pieceSizeY, final float sizeTextureX, final float sizeTextureY) {
        final float f4 = 1.0f / sizeTextureX;
        final float f5 = 1.0f / sizeTextureY;
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a(posX, posY + pieceSizeY, 0.0, startPixX * f4, (startPixY + (float)pieceSizeY) * f5);
        tessellator.func_78374_a(posX + pieceSizeX, posY + pieceSizeY, 0.0, (startPixX + (float)pieceSizeX) * f4, (startPixY + (float)pieceSizeY) * f5);
        tessellator.func_78374_a(posX + pieceSizeX, posY, 0.0, (startPixX + (float)pieceSizeX) * f4, startPixY * f5);
        tessellator.func_78374_a(posX, posY, 0.0, startPixX * f4, startPixY * f5);
        tessellator.func_78381_a();
    }
    
    void draw(final String seller, final ItemStack[] soldItems, final ItemStack[] boughtItems, final TileEntityVendingMachine tile) {
        final boolean isSoldEmpty = General.countNotNull(soldItems) == 0;
        final boolean isBoughtEmpty = General.countNotNull(boughtItems) == 0;
        final boolean isMoney = tile.eco;
        if (isMoney && isSoldEmpty) {
            return;
        }
        if ((isBoughtEmpty || isSoldEmpty) && !isMoney) {
            return;
        }
        final ScaledResolution resolution = new ScaledResolution(this.mc, this.mc.field_71443_c, this.mc.field_71440_d);
        final int screenwidth = resolution.func_78326_a();
        final FontRenderer fontRenderer = this.mc.field_71466_p;
        final int width = resolution.func_78326_a();
        final int height = resolution.func_78328_b();
        int linesBought = 0;
        int lengthBought = 0;
        int linesSold = 0;
        int lengthSold = 0;
        for (final ItemStack bought : boughtItems) {
            if (bought != null) {
                int c = 0;
                for (int i = 0; i < bought.func_82840_a((EntityPlayer)this.mc.field_71439_g, false).size(); ++i) {
                    final String tooltip = bought.func_82840_a((EntityPlayer)this.mc.field_71439_g, false).get(i).toString();
                    if (!tooltip.isEmpty()) {
                        ++c;
                    }
                    if (tooltip.length() > lengthBought) {
                        lengthBought = fontRenderer.func_78256_a(tooltip);
                    }
                }
                linesBought = Math.max(linesBought, c);
            }
        }
        for (final ItemStack sold : soldItems) {
            if (sold != null) {
                int c = 0;
                for (int i = 0; i < sold.func_82840_a((EntityPlayer)this.mc.field_71439_g, false).size(); ++i) {
                    final String tooltip = sold.func_82840_a((EntityPlayer)this.mc.field_71439_g, false).get(i).toString();
                    if (!tooltip.isEmpty()) {
                        ++c;
                    }
                    if (tooltip.length() > lengthSold) {
                        lengthSold = fontRenderer.func_78256_a(tooltip);
                    }
                }
                linesSold = Math.max(linesBought, c);
                linesBought = Math.max(linesBought, c);
            }
        }
        final boolean drawDesc = this.mc.field_71439_g.func_70093_af();
        final int descHeight = Math.max(linesBought, linesSold) * 16;
        final int w = 120 + fontRenderer.func_78256_a(seller);
        int h = 44 + (drawDesc ? descHeight : 0) + 8;
        int centerYOff = -80 + (drawDesc ? (descHeight / 2) : 0) + 8;
        if (drawDesc && !isBoughtEmpty && !isSoldEmpty) {
            h -= 16;
            centerYOff -= 8;
        }
        int cx = width / 2;
        final int x = cx - w / 2;
        final int y = height / 2 - h / 2 + centerYOff;
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, 0.0f, -100.0f);
        GL11.glDisable(2896);
        this.func_73733_a(cx - w / 2 - (drawDesc ? (lengthSold / 2) : 0), y - 10, cx + w / 2 + (drawDesc ? (lengthSold / 2) : 0), y + h + 10, -1072689136, -804253680);
        if (!isMoney || tile.mode == 0) {
            this.func_73732_a(fontRenderer, "\u0412 \u043d\u0430\u043b\u0438\u0447\u0438\u0438: " + tile.count, cx + 7, y + 8, 11206570);
        }
        this.func_73732_a(fontRenderer, StatCollector.func_74838_a("gui.vendingBlock.owner") + ": " + seller, cx, y - 2, 16777215);
        cx += 20;
        this.mc.func_110434_K().func_110577_a(new ResourceLocation("vending:textures/arrow.png"));
        drawTextureCustomSize(-30 + cx, y + 22, 0.0, 0.0, 25.0, 17.0, 25.0f, 17.0f);
        if (!isBoughtEmpty && !isSoldEmpty) {
            this.drawItems(fontRenderer, cx + (drawDesc ? 0 : 0), y + 26, 10526880, soldItems, drawDesc, lengthSold);
            this.drawItems(fontRenderer, -53 + cx - (drawDesc ? (lengthSold / 2) : 0), y + 26, 10526880, boughtItems, drawDesc, lengthBought);
        }
        else if (!isSoldEmpty && isMoney) {
            final int moneyWidth = ("" + tile.getMoney() + "$").length() * 10;
            this.drawItems(fontRenderer, (tile.mode == 0) ? (cx + (drawDesc ? 0 : 0)) : (cx - (drawDesc ? (lengthSold / 2) : 0) - 50), y + 26, 10526880, soldItems, drawDesc, lengthSold);
            this.drawLabel(fontRenderer, EnumChatFormatting.GREEN + "" + tile.getMoney() + "$", (tile.mode == 0) ? (cx - 74) : (cx + (drawDesc ? 10 : 5)), y + 26, 10526880);
        }
        GL11.glDisable(2896);
        GL11.glPopMatrix();
    }
}
