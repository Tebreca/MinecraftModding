package harry.mod.objects.blocks.machines.cable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class UpdateContext {

	private final World world;
	private final BlockPos pos;
	private final UpdateType type;
	private final byte[] data;

	public enum UpdateType {
		NEW_MASTER, NEW_SLAVE, PROMOTION, DEGRADATION
	}

	public UpdateContext(World world, BlockPos pos, UpdateType type, byte[] data) {
		this.world = world;
		this.pos = pos;
		this.type = type;
		this.data = data;
	}

	public World getWorld() {
		return world;
	}

	public BlockPos getPos() {
		return pos;
	}

	public UpdateType getType() {
		return type;
	}

	public byte[] getData() {
		return data;
	}
}
