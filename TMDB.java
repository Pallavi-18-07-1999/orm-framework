package com.thinking.machines.SupportClasses;
import com.thinking.machines.SQLConnection.*;

import com.thinking.machines.annotations.*;
import com.thinking.machines.interfaces.*;
import com.thinking.machines.exception.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import com.google.gson.*;   
import java.io.*;
import java.math.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.net.*;
import java.util.stream.*;     
public class TMDB
{
private  List<TableSetter> ds;
private  Connection connectivity;
private StatementMaker statementMaker;
private static List<InsertValidator> insertValidationList;
private static List<DeleteValidator> deleteValidationList;
private static List<UpdateValidator> updateValidationList;
private int tableIndex;

public TMDB()
{
ds=new LinkedList<>();
statementMaker=new StatementMaker();
tableIndex=0;
insertValidationList=new LinkedList<>();
deleteValidationList=new LinkedList<>();
updateValidationList=new LinkedList<>();
}
public static TMDB getInstance()
{
try
{
TMDB tmdb=new TMDB();
tmdb.insertValidationList=Validators.getInsertValidator();
tmdb.deleteValidationList=Validators.getDeleteValidator();
tmdb.updateValidationList=Validators.getUpdateValidator();
Class c=TMDB.class;
String pathToFile=c.getResource(".").getPath();
int index=pathToFile.indexOf("classes");
pathToFile=pathToFile.substring(0,index);
pathToFile += ("dbSettings"+"/DBConfiguration.json");
System.out.println(pathToFile);
File f=new File(pathToFile);
System.out.println(f.getAbsolutePath());  
BufferedReader br=new BufferedReader(new FileReader(f));
StringBuilder sb=new StringBuilder();
String line;
while(true)
{
line=br.readLine();
if(line==null) break;
sb.append(line);
}
String jsonString=sb.toString();
System.out.println(jsonString);
Gson gson=new Gson();
DAOConnection con=(DAOConnection)gson.fromJson(jsonString,DAOConnection.class);

Connection connection=con.getConnection();

tmdb.connectivity=connection;
DatabaseMetaData databaseMetaData=connection.getMetaData();

String databaseName=con.database;
String table[]={"Table"};
ResultSet tables=databaseMetaData.getTables(databaseName,null,null,table);
String view[]={"View"};
ResultSet views=databaseMetaData.getTables(databaseName,null,null,view);
while(views.next())
{
String tableName=views.getString(3);
TableSetter ts=new TableSetter();
ts.attributeSetterList=new LinkedList<>();
ts.tableName=tableName; 
ts.isView=true;
ResultSet attributes=databaseMetaData.getColumns(databaseName,null,tableName,null);
AttributeSetter as=null;
while(attributes.next())
{
as=new AttributeSetter();
String attributeName=attributes.getString(4);
as.attributeName=attributeName;
as.dataType=attributes.getString(6);
ts.attributeSetterList.add(as);
}
tmdb.ds.add(ts);
}

List<AttributeSetter> list=new LinkedList<>();

List<PrimaryKey> pkeyList=new LinkedList<>();
TableSetter ts=null;
while(tables.next())
{
list=new LinkedList<>();
Map<String,List<ForeignKey>> fkMap=new HashMap<>();
pkeyList=new LinkedList<>();
ts=new TableSetter();
String tableName=tables.getString(3);





ts.tableName=tableName;   
ResultSet attributes=databaseMetaData.getColumns(databaseName,null,tableName,null);

AttributeSetter as=null;

while(attributes.next())
{
as=new AttributeSetter();
String attributeName=attributes.getString(4);
as.attributeName=attributeName;

as.dataType=attributes.getString(6);

as.size=Integer.parseInt(attributes.getString(7));

if(attributes.getString(23).equals("YES"))
{
as.isAutoIncrementPresent=true;
}
else
{
as.isAutoIncrementPresent=false;
}

if(attributes.getString(18).equals("NO"))
as.notNullConstraintApplied=true;
else
{
as.notNullConstraintApplied=false;
}
if(attributes.getInt(9)>0)
{
as.precisionForDecimalDataType=attributes.getInt(9);
}
ResultSet pkey=databaseMetaData.getPrimaryKeys(databaseName,null,tableName);
while(pkey.next())
{ 
String primaryKeyName=pkey.getString(4);
if(primaryKeyName.equals(as.attributeName))
{
PrimaryKey pk=new PrimaryKey();
pk.attributeName=attributeName;
pk.size=as.size;
pk.dataType=as.dataType;
pk.isAutoIncrementPresent=as.isAutoIncrementPresent;
pk.precisionForDecimalDataType=as.precisionForDecimalDataType;
pkeyList.add(pk);
}
}


ResultSet unique=databaseMetaData.getIndexInfo(databaseName,null,tableName,true,false);
while(unique.next())
{
if(unique.getString(9).equals(as.attributeName))
{
as.isUniqueConstraintApplied=true;  

}
}
ResultSet fkey=databaseMetaData.getImportedKeys(databaseName,null,tableName);  

while(fkey.next())
{
if(fkey.getString(8).equals(as.attributeName))
{
if(fkMap.containsKey(fkey.getString(3)))
{
System.out.println("tableName "+ fkey.getString(3)); 

ForeignKey key=new ForeignKey();
key.referencedColumn=fkey.getString(4);
key.size=as.size;
key.attributeName=as.attributeName;
key.dataType=as.dataType;
key.notNullConstraintApplied=as.notNullConstraintApplied;
key.isUniqueConstraintApplied=as.isUniqueConstraintApplied;
key.isAutoIncrementPresent=as.isAutoIncrementPresent;
key.precisionForDecimalDataType=as.precisionForDecimalDataType;
fkMap.get(fkey.getString(3)).add(key);
}
else
{
fkMap.put(fkey.getString(3),new LinkedList<>());
ForeignKey key=new ForeignKey();
key.referencedColumn=fkey.getString(4);
key.size=as.size;
key.attributeName=as.attributeName;
key.dataType=as.dataType;
key.notNullConstraintApplied=as.notNullConstraintApplied;
key.isUniqueConstraintApplied=as.isUniqueConstraintApplied;
key.isAutoIncrementPresent=as.isAutoIncrementPresent;
key.precisionForDecimalDataType=as.precisionForDecimalDataType;
fkMap.get(fkey.getString(3)).add(key);
}
}
}








list.add(as);
}


ts.attributeSetterList=list;
ts.fk=fkMap;
ts.pk=pkeyList;
tmdb.ds.add(ts);
StatementMaker sm=new StatementMaker();

 

}

ResultSet tableNames=databaseMetaData.getTables(databaseName,null,null,table);

while(tableNames.next())
{
String tableName=tableNames.getString(3);


ResultSet rs=databaseMetaData.getExportedKeys(databaseName, null, tableName);
while(rs.next())  
{
int i=0;
for(TableSetter tableSetter:tmdb.ds)
{

if(tableSetter.tableName.equals(rs.getString(3)))
{


if (tableSetter.childTables.containsKey(rs.getString(7))==false)
{
List<ChildTables> columnList=new LinkedList<>(); 
tableSetter.childTables.put(rs.getString(7), columnList);
}
ChildTables ct=new ChildTables();
ct.childTableName=rs.getString(7);
ct.childColumn=rs.getString(8);
ct.parentTableColumn=rs.getString(4);
tableSetter.childTables.get(rs.getString(7)).add(ct);
i++;

}
}
}

}
/*
for(TableSetter tableSetter:tmdb.ds)
{    

    for (String name : tableSetter.childTables.keySet())  
{
                System.out.println("parent table :- "+tableSetter.tableName);
             System.out.println("child table :- "+name);
             for(String column:tableSetter.childTables.get(name))
              {
                 System.out.println("column :- "+column);
                  
              }
}
          
}
*/
return tmdb;


}catch(Exception e)
{
e.printStackTrace();
}
return null;
}
public void begin()
{
try
{
connectivity.setAutoCommit(false);


}catch(Exception e)
{
e.printStackTrace();
}
}
public void save(Object obj) throws TMException
{
try
{  
Class cls=obj.getClass();
Table table=(Table)cls.getAnnotation(Table.class);

int i=0;  
TableSetter tableSetterObject=null;
for(TableSetter ts:this.ds)
{

if(ts.tableName.equals(table.value()) && ts.isView==false)

{
boolean isAutoIncremented=false;
tableSetterObject=ts;
this.tableIndex=i;
StatementMaker sm=new StatementMaker();
String insertStatement=sm.getInsertStatement(ts);
List<AttributeSetter> attributes=ts.attributeSetterList;
PreparedStatement ps=connectivity.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
int j=1;
Field PrimaryKeyField=null;
for(AttributeSetter attribute:attributes)
{

String attributeName=attribute.attributeName;

Field[] fields=cls.getDeclaredFields();
System.out.println("ddddddffd");
for(Field field:fields)
{
System.out.println("field"+field.getName());
field.setAccessible(true);
String columnName="";

FieldAnnotation f=field.getAnnotation(FieldAnnotation.class);
AutoIncrementAnnotation ai=field.getAnnotation(AutoIncrementAnnotation.class); 


columnName=f.value();


if(ai!=null)
{
PrimaryKeyField=field;
}

String fieldName=field.getName();
if(attributeName.equals(columnName))
{
if(ai!=null) {isAutoIncremented=true; break;}
System.out.println(fieldName);

Object result=field.get(obj);
System.out.println(attribute.dataType);

if(attribute.dataType.equals("CHAR")||attribute.dataType.equals("VARCHAR"))
ps.setString(j,(String)result);
else if(attribute.dataType.equals("INT"))
ps.setInt(j,(int)result);
else if(attribute.dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)result);
else if(attribute.dataType.equals("BIT"))
ps.setByte(j,(byte)result);
else if(attribute.dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)result);
else if(attribute.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(attribute.dataType.equals("TIME"))
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

for(InsertValidator iv:insertValidationList)
{
System.out.println("hatt1");
if(iv.success(tableSetterObject,obj)==false)
{
System.out.println("hatt");
throw new TMException(iv.validationFailedMessage());
}
}
ps.executeUpdate();
ResultSet rs =ps.getGeneratedKeys();
List<PrimaryKey> pk=ts.pk;
for(PrimaryKey key:pk)
{
if(key.isAutoIncrementPresent && key.dataType.equals("INT"))
{
if(rs.next())
{

PrimaryKeyField.set(obj,Integer.parseInt(rs.getString(1)));  

 
}
}
}

}
i++;
} 

}catch(Exception e)
{
e.printStackTrace();
throw new TMException(e.getMessage());
}
}
public void commit()
{
try
{
connectivity.commit();
}catch(Exception e)
{
e.printStackTrace();
}
}
public void remove(Class cls,Object ...o) throws TMException
{
try
{

Table table=(Table)cls.getAnnotation(Table.class);
List<Object> list=new LinkedList<>();
TableSetter tableSetterObject=null;
for(TableSetter ts:this.ds)
{
if(ts.tableName.equals(table.value()) && ts.isView==false)
{
tableSetterObject=ts;
List<PrimaryKey> pk=ts.pk;
StatementMaker sm=new StatementMaker();
String deleteStatement=sm.getDeleteStatement(ts);
PreparedStatement ps=connectivity.prepareStatement(deleteStatement);

List<AttributeSetter> attributes=ts.attributeSetterList;
int j=1;


for(Object obj:o)
{
list.add(obj);
PrimaryKey key=pk.get(j-1);

System.out.println(obj);

if(key.dataType.equals("CHAR")||key.dataType.equals("VARCHAR"))
ps.setString(j,(String)obj);
else if(key.dataType.equals("INT"))  
{
System.out.println("yess");
ps.setInt(j,(int)obj);
}
else if(key.dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)obj);
else if(key.dataType.equals("DECIMAL"))
ps.setDouble(j,(double)obj);
else if(key.dataType.equals("BIT"))
ps.setByte(j,(byte)obj);
else if(key.dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)obj);
else if(key.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)obj;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(key.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)obj;
ps.setTime(j,new java.sql.Time(date.getTime()));
}
else 
{
java.util.Date date=(java.util.Date)obj;
ps.setTimestamp(j,new java.sql.Timestamp(date.getTime()));
}
j++;
}

for(DeleteValidator dv:deleteValidationList)
{
System.out.println("hatt1");
if(dv.success(tableSetterObject,list)==false)
{

throw new TMException(dv.validationFailedMessage());
}
}

ps.executeUpdate();
}
}
}catch(Exception e)
{
e.printStackTrace();
throw new TMException(e.getMessage());
}
}
public void update(Object obj) throws TMException
{
try
{
Class cls=obj.getClass();
Table table=(Table)cls.getAnnotation(Table.class);
int i=0;  
TableSetter tableSetterObject=null;
for(TableSetter ts:this.ds)
{

if(ts.tableName.equals(table.value()) && ts.isView==false)
{
StatementMaker sm=new StatementMaker();
String updateStatement=sm.getUpdateStatement(ts);
System.out.println(updateStatement);
List<AttributeSetter> attributes=ts.attributeSetterList;
PreparedStatement ps=connectivity.prepareStatement(updateStatement);
int j=1;
for(AttributeSetter attribute:attributes)
{

String attributeName=attribute.attributeName;

Field[] fields=cls.getDeclaredFields();
System.out.println("ddddddffd");
for(Field field:fields)
{
System.out.println("field"+field.getName());
field.setAccessible(true);
String columnName="";

FieldAnnotation f=field.getAnnotation(FieldAnnotation.class);
PrimaryKeyAnnotation pka=field.getAnnotation(PrimaryKeyAnnotation.class);
columnName=f.value();

String fieldName=field.getName();
if(attributeName.equals(columnName) && pka==null)
{

Object result=field.get(obj);
System.out.println(attribute.dataType);

if(attribute.dataType.equals("CHAR")||attribute.dataType.equals("VARCHAR"))
{
System.out.println((String)result);
ps.setString(j,(String)result);
}
else if(attribute.dataType.equals("INT"))
ps.setInt(j,(int)result);
else if(attribute.dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)result);
else if(attribute.dataType.equals("BIT"))
ps.setByte(j,(byte)result);
else if(attribute.dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)result);
else if(attribute.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(attribute.dataType.equals("TIME"))
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
List<PrimaryKey> pkey=ts.pk;
for(PrimaryKey key:pkey)
{
Field[] fields=cls.getDeclaredFields();
for(Field field:fields)
{
field.setAccessible(true);
String columnName="";
PrimaryKeyAnnotation pk=field.getAnnotation(PrimaryKeyAnnotation.class);
FieldAnnotation f=field.getAnnotation(FieldAnnotation.class);
if(pk!=null)
{
columnName=f.value();
if(columnName.equals(key.attributeName))
{
Object result=field.get(obj);


if(key.dataType.equals("CHAR")||key.dataType.equals("VARCHAR"))
{

ps.setString(j,(String)result);
}
else if(key.dataType.equals("INT"))
{

ps.setInt(j,(int)result);
}
else if(key.dataType.equals("BOOLEAN"))
ps.setBoolean(j,(boolean)result);
else if(key.dataType.equals("BIT"))
ps.setByte(j,(byte)result);
else if(key.dataType.equals("DECIMAL"))
ps.setBigDecimal(j,(BigDecimal)result);
else if(key.dataType.equals("DATE"))
{
java.util.Date date=(java.util.Date)result;
ps.setDate(j,new java.sql.Date(date.getTime()));
}
else if(key.dataType.equals("TIME"))
{
java.util.Date date=(java.util.Date)result;
ps.setTime(j,new java.sql.Time(date.getTime()));
}
else 
{
java.util.Date date=(java.util.Date)result;
ps.setTimestamp(j,new java.sql.Timestamp(date.getTime()));
}
}
}
}
j++;
}
System.out.println("hatt1");
for(UpdateValidator uv:updateValidationList)
{
System.out.println("hatt1");
if(uv.success(ts,obj)==false)
{

throw new TMException(uv.validationFailedMessage());
}
}
ps.executeUpdate();
}
}
}catch(Exception e)
{
throw new TMException(e.getMessage());
}
}
public Select select(Class cls)
{
try
{







Select select=new Select();

Table table=(Table)cls.getAnnotation(Table.class);
System.out.println(table.value());
for(TableSetter ts:this.ds)
{

if(ts.tableName.equals(table.value()))
{
select.ts=ts;
StatementMaker sm=new StatementMaker();
select.sqlStatement=sm.getSelectStatement(ts);
System.out.println(select.sqlStatement);
select.cls=cls;
select.previousOperation="select";   // this is done in order to validate the calling order of functions . 
select.orderByUsed=0;
select.whereUsed=0;
}
}



return select;
}catch(Exception e)
{
e.printStackTrace();
}

return null;
}
}
