package guiContoller;

import Helper.GuiForm;
import guiContoller.derictories.*;
import hibernate.HibernateSessionFactory;
import hibernate.derictories.CategoryEntity;
import hibernate.derictories.PartspeechEntity;
import hibernate.views.FullmaintableEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements IController {
    @FXML
    private TextField translation;
    @FXML
    private TextField englishWord;
    @FXML
    private Label add_word;
    @FXML
    private Label alter_word;
    @FXML
    private Label delete_word;
    @FXML
    private Label add_category;
    @FXML
    private Label compare_with_text;
    @FXML
    private Label test;
    @FXML
    private Label print;
    @FXML
    private TableView<FullmaintableEntity> mainTable;
    @FXML
    private TableColumn<FullmaintableEntity, String> wordColumn;
    @FXML
    private TableColumn<FullmaintableEntity, String> transferColumn;
    @FXML
    private TableColumn<FullmaintableEntity, String> partspeech_idpartspeechColumn;
    @FXML
    private TableColumn<FullmaintableEntity, String> category_idcategoryColumn;
    @FXML
    private ComboBox<PartspeechEntity> partOfSpeechs;
    @FXML
    private ComboBox<CategoryEntity> categorys;

    private ObservableList<FullmaintableEntity> mainTableModels = FXCollections.observableArrayList();
    private ObservableList<PartspeechEntity> partSpeechModels = FXCollections.observableArrayList();
    private ObservableList<CategoryEntity> categoryModels = FXCollections.observableArrayList();
    private Stage primaryStage;
    private Stage popUpStage = new Stage();

    @FXML
    private void initialize() throws IOException {
        add_word.setOnMouseClicked(event -> addWordClick());
        alter_word.setOnMouseClicked(event -> alterWordClick());
        delete_word.setOnMouseClicked(event -> deleteWordClick());
        add_category.setOnMouseClicked(event -> addCategoryClick());
        compare_with_text.setOnMouseClicked(event -> compareWithTextClick());
        test.setOnMouseClicked(event -> testClick());
        print.setOnMouseClicked(event -> printClick());
        translation.textProperty().addListener((observable, oldValue, newValue) -> ClickTranslation());
        englishWord.textProperty().addListener((observable, oldValue, newValue) -> ClickEnglishWord());
        partOfSpeechs.setOnAction(event -> OnPartOfSpeech());
        categorys.setOnAction(event -> OnCategory());
        Session session = HibernateSessionFactory.getSession();
        mainTableModels.addAll(session.createQuery("from FullmaintableEntity", FullmaintableEntity.class)
                .getResultList());
        session.close();
        tableConfiguration();
        comboboxConfiguration();
    }

    private void tableConfiguration() {
        wordColumn.setCellValueFactory(cellData -> cellData.getValue().WordPProperty());
        transferColumn.setCellValueFactory(cellData -> cellData.getValue().TransferPProperty());
        partspeech_idpartspeechColumn.setCellValueFactory(cellData -> cellData.getValue().NamePartPProperty());
        category_idcategoryColumn.setCellValueFactory(cellData -> cellData.getValue().NameCategoryPProperty());
        mainTable.setItems(mainTableModels);
        mainTable.setColumnResizePolicy(param -> false);
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
        categorys.setItems(categoryModels);
        session.close();
    }

    public void OnSearch(ActionEvent actionEvent) {

    }

    private void addWordClick() {
        GuiForm<AnchorPane, AddWordController> form = new GuiForm<>("add_word.fxml");
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Добавление слов");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    private void alterWordClick() {
        GuiForm<AnchorPane, AlterController> form = new GuiForm<>("alter.fxml");
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Редактор слов");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    private void deleteWordClick() {
        //Здесь сразу удаляем слово
    }

    private void addCategoryClick() {
        GuiForm<AnchorPane, AddCategoryController> form = new GuiForm<>("add_category.fxml");
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Добавление категорий");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    private void compareWithTextClick() {
        GuiForm<AnchorPane, CompareWithTextController> form = new GuiForm<>("compare_with_text.fxml");
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Сравнение с текстом");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    private void testClick() {
        GuiForm<AnchorPane, TestController> form = new GuiForm<>("test.fxml");
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Тест");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    private void printClick() {
        //здесь будет работа с джава репорт
    }

    private void OnCategory(){
        if (categorys.getValue().getNameCategory().equals("Удалить")) {
            translation.setDisable(false);
            englishWord.setDisable(false);
            partOfSpeechs.setDisable(false);

        } else if (categorys.getSelectionModel().getSelectedIndex() > -1) {
            translation.setDisable(true);
            englishWord.setDisable(true);
            partOfSpeechs.setDisable(true);
        }
    }

    private void OnPartOfSpeech() {
        if (partOfSpeechs.getValue().getNamePart().equals("Нет данных")) {
            translation.setDisable(false);
            englishWord.setDisable(false);
            categorys.setDisable(false);

        } else if (partOfSpeechs.getSelectionModel().getSelectedIndex() > -1) {
            translation.setDisable(true);
            englishWord.setDisable(true);
            categorys.setDisable(true);
        }
    }

    private void ClickEnglishWord() {
        if (englishWord.getText().equals("")) {
            translation.setDisable(false);
            categorys.setDisable(false);
            partOfSpeechs.setDisable(false);

        } else {
            translation.setText("");
            translation.setDisable(true);
            categorys.setDisable(true);
            partOfSpeechs.setDisable(true);
        }
    }

    private void ClickTranslation() {
        if (translation.getText().equals("")) {
            englishWord.setDisable(false);
            categorys.setDisable(false);
            partOfSpeechs.setDisable(false);
        } else {
            englishWord.setText("");
            englishWord.setDisable(true);
            categorys.setDisable(true);
            partOfSpeechs.setDisable(true);
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


}
