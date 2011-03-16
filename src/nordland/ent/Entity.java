/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.ent;
import nordland.util.math.Vector3;
import nordland.world.map.Chunk;

/**
 *
 * @author Administrator
 */
public class Entity {
    public Vector3 origin = new Vector3(0,0,0);


    public void move_to(Vector3 vec){
        Vector3 origin_new = vec;

        if (!origin_new.equals(origin)){
            origin.set(vec);
            e_on_move(origin,origin_new);
        }

        
    }

    public void e_on_move(Vector3 origin_old, Vector3 origin_new){
        //TODO: move to player
        if (origin_new.x % Chunk.CHUNK_SIZE == 0){
            System.out.println("Border crossed");
        }
    }
}
