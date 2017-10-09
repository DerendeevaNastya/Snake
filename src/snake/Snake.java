package snake;

public class Snake {
    public SnakePart tail;
    public SnakePart head;

    public Snake(int x, int y, Vector direction) {
        head = new SnakePart(x, y, direction, null, null);
        tail = head;
    }

    public SnakePart addPartAndReturnTail() {
        int x = tail.x + tail.direction.DELTA_X * (-1);
        int y = tail.y + tail.direction.DELTA_Y * (-1);
        SnakePart newPart = new SnakePart(x, y, tail.direction, tail, null);
        tail.child = newPart;
        tail = newPart;
        return newPart;
    }
}
