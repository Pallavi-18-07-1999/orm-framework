package com.thinking.machines.SupportClasses;
public class StatementMaker
{
private String insertStatement;
private String deleteStatement;
private String updateStatement;
private String selectStatement;
public StatementMaker()
{
insertStatement="";
deleteStatement="";
updateStatement="";
selectStatement="";
}
public String getInsertStatement(TableSetter ts)
{
insertStatement="insert into "+ts.tableName+" ( ";
int cnt=0;
for(AttributeSetter as:ts.attributeSetterList)
{
if(as.isAutoIncrementPresent==false)
{
cnt++;
insertStatement+=as.attributeName+",";
}
}
insertStatement=insertStatement.substring(0,insertStatement.lastIndexOf(","));
insertStatement+= ") values (";
int i;
for(i=0;i<cnt;i++)
insertStatement+="?,";
insertStatement=insertStatement.substring(0,insertStatement.lastIndexOf(","));
insertStatement+=")";
return insertStatement;
}
public String getDeleteStatement(TableSetter ts)
{
deleteStatement="delete from "+ts.tableName+ " where ";
int i=0;
for(PrimaryKey pkey:ts.pk)
{
if(ts.pk.size()-1==i)
deleteStatement += (pkey.attributeName+" = ?");
else
deleteStatement += (pkey.attributeName+" = ?"+" and ");
i++;
}
return deleteStatement;
}
public String getUpdateStatement(TableSetter ts)
{
updateStatement="update "+ts.tableName+" set ";
for(AttributeSetter as:ts.attributeSetterList)
{
int z=0;
for(PrimaryKey pkey:ts.pk)
{
if(as.attributeName.equals(pkey.attributeName))
{
z=1;
break;
}
}
if(z==0)
{
updateStatement+=(as.attributeName+"= ? ,");



}
}

updateStatement=updateStatement.substring(0,updateStatement.lastIndexOf(","));

updateStatement+=" where ";
int i=0;
for(PrimaryKey pkey:ts.pk)
{
if(ts.pk.size()-1==i)
updateStatement += (pkey.attributeName+" = ?");
else
updateStatement += (pkey.attributeName+" = ?"+" and ");
i++;

}
return updateStatement;
}
public String getSelectStatement(TableSetter ts)
{
selectStatement="select * from "+ts.tableName;
return selectStatement;
}


}