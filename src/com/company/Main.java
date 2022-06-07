package com.company;

import dbAccess.DBCountry;
import dbAccess.DBDivision;
import helper.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

/**This is the Main class of the application
 * @author Ioannis Ntoulis*/
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/LoginForm.fxml"));
        Scene scene = new Scene(root, 520, 400);
        stage.setScene(scene);
        stage.setTitle("Software 2 - C195");
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        DBConnection.startConnection();
        DBCountry.select();
        DBDivision.select();
        launch(args);
        DBConnection.closeConnection();


    }
}


