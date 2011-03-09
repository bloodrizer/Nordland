/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author red
 */
public class Voxel {
    public static final float VOXEL_SIZE = 1.0f;

    public Texture texture;

    private Vector3f origin = null;

    public Voxel(float x, float y, float z){
        origin = new Vector3f(x,y,z);

        init();
    }

    public void init(){
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("Data/Crate.png"));
        }
        catch (IOException ex) {
             Logger.getLogger(Voxel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void render(){

        GL11.glPushMatrix();    //save for the fuck's safe

        GL11.glTranslatef(origin.x,origin.y,origin.z);

        GL11.glBegin(GL11.GL_QUADS);
        texture.bind(); // or GL11.glBind(texture.getTextureID());
        


         // Front Face
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         
      
         GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Top Left Of The Texture and Quad                // Back Face
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);
         
         GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Bottom Left Of The Texture and Quad                // Top Face
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         
         GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad                // Bottom Face
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);
         
         GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad                // Right face
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);
         
         GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad                // Left Face
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glEnd();

        GL11.glPopMatrix();

    }


         /*
         // Front Face
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);


         GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Top Left Of The Texture and Quad                // Back Face
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);

         GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Bottom Left Of The Texture and Quad                // Top Face
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);

         GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad                // Bottom Face
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);

         GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad                // Right face
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f( 1.0f, -1.0f, -1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f, -1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f( 1.0f,  1.0f,  1.0f);   // Top Left Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 0.0f);

         GL11.glVertex3f( 1.0f, -1.0f,  1.0f);   // Bottom Left Of The Texture and Quad                // Left Face
         GL11.glTexCoord2f(0.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f, -1.0f);   // Bottom Left Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 0.0f);
         GL11.glVertex3f(-1.0f, -1.0f,  1.0f);   // Bottom Right Of The Texture and Quad
         GL11.glTexCoord2f(1.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f,  1.0f);   // Top Right Of The Texture and Quad
         GL11.glTexCoord2f(0.0f, 1.0f);
         GL11.glVertex3f(-1.0f,  1.0f, -1.0f);   // Top Left Of The Texture and Quad
         GL11.glEnd();*/
}
