package guiContoller.derictories;

import guiContoller.IController;
import guiContoller.MainController;
import hibernate.HibernateSessionFactory;
import hibernate.derictories.CategoryEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Created by gavri on 15.07.2017.
 */
public class AddCategoryController implements IController {

    @FXML
    private ComboBox<CategoryEntity> categoriesEdit;
    @FXML
    private ComboBox<CategoryEntity> categoriesDelete;
    @FXML
    private TextField newCategory;
    @FXML
    private TextField nameEditCategory;

    private Stage thisStage;
    private MainController parentController;
    private ObservableList<CategoryEntity> categoryModels = FXCollections.observableArrayList();

    @FXML
    private void initialize() throws IOException {
        Session session = HibernateSessionFactory.getSession();
        List<CategoryEntity> newList = session.createQuery("from CategoryEntity ", CategoryEntity.class)
                .getResultList();
        categoryModels.addAll(newList);
        categoriesDelete.setItems(categoryModels);
        categoriesEdit.setItems(categoryModels);
        session.close();

    }

    public void OnAddCategory(ActionEvent actionEvent) {
        if (!newCategory.getText().isEmpty() && checkRu(newCategory.getText())) {
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setNameCategory(newCategory.getText());
            session.save(categoryEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Категория успешна добавлена");
            alert.setTitle("OK!");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                thisStage.close();
                parentController.updateCategories();
            }
        }
    }

    public void OnEditCategory(ActionEvent actionEvent) {
        if(!nameEditCategory.getText().isEmpty()  && checkRu(nameEditCategory.getText())){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            CategoryEntity categoryEntity = categoriesEdit.getSelectionModel().getSelectedItem();
            categoryEntity.setNameCategory(nameEditCategory.getText());
            session.update(categoryEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Категория успешна изменена");
            alert.setTitle("OK!");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                thisStage.close();
                parentController.updateCategories();
            }
        }
    }

    public void OnDeleteCategory(ActionEvent actionEvent) {
        Alert alertApproval = new Alert(Alert.AlertType.WARNING, "Вы точно хотите удалить категорию?");
        alertApproval.setTitle("WARNING!");
        alertApproval.setHeaderText(null);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alertApproval.getButtonTypes().addAll(buttonTypeCancel);
        Optional<ButtonType> result = alertApproval.showAndWait();
        if(result.get() == ButtonType.OK) {
            int selectedIndex = categoriesDelete.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите категорию для удаления");
                alert.showAndWait();
                return;
            }
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            CategoryEntity elem = categoriesDelete.getSelectionModel().getSelectedItem();
            session.delete(elem);
            session.getTransaction().commit();
            session.close();
            thisStage.close();
            parentController.updateCategories();
        }
    }

    public void OnCategoriesEdit(ActionEvent actionEvent) {
        nameEditCategory.setText(categoriesEdit.getSelectionModel().getSelectedItem().getNameCategory());
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


}
