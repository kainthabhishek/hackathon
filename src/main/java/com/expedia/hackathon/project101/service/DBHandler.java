package com.expedia.hackathon.project101.service;

import com.expedia.hackathon.project101.domain.Hotel;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

@Service
public class DBHandler {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public ArrayList<Hotel> readDataBase(String location) throws Exception {

        ArrayList<Hotel> hotels = null;

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            //Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/db_example?"
                            + "user=springuser1&password=thepassword");
            // final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            //final String DB_URL = "jdbc:mysql://localhost:3306/db_example";


            //final String USER = "springuser1";
            // final String PASS = "thepassword";

            //Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // Statements allow to issue SQL queries to the database
            String query="select * from db_example.hotel_info where landmark="+"'" + location + "' ";
            statement = connect.createStatement();
            System.out.println(location);
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery( query);
            hotels = writeResultSet(resultSet);

            // PreparedStatements can use variables and are more efficient
//            preparedStatement = connect
//                    .prepareStatement("insert into  db_example.hotel_info values (?,?)");
//
//            preparedStatement.setString(1, "name");
//            preparedStatement.setString(2, "description");
//            preparedStatement.executeUpdate();
//
//            preparedStatement = connect
//                    .prepareStatement("insert into db_example.hotel_info  values (?,?)");
//            resultSet = preparedStatement.executeQuery();
//            writeResultSet(resultSet);


        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

        return hotels;
    }
    public void putDataBase(String a, String b) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        //Setup the connection with the DB

        preparedStatement = connect
                .prepareStatement("insert into  db_example.hotel_info values (?,?)");
//
        preparedStatement.setString(1,a);
        preparedStatement.setString(2, b);
        preparedStatement.executeUpdate();
//                    preparedStatement = connect
//                    .prepareStatement("insert into db_example.hotel_info  values (?,?)");
//            resultSet = preparedStatement.executeQuery();
        //writeResultSet(resultSet);

    }

    private ArrayList<Hotel> writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set

        ArrayList<Hotel> hotels = new ArrayList<>();

        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String landmark = resultSet.getString("landmark");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            int price = resultSet.getInt("price");
            float rating = resultSet.getFloat("rating");
            float distance = resultSet.getFloat("Distance");
            String description = resultSet.getString("Description");

            hotels.add(new Hotel(name, address, price, rating, distance));
        }

        return hotels;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
