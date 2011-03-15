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
    public int tiles =0;
    
    public int shards =0;
    public int max_shards =0;
    
    public Vector3f    position    = null;

    public void set_font(TrueTypeFont font){
        __font = font;
    }

    public void render(){
        __font.drawString(10, 5, "FPS: " + Integer.toString( fps ), Color.white);
        __font.drawString(10, 20, "Tiles: " + Integer.toString( tiles ) + 
                " ( " + Integer.toString(shards) + " / " + Integer.toString(max_shards) + " ) ",
                Color.white);

        if (position != null) {
            __font.drawString(10, 45, "X: " + Float.toString( position.x ), Color.white);
            __font.drawString(10, 60, "Y: " + Float.toString( position.y ), Color.white);
            __font.drawString(10, 75, "Z: " + Float.toString( position.z ), Color.white);
        }

        __font.drawString(320, 240, ".");
    }

}
