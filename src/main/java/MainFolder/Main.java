package MainFolder;

import Helper.GuiForm;
import guiContoller.MainController;
import hibernate.HibernateSessionFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        configPrimary(primaryStage);

        Stage loaderStage = new Stage(StageStyle.UNDECORATED);

        GuiForm loader = new GuiForm("loader.fxml");
        loaderStage.setScene(new Scene(loader.getParent()));
        loaderStage.centerOnScreen();
        loaderStage.show();


        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    HibernateSessionFactory.init();
                    GuiForm<AnchorPane, MainController> mainForm = new GuiForm<>("main.fxml");
                    AnchorPane root = mainForm.getParent();
                    MainController controller = mainForm.getController();
                    controller.setPrimaryStage(primaryStage);
                    loaderStage.close();
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                });
            } catch (ExceptionInInitializerError ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR, "Ошибка подключения к базе").showAndWait();
                    closeApp();
                });
            }
        }).start();
    }


    private void closeApp() {
        Platform.exit();
        HibernateSessionFactory.shutdown();
        System.exit(0);
    }

    private void configPrimary(Stage primaryStage) {
        primaryStage.setTitle("Словарь");
        primaryStage.setOnCloseRequest(event -> closeApp());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
