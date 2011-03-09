/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;


import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.lwjgl.LWJGLException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import nordland.render.Voxel;
import nordland.util.math.Vector3;
import nordland.world.map.Tile;
import nordland.world.World;

/**
 *
 * @author red
 */
public class Render {
    private static final Render INSTANCE = new Render();

    public static final int DISPLAY_HEIGHT = 480;
    public static final int DISPLAY_WIDTH = 640;

    public Voxel voxel_render;

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

        initGL();

        voxel_render = new Voxel(0.0f, 0.0f, 0.0f);
        voxel_render.init();
    }

    public void initGL(){

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
//        Calculate the aspect ratio of the window
        GLU.gluPerspective(45.0f,((float)DISPLAY_WIDTH)/((float)DISPLAY_HEIGHT),0.1f,100.0f);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_TEXTURE_2D);                                    // Enable Texture Mapping ( NEW )
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);

    }

    //==========================================================================

    public void render_all() {
        
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);

        //GL11.glLoadIdentity();

        GL11.glTranslatef(0.0f,-4.0f,-10.0f);                              // Move Into The Screen 5 Units
        //GL11.glRotatef(26,1.0f,0.0f,0.0f);                        // Rotate On The X Axis
        //GL11.glRotatef(26,0.0f,1.0f,0.0f);                        // Rotate On The Y Axis
        //GL11.glRotatef(26,0.0f,0.0f,1.0f);                        // Rotate On The Z Axis

        //int i = tiles.get(new Vector3(0,0,0));

        Tile __tile = null;

        for (int x=-60; x< 60; x++)
            for (int y=-60; y< 60; y++)
                for (int z=-60; z<60; z++){


                __tile = World.getInstance().game_map.get_tile(x, y, z);

                if (__tile != null){

                    voxel_render.tile_id = __tile.tile_type;

                    voxel_render.set_origin(x,y,z);
                    voxel_render.render();

                }

            }

        
        //testvoxel.render();
    }
}
