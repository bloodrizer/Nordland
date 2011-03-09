
package nordland.world.map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import nordland.util.math.Vector3;
import nordland.world.map.Chunk;
import nordland.world.map.Tile;

/**
 *
 * @author red
 */

public class Map {
    //private java.util.Map<Vector3,Tile> tiles = new HashMap<Vector3,Tile>(1000);

    private Tile[][][] tiles = new Tile[300][300][300];

    private Chunk[][] chunks = new Chunk[3][3];


    public Map(){

    }

    public void build_all(){

        for (int i = -3; i<3; i++)
            for (int j = -3; j<3; j++)
            {
               build_chunk(i,j);
            }
    }

    public void build_chunk(int i, int j){
        //HOLY CRAP IT WORKS!
        //tiles.put(new Vector3(0,0,0),1);
        //int i = tiles.get(new Vector3(0,0,0));

        for (int x= i*Chunk.CHUNK_SIZE; x< (i+1)*Chunk.CHUNK_SIZE; x++)
            for (int z=j*Chunk.CHUNK_SIZE; z<(j+1)*Chunk.CHUNK_SIZE; z++){

                //tiles.put(new Vector3(x,0,z),new Tile(new Vector3(x,0,z)));
                //tiles[x][0][z] = new Tile(new Vector3(x,0,z));
                Vector3 origin = new Vector3(x,0,z);
                set_tile(origin,    new Tile(origin) );
            }




        //System.out.println(get_tile(0,0,0));
        //exit();
    }

    public void set_tile(Vector3 origin, Tile tile){

        origin = V3world2local(origin);
        tiles[origin.x()][origin.y()][origin.z()] = tile;
    }

    public static Vector3 vec_key = new Vector3(0,0,0);

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

        return tiles[x][y][z];
        //return null;

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
