package src.game;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import src.Main;

import java.util.ArrayList;
import java.util.List;

public class Kim extends AbstractGameScene {
    private AnimationTimer timer;
    private List<Node> drop = new ArrayList<>();
    private double mouseX;
    private Rectangle cont;
    private double speed;
    private double falling;
    private Label lblMissed;
    private int missed;

    public Kim(Main main) {
        super(main, 1000, 600);
    }

    @Override
    public void initialize() {
        displayGame();
    }

    public void displayGame() {
        lblMissed = new Label("Missed: 0");
        lblMissed.setLayoutX(10);
        lblMissed.setLayoutY(10);
        missed = 0;

        speed = 1;
        falling = 500;

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(falling), event -> {
            speed += falling / 3000;
            Circle newCircle = createCircle();
            drop.add(newCircle);
            root.getChildren().add(newCircle);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gameUpdate();
            }
        };
        timer.start();

        cont = createRectangle();
        root.getChildren().addAll(cont, lblMissed);

        scene.setOnMouseMoved(e -> mouseX = e.getX());
    }

    private Circle createCircle() {
        Circle circle = new Circle();
        circle.setLayoutX(rand(0, 400));
        circle.setLayoutY(1);
        circle.setRadius(6);
        circle.setFill(Color.BLUE);
        return circle;
    }

    private Rectangle createRectangle() {
        Rectangle rectangle = new Rectangle();
        rectangle.setLayoutX(200);
        rectangle.setLayoutY(550);
        rectangle.setHeight(50);
        rectangle.setWidth(70);
        rectangle.setFill(Color.GREEN);
        return rectangle;
    }

    private int rand(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private void gameUpdate() {
        cont.setLayoutX(mouseX);

        for (int i = 0; i < drop.size(); i++) {
            Circle circle = (Circle) drop.get(i);
            circle.setLayoutY(circle.getLayoutY() + speed + circle.getLayoutY() / 150);

            if (circle.getLayoutX() > cont.getLayoutX() && circle.getLayoutX() < cont.getLayoutX() + 70 &&
                    circle.getLayoutY() >= 550) {
                root.getChildren().remove(circle);
                drop.remove(i);
            } else if (circle.getLayoutY() >= 590) {
                root.getChildren().remove(circle);
                drop.remove(i);
                missed += 1;
                lblMissed.setText("Missed: " + missed);
            }
        }
    }
}
