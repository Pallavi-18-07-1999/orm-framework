package com.thinking.machines.SupportClasses;
import java.sql.*;
import com.thinking.machines.interfaces.*;
import java.util.*;
import com.thinking.machines.SQLConnection.*;
public class DeleteEmptySetValidator implements DeleteValidator
{
public boolean success(TableSetter ts,List<Object>pkList)
{
try
{
Connection con=DAOConnection.con;
PreparedStatement ps=con.prepareStatement("select * from "+ts.tableName);
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
return false;
}
return true;
}catch(Exception e)
{

}
return true;
} 
public String validationFailedMessage()
{
return "table is empty";
} 
}