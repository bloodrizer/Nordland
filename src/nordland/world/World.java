/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.world;

import nordland.world.map.Map;

/**
 *
 * @author red
 */
public class World {
    private static final World INSTANCE = new World();

    public static World getInstance() {
        return INSTANCE;
    }

    public static Map game_map;

    private World() {
        game_map = new Map();
        game_map.build_all();
    }

    /*public Map get_map(){
        return game_map;
    }*/
    
}
