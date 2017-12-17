package snakeGUI;

import snake.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

class MainSnakeWindow extends JFrame
{
    private int sizeWidth;
    private int sizeHeight;
    private int cellSize;
    private Timer timer;
    Vector playerDirection;

    private FieldPanel fieldPanel;
    private ScorePanel scorePanel;

    public FieldPanel getFieldPanel(){return fieldPanel;}

    private Game createGame(String fileName) throws
            NoSuchMethodException, InstantiationException,
            IllegalAccessException, IOException,
            InvocationTargetException {
        Level[] levels = new Level[1];
        LevelGenerator levelGenerator = new LevelGenerator();
        Level level = levelGenerator.createAndGetLevel(fileName, 5, 2);
        Level newLevel = null;
        try {
            newLevel = new Level(new FieldReader(fileName), 4, 5, 2);
        } catch (IllegalAccessException | InstantiationException
                | NoSuchMethodException | InvocationTargetException
                | IOException e1) {

            e1.printStackTrace();
        }
        levels[0] = newLevel;
        Game game = new Game(levels);
        return game;
    }

    public void startNewGame(String fileName){
        Game game = null;
        try {
            game = createGame(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        setWindowSizeConstants(game);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setSize(sizeWidth, sizeHeight);

        scorePanel = new ScorePanel(
                "Уровень 1", 0, sizeWidth, 30);
        fieldPanel = new FieldPanel(game.getCurrentLevel(), cellSize);

        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(fieldPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        setStartSettings(game);
    }

    MainSnakeWindow(){
        super("Snake");
    }


    private void setStartSettings(Game game) {
        addKeyListener(new ArrowKeysListener(this));

        timer = new Timer(Settings.FREQUENCY, timerTick(game));
        timer.start();

        setSize(sizeWidth, sizeHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setResizable(false);
        setVisible(true);
    }

    private ActionListener timerTick(Game game) {
        return e -> {
            if (playerDirection == null)
                return;

            game.setPlayerDirection(playerDirection);
            try {
                game.makeTurn();
                game.getCurrentLevel().getSnake().changeJuggernautTime(Settings.FREQUENCY);
                game.getCurrentLevel().getJuggernautGenerator().changeTimer(Settings.FREQUENCY);
            } catch (TurnException exception) {
                exception.printStackTrace();
            }

            if (game.isGameOver) {
                if (game.isWin)
                    fieldPanel.updateLabels();
                endGame(game.isWin);
                return;
            }

            fieldPanel.updateLabels();

        };
    }

    private void setWindowSizeConstants(Game game) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxWidth = (int) (screenSize.width * 0.9);
        int maxHeight = (int) (screenSize.height * 0.9);

        Vector levelSize = game.getCurrentLevel().getLevelSize();
        cellSize = calculateCellSize(maxWidth, maxHeight, levelSize.x, levelSize.y);

        sizeWidth = cellSize * levelSize.x + 17;
        sizeHeight = cellSize * levelSize.y + 70;
    }

    private int calculateCellSize(
            int width, int height, int fieldWidth, int fieldHeight) {
        return Math.min(width / fieldWidth, height / fieldHeight);
    }

    private void endGame(boolean isWin) {
        timer.stop();
        String isWinMessage = isWin
                ? "Поздравляем! Вы победили!"
                : "Упс! Кажется, вы проиграли";
        JOptionPane.showMessageDialog(this,
                isWinMessage,
                "End of the game", JOptionPane.INFORMATION_MESSAGE);
    }
}
