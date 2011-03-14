
package nordland.world.map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import nordland.util.math.Vector3;
import nordland.world.map.Chunk;
import nordland.world.map.Tile;

import nordland.render.overlay.OverlaySystem;

/**
 *
 * @author red
 */

public class Map {
    //private java.util.Map<Vector3,Tile> tiles = new HashMap<Vector3,Tile>(1000);

    //private static Tile[][][] tiles = new Tile[200][200][200];
    public int tile_count = 0;
    public static Tile[][][] tiles = new Tile[255][255][255];
    private Chunk[][] chunks = new Chunk[3][3];


    public Map(){

    }

    public void build_all(){

        /*for (int i = -1; i<0; i++)
            for (int j = -1; j<0; j++)
            {
               build_chunk(i,j);
            }*/

        build_chunk(0,0);

        System.out.println("Builded " + Integer.toString(tile_count)+ " tiles");

        //OverlaySystem.getInstance().debug.tiles = tile_count;
    }

    public void build_chunk(int i, int j){

        for (int x= i*Chunk.CHUNK_SIZE; x< (i+1)*Chunk.CHUNK_SIZE; x++)
                for (int y= -1*Chunk.CHUNK_SIZE; y<= 0*Chunk.CHUNK_SIZE; y++)
                    for (int z=j*Chunk.CHUNK_SIZE; z<(j+1)*Chunk.CHUNK_SIZE; z++){

                    Vector3 origin = new Vector3(x,y,z);

                    if (java.lang.Math.random() > 0.5 ) {
                        Tile tmp_tile = new Tile(origin);

                        set_tile(origin, tmp_tile);

                        tile_count++;
                   }
            }

    }

    public void set_tile(Vector3 origin, Tile tile){

        origin = V3world2local(origin);
        tiles[origin.x()][origin.y()][origin.z()] = tile;
    }

    public static Vector3 util_v3 = new Vector3(0,0,0);



    public int viewport_x = 0;
    public int viewport_y = 0;
    public int viewport_z = 0;

    public Tile get_tile( int x, int y, int z) {
        //vec_key.set(x,y,z);
        //Vector3 key = new Vector3(x,y,z);

        //if (tiles.containsKey(key)){
        //    return tiles.get(key);
        //}
        //return tiles.get(vec_key);

        x = x + (3*Chunk.CHUNK_SIZE);
        y = y + (3*Chunk.CHUNK_SIZE);
        z = z + (3*Chunk.CHUNK_SIZE);


        /*if (x>=0 && y>=0 && z>=0){
            return tiles[x][y][z];
        }*/
        return tiles[x][y][z];
        //return null;

    }


    public void drop_tile( int x, int y, int z) {

        Tile tile = null;

        util_v3.set(x,y,z);
        util_v3 = V3world2local(util_v3);

        x = util_v3.x;
        y = util_v3.y;
        z = util_v3.z;

        System.out.println("clearing tile @ "+ Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z));

        if ((tiles[x][y][z]) != null){
            tiles[x][y][z] = null;

            if (tiles[x-1][y][z] != null)
                tiles[x-1][y][z].v_reset();
            if (tiles[x+1][y][z] != null)
                tiles[x+1][y][z].v_reset();
            if (tiles[x][y+1][z] != null)
                tiles[x][y+1][z].v_reset();
            if (tiles[x][y-1][z] != null)
                tiles[x][y-1][z].v_reset();
            if (tiles[x][y][z+1] != null)
                tiles[x][y][z+1].v_reset();
            if (tiles[x][y][z-1] != null)
                tiles[x][y][z-1].v_reset();
        }
      
    }

    public void add_tile(int x, int y, int z) {
        Vector3 origin = new Vector3(x,y,z);
        Tile tmp_tile = new Tile(origin);
        set_tile(origin, tmp_tile);
    }

    public Vector3 V3world2local(Vector3 world) {

        world.set(
                    world.x() + (3*Chunk.CHUNK_SIZE),
                    world.y() + (3*Chunk.CHUNK_SIZE),
                    world.z() + (3*Chunk.CHUNK_SIZE)
                );

        return world;
    }
}
