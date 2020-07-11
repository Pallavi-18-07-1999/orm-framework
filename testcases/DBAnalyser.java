

import com.thinking.machines.SupportClasses.*;
  
import java.util.*;
import java.math.*;
class DBAnalyser
{
public static void main(String gg[])
{
try
{


 


TMDB tmdb=TMDB.getInstance();
tmdb.begin();
List<Student> student=tmdb.select(Student.class).orderBy("name").query();

for(Student obj:student)
{
System.out.println(obj.getName());
}

tmdb.commit();    




}catch(Exception e)
{ 
e.printStackTrace();
}
}
}