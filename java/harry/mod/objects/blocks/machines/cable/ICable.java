package harry.mod.objects.blocks.machines.cable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

interface ICable {
	
	public enum Type {
		MASTER, SLAVE
	}

	public ICableNetwork getNetwork();
	
	public ICable getMaster();
	
	public Type getType();
	
	public IConnection[] getConnections();
	
	public IConnection[] getConnectionsOfType(IConnection.Type type);
	
	public boolean checkBlockstate(IBlockState state);
	
	public TileEntity updateBlockState(World world, BlockPos pos);
	
	public void update(UpdateContext context);
	
	public void handleUpdate(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos);
	
	
}
