/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.render;

import nordland.world.World;
import nordland.world.map.Map;

import nordland.render.VBO;



/**
 *
 * @author Administrator
 */
public class MapRender {
    private VBO[][][] vbo_buffer = new VBO[5][5][5];


    public void init(){
        //todo: create VBO there
    }

    public void rebuild_vbo(){

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++) {

                    //todo: test it

                    VBO vbo = new VBO();
                    vbo.init();
                    //vbo.build_chunk(i,j,k);
                    
                    vbo_buffer[i][j][k] = vbo;
                }

        /*VBO vbo = new VBO();
        vbo.init();
        vbo.build_chunk(0,0,0);



        vbo_buffer[0][0][0] = vbo;*/
    }

    public void render(){
       /*for (int i = 0; i < 5; i++)
            for (int j = 0; j < 4; j++)
                for (int k = 0; k < 5; k++) {
                    vbo_buffer[i][j][k].render();
                }*/
                for (int i = 0; i < 2; i++)
                    for (int k = 0; k < 2; k++)
                    {
                        vbo_buffer[i][0][k].render();
                    }
        
    }


}