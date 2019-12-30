package net.dkebnh.bukkit.FlatlandsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class FLBGenerator extends ChunkGenerator {

	private final Logger log = Logger.getLogger("Minecraft");
	private String genMode;
	private String genModeParse;
	private int height = 0;
	private final Material[] BlockFLB = new Material[3];
	private final Biome BiomeFLB = Biome.PLAINS;

	private void setDefaults(String msg) {
		genMode = "grid2";
		BlockFLB[0] = Material.BLACK_WOOL;
		BlockFLB[1] = Material.GRAY_WOOL;
		BlockFLB[2] = Material.LIGHT_GRAY_WOOL;

		if (height != 0) { // Checks to see if a height variable is accepted by FlatlandsBuiler.
			msg = "[FlatlandsBuilder] No Block Settings provided, using default blocks 'black_wool:gray_wool,light_gray_wool'";
		} else {
			height = 64;
		}
		this.log.warning(msg);
	}

	public FLBGenerator(String id) {
		if (id != null) {
			try {
				String[] parts = id.split("[;]");
				id = parts.length == 0 ? "" : parts[0];
				if (parts.length >= 2) {
					this.genModeParse = parts[1];
				}

				List<String> genModechk = Arrays.asList("normal", "grid", "grid2", "grid3", "grid4", "grid5");

				if (genModechk.contains(genModeParse)) {
					log.info("[FlatlandsBuilder] Generation Mode " + genModeParse);
				} else {
					genModeParse = null;
				}

				if (parts[0].length() > 0) {
					String tokens[] = parts[0].split("[,]");

					if (tokens.length > 4) { // Checks to see if a larger string has been provided, and adjusts
												// accordingly.
						String tokenStore[] = new String[4];

						log.warning("[FlatlandsBuilder] The number of variables entered [" + tokens.length
								+ "], is too many. Using first four.");

						for (int tokenNumb = 0; tokenNumb < 4; tokenNumb++) {
							tokenStore[tokenNumb] = tokens[tokenNumb];
						}

						tokens = tokenStore;

					}

					height = Integer.parseInt(tokens[0]); // Sets height variable.

					if (height <= 0 || height >= 128) { // May change max height later on, making it generate any higher
														// seems impractical at this stage.
						log.warning("[FlatlandsBuilder] Invalid height '" + tokens[0] + "'. Using 64 instead.");
						height = 64;
					}

					for (int i = 1; i < tokens.length; i++) { // Sets blocks array in sequential order.
						int t = i - 1;

						String materialToken = tokens[i];

						Material mat = Material.matchMaterial(materialToken);

						if (mat == null) {
							if (mat == null) {
								log.warning("[FlatlandsBuilder] Invalid Block ID '" + materialToken
										+ "'. Defaulting to WHITE_WOOL.");
								mat = Material.WHITE_WOOL;
							}
						}

						if (!mat.isBlock()) {
							log.warning("[FlatlandsBuilder] Error, Block'" + materialToken
									+ "' is not a block. Defaulting to WHITE_WOOL.");
							mat = Material.WHITE_WOOL;
						}

						BlockFLB[t] = mat;
					}

					if (tokens.length == 4) { // Sets generation format based on number of variables entered.
						genMode = "grid2";
					} else if (tokens.length == 3) {
						genMode = "grid";
					} else if (tokens.length == 2) {
						genMode = "normal";
					} else {
						this.setDefaults(
								"[FlatlandsBuilder] Invalid Settings provided, using defaults '64,wool:15,wool:7,wool:8'");
					}

					if (genModeParse != null) {
						if (tokens.length == 4) { // Sets generation format based on number of variables entered.
							if (genModeParse.equalsIgnoreCase("grid5")) {
								genMode = "grid5";
							} else if (genModeParse.equalsIgnoreCase("grid4")) {
								genMode = "grid4";
							} else {
								genMode = "grid2";
							}
						} else if (tokens.length == 3) {
							if (genModeParse.equalsIgnoreCase("grid3")) {
								genMode = "grid3";
							} else {
								genMode = "grid";
							}
						} else if (tokens.length == 2) {
							genMode = "normal";
						} else {
							this.setDefaults(
									"[FlatlandsBuilder] Invalid Settings provided, using defaults '64,wool:15,wool:7,wool:8'");
						}
					}
				}
			} catch (Exception e) {
				log.severe("[FlatlandsBuilder] Error parsing FlatlandsBuilder Settings '" + id
						+ "'. using defaults '64,wool:15,wool:7,wool:8': " + e.toString());
				e.printStackTrace();
				height = 64;
				genMode = "grid2";
				BlockFLB[0] = Material.BLACK_WOOL;
				BlockFLB[1] = Material.GRAY_WOOL;
				BlockFLB[2] = Material.LIGHT_GRAY_WOOL;
			}

		} else {
			this.setDefaults("[FlatlandsBuilder] No Settings provided, using defaults '64,wool:15,wool:7,wool:8'");
		}
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		ArrayList<BlockPopulator> populators = new ArrayList<>();

		populators.add(new FLBPopulator(height, BlockFLB, genMode));

		return populators;
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		return new Location(world, 0, height + 1, 0);
	}

	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkx, int chunkz, BiomeGrid biome) {
		ChunkData blocks = createChunkData(world);
		int x, y, z;

		for (x = 0; x < 16; ++x) {
			for (z = 0; z < 16; ++z) {
				blocks.setBlock(x, 0, z, Material.BEDROCK);
				for (y = 1; y < height; ++y) {
					blocks.setBlock(x, y, z, Material.STONE);
				}
				blocks.setBlock(x, height, z, Material.WHITE_WOOL);
				biome.setBiome(x, z, BiomeFLB);
			}
		}
		return blocks;
	}
}
