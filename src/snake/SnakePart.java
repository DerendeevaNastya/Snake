package snake;

import snakeGUI.TypeOfElement;

@ImageFileName(type = TypeOfElement.CHAIN,
        fileNames = {"snakepart-left.jpg", "snakepart-right.jpg"})
public class SnakePart extends IListedFieldObject {
    private Vector direction;
    private SnakePart parent;
    private SnakePart child;
    private Vector position;

    public SnakePart(
            int x,
            int y,
            Vector direction,
            SnakePart parent,
            SnakePart child) {
        setPosition(x, y);
        this.direction = direction;
        this.parent = parent;
        this.child = child;
    }

    @Override
    public void intersectWithSnake(Game game) {
        game.isGameOver = true;
    }

    @Override
    public String getSymbol() {
        return "S";
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position = new Vector(x, y);
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public SnakePart getParent() {
        return parent;
    }

    public void setParent(SnakePart parent) {
        this.parent = parent;
    }

    public SnakePart getChild() {
        return child;
    }

    public void setChild(SnakePart child) {
        this.child = child;
    }
}
