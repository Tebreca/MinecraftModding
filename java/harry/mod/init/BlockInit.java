package harry.mod.init;

import java.util.ArrayList;
import java.util.List;

import harry.mod.objects.blocks.*;
import harry.mod.objects.blocks.machines.crafting.BlockDirtCraftingTable;
import harry.mod.objects.blocks.machines.electricalSinterer.BlockElectricalSinteringFurnace;
import harry.mod.objects.blocks.machines.miner.BlockMiner;
import harry.mod.objects.blocks.machines.sinterer.BlockSinteringFurnace;
import harry.mod.objects.blocks.machines.spawner.BlockSpawner;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit 
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block BLOCK_COPPER = new BlockBase("block_copper", Material.IRON);
	
	public static final Block ORE_END = new BlockOres("ore_end", "end");
	public static final Block ORE_OVERWORLD = new BlockOres("ore_overworld", "overworld");
	public static final Block ORE_NETHER = new BlockOres("ore_nether", "nether");
	
	public static final Block PLANKS = new BlockPlank("planks");
	public static final Block LOGS = new BlockLogs("log");
	public static final Block LEAVES = new BlockLeaf("leaves");
	public static final Block SAPLINGS = new BlockSaplings("sapling");	
	public static final Block DIRT = new BlockDirts("dirt");
	
	public static final Block SANTA_HAT = new BlockSantaHat("santa_hat");
	
	public static final Block SINTERING_FURNACE = new BlockSinteringFurnace("sintering_furnace");
	public static final Block MINER = new BlockMiner("miner", Material.ROCK);
	public static final Block SPAWNER = new BlockSpawner("spawner", Material.ROCK);
	public static final Block CRAFTER_DIRT = new BlockDirtCraftingTable();
}
