package harry.mod.objects.blocks.machines.spawner;

import java.util.List;
import java.util.UUID;

import harry.mod.objects.blocks.BlockRedstoneMachine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class BlockSpawner extends BlockRedstoneMachine {

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(this.FACING, placer.getHorizontalFacing().getOpposite()));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}

	public BlockSpawner(String name, Material material) {
		super(name, material);
	}

	@Override
	protected boolean acceptsRedstonePower(EnumFacing facing) {
		return facing == EnumFacing.EAST || facing == EnumFacing.WEST;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, this.FACING);
	}

	@Override
	protected void execute(IBlockState state, World world, BlockPos pos) {
		EnumFacing property = state.getValue(this.FACING);
		BlockPos out, in;
		out = pos.offset(property.getOpposite());
		in = pos.offset(property);
		if (world.isAirBlock(in) && world.isAirBlock(out)) {
			List<EntityLivingBase> input = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(in));
				Entity entity = input.get(0);
			if(entity != null) {
			NBTTagCompound data = new NBTTagCompound();
			if(entity.writeToNBTOptional(data)) {
				entity = EntityList.createEntityFromNBT(data, world);
				entity.setUniqueId(MathHelper.getRandomUUID());
				entity.setPositionAndUpdate(out.getX() + 0.5, out.getY(), out.getZ() + 0.5);
				if(!world.isRemote) {
					world.spawnEntity(entity);
				}
			}
			}
		}
	}

	@Override
	protected boolean usesTE() {
		return false;
	}

	@Override
	protected boolean usesFacing() {
		return true;
	}

}
