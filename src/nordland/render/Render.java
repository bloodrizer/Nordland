/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.lwjgl.LWJGLException;
/**
 *
 * @author red
 */
public class Render {
    private static final Render INSTANCE = new Render();

    public static final int DISPLAY_HEIGHT = 480;
    public static final int DISPLAY_WIDTH = 640;

    private Render() {

    }

    public static Render getInstance() {
        return INSTANCE;
    }

    public void create() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH,DISPLAY_HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle("JSKiller");
        Display.create();
    }

    //==========================================================================

    public void render_all() {
        
    }
}
