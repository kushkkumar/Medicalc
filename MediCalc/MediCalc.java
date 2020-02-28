import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MediCalc extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("splashScreen1.fxml"));
        Scene scene1=new Scene(root);
        myStage.setScene(scene1);
        myStage.setTitle("Dashboard");
        myStage.show();
    }
}