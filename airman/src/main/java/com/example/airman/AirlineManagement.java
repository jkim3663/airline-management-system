package com.example.airman;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.airman.DatabaseConnect;


public class AirlineManagement extends Application {
    // 0: main menu scene
    // 1: Airplane menu scene
    public static List<Scene> sceneList = new ArrayList<Scene>();
    // 0: primaryStage
    public static List<Stage> stageList = new ArrayList<Stage>();


    @Override
    public void start(Stage primaryStage) throws IOException {
        stageList.add(primaryStage);

        Label intro = new Label("This is a simple airline management application");
        Label user_id = new Label("User ID");
        Label password = new Label("Password");
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        Button submit = new Button("Submit");

        GridPane root = new GridPane();
        root.addRow(0, intro);
        root.addRow(1, user_id, tf1);
        root.addRow(2, password, tf2);
        root.addRow(3, submit);
        root.setAlignment(Pos.CENTER);

        Scene login = new Scene(root, 720, 480);
        primaryStage.setScene(login);
        primaryStage.setTitle("Airline Management System");

        GridPane menuScene = menuScene();
        Scene mainMenu = new Scene(menuScene, 720, 480);

        sceneList.add(mainMenu);

        submit.setOnAction(e -> {
            String username = tf1.getText();
            String pw = tf2.getText();

            if (username.equals("jkim") && pw.equals("1234")) {
                primaryStage.setScene(mainMenu);
            }
        });


        primaryStage.show();
    }


    public GridPane menuScene() {
        GridPane menu = new GridPane();
        menu.setHgap(70);
        menu.setVgap(70);

        Button airplanes = new Button("Airplanes");
        Button routes = new Button("Routes");
        airplanes.setPrefSize(200, 50);
        routes.setPrefSize(200, 50);

        menu.addRow(0, airplanes, routes);

        Button pilots = new Button("Pilots");
        Button tickets = new Button("Tickets");
        pilots.setPrefSize(200, 50);
        tickets.setPrefSize(200, 50);

        menu.addRow(1, pilots, tickets);

        Button people = new Button("People");
        Button airports = new Button("Airports");
        people.setPrefSize(200, 50);
        airports.setPrefSize(200, 50);

        menu.addRow(2, people, airports);

        Button flights = new Button("Flights");
        Button viewsAndSimulation = new Button("Views / Simulation Cycle");
        flights.setPrefSize(200, 50);
        viewsAndSimulation.setPrefSize(200, 50);

        menu.addRow(3, flights, viewsAndSimulation);

        menu.setAlignment(Pos.CENTER);

        GridPane airplanePane = airplane();
        Scene airplaneScene = new Scene(airplanePane, 720, 480);

        // idx 1: airplaneScene
        sceneList.add(airplaneScene);


        Stage primaryStage = stageList.get(0);
        airplanes.setOnAction(e -> primaryStage.setScene(airplaneScene));

        return menu;
    }

