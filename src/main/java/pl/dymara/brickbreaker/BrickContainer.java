package pl.dymara.brickbreaker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrickContainer {
    private final List<Brick> bricks;
    private static final int INITIAL_BRICK_COUNT = 64;
    private static final int BRICKS_IN_ROW = 8;
    private final int xStart;
    private final int width;
    private final int yEnd;
    private int bricksBroken;


    public BrickContainer(double canvasWidth) {
        width = (((int) canvasWidth)/BRICKS_IN_ROW) * BRICKS_IN_ROW;
        xStart = ((int) canvasWidth - width)/2;
        bricks = new ArrayList<>(BRICKS_IN_ROW);
        for (int i = 0; i< INITIAL_BRICK_COUNT; i++) {
            bricks.add(generateBrickFromIndex(i));
        }

        yEnd = (INITIAL_BRICK_COUNT/BRICKS_IN_ROW)*Brick.BRICK_HEIGHT;
    }

    private Brick generateBrickFromIndex(int i) {
        int x = xStart + ((i*Brick.BRICK_WIDTH) % width);
        int y = ((i*Brick.BRICK_WIDTH) / width) * Brick.BRICK_HEIGHT;
        return new Brick(x, y);
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public Brick checkBrickCollision(Ball ball) {
        for(int i = bricks.size()-1; i>=0; i--) {
            Brick brick = bricks.get(i);
            if (!brick.isBroken() && brick.isColliding(ball)) {
                ball.hitBrick(brick);
                bricksBroken++;
                generateNewRow();
                return brick;
            }
        }
        return null;
    }

    public int getHeight() {
        return yEnd;
    }

    public void generateNewRow() {
        if(!checkIfLastRowEmpty()) {
            return;
        }

        bricks.forEach((brick -> brick.moveBy(0, Brick.BRICK_HEIGHT)));

        for(int i = 0; i<BRICKS_IN_ROW; i++) {
            bricks.add(generateBrickFromIndex(i));
        }
    }

    private boolean checkIfLastRowEmpty() {
        List<Brick> bricksInLastRow = bricks.stream().filter(brick -> brick.getBrickBottomY()==yEnd).collect(Collectors.toList());
        for(Brick brick: bricksInLastRow) if(!brick.isBroken()) return false;

        bricks.removeAll(bricksInLastRow);

        return true;
    }
}
