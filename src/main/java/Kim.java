package src.main.java;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private ImageView cont;
    private double speed;
    private double falling;
    private boolean isPaused = false;
    private Timeline timeline;

    public Kim(src.main.java.Main main) {
        super(main, 1000, 600);
        setInstructionText(
                "게임명: 김 김 김\n" +
                        "'김'은 마치 나윤이의 좋은 점이 너무 많은 것 처럼\n" +
                        "뜻을 아주 많이 갖고 있는 단어이죠 :)\n" +
                        "이제부터 마우스로 카트를 조종해서\n" +
                        "김을 질리도록 카트에 담을거에용.\n" +
                        "애정도 104%를 채워야 하는데,\n" +
                        "어떤 김을 담으면 좋을까요?\n" +
                        "\n1. 골드 김씨 (+2.5*2.5 점, 드랍 확률 15%)\n" +
                        "2. 식용 김씨 (+2.5 점, 드랍 확률 50%)\n" +
                        "3. 김새는 김씨 (-10.4 점, 드랍 확률 35%)\n" +
                        "\n아참, 그리고 S키로 일시정지도 할 수 있어요!\n" +
                        "오른쪽 하단 버튼으로는 시작 페이지로 갈 수 있고요!",
                180, 60);
        setBackGroundAsset("/kim_background.png", "assets/kim_bgm.mp3");
        createPoints("/kim_points.png",0);
        setupKeyHandling();
    }

    public void displayGame() {
        speed = 1;
        falling = 500;

        timeline = new Timeline(new KeyFrame(Duration.millis(falling), event -> {
            speed += falling / 10400;
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

        cont = new ImageView("/lovely_kart.png");
        cont.setLayoutX(350);
        cont.setLayoutY(520);
        cont.setFitHeight(80);
        cont.setFitWidth(100);
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
        Image seaweed = new Image(getClass().getResourceAsStream("/seaweed.png"));
        ImageView seaweedImageView = new ImageView(seaweed);
        seaweedImageView.setLayoutX(rand(200, 700));
        seaweedImageView.setLayoutY(1);
        seaweedImageView.setFitHeight(70);
        seaweedImageView.setFitWidth(70);

        seaweedImageView.getProperties().put("type", "seaweed");
        return seaweedImageView;
    }

    private ImageView createGold() {
        Image gold = new Image(getClass().getResourceAsStream("/gold.png"));
        ImageView goldImageView = new ImageView(gold);
        goldImageView.setLayoutX(rand(200, 700));
        goldImageView.setLayoutY(1);
        goldImageView.setFitHeight(70);
        goldImageView.setFitWidth(70);

        goldImageView.getProperties().put("type", "gold");
        return goldImageView;
    }

    private ImageView createVapour() {
        Image vapour = new Image(getClass().getResourceAsStream("/vapour.png"));
        ImageView vapourImageView = new ImageView(vapour);
        vapourImageView.setLayoutX(rand(200, 700));
        vapourImageView.setLayoutY(1);
        vapourImageView.setFitHeight(70);
        vapourImageView.setFitWidth(70);

        vapourImageView.getProperties().put("type", "vapour");
        return vapourImageView;
    }
    private int rand(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private void gameUpdate() {
        cont.setLayoutX(mouseX);

        for (int i = 0; i < drop.size(); i++) {
            ImageView image = drop.get(i);
            image.setLayoutY(image.getLayoutY() + speed + image.getLayoutY() / 150);

            if (image.getLayoutX() > cont.getLayoutX() && image.getLayoutX() < cont.getLayoutX() + 90 &&
                    image.getLayoutY() >= 530) {
                root.getChildren().remove(image);
                drop.remove(i);

                String type = (String) image.getProperties().get("type");
                if (type.equals("seaweed")) {
                    src.main.java.GlobalState.getInstance().addPoints(0,2.5);
                } else if (type.equals("gold")) {
                    src.main.java.GlobalState.getInstance().addPoints(0,2.5*2.5);
                } else if (type.equals("vapour")) {
                    src.main.java.GlobalState.getInstance().addPoints(0,-10.4);
                }
                updatePoints(0);
                
            } else if (image.getLayoutY() >= 580) {
                root.getChildren().remove(image);
                drop.remove(i);
            }
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            mediaPlayer.pause();
            timer.stop(); // Stop the AnimationTimer
            timeline.pause(); // Pause the Timeline for falling objects
        } else {
            mediaPlayer.play();
            timer.start(); // Start the AnimationTimer
            timeline.play(); // Resume the Timeline
        }
    }

    // Override the onKeyPressed method
    @Override
    protected void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.S) {
            togglePause();
        }
    }
}
