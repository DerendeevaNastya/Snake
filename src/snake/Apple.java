package snake;

@ImageFileName(fileNames = "apple.jpg")
public class Apple implements IFieldObject {
    @Override
    public void intersectWithSnake(Game game) {
        game.getCurrentLevel().addSnakePart();
    }

    @Override
    public String getSymbol() {
        return "A";
    }
}
