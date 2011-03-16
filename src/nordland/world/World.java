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

    public void rebuild()
    {
        game_map.build_all();
        Render.vbo.rebuild();
    }

}
