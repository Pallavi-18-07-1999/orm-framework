import com.thinking.machines.SupportClasses.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import com.thinking.machines.SQLConnection.*;
import java.sql.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.tools.*;
import com.google.gson.*;
import javax.swing.table.*;
import javax.swing.event.*;
class GUITool implements ActionListener ,ListSelectionListener
{
private  JTextField  dbNameField;
private JTextField userNameField;
private  JTextField passwordField;
private JPanel panel2;
private JTabbedPane pojoClassesGenerator;

private JButton generateClassFile;
private JButton save;
private String className;
private String tableName;
private String dbName;
private Connection con;
private DatabaseMetaData databaseMetaData;
private JTable tableList;
private JList list;
private int[] selectedRows;
private TableModel tableModel;
private JFrame gui;
public void setGUI()
{
gui=new JFrame();
gui.setSize(1100,1100);
gui.setVisible(true);

JTabbedPane dbSettings=new JTabbedPane();
dbSettings.setBounds(0,0,1365,700);   


JPanel panel1=new JPanel();
panel1.setLayout(null);
panel1.setBounds(440,150,800,480);
panel1.setVisible(true);
panel2=new JPanel();

JLabel dbNameLabel=new JLabel("enter name of datatbase :-");
dbNameLabel.setBounds(490,200,170,20);
JLabel userNameLabel =new JLabel("enter name of user :-");
userNameLabel.setBounds(490,260,170,20);
JLabel passwordLabel =new JLabel("enter password :-");
passwordLabel.setBounds(490,320,170,20);
dbNameField=new JTextField(20);
dbNameField.setBounds(650,200,150,20);
userNameField=new JTextField(20);
userNameField.setBounds(650,260,150,20);
passwordField=new JTextField(20);
passwordField.setBounds(650,320,150,20);
save=new JButton("Save");
save.setBounds(585,380,90,30);
save.addActionListener(this);
panel1.add(dbNameLabel);
panel1.add(userNameLabel);
panel1.add(passwordLabel);
panel1.add(dbNameField);
panel1.add(userNameField);
panel1.add(passwordField);
panel1.add(save);
dbSettings.add("database settings",panel1);
dbSettings.add("generate Pojo classes",panel2);



gui.add(dbSettings);
pojoClassesGenerator=new JTabbedPane();
gui.add(pojoClassesGenerator);
}


public void actionPerformed(ActionEvent event)
{
try
{
if(event.getSource()==save)
{
dbName=dbNameField.getText();
String userName=userNameField.getText();
String password=passwordField.getText();
Class c=GUIMaker.class;
String pathToFile=c.getResource(".").getPath();
System.out.println(pathToFile);
int index1=pathToFile.indexOf("classes");
pathToFile=pathToFile.substring(0,index1);
pathToFile += ("dbSettings"+"/DBConfiguration.json");
System.out.println(pathToFile);

File file=new File(pathToFile);
if(file.exists()==true)
{
if(!file.delete()) System.out.println("failed");
if(file.exists())  System.out.println("yess");
file.createNewFile();
}
RandomAccessFile raf=new RandomAccessFile(file,"rw");
raf.writeBytes("{"+"\n");
raf.writeBytes("\""+"database"+"\""+":"+"\""+dbName+"\""+","+"\n");
raf.writeBytes("\""+"username"+"\""+":"+"\""+userName+"\""+","+"\n");
raf.writeBytes("\""+"password"+"\""+":"+"\""+password+"\""+","+"\n");
raf.writeBytes("\""+"Driver"+"\""+":"+"\""+"com.mysql.cj.jdbc.Driver"+"\""+","+"\n");
raf.writeBytes("\""+"connectionString"+"\""+":"+"\""+"jdbc:mysql://localhost:3306/"+"\""+"\n");
raf.writeBytes("}");
raf.close();
BufferedReader br=new BufferedReader(new FileReader(pathToFile));
StringBuilder sb=new StringBuilder();
String line;
while(true)
{
line=br.readLine();
if(line==null) break;
sb.append(line);
}
String jsonString=sb.toString();
System.out.println(jsonString);
Gson gson=new Gson();
DAOConnection dao=(DAOConnection)gson.fromJson(jsonString,DAOConnection.class);
Connection con=dao.getConnection();
if(con==null) System.out.println("yess it is null");
databaseMetaData=con.getMetaData();


generateClassFile=new JButton("Generate Class File");
generateClassFile.setBounds(450,350,200,30);
generateClassFile.setVisible(false);
generateClassFile.addActionListener(this);







String view[]={"View"};
ResultSet views=databaseMetaData.getTables(dbName,null,null,view);
String table[]={"Table"};
ResultSet tables=databaseMetaData.getTables(dbName,null,null,table);
java.util.List<Tables>list=new LinkedList<>();
while(tables.next())
{
Tables t=new Tables();
t.table=tables.getString(3);
t.type="Table";

className=tables.getString(3);
int index=0;
while(index<className.length() && index!=-1)
{
index=className.indexOf("_");
if(index!=-1)
{
char ch1=Character.toUpperCase(className.charAt(index+1));
className=className.substring(0,index)+ch1+className.substring(index+2);
}
}
char ch=Character.toUpperCase(className.charAt(0));
className=ch+className.substring(1);

t.pojoClass=className;
list.add(t);
}
while(views.next())
{
Tables t=new Tables();
t.table=views.getString(3);
t.type="View";

className=views.getString(3);
int index=0;
while(index<className.length() && index!=-1)
{
index=className.indexOf("_");
if(index!=-1)
{
char ch1=Character.toUpperCase(className.charAt(index+1));
className=className.substring(0,index)+ch1+className.substring(index+2);
}
}
char ch=Character.toUpperCase(className.charAt(0));
className=ch+className.substring(1);
t.pojoClass=className;
list.add(t);

}

panel2.setLayout(null);
tableModel=new TableModel(list);
tableList=new JTable(tableModel);
tableList.setRowHeight(40);
tableList.setCellSelectionEnabled(false);
tableList.setRowSelectionAllowed(true);
tableList.setColumnSelectionAllowed(false);
tableList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
JScrollPane jsp=new JScrollPane(tableList,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
jsp.setBounds(200,100,800,40*5);


tableList.getSelectionModel().addListSelectionListener(this);

 
panel2.add(jsp);

panel2.add(generateClassFile);

}
else
{
JFileChooser file=new JFileChooser("C:",FileSystemView.getFileSystemView());

file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
int optionChosen=file.showSaveDialog(null);
if(optionChosen==JFileChooser.APPROVE_OPTION)
{
int output = JOptionPane.showConfirmDialog(gui, "do you want to associate this file to a package","Select an option",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
String packageName="";
if(output == JOptionPane.YES_OPTION)
{
packageName=JOptionPane.showInputDialog(gui,"Enter Name of package");  
} 
 
int i=0;
for(i=0;i<selectedRows.length;i++)
{
String pathToFile=file.getSelectedFile().getAbsolutePath();
System.out.println("i"+i);
File f=new File(pathToFile+File.separator+tableModel.getValueAt(selectedRows[i],3)+".java");
tableName=(String)tableModel.getValueAt(selectedRows[i],1);
className=(String)tableModel.getValueAt(selectedRows[i],3);
if(f.exists()==false)
{
System.out.println(pathToFile);
f.createNewFile();
}
RandomAccessFile raf=new RandomAccessFile(f,"rw");
if(packageName.trim().length()!=0)
raf.writeBytes("package "+packageName+";"+"\n");
raf.writeBytes("import com.thinking.machines.annotations.*;"+"\n");
raf.writeBytes("import java.util.Date;"+"\n");
raf.writeBytes("import java.math.*;"+"\n");
raf.writeBytes("@Table(\""+tableName+"\")"+"\n");
raf.writeBytes("public class "+className+"\n");

raf.writeBytes("{"+"\n");
if(tableName!=null)
{
ResultSet attributes=databaseMetaData.getColumns(dbName,null,tableName,null);
java.util.List<String> list=new LinkedList<>();
java.util.List<String> dataTypeList=new LinkedList<>();

while(attributes.next())
{
boolean pkOrUnique=false;
boolean isPrimaryKey=false;
ResultSet pkey=databaseMetaData.getPrimaryKeys(dbName,null,tableName);
while(pkey.next())
{ 
String primaryKeyName=pkey.getString(4);
if(primaryKeyName.equals(attributes.getString(4)))
{
raf.writeBytes("@PrimaryKeyAnnotation"+" ");
isPrimaryKey=true;
pkOrUnique=true;
}
}
ResultSet unique=databaseMetaData.getIndexInfo(dbName,null,tableName,true,false);
while(unique.next())
{
if(unique.getString(9).equals(attributes.getString(4)) && isPrimaryKey==false)
{
raf.writeBytes("@UniqueAnnotation"+" ");
pkOrUnique=true;
}
}
ResultSet fkey=databaseMetaData.getImportedKeys(dbName,null,tableName);  
while(fkey.next())
{
if(fkey.getString(8).equals(attributes.getString(4)))  
{
raf.writeBytes("@ForeignKeyAnnotation(tableName = "+"\""+fkey.getString(3)+"\""+","+" tableColumn = "+"\""+fkey.getString(4)+"\""+")"+" ");
}
}

raf.writeBytes("@FieldAnnotation("+"\""+attributes.getString(4)+"\""+")"+" ");
if(attributes.getString(23).equals("YES"))
{
raf.writeBytes("@AutoIncrementAnnotation()"+" ");
}
if(attributes.getString(18).equals("NO") && isPrimaryKey==false)
raf.writeBytes("@NotNullAnnotation()"+" ");
raf.writeBytes("\n"+"private");
String dataType=attributes.getString(6);
if(dataType.equals("CHAR")||dataType.equals("VARCHAR"))
{
dataTypeList.add("String");
raf.writeBytes(" String ");
}
else if(dataType.equals("INT"))
{
dataTypeList.add("int");
raf.writeBytes(" int ");
}
else if(dataType.equals("BOOLEAN"))
{
dataTypeList.add("boolean");
raf.writeBytes(" boolean ");
}
else if(dataType.equals("BIT"))
{
dataTypeList.add("byte");
raf.writeBytes(" byte ");
}
else if(dataType.equals("DECIMAL"))
{
dataTypeList.add("BigDecimal");
raf.writeBytes(" BigDecimal ");
}
else if(dataType.equals("DATE"))
{
dataTypeList.add("Date");
raf.writeBytes(" Date ");
}
else if(dataType.equals("TIME"))
{
dataTypeList.add("Date");
raf.writeBytes(" Date ");
}
else 
{
dataTypeList.add("Date");
raf.writeBytes(" Date ");
}
raf.writeBytes(attributes.getString(4)+";"+"\n");
list.add(attributes.getString(4));
}
int j=0;
for(String attribute:list)
{
String str=Character.toUpperCase(attribute.charAt(0))+attribute.substring(1);
raf.writeBytes("public void set"+str+"("+dataTypeList.get(j)+" "+attribute+")"+"\n");
raf.writeBytes("{"+"\n");
raf.writeBytes("this."+attribute+" = "+attribute+";"+"\n");
raf.writeBytes("}"+"\n");
raf.writeBytes("public "+dataTypeList.get(j)+" get"+str+"()"+"\n");
raf.writeBytes("{"+"\n");
raf.writeBytes("return this."+attribute+";"+"\n");
raf.writeBytes("}"+"\n");
j++;
}
tableName=null;
}
raf.writeBytes("}");

raf.close();
JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
int finalResult=compiler.run(null,null,null,pathToFile+File.separator+className+".java");
}
}
}
}catch(Exception e)
{
e.printStackTrace();
}
}
public void valueChanged(ListSelectionEvent event) 
{
System.out.println("row clicked!");
generateClassFile.setVisible(true);

selectedRows = tableList.getSelectedRows();

}
}


class TableModel extends AbstractTableModel
{

private String[] columnNames={"S.No.","Table/View","Type","Equivalen POJO Class"};

private java.util.List<Tables> list;
public TableModel(java.util.List<Tables>list) 
{
System.out.println("size of list"+list.size());
this.list =list;
}
public int getColumnCount() 
{
return columnNames.length;
}
public int getRowCount()
{
return list.size();
} 
@Override
public String getColumnName(int col) 
{
System.out.println(columnNames[col]);
return columnNames[col];
} 
public Class getColumnClass(int col) 
{
try
{
if (col == 0) 
{
return Class.forName("java.lang.Integer");
}
else if (col == 1) 
{
return Class.forName("java.lang.String");
}
else if (col == 2) 
{
return Class.forName("java.lang.String");
}
else
{
return Class.forName("java.lang.String");
}
}catch(Exception e)
{
e.printStackTrace();
}
return null;
}
@Override
public Object getValueAt(int row, int col) 
{
Object temp = null;

if (col == 0) 
{

temp =row+1;
}
else if (col == 1) 
{
temp = list.get(row).table;
}
else if (col == 2) 
{
temp = list.get(row).type;
}
else
{
temp=list.get(row).pojoClass;
}
return temp;
}
}
class Tables
{
public String table;
public String type;
public String pojoClass;
}
public class GUIMaker
{
public static void main(String gg[])
{
GUITool gui=new GUITool();
gui.setGUI();
}
}