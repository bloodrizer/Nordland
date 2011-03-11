/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render.overlay;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Administrator
 */


public class DebugOverlay {
    
    static TrueTypeFont __font = null;
    
    public int fps = 0;
    public Vector3f    position    = null;

    public void set_font(TrueTypeFont font){
        __font = font;
    }

    public void render(){
        __font.drawString(10, 5, "FPS:" + Integer.toString( fps ), Color.white);

        if (position != null) {
            __font.drawString(10, 30, "X:" + Float.toString( position.x ), Color.white);
            __font.drawString(10, 45, "Y:" + Float.toString( position.y ), Color.white);
            __font.drawString(10, 60, "Z:" + Float.toString( position.z ), Color.white);
        }
    }

}
