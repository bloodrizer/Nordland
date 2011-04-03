/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.world.map.gen;

/**
 *
 * @author Administrator
 */



public class Heightmap {

   static int HMAP_SIZE = 1024;

   public static int[][] heightmap = new int[HMAP_SIZE][HMAP_SIZE];

   public static boolean dirty = true;
   public static int seed = 0;

   public static float get_height(int x, int z){

       x = world2local(x);
       z = world2local(z);

       update_heightmap();

       //return Perlin.getHeight(x, z, z);
       return heightmap[x][z];
   }

   public static int world2local(int i){

       if (i < 0){
           i = (HMAP_SIZE-i) % HMAP_SIZE;
       }else{
           i = i % HMAP_SIZE;
       }

       return i;
   }


   public static void update_heightmap(){
       if (dirty){
           for(int i =0; i< HMAP_SIZE; i++)
            for(int j =0; j< HMAP_SIZE; j++){
                heightmap[i][j] = (int)Perlin.getHeight(i, j, seed);
            }
       }

       dirty = false;
   }
}
