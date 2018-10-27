package harry.mod.util.helpers;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import harry.mod.objects.blocks.machines.miner.te.TileEntityMiner;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class NBTHelper {

	public static NBTTagCompound toNBT(Object o) {
		if (o instanceof ItemStack)
			return writeItemStack((ItemStack) o);
		if (o instanceof TileEntityMiner) {
			return writeMiner((TileEntityMiner) o);
		}
		return null;
	}

	private static NBTTagCompound writeMiner(TileEntityMiner o) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("y", o.y);
		compound.setInteger("x", o.x);
		compound.setInteger("z", o.z);
		return compound;
	}

	public static NBTTagCompound writeItemStack(ItemStack i) {
		NBTTagCompound nbt = new NBTTagCompound();
		if (i.isEmpty()) {
			nbt.setBoolean("isempty", true);
		} else {
			nbt.setBoolean("isempty", false);
			nbt.setInteger("count", i.getCount());
			nbt.setString("item", i.getItem().getRegistryName().toString());
			nbt.setByte("type", (byte) 0);
		}
		return nbt;
	}

	@Nullable
	public static Object fromNBT(@Nonnull NBTTagCompound compound) {
		switch (compound.getByte("type")) {
		case 0:
			return readItemStack(compound);
		default:
			return null;
		}
	}

	public static ItemStack readItemStack(NBTTagCompound compound) {
		if(compound.getBoolean("isempty"))
			return ItemStack.EMPTY;
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("item")));
		int count = compound.getInteger("count");
		return new ItemStack(item, count);
	}

}
