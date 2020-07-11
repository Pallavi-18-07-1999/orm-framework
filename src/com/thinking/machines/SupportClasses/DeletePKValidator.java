package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.util.*;
import java.math.*;
public class DeletePKValidator implements DeleteValidator
{
public boolean success(TableSetter ts,List<Object> pkList)      
{
try
{
List<PrimaryKey> pk=ts.pk; 



StatementMaker sm=new StatementMaker();
String selectStatement=sm.getSelectStatement(ts);
PreparedStatement ps;
String finalQuery=selectStatement+" where ";
int i=0;
for(PrimaryKey key:pk)
{
if(i==pk.size()-1)
finalQuery += (key.attributeName + " = ?");
else
finalQuery += (key.attributeName+" = ? and ");
i++;
} 

Connection connectivity=DAOConnection.con;
ps=connectivity.prepareStatement(finalQuery);
int j=1;
for(PrimaryKey key:pk)
{
String dataType=key.dataType;
System.out.println(dataType);
Object o=pkList.get(j-1);

Object result=o;

Class val=o.getClass();
if(dataType.equals("INT"))  
ps.setInt(j,(int)result);
else if(dataType.equals("CHAR") || dataType.equals("VARCHAR"))
ps.setString(j,(String)result);
else if(dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)result);
else if(dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)result);
else if(dataType.equals("BIT"))
ps.setByte(j,(byte)result);
else if(dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)result;
ps.setTime(j,new java.sql.Time(date.getTime()));
}
else 
{
java.util.Date date=(java.util.Date)result;
ps.setTimestamp(j,new java.sql.Timestamp(date.getTime()));
}
j++;

}

  
ResultSet rs=ps.executeQuery();
while(rs.next())
{

return true;
}

}catch(Exception e)
{
e.printStackTrace();
}
return false;
}
public String validationFailedMessage()
{
return "primary key doesn't exist";
}
}