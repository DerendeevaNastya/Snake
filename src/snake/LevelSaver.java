package snake;

import java.io.IOException;

public class LevelSaver {
    private Level currentLevel;

    public LevelSaver(Level level){
        currentLevel = level;
    }

    private String[][] getStringField(){
        String[][] strField = new String[currentLevel.getLevelSize().y][currentLevel.getLevelSize().x];
        for (int x = 0; x < currentLevel.getLevelSize().x; x ++){
            for (int y = 0; y < currentLevel.getLevelSize().y; y++){
                strField[y][x] = currentLevel.getFieldObject(x, y).getSymbol();
            }
        }
        return strField;
    }

    public void saveCurrentStateInFile() throws IOException {
        String[][] strField = getStringField();
        LevelGenerator generator = new LevelGenerator();
        generator.createLevelFile(Settings.CURRENT_LEVEL_STATE, strField);
    }
}
