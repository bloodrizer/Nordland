/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nordland.render.overlay.console;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;



import org.lwjgl.LWJGLException;
import java.awt.Font;

import nordland.render.overlay.TrueTypeFont;
/**
 *
 * @author red
 */

public class Console {
    

    private static final Console INSTANCE = new Console();

    private Console() {
        
    }

    public static Console getInstance() {
        return INSTANCE;
    }

    public static final int DISPLAY_HEIGHT = 480;
    public static final int DISPLAY_WIDTH = 640;

    public static int CONSOLE_WIDTH  = 32;
    public static int CONSOLE_HEIGHT = 24;

    ConsoleTile[][] _console = new ConsoleTile[CONSOLE_WIDTH][CONSOLE_HEIGHT];
    static TrueTypeFont _console_font;

    public static int TILE_SIZE = 20;

    public void set_tile_foreground(int x, int y) {
        
    }

    public void create() throws LWJGLException {
        System.out.println("Y HALLO THARE");

        Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH,DISPLAY_HEIGHT));
        Display.setFullscreen(false);
        Display.setTitle("JSKiller");
        Display.create();

        Init();
    }


    public void destroy() {
        Display.destroy();
    }

    public void Init() {
        glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrtho(0.0, Display.getDisplayMode().getWidth(), 0.0, Display.getDisplayMode().getHeight(), -1.0, 1.0);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());

	    // Set up for text
	glEnable(GL_TEXTURE_2D); // exture Mapping, needed for text
	glEnable(GL_BLEND); // Enabled blending for text
	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);


        Font font = new Font("Arial", Font.BOLD, TILE_SIZE);
        _console_font = new TrueTypeFont(font, true);
        
    }

    
    public void render_all() {

        glDisable( GL_TEXTURE_2D );
	glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	glLoadIdentity();
            render_background();

        glEnable( GL_TEXTURE_2D );
            render_foreground();

       }

    public static int[] get_window_coord(int i, int j){
       int x = i*TILE_SIZE;
       int y = DISPLAY_HEIGHT -  j*TILE_SIZE;

       return new int[]{x,y};
    }

    public static void render_background(){
        glPushMatrix();
        glBegin(GL_QUADS);
        for (int i = 0; i<CONSOLE_WIDTH; i++){
            for (int j = 0; j<CONSOLE_HEIGHT; j++){

                glColor3f(1.0f,1.0f,1.0f);

		glVertex2f( i*TILE_SIZE,         DISPLAY_HEIGHT -  j*TILE_SIZE);
		glVertex2f((i+1)*TILE_SIZE-1 ,   DISPLAY_HEIGHT -  j*TILE_SIZE);
		glVertex2f((i+1)*TILE_SIZE-1,    DISPLAY_HEIGHT - ((j+1)*TILE_SIZE-1));
		glVertex2f(i*TILE_SIZE,          DISPLAY_HEIGHT - ((j+1)*TILE_SIZE-1));

            }
        }
       glEnd();
       glPopMatrix();

    }

    public static void render_foreground(){

        for (int i = 0; i<CONSOLE_WIDTH-5; i++){
            for (int j = 0; j<CONSOLE_HEIGHT-5; j++){
                
                int[] coord = get_window_coord(i,j);

                glColor3f(0.5f,0.5f,0.5f);
                _console_font.drawString(coord[0], coord[1], "x", 1.0f, 1.0f);


            }
        }

    }
}
