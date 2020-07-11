package com.thinking.machines.SupportClasses;
import com.thinking.machines.annotations.*;
import com.thinking.machines.SQLConnection.*;
import java.util.*;
import java.sql.*;
import java.lang.reflect.*;
import java.util.stream.*;
import java.lang.*;
import com.thinking.machines.exception.*;   
public class Select
{
public List list;
public Class cls;
public String sqlStatement;
public TableSetter ts;
public int orderByUsed;

public String currentField;
public AttributeSetter currentAttribute;
public int whereUsed;
public String previousOperation;
public Select()
{

orderByUsed=0;
whereUsed=0;  
currentField="";
previousOperation="";
}
public Select le(Object obj) throws TMException   
{
try
{
if(obj==null)   
{
throw new TMException("you are passing null as an argument to le method");
}
if((previousOperation.equals("and") || previousOperation.equals("or") || previousOperation.equals("where"))==false)
{
throw new TMException("you can call le method only after calling  where / or /and method!");
}
Class c=obj.getClass(); 
String typeOfArgument=c.getName();
Field field=cls.getDeclaredField(currentField);
field.setAccessible(true);
Class t=field.getType();
String type=t.getName();

if(type.equals("int") && typeOfArgument.equals("java.lang.Integer"))
{
int val=(int)obj;
sqlStatement += (" <= "+val);
}
else if(type.equals("java.lang.String") && typeOfArgument.equals("java.lang.String"))
{
String val=(String) obj;
sqlStatement += (" <= '"+val+"'");
System.out.println(sqlStatement);
}
else if(type.equals("java.util.Date") && typeOfArgument.equals("java.util.Date"))
{
String fieldName="";


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);
fieldName=fa.value();
for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(fieldName))
{
if(as.dataType.equals("DATE"))
{
java.util.Date  date=(java.util.Date)obj;
java.sql.Date val=new java.sql.Date(date.getTime());
sqlStatement += " <= "+ "'"+val.toString()+"'";
System.out.println(sqlStatement);
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)obj;
java.sql.Time val=new java.sql.Time(date.getTime());
sqlStatement += " <= "+ "'"+val.toString()+"'";
}
else
{
java.util.Date date=(java.util.Date)obj;
java.sql.Timestamp ts=new java.sql.Timestamp(date.getTime());
sqlStatement += " <= "+ "'"+ts.toString()+"'";
System.out.println(sqlStatement);
} 
break;
}
}

}
else if(type.equals("java.math.BigDecimal") && typeOfArgument.equals("java.math.BigDecimal"))
{
java.math.BigDecimal val=(java.math.BigDecimal)obj;
sqlStatement += (" <= "+val);
}
else if(type.equals("byte") && typeOfArgument.equals("java.lang.Byte"))
{
byte val=(byte)obj;
sqlStatement += (" <= "+val);
}
else
throw new TMException("return tye of "+currentField+" does not match with the value provided in eq function");

}catch(Exception e)
{
e.printStackTrace();
throw new TMException(e.getMessage());
}
previousOperation="le";
return this;
}
public Select ge(Object obj) throws TMException
{
try
{
if(obj==null)
{
throw new TMException("you are passing null as an argument to ge method");
}
if((previousOperation.equals("and") || previousOperation.equals("or") || previousOperation.equals("where"))==false)
{
throw new TMException("you can call ge method  only after calling  where / or /and method!");
}
Class c=obj.getClass(); 
String typeOfArgument=c.getName();
Field field=cls.getDeclaredField(currentField);
field.setAccessible(true);
Class t=field.getType();
String type=t.getName();

if(type.equals("int") && typeOfArgument.equals("java.lang.Integer"))
{
int val=(int)obj;
sqlStatement += (" >= "+val);
}
else if(type.equals("java.lang.String") && typeOfArgument.equals("java.lang.String"))
{
String val=(String) obj;
sqlStatement += (" >= '"+val+"'");
System.out.println(sqlStatement);
}
else if(type.equals("java.util.Date") && typeOfArgument.equals("java.util.Date"))
{
String fieldName="";


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(fieldName))
{
if(as.dataType.equals("DATE"))
{
java.util.Date  date=(java.util.Date)obj;
java.sql.Date val=new java.sql.Date(date.getTime());
sqlStatement += " >= "+ "'"+val.toString()+"'";
System.out.println(sqlStatement);
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)obj;
java.sql.Time val=new java.sql.Time(date.getTime());
sqlStatement += " >= "+ "'"+val.toString()+"'";
}
else
{
java.util.Date date=(java.util.Date)obj;
java.sql.Timestamp ts=new java.sql.Timestamp(date.getTime());
sqlStatement += " >= "+ "'"+ts.toString()+"'";
System.out.println(sqlStatement);
} 
break;
}
}

}
else if(type.equals("java.math.BigDecimal") && typeOfArgument.equals("java.math.BigDecimal"))
{
java.math.BigDecimal val=(java.math.BigDecimal)obj;
sqlStatement += (" >= "+val);
}
else if(type.equals("byte") && typeOfArgument.equals("java.lang.Byte"))
{
byte val=(byte)obj;
sqlStatement += (" >= "+val);
}



