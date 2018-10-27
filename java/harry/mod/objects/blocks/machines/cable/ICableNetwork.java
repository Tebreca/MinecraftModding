package harry.mod.objects.blocks.machines.cable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

interface ICableNetwork {

	public ICable[] getCables();
	
	public ICable getMaster();
	
	public ICable[] getChildren();

	public IConnection[] getConnections();

	public IConnection[] getConnectionsofType();
	
	public int getTransferRate();
	
	public void onUpdate(IBlockState state, World world, BlockPos pos, ICable cable, BlockPos fromPos);
	
	public void cableBroken(World world, BlockPos pos);
}
