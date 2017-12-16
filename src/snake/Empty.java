package snake;

@ImageFileName(fileNames = "empty.jpg")
public class Empty implements IFieldObject {
    @Override
    public void intersectWithSnake(Game game) {
    }

    @Override
    public String getSymbol() {
        return ".";
    }
}
