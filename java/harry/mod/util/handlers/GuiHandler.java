package harry.mod.util.handlers;

import harry.mod.objects.blocks.machines.crafting.ContainerCrafting;
import harry.mod.objects.blocks.machines.crafting.GuiCraftingDirtWorkbench;
import harry.mod.objects.blocks.machines.sinterer.ContainerSinteringFurnace;
import harry.mod.objects.blocks.machines.sinterer.GuiSinteringFurnace;
import harry.mod.objects.blocks.machines.sinterer.TileEntitySinteringFurnace;
import harry.mod.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GUI_SINTERING_FURNACE)
			return new ContainerSinteringFurnace(player.inventory,
					(TileEntitySinteringFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_DIRT_CRAFTER)
			return new ContainerCrafting(world, new BlockPos(x, y, z));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GUI_SINTERING_FURNACE)
			return new GuiSinteringFurnace(player.inventory,
					(TileEntitySinteringFurnace) world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_DIRT_CRAFTER)
			return new GuiCraftingDirtWorkbench(player.inventory, world, new BlockPos(x, y, z));
		return null;
	}

}
