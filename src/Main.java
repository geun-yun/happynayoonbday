package src;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.gui.Controller;


public class Main extends Application {
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();

        Font aegukFont = Font.loadFont(getClass().getResourceAsStream("/assets/독립서체_윤봉길_GS.otf"), 24);
        String[] lines = {
                "천사 (나무위키): 하늘의 사자. '신의 뜻을 전하기 위해 내려온 자'라는 의미이다.",
                "본명 '김나윤'은 1999년 01월 04일 생으로 세기말에 강림하신 1004 그 자체다.",
                "지금부터 우리 천사님의 탄생일을 축하하기 위해,",
                "그 분의 이름을 가지고 노는 시간을 가져보겠다.",
                "나윤아 생일 진심으로 축하하고 사랑해!!! :)"
                };
        double delay = 0.0;
        double yPos = 120;
        for (String line : lines) {
            Text text = new Text(line);
            text.setFont(aegukFont);

            double xPos = (1000 - text.getLayoutBounds().getWidth()) / 2;
            text.setLayoutX(xPos);
            text.setLayoutY(yPos);

            fadeIn(text, delay);

            root.getChildren().add(text);

            delay += 2.0; // Increment delay for the next line
            yPos += 50;
        }

        button = new Button("생일 선물 게임\n        ㄱㄱ");
        button.setFont(Font.font("System", 30));
        button.setLayoutX(380);
        button.setLayoutY(yPos);
        button.setPrefHeight(100);

        fadeIn(button, delay);
        root.getChildren().add(button);

        Scene scene = new Scene(root, 1000, 600);
        Image logo = new Image("assets/logo.jpg");
        primaryStage.getIcons().add(logo);
        primaryStage.setTitle("나윤이 생일 축하해!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void fadeIn(Node node, double delay) {
        node.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setDelay(Duration.seconds(delay));
        fadeIn.play();
    }
}
