package src;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.game.Kim;
import src.game.Na;
import src.game.Yoon;

import java.io.File;


public class Main extends Application {
    Stage primaryStage;
    Scene start_scene;
    Scene main_scene;
    Button start_button;
    Button kim_button;
    Button na_button;
    Button yoon_button;
    double screen_width = 1000;
    double screen_height = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        display_start(this.primaryStage);
        display_main();


    }
    public void display_main() {
        kim_button = new Button("김");
        na_button = new Button("나");
        yoon_button = new Button("윤");

        HBox name_box = new HBox(20);
        name_box.getChildren().addAll(kim_button, na_button, yoon_button);

        kim_button.setOnAction(e -> showKim());
        na_button.setOnAction(e -> showNa());
        yoon_button.setOnAction(e -> showYoon());

        main_scene = new Scene(name_box, screen_width, screen_height);
    }

    public void display_start(Stage  primaryStage) {
        String musicFile = "assets/opening_intro.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        mediaPlayer.play();

        // Load the image
        ImageView imageView = new ImageView(new Image("assets/opening_background.jpg")); // Replace with your image path

        // Set ImageView properties to stretch the image
        imageView.setFitHeight(600); // Set to your scene height
        imageView.setFitWidth(1000); // Set to your scene width
        imageView.setPreserveRatio(false);

        // Create the fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(12), imageView);
        fadeTransition.setFromValue(0);  // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();

        Pane root = new Pane(imageView);

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

            fadeIn(text, 2, delay);

            root.getChildren().add(text);

            delay += 2.0; // Increment delay for the next line
            yPos += 50;
        }

        SVGPath heartShape = new SVGPath();
        heartShape.setContent("M2914.4 2077.3c-6.3-13-422.7-867-1144-259C763 2879.5 2894 4325.6 2914.3 4339.4c20.4-13.8 2151.3-1460 1144-2521.3-721.4-608-1137.7 246-1144 259z");
        start_button = new Button("생일 선물 게임\n        ㄱㄱ");
        start_button.setShape(heartShape);
        start_button.setFont(Font.font("System", 30));
        start_button.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;"); // Set background color and text color
        start_button.setLayoutX(350);
        start_button.setLayoutY(yPos - 50);
        start_button.setMinSize(300, 200);

        fadeIn(start_button, 2, delay);
        root.getChildren().add(start_button);
        start_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(main_scene);
            }
        });

        start_scene = new Scene(root, screen_width, screen_height);
        Image logo = new Image("assets/logo.jpg");
        primaryStage.getIcons().add(logo);
        primaryStage.setTitle("나윤이 생일 축하해!");
        primaryStage.setScene(start_scene);
        primaryStage.show();


    }

    public void showMainScene() {
        primaryStage.setScene(main_scene);
        primaryStage.show();
    }

    public void showKim() {
        Kim kim = new Kim(this);
        primaryStage.setScene(kim.getScene());
        primaryStage.show();
    }

    public void showNa() {
        Na na = new Na(this);
        primaryStage.setScene(na.getScene());
        primaryStage.show();
    }

    public void showYoon() {
        Yoon yoon = new Yoon(this);
        primaryStage.setScene(yoon.getScene());
        primaryStage.show();
    }

    public void fadeIn(Node node, double duration, double delay) {
        node.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(duration), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setDelay(Duration.seconds(delay));
        fadeIn.play();
    }

}
