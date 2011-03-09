/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland;

/**
 *
 * @author red
 */
public class Engine {
    private static final Engine INSTANCE = new Engine();

    private Engine() {

    }

    public static Engine getInstance() {
        return INSTANCE;
    }
}
