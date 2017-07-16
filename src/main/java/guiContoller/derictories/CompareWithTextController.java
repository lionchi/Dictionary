package guiContoller.derictories;

import guiContoller.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.io.IOException;

/**
 * Created by gavri on 15.07.2017.
 */
public class CompareWithTextController implements IController {
    @FXML
    Button compare;
    ProgressBar pb = new ProgressBar();

    @FXML
    private void initialize() throws IOException {
        pb.setVisible(false);
    }

    @FXML
    public void onCompare()
    {
        pb.setVisible(true);
    }
}
