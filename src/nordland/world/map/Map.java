/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*<vizlim> <Лэн> Делаешь тип Pair<T1,T2>, например
<vizlim> <Лэн> Пишешь ему equals и hashCode
<vizlim> <Лэн> И оборачиваешь все обращения к HashMap2<K1,K2,V> -> HashMap<Pair<K1,K2>,V>
<vizlim> <Лэн> Минут за 15 пишется*/

package nordland.world.map;
import java.util.concurrent.ConcurrentHashMap;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author red
 */
public class Map {
    private java.util.Map<Vector3f,Integer> queryCounts = new ConcurrentHashMap<Vector3f,Integer>(1000);
}
