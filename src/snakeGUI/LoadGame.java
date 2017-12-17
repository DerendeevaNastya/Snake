package snakeGUI;

import snake.*;
import snake.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class LoadGame implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		MainSnakeWindow win = new MainSnakeWindow();
		win.startNewGame(Settings.CURRENT_LEVEL_STATE);

	}

}
