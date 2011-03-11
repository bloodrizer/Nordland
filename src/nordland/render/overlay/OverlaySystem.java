/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render.overlay;

import java.awt.Font;
//import nordland.render.overlay.TrueTypeFont;

import org.lwjgl.opengl.GL11;
//import org.lwjgl.util.glu.GLU;

/*import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;*/

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author Administrator
 */
public class OverlaySystem {
    private static final OverlaySystem INSTANCE = new OverlaySystem();

    public DebugOverlay debug = null;

    private OverlaySystem() {
         Font font = new Font("Arial", Font.BOLD, 15);
        __font = new TrueTypeFont(font, true);

        debug = new DebugOverlay();
        debug.set_font(__font);
    }


    public static OverlaySystem getInstance() {
        return INSTANCE;
    }

    static TrueTypeFont __font;

    //render whole overlay
    public void render() {

        set2DMode(0, 640, 0, 480);

        //__font.drawString(10, 5, "test", Color.white);

        debug.render();

        set3DMode();
    }

    public static void set2DMode(float x, int width, float y, int height) {

        GL11.glEnable(GL11.GL_TEXTURE_2D); // exture Mapping, needed for text
	GL11.glEnable(GL11.GL_BLEND); // Enabled blending for text
	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


    	GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        GL11.glOrtho(x, width, height, y, -1, 1);                          // Set Up An Ortho Screen
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }
    public static void set3DMode() {

        GL11.glDisable(GL11.GL_TEXTURE_2D); // exture Mapping, needed for text
	GL11.glDisable(GL11.GL_BLEND); // Enabled blending for text

    	GL11.glMatrixMode(GL11.GL_PROJECTION);                        // Select The Projection Matrix
        GL11.glPopMatrix();                                      // Restore The Old Projection Matrix
        GL11.glMatrixMode(GL11.GL_MODELVIEW);                         // Select The Modelview Matrix
        GL11.glPopMatrix();                                      // Restore The Old Projection Matrix

        Color.white.bind();
        
    }


}
