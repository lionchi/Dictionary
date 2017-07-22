package guiContoller.derictories;

import Helper.GuiForm;
import guiContoller.IController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gavri on 15.07.2017.
 */
public class TestController implements IController {

    @FXML
    private TextField answer;
    @FXML
    private Label questionsCount;
    @FXML
    private Label word;
    @FXML
    private ProgressBar pb = new ProgressBar(0);

    private DoubleProperty progress = new SimpleDoubleProperty(0.0);
    private List<Integer> numbers = new ArrayList<>();
    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private int count = 0;
    private int numberSize = 0;
    private int numberQuestions = 0;
    private Stage thisStage;

    @FXML
    private void initialize() throws IOException {
        pb.progressProperty().bind(progress);
    }

    @FXML
    private void OnNext() {
        numberQuestions++;
        if(answer.getText().toLowerCase().equals(answers.get(0))){
            count++;
        }
        questions.remove(0);
        answers.remove(0);
        numbers.remove(0);
        if(questions.size()==0){
            openResult();
            progress.setValue(progress.getValue() + 1.0/numberSize);
        }
        else {
            progress.setValue(progress.getValue() + 1.0 / numberSize);
            word.setText(questions.get(0));
            questionsCount.setText(String.format("Вопрос %d из %d", numbers.get(0), numberSize));
            answer.setText("");
        }
    }

    @FXML
    private void OnClose(){
        openResult();
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
        initTestViewContent();
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    private void openResult(){
        Stage popUpStage = new Stage(StageStyle.UTILITY);
        GuiForm<AnchorPane, TestResult> form = new GuiForm<>("test_results.fxml");
        AnchorPane pane = form.getParent();
        TestResult testResult = form.getController();
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(thisStage.getOwner());
        popUpStage.setTitle("Тест");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        testResult.setThisStage(popUpStage);
        testResult.setMark((double) count/numberQuestions);
        testResult.setCount(count);
        testResult.setNumberSize(numberSize);
        thisStage.close();
        popUpStage.showAndWait();
    }

    private void initTestViewContent() {
        for (int i = 0; i < questions.size(); i++) {
            numbers.add(i+1);
        }
        numberSize = numbers.size();
        word.setText(questions.get(0));
        questionsCount.setText(String.format("Вопрос %d из %d",numbers.get(0),numberSize));
    }

    public void OnKeyPerssed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            OnNext();
        }
    }
}
