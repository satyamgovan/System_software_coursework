/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;
import java.util.stream.*;
import javafx.fxml.FXMLLoader;
/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class TerminatorController implements Initializable {
    @FXML
    private Button RunBtn;
    
    @FXML
    private TextField userTextField;
    
    @FXML
    private TextArea DisplayBox;
    
    @FXML
    private Button backBtn;
    
    private String[] credentials;
    
    @FXML
    private void runBtnHandler(ActionEvent event) {
        
        String cmd = userTextField.getText().toString();
        
        if(cmd!=null && !cmd.isEmpty()){
            try{
                
                runCommand(cmd);
            }catch (Exception e) {
                e.printStackTrace();
                errorDialogue("Error","Invalid Command");
            }
        }else{
            infoDialogue("Information","Field is bank");
        }

    }
    
    private void runCommand(String c) throws IOException, InterruptedException {
        var processBuilder = new ProcessBuilder();
        
        List<String> args = new ArrayList<String>(Arrays.asList(c.split(" ")));
        
        if(args.get(0).contentEquals("nano")){
            
            openExternal(c);
            return;
        }
        System.out.print("out");
        processBuilder.command(args);
        var process = processBuilder.start();
   
        try (var reader = new BufferedReader(
            new InputStreamReader(process.getInputStream()))) {

            String line;
            String fullData = "";

            while ((line = reader.readLine()) != null) {
                fullData = fullData + "\n" + line;
                
            }
            DisplayBox.setText(fullData);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void openExternal(String args) throws IOException , InterruptedException{
        var processBuilder = new ProcessBuilder();
        processBuilder.command("terminator","-e",args);
        var process = processBuilder.start();
        var ret = process.waitFor();
    }
    
    @FXML
    private void backBtnHandler(ActionEvent event) {
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
    
    public void initialise(String[] credentials) {
        this.credentials = credentials;
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    
        
        
        
        
// Error dialogue box
    }
    private void errorDialogue(String headerMsg, String contentMsg) {
        Stage secondaryStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.DARKGRAY);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(headerMsg);
        alert.setContentText(contentMsg);
        Optional<ButtonType> result = alert.showAndWait();
    }
    
// Message dialogue box
    private void infoDialogue(String headerMsg, String contentMsg) {
        Stage secondaryStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.DARKGRAY);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(headerMsg);
        alert.setContentText(contentMsg);
        Optional<ButtonType> result = alert.showAndWait();
    }
    
}
