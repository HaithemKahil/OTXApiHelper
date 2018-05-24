package otxapimodule.main;

import com.alienvault.otx.model.indicator.Indicator;
import com.alienvault.otx.model.pulse.Pulse;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PulseScreenController implements Initializable{

    @FXML
    private SplitMenuButton tlpMenuButton;

    @FXML
    private TextField pulseNameTexField;

    @FXML
    private TextArea pulseDescriptionTextField;

    @FXML
    private TableView<Indicator> indicatorsTableView;

    @FXML
    private TableColumn typeColumn;

    @FXML
    private TableColumn indicatorColumn;

    @FXML
    private Button submitButton;

    @FXML
    private Button resetButton;

    @FXML
    private CheckBox privatePusleCheckBox;

    @FXML
    void reset(ActionEvent event) {
        tlpMenuButton.setText("Choose TLP");
        pulseNameTexField.setText("");
        pulseDescriptionTextField.setText("");
        indicatorsTableView.getItems().clear();
        try {
            loadIndicatorScreen(new Stage());
        } catch (IOException e) {
            Toast.makeText(new Stage(),"Error while loading the window !!!",1500,500,500);
        }
    }

    @FXML
    void submitNewPulse(ActionEvent event) throws IOException {
        String tlp = tlpMenuButton.getText();
        String pulseName = pulseNameTexField.getText();
        String description = pulseDescriptionTextField.getText();

        if(tlp.equalsIgnoreCase("Choose TLP") || pulseName.isEmpty() || description.isEmpty()){
            Toast.makeText(new Stage(),"Please Verify if any of the fields is still empty !!!",2500,500,500);
        }else{
            Pulse createdPulse = OTXCallManager.getPulse(pulseName,description,IndicatorScreenController.addedIndicators,new ArrayList<>(),new ArrayList<>(),tlp,privatePusleCheckBox.isSelected());
            try {
                MainApplication.otxCallManager.setNewPulse(createdPulse);
            } catch (MalformedURLException e) {
                Toast.makeText(new Stage(),"Error while creating the pulse (MalFormedURL Exception) !!!",2500,500,500);
            } catch (URISyntaxException e) {
                Toast.makeText(new Stage(),"Error while creating the pulse (URLSyntax Exception) !!!",2500,500,500);
            }
            Toast.makeText(new Stage(),"Pulse created Succefully :) !!",2500,500,500);
            loadIndicatorScreen(new Stage());
            closePulseWindow();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        indicatorColumn.setCellValueFactory(new PropertyValueFactory<>("indicator"));
        indicatorsTableView.setItems(IndicatorScreenController.addedIndicators);
        tlpMenuButton.getItems().addAll(new MenuItem("white"),new MenuItem("red"),new MenuItem("green"),new MenuItem("amber"));
        for(MenuItem menuItem:tlpMenuButton.getItems())
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    tlpMenuButton.setText(menuItem.getText());
                }
            });
    }

    private void closePulseWindow(){
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
    private void loadIndicatorScreen(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxmlfiles/CreateIndicatorScreen.fxml"));
        primaryStage.setTitle("Create Pulse Indicators");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
