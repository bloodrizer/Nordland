/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.ARBVertexBufferObject;

import nordland.world.map.Tile;
import nordland.world.World;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import java.io.FileInputStream;
import java.io.IOException;
/**
 *
 * @author Red
 */
public class VBO {
   
    int vboVertexAttributes;
    int vboVertexIndecies;

    int totalNumberOfAxis = 3;
    int floatSize = 4;
    int vertexPositionAttributeSize = (totalNumberOfAxis * floatSize);
    int vertexIndexSize = 4;
    int totalVertecies = 4;

    ByteBuffer vertexPositionAttributes = null;
    ByteBuffer vertexIndecies = null;

    public int vertex_index = 0;

    public void init(){
        
    }

    //same shit, refactored version
    
    //--------------------------------------------------------------------------
    public void preload(){
        vboVertexAttributes = ARBVertexBufferObject.glGenBuffersARB();
        vboVertexIndecies = ARBVertexBufferObject.glGenBuffersARB();

        //select vbo
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,vboVertexAttributes);
        //set size
        ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,(vertexPositionAttributeSize * totalVertecies),
          ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

        vertexPositionAttributes = ARBVertexBufferObject.glMapBufferARB(
            ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, ARBVertexBufferObject.GL_WRITE_ONLY_ARB,(vertexPositionAttributeSize * totalVertecies),
            null);
        
        //----------------------------------------------------------------------
        vertexIndexSize = 4;

        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,    vboVertexIndecies   );

        ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,    (vertexIndexSize * totalVertecies),
          ARBVertexBufferObject.GL_STATIC_DRAW_ARB
        );

        vertexIndecies = ARBVertexBufferObject.glMapBufferARB(
          ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,ARBVertexBufferObject.GL_WRITE_ONLY_ARB, (vertexIndexSize * totalVertecies),
          null);
    }

    static Voxel voxel_render = new Voxel(0,0,0);

    public void rebuild(){


        totalVertecies = 30*30*30 * 4 * 6;  //x*y*z* 4 per side * 6 sidex
        preload();

        Tile __tile = null;
        

        //1.7m iterations
        for (int x=-30; x< 30; x++)
            for (int y=-30; y< 30; y++)
                for (int z=-30; z<30; z++){
                    __tile = World.getInstance().game_map.get_tile(x, y, z);
                    if (__tile != null){

                        voxel_render.set_origin(x, y, z);
                        //voxel_render.tile_id = __tile.tile_type;
                        voxel_render.build_vbo(this);
                    }
        }
        

        /*voxel_render.build_vbo(this);
        voxel_render.set_origin(1, 0, 0);
        voxel_render.build_vbo(this);*/

        unload();
    }


    public void unload(){

        vertexPositionAttributes.flip();

        ARBVertexBufferObject.glUnmapBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB);
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);

        vertexIndecies.flip();

        ARBVertexBufferObject.glUnmapBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB);
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, 0);
    }


    public void render(){
        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        ARBVertexBufferObject.glBindBufferARB(
            ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboVertexAttributes);

        ARBVertexBufferObject.glBindBufferARB(
            ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboVertexIndecies);

        // Get the vertex position data
        GL11.glVertexPointer(totalNumberOfAxis,GL11.GL_FLOAT,vertexPositionAttributeSize,0);

        // Draw the quad using the vertex indexes
        GL11.glDrawElements(GL11.GL_QUADS,totalVertecies,GL11.GL_UNSIGNED_INT,0);


        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,0);
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,0);

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
    }

}
