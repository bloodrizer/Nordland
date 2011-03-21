
package nordland.world.map;

import java.util.HashMap;

import nordland.util.math.Vector3;


import nordland.world.map.gen.Perlin;

/**
 *
 * @author red
 */

public class Map {
    //private static java.util.Map<Vector3,Tile> tiles = new HashMap<Vector3,Tile>(1000);


    public static final int cluster_size = 5;

    public static final int __CHUNK_SIZE = Chunk.CHUNK_SIZE;
    public static final int buff_size = cluster_size*__CHUNK_SIZE;


    public static int tile_count = 0;
    public static Tile[][][] tiles = new Tile[buff_size][buff_size][buff_size];
    private Chunk[][] chunks = new Chunk[3][3];


    public Map(){

    }

    
    public void build_cluster(int x, int y, int z){

        for (int i = x; i<x+cluster_size; i++)
        for (int j = y; j<y+cluster_size; j++)
        for (int k = z; k<z+cluster_size; k++)
        {
               build_chunk(i,j,k);
        }
    }


    public static int cluster_x = -1;
    public static int cluster_y = -1;
    public static int cluster_z = -1;

    public static Vector3 V3world2local(Vector3 world) {

        world.set(
                    world.x() - (cluster_x*Chunk.CHUNK_SIZE),
                    world.y() - (cluster_y*Chunk.CHUNK_SIZE),
                    world.z() - (cluster_z*Chunk.CHUNK_SIZE)
                );

        return world;
    }

    public void build_all(){
         long build_start = System.nanoTime();

         build_cluster(cluster_x,cluster_y,cluster_z);
         
         System.out.println("Built " + Integer.toString(tile_count)+ " tiles");
         System.out.println(
                Integer.toString(
                        (int)(
                            ( System.nanoTime() - build_start ) / (1000*1000)
                        )
                ) + " ms elasped"
        ); //in ms
    }

    static Vector3 origin = new Vector3(0,0,0);
    static Vector3 w_origin = new Vector3(0,0,0);

    public static void build_chunk(int i, int j, int k){

        float height = 0.0f;

        for (int x= i*__CHUNK_SIZE; x< (i+1)*__CHUNK_SIZE; x++)
        for (int y= j*__CHUNK_SIZE; y< (j+1)*__CHUNK_SIZE; y++)
        for (int z= k*__CHUNK_SIZE; z< (k+1)*__CHUNK_SIZE; z++)
        {

                    origin.set(x,y,z);
                    //w_origin.set(x,y,z);
                    //Vector3.util_vec3.set(V3world2local(origin));

                    //add some debug clusterisation there
                    // java.lang.Math.random() > 0.01 &&



                    height = Perlin.getHeight(x, z, 0);

                    if ( y< height ) {

                        Tile tmp_tile;
                        tmp_tile = new Tile(origin); 
                       
                        set_tile(origin, tmp_tile);

                        tile_count++;
                   }
       }

    }

    public static Vector3 util_v3 = new Vector3(0,0,0);

    public static void set_tile(Vector3 origin, Tile tile){


        Vector3 _origin = V3world2local(origin);
        tiles[_origin.x()][_origin.y()][_origin.z()] = tile;
        //util_v3.set(V3world2local(origin));
        //tiles.put(util_v3, tile);
    }





    public static Tile get_tile( int x, int y, int z) {
        

        if (util_v3.x<0 || util_v3.y<0 || util_v3.z<0)
            return null;

        if (util_v3.x>buff_size || util_v3.y>buff_size || util_v3.z>buff_size)
            return null;

        util_v3.set(x,y,z);
        util_v3 = V3world2local(util_v3);
        return tiles[util_v3.x][util_v3.y][util_v3.z];

        //return tiles.get(util_v3);

    }


    public void drop_tile( int x, int y, int z) {

        Tile tile = null;

        util_v3.set(x,y,z);
        util_v3 = V3world2local(util_v3);

        x = util_v3.x;
        y = util_v3.y;
        z = util_v3.z;

        System.out.println("clearing tile @ "+ Integer.toString(x) + "," + Integer.toString(y) + "," + Integer.toString(z));
        
        /*Tile __tyle;

        if ((__tyle = tiles.get(util_v3)) != null){
            //__tyle.tile_type = 0;
            __tyle = null;
        }*/

        if ((tiles[x][y][z]) != null){

            if (tiles[x+1][y][z] != null)
                tiles[x+1][y][z].v_reset();
            if (tiles[x-1][y][z] != null)
                tiles[x-1][y][z].v_reset();
            if (tiles[x][y+1][z] != null)
                tiles[x][y+1][z].v_reset();
            if (tiles[x][y-1][z] != null)
                tiles[x][y-1][z].v_reset();
            if (tiles[x][y][z+1] != null)
                tiles[x][y][z+1].v_reset();
            if (tiles[x][y][z-1] != null)
                tiles[x][y][z-1].v_reset();

            tiles[x][y][z] = null;
        }
      
    }

    public void add_tile(int x, int y, int z) {
        Vector3 origin = new Vector3(x,y,z);
        Tile tmp_tile = new Tile(origin);
        set_tile(origin, tmp_tile);
    }


    public static void relocate(int cx, int cy, int cz) {
        
            Map.cluster_x = cx;
            Map.cluster_y = cy;
            Map.cluster_z = cz;

    }

}
