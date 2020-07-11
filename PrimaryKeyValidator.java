package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.util.*;
import java.math.*;
public class PrimaryKeyValidator implements InsertValidator
{
public boolean success(TableSetter ts,Object obj)  
{
try
{
List<PrimaryKey> pk=ts.pk;
for(PrimaryKey pkey:pk)
{
if(pkey.isAutoIncrementPresent==true)
return true;
}
Class cls=obj.getClass();
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
System.out.println(finalQuery);
ps=connectivity.prepareStatement(finalQuery);
int j=1;
for(PrimaryKey pkey:pk)
{
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
field.setAccessible(true);

PrimaryKeyAnnotation pka=field.getAnnotation(PrimaryKeyAnnotation.class);
FieldAnnotation f=field.getAnnotation(FieldAnnotation.class);

if(pka!=null && pkey.attributeName.equals(f.value()))
{

Object result=field.get(obj);
System.out.println("yess1");

if(pkey.dataType.equals("CHAR")||pkey.dataType.equals("VARCHAR"))
ps.setString(j,(String)result);
else if(pkey.dataType.equals("INT"))
ps.setInt(j,(int)result);
else if(pkey.dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)result);
else if(pkey.dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)result);
else if(pkey.dataType.equals("BIT"))
ps.setByte(j,(byte)result);
else if(pkey.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(pkey.dataType.equals("TIME"))
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
System.out.println(j);
}
}
}
ResultSet rs=ps.executeQuery();
while(rs.next())
{
System.out.println("key exist");
return false;
}

}catch(Exception e)
{
e.printStackTrace();
}

return true;
}
public String validationFailedMessage()
{
return "primary key must be unique";
}
}