package com.thinking.machines.SupportClasses;
import com.thinking.machines.interfaces.*;
import java.util.*;
public class Validators
{
private static List<InsertValidator> insertValidatorList;
private static List<DeleteValidator> deleteValidatorList;
private static List<UpdateValidator> updateValidatorList;
public Validators()
{
insertValidatorList=new LinkedList<>();
deleteValidatorList=new LinkedList<>();
updateValidatorList=new LinkedList<>();
}
public static List<InsertValidator> getInsertValidator()
{
InsertValidator iv1=new PrimaryKeyValidator(); 
InsertValidator iv2=new ForeignKeyValidator();

InsertValidator iv3=new UniqueValidator();
InsertValidator iv4=new NullValidator();
InsertValidator iv5=new SizeValidator(); 
insertValidatorList=new LinkedList<>();  
insertValidatorList.add(iv1);
insertValidatorList.add(iv2);

insertValidatorList.add(iv3);
insertValidatorList.add(iv4);
insertValidatorList.add(iv5); 
return insertValidatorList;
}
public static List<DeleteValidator> getDeleteValidator()
{
deleteValidatorList=new LinkedList<>();
deleteValidatorList.add(new DeleteEmptySetValidator());
deleteValidatorList.add(new DeletePKValidator());
deleteValidatorList.add(new DeleteFKValidator());
return  deleteValidatorList;
}
public static List<UpdateValidator> getUpdateValidator()
{
updateValidatorList=new LinkedList<>();  
updateValidatorList.add(new UpdateEmptySetValidator());
updateValidatorList.add(new UpdatePKValidator());
updateValidatorList.add(new UpdateUniqueValidator());
updateValidatorList.add(new UpdateFKValidator());
updateValidatorList.add(new UpdateSizeValidator());
updateValidatorList.add(new UpdateNotNullValidator());
return updateValidatorList;
}


}