package practice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
public class Bill {
	@FXML
    public TableView<Orders> table;
    @FXML
    public TableColumn<Orders,String> item;
    @FXML
    public TableColumn<Orders,Integer> price;
    @FXML
    public TextField totalbill;
    @FXML
    public TextField finalbill;
    @FXML
    public TextField disc;
    ObservableList<Orders> data;
    static Connection connection = sqlConnection.Connector();
    int d;
    @FXML
    private Label info;
    public void billdetails() throws SQLException {
    	ResultSet rs = null;
        try
        {
            data = FXCollections.observableArrayList();
            rs = connection.createStatement().executeQuery("select * from bill");
            while(rs.next())
            {
                data.add(new Orders(rs.getString(1),rs.getInt(2)));
            }
        }catch(SQLException ex){
            System.err.println("Error"  + ex);
        }
        item.setCellValueFactory(new PropertyValueFactory<>("food_item"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.setItems(null);
        table.setItems(data);
        
        ResultSet cpt = connection.createStatement().executeQuery("select creditpt from customer where Mobileno = '"+VerifyCust.mob+"';");
        cpt.next();
        d=cpt.getInt(1);
        rs = connection.createStatement().executeQuery("Select Discount from discount where credit_pt = '"+d+"';");
        rs.next();
        d=rs.getInt(1);
        disc.setText(String.valueOf(d+"%"));
    }
    public void delbill(){
        try{
            String sql="delete from bill";
            java.sql.Statement statement=connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch(Exception e){
            System.out.print(e);
        }
    }
    public void totalbill() throws FileNotFoundException, DocumentException{
        try{
            int i;
            float sum=0,fin; 
            data = FXCollections.observableArrayList();
            ResultSet rs = connection.createStatement().executeQuery("select price from bill;");
            while(rs.next())
            {
                sum=sum+rs.getInt(1);
            }
            totalbill.setText(String.valueOf(sum));
            fin=(sum-((sum*d)/100));
            System.out.println(fin);
            finalbill.setText(String.valueOf(fin));
            ResultSet maximum = connection.createStatement().executeQuery("Select max(Billno) from monsales;");
            maximum.next();
            ResultSet name = connection.createStatement().executeQuery("Select Fname from customer where mobileno = '"+ VerifyCust.mob +"';");
            name.next();
            i=maximum.getInt(1);
            String n = name.getString(1);
            connection.createStatement().executeUpdate("insert into monsales (Billno,mobileno,Fname,Billamt) values('" + ++i + "','" + VerifyCust.mob + "','" + n + "','" + fin + "');");
            rs=null;
            connection.createStatement().executeUpdate("Update customer set creditpt =(creditpt+1)%7 where mobileno = '" +VerifyCust.mob+ "'");
            generatepdf(sum,d,fin);
            info.setText("Bill generated... pdf copy is on the deskktop");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void generatepdf(float sum,int d,float fin) throws FileNotFoundException, DocumentException, SQLException {
    	ResultSet rs = null;
    	Document my_pdf_report = new Document();
                PdfWriter.getInstance(my_pdf_report, new FileOutputStream("C:\\Users\\harsh\\desktop\\bill.pdf"));
                my_pdf_report.open();            
                //we have 2 columns in our table
                PdfPTable my_report_table = new PdfPTable(2);
                //create a cell object
                PdfPCell table_cell;
    	try{
    	rs = connection.createStatement().executeQuery("select * from bill;");
    	
    	String a="****Fastfood corner****";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a="";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a="Bill Date/Time";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a=LocalDateTime.now().toString();
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a="Customer mobileno.";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a=Long.toString(VerifyCust.mob);
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a=" ";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a=" ";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a="***Bill***";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
		a="";
		table_cell=new PdfPCell(new Phrase(a));
		my_report_table.addCell(table_cell);
    			a="Itemname";
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
    			a="Price";
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
                while (rs.next()) {                
                                String Itemname = rs.getString(1);
                                table_cell=new PdfPCell(new Phrase(Itemname));
                                my_report_table.addCell(table_cell);
                                String price=Integer.toString(rs.getInt(2));
                                table_cell=new PdfPCell(new Phrase(price));
                                my_report_table.addCell(table_cell);
                                }
                a="Total Bill";
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
    			a=Float.toString(sum);
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
    			a="Discount";
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
    			a=Integer.toString(d);
    			table_cell=new PdfPCell(new Phrase(a+"%"));
    			my_report_table.addCell(table_cell);
    			a="Final Bill";
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
    			a=Float.toString(fin);
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
    			a="**Have a nice day!**";
    			table_cell=new PdfPCell(new Phrase(a));
    			my_report_table.addCell(table_cell);
      	my_pdf_report.add(my_report_table);                       
      		my_pdf_report.close();
       }catch(Exception e){
          e.printStackTrace();
       }
    }
}
