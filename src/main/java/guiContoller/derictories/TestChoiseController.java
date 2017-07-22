package guiContoller.derictories;

import Helper.GuiForm;
import Helper.Test;
import guiContoller.IController;
import hibernate.HibernateSessionFactory;
import hibernate.derictories.CategoryEntity;
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
            popUpStage.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не выбран тест или неверно указана категория");
            alert.showAndWait();
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
