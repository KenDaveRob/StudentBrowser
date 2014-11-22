/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


    
/**
 *
 * @author GuestUser
 * 
 */


public class ClassInfo implements Comparable<ClassInfo>, java.io.Serializable
{

    private Semester semesterTaken;
    private String title;
    private ClassType subject;
    private Grade grade;
    private java.util.ArrayList<Grade> progressGrades;
    public static final long serialVersionUID = 5837987284690652828L;
    
    public void print()
    {
        System.out.println(this.subject + ": " + this.title + " taken " + this.semesterTaken);
        this.printGrades();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass())
            return false;
        return semesterTaken.equals(((ClassInfo)(obj)).semesterTaken) &&
                title.equals(((ClassInfo)(obj)).title) && 
                subject.equals(((ClassInfo)(obj)).subject) &&
                grade.equals(((ClassInfo)(obj)).grade);
    }
    
    public ClassInfo()
    {
        this.semesterTaken = null;
        this.title = new String();
        this.subject = null;
        try
        {
            this.grade = new Grade("NM");
        }
        catch(Exception e)
        {
            Debug.line("Error creating Final Grade with 'NM':");
            e.getMessage();
            e.printStackTrace();
        }
        
        this.initializeProgressGrades();
         
        /*progressGrades = new ArrayList<Grade>(3);
        int counter = 0;
        for(Grade g : progressGrades) 
        {
            try
            {
                g.setLetterValue("NM");
            }
            catch(Exception e)
            {
                Debug.line("Error creating progress grade number "+counter+" with 'NM':");
                e.getMessage();
                e.printStackTrace();
            }
            counter++;
        }*/
    }

    public ClassInfo(Semester semesterTaken, String title, ClassType subject, Grade grade) {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = grade;
        
        this.initializeProgressGrades();
        
    }
    public ClassInfo(Semester semesterTaken, String title, ClassType subject) {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = new Grade();
        
        this.initializeProgressGrades();
        
    }
    public ClassInfo(Semester semesterTaken, String title, ClassType subject, 
            Grade grade, ArrayList<Grade> progressGrades)
    {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = grade;
        this.progressGrades = progressGrades;
        this.progressGrades.ensureCapacity(3);
    }
    
    public ClassInfo(ClassInfo classInfo)
    {
        this.grade = new Grade(classInfo.getGrade());
        this.semesterTaken = classInfo.getSemesterTaken();
        this.subject = classInfo.getSubject();
        this.title = classInfo.getTitle();
        this.progressGrades = classInfo.progressGrades;
        //this.initializeProgressGrades();
        
        /*progressGrades = new ArrayList<Grade>(3);
        int counter = 0;
        for(Grade g : progressGrades) 
        {
            try
            {
                g.setLetterValue("NM");
            }
            catch(Exception e)
            {
                Debug.line("Error creating progress grade number "+counter+" with 'NM':");
                e.getMessage();
                e.printStackTrace();
            }
            counter++;
        }*/
    }
    
    private void initializeProgressGrades()
    {
        Debug.line("ran initializeProgressGrades");
        
        progressGrades = new ArrayList<Grade>(3);
        int counter = 0;
        for(;counter < 3; counter++)
        {
            try
            {
                progressGrades.add(new Grade("NM"));
                
            }
            catch(Exception e)
            {
                Debug.line("Error creating progress grade number "+counter+" with 'NM':");
                e.getMessage();
                e.printStackTrace();
            }
        }
    }
    
    /*public ClassInfo(Semester semesterTaken, String title, ClassType subject, Grade grade) {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = new Grade(grade);
        progressGrades = new ArrayList<Grade>(3);
        int counter = 0;
        for(Grade g : progressGrades) 
        {
            try
            {
                g.setLetterValue("NM");
            }
            catch(Exception e)
            {
                Debug.line("Error creating progress grade number "+counter+" with 'NM':");
                e.getMessage();
                e.printStackTrace();
            }
            counter++;
        }
    }
    public ClassInfo(Semester semesterTaken, String title, ClassType subject, 
            Grade grade, ArrayList<Grade> progressGrades)
    {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = new Grade(grade);
        this.progressGrades = new ArrayList<Grade>();
        for(Grade g: progressGrades)
            this.progressGrades.add(new Grade(g));
        this.progressGrades.ensureCapacity(3);
    }*/

    public void setSemesterTaken(Semester semesterTaken) {
        this.semesterTaken = semesterTaken;
    }

    public void setSubject(ClassType subject) {
        this.subject = subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }
        
    public int getProgressGradeLength()
        { return progressGrades.size(); }

        public Semester getSemesterTaken() {
            return semesterTaken;
        }

        public ClassType getSubject() {
            return subject;
        }
        
        public Grade getProgressGrade(int progressReportNumber)
        {
            if(progressGrades == null) 
                this.initializeProgressGrades();
            
            
            
            if(progressReportNumber == 0) return grade;
            else return this.progressGrades.get(progressReportNumber-1);
        }
        
        public void printGrades()
        {
            Debug.line("Reporting...");
            Debug.line("final grade = "+grade.letterValue());
            for(int i = 0; i < 3; i++)
                Debug.line((i+1)+"th progress grade = "+this.progressGrades.get(i).letterValue());
        }
        
        public void setProgressGrade(Grade progressGrade, int progressReportNumber)
        {
            if(progressGrades == null) this.initializeProgressGrades();
            
            if(progressReportNumber == 0) grade = progressGrade;
            else this.progressGrades.set(progressReportNumber-1, progressGrade);
        }
        
        public boolean hasProgressReport(int progressReportNumber)
        {
            if(progressGrades == null) this.initializeProgressGrades();
            //the last progressreport is the finalgrade
            if(progressReportNumber == 0 && grade.letterValue() != "NM") return true;
            //tests the progressreport
            else if(progressGrades.size() < progressReportNumber || progressGrades.get(progressReportNumber-1).floatValue() < 0) return false;
            
            return true;
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }
        
        public Grade getGrade() {
            return grade;
        }
        
        public boolean hasFinalGrade() {
            return grade.letterValue() != "NM";
        }

        public String getTitle() {
            return title;
        }

        public int compareTo(ClassInfo anotherClassInfo) {
            return title.compareTo(anotherClassInfo.getTitle());
        }

        @Override
        public String toString() {
            return title;
        }
}

