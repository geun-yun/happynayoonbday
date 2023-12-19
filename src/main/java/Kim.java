package src.main.java;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Kim extends src.main.java.AbstractGameScene {
    private AnimationTimer timer;
    private List<ImageView> drop = new ArrayList<>();
    private double mouseX;
    private Rectangle cont;
    private double speed;
    private double falling;

    public Kim(src.main.java.Main main) {
        super(main, 1000, 600);
        setInstructionText("김 김 김");
    }

    public void displayGame() {
        speed = 1;
        falling = 500;

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(falling), event -> {
            speed += falling / 3000;
            ImageView random = createBiasedRandom();
            drop.add(random);
            root.getChildren().add(random);
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
        root.getChildren().addAll(cont);

        scene.setOnMouseMoved(e -> mouseX = e.getX());
    }

    private ImageView createBiasedRandom()  {
        Random random = new Random();
        double probability  = random.nextDouble();

        if (probability < 0.50) {
            return createSeaweed();
        } else if (probability < 0.85) {
            return createVapour();
        } else {
            return createGold();
        }
    }

    private ImageView createSeaweed() {
        Image seaweed = new Image(getClass().getResourceAsStream("/seaweed.jpg"));
        ImageView seaweedImageView = new ImageView(seaweed);
        seaweedImageView.setLayoutX(rand(0, 400));
        seaweedImageView.setLayoutY(1);
        seaweedImageView.setFitHeight(50);
        seaweedImageView.setFitWidth(50);

        seaweedImageView.getProperties().put("type", "seaweed");
        return seaweedImageView;
    }

    private ImageView createGold() {
        Image gold = new Image(getClass().getResourceAsStream("/gold.jpg"));
        ImageView goldImageView = new ImageView(gold);
        goldImageView.setLayoutX(rand(0, 400));
        goldImageView.setLayoutY(1);
        goldImageView.setFitHeight(50);
        goldImageView.setFitWidth(50);

        goldImageView.getProperties().put("type", "gold");
        return goldImageView;
    }

    private ImageView createVapour() {
        Image vapour = new Image(getClass().getResourceAsStream("/vapour.jpg"));
        ImageView vapourImageView = new ImageView(vapour);
        vapourImageView.setLayoutX(rand(0, 400));
        vapourImageView.setLayoutY(1);
        vapourImageView.setFitHeight(50);
        vapourImageView.setFitWidth(50);

        vapourImageView.getProperties().put("type", "vapour");
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
            ImageView image = drop.get(i);
            image.setLayoutY(image.getLayoutY() + speed + image.getLayoutY() / 150);

            if (image.getLayoutX() > cont.getLayoutX() && image.getLayoutX() < cont.getLayoutX() + 70 &&
                    image.getLayoutY() >= 550) {
                root.getChildren().remove(image);
                drop.remove(i);

                String type = (String) image.getProperties().get("type");
                if (type.equals("seaweed")) {
                    src.main.java.GlobalState.getInstance().addPoints(2.5);
                } else if (type.equals("gold")) {
                    src.main.java.GlobalState.getInstance().addPoints(25);
                } else if (type.equals("vapour")) {
                    src.main.java.GlobalState.getInstance().addPoints(-10.4);
                }
                updatePoints();
                
            } else if (image.getLayoutY() >= 590) {
                root.getChildren().remove(image);
                drop.remove(i);
            }
        }
    }
}
