package src.main.java;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;


public class Main extends Application {
    Stage primaryStage;
    Scene startScene;
    Scene mainScene;
    Scene endScene;
    Button startButton;
    Button kimButton;
    Button naButton;
    Button yoonButton;
    Button endButton;
    double screenWidth = 1000;
    double screenHeight = 600;
    Media mainSound;
    MediaPlayer mainMediaPlayer;

    Text kimPointsText;
    Text naPointsText;
    Text yoonPointsText;
    StackPane kimPointsStack;
    StackPane naPointsStack;
    StackPane yoonPointsStack;
    MediaPlayer happyBday;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        display_start(this.primaryStage);
    }

    public void display_start(final Stage  primaryStage) {
        if (happyBday != null) {
            happyBday.stop();
        }

        String musicFile = "assets/opening_intro.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        mediaPlayer.play();

        // Load the image
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/opening_background.jpg")));

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
            text.setFont(aegukFont(30));

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
        Image logo = new Image(getClass().getResourceAsStream("/logo.jpg"));
        primaryStage.getIcons().add(logo);
        primaryStage.setTitle("나윤이 생일 축하해!");
        primaryStage.setScene(startScene);
        primaryStage.show();
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

        Image kimImage = new Image(getClass().getResourceAsStream("/kim.jpg"));
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

        Image naImage = new Image(getClass().getResourceAsStream("/na.jpg"));
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

        Image yoonImage = new Image(getClass().getResourceAsStream("/yoon.jpg"));
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

        Image backgroundImage = new Image(getClass().getResourceAsStream("/main_background.png"));
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(screenWidth, screenHeight, false, false, true, true)
        );

        endButton = new Button();
        ImageView endGame = new ImageView("/ultimate_points.png");
        endGame.setFitWidth(200);
        endGame.setFitHeight(200);
        endButton.setGraphic(endGame);

        endButton.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-border-width: 0;");
        endButton.setLayoutX(400);
        endButton.setLayoutY(75);
        endButton.setOnAction(e -> display_end());
        endButton.setVisible(false);

        ImageView kimPointsImage = new ImageView(new Image(getClass().getResourceAsStream("/kim_points.png")));
        kimPointsImage.setFitHeight(100); // Set height as needed
        kimPointsImage.setFitWidth(100); // Set width as needed

        kimPointsText = new Text(String.format("%.1f", src.main.java.GlobalState.getInstance().getTotalPoints(0)) + "/104");
        kimPointsText.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font, style, and size
        kimPointsText.setFill(Color.WHITE); // Set text color to white

        // Adding a black stroke (outline) to the text for visibility
        kimPointsText.setStroke(Color.BLACK);
        kimPointsText.setStrokeWidth(0.8); // Adjust width as needed

        kimPointsStack = new StackPane(kimPointsImage, kimPointsText);
        kimPointsStack.setLayoutX(15); // Position of the stack pane
        kimPointsStack.setLayoutY(15);
        fadeIn(kimPointsStack, 3,0);

        ImageView naPointsImage = new ImageView(new Image(getClass().getResourceAsStream("/na_points.png")));
        naPointsImage.setFitHeight(100); // Set height as needed
        naPointsImage.setFitWidth(100); // Set width as needed

        naPointsText = new Text(String.format("%.1f", src.main.java.GlobalState.getInstance().getTotalPoints(1)) + "/104");
        naPointsText.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font, style, and size
        naPointsText.setFill(Color.WHITE); // Set text color to white

        // Adding a black stroke (outline) to the text for visibility
        naPointsText.setStroke(Color.BLACK);
        naPointsText.setStrokeWidth(0.8); // Adjust width as needed

        naPointsStack = new StackPane(naPointsImage, naPointsText);
        naPointsStack.setLayoutX(115); // Position of the stack pane
        naPointsStack.setLayoutY(15);
        fadeIn(naPointsStack, 3, 2);

        ImageView yoonPointsImage = new ImageView(new Image(getClass().getResourceAsStream("/yoon_points.png")));
        yoonPointsImage.setFitHeight(100); // Set height as needed
        yoonPointsImage.setFitWidth(100); // Set width as needed

        yoonPointsText = new Text(String.format("%.1f", src.main.java.GlobalState.getInstance().getTotalPoints(2)) + "/104");
        yoonPointsText.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font, style, and size
        yoonPointsText.setFill(Color.WHITE); // Set text color to white

        // Adding a black stroke (outline) to the text for visibility
        yoonPointsText.setStroke(Color.BLACK);
        yoonPointsText.setStrokeWidth(0.8); // Adjust width as needed

        yoonPointsStack = new StackPane(yoonPointsImage, yoonPointsText);
        yoonPointsStack.setLayoutX(215); // Position of the stack pane
        yoonPointsStack.setLayoutY(15);
        fadeIn(yoonPointsStack, 3, 3);

        Pane root = new Pane();
        root.setBackground(new Background(bgImage));
        root.getChildren().addAll(nameBox,endButton, kimPointsStack, naPointsStack, yoonPointsStack);
        nameBox.setLayoutX(60);
        nameBox.setLayoutY(270);

        mainScene = new Scene(root, screenWidth, screenHeight);

        kimButton.setOnAction(e -> showKim());
        naButton.setOnAction(e -> showNa());
        yoonButton.setOnAction(e -> showYoon());
    }

    public void display_end() {
        mainMediaPlayer.stop();
        primaryStage.setTitle("나윤이 생일 축하 편지");

        String musicFile = "assets/happy_bday.mp3";
        Media happyBdayMedia = new Media(new File(musicFile).toURI().toString());
        happyBday = new MediaPlayer(happyBdayMedia);
        happyBday.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        happyBday.setVolume(5);
        happyBday.play();

        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/nayoon.png")));
        imageView.setOpacity(0.2);

        // Set ImageView properties to stretch the image
        imageView.setFitHeight(600); // Set to your scene height
        imageView.setFitWidth(1000); // Set to your scene width
        imageView.setPreserveRatio(false);

        Text letter = new Text(src.main.java.BirthdayLetter.getInstance().getLetter());
        letter.setFont(letterFont(25));
        letter.setFill(Color.BLACK); // Set letter color
        letter.setLayoutX(200);
        letter.setLayoutY(50);

        Button restartButton = new Button();
        restartButton.setOnAction(e -> restart());

        ImageView restartImage = new ImageView(new Image(getClass().getResourceAsStream("/restart.png")));
        restartImage.setFitHeight(100); // Set height as needed
        restartImage.setFitWidth(100); // Set width as needed

        StackPane restartStack = new StackPane(restartImage);
        restartButton.setGraphic(restartStack); // Set the stack as the button's graphic
        restartButton.setStyle("-fx-padding: 10; -fx-background-color: transparent;"); // Optional: adjust styling

        // Now, use restartButton in your layout
        Pane root = new Pane();
        root.getChildren().addAll(imageView, letter, restartButton);

        // Set the layout position of the restartButton if needed
        restartButton.setLayoutX(875); // Position of the button
        restartButton.setLayoutY(475);

        endScene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(endScene);
        primaryStage.show();
    }

    public void restart() {
        display_start(primaryStage);
        src.main.java.GlobalState.getInstance().resetPoints();
    }

    public void showMainScene() {
        mainMediaPlayer.play();
        primaryStage.setTitle("'김나윤' 폼 미쳤다!");
        primaryStage.setScene(mainScene);
        primaryStage.show();

        boolean isFinished = true;
        for (int i = 0; i < 3; i++) {
            if (src.main.java.GlobalState.getInstance().getTotalPoints(i) < 0) {
                isFinished = false;
                break;
            }
        }

        double kimPoints = src.main.java.GlobalState.getInstance().getTotalPoints(0);
        double naPoints = src.main.java.GlobalState.getInstance().getTotalPoints(1);
        double yoonPoints = src.main.java.GlobalState.getInstance().getTotalPoints(2);
        if (kimPoints >= 104) {
            kimPointsText.setFill(Color.GREEN);
        }
        if (naPoints >= 104) {
            naPointsText.setFill(Color.GREEN);
        }
        if (yoonPoints >= 104) {
            yoonPointsText.setFill(Color.GREEN);
        }
        kimPointsText.setText(String.format("%.1f", kimPoints) + "/104");
        naPointsText.setText(String.format("%.1f", naPoints) + "/104");
        yoonPointsText.setText(String.format("%.1f", yoonPoints) + "/104");
        fadeIn(kimPointsStack, 2, 0.5);
        fadeIn(naPointsStack, 2, 0.5);
        fadeIn(yoonPointsStack, 2, 0.5);

        if (isFinished) {
            System.out.println("finished");
            endButton.setVisible(true);
            fadeIn(endButton, 5,0);
        }
    }

    public void showKim() {
        mainMediaPlayer.pause();
        primaryStage.setTitle("'김' 폼 미쳤다!");
        src.main.java.Kim kim = new src.main.java.Kim(this);
        primaryStage.setScene(kim.getScene());
        primaryStage.show();
    }

    public void showNa() {
        mainMediaPlayer.pause();
        primaryStage.setTitle("'나' 폼 미쳤다!");
        src.main.java.Na na = new src.main.java.Na(this);
        primaryStage.setScene(na.getScene());
        primaryStage.show();
    }

    public void showYoon() {
        mainMediaPlayer.pause();
        primaryStage.setTitle("'윤' 폼 미쳤다!");

        src.main.java.Yoon yoon = new src.main.java.Yoon(this);
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
        return Font.loadFont(getClass().getResourceAsStream("/JSArirang.otf"), size);
    }

    public Font letterFont(double size) {
        return Font.loadFont(getClass().getResourceAsStream("/letter_font.otf"), size);
    }
}
