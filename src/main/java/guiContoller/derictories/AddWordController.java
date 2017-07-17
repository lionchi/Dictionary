package guiContoller.derictories;

import guiContoller.IController;
import guiContoller.MainController;
import hibernate.HibernateSessionFactory;
import hibernate.IModel;
import hibernate.derictories.CategoryEntity;
import hibernate.derictories.MaintableEntity;
import hibernate.derictories.PartspeechEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by gavri on 15.07.2017.
 */
public class AddWordController implements IController {
    @FXML private TextField englishWord;
    @FXML private TextField translation;
    @FXML private ComboBox<PartspeechEntity> partOfSpeechs;
    @FXML private ComboBox<CategoryEntity> categorys;

    private ObservableList<PartspeechEntity> partSpeechModels = FXCollections.observableArrayList();
    private ObservableList<CategoryEntity> categoryModels = FXCollections.observableArrayList();
    private Stage thisStage;
    private MainController parentController;

    @FXML
    private void initialize() throws IOException {
        comboboxConfiguration();
    }

    private void comboboxConfiguration() {
        Session session = HibernateSessionFactory.getSession();
        List<? extends IModel> list = session.createQuery("from PartspeechEntity", PartspeechEntity.class)
                .getResultList();
        partSpeechModels.addAll((Collection<? extends PartspeechEntity>) list);
        partOfSpeechs.setItems(partSpeechModels);
        list.clear();
        list = session.createQuery("from CategoryEntity ", CategoryEntity.class)
                .getResultList();
        categoryModels.addAll((Collection<? extends CategoryEntity>) list);
        categorys.setItems(categoryModels);
        session.close();
    }

    @FXML
    private void OnAdd(){
        if(!englishWord.getText().equals("") && !translation.getText().equals("") && partOfSpeechs.getValue()!=null && categorys.getValue()!=null &&
                !partOfSpeechs.getValue().getNamePart().equals("Нет данных") && !categorys.getValue().getNameCategory().equals("Удалить") && checkRu(translation.getText()) &&
                checkEu(englishWord.getText())){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaintableEntity maintableEntity = new MaintableEntity();
            maintableEntity.setWord(englishWord.getText());
            maintableEntity.setTransfer(translation.getText());
            maintableEntity.setPartspeech_idpartspeech(partOfSpeechs.getValue().getIdpartspeech());
            maintableEntity.setCategory_idcategory(categorys.getValue().getIdcategory());
            session.save(maintableEntity);
            session.getTransaction().commit();
            session.close();
            thisStage.close();
            parentController.update();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неправильно указаны данные");
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


    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }
}
