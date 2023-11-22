package src.game;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import src.Main;

public abstract class AbstractGameScene implements GameScene {
    protected Main main;
    protected Scene scene;
    protected Button back_button;
    protected Pane root; // Using Pane for layout

    public AbstractGameScene(Main main, double width, double height) {
        this.main = main;
        this.root = new Pane(); // Initialize the Pane
        this.back_button = createBackButton();
        this.root.getChildren().add(back_button); // Add button to the Pane
        this.scene = new Scene(root, width, height); // Set Pane as the root of the scene
        initialize();
    }

    protected Button createBackButton() {
        Image back_image = new Image("assets/back_button.png");
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
    @Override
    public abstract void initialize();
}
