package src.main.java;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Random;

public class Yoon extends src.main.java.AbstractGameScene {
    private int targetNumber;
    private boolean used1 = false, used0 = false, used4 = false;
    private Button button1, button0, button4;
    private TextField playerInputField;
    private boolean awaitingCloseParenthesis = false;
    private String operationAwaitingInput = "";

    public Yoon(src.main.java.Main main) {
        super(main, 1000, 600);
    }

    @Override
    public void initialize() {
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(createInstructionsText());

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> startGame());

        root.getChildren().addAll(vBox, startButton);
    }

    private Text createInstructionsText() {
        return new Text(
                "윤 -> 굥 (나윤이는 특별하니까 특별히 뒤집음) -> 공 -> 0 -> 숫자 -> 숫자게임" +
                        "Welcome to Yoon!\n" +
                        "Instructions:\n" +
                        "1. A target number will be shown.\n" +
                        "2. You have to try to make that target number by using 1, 0, and 4 exactly once with any mathematical operation.\n" +
                        "3. You can combine numbers (e.g., 10 or 10.4) to create new numbers.\n" +
                        "4. If you match the target number, you earn 2.5 times the target number in points."
        );
    }

    public void onPlayerSubmit(String input) {
        if(isCorrectAnswer(input)) {
            double pointsEarned = 2.5 * targetNumber;
            src.main.java.GlobalState.getInstance().addPoints(pointsEarned);
        } else {

        }
    }

    private void startGame() {
        targetNumber = new Random().nextInt(10) + 1;
        used1 = used0 = used4 = false;
        setupGameplayUI();
    }

    private void setupGameplayUI() {
        root.getChildren().remove(1); // Clear previous UI elements
        root.getChildren().remove(1); // Clear previous UI elements

        Label targetNumberLabel = new Label("Target Number: " + targetNumber);
        playerInputField = new TextField();
        playerInputField.setPromptText("Enter your solution");

        setupNumberButtons();
        setUpOperationButtons();

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> checkSolution());

        HBox hbox = new HBox(10, targetNumberLabel, playerInputField, button1, button0, button4, submitButton);
        hbox.setAlignment(Pos.CENTER);

        root.getChildren().add(hbox);
    }
    private void setUpOperationButtons() {
        // Adding buttons for basic operations
        Button plusButton = new Button("+");
        plusButton.setOnAction(e -> appendToSolution("+"));

        Button minusButton = new Button("-");
        minusButton.setOnAction(e -> appendToSolution("-"));

        Button mulButton = new Button("*");
        mulButton.setOnAction(e -> appendToSolution("*"));

        Button divButton = new Button("/");
        divButton.setOnAction(e -> appendToSolution("/"));

        Button expButton = new Button("^");
        expButton.setOnAction(e -> appendToSolution("^"));

        Button factButton = new Button("!");
        factButton.setOnAction(e -> appendToSolution("!"));

        Button doublefactButton = new Button("!!");
        doublefactButton.setOnAction(e -> appendToSolution("!!"));


        // Adding these buttons to the UI
        HBox operationButtons = new HBox(plusButton, minusButton, mulButton, divButton, expButton, factButton, doublefactButton);
        operationButtons.setAlignment(Pos.CENTER);
        operationButtons.setLayoutY(300);
        operationButtons.setLayoutX(500);

        // Adding operationButtons to the root or another layout
        root.getChildren().add(operationButtons);
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
        button0 = new Button("0");
        button4 = new Button("4");

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
        // TODO: Implement logic to evaluate player's input
        boolean isCorrect = isCorrectAnswer(playerInput);

        if (isCorrect) {
            int pointsEarned = (int)(2.5 * targetNumber);
            src.main.java.GlobalState.getInstance().addPoints(pointsEarned);
            // Display success message and points earned
            System.out.println("Right answer");
        } else {
            // Display failure message
            System.out.println("Wrong answer");
        }
    }

    private void appendToSolution(String number) {
        playerInputField.setText(playerInputField.getText() + number);
    }

    private boolean isCorrectAnswer(String input) {

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
        Double result = eval.evaluate(input);
        System.out.println(Math.round(result));
        return (Math.round(result) == targetNumber);
    }

}
