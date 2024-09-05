/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 *
 * @author ntu-user
 */
public class updateController {
    @FXML
    private Button newupdatebtn;
    
    @FXML
    private TextField userTextField;
    
    @FXML
    private PasswordField passPasswordField;

    @FXML
    private PasswordField rePassPasswordField;
    
    @FXML
    private Button backupdatebtn;
    
    private String[] credentials;
    
    @FXML
    private void updateBtnHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) newupdatebtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            DB myObj = new DB();
            String pass = passPasswordField.getText().toString();
            String user = userTextField.getText().toString();
            String newPass = rePassPasswordField.getText().toString();
        
            if(!user.isEmpty() && !pass.isEmpty() && !newPass.isEmpty()){
              
                if (myObj.validateUser(user, pass)) {
                    System.out.println(user + " " + pass + " " + newPass);
                    myObj.updateDataToDB(user, newPass);
                    String[] credentials = {user,newPass};
                    loader.setLocation(getClass().getResource("secondary.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 640, 480); 
                    secondaryStage.setScene(scene);
                    SecondaryController controller = loader.getController();
                    secondaryStage.setTitle("Show users");
                    controller.initialise(credentials);
                    String msg = "some data sent from Register Controller";
                    secondaryStage.setUserData(msg);
                } else {
                    String msg = "User Not Present";
                    secondaryStage.setUserData(msg);
                }
                secondaryStage.show();
                primaryStage.close();
            }else{
                // show error msg
                System.out.println("Some field is empty");
            }
          

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void backupdatebtnhandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backupdatebtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            SecondaryController controller = loader.getController();
            controller.initialise(credentials);
            secondaryStage.setTitle("Login");
            secondaryStage.show();
            primaryStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    
    
    public void initialise(String[] credentials) {
        this.credentials = credentials;
    }
}