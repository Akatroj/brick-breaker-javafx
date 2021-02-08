package pl.dymara.brickbreaker;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.concurrent.ThreadLocalRandom;

public class Ball {

    private static final int BALL_SIZE = 16;
    private static final Paint BALL_COLOR = Color.RED;
    private static final int DEFAULT_VELOCITY = -4;
    private static final int MAX_VELOCITY = 8;

    private final int size;
    private final Paint color;
    private int velocityX;
    private int velocityY;
    private int leftX;
    private int topY;

    public Ball(Paddle paddle) {
        color = BALL_COLOR;
        size = BALL_SIZE;

        int velocityBonus = ThreadLocalRandom.current().nextInt(2) - 1;

        velocityX = DEFAULT_VELOCITY + velocityBonus;
        velocityY = DEFAULT_VELOCITY + velocityBonus;

        centerBallOnPaddle(paddle);
    }

    public void centerBallOnPaddle(Paddle paddle) {
        setLeftX(paddle.getX() + paddle.getWidth() / 2 - size / 2);
        setBottomY(paddle.getY());
    }

    public void move() {
        setLeftX(leftX + velocityX);
        setTopY(topY + velocityY);
    }

    public void bounceX() {
        velocityX *= -1;
    }

    public void bounceY() {
        velocityY *= -1;
    }

    public void hitBrick(Brick brick) {

        int brickLeftX = brick.getBrickLeftX();
        int brickRightX = brick.getBrickRightX();
        int brickTopY = brick.getBrickTopY();
        int brickBottomY = brick.getBrickBottomY();

        int distanceFromBrickLeftX = Math.abs(brickLeftX - getRightX());
        int distanceFromBrickRightX = Math.abs(brickRightX - getLeftX());
        int distanceFromBrickTopY = Math.abs(brickTopY - getBottomY());
        int distanceFromBrickBottomY = Math.abs(brickBottomY - getTopY());
        int minimalDistance = Math.min(distanceFromBrickLeftX, (Math.min(distanceFromBrickRightX, Math.min(distanceFromBrickBottomY, distanceFromBrickTopY))));


        if (!brick.isBroken()) {
//            System.out.println(brick);
//            System.out.println(this);
//            System.out.print("rozjebane ");

            if (minimalDistance == distanceFromBrickLeftX) {
                if (minimalDistance == distanceFromBrickTopY) {
                    //brick hit from top-left corner
                    setBottomY(brickTopY);
                    setRightX(brickLeftX);
                    bounceX();
                    bounceY();
                    //System.out.println("lewy gorny");
                } else if (minimalDistance == distanceFromBrickBottomY) {
                    //brick hit from bottom-left corner
                    setTopY(brickBottomY);
                    setRightX(brickLeftX);
                    bounceX();
                    bounceY();
                    //System.out.println("lewy dolny");
                } else {
                    //brick hit from left side
                    setLeftX(brickLeftX);
                    bounceX();
                    //System.out.println("od lewej");
                }

            } else if (minimalDistance == distanceFromBrickRightX) {
                if (distanceFromBrickRightX == distanceFromBrickTopY) {
                    //brick hit from top-right corner
                    setBottomY(brickTopY);
                    setLeftX(brickRightX);
                    bounceX();
                    bounceY();
                   // System.out.println("prawy gorny");
                } else if (distanceFromBrickRightX == distanceFromBrickBottomY) {
                    //brick hit from bottom-right corner
                    setTopY(brickBottomY);
                    setLeftX(brickRightX);
                    bounceX();
                    bounceY();
                    //System.out.println("prawy dolny");
                } else {
                    //brick hit from right side
                    setRightX(brickRightX);
                    bounceX();
                    //System.out.println("od prawej");
                }
            } else if (minimalDistance == distanceFromBrickTopY) {
                //brick hit from top side
                setBottomY(brickTopY);
                bounceY();
                //System.out.println("od gory");
            } else {
                //brick hit from bottom side
                setTopY(brickBottomY);
                bounceY();
                //System.out.println("od dolu");
            }
            brick.decreaseHP();
        }
    }

    public void setVelocity(int velocity) {
        if(velocity<=0) return;

        int newVelocity = Math.min(velocity, MAX_VELOCITY);

        if(velocityX<0) {
            velocityX = newVelocity;
        }
        else {
            velocityX = -newVelocity;
        }
        if (velocityY<0) {
            velocityY = newVelocity;
        }
        else {
            velocityY = -newVelocity;
        }

    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getRightX() {
        return leftX + size;
    }

    public void setRightX(int rightX) {
        this.leftX = rightX - size;
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }

    public int getBottomY() {
        return topY + size;
    }

    public void setBottomY(int bottomY) {
        this.topY = bottomY - size;
    }

    public int getSize() {
        return size;
    }

    public Paint getColor() {
        return color;
    }

    public int getVelocity() {
        return Math.abs(velocityX);
    }

    public String toString() {
        return String.format("BALL: %d, %d, %d, %d\n", topY, getRightX(), getBottomY(), leftX);
    }

    private int getDistanceFromCenterX(int x) {
        return Math.abs(x - leftX + (size / 2));
    }

    private int getDistanceFromCenterY(int y) {
        return Math.abs(y - topY + (size / 2));
    }
}
