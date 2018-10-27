package harry.mod.util.inventory;

import javax.annotation.Nonnull;

import harry.mod.util.helpers.NBTHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class InventoryItem {

	@Nonnull
	public ItemStack[] inventory;
	public int size;

	/**
	 * Creates a new Inventory to be assigned to an item.
	 * 
	 * @param size
	 *            capacity of the inventory
	 */
	public InventoryItem(int size) {
		inventory = new ItemStack[size];
		this.size = size;
		for (int i = 0; i < size; i++) {
			inventory[i] = ItemStack.EMPTY;
		}
	}

	public static InventoryItem fromNBT(NBTTagCompound compound) {
		int size = compound.getInteger("size");
		NBTTagCompound items = (NBTTagCompound) compound.getTag("items");
		InventoryItem inventoryItem = new InventoryItem(size);
		for (int i = 0; i < size; i++) {
			ItemStack stack = NBTHelper.readItemStack((NBTTagCompound) compound.getTag("stack" + i));
			inventoryItem.inventory[i] = stack;
		}
		return inventoryItem;
	}

	public NBTTagCompound toNBT() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInteger("size", this.size);
		NBTTagCompound items = new NBTTagCompound();
		for (int i = 0; i < this.size; i++) {
			ItemStack stack = this.inventory[i];
			items.setTag("stack" + i, NBTHelper.writeItemStack(stack));
		}
		compound.setTag("items", items);
		return compound;
	}

}
