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

public class ClassList extends LinkedList<ClassInfo>
    {
    public static final long serialVersionUID = -5450382398316388132L;
    

    public void removeAll(Semester semesterTaken, ClassType type)
    {
        java.util.Iterator<ClassInfo> i = this.iterator();
        
        while(i.hasNext())
        {
            ClassInfo e = i.next();
            if(e.getSemesterTaken() == semesterTaken && e.getSubject() == type)
                i.remove();
        }
   }
    
    
    public ClassInfo find(Semester semesterTaken, String className)
    {
        for(ClassInfo c : this)
        {
            if((c.getSemesterTaken() == semesterTaken) &&
            (c.getTitle().equals(className)))
                    return c;
        }
        
        return null;
    }

    public boolean presentDuring(Semester timePeriod)
    {
        for(ClassInfo e : this)
        {
            if(e.getSemesterTaken() == timePeriod) return true;
        }
        
        return false;
    }
    
    public boolean presentDuring(Semester timePeriod, int progressReport)
    {
        for(ClassInfo e : this)
        {
            if(e.getSemesterTaken() == timePeriod && 
                    (progressReport == 0 || e.hasProgressReport(progressReport))) return true;
        }
        
        return false;
    }
    
    
    
    public boolean presentDuring(Semester timePeriod, boolean placeHoldersAccepted)
    {
        for(ClassInfo e : this)
        {
            if((e.getSemesterTaken() == timePeriod) &&
                    (!placeHoldersAccepted && e.getSubject() != ClassType.PLACEHOLDER) ||
                    (placeHoldersAccepted)) return true;
           
            //if(e.getSemesterTaken() == timePeriod) return true;
        }
        
        return false;
    }
    
    public Semester greatestSemester()
    {
        if(this.presentDuring(Semester.SUMMER)) return Semester.SUMMER;
        else if(this.presentDuring(Semester.SPRING)) return Semester.SPRING;
        else if(this.presentDuring(Semester.FALL)) return Semester.FALL;
        else return null;
    }
    
    
    
    public ClassList() {
    }
    
    public ClassList getAll(Semester timePeriod, ClassType subject)
    {
        ClassList selectedList = new ClassList();
            for(ClassInfo info : this)
            {
                if(info.getSemesterTaken().equals(timePeriod) && info.getSubject().equals(subject))
                    selectedList.add(info);
            }
            
            return selectedList;
        
    }
    
        //private LinkedList<ClassInfo> classList;
        public ClassList getAll(Semester timePeriod)
        {
            ClassList onSemester = new ClassList();
            for(ClassInfo info : this)
            {
                if(info.getSemesterTaken() == timePeriod)
                    onSemester.add(info);
            }
            
            return onSemester;
        }
        
        public ClassList getAll(Semester timePeriod, int progressReport)
        {
            ClassList onSemester = new ClassList();
            for(ClassInfo info : this)
            {
                //if progressReport == 0(is final return regardless of hasProgressReport() value
                if(info.getSemesterTaken() == timePeriod && 
                        (info.hasProgressReport(progressReport) || progressReport == 0))
                        onSemester.add(info);
                
            }
            
            return onSemester;
        }
        
        public ClassList getAll(ClassType subject)
        {
            ClassList onSubject = new ClassList();
            
            for(ClassInfo info : this)
            {
                if(info.getSubject() == subject)
                    onSubject.add(info);
            }
            return onSubject;
        }
        
        public boolean onlyHasPlaceHolders()
        {
            for(ClassInfo info : this)
            {
                if(info.getSubject() != ClassType.PLACEHOLDER)
                    return false;
            }
            return true;
        }
        
        
        public ClassList getAll(float high, float low)
        {
            //ListIterator<ClassInfo> pos = this.listIterator();
            ClassList range = new ClassList();        
            
            for(ClassInfo info : this)
            {
                if(info.getGrade().doubleValue() >= low)
                {
                    if(info.getGrade().doubleValue() <= high)
                        range.add(info);
                }
            }
            return range;            
        }


    }
