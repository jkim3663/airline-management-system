package com.example.airman;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AirlineManagement extends Application {
    // 0: main menu scene
    private static List<Scene> sceneList = new ArrayList<Scene>();
    // 0: primaryStage
    private static List<Stage> stageList = new ArrayList<Stage>();

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

        Stage primaryStage = stageList.get(0);

        airplanes.setOnAction(e -> primaryStage.setScene(airplaneScene));

        return menu;
    }

    public GridPane airplane() {
        GridPane airplane = new GridPane();
        airplane.setHgap(70);
        airplane.setVgap(70);

        Button add_airplane = new Button("Add Airplane");
        add_airplane.setPrefSize(200, 50);

        Button returnBtn = returnButton();

        airplane.addRow(0, add_airplane);
        airplane.addRow(1, returnBtn);

        return airplane;
    }



    /**
     * A return button that can be used for all pages to go back to the main menu
     */
    public Button returnButton() {
        Button returnBtn = new Button("Return to main menu");
        returnBtn.setPrefSize(200, 50);

        Stage primaryStage = stageList.get(0);
        returnBtn.setOnAction(e -> primaryStage.setScene(sceneList.get(0)));

        return returnBtn;
    }

    public static void main(String[] args) { launch(args); }
}