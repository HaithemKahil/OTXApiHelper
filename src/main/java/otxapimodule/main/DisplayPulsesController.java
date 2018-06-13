package otxapimodule.main;

import com.alienvault.otx.model.pulse.Pulse;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.bson.Document;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;


public class DisplayPulsesController implements Initializable {

    static DBHelper dbHelper;
    public static ObservableList<Pulse> listedPulses;


    static MongoCollection pulsesCollection;
    @FXML
    private Button refreshButton;

    @FXML
    private TableView<Pulse> pulsesTableView;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TableColumn<?, ?> tlpColumn;

    @FXML
    void listPulses(ActionEvent event) {
        Toast.makeText(new Stage(),"Please Wait while fetching the pulses !! ",3000,500,500);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Before"+listedPulses.size());
                    for(Pulse p :MainApplication.otxCallManager.listAllPulses())
                        if(!listedPulses.contains(p)){
                            dbHelper.insertPulseIfNotExists(pulsesCollection,DBHelper.pulseToDocument(p));
                            listedPulses.add(p);
                            System.out.println(true);
                        }
                    System.out.println("After :"+listedPulses.size());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listedPulses = FXCollections.observableArrayList();
        dbHelper = new DBHelper();
        dbHelper.connect();
        pulsesCollection = dbHelper.getCollection("OTXPulses");
        FindIterable<Document> iterDoc = pulsesCollection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) { listedPulses.add(DBHelper.documentToPulse((Document) it.next()));}

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tlpColumn.setCellValueFactory(new PropertyValueFactory<>("tlp"));
        pulsesTableView.setItems(listedPulses);
    }

}
