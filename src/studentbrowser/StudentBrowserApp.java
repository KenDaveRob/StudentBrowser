/*
 * StudentBrowserApp.java
 */

package studentbrowser;

import java.io.File;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import java.util.*;
    
//new StudentListModel(studentList)
    
/**
 * The main class of the application.
 */
public class StudentBrowserApp extends SingleFrameApplication {
    private static NavigableSet<StudentInfo2> studentsInformation;
    private static java.io.File initialFile;

    public static File getInitialFile() {
        return initialFile;
    }

    

    public static NavigableSet<StudentInfo2> getStudentList() {
        return studentsInformation;
    }

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new StudentBrowserView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of StudentBrowserApp
     */
    public static StudentBrowserApp getApplication() {
        return Application.getInstance(StudentBrowserApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        int x = 0;
        assert x == 0 : "Not null";
        //System.out.println("TestAtStartOfmainMethod");
        try
        {
            initialFile = new java.io.File(System.getProperty("user.home")+System.getProperty("file.separator")+"initial.obj");
            //Debug.line("user.dir:"+new java.io.File(
            //System.getProperty("user.dir")));
            try
            {
                if(initialFile.exists())
                {
                    java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(initialFile));
                    //Object reading
                    java.util.NavigableSet<Object> newStudentsTemp = (java.util.TreeSet<Object>)in.readObject();
                    studentsInformation = new java.util.TreeSet<StudentInfo2>();
                    //java.util.NavigableSet<StudentInfo2> newStudentsInfo = new java.util.TreeSet<StudentInfo2>();
                    if(newStudentsTemp.first().getClass() == StudentInfo.class)
                    {
                        for(Object o : newStudentsTemp)
                        {
                            studentsInformation.add(new StudentInfo2((StudentInfo)o));
                        }
                            
                    }
                    else if(newStudentsTemp.first().getClass() == StudentInfo2.class)
                    {
                        for(Object o : newStudentsTemp)
                        {
                            studentsInformation.add((StudentInfo2)o);
                        }
                    }
                    //Old code
                    //studentsInformation = (java.util.TreeSet<StudentInfo>)in.readObject();
                    in.close();
                }
                else
                {
                    java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream("initial.obj"));
                    java.util.NavigableSet<Object> newStudentsTemp = (java.util.TreeSet<Object>)in.readObject();
                    studentsInformation = new java.util.TreeSet<StudentInfo2>();
                    //java.util.NavigableSet<StudentInfo2> newStudentsInfo = new java.util.TreeSet<StudentInfo2>();
                    if(newStudentsTemp.first().getClass() == StudentInfo.class)
                    {
                        for(Object o : newStudentsTemp)
                        {
                            studentsInformation.add(new StudentInfo2((StudentInfo)o));
                        }
                            
                    }
                    else if(newStudentsTemp.first().getClass() == StudentInfo2.class)
                    {
                        for(Object o : newStudentsTemp)
                        {
                            StudentInfo2 newStudent = ((StudentInfo2)o);
                            newStudent.getClasses().clear();
                            for(int currentLevel = 0; currentLevel < ((StudentInfo2)o).getClasses().getSize(); currentLevel++)
                            {
                                for(ClassInfo i : ((StudentInfo2)o).getClasses().getFromIndex(currentLevel))
                                    newStudent.getClasses().getFromIndex(currentLevel).add(new ClassInfo2(i));
                            }
                            studentsInformation.add(newStudent);
                            //studentsInformation.add((StudentInfo2)o); old non ClassInfo2 cast code
                        }
                    }
                    //Old code
                    //studentsInformation = (java.util.TreeSet<StudentInfo>)in.readObject();
                    in.close();
                }
            }
            catch(Exception e)
            {
                Debug.line("ERROR: opening initial file.");
                e.printStackTrace();
            }
            


            launch(StudentBrowserApp.class, args);
        }
        catch(Exception e)
        {
            System.out.println("ERROR: Verson May Be Incorrect, Download Runtimes @ http://www.java.com/en/download/index.jsp");
            javax.swing.JFrame error = new javax.swing.JFrame("Error");
            //error.getContentPane()
            javax.swing.JOptionPane.showMessageDialog(null, "ERROR: Verson May Be Incorrect, Download Runtimes @ http://www.java.com/en/download/index.jsp", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
