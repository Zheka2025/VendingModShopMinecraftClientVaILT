package info.jbcs.minecraft.vending.network;

import info.jbcs.minecraft.vending.tileentity.*;
import io.netty.buffer.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;

public class SetMoneyMsg extends Message
{
    public int mode;
    public int x;
    public int y;
    public int z;
    public int money;
    
    public SetMoneyMsg() {
        this.money = 0;
    }
    
    public SetMoneyMsg(final TileEntityVendingMachine tile) {
        this.money = 0;
        this.z = tile.field_145849_e;
        this.y = tile.field_145848_d;
        this.x = tile.field_145851_c;
        this.money = tile.money;
        this.mode = tile.mode;
    }
    
    @Override
    public void fromBytes(final ByteBuf buffer) {
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
        this.money = buffer.readInt();
        this.mode = buffer.readInt();
    }
    
    @Override
    public void toBytes(final ByteBuf buffer) {
        buffer.writeInt(this.x);
        buffer.writeInt(this.y);
        buffer.writeInt(this.z);
        buffer.writeInt(this.money);
        buffer.writeInt(this.mode);
    }
    
    public static class Handler implements IMessageHandler<SetMoneyMsg, IMessage>
    {
        public IMessage onMessage(final SetMoneyMsg message, final MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().field_147369_b;
            final TileEntity tileEntity = player.field_70170_p.func_147438_o(message.x, message.y, message.z);
            if (!(tileEntity instanceof TileEntityVendingMachine)) {
                return null;
            }
            final TileEntityVendingMachine entity = (TileEntityVendingMachine)tileEntity;
            if (entity.ownerName.equals(player.func_70005_c_()) || player.field_71075_bZ.field_75098_d) {
                entity.money = Math.max(message.money, 0);
                entity.mode = message.mode;
                entity.func_70296_d();
            }
            player.field_70170_p.func_147471_g(message.x, message.y, message.z);
            return null;
        }
    }
}
