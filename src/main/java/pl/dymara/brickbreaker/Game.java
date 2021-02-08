package pl.dymara.brickbreaker;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import pl.dymara.brickbreaker.powerups.PowerUp;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private static final int START_LIVES = 3;
    private static final int SCORE_BONUS = 2000;

    private final int width;
    private final int height;
    private Paddle paddle;
    private final Controller controller;
    private List<Brick> bricks;
    private final List<Ball> balls;

    private final List<Ball> ballsToRemove;

    private final List<PowerUp> powerUps;
    private BrickContainer brickContainer;
    private int bricksY;
    private TimerTask gameLoopTask;

    private long score;
    private long highScore;

    private boolean gameStarted;
    private boolean gamePaused;

    private int lives;
    private boolean debugMode = false;

    public Game(Controller controller) {
        this.controller = controller;

        width = controller.getWidth();
        height = controller.getHeight();

        balls = new LinkedList<>();
        ballsToRemove = new LinkedList<>();
        powerUps = new LinkedList<>();

        reset();
    }

    public void handleKeyboardInputs(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case LEFT:
                paddle.moveLeft();
                if (gamePaused && !debugMode) balls.forEach(ball -> ball.centerBallOnPaddle(paddle));
                break;
            case RIGHT:
                paddle.moveRight();
                if (gamePaused && !debugMode) balls.forEach(ball -> ball.centerBallOnPaddle(paddle));
                break;
            case UP:
                if (debugMode) {
                    gameLoop();
                }
                else if (!gameStarted) {
                    startNewGame();
                }
                else if (gamePaused) {
                    resumeGame();
                }
                break;
            case ESCAPE:
                if (gameStarted) {
                    if (!debugMode) {
                        debugMode = true;
                        pauseGame();
                    } else {
                        debugMode = false;
                        startNewGame();
                    }
                }
        }
        if (!gameStarted || gamePaused) controller.draw();
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    private void startNewGame() {
        gameStarted = true;
        setScore(0);
        setLives(START_LIVES);
        resumeGame();
    }

    private void resumeGame() {
        gamePaused = false;
        Timer timer = new Timer();
        gameLoopTask = new TimerTask() {
            @Override
            public void run() {
                gameLoop();
            }
        };
        timer.schedule(gameLoopTask,0,33);
    }

    private void gameLoop() {
        if (lives>0) {
            for(Ball ball : balls) {
                ball.move();
                detectBallCollisions(ball);
            }

            balls.removeAll(ballsToRemove);
            ballsToRemove.clear();
            if(balls.size()<1) {
                setLives(lives-1);
                startNewRound();
            }

            Iterator<PowerUp> iterator = powerUps.iterator();
            while(iterator.hasNext()) {
                PowerUp powerUp = iterator.next();
                powerUp.move();
                if(paddle.checkIfPowerUpOnPaddle(powerUp)) {
                    activatePowerUp(powerUp);
                    iterator.remove();
                }
            }

            controller.draw();
        }
        if (lives<=0) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game over");
                alert.setHeaderText(null);
                alert.setContentText("Game over!");

                alert.showAndWait();
                reset();
            });
        }
    }

    private void detectBallCollisions(Ball ball) {

        //bounce from walls
        if (ball.getLeftX() <= 0 || ball.getRightX() >= width) {
            if (ball.getLeftX() < 0) ball.setLeftX(0);
            if (ball.getRightX() > width) ball.setRightX(width);

            ball.bounceX();
        }

        //bounce from paddle
        else if (ball.getBottomY() >= paddle.getY()) {
            if (paddle.checkIfBallOnPaddle(ball)) {
                ball.setBottomY(paddle.getY());
                ball.bounceY();
            } else if (ball.getTopY() > height) {
                ballsToRemove.add(ball);
            }
        }

        //bounce from bricks
        else if (ball.getTopY() <= bricksY) {
            Brick brick = brickContainer.checkBrickCollision(ball);
            if (brick != null) {
                setScore(score + Brick.POINTS_REWARD);
                if(ThreadLocalRandom.current().nextBoolean()) {
                    powerUps.add(brick.dropPowerUp());
                }
            }
        }

        //bounce from roof
        if (ball.getTopY() <= 0) {
            ball.setTopY(0);
            ball.bounceY();
        }
    }

    private void activatePowerUp(PowerUp powerUp) {
        switch (powerUp.getType()) {
            case INCREASE_SCORE:
                setScore(score + SCORE_BONUS);
                break;
            case INCREASE_PADDLE_SPEED:
                paddle.levelUpSpeed();
                updatePaddleStats();
                break;
            case INCREASE_PADDLE_WIDTH:
                paddle.levelUpWidth();
                updatePaddleStats();
                break;
            case ADD_BALL:
                balls.add(new Ball(paddle));
                break;
            case EXTRA_LIFE:
                setLives(lives+1);
                break;
        }
        System.out.println(powerUp.getType());
    }

    private void startNewRound() {
        gameLoopTask.cancel();
        powerUps.clear();
        paddle.center();
        gamePaused = true;
        balls.add(new Ball(paddle));
        controller.draw();
    }

    private void setScore(long score) {
        this.score = score;
        highScore = Math.max(highScore, score);
        Platform.runLater(()-> {
            controller.setScoreLabel(score);
            controller.setHighScoreLabel(highScore);
        });
    }

    private void setLives(int lives) {
        this.lives = lives;
        Platform.runLater(() -> controller.setLivesLabel(lives));
    }
    private void updatePaddleStats() {
        Platform.runLater(() -> {
            controller.setSpeedLabel(paddle.getSpeedLevel());
            controller.setWidthLabel(paddle.getWidthLevel());
        });
    }

    private void pauseGame() {
        gamePaused = true;
        gameLoopTask.cancel();
    }

    private void reset() {
        gameStarted = false;
        gamePaused = true;
        brickContainer = new BrickContainer(width);
        bricks = brickContainer.getBricks();
        bricksY = brickContainer.getHeight();
        controller.setBricks(bricks);
        paddle = new Paddle(width, height);
        controller.setPaddle(paddle);
        balls.clear();
        balls.add(new Ball(paddle));
        controller.setBalls(balls);
        controller.setPowerUps(powerUps);
        setScore(0);
        updatePaddleStats();
        controller.draw();
    }
}

