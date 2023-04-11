package info.jbcs.minecraft.vending.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import info.jbcs.minecraft.vending.*;
import info.jbcs.minecraft.vending.network.*;
import cpw.mods.fml.common.network.simpleimpl.*;
import net.minecraft.util.*;

public class ItemPriceCommand extends CommandBase
{
    public boolean func_71519_b(final ICommandSender p_71519_1_) {
        return p_71519_1_ instanceof EntityPlayer && ((EntityPlayer)p_71519_1_).field_71075_bZ.field_75098_d;
    }
    
    public String func_71517_b() {
        return "minprice";
    }
    
    public String func_71518_a(final ICommandSender p_71518_1_) {
        return "/minprice <amount>";
    }
    
    public void func_71515_b(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (this.func_71519_b(p_71515_1_) && p_71515_2_.length > 0) {
            final int price = Integer.parseInt(p_71515_2_[0]);
            EconomyControl.setMinPrice(((EntityPlayer)p_71515_1_).func_71045_bC(), price);
            if (!((EntityPlayer)p_71515_1_).field_70170_p.field_72995_K) {
                Vending.instance.messagePipeline.sendToServer((IMessage)new MsgPrice(EconomyControl.getNbtTagCompound()));
            }
        }
        else {
            p_71515_1_.func_145747_a((IChatComponent)new ChatComponentText(this.func_71518_a(p_71515_1_)));
        }
    }
}
