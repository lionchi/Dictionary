package Helper;

import MainFolder.Main;
import guiContoller.IController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.net.URL;

/**
 * Created by gavri on 14.07.2017.
 */
public class GuiForm<P extends Parent, C extends IController> {
    private P parent;
    private C controller;

    public GuiForm(String[] filePath) {
        try {
            StringBuilder builder = new StringBuilder("gui");
            for (String pack : filePath){
                builder.append("/");
                builder.append(pack);
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = Main.class.getClassLoader().getResource(builder.toString());
            if (url == null)
                throw new IllegalArgumentException("FXML file not found!");
            fxmlLoader.setLocation(url);
            parent = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("FXML file load error!");
        }
    }

    public GuiForm(String fileName){
        this(new String[]{fileName});
    }

    public P getParent() {
        return parent;
    }

    public C getController() {
        return controller;
    }
}
