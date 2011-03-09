/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland;

import org.lwjgl.opengl.Display;
import org.lwjgl.Sys;

import nordland.render.Render;
/**
 *
 * @author red
 */
public class Main {
    
  int fps;
  int lastFPS;
  long lastFrame;

    public static final Render RENDER = Render.getInstance();

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
      while(!Display.isCloseRequested()) {

          updateFPS();
          RENDER.render_all();

          
          Display.update();
          Display.sync(60);

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
