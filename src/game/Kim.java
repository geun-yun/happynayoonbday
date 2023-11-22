package src.game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import src.Main;

public class Kim extends AbstractGameScene{
    private Button back_button;
    private Main main;
    public Scene kim_scene;
    public Kim(Main main) {
        super(main, 1000, 600);
    }

    @Override
    public void initialize() {

    }
}
