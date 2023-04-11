package info.jbcs.minecraft.vending.proxy;

import cpw.mods.fml.relauncher.*;
import info.jbcs.minecraft.vending.network.*;

public class CommonProxy
{
    public void registerEventHandlers() {
    }
    
    public void registerPackets(final MessagePipeline pipeline) {
        pipeline.registerMessage((Class)SetMoneyMsg.Handler.class, (Class)SetMoneyMsg.class, 2, Side.SERVER);
        pipeline.registerMessage((Class)MsgPrice.Handler.class, (Class)MsgPrice.class, 0, Side.CLIENT);
        pipeline.registerMessage((Class)MsgAdvVenSetItem.Handler.class, (Class)MsgAdvVenSetItem.class, 0, Side.SERVER);
        pipeline.registerMessage((Class)MsgWrench.Handler.class, (Class)MsgWrench.class, 1, Side.SERVER);
    }
    
    public void registerRenderers() {
    }
}
