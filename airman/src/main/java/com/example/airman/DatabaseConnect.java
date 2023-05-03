package com.example.airman;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class DatabaseConnect {

    private static String url = "jdbc:mysql://localhost:3306/flight_management";
    private static String username = "root";
    private static String password = "josh0205";

    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static void usedOfferFlight(Object flightID, Object supportTail, Object routeID, Object progress,
                                       Object supportAirline, Object airplaneStatus, Object nextTime) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL offer_flight(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            if (flightID == null) {
                stmt.setNull(1, Types.NULL);
            } else {
                stmt.setString(1, (String) flightID);
            }
            if (routeID == null) {
                stmt.setNull(2, Types.NULL);
            } else {
                stmt.setString(2, (String) routeID);
            }
            if (supportAirline == null) {
                stmt.setNull(3, Types.NULL);
            } else {
                stmt.setString(3, (String) supportAirline);
            }
            if (supportTail == null) {
                stmt.setNull(4, Types.NULL);
            } else {
                stmt.setString(4, (String) supportTail);
            }
            if (progress == null) {
                stmt.setNull(5, Types.NULL);
            } else {
                stmt.setInt(5, (int) progress);
            }
            if (airplaneStatus == null) {
                stmt.setNull(6, Types.NULL);
            } else {
                stmt.setString(6, (String) airplaneStatus);
            }
            if (nextTime == null) {
                stmt.setNull(7, Types.NULL);
            } else {
                stmt.setTime(7, strToDate((String) nextTime));
            }
            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Time strToDate(String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            LocalTime lt = LocalTime.parse(time, dtf);
            Time result = Time.valueOf(lt);
            return result;
        } catch (Exception exception) {
            System.out.println(exception);
            return null;
        }
    }

    public static void usedGrantPilotLicense(Object personID, Object license) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL grant_pilot_license(?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            if (personID == null) {
                stmt.setNull(1, Types.NULL);
            } else {
                stmt.setString(1, (String) personID);
            }
            if (license == null) {
                stmt.setNull(2, Types.NULL);
            } else {
                stmt.setString(2, (String) license);
            }
            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void useAddPerson(Object personID, Object firstName, Object lastName, Object locationID, Object taxID,
                                    Object experience, Object airlineID, Object tail_num, Object miles) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL add_person(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) personID);
            stmt.setString(2, (String) firstName);
            stmt.setString(3, (String) lastName);
            stmt.setString(4, (String) locationID);
            if (taxID == null) {
                stmt.setNull(5, Types.NULL);
                stmt.setNull(6, Types.NULL);
                stmt.setNull(7, Types.NULL);
                stmt.setNull(8, Types.NULL);
            } else {
                stmt.setString(5, (String) taxID);
                stmt.setInt(6, (int) experience);
                stmt.setString(7, (String) airlineID);
                stmt.setString(8, (String) tail_num);
            }

            if (miles == null) {
                stmt.setNull(9, Types.NULL);
            } else {
                stmt.setInt(9,  (int) miles);
            }

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void usedAddAirport(Object airportID, Object airportName, Object city, Object state, Object locationID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL add_airport(?, ?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) airportID);
            stmt.setString(2, (String) airportName);
            stmt.setString(3, (String) city);
            stmt.setString(4, (String) state);
            stmt.setString(5, (String) locationID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void useAddAirplane(Object airplaneID, Object tailNum, Object seatCap, Object speed, Object locationID,
                                      Object planeType, Object skids, Object propellers, Object jetEngines) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL add_airplane(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) airplaneID);
            stmt.setString(2, (String) tailNum);
            stmt.setInt(3, (int) seatCap);
            stmt.setInt(4, (int) speed);
            stmt.setString(5, (String) locationID);
            stmt.setString(6, (String) planeType);
            if (skids == null) {
                stmt.setNull(7, Types.NULL);
            } else {
                stmt.setBoolean(7, (boolean) skids);
            }
            if (propellers == null) {
                stmt.setNull(8, Types.NULL);
            } else {
                stmt.setInt(8, (int) propellers);
            }
            if (jetEngines == null) {
                stmt.setNull(9, Types.NULL);
            } else {
                stmt.setInt(9, (int) jetEngines);
            }

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void usePurchaseTicket(Object ticketID, Object cost, Object carrier, Object customer, Object deplane,
                                         Object seatNum) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL purchase_ticket_and_seat(?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) ticketID);
            stmt.setInt(2, (int) cost);
            stmt.setString(3, (String) carrier);
            stmt.setString(4, (String) customer);
            stmt.setString(5, (String) deplane);
            stmt.setString(6, (String) seatNum);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void useAddUpdateLeg(Object legID, Object distance, Object departure, Object arrival) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL add_update_leg(?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) legID);
            stmt.setInt(2, (int) distance);
            stmt.setString(3, (String) departure);
            stmt.setString(4, (String) arrival);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void useStartRoute(Object routeID, Object legID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL start_route(?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) routeID);
            stmt.setString(2, (String) legID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void useExtendRoute(Object routeID, Object legID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL extend_route(?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) routeID);
            stmt.setString(2, (String) legID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void useFlightLand(Object flightID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL flight_landing(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) flightID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void useFlightOff(Object flightID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL flight_takeoff(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) flightID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void usePassBoard(Object flightID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL passengers_board(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) flightID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void usePassDisembark(Object flightID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL passengers_disembark(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) flightID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void useAssignPilot(Object flightID, Object personID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL assign_pilot(?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) flightID);
            stmt.setString(2, (String) personID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void useRecycleCrew(Object flightID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL recycle_crew(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) flightID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void useRemovePilotRole(Object personID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL remove_pilot_role(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) personID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void useRetireFlight(Object flightID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL retire_flight(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) flightID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void useRemovePassengerRole(Object perosnID) {
        Connection con = connect();
        try {
            //here flight_management is database name, root is username and password
            String query = "{CALL remove_passenger_role(?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, (String) perosnID);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void getSimulCycle() {
        Connection con = connect();
        try {
            String query = "{CALL simulation_cycle()}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static ArrayList<ArrayList<String>> getTable(String tbName) {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        Connection con = connect();
        ArrayList<String> header = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            String query = "select * from " + tbName;
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int count = rsMetaData.getColumnCount();

            for (int i = 1; i <= count; i++) {
                header.add(rsMetaData.getColumnName(i));
            }

            ans.add(header);

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                for (int i = 0; i < header.size(); i++) {
                    String colVal = rs.getString(header.get(i));
                    row.add(colVal);
                }
                ans.add(row);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < ans.size(); i++) {
            for (int j = 0; j < ans.get(i).size(); j++) {
                System.out.println(ans.get(i).get(j));
            }
        }
        return ans;
    }

    public static ArrayList<ArrayList<String>> getAltAirports() {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        Connection con = connect();

        ArrayList<String> header = new ArrayList<>(Arrays.asList("City", "State", "Airports Count", "Airport Code List", "Airport Name List"));
        ans.add(header);

        try {
            Statement stmt = con.createStatement();
            String query = "select * from alternative_airports";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String city = rs.getString("city");
                String state = rs.getString("state");
                String num_airports = Integer.toString(rs.getInt("num_airports"));
                String airport_code_list = rs.getString("airport_code_list");
                String airport_name_list = rs.getString("airport_name_list");

                ArrayList<String> row = new ArrayList<>();
                row.add(city);
                row.add(state);
                row.add(num_airports);
                row.add(airport_code_list);
                row.add(airport_name_list);

                ans.add(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ans;
    }

    public static ArrayList<ArrayList<String>> getRouteSummary() {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        Connection con = connect();

        ArrayList<String> header = new ArrayList<>(Arrays.asList("Route", "Leg Count", "Leg Sequence", "Route Length",
                "Flights Count", "Flight List", "Airport Sequence"));
        ans.add(header);

        try {
            Statement stmt = con.createStatement();
            String query = "select * from route_summary";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String route = rs.getString("route");
                String num_legs = Integer.toString(rs.getInt("num_legs"));
                String leg_sequence = rs.getString("leg_sequence");
                String route_length = Integer.toString(rs.getInt("route_length"));
                String num_flights = Integer.toString(rs.getInt("num_flights"));
                String flight_list = rs.getString("flight_list");
                String airport_sequence = rs.getString("airport_sequence");

                ArrayList<String> row = new ArrayList<>();
                row.add(route);
                row.add(num_legs);
                row.add(leg_sequence);
                row.add(route_length);
                row.add(num_flights);
                row.add(flight_list);
                row.add(airport_sequence);

                ans.add(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ans;
    }

    public static ArrayList<ArrayList<String>> getPeopleInTheGround() {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        Connection con = connect();

        ArrayList<String> header = new ArrayList<>(Arrays.asList("Departure", "Airport", "Airport Name", "City",
                "State", "Pilot Count", "Passenger Count", "Joint Pilot-Passenger", "Person List"));
        ans.add(header);


        try {
            Statement stmt = con.createStatement();
            String query = "select * from people_on_the_ground";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String departing_from = rs.getString("departing_from");
                String airport_ID = rs.getString("airport");
                String airport_name = rs.getString("airport_name");
                String city = rs.getString("city");
                String state = rs.getString("state");
                String num_pilots = Integer.toString(rs.getInt("num_pilots"));
                String num_passengers = Integer.toString(rs.getInt("num_passengers"));
                String joint_pilots_passengers = Integer.toString(rs.getInt("joint_pilots_passengers"));
                String person_list = rs.getString("person_list");

                ArrayList<String> row = new ArrayList<>();
                row.add(departing_from);
                row.add(airport_ID);
                row.add(airport_name);
                row.add(city);
                row.add(state);
                row.add(num_pilots);
                row.add(num_passengers);
                row.add(joint_pilots_passengers);
                row.add(person_list);

                ans.add(row);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ans;
    }

    public static ArrayList<ArrayList<String>> getPeopleInTheAir() {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        Connection con = connect();

        ArrayList<String> header = new ArrayList<>(Arrays.asList("Departure", "Arrival", "Airplane Count", "Airplane List",
                "Flight List", "Earliest Arrival", "Latest Arrival", "Pilot Count", "Passenger Count", "Person Count", "Person List"));
        ans.add(header);


        try {
            Statement stmt = con.createStatement();
            String query = "select * from people_in_the_air";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String departing_from = rs.getString("departing_from");
                String arriving_at = rs.getString("arriving_at");
                String num_airplanes = Integer.toString(rs.getInt("num_airplanes"));
                String airplane_list = rs.getString("airplane_list");
                String flight_list = rs.getString("flight_list");
                String earliest_arrival = rs.getTime("earliest_arrival").toString();
                String latest_arrival = rs.getTime("latest_arrival").toString();
                String num_pilots = Integer.toString(rs.getInt("num_pilots"));
                String num_passengers = Integer.toString(rs.getInt("num_passengers"));
                String joint_pilots_passengers = Integer.toString(rs.getInt("joint_pilots_passengers"));
                String person_list = rs.getString("person_list");

                ArrayList<String> row = new ArrayList<>();
                row.add(departing_from);
                row.add(arriving_at);
                row.add(num_airplanes);
                row.add(airplane_list);
                row.add(flight_list);
                row.add(earliest_arrival);
                row.add(latest_arrival);
                row.add(num_pilots);
                row.add(num_passengers);
                row.add(joint_pilots_passengers);
                row.add(person_list);

                ans.add(row);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ans;
    }

    public static ArrayList<ArrayList<String>> getFlightsOnTheGround() {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        Connection con = connect();

        ArrayList<String> header = new ArrayList<>(Arrays.asList("Departing From", "Number Flights",
                "Flight List", "Earliest Arrival", "Latest Arrival", "Airplane List"));
        ans.add(header);


        try {
            Statement stmt = con.createStatement();
            String query = "select * from flights_on_the_ground";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String departing_from = rs.getString("departing_from");
                String num_flights = Integer.toString(rs.getInt("num_flights"));
                String flight_list = rs.getString("flight_list");
                String earliest_arrival = rs.getTime("earliest_arrival").toString();
                String latest_arrival = rs.getTime("latest_arrival").toString();
                String airplane_list = rs.getString("airplane_list");

                ArrayList<String> row = new ArrayList<>();
                row.add(departing_from);
                row.add(num_flights);
                row.add(flight_list);
                row.add(earliest_arrival);
                row.add(latest_arrival);
                row.add(airplane_list);

                ans.add(row);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ans;
    }

    public static ArrayList<ArrayList<String>> getFlightsInTheAir() {
        ArrayList<ArrayList<String>> ans = new ArrayList<>();
        // arr[0][i...n] = column name
        // arr[i][j...n] = column values
        Connection con = connect();

        ArrayList<String> header = new ArrayList<>(Arrays.asList("Departure Airport", "Arrival Airport",
                "Number Flights", "Flight List", "Earliest Arrival", "Latest_arrival", "Airplane List"));
        ans.add(header);


        try {
            Statement stmt = con.createStatement();
            String query = "select * from flights_in_the_air";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String departing_from = rs.getString("departing_from");
                String arriving_at = rs.getString("arriving_at");
                String num_flights = Integer.toString(rs.getInt("num_flights"));
                String flight_list = rs.getString("flight_list");
                String earliest_arrival = rs.getTime("earliest_arrival").toString();
                String latest_arrival = rs.getTime("latest_arrival").toString();
                String airplane_list = rs.getString("airplane_list");

                ArrayList<String> row = new ArrayList<>();
                row.add(departing_from);
                row.add(arriving_at);
                row.add(num_flights);
                row.add(flight_list);
                row.add(earliest_arrival);
                row.add(latest_arrival);
                row.add(airplane_list);

                ans.add(row);

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ans;
    }

    public static ArrayList<String> getSupportAirline() {
        ArrayList<String> arr = new ArrayList<>();
        Connection con = connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from airline");
            while (rs.next()) {
                String new_airline = rs.getString("airlineID");
                if (arr.contains(new_airline) == false) {
                    arr.add(new_airline);
                }

            }

            System.out.println("select Support Airline complete!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr;
    }

    public static ArrayList<String> getLicenseType() {
        ArrayList<String> arr = new ArrayList<>();
        Connection con = connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from pilot_licenses");
            while (rs.next()) {
                String new_license = rs.getString("license");
                if (arr.contains(new_license) == false) {
                    arr.add(new_license);
                }

            }

            System.out.println("select license complete!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr;
    }

    public static ArrayList<String> getPersonID() {
        ArrayList<String> arr = new ArrayList<>();
        Connection con = connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from person");
            while (rs.next()) {
                arr.add(rs.getString("personID"));
            }

            Collections.sort(arr);

            System.out.println("select personID complete!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr;
    }

    public static ArrayList<String> getLocationID() {
        ArrayList<String> arr = new ArrayList<>();
        Connection con = connect();
        try {
            //here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from location");
            while (rs.next()) {
//                System.out.println(rs.getString("locationID"));
                arr.add(rs.getString("locationID"));
            }

            Collections.sort(arr);

            System.out.println("select locationID complete!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // System.out.println(arr.get(0));

        return arr;
    }
    public static ArrayList<String> getAirportID() {
        ArrayList<String> arr = new ArrayList<>();
        Connection con = connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from airport");
            while (rs.next()) {
                if (!arr.contains(rs.getString("airportID"))) {
                    arr.add(rs.getString("airportID"));
                }
            }

            Collections.sort(arr);

            System.out.println("select airportID complete!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr;
    }

    public static ArrayList<String> getLegID() {
        ArrayList<String> arr = new ArrayList<>();
        Connection con = connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from leg");
            while (rs.next()) {
                arr.add(rs.getString("legID"));
            }

            Collections.sort(arr);

            System.out.println("select legID complete!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr;
    }
    public static ArrayList<String> getFlightID() {
        ArrayList<String> arr = new ArrayList<>();
        Connection con = connect();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from flight");
            while (rs.next()) {
                arr.add(rs.getString("flightID"));
            }

            Collections.sort(arr);

            System.out.println("select flightID complete!");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr;
    }

    public static void test() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            //here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from location");
            while (rs.next()) {
                System.out.println(rs.getString("locationID"));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        getLocationID();
    }
}
