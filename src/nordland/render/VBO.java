/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.BufferUtils;

import nordland.world.map.Tile;
import nordland.world.map.Map;
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
   
    int vboid_data = 0;
    int vboid_index = 0;

    static int totalNumberOfAxis = 3;
    static int floatSize = 4;
    static int vertexPositionAttributeSize = ((3+2) * floatSize);   //3 position coord + 2 texture coord
    int vertexIndexSize = 4;
    int totalVertecies = 4;

    ByteBuffer vertexPositionAttributes = null;
    ByteBuffer vertexTextureAttributes = null;
    ByteBuffer vertexIndecies = null;

    public int vertex_index = 0;

    public Texture texture;

    public void init(){
        
    }

    public static final  int VBO_max_buffer_size = 64*64*64 * 4 * 6 ;
    public static int       VBO_buffer_size = 0;

    public static int createVBOID() {
        return ARBVertexBufferObject.glGenBuffersARB();
    }

    //prepair VBO buffer and get a pointer to it

    public static ByteBuffer precache_bufferData(int vbo_id, int TYPE, int size) {
        ARBVertexBufferObject.glBindBufferARB(TYPE, vbo_id);
        ARBVertexBufferObject.glBufferDataARB(TYPE, size, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

        return  ARBVertexBufferObject.glMapBufferARB( TYPE, ARBVertexBufferObject.GL_WRITE_ONLY_ARB, size, null);
    }

    //--------------------------------------------------------------------------
    public void preload(){

        if (vboid_data != 0){
            ARBVertexBufferObject.glDeleteBuffersARB(vboid_data);
        }
        if (vboid_index != 0){
            ARBVertexBufferObject.glDeleteBuffersARB(vboid_index);
        }

        vboid_data  =   createVBOID();
        vboid_index = createVBOID();

        vertexPositionAttributes    = precache_bufferData( vboid_data,
                ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexPositionAttributeSize * VBO_max_buffer_size);
        if (vertexPositionAttributes == null){
            System.out.println("Failed to create vertex buffer");
            System.out.println(GL11.glGetError());
            return;
        }
        

        //----------------------------------------------------------------------
        vertexIndexSize = 4;
        vertexIndecies = precache_bufferData(vboid_index,
                ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,vertexIndexSize * VBO_max_buffer_size
                );

        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("Data/terrain.png"));
        }
        catch (IOException ex) {
        }
    }

    static Voxel voxel_render = new Voxel(0,0,0);

    public void build_chunk(int chunk_x, int chunk_y, int chunk_z) {
        
    }

    public void rebuild(){

        vertex_index = 0;
        VBO_buffer_size = 0;

        preload();

        Tile __tile = null;
        Map __map = World.getInstance().game_map;

        //1.7m iterations
        
        long vbo_build_start = System.nanoTime();
        
        for (int x=-32; x< 32; x++)
            for (int y=-32; y< 32; y++)
                for (int z=-32; z<32; z++){
                    __tile = __map.get_tile(x, y, z);
                    if (__tile != null){

                        //f k l r t b

                        if ( (__map.get_tile(x-1,y,z)) != null){
                            __tile.lv = false;
                        }
                        if ( (__map.get_tile(x,y-1,z)) != null){
                            __tile.bv = false;
                        }
                        if ( (__map.get_tile(x,y,z-1)) != null){
                            __tile.kv = false;
                        }
                        if ( (__map.get_tile(x+1,y,z)) != null){
                            __tile.rv = false;
                        }
                        if ( (__map.get_tile(x,y+1,z)) != null){
                            __tile.tv = false;
                        }
                        if ( (__map.get_tile(z,y,z+1)) != null){
                            __tile.fv = false;
                        }

                        voxel_render.set_origin(x, y, z);
                        voxel_render.tile_id = __tile.tile_type;
                        voxel_render.build_vbo(this, __tile);
                    }
        }
        

        unload();

        System.out.println("VBO buffer: " + Integer.toString(VBO_buffer_size)+ " vertex");
        System.out.println("VBO buffer: " + Integer.toString(VBO_buffer_size/24)+ " tileset");

        System.out.println(
                Integer.toString(
                    (int)(
                        ( System.nanoTime() - vbo_build_start ) / (1000*1000)
                    )
                )
        ); //in ms
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
        texture.bind();

        GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboid_data);
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboid_index);
        
        int stride = (3+2) * 4;   //3 vertex + 2 texture
        
        int offset = 0 * 4;
        GL11.glVertexPointer(3,GL11.GL_FLOAT, stride, offset);

        // 3 vertex coord * size of float
        offset = 3 * 4;
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, stride, offset);
        GL11.glDrawElements(GL11.GL_QUADS, VBO_buffer_size, GL11.GL_UNSIGNED_INT,0);

        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
	ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, 0);

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }

}
