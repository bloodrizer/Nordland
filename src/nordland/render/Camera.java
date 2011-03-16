/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import nordland.util.math.Vector3;

/**
 *
 * @author red
 */
public class Camera {
    public Vector3f    position    = null;
    private float       yaw         = 120.0f;
    private float       pitch       = 0.0f;


    public Camera(float x, float y, float z)
    {
        position = new Vector3f(x, y, z);
    }

    public Vector3 get_V3position(){
        
        return Vector3.util_vec3.set(position);
    }


    public void yaw(float amount)
    {
        yaw += amount;
    }

    public void pitch(float amount)
    {
        pitch += amount;
    }


    //basic camera translation goes there

    public void walkForward(float distance)
    {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw));
    }

    public void walkBackwards(float distance)
    {
        position.x += distance * (float)Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float)Math.cos(Math.toRadians(yaw));
    }

    public void strafeLeft(float distance)
    {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw-90));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw-90));
    }

    public void strafeRight(float distance)
    {
        position.x -= distance * (float)Math.sin(Math.toRadians(yaw+90));
        position.z += distance * (float)Math.cos(Math.toRadians(yaw+90));
    }

    public void lift(float distance)
    {
        position.y -= distance;
    }

    public void dive(float distance)
    {
        position.y += distance;
    }

    //translates and rotate the matrix so that it looks through the camera
    //this dose basic what setMatrix() does
    public void setMatrix()
    {
        //roatate the pitch around the X axis
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //roatate the yaw around the Y axis
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        GL11.glTranslatef(position.x, position.y, position.z);
    }

}
