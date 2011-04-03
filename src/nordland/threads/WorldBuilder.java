/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.threads;

import nordland.render.Render;
import nordland.util.NLTimer;
import nordland.world.map.Map;

/**
 *
 * @author Administrator
 */


import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;

public class WorldBuilder implements Runnable{
    @Override
    public void run() {


        while (!Display.isCloseRequested() &&
                !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {

            boolean vbo_valid = true;

            NLTimer.push();

            for (int i = 0; i< Map.cluster_size; i++)
            for (int j = 0; j< Map.cluster_size; j++)
            for (int k = 0; k< Map.cluster_size; k++){
                
                if (Map.chunks[i][j][k].dirty){
                    Map.build_chunk(i+Map.cluster_x, j+Map.cluster_y, k+Map.cluster_z);
                    Map.chunks[i][j][k].dirty = false;

                    vbo_valid = false;
                }
            }

            

            //We can not rebuild vbo directly as it is only allowed to access it in the main thread
            if (!vbo_valid){
                NLTimer.pop("map rebuilding");
                Render.invalidate_vbo();
            }

            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            

        }

    }

}
