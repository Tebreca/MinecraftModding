package harry.mod.objects.blocks.machines.cable;

import java.util.List;

import harry.mod.objects.blocks.machines.cable.IConnection.Type;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ICableNetwork {

	public World getWorld();

	public List<ICable> getCables();

	public ICable getMaster();

	public void setMaster(ICable master);

	public void addSlave(ICable slave);

	public List<ICable> getSlaves();

	public IConnection[] getConnections();

	public IConnection[] getConnectionsofType(Type t);

	public int getTransferRate();

	public void onUpdate(IBlockState state, World world, BlockPos pos, ICable cable, BlockPos fromPos);

	public void cableBroken(World world, BlockPos pos);

	public void destroy();
	
	public void remove(ICable cable);
}
