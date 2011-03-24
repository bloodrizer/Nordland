/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland;

import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.Sys;

import nordland.world.World;
import nordland.world.map.Tile;
import nordland.render.Render;
import nordland.render.overlay.OverlaySystem;
import nordland.render.Raycast;
import nordland.render.Camera;

import nordland.ent.Entity;
import nordland.ent.EntityManager;

import java.nio.FloatBuffer;
import nordland.util.math.Vector3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
/**
 *
 * @author red
 */
public class Main {
    
  int fps;
  long lastFPS;
  long lastFrame;

    public static final Render RENDER = Render.getInstance();
    public static final Input INPUT = Input.getInstance();
    public static final World WORLD = World.getInstance();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = null;

        main = new Main();
        main.create();


        main.run();
    }

    public void create(){
        try {
            RENDER.create();
        }
        catch(Exception ex) {
          //LOGGER.log(Level.SEVERE,ex.toString(),ex);
        }
        finally {
          if(this != null) {
            this.destroy();
          }
        }
    }

    public void destroy(){
    
    }

    public float wx = 0.0f;
    public float wy = 0.0f;
    public float wz = 0.0f;


   
   
   public void run(){
      getDelta();
      lastFPS = getTime();

      Camera camera = new Camera(-16, 0, -16);

      float dx        = 0.0f;
      float dy        = 0.0f;
      float dt        = 0.0f; //length of frame
      float lastTime  = 0.0f; // when the last frame was
      float time      = 0.0f;

      float mouseSensitivity = 0.05f;
      float movementSpeed = 1.0f; //move 10 units per second

        //hide the mouse
      Mouse.setGrabbed(true);

      
      WORLD.start();


      //player generation and shit
      Entity player = new Entity();
      EntityManager.add(player);
      

      while (!Display.isCloseRequested() &&
                !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
      {

            updateFPS();
            OverlaySystem.getInstance().debug.tiles = WORLD.game_map.tile_count;
            OverlaySystem.getInstance().debug.shards = RENDER.vbo.VBO_buffer_size/24;
            OverlaySystem.getInstance().debug.max_shards = RENDER.vbo.VBO_max_buffer_size/24;


            time = getTime();
            dt = (time - lastTime)/1000.0f;
            lastTime = time;


            //distance in mouse movement from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement from the last getDY() call.
            dy = -Mouse.getDY();

            //controll camera yaw from x movement fromt the mouse
            camera.yaw(dx * mouseSensitivity);
            //controll camera pitch from y movement fromt the mouse
            camera.pitch(dy * mouseSensitivity);

           

            INPUT.poll();

            float movement_amt = 0.25f;

            if (INPUT.key_state_w){ camera.walkForward(movement_amt); }
            if (INPUT.key_state_s){ camera.walkBackwards(movement_amt); }
            if (INPUT.key_state_a){ camera.strafeLeft(movement_amt); }
            if (INPUT.key_state_d){ camera.strafeRight(movement_amt); }

            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))//move forward
            {
                camera.lift(movement_amt);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_C))//move forward
            {
                camera.dive(movement_amt);
            }

            OverlaySystem.getInstance().debug.position = camera.position;

            //set the modelview matrix back to the identity
            GL11.glLoadIdentity();
            //look through the camera before you draw anything
            camera.setMatrix();
            //you would draw your scene here.


            player.move_to(camera.get_V3position());


            RENDER.rebuild_vbo();   //check if vbo is mareked as dirty and rebuild it
            RENDER.render_world();
            RENDER.render_entity(player);
            RENDER.render_overlay();


            //render will authomaticaly raytrace and set in-game cursor
            Vector3f cursor = RENDER.cursor_position;
            if (Mouse.isButtonDown(1))
            {
                WORLD.game_map.drop_tile(
                        (int)cursor.x, (int)cursor.y-1, (int)cursor.z
                );

                Render.vbo.rebuild();
            }
            if (Mouse.isButtonDown(0))
            {
                WORLD.game_map.add_tile(
                        (int)cursor.x, (int)cursor.y-1, (int)cursor.z
                );

                Render.vbo.rebuild();
            }

          
          Display.update();
          Display.sync(60);

        }
  }

  public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }

  public int getDelta() {
    long time = getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;

    return delta;
}

  public void updateFPS() {
    if (getTime() - lastFPS > 1000) {
        //Display.setTitle("FPS: " + fps);

        OverlaySystem.getInstance().debug.fps = fps;
	fps = 0;
	lastFPS += 1000;
    }
    fps++;
  }


}
