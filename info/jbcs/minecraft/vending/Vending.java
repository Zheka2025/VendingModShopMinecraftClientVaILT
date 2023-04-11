package info.jbcs.minecraft.vending;

import cpw.mods.fml.common.network.*;
import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import net.minecraftforge.common.config.*;
import info.jbcs.minecraft.vending.network.*;
import info.jbcs.minecraft.vending.proxy.*;
import net.minecraftforge.common.*;
import net.minecraft.item.*;
import info.jbcs.minecraft.vending.block.*;
import info.jbcs.minecraft.vending.item.*;
import cpw.mods.fml.common.registry.*;
import info.jbcs.minecraft.vending.tileentity.*;
import net.minecraft.item.crafting.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.tileentity.*;
import info.jbcs.minecraft.vending.inventory.*;
import info.jbcs.minecraft.vending.gui.*;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.*;
import info.jbcs.minecraft.vending.command.*;
import net.minecraft.command.*;

@Mod(modid = "vending", name = "vending", version = "1.2.3")
public class Vending
{
    public static final String MOD_ID = "vending";
    public static final String MOD_NAME = "vending";
    public static final String VERSION = "1.2.3";
    @Mod.Instance("vending")
    public static Vending instance;
    public static FMLEventChannel Channel;
    public static Block blockVendingMachine;
    public static Block blockAdvancedVendingMachine;
    public static Block blockEcoVendingMachine;
    public static Item itemWrench;
    public static GuiHandler guiVending;
    public static GuiHandler guiWrench;
    public static CreativeTabs tabVending;
    static Configuration config;
    public static double priceMultiplier;
    public static final boolean isServer = false;
    public MessagePipeline messagePipeline;
    public static Block[] supports;
    static Object[] reagents;
    @SidedProxy(clientSide = "info.jbcs.minecraft.vending.proxy.ClientProxy", serverSide = "info.jbcs.minecraft.vending.proxy.CommonProxy")
    public static CommonProxy commonProxy;
    
