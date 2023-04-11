package info.jbcs.minecraft.vending.proxy;

import net.minecraft.client.*;
import cpw.mods.fml.common.*;
import net.minecraftforge.common.*;
import info.jbcs.minecraft.vending.gui.*;
import info.jbcs.minecraft.vending.tileentity.*;
import info.jbcs.minecraft.vending.renderer.*;
import net.minecraft.client.renderer.tileentity.*;
import cpw.mods.fml.client.registry.*;

public class ClientProxy extends CommonProxy
{
    private Minecraft mc;
    
    @Override
    public void registerEventHandlers() {
        FMLCommonHandler.instance().bus().register((Object)new ClientTicks());
        MinecraftForge.EVENT_BUS.register((Object)new HintGui(Minecraft.func_71410_x()));
    }
    
    @Override
    public void registerRenderers() {
        BlockVendingMachineRenderer.id = RenderingRegistry.getNextAvailableRenderId();
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileEntityVendingMachine.class, (TileEntitySpecialRenderer)new TileEntityVendingMachineRenderer());
        RenderingRegistry.registerBlockHandler((ISimpleBlockRenderingHandler)new BlockVendingMachineRenderer());
    }
}
