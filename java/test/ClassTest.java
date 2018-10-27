package test;

import harry.mod.objects.blocks.machines.miner.te.TileEntityMiner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ClassTest {

	public ClassTest() {
		TileEntityMiner tileEntity = new TileEntityMiner();
		EntityPlayer player = new EntityPlayer(null, null) {
			
			@Override
			public boolean isSpectator() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isCreative() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		
		EnumHand hand = EnumHand.MAIN_HAND;
		IItemHandler iItemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		ItemStack stack = iItemHandler.insertItem(0, player.getActiveItemStack(), false);
		ItemStack activeStack = player.getHeldItem(hand);
		if(stack == ItemStack.EMPTY) {
			ItemStack stackInSlot = iItemHandler.getStackInSlot(0);
			
			if(activeStack.getItem() == stackInSlot.getItem()) {
				activeStack.grow(iItemHandler.extractItem(0, 1, false).getCount());
			} else {
				player.addItemStackToInventory(iItemHandler.extractItem(0, 1, false));
				iItemHandler.insertItem(0, activeStack, false);
				activeStack.shrink(1);
			}
		} else {
			activeStack.shrink(1);
		}
	}

}
