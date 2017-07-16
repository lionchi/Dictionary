package guiContoller.derictories;

import guiContoller.IController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

/**
 * Created by gavri on 15.07.2017.
 */
public class TestController implements IController {

    @FXML
    Button next;
    @FXML
    ProgressBar pb = new ProgressBar(0);
    DoubleProperty progress = new SimpleDoubleProperty(0.0);

    @FXML
    public void onNext(){
        pb.progressProperty().bind(progress);
        progress.setValue(progress.getValue()+0.2);
    }




}
