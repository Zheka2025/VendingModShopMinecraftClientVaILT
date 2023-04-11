package info.jbcs.minecraft.vending.renderer;

import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.tileentity.*;
import info.jbcs.minecraft.vending.tileentity.*;
import info.jbcs.minecraft.vending.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.item.*;

public class TileEntityVendingMachineRenderer extends TileEntitySpecialRenderer
{
    RenderItem renderer;
    
    public TileEntityVendingMachineRenderer() {
        (this.renderer = new RenderItem()).func_76976_a(RenderManager.field_78727_a);
    }
    
    public void func_147500_a(final TileEntity tileentity, final double x, final double y, final double z, final float f) {
        final TileEntityVendingMachine machine = (TileEntityVendingMachine)tileentity;
        if (machine == null || machine.func_145838_q() == null) {
            return;
        }
        final int minPriceSold = EconomyControl.getMinPrice(machine.getSoldItems());
        final int minPriceBought = (tileentity.func_145831_w().func_72805_g(tileentity.field_145851_c, tileentity.field_145848_d, tileentity.field_145849_e) == 8) ? machine.getMoney() : EconomyControl.getMinPrice(machine.getBoughtItems()[0]);
        if (minPriceSold > minPriceBought * Vending.priceMultiplier) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5f, (float)y + 0.35f, (float)z + 0.5f);
        GL11.glEnable(32826);
        int A = 0;
        for (final ItemStack itemStack : machine.getSoldItems()) {
            if (itemStack != null) {
                final EntityItem entity = new EntityItem((World)null, x, y, z, itemStack);
                entity.field_70290_d = 0.0f;
                if (Minecraft.func_71410_x() != null && Minecraft.func_71410_x().field_71439_g != null) {
                    entity.field_70292_b = Minecraft.func_71410_x().field_71439_g.field_70173_aa;
                }
                final int meta = tileentity.func_145832_p();
                try {
                    this.renderer.func_76986_a(entity, -0.1 + A % 2 * 0.2, 0.0, -0.1 + ((A >= 2) ? 1 : 0) * 0.2, f, f);
                }
                catch (Throwable t) {}
                ++A;
            }
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
