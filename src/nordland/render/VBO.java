/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
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
   
    int vboVertexAttributes;
    int vboVertexIndecies;
    int vboTextureAttributes;

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

    //same shit, refactored version

    public static int createVBOID() {
        IntBuffer buffer = BufferUtils.createIntBuffer(1);
        ARBVertexBufferObject.glGenBuffersARB(buffer);
        return buffer.get(0);
    }

    //prepair VBO buffer and get a pointer to it

    public static ByteBuffer precache_bufferData(int vbo_id, int TYPE, int size) {
        ARBVertexBufferObject.glBindBufferARB(TYPE, vbo_id);
        ARBVertexBufferObject.glBufferDataARB(TYPE, size, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

        return  ARBVertexBufferObject.glMapBufferARB( TYPE, ARBVertexBufferObject.GL_WRITE_ONLY_ARB,size, null);
    }

    //--------------------------------------------------------------------------
    public void preload(){
        vboVertexAttributes     = createVBOID();
        vboVertexIndecies       = createVBOID();
        vboTextureAttributes    = createVBOID();


        vertexPositionAttributes    = precache_bufferData( vboVertexAttributes,
                ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexPositionAttributeSize * VBO_max_buffer_size);
        

        //----------------------------------------------------------------------
        vertexIndexSize = 4;

        vertexIndecies = precache_bufferData(vboVertexIndecies,
                ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB,vertexIndexSize * VBO_max_buffer_size
                );

        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("Data/terrain.png"));
        }
        catch (IOException ex) {
        }
    }

    static Voxel voxel_render = new Voxel(0,0,0);

    public void rebuild(){

        vertex_index = 0;   //important!
        VBO_buffer_size = 0;

        preload();

        Tile __tile = null;
        Map __map = World.getInstance().game_map;

        //1.7m iterations
        for (int x=-64; x< 64; x++)
            for (int y=-64; y< 64; y++)
                for (int z=-64; z<64; z++){
                    __tile = __map.get_tile(x, y, z);
                    if (__tile != null){

                        if ( (__map.get_tile(x-1,y,z)) != null){
                            __tile.lv = false;
                        }
                        if ( (__map.get_tile(y-1,y,z)) != null){
                            __tile.bv = false;
                        }
                        if ( (__map.get_tile(z-1,y,z)) != null){
                            __tile.kv = false;
                        }
                        if ( (__map.get_tile(x+1,y,z)) != null){
                            __tile.rv = false;
                        }
                        if ( (__map.get_tile(y+1,y,z)) != null){
                            __tile.tv = false;
                        }
                        if ( (__map.get_tile(z+1,y,z)) != null){
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

        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vboVertexAttributes);
        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboVertexIndecies);
        
        int stride = (3+2) * 4;   //3 vertex + 2 texture
        
        int offset = 0 * 4;
        GL11.glVertexPointer(3,GL11.GL_FLOAT, stride, offset);

        // 3 vertex coord * size of float
        offset = 3 * 4;
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, stride, offset);

        ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, vboVertexIndecies);

        GL11.glDrawElements(GL11.GL_QUADS, VBO_buffer_size, GL11.GL_UNSIGNED_INT,0);

        GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
    }

}