else
throw new TMException("return tye of "+currentField+" does not match with the value provided in eq function");

}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="ge";
return this;
}
public Select lt(Object obj) throws TMException
{
try
{
if(obj==null)
{
throw new TMException("you are passing null as an argument to lt method");
}
if((previousOperation.equals("and") || previousOperation.equals("or") || previousOperation.equals("where"))==false)
{
throw new TMException("you can call lt method only after calling  where / or /and method!");
}
Class c=obj.getClass(); 
String typeOfArgument=c.getName();
Field field=cls.getDeclaredField(currentField);
field.setAccessible(true);
Class t=field.getType();
String type=t.getName();

if(type.equals("int") && typeOfArgument.equals("java.lang.Integer"))
{
int val=(int)obj;
sqlStatement += (" < "+val);
}
else if(type.equals("java.lang.String") && typeOfArgument.equals("java.lang.String"))
{
String val=(String) obj;
sqlStatement += (" < '"+val+"'");
System.out.println(sqlStatement);
}
else if(type.equals("java.util.Date") && typeOfArgument.equals("java.util.Date"))
{
String fieldName="";


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(fieldName))
{
if(as.dataType.equals("DATE"))
{
java.util.Date  date=(java.util.Date)obj;
java.sql.Date val=new java.sql.Date(date.getTime());
sqlStatement += " < "+ "'"+val.toString()+"'";
System.out.println(sqlStatement);
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)obj;
java.sql.Time val=new java.sql.Time(date.getTime());
sqlStatement += " < "+ "'"+val.toString()+"'";
}
else
{
java.util.Date date=(java.util.Date)obj;
java.sql.Timestamp ts=new java.sql.Timestamp(date.getTime());
sqlStatement += " < "+ "'"+ts.toString()+"'";
System.out.println(sqlStatement);
} 
break;
}
}

}
else if(type.equals("java.math.BigDecimal") && typeOfArgument.equals("java.math.BigDecimal"))
{
java.math.BigDecimal val=(java.math.BigDecimal)obj;
sqlStatement += (" < "+val);
}
else if(type.equals("byte") && typeOfArgument.equals("java.lang.Byte"))
{
byte val=(byte)obj;
sqlStatement += (" < "+val);
}


