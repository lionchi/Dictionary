package guiContoller.derictories;

import MainFolder.Main;
import guiContoller.IController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Created by gavri on 22.07.2017.
 */
public class TestResult implements IController {

    @FXML
    private ImageView image_results;
    @FXML
    private Label  word;
    @FXML
    private Label questionsCount;
    private Stage thisStage;
    private double mark;
    private int count;
    private int numberSize;


    private void initWindow() {
        questionsCount.setText(String.format("Правильных ответов %d из %d",count,numberSize));
        if(0.0<=mark && mark<=0.25){
            initLableAndImage("ПЛОХО","image/bad.jpg");
        }
        else if(0.26<=mark && mark<=0.5){
            initLableAndImage("НЕПЛОХО","image/notbad.jpg");
        }
        else if(0.51<=mark && mark<=0.75){
            initLableAndImage("ХОРОШО","image/good.jpg");
        }
        else if(0.76<=mark && mark<=0.99){
            initLableAndImage("ОТЛИЧНО","image/better.jpg");
        }
        else if(mark==1){
            initLableAndImage("ВЕЛЕКОЛЕПНО","image/perfect.png");
        }
        else {
            initLableAndImage("ЧТО-ТО ПОШЛО НЕ ТАК","image/error.jpg");
        }
    }

    private void initLableAndImage(String text,String path){
        word.setText(text);
        URL url = Main.class.getClassLoader().getResource(path);
        image_results.setImage(new Image(url.toString()));
    }

    @FXML
    private void OnClose(){
        thisStage.close();
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setNumberSize(int numberSize) {
        this.numberSize = numberSize;
        initWindow();
    }
}
