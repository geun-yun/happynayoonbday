package src.main.java;

import WordSearchGame.BoardDisplay;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Na extends src.main.java.AbstractGameScene {
    private Button back_button;
    private src.main.java.Main main;
    public Scene kim_scene;
    public Na(src.main.java.Main main) {
        super(main, 1000, 600);
    }

    @Override
    public void initialize() {
        BoardDisplay game = new BoardDisplay(15, 8);
        game.buildGrid();
    }
}