package snake;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import static java.lang.Math.min;

public class LevelGenerator {

    private Random random = new Random();


    public Level createAndGetLevel(String fileName, int applesCount, int teleportCount)
            throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IOException,
            IllegalAccessException {
        int fieldHeight = random.nextInt(40) + 10;
        int fieldWidth = random.nextInt(40) + 10;
        String[][] field = getStringField(fieldHeight, fieldWidth);
        generateAllParameters(field, teleportCount);
        createLevelFile(fileName, field);
        return new Level(new FieldReader(fileName), applesCount, 5, 2);
    }

    public void createLevelFile(String fileName, String[][] field) throws IOException {
        File levelFile = new File(Settings.LEVEL_URL +fileName);
        FileWriter writeFile = new FileWriter(levelFile);
        StringBuilder strField = new StringBuilder();
        for (String[] line : field){
            strField.append(String.join("", line));
            strField.append('\n');
        }
        writeFile.write(strField.toString());
        writeFile.close();
    }

    private String[][] getStringField(int fieldHeight, int fieldWidth){
        String[][] field = new String[fieldHeight][fieldWidth];
        fillFieldOfStartingParameters(fieldHeight, fieldWidth, field);
        List<Cell> cells = new ArrayList<>();
        int cellSize = random.nextInt(min(fieldWidth/2, fieldHeight/2)) + 5;
        fillCells(fieldWidth, fieldHeight, cellSize, cells);
        setInternalCell(cells);
        fillField(field, cells);
        List<Integer> minSize = getMinSizeOfInternalCell(cells, fieldWidth, fieldHeight);
        makeHoles(field, minSize, fieldHeight, fieldWidth, cellSize);
        return field;
    }

    private List<Integer> getMinSizeOfInternalCell(List<Cell> cells, int fieldWidth, int fieldHeight){
        int minWidth = fieldWidth;
        int minHeight = fieldHeight;
        for(Cell cell:cells){
            minWidth = setMin(minWidth, cell.getInternalCell().width);
            minHeight = setMin(minHeight, cell.getInternalCell().height);
        }
        List<Integer> result = new ArrayList<>();
        result.add(minWidth);
        result.add(minHeight);
        return result;
    }

    private int setMin(int minValue, int internalCellWidth) {
        if(minValue > internalCellWidth){
            minValue = internalCellWidth;
        }
        return minValue;
    }

    private void fillFieldOfStartingParameters(int fieldHeight, int fieldWidth, String[][] field) {
        for(int x=0; x<fieldWidth; x++){
            for(int y=0; y<fieldHeight; y++){
                field[y][x] = ".";
            }
        }
    }

    private void fillField(String[][] field, List<Cell> cells){
        for (Cell cell : cells){
            Cell room = cell.getInternalCell();
            createHorizontalWalls(field, room);
            createVerticalWalls(field, room);
        }
    }

    private void createVerticalWalls(String[][] field, Cell room) {
        for (int y = room.getTopLeft().y; y < room.getBottomRight().y; y++){
            field[y][room.getTopLeft().x] = "#";
            field[y][room.getBottomRight().x] = "#";
        }
    }

    private void createHorizontalWalls(String[][] field, Cell room) {
        for (int x = room.getTopLeft().x; x <= room.getBottomRight().x; x++){
            field[room.getTopLeft().y][x] = "#";
            field[room.getBottomRight().y][x] = "#";
        }
    }

    private void generateAllParameters(String[][] field, int teleportCount){
        ArrayList<Vector> indexesFreeCells = getIndexesFreeCells(field);
        setParameterInFreeCell(field, indexesFreeCells, "H");

        createParameters(field, 1, "A");
        createParameters(field, 1, "J");
        createParameters(field, teleportCount, "T");
        createParameters(field, 10, "P");
    }

    private void createParameters(String[][] field, int count, String parameter) {
        ArrayList<Vector> indexesFreeCells;
        for (int i = 0; i < count; i++) {
            indexesFreeCells = getIndexesFreeCells(field);
            setParameterInFreeCell(field, indexesFreeCells, parameter);
        }
    }

