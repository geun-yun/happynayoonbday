package src.main.java;

import LovelyWordSearchGame.LovelyWordSearchGenerator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Na extends src.main.java.AbstractGameScene {
    private BoardDisplay boardDisplay;
    public Na(src.main.java.Main main) {
        super(main, 1000, 600);
        setInstructionText(
                "게임명: 나는 나윤이다\n\n" +
                        "나윤이를 묘사하는 단어들을 찾아서 애정도 104%를 충족시켜봐용.\n" +
                        "단어를 찾을 떄마다 철자 길이의 2.5배만큼 애정도가 증가합니다!\n" +
                        "철자 순서대로 버튼을 눌러야 단어로 인정이 됩니당.\n" +
                        "예시: (김나윤: 김 -> 나 -> 윤)\n" +
                        "\n아, 그리고 한 철자가 단어 두개에 중복 될 일은 없게 설계됬지만,\n" +
                        "중복 철자가 있으면 심각한 버그니까,\n" +
                        "즉시 남친한테 연락 주세요!!!",
                120, 160);
        setBackGroundAsset("/na_background.png", "assets/na_bgm.mp3");
        createPoints("/na_points.png",1);
    }

    @Override
    public void displayGame() {
        boardDisplay = new BoardDisplay(14, 3);
        root.getChildren().add(boardDisplay.getLayout());
    }

    public class BoardDisplay implements EventHandler<ActionEvent> {

        private HBox layout; // The main layout container
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

            layout = new HBox(30);
            gridPane = new GridPane();
            layout.setAlignment(Pos.CENTER);
            layout.setPadding(new Insets(10, 0, 10, 0));
            layout.setLayoutX(175);
            layout.setLayoutY(60);
            buildGrid();
        }

        public void buildGrid() {
            LovelyWordSearchGenerator ws = new LovelyWordSearchGenerator(3, 14);
            ws.setUpGuaranteed();
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
            double btnSize = 30;
            double fontSize = 13;
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    int n = r.nextInt(26);
                    Button btn;
                    if (board[i][j] == null)
                        btn = new Button(randLetters[n]);
                    else
                        btn = new Button(board[i][j]);
                    btn.setOnAction(this);
                    btn.setFont(new Font(fontSize));
                    btn.setPrefSize(btnSize,btnSize);
                    btn.setMinSize(btnSize, btnSize);
                    btn.setMaxSize(btnSize, btnSize);
                    gridPane.add(btn, j, i);
                    btnArr.add(btn);
                }
            }

            VBox bottomPanel = new VBox(30);
            bottomPanel.setAlignment(Pos.CENTER);
            bottomPanel.setPadding(new Insets(0, 0, 0, 20));
            for (int i = 0; i < numOfWords; i++) {
                labels[i] = new Label(wordList.get(i));
                labels[i].setFont(new Font("Arial", 30)); // Increase font size
                labels[i].setTextFill(Color.HOTPINK); // Change font color
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
                btn.setStyle("-fx-background-color: pink;"); // Example style for selected button
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
                src.main.java.GlobalState.getInstance().addPoints(1,word.length() * 2.5);
                updatePoints(1);
            }

            if (wordsToFind.isEmpty()) {
                resetGame();
                showAlert("역시 뇌섹녀!", "역시 뇌도 섹시한 내 여친!");

            }
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

        public HBox getLayout() {
            return layout;
        }

        // Additional methods if needed for game logic
    }

}