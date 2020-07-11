package com.thinking.machines.SupportClasses;
public class PrimaryKey
{
public String attributeName;
public int size;
public String dataType;
public boolean isAutoIncrementPresent; 
public int precisionForDecimalDataType;
public PrimaryKey()
{
attributeName="";
size=0;
dataType="";
isAutoIncrementPresent=false;
precisionForDecimalDataType=-1;  
}
}
