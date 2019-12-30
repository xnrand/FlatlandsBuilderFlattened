package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class FLBPopulator extends BlockPopulator {
	private final String genMode;
	private final int height;
	private Material BlockFLB[] = new Material[3];

	protected FLBPopulator(int height, Material[] BlockFLB, String genMode) {
		this.genMode = genMode;
		this.height = height;
		this.BlockFLB = BlockFLB;
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		int blockx, blockz, blockx2, blockz2;
		int chunkx = chunk.getX();
		int chunkz = chunk.getZ();
		Block block;

		if (genMode == "normal" || genMode == null) {
			for (blockx2 = 0; blockx2 < 16; ++blockx2) {
				for (blockz2 = 0; blockz2 < 16; ++blockz2) {
					block = chunk.getBlock(blockx2, height, blockz2);
					block.setType(BlockFLB[0], false);
				}
			}
		} else if (genMode == "grid") {
			for (blockx = 0; blockx < 16; ++blockx) {
				for (blockz = 0; blockz < 16; ++blockz) {
					if (blockx == 0 || blockx == 15 || blockz == 0 || blockz == 15) {
						block = chunk.getBlock(blockx, height, blockz);
						block.setType(BlockFLB[1], false);
					} else {
						block = chunk.getBlock(blockx, height, blockz);
						block.setType(BlockFLB[0], false);
					}
				}
			}
		} else if (genMode == "grid2") {
			for (blockx = 0; blockx < 16; ++blockx) {
				for (blockz = 0; blockz < 16; ++blockz) {
					if (blockx == 0 || blockx == 15 || blockz == 0 || blockz == 15) {
						block = chunk.getBlock(blockx, height, blockz);
						if ((chunkx + chunkz) % 2 == 0) {
							block.setType(BlockFLB[1], false);
						} else {
							block.setType(BlockFLB[2], false);
						}
					} else {
						block = chunk.getBlock(blockx, height, blockz);
						block.setType(BlockFLB[0], false);
					}
				}
			}
		} else if (genMode == "grid3") {
			for (blockx = 0; blockx < 16; ++blockx) {
				for (blockz = 0; blockz < 16; ++blockz) {
					block = chunk.getBlock(blockx, height, blockz);
					if ((chunkx + chunkz) % 2 == 0) {
						block.setType(BlockFLB[1], false);
					} else {
						block.setType(BlockFLB[0], false);
					}
				}
			}
		} else if (genMode == "grid4") {
			if ((chunkx + chunkz) % 2 == 0) {
				for (blockx2 = 0; blockx2 < 16; ++blockx2) {
					for (blockz2 = 0; blockz2 < 16; ++blockz2) {
						block = chunk.getBlock(blockx2, height, blockz2);
						block.setType(BlockFLB[1], false);
					}
				}
			} else {
				for (blockx = 0; blockx < 16; ++blockx) {
					for (blockz = 0; blockz < 16; ++blockz) {
						if (blockx == 0 || blockx == 15 || blockz == 0 || blockz == 15) {
							block = chunk.getBlock(blockx, height, blockz);
							block.setType(BlockFLB[2], false);
						} else {
							block = chunk.getBlock(blockx, height, blockz);
							block.setType(BlockFLB[0], false);
						}
					}
				}
			}
		} else if (genMode == "grid5") {
			for (blockx = 0; blockx < 16; ++blockx) {
				for (blockz = 0; blockz < 16; ++blockz) {
					if (blockx == 0 || blockx == 15 || blockz == 0 || blockz == 15) {
						block = chunk.getBlock(blockx, height, blockz);
						block.setType(BlockFLB[1], false);
					} else {
						block = chunk.getBlock(blockx, height, blockz);
						if ((chunkx + chunkz) % 2 == 0) {
							block.setType(BlockFLB[0], false);
						} else {
							block.setType(BlockFLB[2], false);
						}
					}
				}
			}
		}
	}
}
