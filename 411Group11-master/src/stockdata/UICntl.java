package stockdata;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 *
 * @author Ryan Dougherty
 */
public class UICntl implements Initializable{
    
    
    Connect connect;
    Company cmp1;
    Company cmp2;
    Stock s1;
    Stock s2;
    StockPrices sp1;
    StockPrices sp2;
    
    @FXML
    TextField c1Text;
    @FXML
    TextField c2Text;
    @FXML
    ListView<String> c1List;
    @FXML
    ListView<String> c2List;

    @Override
    public void initialize(URL url, ResourceBundle rd){
        
        connect = new Connect();
                    
    }
    
    @FXML 
    public void c1Click(ActionEvent e) throws IOException{
        
        String cmpName = c1Text.getText();
        cmp1 = connect.getCompanyData(cmpName);
        if(cmp1 != null){
            s1 = connect.getStockPrices(cmp1);
            sp1 = s1.getPrice(20);

            c1List.getItems().clear();
            c1List.getItems().addAll(sp1.getListView());
        }else{
            JOptionPane.showMessageDialog(null, "We could not find that company. Try again.");
        }
        
        
    }
    
    @FXML
    public void c2Click(ActionEvent e) throws IOException{
        
        String cmpName = c2Text.getText();
        cmp2 = connect.getCompanyData(cmpName);
        if(cmp2 != null){
            s2 = connect.getStockPrices(cmp2);
            sp2 = s2.getPrice(20);

            c2List.getItems().clear();
            c2List.getItems().addAll(sp2.getListView());
        }else{
            JOptionPane.showMessageDialog(null, "We could not find that company. Try again.");
        }
        
    }
    
}
