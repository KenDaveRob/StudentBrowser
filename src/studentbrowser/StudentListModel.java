/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

import java.util.*;

/**
 *
 * @author GuestUser
 */
public class StudentListModel extends javax.swing.AbstractListModel
{
    private NavigableSet<StudentInfo2> studentsInformation;
    

    /*public StudentListModel(List<StudentInfo> studentList) {
        this.studentList = new ArrayList<String>();
        for(StudentInfo e : studentList) this.studentList.add(e.toString());
    }*/
    
    public StudentListModel(NavigableSet<StudentInfo2> studentsInformation)
    {
        this.studentsInformation = studentsInformation;
    }
    
    public int getSize() {
        return studentsInformation.size();
    }

    public Object getElementAt(int index) {
        int pos = 0;
        //Probably ineffeicent search should be changed
        for(StudentInfo2 e : studentsInformation)
        {
            if(index == pos) return e;
            
            pos++;
        }
        
        return null;
    }

}


