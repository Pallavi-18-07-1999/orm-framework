package com.thinking.machines.SupportClasses;
import java.lang.reflect.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.SQLConnection.*;
import com.thinking.machines.annotations.*;
import java.sql.*;
import java.util.*;
public class UpdateNotNullValidator implements UpdateValidator
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
NotNullAnnotation nna=field.getAnnotation(NotNullAnnotation.class);

if(nna!=null)
{
Object result=field.get(obj);
if(result==null) return false;
if(result==null)
return false;
Class returnType=field.getType();
if(returnType.getName().equals("java.lang.String"))
{
String res=(String)result;
if(res.trim().length()==0)
return false;
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
return "not null validation failed";
}
}