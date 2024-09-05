/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.in;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class FileManagerController {
    
    @FXML
    private Button createBtn;
    
    @FXML
    private Button copyBtn;
    
    @FXML
    private Button moveBtn;

    @FXML
    private Button uploadBtn;
    
    @FXML
    private Button delBtn;
    
    @FXML
    private Button renameBtn;
    
    @FXML
    private Button backBtn;
   
    @FXML
    private TextField txtFileRename;
    
    @FXML
    private TextField txtFileField;
     private String[] credentials;
    private boolean isTextEmpty(){
        return txtFileField.getText().toString().isEmpty();
    }

    private void showMsg(String headerMsg, String contentMsg) {
        Stage secondaryStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.DARKGRAY);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(headerMsg);
        alert.setContentText(contentMsg);
        Optional<ButtonType> result = alert.showAndWait();
    }
    
    @FXML
    private void createfile (ActionEvent event) {
    try {
        if(isTextEmpty()){
            showMsg("Information","Please enter a file name");
            return;
        }
        String filename = txtFileField.getText().toString();
        File myObj = new File(filename);
        if (myObj.createNewFile()) {
            showMsg("Information","File created: " + myObj.getName());
        } else {
            showMsg("Information","File already exists.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }}
    
    // Rename file 
    @FXML
    private void rename (ActionEvent event) {
    try {
        if(isTextEmpty()){
            showMsg("Information","Please enter a file name");
            return;
        }
        if(txtFileRename.getText().toString().isEmpty()){
            showMsg("Information","Please enter a rename file name");
            return;
        }
        String filename = txtFileField.getText().toString();
        String rname = txtFileRename.getText().toString();
        File file = new File(filename);
        File rename = new File(rname);
        boolean flag = file.renameTo(rename);
        if(flag){
            showMsg("Information","File Successfully Rename.");
        }else{
            showMsg("Information","Operatnoin Failed.");
        }
        
       
    } catch (Exception e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }}
    
    
    @FXML
    private void move (ActionEvent event) {
    try {
        if(isTextEmpty()){
            showMsg("Information","Please enter a file name");
            return;
        }
        if(txtFileRename.getText().toString().isEmpty()){
            showMsg("Information","Please enter the location of file name");
            return;
        }
        String filename = txtFileField.getText().toString();
        String rname = txtFileRename.getText().toString();
        
        
        Path file = Paths.get(filename);
        Path target = Paths.get(rname);
        Path temp = Files.move(file, target, StandardCopyOption.REPLACE_EXISTING);
        
        if(temp!=null){
            showMsg("Information","File Successfully Move.");
        }else{
            showMsg("Information","Operatnoin Failed.");
        }
        
       
    } catch (Exception e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }}
    @FXML
    public void readinfo(ActionEvent event) {
        if(isTextEmpty()){
            showMsg("","");
            return;
        }
        String filename = txtFileField.getText().toString();
        File myObj = new File(filename);
        if (myObj.exists()) {
            System.out.println("File name: " + myObj.getName());
            System.out.println("Absolute path: " + myObj.getAbsolutePath());
            System.out.println("Writeable: " + myObj.canWrite());
            System.out.println("Readable " + myObj.canRead());
            System.out.println("File size in bytes " + myObj.length());
        } else {
            System.out.println("The file does not exist.");
        }
        }
    
    @FXML
    private void delete(ActionEvent event) { 
        if(isTextEmpty()){
            showMsg("","");
            return;
        }
        String filename = txtFileField.getText().toString();
        File myObj = new File(filename);
        if (myObj.delete()) { 
          showMsg("Information","Deleted the file: " + myObj.getName());
        } else {
          showMsg("Information","Failed to delete the file.");
        } 
      }
    
      public void initialise(String[] credentials) {
        this.credentials = credentials;
        
    }  
    @FXML
    private void copy(ActionEvent event) { 
        if(isTextEmpty()){
            showMsg("Information","Created a copy file");
            return;
        }
        try{
            String filename = txtFileField.getText().toString();
            Path in = Paths.get(filename);
            Path out = Paths.get("copy"+filename);
            showMsg("Information","File Copied " + out.getFileName());
            Files.copy(in, out,StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            showMsg("Error","An error occurred.");
            e.printStackTrace();
        }
        


      }

        
 @FXML
    private void back(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            SecondaryController back = loader.getController();
            back.initialise(credentials);
            secondaryStage.setTitle("Login");
            secondaryStage.show();
            primaryStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
    
  
    
}
