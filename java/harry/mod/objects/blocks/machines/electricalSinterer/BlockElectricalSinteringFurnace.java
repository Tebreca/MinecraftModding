/**
 * 
 */
package harry.mod.objects.blocks.machines.electricalSinterer;

import harry.mod.objects.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Ewan Arends
 * date : 25th march 2018
 *  
 * This block is a fork from the {@link BlockSinteringFurnace} block, Working on FE (Forge Enery)
 */
public class BlockElectricalSinteringFurnace extends BlockBase {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	/**
	 * @param name
	 * @param material
	 */
	public BlockElectricalSinteringFurnace(String name) {
		super(name, Material.ROCK);
		setHarvestLevel("pickaxe", 2);
		setHardness(3.0f);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityElectricalSinteringFurnace();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
}