    public Vending() {
        this.messagePipeline = new MessagePipeline();
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Vending.config = new Configuration(event.getSuggestedConfigurationFile());
        Vending.priceMultiplier = Vending.config.getFloat("priceMultiplier", "general", 1.0f, 0.0f, 10.0f, "Multiplier for bottom price limit");
        Vending.config.load();
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register((Object)new EconomyControl.EventLoad());
        Vending.commonProxy.registerPackets(this.messagePipeline);
        Vending.commonProxy.registerEventHandlers();
        Vending.commonProxy.registerRenderers();
        if (Vending.config.get("general", "use custom creative tab", true, "Add a new tab to creative mode and put all vending blocks there.").getBoolean(true)) {
            Vending.tabVending = new CreativeTabs("tabVending") {
                public ItemStack func_151244_d() {
                    return new ItemStack(Vending.blockVendingMachine, 1, 4);
                }
                
                public Item func_78016_d() {
                    return new ItemStack(Vending.blockVendingMachine, 1, 4).func_77973_b();
                }
            };
        }
        else {
            Vending.tabVending = CreativeTabs.field_78031_c;
        }
        GameRegistry.registerBlock(Vending.blockVendingMachine = (Block)new BlockVendingMachine(Vending.supports, false, false), (Class)ItemMetaBlock.class, "vendingMachine");
        GameRegistry.registerBlock(Vending.blockAdvancedVendingMachine = new BlockVendingMachine(Vending.supports, true, false).func_149663_c("vendingMachineAdvanced"), (Class)ItemMetaBlock.class, "vendingMachineAdvanced");
        GameRegistry.registerBlock(Vending.blockEcoVendingMachine = new BlockVendingMachine(Vending.supports, false, true).func_149663_c("vendingMachineEco"), (Class)ItemMetaBlock.class, "vendingMachineEco");
        GameRegistry.registerItem(Vending.itemWrench = new Item().func_77655_b("vendingMachineWrench").func_77637_a(Vending.tabVending).func_111206_d("Vending:wrench"), "vendingMachineWrench");
        GameRegistry.registerTileEntity((Class)TileEntityVendingMachine.class, "containerVendingMachine");
        for (int i = 0; i < Vending.supports.length; ++i) {
            CraftingManager.func_77594_a().func_92103_a(new ItemStack(Vending.blockVendingMachine, 1, i), new Object[] { "XXX", "XGX", "*R*", 'X', this.getGlass(), 'G', Items.field_151043_k, 'R', Items.field_151137_ax, '*', Vending.reagents[i] });
            CraftingManager.func_77594_a().func_92103_a(new ItemStack(Vending.blockAdvancedVendingMachine, 1, i), new Object[] { "XXX", "XGX", "*R*", 'X', this.getGlass(), 'G', Items.field_151043_k, 'R', Items.field_151107_aW, '*', Vending.reagents[i] });
            CraftingManager.func_77594_a().func_92103_a(new ItemStack(Vending.blockEcoVendingMachine, 1, i), new Object[] { "XXX", "XGX", "*R*", 'X', this.getGlass(), 'G', Items.field_151043_k, 'R', Blocks.field_150367_z, '*', Vending.reagents[i] });
        }
        Vending.guiVending = new GuiHandler("vending") {
            @Override
            public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
                final TileEntity tileEntity = world.func_147438_o(x, y, z);
                if (!(tileEntity instanceof TileEntityVendingMachine)) {
                    return null;
                }
                final TileEntityVendingMachine e = (TileEntityVendingMachine)tileEntity;
                if (e.advanced) {
                    return new ContainerAdvancedVendingMachine((IInventory)player.field_71071_by, e);
                }
                if (e.eco) {
                    return new ContainerEcoVendingMachine(player.field_71071_by, e);
                }
                return new ContainerVendingMachine((IInventory)player.field_71071_by, e);
            }
            
            @Override
            public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
                final TileEntity tileEntity = world.func_147438_o(x, y, z);
                if (!(tileEntity instanceof TileEntityVendingMachine)) {
                    return null;
                }
                final TileEntityVendingMachine e = (TileEntityVendingMachine)tileEntity;
                if (e.advanced) {
                    return new GuiAdvancedVendingMachine(player.field_71071_by, e);
                }
                if (e.eco) {
                    return new GuiEcoVendingMachine(player.field_71071_by, e);
                }
                return new GuiVendingMachine(player.field_71071_by, e);
            }
        };
        Vending.guiWrench = new GuiHandler("wrench") {
            @Override
            public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
                return new DummyContainer();
            }
            
            @Override
            public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int x, final int y, final int z) {
                return new GuiWrenchVendingMachine(world, x, y, z, player);
            }
        };
        GuiHandler.register(this);
    }
    
    public static ItemStack getBlockEnder(final String itemString) {
        ItemStack item = null;
        try {
            final String itemClass = "crazypants.enderio.EnderIO";
            final Object obj = Class.forName(itemClass).getField(itemString).get(null);
            if (obj instanceof Block) {
                item = new ItemStack((Block)obj, 1);
            }
        }
        catch (Exception ex) {}
        return item;
    }
    
    public static ItemStack getBlockThaum(final String itemString, final int meta) {
        ItemStack item = null;
        try {
            final String itemClass = "thaumcraft.common.config.ConfigBlocks";
            final Object obj = Class.forName(itemClass).getField(itemString).get(null);
            if (obj instanceof Block) {
                item = new ItemStack((Block)obj, 1, meta);
            }
            else if (obj instanceof ItemStack) {
                item = (ItemStack)obj;
            }
        }
        catch (Exception ex) {}
        return item;
    }
    
    private Object getGlass() {
        if (Loader.isModLoaded("Thaumcraft")) {
            return getBlockThaum("blockCosmeticOpaque", 2);
        }
        if (Loader.isModLoaded("EnderIO")) {
            return getBlockEnder("blockFusedQuartz");
        }
        return Blocks.field_150359_w;
    }
    
    @Mod.EventHandler
    public void postInit(final FMLServerStartingEvent event) {
        event.registerServerCommand((ICommand)new ItemPriceCommand());
    }
    
    static {
        Vending.priceMultiplier = 1.0;
        Vending.supports = new Block[] { Blocks.field_150348_b, Blocks.field_150347_e, Blocks.field_150417_aV, Blocks.field_150344_f, Blocks.field_150462_ai, Blocks.field_150351_n, Blocks.field_150323_B, Blocks.field_150322_A, Blocks.field_150340_R, Blocks.field_150339_S, Blocks.field_150336_V, Blocks.field_150341_Y, Blocks.field_150343_Z, Blocks.field_150484_ah, Blocks.field_150475_bE, Blocks.field_150368_y };
        Vending.reagents = new Object[] { Blocks.field_150348_b, Blocks.field_150347_e, Blocks.field_150417_aV, Blocks.field_150344_f, Blocks.field_150462_ai, Blocks.field_150351_n, Blocks.field_150323_B, Blocks.field_150322_A, Items.field_151043_k, Items.field_151042_j, Blocks.field_150336_V, Blocks.field_150341_Y, Blocks.field_150343_Z, Items.field_151045_i, Items.field_151166_bC, Blocks.field_150368_y };
    }
}
