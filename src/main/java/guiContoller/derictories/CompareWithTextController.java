package guiContoller.derictories;

import Helper.IFileManager;
import com.sun.javafx.scene.control.skin.FXVK;
import guiContoller.IController;
import guiContoller.MainController;
import hibernate.HibernateSessionFactory;
import hibernate.IModel;
import hibernate.derictories.CategoryEntity;
import hibernate.derictories.MaintableEntity;
import hibernate.derictories.PartspeechEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.TypeVariable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gavri on 15.07.2017.
 */
public class CompareWithTextController implements IController, IFileManager {
    @FXML
    private ComboBox<PartspeechEntity> partOfSpeachs;
    @FXML
    private ComboBox<CategoryEntity> categories;
    @FXML
    private TextField translation;
    @FXML
    private TableView<Word> newWordTable;
    @FXML
    private TableColumn<Word,String> wordColumn;
    @FXML
    private TextField pathOfFile;
    @FXML
    private ProgressBar pb = new ProgressBar();

    private ObservableList<PartspeechEntity> partSpeechModels = FXCollections.observableArrayList();
    private ObservableList<CategoryEntity> categoryModels = FXCollections.observableArrayList();
    private ObservableList<Word> newWordTableModels = FXCollections.observableArrayList();
    private List<String> contents = new ArrayList<>();
    private Stage thisStage;
    private MainController parentController;

    @FXML
    private void initialize() throws IOException {
        pb.setVisible(false);
        comboboxConfiguration();
    }

    private void  comboboxConfiguration() {
        Session session = HibernateSessionFactory.getSession();
        List<? extends IModel> list = session.createQuery("from PartspeechEntity", PartspeechEntity.class)
                .getResultList();
        partSpeechModels.addAll((Collection<? extends PartspeechEntity>) list);
        partOfSpeachs.setItems(partSpeechModels);
        list.clear();
        list = session.createQuery("from CategoryEntity ", CategoryEntity.class)
                .getResultList();
        categoryModels.addAll((Collection<? extends CategoryEntity>) list);
        categories.setItems(categoryModels);
        session.close();
    }

    @FXML
    private void OnCompare() {
        pb.setVisible(true);
        Session session = HibernateSessionFactory.getSession();
        List<MaintableEntity> list = session.createQuery("from MaintableEntity",MaintableEntity.class).getResultList();
        session.close();
        boolean flag;
        for (String content:contents) {
            flag = true;
            for (MaintableEntity obj : list) {
                if(obj.getWord().toLowerCase().equals(content)){
                    flag=false;
                    break;
                }
            }
            if(flag){
                newWordTableModels.add(new Word(content));
            }
        }
        wordColumn.setCellValueFactory(param -> param.getValue().ContentWordPProperty());
        newWordTable.setItems(newWordTableModels);
        newWordTable.setColumnResizePolicy(param -> false);
        pb.setVisible(false);
    }

    @FXML
    private void OnOverView(ActionEvent actionEvent) throws IOException {
        getPath();
    }

    @FXML
    private void OnAdd(ActionEvent actionEvent) {
        if(newWordTable.getSelectionModel().getSelectedItem()!=null && !translation.getText().equals("") && partOfSpeachs.getValue()!=null && categories.getValue()!=null &&
                !partOfSpeachs.getValue().getNamePart().equals("Нет данных") && !categories.getValue().getNameCategory().equals("Удалить") && checkRu(translation.getText())){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaintableEntity maintableEntity = new MaintableEntity();
            maintableEntity.setWord(newWordTable.getSelectionModel().getSelectedItem().contentWord);
            maintableEntity.setTransfer(translation.getText());
            maintableEntity.setPartspeech_idpartspeech(partOfSpeachs.getValue().getIdpartspeech());
            maintableEntity.setCategory_idcategory(categories.getValue().getIdcategory());
            session.save(maintableEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Слово успешно добавлено");
            alert.setTitle("OK!");
            alert.setHeaderText(null);
            alert.showAndWait();
            newWordTable.getItems().remove(newWordTable.getSelectionModel().getSelectedItem());
            translation.setText("");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Неправильно указаны данные");
            alert.setTitle("ERORR!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @FXML
    private void OnClose(){
        thisStage.close();
        parentController.update();
    }

    @Override
    public void getPath() throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Текстовый файл (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(filter);
        File filePath = fileChooser.showOpenDialog(null);
        pathOfFile.setText(filePath.getAbsolutePath());
        pathOfFile.setDisable(true);
        if(filePath!=null){
            Files.lines(Paths.get(filePath.getAbsolutePath()), StandardCharsets.UTF_8).forEach(s -> delimetr(s));
        }
    }

    private void delimetr(String str){
        String[] strings = str.split("[,\\s?!.:;()*]");
        for (String s : strings) {
            if(!s.isEmpty() && !contents.contains(s.toLowerCase()) && !s.matches("[\\d]+")) {
                contents.add(s.toLowerCase());
            }
        }
    }

    private boolean checkRu(String text){
        return text.matches("[а-яёА-ЯЁ,\\s]+");
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    class Word{
        private String contentWord;

        private StringProperty contentWordP = new SimpleStringProperty();

        public String getContentWordP(){return  contentWordP.get();}

        public StringProperty ContentWordPProperty(){return contentWordP;}

        public void setContentWordP(String contentWordP){this.contentWordP.set(contentWordP);}

        public Word(String contentWord) {
            this.contentWord = contentWord;
            setContentWordP(contentWord);
        }

        public String getContentWord() {
            return contentWord;
        }

        public void setContentWord(String contentWord) {
            this.contentWord = contentWord;
        }
    }
}
