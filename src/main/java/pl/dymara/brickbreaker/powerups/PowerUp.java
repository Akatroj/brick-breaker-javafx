package pl.dymara.brickbreaker.powerups;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pl.dymara.brickbreaker.Brick;


public class PowerUp {

    public static final int POWERUP_DEFAULT_VELOCITY = 2;

    private static final int POWERUP_WIDTH = 15;
    private static final int POWERUP_HEIGHT = 15;


    private final int leftX;
    private final int width;
    private final int height;
    private int topY;
    private final Paint color;
    private final PowerUpType type;


    private final int velocity;
    private boolean isFalling;


    public PowerUp(Brick father, int velocity, PowerUpType type) {
        leftX = father.getMiddleX() - POWERUP_WIDTH/2;
        topY = father.getMiddleY() - POWERUP_HEIGHT/2;
        this.velocity = velocity;
        this.type = type;
        width = POWERUP_WIDTH;
        height = POWERUP_HEIGHT;

        color = PowerUpType.color(type);
    }

    public void move() {
        topY +=velocity;
    }

    public int getLeftX() {
        return leftX;
    }

    public int getTopY() {
        return topY;
    }

    public int getRightX() {
        return leftX + width;
    }

    public int getBottomY() {
        return topY + height;
    }

    public Paint getColor() {
        return color;
    }

    public PowerUpType getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
