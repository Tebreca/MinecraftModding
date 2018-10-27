package harry.mod.objects.blocks.machines.cable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICable {

	public enum Type {
		MASTER, SLAVE
	}

	public ICableNetwork getNetwork();

	public ICable getMaster();

	public ICable[] getConnectedCables();

	public Type getType();

	public BlockPos getPosition();

	public default boolean isConnectedto(ICable cable) {
		BlockPos own = getPosition();
		BlockPos cable2 = cable.getPosition();
		int totalDifference = Math.abs(own.getX() - cable2.getX()) + Math.abs(own.getY() - cable2.getY())
				+ Math.abs(own.getZ() - cable2.getZ());
		return totalDifference == 1;
	}

	public IConnection[] getConnections();

	public IConnection[] getConnectionsOfType(IConnection.Type type);

	public boolean checkBlockstate(IBlockState state);

	public TileEntity updateBlockState(World world, BlockPos pos);

	public void update(UpdateContext context);

	public void handleUpdate(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos);

	public void spread(ICableNetwork network);

	public void eraseMaster();

	public void notify(ICable slave);

	public void detach();

}
