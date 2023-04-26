package com.example.airman;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseConnect {

    private static String url = "jdbc:mysql://localhost:3306/flight_management";
    private static String username = "root";
    private static String password = "josh0205";

    public static void usedAddAirport(String airportID, String airportName, String city, String state, String locationID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            //here flight_management is database name, root is username and password
            String query = "{CALL add_airport(?, ?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, airportID);
            stmt.setString(2, airportName);
            stmt.setString(3, city);
            stmt.setString(4, state);
            stmt.setString(5, locationID);

            System.out.println("HELLO CONNECTED COMPLETE");

            stmt.execute();
            stmt.close();

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void useAddAirplane(String airplaneID, String tailNum, int seatCap, int speed, String locationID,
                                      String planeType, Object skids, Object propellers, Object jetEngines) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/flight_management", "root", "josh0205");
            //here flight_management is database name, root is username and password
            String query = "{CALL add_airplane(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement stmt = con.prepareCall(query);

            stmt.setString(1, airplaneID);
            stmt.setString(2, tailNum);
            stmt.setInt(3, seatCap);
            stmt.setInt(4, speed);
            stmt.setString(5, locationID);
            stmt.setString(6, planeType);
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

    public static ArrayList<String> getLocationID() {
        ArrayList<String> arr = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/flight_management", "root", "josh0205");
            //here flight_management is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from location");
            while (rs.next()) {
                arr.add(rs.getString("locationID"));
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr;
    }

    public static void test() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/flight_management", "root", "josh0205");
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
