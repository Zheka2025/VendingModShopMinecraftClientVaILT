package info.jbcs.minecraft.vending.renderer;

import cpw.mods.fml.client.registry.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import info.jbcs.minecraft.vending.*;
import net.minecraft.world.*;

public class BlockVendingMachineRenderer implements ISimpleBlockRenderingHandler
{
    public static int id;
    
    void drawBlock(final Block block, final int meta, final RenderBlocks renderer) {
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78375_b(0.0f, -1.0f, 0.0f);
        renderer.func_147768_a(block, 0.0, 0.0, 0.0, block.func_149691_a(0, meta));
        tessellator.func_78375_b(0.0f, 1.0f, 0.0f);
        renderer.func_147806_b(block, 0.0, 0.0, 0.0, block.func_149691_a(1, meta));
        tessellator.func_78375_b(0.0f, 0.0f, -1.0f);
        renderer.func_147761_c(block, 0.0, 0.0, 0.0, block.func_149691_a(2, meta));
        tessellator.func_78375_b(0.0f, 0.0f, 1.0f);
        renderer.func_147734_d(block, 0.0, 0.0, 0.0, block.func_149691_a(3, meta));
        tessellator.func_78375_b(-1.0f, 0.0f, 0.0f);
        renderer.func_147798_e(block, 0.0, 0.0, 0.0, block.func_149691_a(4, meta));
        tessellator.func_78375_b(1.0f, 0.0f, 0.0f);
        renderer.func_147764_f(block, 0.0, 0.0, 0.0, block.func_149691_a(5, meta));
        tessellator.func_78381_a();
    }
    
    public void renderInventoryBlock(final Block block, final int meta, final int modelID, final RenderBlocks renderer) {
        this.drawBlock(block, meta, renderer);
        renderer.func_147782_a(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
        this.drawBlock(Vending.supports[meta], 0, renderer);
    }
    
    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        final int meta = world.func_72805_g(x, y, z);
        renderer.func_147784_q(block, x, y, z);
        renderer.func_147782_a(0.0, 0.0, 0.0, 1.0, 0.125, 1.0);
        renderer.func_147784_q(Vending.supports[meta], x, y, z);
        return false;
    }
    
    public boolean shouldRender3DInInventory(final int var1) {
        return true;
    }
    
    public int getRenderId() {
        return BlockVendingMachineRenderer.id;
    }
}
