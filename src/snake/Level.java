package snake;

import java.io.IOException;
import java.util.HashMap;

import static java.lang.Math.abs;

public class Level {
    private IFieldObject[][] field;
    private Snake snake;
    private HashMap<Teleport, Vector> teleports;
    public AppleGenerator appleGenerator;
    private JuggernautGenerator juggernautGenerator;
    private TeleportGenerator teleportGenerator;

    public Level(
            int width,
            int height,
            int applesCount,
            int juggernautCount,
            int teleportsPairCount,
            Vector snakePosition,
            Vector snakeDirection) {
        field = new IFieldObject[height][width];
        appleGenerator = new AppleGenerator(applesCount);
        juggernautGenerator = new JuggernautGenerator(juggernautCount);
        teleportGenerator = new TeleportGenerator(teleportsPairCount);
        snake = new Snake(snakePosition.x, snakePosition.y, snakeDirection);
        field[snakePosition.y][snakePosition.x] = snake.getHead();
    }

    public Level(FieldReader reader, int applesCount, int juggernautCount, int teleportsPairCount) {
        field = reader.getField();
        snake = reader.getSnake();
        teleports = reader.getTeleports();
        appleGenerator = new AppleGenerator(applesCount);
        juggernautGenerator = new JuggernautGenerator(juggernautCount);
        teleportGenerator = new TeleportGenerator(teleportsPairCount);
    }

    public HashMap<Teleport, Vector> getTeleports() {
        return teleports;
    }

    public IFieldObject moveSnakeAndReturnOldCell(Vector snakeDirection) {
        SnakePart currentSnakePart = snake.getHead().getChild();
        Vector parentDirection = snake.getHead().getDirection();
        Empty emptyObj = new Empty();

        while (currentSnakePart != null) {
            Vector nextPosition= getCoordinates(
                    currentSnakePart.getPosition(),
                    parentDirection);

            Vector tmp = currentSnakePart.getDirection();
            field[currentSnakePart.getY()][currentSnakePart.getX()] = emptyObj;
            currentSnakePart.setDirection(parentDirection);
            currentSnakePart.setPosition(nextPosition);
            field[nextPosition.y][nextPosition.x] = currentSnakePart;

            parentDirection = tmp;
            currentSnakePart = currentSnakePart.getChild();
        }

        return moveSnakeHeadAndReturnOldCell(snakeDirection);
    }

    private IFieldObject moveSnakeHeadAndReturnOldCell(Vector snakeDirection) {
        Vector direction;
        Vector nextPosition;
        if ((snakeDirection == null ||
                snake.getHead().getDirection().isOpposite(snakeDirection)) &&
                checkIsInstanceOfDirection(snakeDirection))
            direction = snake.getHead().getDirection();
        else
            direction = snakeDirection;

        nextPosition = getCoordinates(
                snake.getHead().getPosition(),
                direction);

        IFieldObject oldCell = field[nextPosition.y][nextPosition.x];
        if (snake.getHead().getChild() == null)
            field[snake.getHead().getY()][snake.getHead().getX()] = new Empty();
        snake.getHead().setPosition(nextPosition);
        snake.getHead().setDirection(direction);
        field[nextPosition.y][nextPosition.x] = snake.getHead();

        return oldCell;
    }

    private boolean checkIsInstanceOfDirection(Vector snakeDirection) {
        return (snake.getHead().getDirection().x == 0 || abs(snake.getHead().getDirection().x) == 1) &&
                (snake.getHead().getDirection().y == 0 || abs(snake.getHead().getDirection().y) == 1) &&
                (snakeDirection.x == 0 || abs(snakeDirection.x) == 1) &&
                (snakeDirection.y == 0 || abs(snakeDirection.y) == 1);
    }

    public void addSnakePart() {
        SnakePart tail = snake.addPartAndReturnTail(getLevelSize());
        field[tail.getY()][tail.getX()] = tail;
    }

    public JuggernautGenerator getJuggernautGenerator() {
        return juggernautGenerator;
    }

    public boolean isOver(){
        return appleGenerator.getApplesCount() == 0;
    }

    private Vector getCoordinates(Vector position, Vector direction) {
        Vector sumVector = position.sum(direction);
        return new Vector(
                (sumVector.x + field[0].length) % field[0].length,
                (sumVector.y + field.length) % field.length
        );
    }

    public IFieldObject getFieldObject(int x, int y) {
        return field[y][x];
    }

    public void setObjectOnField(Vector coordinates, IFieldObject object) {
        field[coordinates.y][coordinates.x] = object;
    }

    public void setObjectOnField(int x, int y, IFieldObject object) {
        setObjectOnField(new Vector(x, y), object);
    }

    public Vector getLevelSize() {
        return new Vector(field[0].length, field.length);
    }

    public Snake getSnake() {
        return snake;
    }

    public TeleportGenerator getTeleportGenerator() {
        return teleportGenerator;
    }
}
