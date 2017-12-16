package snake;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ImageFileName(fileNames = "teleport.jpg")
public class Teleport implements IFieldObject {
    @Override
    public void intersectWithSnake(Game game) {
        game.getCurrentLevel().getTeleports().remove(this);
        Random random = new Random();
        List<Teleport> teleports = new ArrayList<>(game.getCurrentLevel().getTeleports().keySet());
        int index = random.nextInt(game.getCurrentLevel().getTeleports().size());

        Vector futureTeleportPosition = game.getCurrentLevel().getTeleports().get(teleports.get(index));
        game.getCurrentLevel().setObjectOnField(game.getCurrentLevel().getSnake().getHead().getPosition(), new Empty());

        if(game.getCurrentLevel().getSnake().getHead().getChild() != null) {
            Vector direction = futureTeleportPosition.subtract(game.getCurrentLevel().getSnake().getHead().getChild().getPosition());
            game.getCurrentLevel().getSnake().getHead().setDirection(direction);
        }

        IFieldObject oldCell = game.getCurrentLevel().getFieldObject(futureTeleportPosition.x, futureTeleportPosition.y);
        game.getCurrentLevel().getSnake().getHead().setPosition(futureTeleportPosition);
        game.getCurrentLevel().setObjectOnField(futureTeleportPosition, game.getCurrentLevel().getSnake().getHead());

        Teleport futureTeleport = (Teleport) oldCell;
        game.getCurrentLevel().getTeleports().remove(futureTeleport);
    }

    @Override
    public String getSymbol() {
        return "T";
    }
}
