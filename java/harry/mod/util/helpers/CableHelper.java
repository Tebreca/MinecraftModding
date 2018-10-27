package harry.mod.util.helpers;

import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

import harry.mod.objects.blocks.machines.cable.ICable;
import harry.mod.objects.blocks.machines.cable.ICable.Type;
import harry.mod.objects.blocks.machines.cable.ICableNetwork;
import harry.mod.objects.blocks.machines.cable.UpdateContext;
import harry.mod.objects.blocks.machines.cable.UpdateContext.UpdateType;
import harry.mod.util.factory.cable.NetworkFactory;
import net.minecraft.world.World;

public class CableHelper {

	public static void split(ICableNetwork network, ICable broken) {
		World world = network.getWorld();
		int transferrate = network.getTransferRate();
		List<ICable> cableList = network.getCables();
		Stack<ICableNetwork> networks = new Stack<>();
		ICable oldMaster = network.getMaster();
		network.destroy();
		ICable newMaster;
		for (ICable cable : cableList) {
			cable.eraseMaster();
		}
		ICableNetwork networkNew = NetworkFactory.newInstance().setWorld(world).setMaster(oldMaster).setTransferRate(network.getTransferRate()).build();
		oldMaster.spread(networkNew);
		networks.push(NetworkFactory.newInstance().setMaster(oldMaster)
				.setSlaves((ICable[]) cableList.stream().filter(c -> c.getMaster() == oldMaster).toArray())
				.setTransferRate(transferrate).setWorld(world).build());
		ICable[] notConnected = (ICable[]) cableList.stream().filter(c -> c.getMaster() == null).toArray();
		while (notConnected.length != 0) {
			newMaster = notConnected[0];
			networkNew = NetworkFactory.newInstance().setWorld(world).setMaster(newMaster).setTransferRate(network.getTransferRate()).build();
			newMaster.spread(networkNew);
			networks.push(NetworkFactory.newInstance().setMaster(oldMaster)
					.setSlaves((ICable[]) cableList.stream().filter(new Comparator(newMaster)).toArray())
					.setTransferRate(transferrate).setWorld(world).build());
			notConnected = (ICable[]) cableList.stream().filter(c -> c.getMaster() == null).toArray();
		}
	}

	public void sendEventNewMaster(ICableNetwork network, ICable newMaster) {
		String pos = Long.toString(newMaster.getPosition().toLong());
		network.setMaster(newMaster);
		UpdateContext context = new UpdateContext(network.getWorld(), newMaster.getPosition(), UpdateType.NEW_MASTER,
				pos.getBytes());
		for (ICable cable : network.getSlaves()) {
			cable.update(context);
		}
		if (newMaster.getType() != Type.MASTER) {
			newMaster.update(context);
		}
	}
	
	public void connect(ICableNetwork network1, ICableNetwork network2) {
		network1.getCables().forEach(cable -> {
			network2.addSlave(cable);
			network2.getMaster().notify(cable);
		});
		network1.destroy();
	}

	private static class Comparator implements Predicate<ICable> {

		ICable master;

		public Comparator(ICable master) {
			this.master = master;
		}

		@Override
		public boolean test(ICable t) {
			return t.getMaster() == master;
		}

	}

}
