package info.jbcs.minecraft.vending.network;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.*;

public abstract class Message implements IMessage
{
    public abstract void fromBytes(final ByteBuf p0);
    
    public abstract void toBytes(final ByteBuf p0);
}
