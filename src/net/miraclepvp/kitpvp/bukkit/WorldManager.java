package net.miraclepvp.kitpvp.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldManager {

    public static void loadWorld(WorldType worldType, Boolean generateStructures, World.Environment env, String name){
        WorldCreator wc = new WorldCreator(name);
        wc.type(worldType);
        wc.generateStructures(generateStructures);
        wc.generator(new VoidGenerator());
        wc.environment(env);
        wc.createWorld();
    }

    public static void loadEmptyWorld(World.Environment env, String name){
        loadWorld(WorldType.FLAT, false, env, name);
    }

    private static class VoidGenerator extends ChunkGenerator {

        public byte[][] blockSections;

        @Override
        public ChunkData generateChunkData(World world, Random random, int cx, int cz, BiomeGrid biomeGrid) {
            final ChunkData chunkData = createChunkData(world);

            Biome biome = Biome.PLAINS;
            for (int x = 0; x <= 15; x++) {
                for (int z = 0; z <= 15; z++) {
                    biomeGrid.setBiome(x, z, biome);
                }
            }

            return chunkData;
        }

        public byte[][] generateBlockSections(World world, Random random, int x, int z, BiomeGrid biomes) {
            if (blockSections == null) {
                blockSections = new byte[world.getMaxHeight() / 16][];
            }
            return blockSections;
        }

        @Override
        public boolean canSpawn(World world, int x, int z) {
            return true;
        }

        @Override
        public List<BlockPopulator> getDefaultPopulators(World world) {
            return Collections.emptyList();
        }
    }
}
