package guiContoller.derictories;

import guiContoller.IController;
import guiContoller.MainController;
import hibernate.HibernateSessionFactory;
import hibernate.derictories.CategoryEntity;
import hibernate.derictories.MaintableEntity;
import hibernate.derictories.PartspeechEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by gavri on 15.07.2017.
 */
public class AlterController implements IController {


    @FXML
    private TextField englishWord;
    @FXML
    private TextField translation;
    @FXML
    private ComboBox<PartspeechEntity> partOfSpeechs;
    @FXML
    private ComboBox<CategoryEntity> categories;

    private ObservableList<PartspeechEntity> partSpeechModels = FXCollections.observableArrayList();
    private ObservableList<CategoryEntity> categoryModels = FXCollections.observableArrayList();
    private Stage thisStage;
    private MainController parentContoller;
    private MaintableEntity selectMainTable;

    @FXML
    private void initialize() throws IOException {
        comboboxConfiguration();
    }

    private void comboboxConfiguration() {
        Session session = HibernateSessionFactory.getSession();
        List<PartspeechEntity> list = session.createQuery("from PartspeechEntity ", PartspeechEntity.class)
                .getResultList();
        partSpeechModels.addAll(list);
        partOfSpeechs.setItems(partSpeechModels);

        List<CategoryEntity> newList = session.createQuery("from CategoryEntity ", CategoryEntity.class)
                .getResultList();
        categoryModels.addAll(newList);
        categories.setItems(categoryModels);
        session.close();
    }

    public void OnSave(ActionEvent actionEvent) {
        if(checkRu(translation.getText()) && checkEu(englishWord.getText())) {
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            selectMainTable.setWord(englishWord.getText());
            selectMainTable.setTransfer(translation.getText());
            selectMainTable.setPartspeech_idpartspeech(partOfSpeechs.getSelectionModel().getSelectedItem().getIdpartspeech());
            selectMainTable.setCategory_idcategory(categories.getSelectionModel().getSelectedItem().getIdcategory());
            session.update(selectMainTable);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Слово успешно изменено успешно изменен");
            alert.setTitle("OK!");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                thisStage.close();
                parentContoller.update();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный формат слова(или перевода)");
            alert.setTitle("ERORR!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    private boolean checkRu(String text){
        return text.matches("[а-яёА-ЯЁ,\\s]+");
    }

    private boolean checkEu(String text){
        return text.matches("[a-zA-Z,\\s()]+");
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setParentContoller(MainController parentContoller) {
        this.parentContoller = parentContoller;
    }

    public void setSelectMainTable(MaintableEntity selectMainTable) {
        this.selectMainTable = selectMainTable;
        configuration();
    }

    private void configuration() {
        englishWord.setText(selectMainTable.getWordP());
        translation.setText(selectMainTable.getTrasferP());
        Session session = HibernateSessionFactory.getSession();
        PartspeechEntity currentPartspeech =  session.createQuery("from PartspeechEntity where idpartspeech=:idpartspeech",PartspeechEntity.class).setParameter("idpartspeech",selectMainTable.getPartspeech_idpartspeech()).getSingleResult();
        partOfSpeechs.getSelectionModel().select(currentPartspeech);
        CategoryEntity currentCategory =  session.createQuery("from CategoryEntity where idcategory=:idcategory",CategoryEntity.class).setParameter("idcategory",selectMainTable.getCategory_idcategory()).getSingleResult();
        categories.getSelectionModel().select(currentCategory);
        session.close();
    }
}
