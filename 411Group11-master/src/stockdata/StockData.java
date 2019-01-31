package stockdata;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StockData extends Application {

    public static void main(String[] args) {
        
        
        launch();
        
//        Scanner reader = new Scanner(System.in);
//        String name = "";
//        System.out.print("Please Enter company symbol: ");
//        name = reader.nextLine();
//        Company data = Connect.getCompanyData(name);
//        System.out.println(data.toString())  
    }

    public void start(Stage stage) throws Exception{
        
        stage.setTitle("Stock Comparisons");
        stage.setResizable(false);
        stage.setHeight(428.0);
        stage.setWidth(606.0);
        stage.setOnCloseRequest((WindowEvent we) -> {
            stage.close();
        });
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLStockComparison.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
