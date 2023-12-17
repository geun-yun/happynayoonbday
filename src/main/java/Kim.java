package src.main.java;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Kim extends src.main.java.AbstractGameScene {
    private AnimationTimer timer;
    private List<ImageView> drop = new ArrayList<>();
    private double mouseX;
    private Rectangle cont;
    private double speed;
    private double falling;
    private Label lblMissed;
    private int missed;

    public Kim(src.main.java.Main main) {
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
            ImageView seaweed = createSeaweed();
            drop.add(seaweed);
            root.getChildren().add(seaweed);
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

    private ImageView createSeaweed() {
        Image seaweed = new Image(getClass().getResourceAsStream("/seaweed.jpg"));
        ImageView seaweedImageView = new ImageView(seaweed);
        seaweedImageView.setLayoutX(rand(0, 400));
        seaweedImageView.setLayoutY(1);
        seaweedImageView.setFitHeight(50);
        seaweedImageView.setFitWidth(50);
        return seaweedImageView;
    }

    private ImageView createGold() {
        Image gold = new Image(getClass().getResourceAsStream("/gold.jpg"));
        ImageView goldImageView = new ImageView(gold);
        goldImageView.setLayoutX(rand(0, 400));
        goldImageView.setLayoutY(1);
        goldImageView.setFitHeight(50);
        goldImageView.setFitWidth(50);
        return goldImageView;
    }

    private ImageView createVapour() {
        Image vapour = new Image(getClass().getResourceAsStream("/vapour.jpg"));
        ImageView vapourImageView = new ImageView(vapour);
        vapourImageView.setLayoutX(rand(0, 400));
        vapourImageView.setLayoutY(1);
        vapourImageView.setFitHeight(50);
        vapourImageView.setFitWidth(50);
        return vapourImageView;
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
            ImageView seaweed = drop.get(i);
            seaweed.setLayoutY(seaweed.getLayoutY() + speed + seaweed.getLayoutY() / 150);

            if (seaweed.getLayoutX() > cont.getLayoutX() && seaweed.getLayoutX() < cont.getLayoutX() + 70 &&
                    seaweed.getLayoutY() >= 550) {
                root.getChildren().remove(seaweed);
                drop.remove(i);
            } else if (seaweed.getLayoutY() >= 590) {
                root.getChildren().remove(seaweed);
                drop.remove(i);
                missed += 1;
                lblMissed.setText("Missed: " + missed);
            }
        }
    }
}