/*Original
public class ClassInfo implements Comparable<ClassInfo>, java.io.Serializable
{
    private Semester semesterTaken;
    private String title;
    private ClassType subject;
    private Grade grade;
    
    public void print()
    {
        System.out.println(this.subject + ": " + this.title + " taken " + this.semesterTaken + " and Got a " + this.grade.floatValue());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        if (getClass() != obj.getClass())
            return false;
        return semesterTaken.equals(((ClassInfo)(obj)).semesterTaken) &&
                title.equals(((ClassInfo)(obj)).title) && 
                subject.equals(((ClassInfo)(obj)).subject) &&
                grade.equals(((ClassInfo)(obj)).grade);
    }
    
    public ClassInfo()
    {
        this.semesterTaken = null;
        this.title = new String();
        this.subject = null;
        this.grade = new Grade();
    }

    public ClassInfo(Semester semesterTaken, String title, ClassType subject, Grade grade) {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = grade;
    }
        
        

    public void setSemesterTaken(Semester semesterTaken) {
        this.semesterTaken = semesterTaken;
    }

    public void setSubject(ClassType subject) {
        this.subject = subject;
    }

    public void setTitle(String title) {
        this.title = title;
    }
        

        public Semester getSemesterTaken() {
            return semesterTaken;
        }

        public ClassType getSubject() {
            return subject;
        }

        public Grade getGrade() {
            return grade;
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }

        public String getTitle() {
            return title;
        }

        public int compareTo(ClassInfo anotherClassInfo) {
            return title.compareTo(anotherClassInfo.getTitle());
        }

        @Override
        public String toString() {
            return title;
        }
}*/
    
