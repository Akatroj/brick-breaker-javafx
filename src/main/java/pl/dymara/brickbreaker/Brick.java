package pl.dymara.brickbreaker;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pl.dymara.brickbreaker.powerups.PowerUp;
import pl.dymara.brickbreaker.powerups.PowerUpType;


public class Brick {

    public static final int BRICK_WIDTH = 63;
    public static final int BRICK_HEIGHT = 32;
    public static final int POINTS_REWARD = 100;

    private int hp;
    private boolean isBroken;

    private int brickLeftX;
    private int brickRightX;
    private int brickTopY;
    private int brickBottomY;
    private final Paint color;


    public Brick(int x, int y) {
        brickLeftX = x;
        brickTopY = y;
        brickRightX = x + BRICK_WIDTH;
        brickBottomY = y + BRICK_HEIGHT;
        hp=100;
        isBroken = false;
        Paint color;
        do {
            color = Color.color(Math.random(), Math.random(), Math.random());
        }
        while (color.equals(Color.WHITE));
        this.color = color;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void decreaseHP() {
        hp = 0;
        isBroken = true;
    }

    public boolean isColliding(Ball ball) {
        return checkIfObjectInXBounds(ball.getLeftX(), ball.getRightX()) && checkIfObjectInYBounds(ball.getTopY(), ball.getBottomY());
    }

    public Paint getColor() {
        return color;
    }

    public void moveBy(int x, int y) {
        brickLeftX += x;
        brickRightX += x;
        brickTopY += y;
        brickBottomY += y;
    }

    public int getBrickLeftX() {
        return brickLeftX;
    }

    public int getBrickRightX() {
        return brickRightX;
    }

    public int getBrickTopY() {
        return brickTopY;
    }

    public int getBrickBottomY() {
        return brickBottomY;
    }

    public int getMiddleX() {
        return brickLeftX + BRICK_WIDTH/2;
    }

    public int getMiddleY() {
        return brickTopY + BRICK_HEIGHT/2;
    }

    public boolean checkIfObjectInXBounds(int xLeft, int xRight) {
        return xLeft <= brickRightX && xRight >= brickLeftX;
    }

    public boolean checkIfObjectInYBounds(int yTop, int yBottom) {
        return yTop <= brickBottomY && yBottom >= brickTopY;
    }

    public PowerUp dropPowerUp() {
        return new PowerUp(this, PowerUp.POWERUP_DEFAULT_VELOCITY, PowerUpType.getRandomPowerUpType());
    }

    @Override
    public String toString() {
        return String.format("BRICK: %d, %d, %d, %d\n", brickTopY, brickRightX, brickBottomY, brickLeftX);
    }
}
