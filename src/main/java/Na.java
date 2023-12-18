package src.main.java;

import WordSearchGame.WordsearchGenerator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;


import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Na extends src.main.java.AbstractGameScene {
//    private Button back_button;
//    private src.main.java.Main main;
//    public Scene kim_scene;
    private BoardDisplay boardDisplay;
    public Na(src.main.java.Main main) {
        super(main, 1000, 600);
        setInstructionText("Na Na Na");
    }

    @Override
    public void displayGame() {
        boardDisplay = new BoardDisplay(15, 5);
        root.getChildren().add(boardDisplay.getLayout());
    }

    public class BoardDisplay implements EventHandler<ActionEvent> {

        private VBox layout; // The main layout container
        private GridPane gridPane; // Grid for the word search buttons
        private Label[] labels; // Labels for words
        private int length; // Length of the wordsearch
        private ArrayList<Button> btnArr = new ArrayList<>(); // JavaFX Buttons
        private ArrayList<Button> selectedPositions = new ArrayList<>(); // Selected buttons
        private boolean letterVerticalOrientation;
        private ArrayList<String> wordsToFind = new ArrayList<>(); // Words left to find
        private ArrayList<String> wordList = new ArrayList<>(); // All words in wordsearch
        private String word = ""; // Formed word
        private int numOfWords;

        public BoardDisplay(int length, int numOfWords) {
            this.length = length;
            this.numOfWords = numOfWords;
            this.labels = new Label[numOfWords];

            layout = new VBox(10);
            gridPane = new GridPane();
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(10, 0, 10, 0));
            layout.setLayoutX(200);
            layout.setLayoutY(50);
            buildGrid();
        }

        public void buildGrid() {
            WordsearchGenerator ws = new WordsearchGenerator(5, 15);
            ws.setUp();
            String[][] board = ws.getBoard();
            wordList = ws.getListOfWords();
            wordsToFind = new ArrayList<>(wordList);

            gridPane.setPadding(new Insets(3));
            gridPane.setHgap(3);
            gridPane.setVgap(3);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setPadding(new Insets(0, 0, 20, 0));

            String[] randLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            Random r = new Random();
            double btnSize = 25;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    int n = r.nextInt(26);
                    Button btn;
                    if (board[i][j] == null)
                        btn = new Button(randLetters[n]);
                    else
                        btn = new Button(board[i][j]);
                    btn.setOnAction(this);
                    btn.setFont(new Font(10));
                    btn.setPrefSize(btnSize,btnSize);
                    btn.setMinSize(btnSize, btnSize);
                    btn.setMaxSize(btnSize, btnSize);
                    gridPane.add(btn, j, i);
                    btnArr.add(btn);
                }
            }

            HBox bottomPanel = new HBox(10);
            bottomPanel.setAlignment(Pos.CENTER);
            bottomPanel.setPadding(new Insets(20));
            for (int i = 0; i < numOfWords; i++) {
                labels[i] = new Label(wordList.get(i));
                bottomPanel.getChildren().add(labels[i]);
            }

            layout.getChildren().addAll(gridPane, bottomPanel);
        }

        @Override
        public void handle(ActionEvent event) {
            Button btn = (Button) event.getSource();
            toggleButtonSelection(btn);
            updateWord();
            checkMatch();
        }

        private void toggleButtonSelection(Button btn) {
            if (selectedPositions.contains(btn)) {
                selectedPositions.remove(btn);
                if (!btn.isDisabled()) { // Only reset style if button is not part of a found word
                    btn.setStyle(""); // Reset style
                }
            } else {
                selectedPositions.add(btn);
                btn.setStyle("-fx-background-color: yellow;"); // Example style for selected button
            }
        }

        private void updateWord() {
            // Reconstruct the word based on the selected buttons
            word = selectedPositions.stream()
                    .map(Button::getText)
                    .collect(Collectors.joining());
        }

        public void checkMatch() {
            if (wordsToFind.contains(word)) {
                int index = wordList.indexOf(word);
                wordsToFind.remove(word);
                labels[index].setTextFill(Color.GRAY); // Mark word as found

                for (Button btn : selectedPositions) {
                    btn.setDisable(true); // Disable buttons that formed the word
                    btn.setStyle("-fx-background-color: lightgray;"); // Mark as found
                }
                clearSelectedBtns();
                src.main.java.GlobalState.getInstance().addPoints(word.length() * 2.5);
                updatePoints();
            }

            if (wordsToFind.isEmpty()) {
                showAlert("You Win!", "Congratulations, you found all the words!");
                resetGame();
            }
        }

        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        private void resetGame() {
            // Reset game logic
            // Clear current state and rebuild grid
            btnArr.clear();
            selectedPositions.clear();
            word = "";
            layout.getChildren().clear();
            gridPane.getChildren().clear();
            buildGrid();
        }

        public void clearSelectedBtns() {
            selectedPositions.forEach(btn -> btn.setStyle("")); // Reset style
            selectedPositions.clear();
        }

        public VBox getLayout() {
            return layout;
        }

        // Additional methods if needed for game logic
    }

}