else
throw new TMException("return tye of "+currentField+" does not match with the value provided in eq function");

}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="lt";
return this;
}
public Select gt(Object obj) throws TMException
{
try
{
if(obj==null)
{
throw new TMException("you are passing null as an argument to gt method");
}
if((previousOperation.equals("and") || previousOperation.equals("or") || previousOperation.equals("where"))==false)
{
throw new TMException("you can call gt method only after calling  where / or /and method!");
}
Class c=obj.getClass(); 
String typeOfArgument=c.getName();
Field field=cls.getDeclaredField(currentField);
field.setAccessible(true);
Class t=field.getType();
String type=t.getName();

if(type.equals("int") && typeOfArgument.equals("java.lang.Integer"))
{
int val=(int)obj;
sqlStatement += (" > "+val);
}
else if(type.equals("java.lang.String") && typeOfArgument.equals("java.lang.String"))
{
String val=(String) obj;
sqlStatement += (" > '"+val+"'");
System.out.println(sqlStatement);
}

else if(type.equals("java.util.Date") && typeOfArgument.equals("java.util.Date"))
{
String fieldName="";


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(fieldName))
{
if(as.dataType.equals("DATE"))
{
java.util.Date  date=(java.util.Date)obj;
java.sql.Date val=new java.sql.Date(date.getTime());
sqlStatement += " > "+ "'"+val.toString()+"'";
System.out.println(sqlStatement);
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)obj;
java.sql.Time val=new java.sql.Time(date.getTime());
sqlStatement += " > "+ "'"+val.toString()+"'";
}
else
{
java.util.Date date=(java.util.Date)obj;
java.sql.Timestamp ts=new java.sql.Timestamp(date.getTime());
sqlStatement += " > "+ "'"+ts.toString()+"'";
System.out.println(sqlStatement);
} 
break;
}
}

}
else if(type.equals("java.math.BigDecimal") && typeOfArgument.equals("java.math.BigDecimal"))
{
java.math.BigDecimal val=(java.math.BigDecimal)obj;
sqlStatement += (" > "+val);
}
else if(type.equals("byte") && typeOfArgument.equals("java.lang.Byte"))
{
byte val=(byte)obj;
sqlStatement += (" > "+val);
}
else
throw new TMException("return tye of "+currentField+" does not match with the value provided in eq function");

}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="gt";
return this;
}
public Select ne(Object obj) throws TMException
{
try
{
if(obj==null)
{
throw new TMException("you are passing null as an argument to ne method");
}
if((previousOperation.equals("and") || previousOperation.equals("or") || previousOperation.equals("where"))==false)
{
throw new TMException("you can call ne  method only after calling  where / or /and method!");
}
Class c=obj.getClass(); 
String typeOfArgument=c.getName();
Field field=cls.getDeclaredField(currentField);
field.setAccessible(true);
Class t=field.getType();
String type=t.getName();

if(type.equals("int") && typeOfArgument.equals("java.lang.Integer"))
{
int val=(int)obj;
sqlStatement += (" != "+val);
}
else if(type.equals("java.lang.String") && typeOfArgument.equals("java.lang.String"))
{
String val=(String) obj;
sqlStatement += (" != '"+val+"'");
System.out.println(sqlStatement);
}
else if(type.equals("java.util.Date") && typeOfArgument.equals("java.util.Date"))
{
String fieldName="";


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(fieldName))
{
if(as.dataType.equals("DATE"))
{
java.util.Date  date=(java.util.Date)obj;
java.sql.Date val=new java.sql.Date(date.getTime());
sqlStatement += " != "+ "'"+val.toString()+"'";
System.out.println(sqlStatement);
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)obj;
java.sql.Time val=new java.sql.Time(date.getTime());
sqlStatement += " != "+ "'"+val.toString()+"'";
}
else
{
java.util.Date date=(java.util.Date)obj;
java.sql.Timestamp ts=new java.sql.Timestamp(date.getTime());
sqlStatement += " != "+ "'"+ts.toString()+"'";
System.out.println(sqlStatement);
} 
break;
}
}

}
else if(type.equals("java.math.BigDecimal") && typeOfArgument.equals("java.math.BigDecimal"))
{
java.math.BigDecimal val=(java.math.BigDecimal)obj;
sqlStatement += (" != "+val);
}
else if(type.equals("byte") && typeOfArgument.equals("java.lang.Byte"))
{
byte val=(byte)obj;
sqlStatement += (" != "+val);
}
else
throw new TMException("return type of "+currentField+" does not match with the value provided in eq function");

}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="ne";
return this;
}
public Select eq(Object obj) throws TMException
{
try
{
if(obj==null)
{
throw new TMException("you are passing null as an argument to eq method");
}
if((previousOperation.equals("and") || previousOperation.equals("or") || previousOperation.equals("where"))==false)
{
throw new TMException("you can call eq method only after calling  where / or /and method!");
}
Class c=obj.getClass();
String typeOfArgument=c.getName();
Field field=cls.getDeclaredField(currentField);
field.setAccessible(true);
Class t=field.getType();
String type=t.getName();

if(type.equals("int") && typeOfArgument.equals("java.lang.Integer"))
{
int val=(int)obj;
sqlStatement += (" = "+val);
}
else if(type.equals("java.lang.String") && typeOfArgument.equals("java.lang.String"))
{
String val=(String) obj;
sqlStatement += (" = '"+val+"'");
System.out.println(sqlStatement);
}
else if(type.equals("java.util.Date") && typeOfArgument.equals("java.util.Date"))
{
String fieldName="";


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

for(AttributeSetter as:ts.attributeSetterList)
{
if(as.attributeName.equals(fieldName))
{
if(as.dataType.equals("DATE"))
{
java.util.Date  date=(java.util.Date)obj;
java.sql.Date val=new java.sql.Date(date.getTime());
sqlStatement += " = "+ "'"+val.toString()+"'";
System.out.println(sqlStatement);
}
else if(as.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)obj;
java.sql.Time val=new java.sql.Time(date.getTime());
sqlStatement += " = "+ "'"+val.toString()+"'";
}
else
{
java.util.Date date=(java.util.Date)obj;
java.sql.Timestamp ts=new java.sql.Timestamp(date.getTime());
sqlStatement += " = "+ "'"+ts.toString()+"'";
System.out.println(sqlStatement);
} 
break;
}
}

}
else if(type.equals("java.math.BigDecimal") && typeOfArgument.equals("java.math.BigDecimal"))
{
java.math.BigDecimal val=(java.math.BigDecimal)obj;
sqlStatement += (" = "+val);
}
else if(type.equals("byte") && typeOfArgument.equals("java.lang.Byte"))
{
byte val=(byte)obj;
sqlStatement += (" = "+val);
}

else
throw new TMException("return tye of "+currentField+" does not match with the value provided in eq function");

}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="eq";
return this;

}
public Select and(String col) throws TMException
{
try
{
if(col==null)   
{
throw new TMException("you are passing null as an argument to and method");
}
if(col.trim().equals(""))   
{
throw new TMException("you are passing empty string as an argument to and method");
}
if(whereUsed==0)
{
throw new TMException("you can call and method  only after calling  where method!");
}
int noSuchColumnExist=1;
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
if(field.getName().equals(col))
{

String fieldName="";
noSuchColumnExist=0;


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

sqlStatement += (" and "+fieldName);
currentField=col;
break;
}
}
if(noSuchColumnExist==1)
{
throw new TMException("no field with name" +col +" exist");
}
}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="and";
return this;
}
public Select or(String col) throws TMException
{
try
{
if(col==null)   
{
throw new TMException("you are passing null as an argument to or method");
}
if(col.trim().equals(""))   
{
throw new TMException("you are passing empty string as an argument to or method");
}
if(whereUsed==0)
{
throw new TMException("you can call or method  only after calling  where method!");
}
int noSuchColumnExist=1;
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
if(field.getName().equals(col))
{

String fieldName="";
noSuchColumnExist=0;


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

sqlStatement += (" or "+fieldName);
currentField=col;
break;
}
}
if(noSuchColumnExist==1)
{
throw new TMException("no field with name" +col +" exist");
}
}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="or";
return this;
}
public Select where(String col) throws TMException
{
try
{
if(col==null)   
{
throw new TMException("you are passing null as an argument to where method");
}
if(col.trim().equals(""))   
{
throw new TMException("you are passing empty string as an argument to where method");
}
if(whereUsed==1)
{
throw new TMException("you can call where method more than once");
}
if(orderByUsed==1)
{
throw new TMException("you can't call where method after calling order by method");
}
int noSuchColumnExist=1;
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
if(field.getName().equals(col))
{

String fieldName="";
whereUsed=1;
noSuchColumnExist=0;


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

sqlStatement += (" where "+fieldName);
currentField=col;
break;
}
}
if(noSuchColumnExist==1)
{
throw new TMException("no field with name" +col +" exist");
}
}catch(Exception e)
{
throw new TMException(e.getMessage());
}
previousOperation="where";
return this;
}
public Select ascending() throws TMException
{
try
{
if(previousOperation.equals("orderBy"))
{
previousOperation="ascending";
sqlStatement += (" asc ");
return this;
} 
throw new TMException("you can call ascending method only after calling order by ");
}catch(Exception e)
{
throw new TMException(e.getMessage());
}


}
public Select descending() throws TMException
{
try
{
if(previousOperation.equals("orderBy"))
{
previousOperation="descending";
sqlStatement += (" desc ");
return this;
}
throw new TMException("you can call descending method only after calling order by ");
}catch(Exception e)
{
throw new TMException(e.getMessage());
}
}
public Select orderBy(String col) throws TMException
{
try
{
int noSuchColumnExist=1;
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
if(field.getName().equals(col))
{

String fieldName="";
noSuchColumnExist=0;


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

if(orderByUsed==0)
{
sqlStatement += (" order by "+fieldName);
System.out.println(sqlStatement);
orderByUsed=1;
}
else
{
sqlStatement += (" , "+ fieldName);
}
break;
}
}
if(noSuchColumnExist==1)
{
throw new TMException("no field with name" +col +" exist");
}
}catch(Exception e)
{
throw new TMException(e.getMessage());
}
return this;
}
public List query() 
{
try
{
Connection connection = DAOConnection.con;
List<Object>list=new LinkedList<>();

PreparedStatement ps=connection.prepareStatement(sqlStatement);

ResultSet rs=ps.executeQuery();
while(rs.next())
{

Object obj=cls.newInstance();
for(AttributeSetter as:ts.attributeSetterList)  
{
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
field.setAccessible(true);
String fieldName="";


FieldAnnotation fa=field.getAnnotation(FieldAnnotation.class);

fieldName=fa.value();

if(fieldName.equals(as.attributeName))
{

if(as.dataType.equals("INT"))
{

field.set(obj,rs.getInt(as.attributeName));
System.out.println(rs.getInt(as.attributeName));
}
else if(as.dataType.equals("CHAR")||as.dataType.equals("VARCHAR"))
{
field.set(obj,rs.getString(as.attributeName));
}
else if(as.dataType.equals("DECIMAL"))
{


field.set(obj,rs.getBigDecimal(as.attributeName));;
}
else if(as.dataType.equals("BIT"))
{
java.lang.Byte res=rs.getByte(as.attributeName);
if(res!=null)
field.set(obj,rs.getByte(as.attributeName));
else 
field.set(obj,0);
}
else if(as.dataType.equals("DATE"))
{
if(rs.getDate(as.attributeName)==null) 
field.set(obj,null); 
else
{
java.util.Date date=new java.util.Date(rs.getDate(as.attributeName).getTime());


field.set(obj,date);
}
}
else if(as.dataType.equals("TIME"))
{
if(rs.getTime(as.attributeName)==null)
field.set(obj,null);
else
{


java.util.Date date=new java.util.Date();
date.setHours(rs.getTime(as.attributeName).getHours());
date.setMinutes(rs.getTime(as.attributeName).getMinutes());
date.setSeconds(rs.getTime(as.attributeName).getSeconds());
field.set(obj,date);
}
}
else
{
if(rs.getDate(as.attributeName)==null)
field.set(obj,null);
else
{
java.sql.Timestamp sqlTimestamp=rs.getTimestamp(as.attributeName);
java.util.Date date=new java.util.Date(sqlTimestamp.getYear(),sqlTimestamp.getMonth(),sqlTimestamp.getDate(),sqlTimestamp.getHours(),sqlTimestamp.getMinutes(),sqlTimestamp.getSeconds());



field.set(obj,date);
}
}
}



}
}
list.add(obj);

}
return (java.util.List)list.stream().map(e -> cls.cast(e)).collect(Collectors.toList());
}catch(Exception e)
{
e.printStackTrace();
}
return null;

}
}
