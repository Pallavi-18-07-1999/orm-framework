package com.thinking.machines.SQLConnection;
import java.sql.*;

public class DAOConnection
{

public  String database;
public String username;
public String password;
public   String connectionString;
public String Driver;
public static Connection con;
public DAOConnection()
{

}

public Connection getConnection()
{
try
{
System.out.println(Driver);
Class.forName(Driver);
con=DriverManager.getConnection(connectionString+database,username,password);
return DriverManager.getConnection(connectionString+database,username,password);

}catch(Exception e)
{
e.printStackTrace();
return null;
}
}

}

