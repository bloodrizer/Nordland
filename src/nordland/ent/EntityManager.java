/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland.ent;

import nordland.ent.Entity;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;


/**
 *
 * @author Administrator
 */
public class EntityManager {

    

    static Collection<Entity> ent_list = new ArrayList<Entity>();
    public static Collection ent_list_sync = Collections.synchronizedCollection(ent_list);

    public static void add(Entity ent){
        ent_list_sync.add(ent);
    }

    /*private static final EntityManager INSTANCE = new EntityManager();
    private EntityManager() {

    }

    public static synchronized EntityManager getInstance() {
        return INSTANCE;
    }*/

}