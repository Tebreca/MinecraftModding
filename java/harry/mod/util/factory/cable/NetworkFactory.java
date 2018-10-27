package harry.mod.util.factory.cable;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import harry.mod.objects.blocks.machines.cable.ICable;
import harry.mod.objects.blocks.machines.cable.ICableNetwork;
import harry.mod.objects.blocks.machines.cable.IConnection;
import harry.mod.objects.blocks.machines.cable.UpdateContext;
import harry.mod.objects.blocks.machines.cable.UpdateContext.UpdateType;
import harry.mod.util.helpers.CableHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetworkFactory {

	public enum Type {
		DEFAULT, EMPTY
	}

	private NetworkFactory(Type t) {
		switch (t) {
		case DEFAULT:
			this.slaves = new ICable[0];
			this.master = null;
			this.transferRate = 0;
			break;
		case EMPTY:
			break;

		}
	}

	private ICable[] slaves;
	private ICable master;
	private int transferRate;
	private World world;

	public static NetworkFactory newInstance() {
		return new NetworkFactory(Type.DEFAULT);
	}

	public NetworkFactory setMaster(ICable master) {
		this.master = master;
		return this;
	}

	public NetworkFactory setTransferRate(int rate) {
		this.transferRate = rate;
		return this;
	}

	public NetworkFactory setWorld(World w) {
		this.world = w;
		return this;
	}

	public NetworkFactory setSlaves(ICable... slaves) {
		this.slaves = slaves;
		return this;
	}

	public ICableNetwork build() {
		return new ICableNetwork() {

			Stack<IConnection> connections = new Stack<>();
			ICable master = NetworkFactory.this.master;
			List<ICable> slaves = Arrays.asList(NetworkFactory.this.slaves);
			World world = NetworkFactory.this.world;

			@Override
			public void setMaster(ICable master) {
				this.master = master;
			}

			@Override
			public void onUpdate(IBlockState state, World world, BlockPos pos, ICable cable, BlockPos fromPos) {

			}

			@Override
			public World getWorld() {
				return this.world;
			}

			@Override
			public int getTransferRate() {
				// TODO: Make rate depend on the slowest cable in the network
				return transferRate;
			}

			@Override
			public List<ICable> getSlaves() {
				return slaves;
			}

			@Override
			public ICable getMaster() {
				return master;
			}

			@Override
			public IConnection[] getConnectionsofType(IConnection.Type t) {
				return (IConnection[]) connections.stream().filter(connection -> connection.getType() == t).toArray();
			}

			@Override
			public IConnection[] getConnections() {
				return connections.toArray(new IConnection[0]);
			}

			@Override
			public List<ICable> getCables() {
				List l = slaves.subList(0, slaves.size() - 1);
				l.add(master);
				return l;
			}

			@Override
			public void destroy() {
				getCables().forEach(cable -> {
					cable.update(new UpdateContext(world, cable.getPosition(), UpdateType.RESET));
				});
				this.connections = null;
				this.slaves = null;
				this.master = null;
			}

			@Override
			public void cableBroken(World world, BlockPos pos) {
				CableHelper.split(this, (ICable) world.getTileEntity(pos));
			}

			@Override
			public void addSlave(ICable slave) {
				this.slaves.add(slave);
			}

			@Override
			public void remove(ICable cable) {
				if(cable != master)	slaves.remove(cable);
				else {
					this.master = slaves.remove(0);
				}
			}
		};
	}

}
