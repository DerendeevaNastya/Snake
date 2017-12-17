package snake;


import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Level[] levels;
    private int currentLevelIndex;
    private Vector playerDirection;
    public boolean isGameOver;
    public boolean isWin;

    public void makeTurn() throws TurnException {
        if(isGameOver) {
            throw new TurnException();
        }

        for (Pipka pipka : getCurrentLevel().getPipkas()){
            pipka.setNewDirection(getCurrentLevel());
        }

        IFieldObject oldCell = getCurrentLevel()
                .moveSnakeAndReturnOldCell(playerDirection);
        oldCell.intersectWithSnake(this);
        List<Pipka> pipkas = new ArrayList<>();
        for (Pipka pipka : getCurrentLevel().getPipkas()){
            if (!pipka.getPosition().equals(oldCell))
                pipkas.add(pipka);
        }
        getCurrentLevel().setPipkas(pipkas);
        getCurrentLevel().movePipkas();


        if(isGameOver) {
            return;
        }
        if (getCurrentLevel().getTeleportGenerator().isNeedToAdd(oldCell)) {
            getCurrentLevel().getTeleportGenerator().generate(getCurrentLevel());
        }

        if (getCurrentLevel().getJuggernautGenerator().isNeedToAdd()) {
            getCurrentLevel().getJuggernautGenerator().generate(getCurrentLevel());
        }

        boolean appleOnField = false;
        for (int x = 0; x < this.getCurrentLevel().getLevelSize().x; x++) {
            for (int y = 0; y < getCurrentLevel().getLevelSize().y; y++) {
                if (getCurrentLevel().getFieldObject(x, y).getSymbol() == "A") {
                    appleOnField = true;
                }
            }
        }
        if (!appleOnField) {
            getCurrentLevel().appleGenerator.generate(getCurrentLevel());
            return;
        }

        if (!getCurrentLevel().appleGenerator.isNeedToAdd(oldCell)) {
            return;
        }

        if (getCurrentLevel().isOver()) {
            if(isLevelLast()) {
                isWin = true;
                isGameOver = true;
                return;
            }
            currentLevelIndex++;
        }
        getCurrentLevel().appleGenerator.generate(getCurrentLevel());
    }

    public Game(Level[] levels) {
        this.levels = levels;
        currentLevelIndex = 0;
        isGameOver = false;
        isWin = false;
    }

    public void setPlayerDirection(Vector playerDirection) {
        this.playerDirection = playerDirection;
    }

    public Level getCurrentLevel() {
        return levels[currentLevelIndex];
    }

    private boolean isLevelLast() {
        return currentLevelIndex == levels.length - 1;
    }
}
