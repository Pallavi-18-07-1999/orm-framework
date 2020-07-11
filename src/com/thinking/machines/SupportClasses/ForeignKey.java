package com.thinking.machines.SupportClasses;
import java.util.*;
public class ForeignKey
{
public String attributeName;
public int size;
public String dataType;
public boolean notNullConstraintApplied;
public String referencedTableName;
public String referencedColumn;
public boolean isUniqueConstraintApplied;
public boolean isAutoIncrementPresent;
public int precisionForDecimalDataType;

public ForeignKey()
{
attributeName="";
size=0;
dataType="";
notNullConstraintApplied=false;
referencedTableName="";
referencedColumn="";
isUniqueConstraintApplied=false;
isAutoIncrementPresent=false;
precisionForDecimalDataType=0;
}

}
