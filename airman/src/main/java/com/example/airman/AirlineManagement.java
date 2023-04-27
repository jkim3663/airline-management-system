package com.example.airman;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

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

        GridPane pilotPane = pilot();
        Scene pilotScene = new Scene(pilotPane, 720, 480);

        GridPane peoplePane = people();
        Scene peopleScene = new Scene(peoplePane, 720, 480);

        GridPane airportPane = airport();
        Scene airportScene = new Scene(airportPane, 720, 480);

        GridPane flightPane = flight();
        Scene flightScene = new Scene(flightPane, 720, 480);

        GridPane viewSimPane = viewSimPane();
        Scene viewSimScene = new Scene(viewSimPane, 720, 480);

        sceneList.add(airplaneScene);       // idx 1: airplaneScene
        sceneList.add(pilotScene);          // idx 2: pilotScene
        sceneList.add(peopleScene);         // idx 3: personScene
        sceneList.add(airportScene);        // idx 5: airportScene

        Stage primaryStage = stageList.get(0);
        airplanes.setOnAction(e -> primaryStage.setScene(airplaneScene));
        airports.setOnAction(e -> primaryStage.setScene(airportScene));
        people.setOnAction(e -> primaryStage.setScene(peopleScene));
        pilots.setOnAction(e -> primaryStage.setScene(pilotScene));
        flights.setOnAction(e -> primaryStage.setScene(flightScene));
        viewsAndSimulation.setOnAction(e -> primaryStage.setScene(viewSimScene));

        return menu;
    }

    /**
     * This is going to contain all buttons to views
     */
    public GridPane viewSimPane() {
        GridPane viewSimPane = new GridPane();
        viewSimPane.setPadding(new Insets(20, 20, 20, 20));
        viewSimPane.setHgap(70);
        viewSimPane.setVgap(70);

        Button flightsInAir = new Button("View flights_in_air");
        flightsInAir.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        viewSimPane.addRow(0, flightsInAir);
        viewSimPane.addRow(1, returnBtn);

        GridPane flightsInTheAir = flightsInTheAir();
        Scene query19 = new Scene(flightsInTheAir, 720, 480);

        Stage primaryStage = stageList.get(0);
        flightsInAir.setOnAction(e -> primaryStage.setScene(query19));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));


        return viewSimPane;
    }

    public GridPane flightsInTheAir() {
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
            flightsInTheAir.addRow(i, row);
        }

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        flightsInTheAir.addRow(flightsInTheAir.getRowCount(), returnBtn);

        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(viewSimPane()));

        return flightsInTheAir;
    }

    public GridPane flight() {
        GridPane flight = new GridPane();
        flight.setPadding(new Insets(20, 20, 20, 20));
        flight.setHgap(70);
        flight.setVgap(70);

        Button offer_flight = new Button("Offer Flight");
        offer_flight.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        flight.addRow(0, offer_flight);
        flight.addRow(1, returnBtn);

        GridPane offerFlightPane = offerFlightPane();
        Scene offerFlightScene = new Scene(offerFlightPane, 720, 480);

        Stage primaryStage = stageList.get(0);
        offer_flight.setOnAction(e -> primaryStage.setScene(offerFlightScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return flight;
    }

    public GridPane offerFlightPane() {
        GridPane offerFlightPane = new GridPane();
        offerFlightPane.setPadding(new Insets(20, 20, 20, 20));
        offerFlightPane.setHgap(70);
        offerFlightPane.setVgap(30);


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

    public GridPane pilot() {
        GridPane pilot = new GridPane();
        pilot.setPadding(new Insets(20, 20, 20, 20));
        pilot.setHgap(70);
        pilot.setVgap(70);

        Button grant_pilot = new Button("Grant Pilot License");
        grant_pilot.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        pilot.addRow(0, grant_pilot);
        pilot.addRow(1, returnBtn);

        GridPane grantPilotPane = grantPilotPane();
        Scene grantPilotScene = new Scene(grantPilotPane, 720, 480);

        Stage primaryStage = stageList.get(0);
        grant_pilot.setOnAction(e -> primaryStage.setScene(grantPilotScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return pilot;
    }

    public GridPane grantPilotPane() {
        GridPane grantPilotPane = new GridPane();
        grantPilotPane.setPadding(new Insets(20, 20, 20, 20));
        grantPilotPane.setHgap(70);
        grantPilotPane.setVgap(30);


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

    public GridPane people() {
        GridPane people = new GridPane();
        people.setPadding(new Insets(20, 20, 20, 20));
        people.setHgap(70);
        people.setVgap(70);

        Button add_person = new Button("Add People");
        add_person.setPrefSize(200, 50);

        Button returnBtn = new Button("Return to previous");
        returnBtn.setPrefSize(200, 50);

        people.addRow(0, add_person);
        people.addRow(1, returnBtn);

        GridPane addPeoplePane = addPeoplePane();
        Scene addPeopleScene = new Scene(addPeoplePane, 720, 480);

        Stage primaryStage = stageList.get(0);
        add_person.setOnAction(e -> primaryStage.setScene(addPeopleScene));
        returnBtn.setOnAction(e -> returnBtn.getScene().setRoot(menuScene()));

        return people;
    }

    public GridPane addPeoplePane() {
        GridPane addPeoplePane = new GridPane();
        addPeoplePane.setPadding(new Insets(20, 20, 20, 20));
        addPeoplePane.setHgap(70);
        addPeoplePane.setVgap(30);

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

    public GridPane airport() {
        GridPane airport = new GridPane();
        airport.setPadding(new Insets(20, 20, 20, 20));
        airport.setHgap(70);
        airport.setVgap(70);

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
        GridPane addAirportPane = new GridPane();
        addAirportPane.setPadding(new Insets(20, 20, 20, 20));
        addAirportPane.setHgap(70);
        addAirportPane.setVgap(30);

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