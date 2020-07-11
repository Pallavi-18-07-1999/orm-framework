package com.thinking.machines.interfaces;
import com.thinking.machines.SupportClasses.*;
import java.util.*;
public interface DeleteValidator
{
public boolean success(TableSetter ts,List<Object> obj);
public String validationFailedMessage();
}  
