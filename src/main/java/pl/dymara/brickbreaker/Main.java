package pl.dymara.brickbreaker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {

    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BrickBreaker.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Controller controller = loader.getController();
        controller.setEventListenerOnScene(scene);

        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(
                (WindowEvent t) -> {
                    Platform.exit();
                    System.exit(0);
                }
        );
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
