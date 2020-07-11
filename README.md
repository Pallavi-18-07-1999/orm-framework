# orm-framework
# About the framework:-
while writing sql statements the programmer  has to rememeber the exact syntax.Even a minor mistake can cause him a huge trouble .so i have created an orm framework
that frees the programmer from remembering the sql queries. this framework maps a table of MySQL to a Java Class and the  field of that table is the property of that class.this framework basically  performs 4 types of operations.
1) insertion
2) deletion
3) updation
4) retrieving the data from the table.
 
this framework works only for MySQL.
Following data types of mysql are supported by this framework:-
1) int 
2) char 
3) varchar
4) date
5) time
6) datetime



# Benefits of orm framework
1) the programmer doesnt need to remember the sql statements.
2) sql queries are encapsulated into functions.so if you want to perform any operation then you just have to call the function any the sql query will be fired under the hood.
3) the user doesnt need to write the code for the class which maps it to the table .

# Usage
1) downlaod the  file from this repository and place it in any location.
2) go to the gui folder and run the batch file named as .bat.

![rsz_1dbsettigs](https://user-images.githubusercontent.com/66680113/87186074-73e89b00-c308-11ea-9262-b9ddfd4298ac.png)

3) enter the details click on save button . the details which you have entered will be stored in json format in a file named as DBConfiguration.json.
4) now if you want to create the java class then you need to switch to the tab named as generate Pojo classes.

![Tables](https://user-images.githubusercontent.com/66680113/87187659-2a4d7f80-c30b-11ea-904d-deeb7e600f8a.png)

5) here you can generate class files by clicking on any row of the table.

6) after clicking on generate class file button you have to choose the directory in which you want to place your class files .

![FileChooser](https://user-images.githubusercontent.com/66680113/87188010-b95a9780-c30b-11ea-906a-51c8fedead5a.png)

7) you can see the code of the java class in the directory you have chosen.


# Information about the methods of orm framework

user can create the java class by using the gui but if dont want to use gui tool then he can write the code for class . while writing the code he/she has to use the following annotations:-

1) Table :- you need to apply this annotation on the class level.
2) FieldAnnotation :- you have to apply this annotation on the properties of the class. you have to specify
3) PrimaryKeyAnnotaton :-you have to apply this annotation on the properties of the class in order to specify  primary key.
4) AutoIncrementAnnotation :- if the field 
5) UniqueAnnotation :-
6) NotNullAnnotation :-

```
import com.thinking.machines.annotations.*;
import java.util.Date;
import java.math.*;
@Table("student")
public class Student
{
@PrimaryKeyAnnotation  @AutoIncrementAnnotation @FieldAnnotation("rollNumber") 
private int rollNumber;
@FieldAnnotation("name") 
private String name;
@UniqueAnnotation @FieldAnnotation("aadharId")
private String aadharId;

public void setRollNumber(int rollNumber)
{
this.rollNumber = rollNumber;
}
public int getRollNumber()
{
return this.rollNumber;
}
public void setName(String name)
{
this.name = name;
}
public String getName()
{
return this.name;
}
public void setAadharId(String aadharId)
{
this.aadharId = aadharId;
}
public String getAadharId()
{
return this.aadharId;
}
}
```



you can use the following methods :- 
1) getInstance() :- to get the instance of TMDB class.
2) begin():-     :- to begin a transaction 
3) commit():-    :- to commit the transaction
4) save(Object obj) :- to insert a row in table
5) remove(Class cls,Object ...obj) :- to delete a row from table
6) update(Object obj)  :- to update a record in table 
7) select(Class cls) :- to retrieve data from table

```
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
Student student=new Student();
student.setRollNumber(1);
student.setName("pallavi");
student.setGender("M");
student.setAadharId("abc1223");
tmdb.save(student);
tmdb.delete(Student.class,2);
City city=new City();
city.setName("mumbai");
city.setCityCode(1);
tmdb.update(city);
tmdb.commit();    
}catch(Exception e)
{ 
e.printStackTrace();
}
}
}
```
in order to retieve the data from table you can use the following methods:-
1) orderBy(String column) :- to sort the records
2) asc()                  :- sort the records in ascending order
3) desc()                 :- sort the records in descending order
4) where(String column)   :- to extract only those records that fulfill a specified condition. 
5) and(String column)     :- to  filter records based on more than one condition which  are separated by and
6) or(String column)      :- to  filter records based on more than one condition which are separated by or
7) eq(Object obj)         :- to  
8) query()                :- will return a list of objects 

```
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
List<Student> list1=tmdb.select(Student.class).query();
List<Student> list2=tmdb.select(Student.class).orderBy("name").desc().query();
List<Student> list3=tmdb.select(Student.class).where("rollNumber").eq(1).query();
List<Student> list4=tmdb.select(Student.class).where("rollNumber").ne(2).orderBy("name").query();
tmdb.commit();    
}catch(Exception e)
{ 
e.printStackTrace();
}
}
}
```






















