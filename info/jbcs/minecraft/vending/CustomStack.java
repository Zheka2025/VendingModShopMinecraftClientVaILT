package info.jbcs.minecraft.vending;

import java.io.*;
import net.minecraft.nbt.*;
import org.apache.commons.lang3.builder.*;
import net.minecraft.item.*;

public class CustomStack implements Serializable
{
    public String name;
    public int damage;
    public NBTTagCompound tag;
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(41, 59).append((Object)this.name).append(this.damage).append((Object)this.tag).hashCode();
    }
    
    @Override
    public String toString() {
        return this.name + "|" + this.damage + "|" + ((this.tag != null) ? this.tag.toString() : "");
    }
    
    public CustomStack(final ItemStack stack) {
        this.name = stack.func_77973_b().delegate.name();
        this.damage = stack.func_77960_j();
        this.tag = stack.field_77990_d;
    }
    
    public CustomStack(final String name, final short damage) {
        this.name = name;
        this.damage = damage;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (p_equals_1_ instanceof CustomStack) {
            final CustomStack stack = (CustomStack)p_equals_1_;
            return stack.name.equals(this.name) && stack.damage == this.damage && this.compareTag(stack, this);
        }
        return false;
    }
    
    private boolean compareTag(final CustomStack stack, final CustomStack customStack) {
        return stack.tag == null == (customStack.tag == null) && (stack.tag == null || customStack.tag == null || stack.tag.equals((Object)customStack.tag));
    }
}
