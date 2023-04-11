package info.jbcs.minecraft.vending;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class GeneralClient
{
    public static Random rand;
    static HashMap<String, ResourceLocation> resources;
    
    public static void playChiselSound(final World world, final int x, final int y, final int z, final String sound) {
        Minecraft.func_71410_x().field_71441_e.func_72980_b(x + 0.5, y + 0.5, z + 0.5, sound, 0.3f + 0.7f * GeneralClient.rand.nextFloat(), 0.6f + 0.4f * GeneralClient.rand.nextFloat(), true);
    }
    
    public static IIcon getMissingIcon() {
        return (IIcon)((TextureMap)Minecraft.func_71410_x().func_110434_K().func_110581_b(TextureMap.field_110575_b)).func_110572_b("missingno");
    }
    
    public static void bind(final String textureName) {
        ResourceLocation res = GeneralClient.resources.get(textureName);
        if (res == null) {
            res = new ResourceLocation(textureName);
            GeneralClient.resources.put(textureName, res);
        }
        Minecraft.func_71410_x().func_110434_K().func_110577_a(res);
    }
    
    static {
        GeneralClient.rand = new Random();
        GeneralClient.resources = new HashMap<String, ResourceLocation>();
    }
}
