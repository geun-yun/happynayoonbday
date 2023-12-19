package src.main.java;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;

public abstract class AbstractGameScene implements src.main.java.GameScene {
    protected src.main.java.Main main;
    protected Scene scene;
    protected Button back_button;
    protected Pane root; // Using Pane for layout
    protected Text instructionsLabel;
    protected Button startGameButton;
    private MediaPlayer typewriterSoundPlayer;
    protected ImageView pointsImage;
    protected Text pointsText;

    protected MediaPlayer mediaPlayer;


    public AbstractGameScene(src.main.java.Main main, double width, double height) {
        this.main = main;
        this.root = new Pane(); // Initialize the Pane
        this.back_button = createBackButton();
        this.root.getChildren().add(back_button); // Add button to the Pane
        this.scene = new Scene(root, width, height); // Set Pane as the root of the scene

        initializeInstruction();
    }

    // Method to update points display
    public void updatePoints(int index) {
        double currPoints = src.main.java.GlobalState.getInstance().getTotalPoints(index);
        if (currPoints >= 104) {
            pointsText.setFill(Color.GREEN);
        }
        pointsText.setText(String.format("%.1f", currPoints) + "/104");
        Media ching = new Media(new File("assets/ching.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(ching);
        player.setCycleCount(1);
        player.play();
    }

    protected void createPoints(String image, int index) {
        pointsImage = new ImageView(new Image(getClass().getResourceAsStream(image)));
        pointsImage.setFitHeight(100); // Set height as needed
        pointsImage.setFitWidth(100); // Set width as needed

        pointsText = new Text(String.format("%.1f", src.main.java.GlobalState.getInstance().getTotalPoints(index)) + "/104");
        pointsText.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font, style, and size
        pointsText.setFill(Color.WHITE); // Set text color to white

        // Adding a black stroke (outline) to the text for visibility
        pointsText.setStroke(Color.BLACK);
        pointsText.setStrokeWidth(0.8); // Adjust width as needed

        StackPane pointsStack = new StackPane(pointsImage, pointsText);
        pointsStack.setLayoutX(15); // Position of the stack pane
        pointsStack.setLayoutY(15);

        root.getChildren().add(pointsStack);
    }

    protected Button createBackButton() {
        Image back_image = new Image(getClass().getResourceAsStream("/back_button.png"));
        ImageView imageView = new ImageView(back_image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.setPreserveRatio(false);

        Button back = new Button();
        back.setGraphic(imageView);
        back.setStyle("-fx-padding: 0;");

        back.setLayoutX(900); // Positioning
        back.setLayoutY(500);

        back.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-border-width: 0;");
        back.setShape(new Circle(50)); // Set the shape of the button to be a circle
        back.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        back.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        back.setPrefSize(80, 80); // Set preferred size
        back.setOnAction(e -> backToMain());
        return back;
    }

    private void backToMain() {
        if (typewriterSoundPlayer != null && typewriterSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            typewriterSoundPlayer.stop();
        }
        mediaPlayer.stop();
        main.showMainScene();
    }


    @Override
    public Scene getScene() {
        return scene;
    }

    private void initializeInstruction() {
        instructionsLabel = new Text();
        instructionsLabel.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 25));
        instructionsLabel.setFill(Color.WHITE);

        startGameButton = new Button();
        ImageView startGame = new ImageView("/game_start_button.png");
        startGame.setFitWidth(100);
        startGame.setFitHeight(100);
        startGameButton.setGraphic(startGame);

        startGameButton.setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-border-width: 0;");
        startGameButton.setShape(new Circle(50)); // Set the shape of the button to be a circle
        startGameButton.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        startGameButton.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        startGameButton.setPrefSize(50, 50);

        startGameButton.setOnAction(e -> startGame());
        startGameButton.setVisible(false);

        String typewriteSoundPath = "assets/typewrite.mp3";
        Media sound = new Media(new File(typewriteSoundPath).toURI().toString());
        typewriterSoundPlayer = new MediaPlayer(sound);
        typewriterSoundPlayer.setVolume(3);

        root.getChildren().addAll(instructionsLabel, startGameButton);

    }

    protected void setBackGroundAsset(String image, String music) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(image)));

        // Set ImageView properties to stretch the image
        imageView.setFitHeight(600); // Set to your scene height
        imageView.setFitWidth(1000); // Set to your scene width
        imageView.setPreserveRatio(false);

        root.getChildren().add(0, imageView);

        Media sound = new Media(new File(music).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
        mediaPlayer.play();
    }

    protected void setInstructionText(String text, double posX, double posY) {
        animateText(text);

        // Wait for the text to be populated to get the correct dimensions
        Timeline delay = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            instructionsLabel.setLayoutX(posX);
            instructionsLabel.setLayoutY(posY);

            // Position the start button just below the instruction label\
            double xPosBtn = (1000 - startGameButton.getLayoutBounds().getWidth()) / 2;
            startGameButton.setLayoutX(xPosBtn);
            startGameButton.setLayoutY(470); // 10 is a small padding
        }));
        delay.play();
    }

    private void animateText(String text) {
        typewriterSoundPlayer.play();
        final int[] i = {0};
        Timeline timeline = new Timeline();
        timeline.setCycleCount(text.length());
        KeyFrame keyFrame = new KeyFrame(Duration.millis(50), event -> {
            if (i[0] < text.length()) {
                instructionsLabel.setText(instructionsLabel.getText() + text.charAt(i[0]));
                i[0]++;
            }
            // Check if the last character is reached
            if (i[0] >= text.length()) {
                startGameButton.setVisible(true); // Make button visible only after the text is completely displayed
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        timeline.setOnFinished(e -> typewriterSoundPlayer.stop());
    }
    private void startGame() {
        root.getChildren().remove(instructionsLabel); // Clear previous UI elements
        root.getChildren().remove(startGameButton); // Clear previous UI elements
        displayGame();
    }
    protected abstract void displayGame();

    protected void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // This method can be overridden by subclasses to handle key events
    protected void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            // Default implementation can be empty or contain common logic
        }
    }

    // Call this method in the constructor or an initialization block
    protected void setupKeyHandling() {
        scene.setOnKeyPressed(this::onKeyPressed);
    }
}
