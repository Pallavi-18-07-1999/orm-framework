package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.util.*;
import java.math.*;
public class UpdateSizeValidator implements UpdateValidator
{
public boolean success(TableSetter ts,Object obj)  
{
try
{
for(AttributeSetter as:ts.attributeSetterList)
{
int size=as.size;
Class cls=obj.getClass();
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
field.setAccessible(true);
Object result=field.get(obj);
FieldAnnotation f=field.getAnnotation(FieldAnnotation.class);

String attributeName=f.value();
if(attributeName.equals(as.attributeName))
{
Object o=field.get(obj);
if(as.dataType.equals("CHAR")||as.dataType.equals("VARCHAR"))
{
String res=(String)result;

if(res.trim().length()>as.size)
return false;
}
if(as.dataType.equals("DECIMAL"))
{

BigDecimal bd=(BigDecimal)result;
String res=bd.toString();
int index=res.indexOf(".");
if(res.substring(0,index).length()>as.size || res.substring(index+1).length()>as.precisionForDecimalDataType)
return false;
}
if(as.dataType.equals("INT"))
{
int res=(int)result;
if(String.valueOf(res).length()>as.size) return false;
}
if(as.dataType.equals("BIT"))
{
byte res=(byte)result;
if(String.valueOf(res).length()>as.size) return false;
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
return "size validation failed";
}
}