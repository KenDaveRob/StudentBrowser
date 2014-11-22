/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;
import java.util.*;


    
/**
 *
 * @author GuestUser
 * 
 */


public class ClassInfo2 extends ClassInfo implements Comparable<ClassInfo>, java.io.Serializable 
{

    private Semester semesterTaken;
    private String title;
    private ClassType subject;
    private Grade grade;
    private java.util.ArrayList<Grade> progressGrades;

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
        return semesterTaken.equals(((ClassInfo2)(obj)).semesterTaken) &&
                title.equals(((ClassInfo2)(obj)).title) && 
                subject.equals(((ClassInfo2)(obj)).subject) &&
                grade.equals(((ClassInfo2)(obj)).grade);
    }
    
    public ClassInfo2()
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

    public ClassInfo2(Semester semesterTaken, String title, ClassType subject, Grade grade) {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = grade;
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
    public ClassInfo2(Semester semesterTaken, String title, ClassType subject, 
            Grade grade, ArrayList<Grade> progressGrades)
    {
        this.semesterTaken = semesterTaken;
        this.title = title;
        this.subject = subject;
        this.grade = grade;
        this.progressGrades = progressGrades;
        this.progressGrades.ensureCapacity(3);
    }
    
    public ClassInfo2(ClassInfo oldType)
    {
        this.grade = new Grade(oldType.getGrade());
        this.semesterTaken = oldType.getSemesterTaken();
        this.subject = oldType.getSubject();
        this.title = oldType.getTitle();
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
        

        public Semester getSemesterTaken() {
            return semesterTaken;
        }

        public ClassType getSubject() {
            return subject;
        }
        
        public Grade getProgressGrade(int progressReportNumber)
        {
            if(progressReportNumber == progressGrades.size()+1) return grade;
            else return this.progressGrades.get(progressReportNumber);
        }
        
        public void setProgressGrade(Grade progressGrade, int progressReportNumber)
        {
            if(progressReportNumber == progressGrades.size()+1) grade = progressGrade;
            else this.progressGrades.set(progressReportNumber, progressGrade);
        }
        
        public boolean hasProgressReport(int progressReportNumber)
        {
            //the last progressreport is the finalgrade
            if(progressReportNumber == progressGrades.size()+1 && grade.letterValue() != "NM") return true;
            //tests the progressreport
            else if(progressGrades.get(progressReportNumber).floatValue() < 0.0F) return false;
            
            return true;
        }
        
        public int getProgressGradeLength()
        { return progressGrades.size(); }

        
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

        public int compareTo(ClassInfo2 anotherClassInfo2) {
            return title.compareTo(anotherClassInfo2.getTitle());
        }

        @Override
        public String toString() {
            return title;
        }
}