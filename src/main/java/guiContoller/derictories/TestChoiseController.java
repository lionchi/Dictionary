package guiContoller.derictories;

import Helper.GuiForm;
import Helper.Test;
import guiContoller.IController;
import hibernate.HibernateSessionFactory;
import hibernate.derictories.CategoryEntity;
import hibernate.derictories.MaintableEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by gavri on 15.07.2017.
 */
public class TestChoiseController implements IController {


    @FXML
    private ComboBox<Test> selectedTests;
    @FXML
    private ComboBox<CategoryEntity> selectedCategories;

    private ObservableList<CategoryEntity> categoryModels = FXCollections.observableArrayList();
    private ObservableList<Test> testModels = FXCollections.observableArrayList();
    private List<String> questions = new ArrayList<>();
    private List<String> answers = new ArrayList<>();
    private Stage thisStage;

    @FXML
    private void initialize() throws IOException {
        comboboxConfiguration();
    }

    private void comboboxConfiguration() {
        Session session = HibernateSessionFactory.getSession();
        List<CategoryEntity> newList = session.createQuery("from CategoryEntity ", CategoryEntity.class)
                .getResultList();
        categoryModels.addAll(newList);
        selectedCategories.setItems(categoryModels);
        session.close();

        testModels.addAll(Test.values());
        selectedTests.setItems(testModels);
    }


    @FXML
    private void OnStartTest(ActionEvent actionEvent) {
        CategoryEntity nameDCategory = selectedCategories.getSelectionModel().getSelectedItem();
        Test test = selectedTests.getValue();
        if((nameDCategory!=null||nameDCategory.getNameCategory().equals("Удалить"))&& (test!=null && !test.getNameTest().equals("Нет данных"))) {
            initContentTest(nameDCategory,test);
            Stage popUpStage = new Stage(StageStyle.UTILITY);
            GuiForm<AnchorPane, TestController> form = new GuiForm<>("test.fxml");
            AnchorPane pane = form.getParent();
            TestController testController = form.getController();
            popUpStage.initModality(Modality.WINDOW_MODAL);
            popUpStage.initOwner(selectedCategories.getScene().getWindow());
            popUpStage.setTitle("Тест");
            Scene scene = new Scene(pane);
            popUpStage.setScene(scene);
            testController.setThisStage(popUpStage);
            testController.setQuestions(questions);
            testController.setAnswers(answers);
            popUpStage.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не выбран тест или неверно указана категория");
            alert.showAndWait();
        }
    }

    private void initContentTest(CategoryEntity category, Test test){
        Session session = HibernateSessionFactory.getSession();
        int count = 0;
        answers.clear();
        questions.clear();
        List<MaintableEntity> list;
        if(!category.getNameCategory().equals("Удалить")) {
            list = session.createQuery("from MaintableEntity where category_idcategory=:idcategory", MaintableEntity.class).setParameter("idcategory", category.getIdcategory()).getResultList();
            session.close();
        }
        else{
            list = session.createQuery("from MaintableEntity", MaintableEntity.class).getResultList();
            session.close();
        }
        Collections.shuffle(list);
        if(test.name().equals(Test.С_АНГЛИЙСКОГО_НА_РУССИКЙ.name())){
            for (MaintableEntity obj : list) {
                if(count<=20) {
                    questions.add(obj.getWord().toLowerCase());
                    answers.add(obj.getTransfer().toLowerCase());
                    count++;
                }
            }
        }
        else if (test.name().equals(Test.С_РУССКОГО_НА_АНГЛИЙСКИЙ.name())){
            for (MaintableEntity obj : list) {
                if(count<=20) {
                    questions.add(obj.getTransfer().toLowerCase());
                    answers.add(obj.getWord().toLowerCase());
                    count++;
                }
            }
        }
    }

/*    private <P extends AnchorPane, C extends TestController> void prst(P pane, C contoller,String title){
        Stage popUpStage = new Stage(StageStyle.UTILITY);
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(selectedCategories.getScene().getWindow());
        popUpStage.setTitle(title);
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);

        contoller.setThisStage(popUpStage);
        popUpStage.showAndWait();
    }*/

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
}
