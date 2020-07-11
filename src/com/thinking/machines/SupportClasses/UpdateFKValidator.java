package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.math.*;
import java.util.*;
public class UpdateFKValidator implements UpdateValidator
{
public boolean success(TableSetter ts,Object obj)  
{
try
{
Map<String,List<ForeignKey>> map=ts.fk;

for(String tableName:map.keySet())
{
System.out.println(tableName);
String query="select * from "+tableName+" where ";
int i=0;
Connection connection=DAOConnection.con;
for(ForeignKey fk:map.get(tableName))
{



if(i!=map.get(tableName).size()-1)
query=query + (fk.referencedColumn + " = ? and ");
else 
query=query+(fk.referencedColumn+" = ?");
i++;
}
System.out.println(query);
Class cls=obj.getClass();

Field[] fields=cls.getDeclaredFields();
int j=1;
Connection connectivity=DAOConnection.con;
PreparedStatement ps=connectivity.prepareStatement(query);
for(Field field:fields)
{
field.setAccessible(true);
Object result=field.get(obj);
ForeignKeyAnnotation fka=field.getAnnotation(ForeignKeyAnnotation.class);
FieldAnnotation f=field.getAnnotation(FieldAnnotation.class);
if(fka!=null)
{

for(ForeignKey fk:map.get(tableName))
{
if(fk.attributeName.equals(f.value()))
{
if(fk.dataType.equals("CHAR")||fk.dataType.equals("VARCHAR"))
ps.setString(j,(String)result);
else if(fk.dataType.equals("INT"))
ps.setInt(j,(int)result);
else if(fk.dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)result);
else if(fk.dataType.equals("BIT"))
ps.setByte(j,(byte)result);
else if(fk.dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)result);
else if(fk.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(fk.dataType.equals("TIME"))
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
}
ResultSet rs=ps.executeQuery();
if(rs.next()==false)
{
return false;
}
}


}catch(Exception e)
{
}
return true;
}
public String validationFailedMessage()
{
return "foreign key validation failed";
}
}