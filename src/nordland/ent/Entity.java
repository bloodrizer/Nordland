/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.ent;
import nordland.render.overlay.OverlaySystem;
import nordland.util.math.Vector3;
import nordland.world.map.Chunk;
import nordland.world.map.Map;
import nordland.world.World;

/**
 *
 * @author Administrator
 */
public class Entity {
    public Vector3 origin = new Vector3(0,0,0);


    public void move_to(Vector3 vec){
        //Vector3 origin_new = vec;

        if (!vec.equals(origin)){
            e_on_move(origin,vec);
            origin.set(vec);
        }

        
    }

    public void e_on_move(Vector3 origin_old, Vector3 origin_new){
        //TODO: move to player
        /*if (origin_new.x % Chunk.CHUNK_SIZE == 0){

        }*/
        int cx = (int)(origin_new.x / Chunk.CHUNK_SIZE)-1;
        int cy = (int)(origin_new.y / Chunk.CHUNK_SIZE)-1;
        int cz = (int)(origin_new.z / Chunk.CHUNK_SIZE)-1;

        OverlaySystem.getInstance().debug.cluster_position.set(cx,cy,cz);

        if ( cx != Map.cluster_x ||
             cy != Map.cluster_y ||
             cz != Map.cluster_z
        ){
            Map.cluster_x = cx;
            Map.cluster_y = cy;
            Map.cluster_z = cz;
            World.rebuild();
        }



    }
}
