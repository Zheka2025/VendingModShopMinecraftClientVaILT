package info.jbcs.minecraft.vending.network;

import net.minecraft.tileentity.*;
import info.jbcs.minecraft.vending.tileentity.*;
import io.netty.buffer.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import info.jbcs.minecraft.vending.*;
import net.minecraft.entity.player.*;

public class MsgWrench extends Message
{
    private int x;
    private int y;
    private int z;
    private boolean infinite;
    private String ownerName;
    
    public MsgWrench() {
    }
    
    public MsgWrench(final TileEntity tileEntityVendingMachine, final boolean infinite, final String ownerName) {
        final TileEntityVendingMachine entity = (TileEntityVendingMachine)tileEntityVendingMachine;
        this.x = entity.field_145851_c;
        this.y = entity.field_145848_d;
        this.z = entity.field_145849_e;
        this.infinite = infinite;
        this.ownerName = ownerName;
    }
    
    @Override
    public void fromBytes(final ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.infinite = buf.readBoolean();
        this.ownerName = ByteBufUtils.readUTF8String(buf);
    }
    
    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeBoolean(this.infinite);
        ByteBufUtils.writeUTF8String(buf, this.ownerName);
    }
    
    public static class Handler implements IMessageHandler<MsgWrench, IMessage>
    {
        public IMessage onMessage(final MsgWrench message, final MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().field_147369_b;
            if (player.field_71071_by.func_70448_g() == null || player.field_71071_by.func_70448_g().func_77973_b() != Vending.itemWrench) {
                return null;
            }
            final TileEntity tileEntity = player.field_70170_p.func_147438_o(message.x, message.y, message.z);
            if (!(tileEntity instanceof TileEntityVendingMachine)) {
                return null;
            }
            final TileEntityVendingMachine entity = (TileEntityVendingMachine)tileEntity;
            entity.infinite = message.infinite;
            entity.ownerName = message.ownerName;
            player.field_70170_p.func_147471_g(message.x, message.y, message.z);
            return null;
        }
    }
}
