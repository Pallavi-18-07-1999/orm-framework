package com.thinking.machines.SupportClasses;
public class AttributeSetter
{
public String attributeName;
public int size;
public String dataType;
public boolean notNullConstraintApplied;
public boolean isUniqueConstraintApplied;
public boolean isAutoIncrementPresent;
public int precisionForDecimalDataType;
public AttributeSetter()
{
attributeName="";
size=0;
dataType="";
notNullConstraintApplied=false;
isUniqueConstraintApplied=false;
isAutoIncrementPresent=false;
precisionForDecimalDataType=-1;
}
}