    private void setParameterInFreeCell(String[][] field, ArrayList<Vector> indexesFreeCells, String parameter) {
        int snakeIndex = random.nextInt(indexesFreeCells.size());
        Vector randomFreeCell = indexesFreeCells.get(snakeIndex);
        field[randomFreeCell.y][randomFreeCell.x] = parameter;
    }

    private ArrayList<Vector> getIndexesFreeCells(String[][] field) {
        ArrayList<Vector> indexesFreeCells = new ArrayList<>();
        for(int y = 0; y < field.length; y++){
            for (int x = 0; x < field[y].length; x++){
                if(Objects.equals(field[y][x], ".")){
                    indexesFreeCells.add(new Vector(x,y));
                }
            }
        }
        return indexesFreeCells;
    }

    private void makeHoles(String[][] field, List<Integer> minsize, int fieldHeight, int fieldWidth, int cellSize){
        createHolesVertically(field, minsize, fieldWidth, fieldHeight, cellSize);
        createHolesHorizontally(field, minsize, fieldWidth, fieldHeight, cellSize);
    }

    private void createHolesHorizontally(String[][] field,
            List<Integer> minsize,
            int fieldWidth, int fieldHeight, int cellSize) {
        int min_v = minsize.get(1) - 2;
        int countOfCells = fieldHeight/cellSize;
        for (int y = 0; y < countOfCells; y++){
            int holeMark = random.nextInt(min_v) + 1 + cellSize * y;
            for (int x = 1; x < fieldWidth - 1; x++){
                field[holeMark][x] = ".";
            }
        }
    }

    private void createHolesVertically(String[][] field, List<Integer> minsize, int fieldWidth,
            int fieldHeight, int cellSize) {
        int min_v = minsize.get(0) - 2;
        int countOfCells = fieldWidth/cellSize;
        for (int x = 0; x < countOfCells; x++){
            int holeMark = random.nextInt(min_v) + 1 + cellSize * x;
            for (int y = 1; y < fieldHeight - 1; y++){
                field[y][holeMark] = ".";
            }
        }
    }

    private void fillCells(int fieldWidth, int fieldHeight, int cellSize, List<Cell> cells){
        int numberOfCellsVertically = fieldHeight / cellSize;
        int remainder = fieldHeight % cellSize;
        for (int i = 0; i < numberOfCellsVertically; i++){
            if (i == numberOfCellsVertically - 1)
                fillHorizontal(i, cellSize + remainder, cellSize, fieldWidth, cells);
            else
                fillHorizontal(i, cellSize, cellSize, fieldWidth, cells);
        }
    }

    private void fillHorizontal(int numberOfRow, int heightOfCell, int widthOfCell, int fieldWidth, List<Cell> cells) {
        int numberOfCellsHorizontally = fieldWidth / widthOfCell;
        int remainder = fieldWidth % widthOfCell;
        for (int j = 0; j < numberOfCellsHorizontally; j++){
            Vector topLeft = new Vector(j*widthOfCell, numberOfRow*widthOfCell);
            Vector bottomRight = topLeft.sum(new Vector(widthOfCell - 1, heightOfCell - 1));
            if (j == numberOfCellsHorizontally - 1)
                bottomRight = topLeft.sum(new Vector(widthOfCell + remainder - 1, heightOfCell - 1));
            cells.add(new Cell(topLeft, bottomRight));
        }
    }

    private void setInternalCell(List<Cell> cells) {
        for (Cell cell : cells) {
            if (cell.height > 4 && cell.width > 4) {
                boolean room = random.nextBoolean();
                if (room) {
                    int horizontallyShift = random.nextInt(cell.width - 4);
                    horizontallyShift += horizontallyShift == 1 ? 1 : 0;
                    int verticallyShift = random.nextInt(cell.height - 4);
                    verticallyShift += verticallyShift == 1 ? 1 : 0;
                    Vector shift = new Vector(-horizontallyShift, -verticallyShift);
                    cell.setInternalCell(new Cell(cell.getTopLeft(), cell.getBottomRight().sum(shift)));
                }
            }
            if (cell.getInternalCell() == null) {
                cell.setInternalCell(new Cell(cell.getTopLeft(), cell.getBottomRight()));
            }
        }
    }
}
