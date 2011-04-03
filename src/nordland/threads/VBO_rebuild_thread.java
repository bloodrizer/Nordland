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
            Render.vbo.get_vpa().flip();
            Render.vbo.get_vi().flip();
            
            Render.vbo.vbo_invalidate = true;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
