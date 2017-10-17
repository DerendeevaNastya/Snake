package snakeGUI;

import snake.Direction;
import snake.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;

public class ArrowKeysListener implements KeyListener {
    private MainSnakeWindow parent;
    private Hashtable<Integer, Vector> keysCode;

    ArrowKeysListener(MainSnakeWindow parent) {
        super();
        this.parent = parent;

        keysCode = new Hashtable<>();
        keysCode.put(37, Direction.LEFT);
        keysCode.put(40, Direction.BOTTOM);
        keysCode.put(39, Direction.RIGHT);
        keysCode.put(38, Direction.TOP);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Integer keyCode = e.getKeyCode();

        if (keysCode.containsKey(keyCode))
            parent.playerDirection = keysCode.get(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}
