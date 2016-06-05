package de.vogella.mysql.first;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;



public class MySQLAccess {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  //private Gson data;
  private String data;
  
  
  public MySQLAccess(String data) throws Exception{
	  this.data = data;
	  
	  
  }


public void readDataBase() throws Exception {
    try {
      // This will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // Setup the connection with the DB
      connect = DriverManager
          .getConnection("jdbc:mysql://localhost/inno?"
              + "user=root&password=");

      // Statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // Result set get the result of the SQL query
      resultSet = statement
          .executeQuery("select * from inno.tbl_lean");
    

      // PreparedStatements can use variables and are more efficient
      preparedStatement = connect
          .prepareStatement("insert into inno.tbl_lean values (default, ?, default)");
      //preparedStatement.setString(1, "Test");
      preparedStatement.setString(1, data);
      //preparedStatement.setString(3, "TestWebpage");
      preparedStatement.executeUpdate();
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }




  // You need to close the resultSet
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