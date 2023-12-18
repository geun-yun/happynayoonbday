package src.main.java;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public abstract class AbstractGameScene implements src.main.java.GameScene {
    protected src.main.java.Main main;
    protected Scene scene;
    protected Button back_button;
    protected Pane root; // Using Pane for layout
    protected Label instructionsLabel;
    protected Button startGameButton;
    protected Label pointsLabel; // Label to display points


    public AbstractGameScene(src.main.java.Main main, double width, double height) {
        this.main = main;
        this.root = new Pane(); // Initialize the Pane
        this.back_button = createBackButton();
        this.root.getChildren().add(back_button); // Add button to the Pane
        this.scene = new Scene(root, width, height); // Set Pane as the root of the scene

        pointsLabel = new Label("Points: " + src.main.java.GlobalState.getInstance().getTotalPoints() + "/104");
        pointsLabel.setLayoutX(10); // Set X position
        pointsLabel.setLayoutY(10); // Set Y position
        root.getChildren().add(pointsLabel);

        initializeInstruction();
    }

    // Method to update points display
    public void updatePoints() {
        pointsLabel.setText("Points: " + src.main.java.GlobalState.getInstance().getTotalPoints() + "/104");
    }

    protected Button createBackButton() {
        Image back_image = new Image(getClass().getResourceAsStream("/back_button.png"));
        ImageView imageView = new ImageView(back_image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(false);

        Button back = new Button();
        back.setGraphic(imageView);
        back.setStyle("-fx-padding: 0;");
        back.setPrefSize(50, 50); // Set preferred size
        back.setLayoutX(930); // Positioning
        back.setLayoutY(530);
        back.setOnAction(e -> main.showMainScene());
        return back;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    // Abstract method that must be implemented by subclasses
    private void initializeInstruction() {
        instructionsLabel = new Label();
        startGameButton = new Button("Start Game");

        startGameButton.setOnAction(e -> startGame());

        root.getChildren().addAll(instructionsLabel, startGameButton);
    }

    protected void setInstructionText(String text) {
        instructionsLabel.setText(text);
        instructionsLabel.setFont(new Font(50));
        double xPos = (1000 - instructionsLabel.getLayoutBounds().getWidth()) / 2;
        instructionsLabel.setLayoutX(xPos);
        instructionsLabel.setLayoutY(400);
    }

    private void startGame() {
        root.getChildren().remove(instructionsLabel); // Clear previous UI elements
        root.getChildren().remove(startGameButton); // Clear previous UI elements
        displayGame();
    }
    protected abstract void displayGame();
}