    public GridPane airplane() {
        GridPane airplane = new GridPane();
        airplane.setPadding(new Insets(20, 20, 20, 20));
        airplane.setHgap(70);
        airplane.setVgap(70);

        Button add_airplane = new Button("Add Airplane");
        add_airplane.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        airplane.addRow(0, add_airplane);
        airplane.addRow(1, returnBtn);

        GridPane addAirplanePane = addAirplanePane();
        Scene addAirplaneScene = new Scene(addAirplanePane, 720, 480);

        Stage primaryStage = stageList.get(0);
        add_airplane.setOnAction(e -> primaryStage.setScene(addAirplaneScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return airplane;
    }


    public GridPane addAirplanePane () {
        GridPane addAirplanePane = new GridPane();
        addAirplanePane.setPadding(new Insets(20, 20, 20, 20));
        addAirplanePane.setHgap(70);
        addAirplanePane.setVgap(30);

        HBox row1 = new HBox(20);
        Label aID = new Label("airlineID");
        MenuButton aIDList = new MenuButton();
        aIDList.getItems().addAll(new MenuItem("Air_France"), new MenuItem("American"), new MenuItem("Delta"),
                new MenuItem("JetBlue"), new MenuItem("Lufthansa"), new MenuItem("Southwest"),
                new MenuItem("Spirit"), new MenuItem("United"));

        Label planeType = new Label("plane_type");
        TextField pType = new TextField();

        row1.getChildren().addAll(aID, aIDList, planeType, pType);
        for (int i = 0; i < aIDList.getItems().size(); i++) {
            MenuItem curr = aIDList.getItems().get(i);
            curr.setOnAction(e -> aIDList.setText(curr.getText()));
        }

        HBox row2 = new HBox(20);
        Label tailNum = new Label("tail_num");
        TextField tNum = new TextField();
        Label skids = new Label("skids");
        TextField skidText = new TextField();

        row2.getChildren().addAll(tailNum, tNum, skids, skidText);

        HBox row3 = new HBox(20);
        Label seatCap = new Label("seat_capacity");
        TextField seatCapNum = new TextField();
        Label propeller = new Label("propeller");
        TextField prop = new TextField();

        row3.getChildren().addAll(seatCap, seatCapNum, propeller, prop);

        HBox row4 = new HBox(20);
        Label speedLabel = new Label("speed");
        TextField speedNum = new TextField();
        Label jetEngines = new Label("jet_engines");
        TextField jetNum = new TextField();

        row4.getChildren().addAll(speedLabel, speedNum, jetEngines, jetNum);

        HBox row5 = new HBox(20);
        Label locID = new Label("location_id");
        MenuButton locMenu = new MenuButton();
        ArrayList<String> locList = DatabaseConnect.getLocationID();
        for (int i = 0; i < locList.size(); i++) {
            MenuItem locItem = new MenuItem(locList.get(i));
            locItem.setOnAction(e -> locMenu.setText(locItem.getText()));
            locMenu.getItems().add(locItem);
        }

        row5.getChildren().addAll(locID, locMenu);

        HBox row6 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(airplane()));

        Button callAddAirplane = new Button("Add");
        callAddAirplane.setPrefSize(200, 50);
        callAddAirplane.setOnAction(e -> {
            String airplaneID = aIDList.getText();
            String plane_type = pType.getText();
            String tail_num = tNum.getText();
            String getSkidText = skidText.getText();
            Object skidVal;
            if ( getSkidText.equals("True") || getSkidText.equals("true") || getSkidText.equals("TRUE") ) {
                skidVal = true;
            } else if (getSkidText.equals("False") || getSkidText.equals("false") || getSkidText.equals("FALSE")) {
                skidVal = false;
            } else {
                skidVal = null;
            }
            try {
                int seat_capacity = Integer.parseInt(seatCapNum.getText());
                Object propVal = prop.getText().equals("") ? null : Integer.parseInt(prop.getText());
                int speedVal = speedNum.getText().equals("") ? null : Integer.parseInt(speedNum.getText());
                Object jet_engines = jetNum.getText().equals("") ? null : Integer.parseInt(jetNum.getText());
                String location_id = locMenu.getText();


                DatabaseConnect.useAddAirplane(airplaneID, tail_num, seat_capacity, speedVal, location_id, plane_type,
                        skidVal, propVal, jet_engines);

                Alert a = new Alert(Alert.AlertType.NONE);
                // set alert type
                a.setAlertType(Alert.AlertType.CONFIRMATION);
                a.setContentText("View airplane table to check the update");

                // show the dialog
                a.show();
            } catch (Exception exception) {
                Alert a = new Alert(Alert.AlertType.NONE);
                // set alert type
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Retry with correct format");

                // show the dialog
                a.show();
            }

        });

        row6.getChildren().addAll(cancel, callAddAirplane);


        addAirplanePane.addRow(0, row1);
        addAirplanePane.addRow(1, row2);
        addAirplanePane.addRow(2, row3);
        addAirplanePane.addRow(3, row4);
        addAirplanePane.addRow(4, row5);
        addAirplanePane.addRow(5, row6);


        return addAirplanePane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}