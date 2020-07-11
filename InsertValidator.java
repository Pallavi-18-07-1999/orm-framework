package com.thinking.machines.interfaces;
import com.thinking.machines.SupportClasses.*;
public interface InsertValidator
{
public boolean success(TableSetter ts,Object obj);
public String validationFailedMessage();
}