package snake;

@ImageFileName(fileNames = "wall.jpg")
public class Wall implements IFieldObject {
    @Override
    public void intersectWithSnake(Game game) {
        if (!game.getCurrentLevel().getSnake().isJuggernaut()){
            game.isGameOver = true;
        }
    }

    @Override
    public String getSymbol() {
        return "#";
    }
}
