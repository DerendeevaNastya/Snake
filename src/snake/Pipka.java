package snake;

import java.util.ArrayList;
import java.util.List;

@ImageFileName(fileNames = "pipka.png")
public class Pipka implements IFieldObject {

    private Vector nextDirection;
    public Vector getNextDirection(){return nextDirection;}
    private Vector position;
    public Vector getPosition(){return position;}
    public Pipka(){
        position = null;
        nextDirection = Direction.ZERO;
    }
    public Pipka(int x, int y){
        Vector pos = new Vector(x, y);
        position = pos;
        nextDirection = Direction.ZERO;
    }

    @Override
    public void intersectWithSnake(Game game) {
        return;
    }

    @Override
    public String getSymbol() {
        return "P";
    }

    public void setNewDirection(Level level){
        List<Vector> directions = new ArrayList<>();
        directions.add(Direction.RIGHT);
        directions.add(Direction.LEFT);
        directions.add(Direction.TOP);
        directions.add(Direction.BOTTOM);
        directions.add(Direction.ZERO);
        List<Vector> directionsForChoiсe = new ArrayList<>();
        for (Vector oneDirection : directions){
            Vector newPosition = position
                    .sum(oneDirection)
                    .looping(level.getLevelSize().x, level.getLevelSize().y);;
            if (level.getFieldObject(newPosition.x, newPosition.y).getSymbol() != "#" &&
                    level.getFieldObject(newPosition.x, newPosition.y).getSymbol() != "S" &&
                    level.getFieldObject(newPosition.x, newPosition.y).getSymbol() != "T" &&
                    level.getFieldObject(newPosition.x, newPosition.y).getSymbol() != "P") {
                directionsForChoiсe.add(oneDirection);
            }
        }
        nextDirection = directionsForChoiсe.get((int) (Math.random() * directionsForChoiсe.size()));
    }

    public void setNewPosition(Level level){
        position = position.sum(nextDirection)
                .looping(level.getLevelSize().x, level.getLevelSize().y);;
        nextDirection = Direction.ZERO;
    }
}
