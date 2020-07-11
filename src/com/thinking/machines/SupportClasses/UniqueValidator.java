package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.math.*;
public class UniqueValidator implements InsertValidator
{
public boolean success(TableSetter ts,Object obj)  
{
try
{
Class cls=obj.getClass();
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
field.setAccessible(true);
UniqueAnnotation ua=field.getAnnotation(UniqueAnnotation.class);
FieldAnnotation f=field.getAnnotation(FieldAnnotation.class);
if(ua!=null)
{
Object result=field.get(obj);

StatementMaker sm=new StatementMaker();
String selectStatement=sm.getSelectStatement(ts);
Connection connectivity=DAOConnection.con;
PreparedStatement ps=connectivity.prepareStatement(selectStatement+" where "+f.value()+" = ?");
for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(f.value()))
{
if(as.dataType.equals("CHAR")||as.dataType.equals("VARCHAR"))
ps.setString(1,(String)result);
else if(as.dataType.equals("INT"))
ps.setInt(1,(int)result);
else if(as.dataType.equals("BOOLEAN"))
ps.setBoolean(1,(boolean)result);
else if(as.dataType.equals("BIT"))
ps.setByte(1,(byte)result);
else if(as.dataType.equals("DECIMAL"))
ps.setBigDecimal(1,(BigDecimal)result);
else if(as.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(1,new java.sql.Date(date.getTime()));
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)result;
ps.setTime(1,new java.sql.Time(date.getTime()));
}
else 
{
java.util.Date date=(java.util.Date)result;
ps.setTimestamp(1,new java.sql.Timestamp(date.getTime()));
}
ResultSet rs=ps.executeQuery();
while(rs.next())
{
return false;
}
}
}

}
}
}catch(Exception e)
{
}
return true;
}
public String validationFailedMessage()
{
return "unique constraint must be satisfied";
}
}