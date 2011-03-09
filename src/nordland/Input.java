/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nordland;

import org.lwjgl.input.Keyboard;
/**
 *
 * @author Administrator
 */
public class Input {
    private static final Input INSTANCE = new Input();

    private Input() {

    }

    public static Input getInstance() {
        return INSTANCE;
    }

    boolean key_state_w = false;
    boolean key_state_s = false;
    boolean key_state_a = false;
    boolean key_state_d = false;

    public void poll(){
        while (Keyboard.next()) {
	    if (Keyboard.getEventKeyState()) {
	        if (Keyboard.getEventKey() == Keyboard.KEY_W) {
		    key_state_w = true;
		}
	        if (Keyboard.getEventKey() == Keyboard.KEY_S) {
		    key_state_s = true;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_A) {
		    key_state_a = true;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_D) {
		    key_state_d = true;
		}
	    } else {
	        if (Keyboard.getEventKey() == Keyboard.KEY_W) {
		    key_state_w = false;
		}
	        if (Keyboard.getEventKey() == Keyboard.KEY_S) {
		    key_state_s = false;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_A) {
		    key_state_a = false;
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_D) {
		    key_state_d = false;
		}
	    }
	}
    }
}
