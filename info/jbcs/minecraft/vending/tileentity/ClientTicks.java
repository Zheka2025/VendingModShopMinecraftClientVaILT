package info.jbcs.minecraft.vending.tileentity;

import cpw.mods.fml.common.gameevent.*;
import cpw.mods.fml.common.eventhandler.*;

public class ClientTicks
{
    public static int ticks;
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent e) {
        ++ClientTicks.ticks;
        if (ClientTicks.ticks == 21) {
            ClientTicks.ticks = 0;
        }
    }
    
    static {
        ClientTicks.ticks = 0;
    }
}
