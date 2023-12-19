package src.main.java;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class Yoon extends src.main.java.AbstractGameScene {
    private int targetNumber;
    private boolean used1 = false, used0 = false, used4 = false;
    private Button button1, button0, button4;
    private TextField playerInputField;
    private HBox hbox;
    private Pane heartPane;

    private Button plusButton;
    private Button minusButton;
    private Button mulButton;
    private Button divButton;
    private Button expButton;
    private Button factButton;
    private Button doublefactButton;

    public Yoon(src.main.java.Main main) {
        super(main, 1000, 600);
        setInstructionText(
                "게임명: 윤 -> 굥 -> 공 -> 0 -> 숫자 -> 숫자게임\n\n" +
                        "어쩌다가 '윤'이 '굥'이 되었냐구요?\n" +
                        "그거야 당연히 나윤이는 특별하니까 저도 윤을 특별히 뒤집었죠!\n" +
                        "나윤이의 생일인 만큼 숫자 1, 0, 4를 무조건 한번 씩만 써서,\n" +
                        "타겟 숫자를 (1~9) 만드면 됩니닷.\n" +
                        "주어진 연산기호 버튼만 쓸 수 있고,\n" +
                        "타자로는 정답을 입력 할 수 없어용.\n" +
                        "그러니 버튼으로만 정답을 완성해야겠죠?\n" +
                        "숫자 6은 어떻게 만들지 궁금아네요 :)",
                140, 160);
        setBackGroundAsset("/yoon_background.png", "assets/yoon_bgm.mp3");
        createPoints("/yoon_points.png", 2);
    }
    public void displayGame() {
        targetNumber = new Random().nextInt(9) + 1;
        used1 = used0 = used4 = false;
        setupGameplayUI();
        positionButtonsInHeartShape();
    }

    private void setupGameplayUI() {
        heartPane = new Pane();
        heartPane.setLayoutX(0);
        heartPane.setLayoutY(0);
        heartPane.setPrefSize(1000, 600);

        // Increase the font size for the labels and buttons
        Font largeFont = new Font("Arial", 20); // Example font, adjust as needed

        Label targetNumberLabel = new Label("타겟 숫자: " + targetNumber);
        targetNumberLabel.setTextFill(Color.WHITE);
        targetNumberLabel.setFont(largeFont);

        playerInputField = new TextField();
        playerInputField.setPromptText("정답을 입력하세요");
        playerInputField.setEditable(false);
        playerInputField.setFont(largeFont);
        playerInputField.setPrefHeight(40); // Set preferred height

        // Setup buttons with larger size
        setupNumberButtons();
        setUpOperationButtons();

        Button submitButton = new Button("정답 제출");
        submitButton.setFont(largeFont);
        submitButton.setPrefSize(120, 40); // Set preferred size

        Button clearButton = new Button("지우기");
        clearButton.setFont(largeFont);
        clearButton.setPrefSize(120, 40); // Set preferred size

        hbox = new HBox(20, targetNumberLabel, playerInputField, button1, button0, button4, submitButton, clearButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(15, 15, 15, 15)); // Increase padding
        Platform.runLater(() -> {
            double xPos = 500 - (hbox.getWidth() / 2);
            // Now xPos has the correct value and can be used to set the position
            hbox.setLayoutX(xPos);
        });
        hbox.setLayoutY(200);

        heartPane.getChildren().addAll(button1, button0, button4);
        root.getChildren().addAll(heartPane, hbox);

        back_button.toFront();
    }

    private void positionButtonsInHeartShape() {
        button1.setLayoutX(225); button1.setLayoutY(75);
        button0.setLayoutX(500); button0.setLayoutY(130);
        button4.setLayoutX(775); button4.setLayoutY(75);
        plusButton.setLayoutX(100); plusButton.setLayoutY(225);
        minusButton.setLayoutX(900); minusButton.setLayoutY(225);
        mulButton.setLayoutX(220); mulButton.setLayoutY(350);
        divButton.setLayoutX(780); divButton.setLayoutY(350);
        factButton.setLayoutX(350); factButton.setLayoutY(450);
        doublefactButton.setLayoutX(650); doublefactButton.setLayoutY(450);
        expButton.setLayoutX(500); expButton.setLayoutY(530);
    }

    private void resetTargetNumber() {
        targetNumber = new Random().nextInt(9) + 1;
        clearSolution();
        Label targetNumberLabel = new Label("Target Number: " + targetNumber);
        hbox.getChildren().set(0, targetNumberLabel);
    }
    private void clearSolution() {
        toggleNumber("1", used1 = false, button1);
        toggleNumber("0", used0 = false, button0);
        toggleNumber("4", used4 = false, button4);
        playerInputField.clear();
    }
    private void setUpOperationButtons() {
        // Adding buttons for basic operations
        plusButton = new Button("+");
        plusButton.setOnAction(e -> appendToSolution("+"));
        plusButton.setPrefSize(40,40);
        plusButton.setFont(new Font(21));

        minusButton = new Button("-");
        minusButton.setOnAction(e -> appendToSolution("-"));
        minusButton.setPrefSize(40,40);
        minusButton.setFont(new Font(25));

        mulButton = new Button("*");
        mulButton.setOnAction(e -> appendToSolution("*"));
        mulButton.setPrefSize(40,40);
        mulButton.setFont(new Font(25));

        divButton = new Button("/");
        divButton.setOnAction(e -> appendToSolution("/"));
        divButton.setPrefSize(40,40);
        divButton.setFont(new Font(25));

        expButton = new Button("^");
        expButton.setOnAction(e -> appendToSolution("^"));
        expButton.setPrefSize(40,40);
        expButton.setFont(new Font(21));

        factButton = new Button("!");
        factButton.setOnAction(e -> appendToSolution("!"));
        factButton.setPrefSize(40,40);
        factButton.setFont(new Font(25));

        doublefactButton = new Button("!!");
        doublefactButton.setOnAction(e -> appendToSolution("!!"));
        doublefactButton.setPrefSize(40,40);
        doublefactButton.setFont(new Font(25));

        // Adding operationButtons to the root or another layout
        heartPane.getChildren().addAll(plusButton, minusButton, mulButton, divButton, expButton, factButton, doublefactButton);
    }
    private void toggleNumber(String number, boolean toggle, Button button) {
        if (toggle) {
            appendToSolution(number);
            button.setStyle("-fx-background-color: lightgreen;"); // Indicate the button is toggled on
        } else {
            removeFromSolution(number);
            button.setStyle(""); // Reset the button style
        }
    }
    private void removeFromSolution(String number) {
        String currentText = playerInputField.getText();
        currentText = currentText.replaceFirst(number, ""); // Remove the first occurrence
        playerInputField.setText(currentText);
    }
    private void setupNumberButtons() {
        button1 = new Button("1");
        button1.setPrefSize(40, 40);
        button1.setFont(new Font(25));
        button0 = new Button("0");
        button0.setPrefSize(40, 40);
        button0.setFont(new Font(25));
        button4 = new Button("4");
        button4.setPrefSize(40, 40);
        button4.setFont(new Font(25));

        button1.setOnAction(e -> toggleNumber("1", used1 = !used1, button1));
        button0.setOnAction(e -> toggleNumber("0", used0 = !used0, button0));
        button4.setOnAction(e -> toggleNumber("4", used4 = !used4, button4));

        // Reset the disabled state if restarting the game
        button1.setDisable(false);
        button0.setDisable(false);
        button4.setDisable(false);
    }

    private void checkSolution() {
        String playerInput = playerInputField.getText();
        boolean isCorrect = isCorrectAnswer(playerInput);

        if (isCorrect) {
            int pointsEarned = (int)(2.5 * targetNumber);
            src.main.java.GlobalState.getInstance().addPoints(2,pointsEarned);
            updatePoints(2);
            // Display success message and points earned
            System.out.println("Right answer");
            showAlert("You win!", "Noice");
            resetTargetNumber();
        } else {
            // Display failure message
            System.out.println("Wrong answer");
            showAlert("Rip", "Oops");
        }
    }

    private void appendToSolution(String number) {
        playerInputField.setText(playerInputField.getText() + number);
    }

    private boolean isCorrectAnswer(String input) {

        String check104 = "104";
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) && check104.contains(String.valueOf(c))) {
                check104 = check104.replace(c, ' ');
            } else if (Character.isDigit(c)) {
                return false;
            }
        }

        DoubleEvaluator eval = new DoubleEvaluator();
        for (int i = 0; i < input.length(); i++) {
            if (i > 0 && input.charAt(i) == '!') {
                if (i != input.length() - 1 && input.charAt(i + 1) == '!') {
                    // Double Factorial
                    if (input.charAt(i - 1) == '4') {
                        input = input.replace(input.substring(i-1, i+2), "8");
                    } else {
                        input = input.replace(input.substring(i-1, i+2), "1");
                    }
                } else {
                    // Single Factorial
                    if (input.charAt(i - 1) == '4') {
                        input = input.replace(input.substring(i-1, i+1), "24");
                    } else {
                        input = input.replace(input.substring(i-1, i+1), "1");
                    }
                }
            }
        }
        Double result = -1.0;
        try {
            result = eval.evaluate(input);
        } catch (Exception e) {
            System.out.println("Wrong expression :(");
        }
        return (Math.round(result) == targetNumber);
    }
}
