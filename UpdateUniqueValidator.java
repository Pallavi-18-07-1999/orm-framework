package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.math.*;
public class UpdateUniqueValidator implements UpdateValidator
{
public boolean success(TableSetter ts,Object obj)   
{
try
{

int i=0;
String query2="";
for(PrimaryKey key:ts.pk)
{
if(i==ts.pk.size()-1)

query2 += (key.attributeName + " = ?");
else
query2 += (key.attributeName+" = ? and ");
i++;
} 
PreparedStatement ps1;
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
ps1=connectivity.prepareStatement(selectStatement+" where "+f.value()+" = ?"+" and "+query2);

for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(f.value()))
{
if(as.dataType.equals("CHAR")||as.dataType.equals("VARCHAR"))
{
ps.setString(1,(String)result);
ps1.setString(1,(String)result);
}
else if(as.dataType.equals("INT"))
{
ps.setInt(1,(int)result);
ps1.setInt(1,(int)result);
}
else if(as.dataType.equals("BOOLEAN"))
{
ps.setBoolean(1,(boolean)result);
ps1.setBoolean(1,(boolean)result);
}
else if(as.dataType.equals("BIT"))
{
ps.setByte(1,(byte)result);
ps1.setByte(1,(byte)result);
}
else if(as.dataType.equals("DECIMAL"))
{
ps.setBigDecimal(1,(BigDecimal)result);
ps1.setBigDecimal(1,(BigDecimal)result);
}
else if(as.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(1,new java.sql.Date(date.getTime()));
ps1.setDate(1,new java.sql.Date(date.getTime()));
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)result;
ps.setTime(1,new java.sql.Time(date.getTime()));
ps1.setTime(1,new java.sql.Time(date.getTime()));
}
else 
{
java.util.Date date=(java.util.Date)result;
ps.setTimestamp(1,new java.sql.Timestamp(date.getTime()));
ps1.setTimestamp(1,new java.sql.Timestamp(date.getTime()));
}
int j=2;
for(PrimaryKey key:ts.pk)
{

for(Field field1:fields)
{
field1.setAccessible(true);
PrimaryKeyAnnotation pka=field1.getAnnotation(PrimaryKeyAnnotation.class); 
FieldAnnotation fa=field1.getAnnotation(FieldAnnotation.class); 
Object res=field1.get(obj);
if(pka!=null)
{
if(fa.value().equals(key.attributeName))
{

if(key.dataType.equals("CHAR")||key.dataType.equals("VARCHAR"))
{

ps1.setString(j,(String)res);
}
else if(key.dataType.equals("INT"))
{

ps1.setInt(j,(int)res);
}
else if(key.dataType.equals("BOOLEAN"))
{

ps1.setBoolean(j,(boolean)res);
}
else if(key.dataType.equals("BIT"))
{

ps1.setByte(j,(byte)res);
}
else if(key.dataType.equals("DECIMAL"))
{

ps1.setBigDecimal(j,(BigDecimal)res);
}
else if(key.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)res;

ps1.setDate(j,new java.sql.Date(date.getTime()));
}
else if(key.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)res;

ps1.setTime(j,new java.sql.Time(date.getTime()));
}
else 
{
java.util.Date date=(java.util.Date)res;

ps1.setTimestamp(j,new java.sql.Timestamp(date.getTime()));
}
j++;
}

}
}
}
ResultSet rs=ps.executeQuery();
ResultSet rs1=ps1.executeQuery();
int count=0;
while(rs.next())
{
count++;
}

if(count>1) return false;

if(rs1.next()==false && count==1)
{

return false;
}
}
}
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
return "unique validation failed";
}
}

