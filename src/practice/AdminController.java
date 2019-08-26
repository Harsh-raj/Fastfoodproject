package practice;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AdminController implements Initializable{

	@FXML
	private Label admin;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void getUser(String user) {
		admin.setText(user);
	}
	
	public void signOut(ActionEvent event) {
	try {
		((Node)event.getSource()).getScene().getWindow().hide(); 
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root= loader.load(getClass().getResource("/practice/login.fxml").openStream());
		Scene scene = new Scene(root,500,300);
		scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fastfood Corner");
		primaryStage.show();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changepwd(ActionEvent event) {
	try {
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root= loader.load(getClass().getResource("/practice/settings.fxml").openStream());
		Scene scene = new Scene(root,500,300);
		scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fastfood Corner");
		primaryStage.show();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addMenuItem(ActionEvent event) {
	try {
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root= loader.load(getClass().getResource("/practice/addMenu.fxml").openStream());
		Scene scene = new Scene(root,500,300);
		scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fastfood Corner");
		primaryStage.show();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delMenuItem(ActionEvent event) {
	try {
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root= loader.load(getClass().getResource("/practice/delmenu.fxml").openStream());
		Scene scene = new Scene(root,500,300);
		scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fastfood Corner");
		primaryStage.show();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void monsales(ActionEvent event) {
	try {
		Stage primaryStage=new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root= loader.load(getClass().getResource("/practice/monsales.fxml").openStream());
		Scene scene = new Scene(root,500,300);
		scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fastfood Corner");
		primaryStage.show();
		} catch (IOException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void offers(ActionEvent event) {
		try {
			Stage primaryStage=new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root= loader.load(getClass().getResource("/practice/EditOffers.fxml").openStream());
			Scene scene = new Scene(root,500,300);
			scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Fastfood Corner");
			primaryStage.show();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
