package src.game;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import src.Main;

public class Na extends AbstractGameScene{
    private Button back_button;
    private Main main;
    public Scene kim_scene;
    public Na(Main main) {
        super(main, 1000, 600);
    }

    @Override
    public void initialize() {
        System.out.println("Game Na started");
    }
}