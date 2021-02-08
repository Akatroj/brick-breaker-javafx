package pl.dymara.brickbreaker;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import pl.dymara.brickbreaker.powerups.PowerUp;

import java.util.List;

public class Controller {

    @FXML
    private Canvas canvas;
    @FXML
    private Label highScoreLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private Label livesLabel;
    @FXML
    private Label widthLabel;
    @FXML
    private Label speedLabel;

    private GraphicsContext gc;
    private int width;
    private int height;

    private Paddle paddle;
    private List<Brick> bricks;
    private List<Ball> balls;
    private List<PowerUp> powerUps;

    private Game game;


    public void initialize() {
        width = (int) canvas.getWidth();
        height = (int) canvas.getHeight();

        gc = canvas.getGraphicsContext2D();

        game = new Game(this);
        paddle = game.getPaddle();
        balls = game.getBalls();
        bricks = game.getBricks();
        powerUps = game.getPowerUps();

        draw();
    }

    public void setEventListenerOnScene(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> game.handleKeyboardInputs(keyEvent));
    }


    public void draw() {
       clear();
       drawPaddle();
       drawBalls();
       drawBricks();
       drawPowerUps();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void clear() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
    }

    private void drawPaddle() {
        gc.setFill(paddle.getColor());
        gc.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());
    }

    private void drawBricks() {
        for (Brick brick : bricks) {
            if(!brick.isBroken()) {
                gc.setFill(brick.getColor());
                gc.fillRect(brick.getBrickLeftX(), brick.getBrickTopY(), Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT);
            }
        }
    }

    private void drawBalls() {
        for (Ball ball : balls) {
            gc.setFill(ball.getColor());
            gc.fillOval(ball.getLeftX(), ball.getTopY(), ball.getSize(), ball.getSize());
        }
    }

    private void drawPowerUps() {
        for(PowerUp powerUp : powerUps) {
            gc.setFill(powerUp.getColor());
            gc.fillRect(powerUp.getLeftX(), powerUp.getTopY(), powerUp.getWidth(), powerUp.getHeight());
        }
    }

    public void setHighScoreLabel(long highScore) {
        highScoreLabel.setText(String.valueOf(highScore));
    }

    public void setScoreLabel(long score) {
        scoreLabel.setText(String.valueOf(score));
    }

    public void setLivesLabel(int lives) {
        livesLabel.setText(String.valueOf(lives));
    }

    public void setWidthLabel(int widthLevel) {
        widthLabel.setText(String.valueOf(widthLevel));
    }

    public void setSpeedLabel(int speedLevel) {
        speedLabel.setText(String.valueOf(speedLevel));
    }

    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }

    public void setPowerUps(List<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }
}
