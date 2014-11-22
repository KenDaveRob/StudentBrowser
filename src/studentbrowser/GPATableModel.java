/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

import java.util.*;

//new Table Constructor code
//new GPATableModel(studentsInformation)

/**
 *
 * @author GuestUser
 */
public class GPATableModel extends javax.swing.table.AbstractTableModel
{
    /*
             9th Grade FALL  9th Grade SPRING...
    Student1  <F,P1,P2,P3>     <F,P1,P2,P3>...
    Student2  <F,P1,P2,P3>     <F,P1,P2,P3>...
    ...
    */
            
    private ArrayList<ArrayList<ArrayList<Float>>> gpas;
    NavigableSet<StudentInfo2> studentsInformation;
    public GPATableModel(NavigableSet<StudentInfo2> studentsInformation) 
    {
        this.studentsInformation = studentsInformation;
        generateGPAs();
        
    }
    
    
    
    public void generateGPAs()
    {
        gpas = new ArrayList<ArrayList<ArrayList<Float>>>(studentsInformation.size());
        
       
        for(StudentInfo2 s : studentsInformation)
        {
            ArrayList<ArrayList<Float>> studentGPAs = new ArrayList<ArrayList<Float>>(12);
            
            for(int grade = 9; grade <= 12; grade++)
            {
                ArrayList<Float> progressReportGPAs;
                
                if(s.getClasses().hasGradeList(grade))
                {
                    progressReportGPAs = new ArrayList<Float>(4);
                    
                    if(s.getClasses().getGradeList(grade).presentDuring(Semester.FALL))
                    {
                        progressReportGPAs.add(new Float(s.getGPAFor((grade), Semester.FALL)));

                        for(int p = 1; p < 4; p++)
                            progressReportGPAs.add(new Float(s.getGPAFor((grade), Semester.FALL, p)));
                        
                        
                    }
                    
                    else
                        for(int p = 0; p < 4; p++)
                            progressReportGPAs.add(new Float(-1));
                    
                    studentGPAs.add(progressReportGPAs);
                    progressReportGPAs = new ArrayList<Float>(4);
                    
                    if(s.getClasses().getGradeList(grade).presentDuring(Semester.SPRING))
                    {
                        progressReportGPAs.add(new Float(s.getGPAFor((grade), Semester.SPRING)));
                        
                        for(int p = 1; p < 4; p++)
                            progressReportGPAs.add(new Float(s.getGPAFor((grade), Semester.SPRING, p)));
                        
                    }
                    
                    else
                        for(int p = 0; p < 4; p++)
                            progressReportGPAs.add(new Float(-1));
                    
                    studentGPAs.add(progressReportGPAs);
                    progressReportGPAs = new ArrayList<Float>(4);
                    
                    if(s.getClasses().getGradeList(grade).presentDuring(Semester.SUMMER))
                    {
                        progressReportGPAs.add(new Float(s.getGPAFor((grade), Semester.SUMMER)));
                        for(int p = 1; p < 4; p++)
                            progressReportGPAs.add(new Float(s.getGPAFor((grade), Semester.SUMMER, p)));
                    }
                    
                    else
                        for(int p = 0; p < 4; p++)
                            progressReportGPAs.add(new Float(-1));
                    
                    studentGPAs.add(progressReportGPAs);
                    progressReportGPAs = new ArrayList<Float>(4); 
                }
                else
                {
                    progressReportGPAs = new ArrayList<Float>(4);
                    for(int p = 0; p < 4; p++)
                        progressReportGPAs.add(new Float(-1));
                    studentGPAs.add(progressReportGPAs);
                    studentGPAs.add(progressReportGPAs);
                    studentGPAs.add(progressReportGPAs);
                }
                
            }
                    
            gpas.add(studentGPAs);
        }
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0) return "Name";
        //1-to-12
        //0,1,2->0
        //3,4,5->1
        //6,7,8->2
        //9,10,11->3
        StringBuilder name = new StringBuilder(Integer.toString((int)(Math.floor((column-1)/3))+9)+"th Grade");
        //column--;
        if (column % 3 == 1)
        {
            name.append(" Fall");
        }
        
        else if(column % 3 == 2)
        {
            name.append(" Spring");
        }
        
        else
        {
            name.append(" Summer");
        }
        
        return name.toString();
    }
    
    

    public int getRowCount() {
        return gpas.size();
        //return 10;
    }

    public int getColumnCount() {
        return 13;
    }

    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        //Probably ineffeicent search should be changed
        if(columnIndex == 0)
        {
            int pos = 0;
            for(StudentInfo2 e : studentsInformation)
            {
                if(rowIndex == pos) 
                    return e.getName();
            
                pos++;
            }
            return "Name not found";
        }
        
        else
        {
            if(gpas.get(rowIndex).get(columnIndex-1).get(0)<0)
                return "N.A";
            
            java.lang.StringBuilder combinedProgressGPAs = 
                    new java.lang.StringBuilder("(F=["+gpas.get(rowIndex).get(columnIndex-1).get(0));
            //(F=[G], P1=[G],..)
            
            for(int p = 1; p < 4; p++)
            {
                Float currentProgress = gpas.get(rowIndex).get(columnIndex-1).get(p);
                combinedProgressGPAs.append("], P"+p+"=[");
                if(currentProgress == -1)
                    combinedProgressGPAs.append("NM");
                else
                    combinedProgressGPAs.append(currentProgress);
            }
            
            combinedProgressGPAs.append("])");
                
            return combinedProgressGPAs.toString();
            /*
            if(gpas.get(rowIndex).get(columnIndex-1)<0)
                return "N.A";
            else
                return gpas.get(rowIndex).get(columnIndex-1);*/
        }
        
        //return "EMPTY";
       
    }

}