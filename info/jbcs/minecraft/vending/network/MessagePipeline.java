package info.jbcs.minecraft.vending.network;

import cpw.mods.fml.common.network.simpleimpl.*;

public class MessagePipeline extends SimpleNetworkWrapper
{
    public MessagePipeline() {
        super("Vending");
    }
}
