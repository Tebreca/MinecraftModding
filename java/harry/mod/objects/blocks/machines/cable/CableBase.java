package harry.mod.objects.blocks.machines.cable;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.platform.win32.WinDef.WPARAM;

import harry.mod.objects.blocks.machines.cable.IConnection.Type;
import harry.mod.util.factory.cable.NetworkFactory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class CableBase extends TileEntity implements ICable, ITickable {

	private ICableNetwork network;

	private List<ICable> connectedCables = new ArrayList();
	private List<IConnection> connections = new ArrayList<>();

	private Type type;

	public void onPlaced() {

	}

	@Override
	public ICableNetwork getNetwork() {
		return network;
	}

	@Override
	public ICable getMaster() {
		return network.getMaster();
	}

	@Override
	public ICable[] getConnectedCables() {
		return connectedCables.toArray(new ICable[0]);
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public BlockPos getPosition() {
		return pos;
	}

	@Override
	public IConnection[] getConnections() {
		return connections.toArray(new IConnection[0]);
	}

	@Override
	public IConnection[] getConnectionsOfType(IConnection.Type type) {
		return (IConnection[]) connections.stream().filter(c -> c.getType() == type).toArray();
	}

	@Override
	public void spread(ICableNetwork network) {
		this.network = network;
		for(EnumFacing facing : EnumFacing.VALUES) {
			World world = network.getWorld(); 
			TileEntity nextTo = world.getTileEntity(pos.offset(facing));
			if(nextTo != null) {
				if(nextTo instanceof ICable) {
					ICable cable = ((ICable) nextTo);
					if(cable.getNetwork() == null)
						cable.spread(network);
					else
						return;
				}
			}
		}
	}	

	abstract int getTransferRate();

	@Override
	public void eraseMaster() {
		this.network = null;
	}

	@Override
	public void notify(ICable slave) {
		slave.update(UpdateContext.notifyNewMaster(slave.getPosition(), network.getWorld(), pos));
	}

	@Override
	public void detach() {
		this.network = NetworkFactory.newInstance().setWorld(world).setMaster(this).setTransferRate(getTransferRate()).build();
	}

}
