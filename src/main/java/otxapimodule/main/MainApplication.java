package otxapimodule.main;

import com.alienvault.otx.model.indicator.IndicatorType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import sun.applet.Main;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@SpringBootApplication
public class MainApplication extends Application {
	static String API_KEY = "d66bd1393202c083891d4ec000d9153cd144f246692ad52ad7221439164b221d";
	public static OTXCallManager otxCallManager ;
	public static void main(String[] args) throws MalformedURLException, URISyntaxException {
		otxCallManager = new OTXCallManager();
		ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class,args) ;
		otxCallManager.setUpConnection(context.getEnvironment());
		System.out.println(otxCallManager.getOtxConnection().getMyDetails().getUsername());
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxmlfiles/CreateIndicatorScreen.fxml"));
        primaryStage.setTitle("OTX Api Helper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}
}
