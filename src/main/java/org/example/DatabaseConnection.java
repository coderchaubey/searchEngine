//package org.example;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DatabaseConnection {
//    //this stores a connection from the SQL server
//    static Connection connection=null;
//    //creating a function whose return type is connection
//    public static Connection getConnection(){
//        if (connection!=null){
//            return connection;
//        }
//        //if we don't have  a connection then we will be creating one//
//        //Below are three things that are required to create a connection to the database//
//        String db="searchaccio"; //Database name
//        String user="root"; //the user
//        String pass="9954381836Dc*"; //lastly the password
//        return getConnection( db,user,pass); //for method overloading
//    }
//    //overloading
//    private static Connection getConnection(String db,String user,String pass){
//        try{
//            //com.mysql.cj.jdbc is the package inside which driver class is stored//
//            Class.forName("com.mysql.cj.jdbc.Driver"); //it throws an exception for class not found and that is why we have created a try and catch function//
//            connection= DriverManager.getConnection("jdbc:mysql://localhost/"+db+"?user="+user+"&password"+pass);
//        }catch(Exception exception){
//            exception.printStackTrace();
//        }
//        return connection;
//    }
//}
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    static Connection connection=null;
    public static Connection getConnection(){
        if(connection!=null)
        {
            return connection;
        }
        String db="searchaccio";
        String user="root";
        String pwd="9954381836Dc*";
        return getConnection(db,user,pwd);
    }
    private static Connection getConnection(String db,String user,String pwd){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection=DriverManager.getConnection("jdbc:mysql://localhost/"+db+"?user="+user+"&password="+pwd);
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return connection;
    }
}
