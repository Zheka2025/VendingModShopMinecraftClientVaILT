package info.jbcs.minecraft.vending.network;

import io.netty.buffer.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.nbt.*;
import info.jbcs.minecraft.vending.*;

public class MsgPrice extends Message
{
    private NBTTagCompound tag;
    
    public MsgPrice() {
    }
    
    public MsgPrice(final NBTTagCompound tag) {
        this.tag = tag;
    }
    
    @Override
    public void fromBytes(final ByteBuf buf) {
        this.tag = ByteBufUtils.readTag(buf);
    }
    
    @Override
    public void toBytes(final ByteBuf buf) {
        ByteBufUtils.writeTag(buf, this.tag);
    }
    
    public static class Handler implements IMessageHandler<MsgPrice, IMessage>
    {
        public IMessage onMessage(final MsgPrice message, final MessageContext ctx) {
            EconomyControl.setNBTTagCompoud((NBTTagList)message.tag.func_74781_a("list"));
            EconomyControl.hasChanges = true;
            return null;
        }
    }
}
