package harry.mod.objects.blocks.machines.cable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IConnection {

	public enum Type {
		IN, OUT, BOTH
	}

	public ICable getCable();

	public BlockPos getConnectionPos();

	public TileEntity getConnected();

	public int getMaxIn();

	public int getMaxOut();

	public Type getType();

	public Type getTypeInContext(World worldIn, BlockPos posTile, ICableNetwork network, ICable cable, TileEntity tileEntity);
	
	public void update(UpdateContext context);
	
	public boolean isChunkLoaded(World world);
	
}
