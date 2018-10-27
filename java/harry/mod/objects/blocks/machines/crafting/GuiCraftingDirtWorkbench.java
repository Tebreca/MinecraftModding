package harry.mod.objects.blocks.machines.crafting;

import java.util.Random;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GuiCraftingDirtWorkbench extends GuiCrafting {
	World world;
	BlockPos pos;
	Random random = new Random();

	public GuiCraftingDirtWorkbench(InventoryPlayer playerInv, World worldIn) {
		super(playerInv, worldIn);
	}

	public GuiCraftingDirtWorkbench(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition) {
		super(playerInv, worldIn, blockPosition);
		this.world = worldIn;
		this.pos = blockPosition;
	}
}
