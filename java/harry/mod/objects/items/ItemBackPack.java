package harry.mod.objects.items;

import harry.mod.util.inventory.InventoryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemBackPack extends ItemBase {

	public ItemBackPack() {
		super("backpack");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(!playerIn.isSneaking()) {
			ItemStack stack = playerIn.getHeldItem(handIn);
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new InventoryItem(1).toNBT());
			}
			NBTTagCompound tagCompound = stack.getTagCompound();
			InventoryItem inventory = InventoryItem.fromNBT(tagCompound);
			playerIn.sendStatusMessage(new TextComponentString("Your backpack has the following items:"), false);
			int amount = 0;
			for (ItemStack activeStack : inventory.inventory) {
				if(!activeStack.isEmpty()) {
					playerIn.sendStatusMessage(new TextComponentString(activeStack.getCount() + " * " + activeStack.getDisplayName()), false);
					amount++;
				}
			}
			if(amount == 0) {
				playerIn.sendStatusMessage(new TextComponentString("Your backpack is empty!"), false);
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
