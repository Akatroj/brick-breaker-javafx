package pl.dymara.brickbreaker.powerups;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.concurrent.ThreadLocalRandom;

public enum PowerUpType {
    INCREASE_PADDLE_WIDTH, INCREASE_PADDLE_SPEED, INCREASE_SCORE, EXTRA_LIFE, ADD_BALL;

    private static final PowerUpType[] VALUES = PowerUpType.values();
    private static final int SIZE = VALUES.length;

    public static PowerUpType getRandomPowerUpType() {
        return VALUES[ThreadLocalRandom.current().nextInt(SIZE)];
    }

    public static Paint color(PowerUpType type) {
        switch (type) {
            case ADD_BALL:
                return Color.MAGENTA;
            case INCREASE_PADDLE_WIDTH:
                return Color.CHOCOLATE;
            case INCREASE_SCORE:
                return Color.ROYALBLUE;
            case INCREASE_PADDLE_SPEED:
                return Color.TEAL;
            case EXTRA_LIFE:
                return Color.LIMEGREEN;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
