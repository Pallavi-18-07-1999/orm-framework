package com.thinking.machines.SupportClasses;
import java.util.*;
public class TableSetter
{
public String tableName;
public List<AttributeSetter> attributeSetterList;
public List<PrimaryKey> pk;
public Map<String,List<ForeignKey>> fk;
public Map<String,List<ChildTables>> childTables; 
public boolean isView;
public TableSetter()
{
tableName="";
attributeSetterList=null;
childTables=new HashMap<>();
isView=false;
}
}
