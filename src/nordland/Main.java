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
import nordland.render.Render;
import nordland.render.Camera;

import org.lwjgl.opengl.GL11;
/**
 *
 * @author red
 */
public class Main {
    
  int fps;
  int lastFPS;
  long lastFrame;

    public static final Render RENDER = Render.getInstance();
    public static final World WORLD = World.getInstance();
    public static final Input INPUT = Input.getInstance();

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


   public void run(){
      getDelta();
      lastFPS = (int)Sys.getTime();

      Camera camera = new Camera(0, 0, 0);

      float dx        = 0.0f;
      float dy        = 0.0f;
      float dt        = 0.0f; //length of frame
      float lastTime  = 0.0f; // when the last frame was
      float time      = 0.0f;

      float mouseSensitivity = 0.05f;
      float movementSpeed = 1.0f; //move 10 units per second

        //hide the mouse
      Mouse.setGrabbed(true);

      while (!Display.isCloseRequested() &&
                !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
      {

            updateFPS();



            time = Sys.getTime();
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


            //when passing in the distrance to move
            //we times the movementSpeed with dt this is a time scale
            //so if its a slow frame u move more then a fast frame
            //so on a slow computer you move just as fast as on a fast computer
            //Keyboard.poll();

            INPUT.poll();

            float movement_amt = 0.25f;

            if (INPUT.key_state_w){ camera.walkForward(movement_amt); }
            if (INPUT.key_state_s){ camera.walkBackwards(movement_amt); }
            if (INPUT.key_state_a){ camera.strafeLeft(movement_amt); }
            if (INPUT.key_state_d){ camera.strafeRight(movement_amt); }

            /*if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
            {
                camera.walkForward(movementSpeed*dt);
            }*/

            //set the modelview matrix back to the identity
            GL11.glLoadIdentity();
            //look through the camera before you draw anything
            camera.setMatrix();
            //you would draw your scene here.

            RENDER.render_all();
          
          Display.update();
          //Display.sync(60);

        }
  }

  public int getDelta() {
    long time = Sys.getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;

    return delta;
}

  public void updateFPS() {
    if (Sys.getTime() - lastFPS > 1000) {
        Display.setTitle("FPS: " + fps);
	fps = 0;
	lastFPS += 1000;
    }
    fps++;
  }


}
