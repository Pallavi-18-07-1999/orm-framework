package com.thinking.machines.SupportClasses;
import java.sql.*;
import com.thinking.machines.interfaces.*;
import java.util.*;
import com.thinking.machines.SQLConnection.*;
public class UpdateEmptySetValidator implements UpdateValidator
{
public boolean success(TableSetter ts,Object obj)
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