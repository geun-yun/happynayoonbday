package src;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import src.game.Kim;
import src.game.Na;
import src.game.Yoon;

import javax.tools.Tool;
import java.io.File;


public class Main extends Application {
    Stage primaryStage;
    Scene startScene;
    Scene mainScene;
    Button startButton;
    Button kimButton;
    Button naButton;
    Button yoonButton;
    double screenWidth = 1000;
    double screenHeight = 600;
    Media mainSound;
    MediaPlayer mainMediaPlayer;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        display_start(this.primaryStage);
    }
    public void display_main() {
        primaryStage.setTitle("'김나윤' 폼 미쳤다!");

        String musicFile = "assets/main_bgm.mp3";
        mainSound = new Media(new File(musicFile).toURI().toString());
        mainMediaPlayer = new MediaPlayer(mainSound);
        mainMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        mainMediaPlayer.play();

        kimButton = new Button();
        naButton = new Button();
        yoonButton = new Button();
        kimButton.setStyle("-fx-font-size: 70px; -fx-pref-width: 300; -fx-pref-height: 300px;");
        naButton.setStyle("-fx-font-size: 70px; -fx-pref-width: 300; -fx-pref-height: 300px;");
        yoonButton.setStyle("-fx-font-size: 70px; -fx-pref-width: 300; -fx-pref-height: 300px;");

        Image kimImage = new Image("assets/kim.jpg");
        ImageView kimImageView = new ImageView(kimImage);
        kimImageView.setFitWidth(250);
        kimImageView.setFitHeight(250);
        kimImageView.setPreserveRatio(false);
        kimButton.setGraphic(kimImageView);
        kimButton.setContentDisplay(ContentDisplay.TOP);
        kimButton.setStyle("-fx-background-color: transparent;");
        StackPane kimPane = new StackPane(kimButton);
        Label kimText = new Label("김");
        kimText.setFont(aegukFont(100)); // Adjust font size as needed
        kimText.setAlignment(Pos.CENTER);
        kimPane.setAlignment(Pos.CENTER);
        kimImageView.setMouseTransparent(true);
        kimText.setMouseTransparent(true);
        kimPane.getChildren().addAll(kimImageView, kimText);

        Image naImage = new Image("assets/na.jpg");
        ImageView naImageView = new ImageView(naImage);
        naImageView.setFitWidth(250);
        naImageView.setFitHeight(250);
        naImageView.setPreserveRatio(false);
        naButton.setGraphic(naImageView);
        naButton.setContentDisplay(ContentDisplay.TOP);
        naButton.setStyle("-fx-background-color: transparent;");
        StackPane naPane = new StackPane(naButton);
        Label naText = new Label("나");
        naText.setFont(aegukFont(100)); // Adjust font size as needed
        naText.setAlignment(Pos.CENTER);
        naPane.setAlignment(Pos.CENTER);
        naImageView.setMouseTransparent(true);
        naText.setMouseTransparent(true);
        naPane.getChildren().addAll(naImageView, naText);

        Image yoonImage = new Image("assets/yoon.png");
        ImageView yoonImageView = new ImageView(yoonImage);
        yoonImageView.setFitWidth(250);
        yoonImageView.setFitHeight(250);
        yoonImageView.setPreserveRatio(false);
        yoonButton.setGraphic(yoonImageView);
        yoonButton.setContentDisplay(ContentDisplay.TOP);
        yoonButton.setStyle("-fx-background-color: transparent;");
        StackPane yoonPane = new StackPane(yoonButton);
        Label yoonText = new Label("윤");
        yoonText.setFont(aegukFont(100)); // Adjust font size as needed
        yoonText.setAlignment(Pos.CENTER);
        yoonPane.setAlignment(Pos.CENTER);
        yoonImageView.setMouseTransparent(true);
        yoonText.setMouseTransparent(true);
        yoonPane.getChildren().addAll(yoonImageView, yoonText);

        HBox nameBox = new HBox(45);
        nameBox.getChildren().addAll(kimPane, naPane, yoonPane);
        nameBox.setAlignment(Pos.CENTER);

        Image backgroundImage = new Image("assets/main_background.png");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(screenWidth, screenHeight, false, false, true, true)
        );

        Pane root = new Pane();
        root.setBackground(new Background(bgImage));
        root.getChildren().add(nameBox);
        nameBox.setLayoutX(60);
        nameBox.setLayoutY(270);

        mainScene = new Scene(root, screenWidth, screenHeight);

        kimButton.setOnAction(e -> showKim());
        naButton.setOnAction(e -> showNa());
        yoonButton.setOnAction(e -> showYoon());
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
        imageView.setFitHeight(screenHeight); // Set to your scene height
        imageView.setFitWidth(screenWidth); // Set to your scene width
        imageView.setPreserveRatio(false);

        // Create the fade transition
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(12), imageView);
        fadeTransition.setFromValue(0);  // Start fully transparent
        fadeTransition.setToValue(1);    // End fully opaque
        fadeTransition.play();

        Pane root = new Pane(imageView);


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
            text.setFont(aegukFont(24));

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
        startButton = new Button("생일 선물 게임\n        ㄱㄱ");
        startButton.setShape(heartShape);
        startButton.setFont(Font.font("System", 30));
        startButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white;"); // Set background color and text color
        startButton.setLayoutX(350);
        startButton.setLayoutY(yPos - 50);
        startButton.setMinSize(300, 200);

        fadeIn(startButton, 2, delay);
        root.getChildren().add(startButton);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                display_main();
                primaryStage.setScene(mainScene);
                mediaPlayer.stop();
            }
        });

        startScene = new Scene(root, screenWidth, screenHeight);
        Image logo = new Image("assets/logo.jpg");
        primaryStage.getIcons().add(logo);
        primaryStage.setTitle("나윤이 생일 축하해!");
        primaryStage.setScene(startScene);
        primaryStage.show();


    }

    public void showMainScene() {
        mainMediaPlayer.play();
        primaryStage.setTitle("'김나윤' 폼 미쳤다!");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void showKim() {
        mainMediaPlayer.pause();
        primaryStage.setTitle("'김' 폼 미쳤다!");
        Kim kim = new Kim(this);
        primaryStage.setScene(kim.getScene());
        primaryStage.show();
    }

    public void showNa() {
        mainMediaPlayer.pause();
        primaryStage.setTitle("'나' 폼 미쳤다!");
        Na na = new Na(this);
        primaryStage.setScene(na.getScene());
        primaryStage.show();
    }

    public void showYoon() {
        mainMediaPlayer.pause();
        primaryStage.setTitle("'윤' 폼 미쳤다!");
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

    public Font aegukFont(double size) {
        return Font.loadFont(getClass().getResourceAsStream("/assets/독립서체_윤봉길_GS.otf"), size);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
