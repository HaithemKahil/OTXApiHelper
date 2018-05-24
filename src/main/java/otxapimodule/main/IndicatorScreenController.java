package otxapimodule.main;

import com.alienvault.otx.model.indicator.Indicator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.alienvault.otx.model.indicator.IndicatorType;
import javafx.stage.Stage;
import sun.applet.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IndicatorScreenController implements Initializable {

    public MenuButton typeMenuButton;
    public TextField indicatorTextField;
    public TextArea descriptionTextArea;
    public CheckBox addCheckBox;
    public Button cancelButton;
    public Button addButton;

    public static ObservableList<Indicator> addedIndicators;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addedIndicators = FXCollections.observableArrayList();
        for (IndicatorType ind : IndicatorType.values()) {
            MenuItem menuItem = new MenuItem(ind.getValue());
            typeMenuButton.getItems().add(menuItem);
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    typeMenuButton.setText(menuItem.getText());
                }
            });
        }

    }

    public void addIndicator(ActionEvent actionEvent) {
        String indicatorType = typeMenuButton.getText();
        String indicator = indicatorTextField.getText();
        String description = descriptionTextArea.getText();
        if(indicatorType.equalsIgnoreCase("choose a type") || indicator.isEmpty()  || description.isEmpty()){
            String toastMsg = "Please verify if one of the fields is still EMPTY !!! ";
            int toastMsgTime = 1000; //3.5 seconds
            int fadeInTime = 500; //0.5 seconds
            int fadeOutTime= 500; //0.5 seconds
            Toast.makeText(new Stage(), toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
        }else{
            try{
                System.out.println(typeMenuButton.getText());
                addedIndicators.add(OTXCallManager.getIndicator(OTXCallManager.valueOf(typeMenuButton.getText()),indicator,description));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        if(addCheckBox.isSelected()){
            resetAllFields();
        }else{
            try {
                closeIndicatorWindow();
                loadPulseCreationWindow(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeIndicatorWindow(){
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    public void loadPulseCreationWindow(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxmlfiles/CreatePulseScreen.fxml"));
        primaryStage.setTitle("OTX Api Helper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void resetAllFields(){
        typeMenuButton.setText("Choose a type");
        indicatorTextField.setText("");
        descriptionTextArea.setText("");
    }
}
