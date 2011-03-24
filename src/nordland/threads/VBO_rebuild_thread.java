/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.threads;

import nordland.render.Render;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
/**
 *
 * @author Administrator
 */
public class VBO_rebuild_thread implements Runnable{
    @Override
    public void run() {

        try {
            Render.vbo.build_chunks_all();
            Render.vbo.vbo_invalidate = true;

            Render.vbo.vertexPositionAttributes.flip();
            Render.vbo.vertexIndecies.flip();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
