package snakeGUI;

import javax.swing.*;

import java.awt.*;

public class MainMenuWindow extends JFrame{


	MainMenuWindow() {
        super("Menu");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2,1));
        
        JButton startButton = new JButton("New Game");
        JButton loadButton = new JButton("Load Game");

        mainPanel.add(loadButton);
        mainPanel.add(startButton);

        startButton.addActionListener(new StartGame());
        loadButton.addActionListener(new LoadGame());

        mainPanel.add(startButton);
        mainPanel.add(loadButton);
        
        setContentPane(mainPanel);
        setSize(new Dimension(500, 500));
        setResizable(false);
        setVisible(true);
    } 


}


