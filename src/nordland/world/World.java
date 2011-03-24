/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.world;

import nordland.world.map.Map;
import nordland.render.Render;

/**
 *
 * @author red
 */
public class World {

    static World _instance;

    public static World getInstance() {
        if (_instance == null) {
            _instance = new World();
        } return _instance;
    }

    public static Map game_map = new Map();

    public static void start(){
        
        game_map.world_builder.start();
        //Render.vbo.rebuild();
        
        //rebuild();
    }
    
    public static void rebuild()
    {
        game_map.rebuild();
    }

}
