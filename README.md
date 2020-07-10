# orm-framework
# About the framework:-
while writing sql statements the programmer  has to rememeber the exact syntax.Even a minor mistake can cause him a huge trouble .so i have created an orm framework
that frees the programmer from remembering the sql queries. this framework maps a table of MySQL to a Java Class and the  field of that table is the property of that class.this framework basically  performs 4 types of operations 
1) insert
2) delete
3) update
4) retrieving the data from the table.
this framework works only for MySQL.

# Benefits of orm framework
1) the programmer doesnt need to remember the sql statements.
2) sql queries are encapsulated into functions.so if you want to perform any operation then you just have to call the function any the sql query will be fired under the hood.
3) the user doesnt need to create the java class .java 

# Usage
1) downlaod the  file from this repository and place it in any location.
2) go to the gui folder and run the batch file named as .bat.

![rsz_1dbsettigs](https://user-images.githubusercontent.com/66680113/87186074-73e89b00-c308-11ea-9262-b9ddfd4298ac.png)
3) enter the details click on save button . the details which you have entered will be stored in json format in a file named as DBConfiguration.json.
4) now if you want to create the java class then you need to switch to the tab named as generate Pojo classes.
5) here you can generate class files by clicking on any row of the table.
6) after clicking on generate class file button you have to choose the directory in which you want to place your class files .










