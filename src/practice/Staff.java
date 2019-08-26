package practice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Staff {
	static Connection connection = sqlConnection.Connector();
	
	ResultSet res=null;
	
	@FXML
	private Label info;
	
	@FXML
	private TextField staffName;
	
	@FXML
	private TextField staffemail;
	
	@FXML
	private Button saveStaff;
	
	@FXML
	private Button back;
	PreparedStatement preparedStatement = null;
	
	public void staffLogin() throws SQLException{
		if(!staffName.getText().equals("") && !staffemail.getText().equals("")) {
		try {
			if(connection==null)
				throw new NullPointerException();
			String query= "Select max(Staff_ID) from staff;";
			preparedStatement = connection.prepareStatement(query);
			res=preparedStatement.executeQuery(query);
			res.next();
			int r=res.getInt(1)+1;
			if(isValid(staffemail.getText())) {
			String query1="insert into staff (Staff_ID,Name,email) values('"+ r +"','" + staffName.getText() + "','" + staffemail.getText() + "');";
			preparedStatement = connection.prepareStatement(query1);
			preparedStatement.executeUpdate(query1);
			try {
				Stage primaryStage=new Stage();
				FXMLLoader loader = new FXMLLoader();
				Pane root= loader.load(getClass().getResource("/practice/verifycust.fxml").openStream());
				Scene scene = new Scene(root,500,300);
				scene.getStylesheets().add(getClass().getResource("gui.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle("Fastfood Corner");
				primaryStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}else {
				info.setText("Invalid email... enter email correctly");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		}else {
			info.setText("fields are empty or entered data is/are invalid");
		}
		
	}
	
	public static boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 
	
	public void backbtn(ActionEvent event){
		((Node)event.getSource()).getScene().getWindow().hide(); 
	}
}
