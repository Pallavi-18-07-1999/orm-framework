package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.util.*;
import java.math.*;
public class DeleteFKValidator implements DeleteValidator
{
public boolean success(TableSetter ts,List<Object> pkList)      
{
try
{


Map<String,List<ChildTables>> map=ts.childTables;
for(String tableName:map.keySet())
{
String query="select * from "+tableName+" where ";
int i=0;
Connection connection=DAOConnection.con;
for(ChildTables columns:map.get(tableName))
{

String parentTableColumn=columns.parentTableColumn;
String childColumn=columns.childColumn;

if(i!=map.get(tableName).size()-1)
query=query + (childColumn + " = ? and ");
else 
query=query+(childColumn+" = ?");
i++;
}
System.out.println(query);
int j=1;
PreparedStatement ps=connection.prepareStatement(query);
for(ChildTables columns:map.get(tableName))
{
String parentTableColumn=columns.parentTableColumn;


for(PrimaryKey pk:ts.pk)
{
if(pk.attributeName.equals(parentTableColumn))
{

Object result=pkList.get(j-1);
if(j==2) System.out.println("j is 2");
if(pk.dataType.equals("CHAR")||pk.dataType.equals("VARCHAR"))
{
System.out.println((String)result);
ps.setString(j,(String)result);
}
else if(pk.dataType.equals("INT"))
{
System.out.println((int)result);
ps.setInt(j,(int)result);
}
else if(pk.dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)result);
else if(pk.dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)result);
else if(pk.dataType.equals("BIT"))
ps.setByte(j,(byte)result);
else if(pk.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(pk.dataType.equals("TIME"))
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
}
}

ResultSet rs=ps.executeQuery();
while(rs.next())
{
return false;
}
}



}catch(Exception e)
{
e.printStackTrace();
}
return true;
}
public String validationFailedMessage()
{
return "foreign key validation failed";
}
}