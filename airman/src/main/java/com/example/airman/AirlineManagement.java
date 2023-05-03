package com.example.airman;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    // 2: Pilot menu scene
    // 3: People menu scene
    // 4: Flight menu scene
    // 7: airportScene menu scene ...
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
        GridPane menu = getGridPane();

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

        Button viewTable = new Button("View all tables");
        viewTable.setPrefSize(200, 50);

        menu.addRow(4, viewTable);

        menu.setAlignment(Pos.CENTER);

        GridPane airplanePane = airplane();
        Scene airplaneScene = new Scene(airplanePane, 720, 480);

        GridPane pilotPane = pilot();
        Scene pilotScene = new Scene(pilotPane, 720, 480);

        GridPane peoplePane = people();
        Scene peopleScene = new Scene(peoplePane, 720, 480);

        GridPane airportPane = airport();
        Scene airportScene = new Scene(airportPane, 720, 480);

        GridPane flightPane = flight();
        Scene flightScene = new Scene(flightPane, 720, 480);

        GridPane ticketPane = ticket();
        Scene ticketScene = new Scene(ticketPane, 720, 480);

        GridPane routePane = route();
        Scene routeScene = new Scene(routePane, 720, 480);

        GridPane viewSimPane = viewSimPane();
        Scene viewSimScene = new Scene(viewSimPane, 720, 480);

        GridPane viewTablePane = viewTablePane();
        Scene viewTableScene = new Scene(viewTablePane, 720, 480);


        sceneList.add(airplaneScene);       // idx 1: airplaneScene
        sceneList.add(pilotScene);          // idx 2: pilotScene
        sceneList.add(peopleScene);         // idx 3: personScene
        sceneList.add(flightScene);         // idx 4: flightScene
        sceneList.add(airportScene);        // idx 5: airportScene
        sceneList.add(ticketScene);         // idx 6: ticketScene
        sceneList.add(routeScene);         // idx 7: ticketScene


        Stage primaryStage = stageList.get(0);
        airplanes.setOnAction(e -> primaryStage.setScene(airplaneScene));
        airports.setOnAction(e -> primaryStage.setScene(airportScene));
        people.setOnAction(e -> primaryStage.setScene(peopleScene));
        pilots.setOnAction(e -> primaryStage.setScene(pilotScene));
        flights.setOnAction(e -> primaryStage.setScene(flightScene));
        tickets.setOnAction(e -> primaryStage.setScene(ticketScene));
        routes.setOnAction(e -> primaryStage.setScene(routeScene));
        viewsAndSimulation.setOnAction(e -> primaryStage.setScene(viewSimScene));
        viewTable.setOnAction(e -> primaryStage.setScene(viewTableScene));

        return menu;
    }

    public GridPane viewTablePane() {
        GridPane viewTablePane = getGridPane();

        return viewTablePane;
    }

    /**
     * This is going to contain all buttons to views
     */
    public GridPane viewSimPane() {
        GridPane viewSimPane = getGridPane();

        Button flightsInAir = new Button("View flights-in-air");
        flightsInAir.setPrefSize(200, 50);

        Button flightsOnGround = new Button("View flights-on-ground");
        flightsOnGround.setPrefSize(200, 50);

        viewSimPane.addRow(0, flightsInAir, flightsOnGround);

        Button peopleInAir = new Button("View people-in-air");
        peopleInAir.setPrefSize(200, 50);

        Button peopleInGround = new Button("View people-in-ground");
        peopleInGround.setPrefSize(200, 50);

        viewSimPane.addRow(1, peopleInAir, peopleInGround);

        Button routeSummary = new Button("View Route-Summary");
        routeSummary.setPrefSize(200, 50);

        Button altAirport = new Button("View Alternative-Airports");
        altAirport.setPrefSize(200, 50);

        viewSimPane.addRow(2, routeSummary, altAirport);

        Button simulation_cycle = new Button("View Simulation-Cycle");
        simulation_cycle.setPrefSize(200, 50);

        viewSimPane.addRow(3, simulation_cycle);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        viewSimPane.addRow(4, returnBtn);

        viewSimPane.setAlignment(Pos.CENTER);

        ScrollPane flightsInTheAir = flightsInTheAir();
        Scene query19 = new Scene(flightsInTheAir, 720, 480);

        ScrollPane flightsOnTheGround = flightsOnGround();
        Scene query20 = new Scene(flightsOnTheGround, 720, 480);

        ScrollPane peopleInTheAir = peopleInAir();
        Scene query21 = new Scene(peopleInTheAir, 720, 480);

        ScrollPane peopleInTheGround = peopleInGround();
        Scene query22 = new Scene(peopleInTheGround, 720, 480);

        ScrollPane route_Summary = routeSummary();
        Scene query23 = new Scene(route_Summary, 720, 480);

        ScrollPane alternativeAirport = alternativeAirport();
        Scene query24 = new Scene(alternativeAirport, 720, 480);

        GridPane simulCycle = simulationCycle();
        Scene query25 = new Scene(simulCycle, 720, 480);

        Stage primaryStage = stageList.get(0);
        flightsInAir.setOnAction(e -> primaryStage.setScene(query19));
        flightsOnGround.setOnAction(e -> primaryStage.setScene(query20));
        peopleInAir.setOnAction(e -> primaryStage.setScene(query21));
        peopleInGround.setOnAction(e -> primaryStage.setScene(query22));
        routeSummary.setOnAction(e -> primaryStage.setScene(query23));
        altAirport.setOnAction(e -> primaryStage.setScene(query24));
        simulation_cycle.setOnAction(e -> primaryStage.setScene(query25));

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));


        return viewSimPane;
    }

    public GridPane simulationCycle() {
        GridPane simulCycle = getGridPane();

        HBox row = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(viewSimPane()));

        Button runCycle = new Button("Run");
        runCycle.setPrefSize(200, 50);
        runCycle.setOnAction(e -> {

            try {
                DatabaseConnect.getSimulCycle();
                alert(0, "View flight Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with simulation_cycle");
            }

        });

        row.getChildren().addAll(cancel, runCycle);

        simulCycle.addRow(0, row);

        return simulCycle;
    }


    public ScrollPane alternativeAirport() {
        GridPane altAirports = new GridPane();
        altAirports.setPadding(new Insets(50, 50, 50, 50));

        ArrayList<ArrayList<String>> tableValues = DatabaseConnect.getAltAirports();
        for (int i = 0; i < tableValues.size(); i ++) {
            for (int j = 0; j < tableValues.get(0).size(); j++) {
                StackPane onebox = new StackPane();
                onebox.setStyle("-fx-border-width: 1;" + "-fx-border-color: black;"
                        + "-fx-padding: 5;" + "-fx-background-color: transparent;");
                Label lb = new Label(tableValues.get(i).get(j));
                onebox.getChildren().add(lb);
                GridPane.setConstraints(onebox, j, i);
                altAirports.getChildren().add(onebox);
            }
        }

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        altAirports.addRow(altAirports.getRowCount(), returnBtn);

        ScrollPane scrollPane = makeScrollPane(altAirports);

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(viewSimPane()));

        return scrollPane;
    }

    public ScrollPane makeScrollPane(GridPane gridPane) {
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(600, 400);

        return  scrollPane;
    }

    public ScrollPane routeSummary() {
        GridPane routeSummary = new GridPane();
        routeSummary.setPadding(new Insets(50, 50, 50, 50));

        ArrayList<ArrayList<String>> tableValues = DatabaseConnect.getRouteSummary();
        for (int i = 0; i < tableValues.size(); i ++) {
            for (int j = 0; j < tableValues.get(0).size(); j++) {
                StackPane box = new StackPane();
                box.setStyle("-fx-border-width: 1;" + "-fx-border-color: black;"
                        + "-fx-padding: 5;" + "-fx-background-color: transparent;");
                Label lb = new Label(tableValues.get(i).get(j));
                box.getChildren().add(lb);
                GridPane.setConstraints(box, j, i);
                routeSummary.getChildren().add(box);
            }
        }

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        routeSummary.addRow(routeSummary.getRowCount(), returnBtn);

        ScrollPane scrollPane = makeScrollPane(routeSummary);

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(viewSimPane()));

        return scrollPane;
    }


    public ScrollPane peopleInGround() {
        GridPane peopleInGround = new GridPane();
        peopleInGround.setPadding(new Insets(50, 50, 50, 50));

        ArrayList<ArrayList<String>> tableValues = DatabaseConnect.getPeopleInTheGround();
        for (int i = 0; i < tableValues.size(); i ++) {
            for (int j = 0; j < tableValues.get(0).size(); j++) {
                StackPane box = new StackPane();
                box.setStyle("-fx-border-width: 1;" + "-fx-border-color: black;"
                        + "-fx-padding: 5;" + "-fx-background-color: transparent;");
                Label lb = new Label(tableValues.get(i).get(j));
                box.getChildren().add(lb);
                GridPane.setConstraints(box, j, i);
                peopleInGround.getChildren().add(box);
            }
        }

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        peopleInGround.addRow(peopleInGround.getRowCount(), returnBtn);

        ScrollPane scrollPane = makeScrollPane(peopleInGround);

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(viewSimPane()));

        return scrollPane;
    }

    public ScrollPane peopleInAir() {
        GridPane peopleInAir = new GridPane();
        peopleInAir.setPadding(new Insets(50, 50, 50, 50));

        ArrayList<ArrayList<String>> tableValues = DatabaseConnect.getPeopleInTheGround();
        for (int i = 0; i < tableValues.size(); i ++) {
            for (int j = 0; j < tableValues.get(0).size(); j++) {
                StackPane box = new StackPane();
                box.setStyle("-fx-border-width: 1;" + "-fx-border-color: black;"
                        + "-fx-padding: 5;" + "-fx-background-color: transparent;");
                Label lb = new Label(tableValues.get(i).get(j));
                box.getChildren().add(lb);
                GridPane.setConstraints(box, j, i);
                peopleInAir.getChildren().add(box);
            }
        }

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        peopleInAir.addRow(peopleInAir.getRowCount(), returnBtn);

        ScrollPane scrollPane = makeScrollPane(peopleInAir);

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(viewSimPane()));

        return scrollPane;
    }

    public ScrollPane flightsOnGround() {
        GridPane flightsOnGround = new GridPane();
        flightsOnGround.setPadding(new Insets(50, 50, 50, 50));

        ArrayList<ArrayList<String>> tableValues = DatabaseConnect.getFlightsOnTheGround();
        for (int i = 0; i < tableValues.size(); i ++) {
            HBox row = new HBox(20);
            row.setAlignment(Pos.CENTER);
            for (int j = 0; j < tableValues.get(0).size(); j++) {
                Label lb = new Label(tableValues.get(i).get(j));
                lb.setMinWidth(70);
                row.getChildren().add(lb);
            }
            row.setStyle("-fx-border-width: 1;" + "-fx-border-radius: 1;" + "-fx-border-color: black;");
            flightsOnGround.addRow(i, row);
        }

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        flightsOnGround.addRow(flightsOnGround.getRowCount(), returnBtn);

        ScrollPane scrollPane = makeScrollPane(flightsOnGround);

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(viewSimPane()));

        return scrollPane;
    }

    public ScrollPane flightsInTheAir() {
        GridPane flightsInTheAir = new GridPane();
        flightsInTheAir.setPadding(new Insets(50, 50, 50, 50));

        ArrayList<ArrayList<String>> tableValues = DatabaseConnect.getFlightsInTheAir();
        for (int i = 0; i < tableValues.size(); i ++) {
            HBox row = new HBox(20);
            row.setAlignment(Pos.CENTER);
            for (int j = 0; j < tableValues.get(0).size(); j++) {
                Label lb = new Label(tableValues.get(i).get(j));
                lb.setMinWidth(70);
                row.getChildren().add(lb);
            }
            row.setStyle("-fx-border-width: 1;" + "-fx-border-radius: 1;" + "-fx-border-color: black;");
            flightsInTheAir.addRow(i, row);
        }

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        flightsInTheAir.addRow(flightsInTheAir.getRowCount(), returnBtn);

        ScrollPane scrollPane = makeScrollPane(flightsInTheAir);

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(viewSimPane()));

        return scrollPane;
    }

    public GridPane flight() {
        GridPane flight = getGridPane();

        Button offer_flight = new Button("Offer Flight");
        offer_flight.setPrefSize(200, 50);

        Button flightLanding = new Button("Flight Landing");
        flightLanding.setPrefSize(200, 50);

        Button flightOff = new Button("Flight Takeoff");
        flightOff.setPrefSize(200, 50);

        Button retireFlight = new Button("Retire Flight");
        retireFlight.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        flight.addRow(0, offer_flight, retireFlight);
        flight.addRow(1, flightLanding, flightOff);
        flight.addRow(2, returnBtn);

        GridPane offerFlightPane = offerFlightPane();
        Scene offerFlightScene = new Scene(offerFlightPane, 720, 480);
        GridPane fLandPane = fLandPane();
        Scene fLandScene = new Scene(fLandPane, 720, 480);
        GridPane fOffPane = fOffPane();
        Scene fOffScene = new Scene(fOffPane, 720, 480);
        GridPane retireFlightPane = retireFlightPane();
        Scene retireFlightScene = new Scene(retireFlightPane, 720, 480);

        Stage primaryStage = stageList.get(0);
        offer_flight.setOnAction(e -> primaryStage.setScene(offerFlightScene));
        flightLanding.setOnAction(e -> primaryStage.setScene(fLandScene));
        flightOff.setOnAction(e -> primaryStage.setScene(fOffScene));
        retireFlight.setOnAction(e -> primaryStage.setScene(retireFlightScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return flight;
    }

    public GridPane offerFlightPane() {
        GridPane offerFlightPane = getGridPane();

        HBox row1 = new HBox(20);
        Label flightID = new Label("flightID");
        TextField flightType = new TextField();

        Label supportTail = new Label("support_tail");
        TextField supportTailType = new TextField();

        row1.getChildren().addAll(flightID, flightType, supportTail, supportTailType);

        HBox row2 = new HBox(20);
        Label routeID = new Label("routeID");
        TextField routeIDType = new TextField();

        Label progress = new Label("progress");
        TextField progressType = new TextField();

        row2.getChildren().addAll(routeID, routeIDType, progress, progressType);

        HBox row3 = new HBox(20);
        Label supportAirline = new Label("support_airline");
        MenuButton airlineMenu = new MenuButton();
        ArrayList<String> airlineList = DatabaseConnect.getSupportAirline();
        for (int i = 0; i < airlineList.size(); i++) {
            MenuItem airlineItem = new MenuItem(airlineList.get(i));
            airlineItem.setOnAction(e -> airlineMenu.setText(airlineItem.getText()));
            airlineMenu.getItems().add(airlineItem);
        }

        Label airplaneStatus = new Label("airplane_status");
        MenuButton aStatusMenu = new MenuButton();
        MenuItem inGround = new MenuItem("in_ground");
        inGround.setOnAction(e -> aStatusMenu.setText(inGround.getText()));
        MenuItem onGround = new MenuItem("on_ground");
        onGround.setOnAction(e -> aStatusMenu.setText(onGround.getText()));
        aStatusMenu.getItems().addAll(inGround, onGround);

        row3.getChildren().addAll(supportAirline, airlineMenu, airplaneStatus, aStatusMenu);

        HBox row4 = new HBox(20);
        Label nextTime = new Label("next_time");
        TextField nextTimeType = new TextField();


        row4.getChildren().addAll(nextTime, nextTimeType);

        HBox row5 = new HBox(20);
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(pilot()));

        Button callAddPerson = new Button("Add");
        callAddPerson.setPrefSize(200, 50);
        callAddPerson.setOnAction(e -> {

            try {
                Object str_flightID = flightType.getText().equals("") ? null : flightType.getText();
                Object str_supportTail = supportTailType.getText().equals("") ? null : supportTailType.getText();
                Object str_routeID = routeIDType.getText().equals("") ? null : routeIDType.getText();
                Object int_progress = progressType.getText().equals("") ? null : Integer.parseInt(progressType.getText());
                Object str_supportAirline = airlineMenu.getText().equals("") ? null : airlineMenu.getText();
                Object str_airplaneStatus = aStatusMenu.getText().equals("") ? null : aStatusMenu.getText();
                Object str_nextTime = nextTimeType.getText().equals("") ? null : nextTimeType.getText();

                System.out.println("Status: " + (String) str_airplaneStatus);

                DatabaseConnect.usedOfferFlight(str_flightID, str_supportTail, str_routeID, int_progress, str_supportAirline, str_airplaneStatus, str_nextTime);
                alert(0, "View pilot_licenses Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct grant_pilot_license form");
            }

        });

        row5.getChildren().addAll(cancel, callAddPerson);


        offerFlightPane.addRow(0, row1);
        offerFlightPane.addRow(1, row2);
        offerFlightPane.addRow(2, row3);
        offerFlightPane.addRow(3, row4);
        offerFlightPane.addRow(4, row5);

        return offerFlightPane;
    }

    public GridPane fLandPane() {
        GridPane fLandPane = getGridPane();

        HBox row1 = new HBox(20);
        Label flightID = new Label("flightID");
        TextField putFlightID = new TextField();

        row1.getChildren().addAll(flightID, putFlightID);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(flight()));

        Button callFLand = new Button("Update");
        callFLand.setPrefSize(200, 50);
        callFLand.setOnAction(e -> {

            try {
                Object str_flightID = putFlightID.getText().equals("") ? null : putFlightID.getText();

                DatabaseConnect.useFlightLand(str_flightID);
                alert(0, "View flight, pilot, and passenger Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct flight_land() form");
            }

        });

        row2.getChildren().addAll(cancel, callFLand);

        fLandPane.addRow(0, row1);
        fLandPane.addRow(1, row2);

        return fLandPane;
    }
    public GridPane fOffPane() {
        GridPane fOffPane = getGridPane();

        HBox row1 = new HBox(20);
        Label flightID = new Label("flightID");
        TextField putFlightID = new TextField();

        row1.getChildren().addAll(flightID, putFlightID);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(flight()));

        Button callFOff = new Button("Update");
        callFOff.setPrefSize(200, 50);
        callFOff.setOnAction(e -> {

            try {
                Object str_flightID = putFlightID.getText().equals("") ? null : putFlightID.getText();

                DatabaseConnect.useFlightOff(str_flightID);
                alert(0, "View flight Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct flight_off() form");
            }

        });

        row2.getChildren().addAll(cancel, callFOff);

        fOffPane.addRow(0, row1);
        fOffPane.addRow(1, row2);

        return fOffPane;
    }

    public GridPane retireFlightPane() {
        GridPane retireFlightPane = getGridPane();

        HBox row1 = new HBox(20);
        Label flightID = new Label("flightID");
        TextField putFlightID = new TextField();

        row1.getChildren().addAll(flightID, putFlightID);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(flight()));

        Button callRetireF = new Button("Update");
        callRetireF.setPrefSize(200, 50);
        callRetireF.setOnAction(e -> {

            try {
                Object str_flightID = putFlightID.getText().equals("") ? null : putFlightID.getText();

                DatabaseConnect.useRetireFlight(str_flightID);
                alert(0, "View flight Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct retire_flight() form");
            }

        });

        row2.getChildren().addAll(cancel, callRetireF);

        retireFlightPane.addRow(0, row1);
        retireFlightPane.addRow(1, row2);

        return retireFlightPane;
    }


    public GridPane pilot() {
        GridPane pilot = getGridPane();

        Button grant_pilot = new Button("Grant Pilot License");
        grant_pilot.setPrefSize(200, 50);

        Button assign_pilot = new Button("Assign Pilot");
        assign_pilot.setPrefSize(200, 50);
        Button recycle_crew = new Button("Recycle Crew");
        recycle_crew.setPrefSize(200, 50);
        Button remove_pilot = new Button("Remove Pilot Role");
        remove_pilot.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        pilot.addRow(0, grant_pilot, assign_pilot);
        pilot.addRow(1, recycle_crew, remove_pilot);
        pilot.addRow(2, returnBtn);

        GridPane grantPilotPane = grantPilotPane();
        Scene grantPilotScene = new Scene(grantPilotPane, 720, 480);
        GridPane assignPilotPane = assignPilotPane();
        Scene assignPilotScene = new Scene(assignPilotPane, 720, 480);
        GridPane recycleCrewPane = recycleCrewPane();
        Scene recycleCrewScene = new Scene(recycleCrewPane, 720, 480);
        GridPane removePilotPane = removePilotPane();
        Scene removePilotScene = new Scene(removePilotPane, 720, 480);

        Stage primaryStage = stageList.get(0);
        grant_pilot.setOnAction(e -> primaryStage.setScene(grantPilotScene));
        assign_pilot.setOnAction(e -> primaryStage.setScene(assignPilotScene ));
        recycle_crew.setOnAction(e -> primaryStage.setScene(recycleCrewScene ));
        remove_pilot.setOnAction(e -> primaryStage.setScene(removePilotScene ));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return pilot;
    }

    public GridPane grantPilotPane() {
        GridPane grantPilotPane = getGridPane();

        HBox row1 = new HBox(20);
        Label personID = new Label("personID");
        MenuButton personMenu = new MenuButton();
        ArrayList<String> personList = DatabaseConnect.getPersonID();
        for (int i = 0; i < personList.size(); i++) {
            MenuItem personItem = new MenuItem(personList.get(i));
            personItem.setOnAction(e -> personMenu.setText(personItem.getText()));
            personMenu.getItems().add(personItem);
        }

        row1.getChildren().addAll(personID, personMenu);


        HBox row2 = new HBox(20);
        Label license = new Label("License type");
        MenuButton licenseMenu = new MenuButton();
        ArrayList<String> licenseList = DatabaseConnect.getLicenseType();
        for (int i = 0; i < licenseList.size(); i++) {
            MenuItem licItem = new MenuItem(licenseList.get(i));
            licItem.setOnAction(e -> licenseMenu.setText(licItem.getText()));
            licenseMenu.getItems().add(licItem);
        }

        row2.getChildren().addAll(license, licenseMenu);

        HBox row3 = new HBox(20);
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(pilot()));

        Button callAddPerson = new Button("Add");
        callAddPerson.setPrefSize(200, 50);
        callAddPerson.setOnAction(e -> {

            try {
                Object str_personID = personMenu.getText();
                Object type_license = licenseMenu.getText();

                DatabaseConnect.usedGrantPilotLicense(str_personID, type_license);
                alert(0, "View pilot_licenses Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct grant_pilot_license form");
            }

        });

        row3.getChildren().addAll(cancel, callAddPerson);


        grantPilotPane.addRow(0, row1);
        grantPilotPane.addRow(1, row2);
        grantPilotPane.addRow(2, row3);

        return grantPilotPane;
    }

    public GridPane assignPilotPane() {
        GridPane assignPilotPane = getGridPane();

        HBox row1 = new HBox(20);
        Label personID = new Label("personID");
        MenuButton personMenu = new MenuButton();
        ArrayList<String> personList = DatabaseConnect.getPersonID();
        for (int i = 0; i < personList.size(); i++) {
            MenuItem personItem = new MenuItem(personList.get(i));
            personItem.setOnAction(e -> personMenu.setText(personItem.getText()));
            personMenu.getItems().add(personItem);
        }

        row1.getChildren().addAll(personID, personMenu);


        HBox row2 = new HBox(20);
        Label flightID = new Label("flightID");
        MenuButton fIdMenu = new MenuButton();
        ArrayList<String> fIdList = DatabaseConnect.getFlightID();
        for (int i = 0; i < fIdList.size(); i++) {
            MenuItem depItem = new MenuItem(fIdList.get(i));
            depItem.setOnAction(e -> fIdMenu.setText(depItem.getText()));
            fIdMenu.getItems().add(depItem);
        }

        row2.getChildren().addAll(flightID, fIdMenu);

        HBox row3 = new HBox(20);
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(pilot()));

        Button callAddPerson = new Button("Assign");
        callAddPerson.setPrefSize(200, 50);
        callAddPerson.setOnAction(e -> {

            try {
                Object str_personID = personMenu.getText();
                Object str_flightID = fIdMenu.getText();

                DatabaseConnect.useAssignPilot(str_flightID, str_personID);
                alert(0, "View Pilot and Person Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct assign_pilot form");
            }

        });

        row3.getChildren().addAll(cancel, callAddPerson);


        assignPilotPane.addRow(0, row1);
        assignPilotPane.addRow(1, row2);
        assignPilotPane.addRow(2, row3);

        return assignPilotPane;
    }
    public GridPane recycleCrewPane() {
        GridPane recycleCrewPane = getGridPane();

        HBox row1 = new HBox(20);
        Label flightID = new Label("flightID");
        MenuButton fIdMenu = new MenuButton();
        ArrayList<String> fIdList = DatabaseConnect.getFlightID();
        for (int i = 0; i < fIdList.size(); i++) {
            MenuItem depItem = new MenuItem(fIdList.get(i));
            depItem.setOnAction(e -> fIdMenu.setText(depItem.getText()));
            fIdMenu.getItems().add(depItem);
        }

        row1.getChildren().addAll(flightID, fIdMenu);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(pilot()));

        Button callRecycle = new Button("Recycle Crew");
        callRecycle.setPrefSize(200, 50);
        callRecycle.setOnAction(e -> {

            try {
                Object str_flightID = fIdMenu.getText().equals("") ? null : fIdMenu.getText();

                DatabaseConnect.useRecycleCrew(str_flightID);
                alert(0, "View Person and Pilot Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct recycle_crew() form");
            }

        });

        row2.getChildren().addAll(cancel, callRecycle);

        recycleCrewPane.addRow(0, row1);
        recycleCrewPane.addRow(1, row2);

        return recycleCrewPane;
    }
    public GridPane removePilotPane() {
        GridPane removePilotPane = getGridPane();

        HBox row1 = new HBox(20);
        Label personID = new Label("personID");
        MenuButton personMenu = new MenuButton();
        ArrayList<String> personList = DatabaseConnect.getPersonID();
        for (int i = 0; i < personList.size(); i++) {
            MenuItem personItem = new MenuItem(personList.get(i));
            personItem.setOnAction(e -> personMenu.setText(personItem.getText()));
            personMenu.getItems().add(personItem);
        }

        row1.getChildren().addAll(personID, personMenu);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(pilot()));

        Button callremoveP = new Button("Assign");
        callremoveP.setPrefSize(200, 50);
        callremoveP.setOnAction(e -> {

            try {
                Object str_personID = personMenu.getText();

                DatabaseConnect.useRemovePilotRole(str_personID);
                alert(0, "View Pilot and Pilot_licenses Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct remove_pilot_role form");
            }

        });

        row2.getChildren().addAll(cancel, callremoveP);


        removePilotPane.addRow(0, row1);
        removePilotPane.addRow(1, row2);

        return removePilotPane;
    }

    public GridPane people() {
        GridPane people = getGridPane();

        Button add_person = new Button("Add People");
        add_person.setPrefSize(200, 50);
        Button passBoard = new Button("Passengers Board");
        passBoard.setPrefSize(200, 50);
        Button passDisemBark = new Button("Passengers Disembark");
        passDisemBark.setPrefSize(200, 50);
        Button removePass = new Button("Remove Passenger Role");
        removePass.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        people.addRow(0, add_person, removePass);
        people.addRow(1, passBoard, passDisemBark);
        people.addRow(2, returnBtn);

        GridPane addPeoplePane = addPeoplePane();
        Scene addPeopleScene = new Scene(addPeoplePane, 720, 480);
        GridPane passBoardPane = passBoardPane();
        Scene passBoardScene = new Scene(passBoardPane, 720, 480);
        GridPane passDisemBarkPane = passDisemBarkPane();
        Scene passDisemBarkScene = new Scene(passDisemBarkPane, 720, 480);
        GridPane removePassPane = removePassPane();
        Scene removePassScene = new Scene(removePassPane, 720, 480);

        Stage primaryStage = stageList.get(0);
        add_person.setOnAction(e -> primaryStage.setScene(addPeopleScene));
        passBoard.setOnAction(e -> primaryStage.setScene(passBoardScene));
        passDisemBark.setOnAction(e -> primaryStage.setScene(passDisemBarkScene));
        removePass.setOnAction(e -> primaryStage.setScene(removePassScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));


        return people;
    }
    public GridPane removePassPane() {
        GridPane removePassPane = getGridPane();

        HBox row1 = new HBox(20);
        Label personID = new Label("personID");
        MenuButton personMenu = new MenuButton();
        ArrayList<String> personList = DatabaseConnect.getPersonID();
        for (int i = 0; i < personList.size(); i++) {
            MenuItem personItem = new MenuItem(personList.get(i));
            personItem.setOnAction(e -> personMenu.setText(personItem.getText()));
            personMenu.getItems().add(personItem);
        }

        row1.getChildren().addAll(personID, personMenu);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(people()));

        Button callRemovePassenger = new Button("Remove");
        callRemovePassenger.setPrefSize(200, 50);
        callRemovePassenger.setOnAction(e -> {

            try {
                Object str_personID = personMenu.getText();

                DatabaseConnect.useRemovePassengerRole(str_personID);
                alert(0, "View Person Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct remove_passenger_role() form");
            }

        });

        row2.getChildren().addAll(cancel, callRemovePassenger);

        removePassPane.addRow(0, row1);
        removePassPane.addRow(1, row2);

        return removePassPane;
    }

    public GridPane addPeoplePane() {
        GridPane addPeoplePane = getGridPane();

        HBox row1 = new HBox(20);
        Label personID = new Label("personID");
        TextField personIDType = new TextField();

        Label experience = new Label("experience");
        TextField exp_type = new TextField();

        row1.getChildren().addAll(personID, personIDType, experience, exp_type);

        HBox row2 = new HBox(20);
        Label first_name = new Label("first_name");
        TextField first_nameType = new TextField();

        Label airline = new Label("airline");
        TextField airlineType = new TextField();

        row2.getChildren().addAll(first_name, first_nameType, airline, airlineType);

        HBox row3 = new HBox(20);
        Label last_name = new Label("last_name");
        TextField last_nameType = new TextField();

        Label tail = new Label("tail");
        TextField tailType = new TextField();

        row3.getChildren().addAll(last_name, last_nameType, tail, tailType);

        HBox row4 = new HBox(20);
        Label tax = new Label("taxID");
        TextField taxType = new TextField();

        Label miles = new Label("miles");
        TextField milesType = new TextField();

        row4.getChildren().addAll(tax, taxType, miles, milesType);

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
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(people()));

        Button callAddPerson = new Button("Add");
        callAddPerson.setPrefSize(200, 50);
        callAddPerson.setOnAction(e -> {

            try {
                Object str_personID = personIDType.getText().equals("") ? null : personIDType.getText();
                Object int_exp = exp_type.getText().equals("") ? null : Integer.parseInt(exp_type.getText());
                Object str_firstname = first_nameType.getText().equals("") ? null : first_nameType.getText();
                Object str_airline = airlineType.getText().equals("") ? null : airlineType.getText();
                Object str_lastname = last_nameType.getText().equals("") ? null : last_nameType.getText();
                Object str_tail = tailType.getText().equals("") ? null : tailType.getText();
                Object str_tax = taxType.getText().equals("") ? null : taxType.getText();
                Object int_mile = milesType.getText().equals("") ? null : Integer.parseInt(milesType.getText());
                Object location_id = locMenu.getText().equals("") ? null : locMenu.getText();


                DatabaseConnect.useAddPerson(str_personID, str_firstname, str_lastname, location_id, str_tax,
                        int_exp, str_airline, str_tail, int_mile);
                alert(0, "View Person Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct add_person form");
            }

        });

        row6.getChildren().addAll(cancel, callAddPerson);


        addPeoplePane.addRow(0, row1);
        addPeoplePane.addRow(1, row2);
        addPeoplePane.addRow(2, row3);
        addPeoplePane.addRow(3, row4);
        addPeoplePane.addRow(4, row5);
        addPeoplePane.addRow(5, row6);


        return addPeoplePane;
    }

    public GridPane passBoardPane() {
        GridPane passBoardPane = getGridPane();

        HBox row1 = new HBox(20);
        Label flightID = new Label("flightID");
        MenuButton fIdMenu = new MenuButton();
        ArrayList<String> fIdList = DatabaseConnect.getFlightID();
        for (int i = 0; i < fIdList.size(); i++) {
            MenuItem depItem = new MenuItem(fIdList.get(i));
            depItem.setOnAction(e -> fIdMenu.setText(depItem.getText()));
            fIdMenu.getItems().add(depItem);
        }

        row1.getChildren().addAll(flightID, fIdMenu);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(people()));

        Button callPassBoard = new Button("Continue");
        callPassBoard.setPrefSize(200, 50);
        callPassBoard.setOnAction(e -> {

            try {
                Object str_flightID = fIdMenu.getText().equals("") ? null : fIdMenu.getText();

                DatabaseConnect.usePassBoard(str_flightID);
                alert(0, "View Person Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct passengers_board() form");
            }

        });

        row2.getChildren().addAll(cancel, callPassBoard);

        passBoardPane.addRow(0, row1);
        passBoardPane.addRow(1, row2);

        return passBoardPane;
    }

    public GridPane passDisemBarkPane() {
        GridPane passDisemBarkPane = getGridPane();

        HBox row1 = new HBox(20);
        Label flightID = new Label("flightID");
        MenuButton fIdMenu = new MenuButton();
        ArrayList<String> fIdList = DatabaseConnect.getFlightID();
        for (int i = 0; i < fIdList.size(); i++) {
            MenuItem depItem = new MenuItem(fIdList.get(i));
            depItem.setOnAction(e -> fIdMenu.setText(depItem.getText()));
            fIdMenu.getItems().add(depItem);
        }

        row1.getChildren().addAll(flightID, fIdMenu);

        HBox row2 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(people()));

        Button callPassBoard = new Button("Continue");
        callPassBoard.setPrefSize(200, 50);
        callPassBoard.setOnAction(e -> {

            try {
                Object str_flightID = fIdMenu.getText().equals("") ? null : fIdMenu.getText();

                DatabaseConnect.usePassDisembark(str_flightID);
                alert(0, "View Person Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct passengers_disembark() form");
            }

        });

        row2.getChildren().addAll(cancel, callPassBoard);

        passDisemBarkPane.addRow(0, row1);
        passDisemBarkPane.addRow(1, row2);

        return passDisemBarkPane;
    }



    public GridPane airport() {
        GridPane airport = getGridPane();

        Button add_airport = new Button("Add Airport");
        add_airport.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        airport.addRow(0, add_airport);
        airport.addRow(1, returnBtn);

        GridPane addAirportPane = addAirportPane();
        Scene addAirportScene = new Scene(addAirportPane, 720, 480);

        Stage primaryStage = stageList.get(0);
        add_airport.setOnAction(e -> primaryStage.setScene(addAirportScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return airport;
    }

    public GridPane addAirportPane() {
        GridPane addAirportPane = getGridPane();

        HBox row1 = new HBox(20);
        Label airportID = new Label("airportID");
        TextField airportType = new TextField();

        row1.getChildren().addAll(airportID, airportType);

        HBox row2 = new HBox(20);
        Label airportName = new Label("airport_name");
        TextField airportNameType = new TextField();

        row2.getChildren().addAll(airportName, airportNameType);

        HBox row3 = new HBox(20);
        Label city = new Label("City");
        TextField cityType = new TextField();

        row3.getChildren().addAll(city, cityType);

        HBox row4 = new HBox(20);
        Label state = new Label("State");
        TextField stateType = new TextField();

        row4.getChildren().addAll(state, stateType);

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
        Button cancel = new Button("Cancel");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(airport()));

        Button callAddAirport = new Button("Add");
        callAddAirport.setPrefSize(200, 50);
        callAddAirport.setOnAction(e -> {

            try {
                // airportType, airportNameType, cityType, stateType, locMenu
                Object str_airportID = airportType.getText().equals("") ? null : airportType.getText();
                Object str_airportName = airportNameType.getText().equals("") ? null : airportNameType.getText();
                Object str_city = cityType.getText().equals("") ? null : cityType.getText();
                Object str_state = stateType.getText().equals("") ? null : stateType.getText();
                Object str_locID = locMenu.getText().equals("") ? null : locMenu.getText();


                DatabaseConnect.usedAddAirport(str_airportID, str_airportName, str_city, str_state, str_locID);
                alert(0, "View Airport Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct add_airport form");
            }

        });

        row6.getChildren().addAll(cancel, callAddAirport);


        addAirportPane.addRow(0, row1);
        addAirportPane.addRow(1, row2);
        addAirportPane.addRow(2, row3);
        addAirportPane.addRow(3, row4);
        addAirportPane.addRow(4, row5);
        addAirportPane.addRow(5, row6);


        return addAirportPane;
    }

    public GridPane airplane() {
        GridPane airplane = getGridPane();

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
        GridPane addAirplanePane = getGridPane();

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

            try {
                Object airplaneID = aIDList.getText().equals("") ? null : aIDList.getText();
                Object plane_type = pType.getText().equals("") ? null : pType.getText();
                Object tail_num = tNum.getText().equals("") ? null : tNum.getText();
                Object getSkidText = skidText.getText().equals("") ? null : skidText.getText();
                Object skidVal;
                if ( getSkidText.equals("True") || getSkidText.equals("true") || getSkidText.equals("TRUE") ) {
                    skidVal = true;
                } else if (getSkidText.equals("False") || getSkidText.equals("false") || getSkidText.equals("FALSE")) {
                    skidVal = false;
                } else {
                    skidVal = null;
                }
                Object seat_capacity = seatCapNum.getText().equals("") ? null : Integer.parseInt(seatCapNum.getText());
                Object propVal = prop.getText().equals("") ? null : Integer.parseInt(prop.getText());
                Object speedVal = speedNum.getText().equals("") ? null : Integer.parseInt(speedNum.getText());
                Object jet_engines = jetNum.getText().equals("") ? null :Integer.parseInt(jetNum.getText());
                String location_id = locMenu.getText().equals("") ? null : locMenu.getText();


                DatabaseConnect.useAddAirplane(airplaneID, tail_num, seat_capacity, speedVal, location_id, plane_type,
                        skidVal, propVal, jet_engines);
                alert(0, "View Airplane Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct add_airplane form");
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

    public GridPane ticket() {
        GridPane airport = getGridPane();

        Button purchaseB = new Button("Purchase Ticket & Seat");
        purchaseB.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        airport.addRow(0, purchaseB);
        airport.addRow(1, returnBtn);

        GridPane purchaseTicketPane = purchaseTicketPane();
        Scene purchaseTicketScene = new Scene(purchaseTicketPane, 720, 480);

        Stage primaryStage = stageList.get(0);
        purchaseB.setOnAction(e -> primaryStage.setScene(purchaseTicketScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return airport;
    }

    public GridPane purchaseTicketPane() {
        GridPane purchaseTicketPane = getGridPane();

        HBox row1 = new HBox(20);
        Label tID = new Label("ticketID");
        TextField putTicket = new TextField();

        Label seatNum = new Label("seat_num");
        TextField putSeatNum = new TextField();

        row1.getChildren().addAll(tID, putTicket, seatNum, putSeatNum);

        HBox row2 = new HBox(20);
        Label cost = new Label("cost");
        TextField putCost = new TextField();

        row2.getChildren().addAll(cost, putCost);

        HBox row3 = new HBox(20);
        Label carrier = new Label("carrier");
        TextField putCarrier = new TextField();

        row3.getChildren().addAll(carrier, putCarrier);

        HBox row4 = new HBox(20);
        Label customer = new Label("customer");
        TextField putCustomer = new TextField();

        row4.getChildren().addAll(customer, putCustomer);

        HBox row5 = new HBox(20);
        Label deplane = new Label("deplane");
        MenuButton depMenu = new MenuButton();
        ArrayList<String> depList = DatabaseConnect.getAirportID();
        for (int i = 0; i < depList.size(); i++) {
            MenuItem depItem = new MenuItem(depList.get(i));
            depItem.setOnAction(e -> depMenu.setText(depItem.getText()));
            depMenu.getItems().add(depItem);
        }

        row5.getChildren().addAll(deplane, depMenu);

        HBox row6 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(ticket()));

        Button callpurchaseT = new Button("Continue");
        callpurchaseT.setPrefSize(200, 50);
        callpurchaseT.setOnAction(e -> {

            try {
                Object str_ticketID = putTicket.getText().equals("") ? null : putTicket.getText();
                Object int_cost = putCost.getText().equals("") ? null : Integer.parseInt(putCost.getText());
                Object str_carrier = putCarrier.getText().equals("") ? null : putCarrier.getText();
                Object str_customer = putCustomer.getText().equals("") ? null : putCustomer.getText();
                Object str_deplane = depMenu.getText().equals("") ? null : depMenu.getText();
                Object str_seatNum = putSeatNum.getText().equals("") ? null : putSeatNum.getText();

                DatabaseConnect.usePurchaseTicket(str_ticketID,int_cost, str_carrier, str_customer, str_deplane, str_seatNum);
                alert(0, "View Tickets Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct purchase_ticket_and_seat() form");
            }

        });

        row6.getChildren().addAll(cancel, callpurchaseT);


        purchaseTicketPane.addRow(0, row1);
        purchaseTicketPane.addRow(1, row2);
        purchaseTicketPane.addRow(2, row3);
        purchaseTicketPane.addRow(3, row4);
        purchaseTicketPane.addRow(4, row5);
        purchaseTicketPane.addRow(5, row6);


        return purchaseTicketPane;
    }

    public GridPane route() {
        GridPane route = getGridPane();

        Button addLeg = new Button("Add/Update Leg");
        addLeg.setPrefSize(200, 50);
        Button startRoute = new Button("Start Route");
        startRoute.setPrefSize(200, 50);
        Button extendRoute = new Button("Extend Route");
        extendRoute.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        route.addRow(0, addLeg);
        route.addRow(1, startRoute, extendRoute);
        route.addRow(2, returnBtn);

        GridPane addLegPane = addLegPane();
        Scene addLegScene = new Scene(addLegPane, 720, 480);
        GridPane startRoutePane = startRoutePane();
        Scene startRouteScene = new Scene(startRoutePane, 720, 480);
        GridPane extendRoutePane = extendRoutePane();
        Scene extendRouteScene = new Scene(extendRoutePane, 720, 480);

        Stage primaryStage = stageList.get(0);
        addLeg.setOnAction(e -> primaryStage.setScene(addLegScene));
        startRoute.setOnAction(e -> primaryStage.setScene(startRouteScene));
        extendRoute.setOnAction(e -> primaryStage.setScene(extendRouteScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return route;
    }

    public GridPane startRoutePane() {
        GridPane startRoutePane = getGridPane();

        HBox row1 = new HBox(20);
        Label routeID = new Label("routeID");
        TextField putRouteID = new TextField();

        row1.getChildren().addAll(routeID, putRouteID);

        HBox row2 = new HBox(20);
        Label legID = new Label("legID");
        MenuButton legMenu = new MenuButton();
        ArrayList<String> legList = DatabaseConnect.getLegID();
        for (int i = 0; i < legList.size(); i++) {
            MenuItem depItem = new MenuItem(legList.get(i));
            depItem.setOnAction(e -> legMenu.setText(depItem.getText()));
            legMenu.getItems().add(depItem);
        }

        row2.getChildren().addAll(legID, legMenu);

        HBox row3 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(route()));

        Button callSr = new Button("Assign");
        callSr.setPrefSize(200, 50);
        callSr.setOnAction(e -> {

            try {
                Object str_routeID = putRouteID.getText().equals("") ? null : putRouteID.getText();
                Object str_legID = legMenu.getText().equals("") ? null : legMenu.getText();

                DatabaseConnect.useStartRoute(str_routeID, str_legID);
                alert(0, "View route and route_path Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct start_route() form");
            }

        });

        row3.getChildren().addAll(cancel, callSr);

        startRoutePane.addRow(0, row1);
        startRoutePane.addRow(1, row2);
        startRoutePane.addRow(2, row3);


        return startRoutePane;
    }

    public GridPane extendRoutePane() {
        GridPane extendRoutePane = getGridPane();

        HBox row1 = new HBox(20);
        Label routeID = new Label("routeID");
        TextField putRouteID = new TextField();

        row1.getChildren().addAll(routeID, putRouteID);


        HBox row2 = new HBox(20);
        Label legID = new Label("legID");
        MenuButton legMenu = new MenuButton();
        ArrayList<String> legList = DatabaseConnect.getLegID();
        for (int i = 0; i < legList.size(); i++) {
            MenuItem depItem = new MenuItem(legList.get(i));
            depItem.setOnAction(e -> legMenu.setText(depItem.getText()));
            legMenu.getItems().add(depItem);
        }

        row2.getChildren().addAll(legID, legMenu);

        HBox row3 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(route()));

        Button callEr = new Button("Assign");
        callEr.setPrefSize(200, 50);
        callEr.setOnAction(e -> {

            try {
                Object str_routeID = putRouteID.getText().equals("") ? null : putRouteID.getText();
                Object str_legID = legMenu.getText().equals("") ? null : legMenu.getText();

                DatabaseConnect.useExtendRoute(str_routeID, str_legID);
                alert(0, "View route and route_path Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct extend_route() form");
            }

        });

        row3.getChildren().addAll(cancel, callEr);

        extendRoutePane.addRow(0, row1);
        extendRoutePane.addRow(1, row2);
        extendRoutePane.addRow(2, row3);


        return extendRoutePane;
    }

    public GridPane addLegPane() {
        GridPane addLegPane = getGridPane();

        HBox row1 = new HBox(20);
        Label lID = new Label("legID");
        TextField putLegID = new TextField();

        row1.getChildren().addAll(lID, putLegID);

        HBox row2 = new HBox(20);
        Label distance = new Label("Distance");
        TextField putDistance = new TextField();

        row2.getChildren().addAll(distance, putDistance);

        HBox row3 = new HBox(20);
        Label departure = new Label("Departure");
        MenuButton depMenu = new MenuButton();
        ArrayList<String> depList = DatabaseConnect.getAirportID();
        for (int i = 0; i < depList.size(); i++) {
            MenuItem depItem = new MenuItem(depList.get(i));
            depItem.setOnAction(e -> depMenu.setText(depItem.getText()));
            depMenu.getItems().add(depItem);
        }

        row3.getChildren().addAll(departure, depMenu);

        HBox row4 = new HBox(20);
        Label arrival = new Label("Arrival");
        MenuButton arrMenu = new MenuButton();
        ArrayList<String> arrList = DatabaseConnect.getAirportID();
        for (int i = 0; i < arrList.size(); i++) {
            MenuItem arrItem = new MenuItem(arrList.get(i));
            arrItem.setOnAction(e ->arrMenu.setText(arrItem.getText()));
            arrMenu.getItems().add(arrItem);
        }

        row4.getChildren().addAll(arrival, arrMenu);

        HBox row5 = new HBox(20);
        Button cancel = new Button("Return to previous");
        cancel.setPrefSize(200, 50);
        cancel.setOnAction(e -> cancel.getScene().setRoot(route()));

        Button callAddLeg = new Button("Assign");
        callAddLeg.setPrefSize(200, 50);
        callAddLeg.setOnAction(e -> {

            try {
                Object str_legID = putLegID.getText().equals("") ? null : putLegID.getText();
                Object int_distance = putDistance.getText().equals("") ? null : Integer.parseInt(putDistance.getText());
                Object str_departure = depMenu.getText().equals("") ? null : depMenu.getText();
                Object str_arrival = arrMenu.getText().equals("") ? null : arrMenu.getText();

                DatabaseConnect.useAddUpdateLeg(str_legID,int_distance, str_departure, str_arrival);
                alert(0, "View Routes Table to check the update");
            } catch (Exception exception) {
                alert(1, "Retry with correct add_update_leg() form");
            }

        });

        row5.getChildren().addAll(cancel, callAddLeg);


        addLegPane.addRow(0, row1);
        addLegPane.addRow(1, row2);
        addLegPane.addRow(2, row3);
        addLegPane.addRow(3, row4);
        addLegPane.addRow(4, row5);


        return addLegPane;
    }

    public GridPane getGridPane() {
        GridPane newGridPane = new GridPane();
        newGridPane.setPadding(new Insets(20, 20, 20, 20));
        newGridPane.setHgap(70);
        newGridPane.setVgap(30);
        newGridPane.setAlignment(Pos.CENTER);
        return newGridPane;
    }

    public void alert(int type, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        if (type == 0) {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
        } else if (type == 1) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        alert.setContentText(message);
        alert.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
