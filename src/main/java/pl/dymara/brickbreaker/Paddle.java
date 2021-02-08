package pl.dymara.brickbreaker;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pl.dymara.brickbreaker.powerups.PowerUp;

public class Paddle {

    public static final int PADDLE_DEFAULT_HEIGHT = 10;
    private static final int PADDLE_DEFAULT_WIDTH = 80;
    private static final int PADDLE_DEFAULT_VELOCITY = 6;
    private static final Paint PADDLE_COLOR = Color.BLACK;
    private static final double WIDTH_MULTIPLIER = 1.1;
    private static final double VELOCITY_MULTIPLIER = 1.5;

    private static final int PADDLE_MAX_WIDTH = 2*PADDLE_DEFAULT_WIDTH;
    private static final int PADDLE_MAX_VELOCITY = (int) (0.75*PADDLE_MAX_WIDTH);

    private int maxX;
    private int width;
    private final int height;
    private int velocity;
    private int x;
    private final int y;
    private final int gameWidth;
    private final Paint color;
    private int widthLevel;
    private int speedLevel;


    public Paddle(int gameWidth, int gameHeight) {
        width = PADDLE_DEFAULT_WIDTH;
        height = PADDLE_DEFAULT_HEIGHT;
        velocity = PADDLE_DEFAULT_VELOCITY;

        this.gameWidth = gameWidth;
        y = gameHeight - height;
        maxX = gameWidth - width;
        color = PADDLE_COLOR;

        center();
    }

    public void moveLeft() {
        x = Math.max(x-velocity, 0);
    }

    public void moveRight() {
        x = Math.min(x+velocity, maxX);
    }

    public boolean checkIfBallOnPaddle(Ball ball) {
        return checkIfOnPaddleY(ball.getTopY(), ball.getBottomY()) && checkIfOnPaddleX(ball.getLeftX(), ball.getRightX());
    }

    public boolean checkIfPowerUpOnPaddle(PowerUp powerUp) {
        return checkIfOnPaddleY(powerUp.getTopY(), powerUp.getBottomY()) && checkIfOnPaddleX(powerUp.getLeftX(), powerUp.getRightX());
    }

    public void center() {
        x = (gameWidth - width)/2;
    }

    public void levelUpWidth() {
        int newWidth = (int) (PADDLE_DEFAULT_WIDTH*Math.pow(WIDTH_MULTIPLIER, widthLevel+1));
        if(newWidth<=PADDLE_MAX_WIDTH) {
            widthLevel++;
            setWidth(newWidth);
        }
    }

    public void levelUpSpeed() {
        int newSpeed = (int) (PADDLE_DEFAULT_VELOCITY*Math.pow(VELOCITY_MULTIPLIER, speedLevel+1));
        if (newSpeed<=PADDLE_MAX_VELOCITY) {
            speedLevel++;
            setVelocity(newSpeed);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Paint getColor() {
        return color;
    }

    public int getWidthLevel() {
        return widthLevel;
    }

    public int getSpeedLevel() {
        return speedLevel;
    }

    public int getVelocity() {
        return velocity;
    }


    private boolean checkIfOnPaddleX(int xLeft, int xRight) {
        return xLeft <= this.x + width && xRight >= this.x;
    }

    private boolean checkIfOnPaddleY(int yTop, int yBottom) {
        return yBottom >= this.y && yTop <=this.y+height;
    }

    private void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    private void setWidth(int width) {
        int widthDiff = width - this.width;
        this.width = width;
        x = Math.max(0, x-widthDiff/2);
        maxX -= widthDiff;
    }
}
