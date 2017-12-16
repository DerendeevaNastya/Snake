package snakeGUI;

import snake.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

class FieldPanel extends JPanel {
    private int cellSize;
    private Level level;
    public Level getLevel(){return level;}
    private HashSet<FieldObjectImage> labels;
    private ImageSaver imageSaver;


    FieldPanel(Level level, int cellSize) {
        super();
        setBackground(Color.WHITE);

        this.cellSize = cellSize;
        imageSaver = new ImageSaver(cellSize);

        Vector levelSize = level.getLevelSize();
        int width = cellSize * levelSize.x;
        int height = cellSize * levelSize.y;

        setPreferredSize(new Dimension(width, height));
        setLayout(new FieldLayout(width, height));

        this.level = level;

        labels = new HashSet<>();
        updateLabels();
    }

    void updateLabels() {
        if (labels.size() > 0)
            removeLabels();

        labels = new HashSet<>();
        Vector levelSize = level.getLevelSize();
        for (int y = 0; y < levelSize.y; y++) {
            for (int x = 0; x < levelSize.x; x++) {
                FieldObjectImage label = new FieldObjectImage(
                        level.getFieldObject(x, y),
                        cellSize,
                        imageSaver);
                labels.add(label);
                add(label);
            }
        }
    }

    private void removeLabels() {
        for (FieldObjectImage label : labels) {
            label.setVisible(false);
            remove(label);
        }
    }
}
