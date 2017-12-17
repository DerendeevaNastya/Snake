package snakeGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import snake.FieldReader;
import snake.Game;
import snake.Level;
import snake.LevelGenerator;

public class StartGame implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
        MainSnakeWindow window = new MainSnakeWindow();
        window.startNewGame("level8.txt");
	}
}
