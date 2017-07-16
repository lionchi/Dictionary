package guiContoller;

import Helper.GuiForm;
import guiContoller.derictories.*;
import hibernate.HibernateSessionFactory;
import hibernate.derictories.CategoryEntity;
import hibernate.derictories.MaintableEntity;
import hibernate.derictories.PartspeechEntity;
import hibernate.views.FullmaintableEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    private FilteredList<FullmaintableEntity> filteredData;
    private ObservableList<PartspeechEntity> partSpeechModels = FXCollections.observableArrayList();
    private ObservableList<CategoryEntity> categoryModels = FXCollections.observableArrayList();
    private Stage primaryStage;

    @FXML
    private void initialize() throws IOException {
        add_word.setOnMouseClicked(event -> addWordClick());
        alter_word.setOnMouseClicked(event -> alterWordClick());
        delete_word.setOnMouseClicked(event -> deleteWordClick());
        add_category.setOnMouseClicked(event -> addCategoryClick());
        compare_with_text.setOnMouseClicked(event -> compareWithTextClick());
        test.setOnMouseClicked(event -> testClick());
        print.setOnMouseClicked(event -> printClick());
        translation.textProperty().addListener((observable, oldValue, newValue) -> ChangeTranslation());
        englishWord.textProperty().addListener((observable, oldValue, newValue) -> ChangeEnglishWord());
        partOfSpeechs.setOnAction(event -> OnPartOfSpeech());
        categorys.setOnAction(event -> OnCategory());
        tableConfiguration();
        comboboxConfiguration();
    }

    private void tableConfiguration() {
        Session session = HibernateSessionFactory.getSession();
        mainTableModels.addAll(session.createQuery("from FullmaintableEntity", FullmaintableEntity.class)
                .getResultList());
        session.close();
        filteredData = new FilteredList<>(mainTableModels, p -> true);
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

    private void addWordClick() {
        Stage popUpStage = new Stage(StageStyle.UTILITY);
        GuiForm<AnchorPane, AddWordController> form = new GuiForm<>("add_word.fxml");
        AnchorPane pane = form.getParent();
        AddWordController controller = form.getController();
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(mainTable.getScene().getWindow());
        popUpStage.setTitle("Добавление слов");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        controller.setThisStage(popUpStage);
        controller.setParentController(this);
        popUpStage.showAndWait();
    }

    private void alterWordClick() {
        if(mainTable.getSelectionModel().getSelectedIndex()<0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите слово для изменения");
            alert.showAndWait();
            return;
        }

        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        MaintableEntity elem = session.createQuery("from MaintableEntity where word = :word", MaintableEntity.class)
                .setParameter("word", mainTable.getSelectionModel().getSelectedItem().getWord())
                .getSingleResult();
        session.close();

        Stage popUpStage = new Stage(StageStyle.UTILITY);
        GuiForm<AnchorPane, AlterController> form = new GuiForm<>("alter.fxml");
        AnchorPane pane = form.getParent();
        AlterController controller = form.getController();
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(mainTable.getScene().getWindow());
        popUpStage.setTitle("Редактор слов");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        controller.setThisStage(popUpStage);
        controller.setParentContoller(this);
        controller.setSelectMainTable(elem);
        popUpStage.showAndWait();

    }

    private void deleteWordClick() {
        Alert alertApproval = new Alert(Alert.AlertType.WARNING,"Вы точно хотите удалить выбранное слово?");
        alertApproval.setTitle("WARNING!");
        alertApproval.setHeaderText(null);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertApproval.getButtonTypes().addAll(buttonTypeCancel);
        Optional<ButtonType> result = alertApproval.showAndWait();
        if(result.get() == ButtonType.OK) {
            int selectedIndex = mainTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите слово для удаления");
                alert.showAndWait();
                return;
            }
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaintableEntity elem = session.createQuery("from MaintableEntity where word = :word", MaintableEntity.class)
                    .setParameter("word", mainTable.getSelectionModel().getSelectedItem().getWord())
                    .getSingleResult();
            session.delete(elem);
            session.getTransaction().commit();
            mainTable.getItems().remove(mainTable.getSelectionModel().getSelectedItem());
            session.close();
        }
    }

    private void addCategoryClick() {
        Stage popUpStage = new Stage(StageStyle.UTILITY);
        GuiForm<AnchorPane, AddCategoryController> form = new GuiForm<>("add_category.fxml");
        AnchorPane pane = form.getParent();
        AddCategoryController categoryController = form.getController();
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(mainTable.getScene().getWindow());
        popUpStage.setTitle("Редактор категорий");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        categoryController.setParentController(this);
        categoryController.setThisStage(popUpStage);
        popUpStage.showAndWait();
    }

    private void compareWithTextClick() {
        Stage popUpStage = new Stage(StageStyle.UTILITY);
        GuiForm<AnchorPane, CompareWithTextController> form = new GuiForm<>("compare_with_text.fxml");
        AnchorPane pane = form.getParent();
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(mainTable.getScene().getWindow());
        popUpStage.setTitle("Сравнение с текстом");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    private void testClick() {
        Stage popUpStage = new Stage(StageStyle.UTILITY);
        GuiForm<AnchorPane, TestController> form = new GuiForm<>("test.fxml");
        AnchorPane pane = form.getParent();
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(mainTable.getScene().getWindow());
        popUpStage.setTitle("Тест");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    private void printClick() {
        //здесь будет работа с джава репорт
    }

    public void update() {
        mainTableModels.clear();
        Session session = HibernateSessionFactory.getSession();
        mainTableModels.addAll(session.createQuery("from FullmaintableEntity", FullmaintableEntity.class)
                .getResultList());
        session.close();
    }

    public void updateCategories(){
        categoryModels.clear();
        Session session = HibernateSessionFactory.getSession();
        categoryModels.addAll(session.createQuery("from CategoryEntity",CategoryEntity.class).getResultList());
        session.close();
        categorys.setItems(categoryModels);
    }

    private void OnCategory() {
        if (categorys.getValue().getNameCategory().equals("Удалить")) {
            translation.setDisable(false);
            englishWord.setDisable(false);
            partOfSpeechs.setDisable(false);
            mainTable.setItems(mainTableModels);

        } else if (categorys.getSelectionModel().getSelectedIndex() > -1) {
            translation.setDisable(true);
            englishWord.setDisable(true);
            partOfSpeechs.setDisable(true);
            filteredData.setPredicate(fullmaintableEntity -> fullmaintableEntity.getNameCategory().equals(categorys.getValue().getNameCategory()));
            mainTable.setItems(filteredData);
        }
    }

    private void OnPartOfSpeech() {
        if (partOfSpeechs.getValue().getNamePart().equals("Нет данных")) {
            translation.setDisable(false);
            englishWord.setDisable(false);
            categorys.setDisable(false);
            mainTable.setItems(mainTableModels);

        } else if (partOfSpeechs.getSelectionModel().getSelectedIndex() > -1) {
            translation.setDisable(true);
            englishWord.setDisable(true);
            categorys.setDisable(true);
            filteredData.setPredicate(fullmaintableEntity -> fullmaintableEntity.getNamePart().equals(partOfSpeechs.getValue().getNamePart()));
            mainTable.setItems(filteredData);
        }
    }

    private void ChangeEnglishWord() {
        if (englishWord.getText().equals("")) {
            translation.setDisable(false);
            categorys.setDisable(false);
            partOfSpeechs.setDisable(false);
            mainTable.setItems(mainTableModels);
        } else {
            translation.setText("");
            translation.setDisable(true);
            categorys.setDisable(true);
            partOfSpeechs.setDisable(true);
            filteredData.setPredicate(fullmaintableEntity -> fullmaintableEntity.getWord().toLowerCase().contains(englishWord.getText()));
            mainTable.setItems(filteredData);
        }
    }

    private void ChangeTranslation() {
        if (translation.getText().equals("")) {
            englishWord.setDisable(false);
            categorys.setDisable(false);
            partOfSpeechs.setDisable(false);
            mainTable.setItems(mainTableModels);
        } else {
            englishWord.setText("");
            englishWord.setDisable(true);
            categorys.setDisable(true);
            partOfSpeechs.setDisable(true);
            filteredData.setPredicate(fullmaintableEntity -> fullmaintableEntity.getTransferP().toLowerCase().contains(translation.getText()));
            mainTable.setItems(filteredData);
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


}
