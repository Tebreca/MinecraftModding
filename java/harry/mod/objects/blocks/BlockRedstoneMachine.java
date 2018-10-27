package harry.mod.objects.blocks;

import java.util.HashMap;

import harry.mod.objects.blocks.tile.TileRedstoneMachine;
import harry.mod.util.helpers.EnumHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockRedstoneMachine extends BlockBase {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	HashMap<EnumFacing, Boolean> REDSTONE_ACCECPTANCE = new HashMap<>(6);
	private boolean usesFacing = usesFacing();
	private boolean usesTE = usesTE();

	public BlockRedstoneMachine(String name, Material material) {
		super(name, material);
		initRedstoneSides();
		if(usesFacing)
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	protected abstract boolean usesFacing();
	protected abstract boolean usesTE();

	@Override
	protected BlockStateContainer createBlockState() {
		return usesFacing ? new BlockStateContainer(this, FACING) : super.createBlockState();
	}

	protected abstract boolean acceptsRedstonePower(EnumFacing facing);

	private void initRedstoneSides() {
		for (EnumFacing facing : EnumFacing.VALUES) {
			this.REDSTONE_ACCECPTANCE.put(facing, this.acceptsRedstonePower(facing));
		}
	}
	
	protected abstract void execute(IBlockState state, World world, BlockPos pos);

	protected boolean canExecute(World world, IBlockState state, BlockPos pos) {
		return true;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		boolean canExecute = this.canExecute(worldIn, state, pos)
				&& this.acceptsRedstonePower(!usesFacing ? (EnumHelper.getFacingFromOffset(pos, fromPos))
				: EnumHelper.getOffsetFacingWithProperty(EnumHelper.getFacingFromOffset(pos, fromPos),
				state.getValue(this.FACING)));

		if (canExecute) {
			if (worldIn.getRedstonePower(pos, null) > 0) {
				if (this.usesTE) {
					TileEntity te = worldIn.getTileEntity(pos);
					if (te instanceof TileRedstoneMachine) {
						((TileRedstoneMachine) te).execute();
					}
				} else
					this.execute(state, worldIn, pos);
			}
		}
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		if(usesFacing)
		switch (state.getValue(this.FACING)) {
		case DOWN:
			i = 1;
			break;
		case EAST:
			i = 2;
			break;
		case NORTH:
			i = 3;
			break;
		case SOUTH:
			i = 4;
			break;
		case UP:
			i = 5;
			break;
		case WEST:
			i = 6;
			break;
		}
		return i;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		switch(meta) {
		case 0:
			return this.getDefaultState();
		case 1:
			return this.getDefaultState().withProperty(this.FACING, EnumFacing.DOWN);
		case 2:
			return this.getDefaultState().withProperty(this.FACING, EnumFacing.EAST);
		case 3:
			return this.getDefaultState().withProperty(this.FACING, EnumFacing.NORTH);
		case 4:
			return this.getDefaultState().withProperty(this.FACING, EnumFacing.SOUTH);
		case 5:
			return this.getDefaultState().withProperty(this.FACING, EnumFacing.UP);
		case 6: 
			return this.getDefaultState().withProperty(this.FACING, EnumFacing.WEST);
		}
		return super.getStateFromMeta(meta);
	}
}
