package info.jbcs.minecraft.vending.network;

import io.netty.buffer.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import info.jbcs.minecraft.vending.inventory.*;
import info.jbcs.minecraft.vending.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class MsgAdvVenSetItem extends Message
{
    private int id;
    private int count;
    private int damage;
    
    public MsgAdvVenSetItem() {
    }
    
    public MsgAdvVenSetItem(final int id, final int count, final int damage) {
        this.id = id;
        this.count = count;
        this.damage = damage;
    }
    
    @Override
    public void fromBytes(final ByteBuf buf) {
        this.id = buf.readInt();
        this.count = buf.readInt();
        this.damage = buf.readInt();
    }
    
    @Override
    public void toBytes(final ByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeInt(this.count);
        buf.writeInt(this.damage);
    }
    
    public static class Handler implements IMessageHandler<MsgAdvVenSetItem, IMessage>
    {
        public IMessage onMessage(final MsgAdvVenSetItem message, final MessageContext ctx) {
            final EntityPlayerMP player = ctx.getServerHandler().field_147369_b;
            final Container con = player.field_71070_bA;
            if (con == null || !(con instanceof ContainerAdvancedVendingMachine)) {
                return null;
            }
            final ContainerAdvancedVendingMachine container = (ContainerAdvancedVendingMachine)con;
            ((TileEntityVendingMachine)container.entity).setBoughtItem((message.id == 0) ? null : new ItemStack(Item.func_150899_d(message.id), message.count, message.damage));
            return null;
        }
    }
}
