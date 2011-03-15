/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.world.map;
import nordland.util.math.Vector3;

/**
 *
 * @author red
 */
public class Tile {

    public Vector3 position;
    public int tile_type = 1;


    //public boolean[] visible_sides = new boolean[6];    //which side is visible and which is not
    //*left, *right, *top, *bottom, *front, bac*k
    public boolean lv = true;
    public boolean rv = true;
    public boolean tv = true;
    public boolean bv = true;
    public boolean fv = true;
    public boolean kv = true;

    //reset visibility to FULL visble
    public void v_reset(){
        lv = true;
        rv = true;
        tv = true;
        bv = true;
        fv = true;
        kv = true;
    }

    public Tile(Vector3 position){
        this.position = position;

        if (position.x() % Chunk.CHUNK_SIZE == 0 || position.z() % Chunk.CHUNK_SIZE == 0){
            tile_type = 0;
        }
    }

}
