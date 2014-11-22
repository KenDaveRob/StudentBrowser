/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;


import java.util.*;
import javax.rmi.CORBA.Util;
import javax.swing.text.*;

/**
 *
 * @author GuestUser
 */

public class StudentInfo implements Comparable<StudentInfo>, java.io.Serializable
{
    private ClassArrayLists classes;
    private String name;
    private int age;
    private StudentGrade studentGrade;
    private Document notes;
    public static final long serialVersionUID = 3643967497259528484L;

    //private float gpa;
    public Pair<java.lang.Integer, Semester> getSemesterANumberBefore(int ago, boolean includeSummer)
    {
        Pair<java.lang.Integer, Semester> position = getLatestSemester(includeSummer);
        for(int index = 0; index < ago; index++)
        {
            if(position.type1.intValue() == -1) break;
            position = getSemesterBefore(position.type1.intValue(), position.type2, includeSummer);
        }
        return position;
    }
    public Pair<java.lang.Integer, Semester> getSemesterBefore(int grade, Semester recentSemester, boolean includeSummer)
    {
        String semesterNames[] = {"FALL", "SPRING", "SUMMER"};
        
        for(int g = grade; g >= 9; g--)
        {          
            //Start looking one behind the current latest semester if on the latest grade
            if(g == grade)
            {
                for(int s = recentSemester.ordinal()-1; s >= 0; s--)
                {
                    if(includeSummer || s != 2)
                    {
                        if(classes.getGradeList(g).presentDuring(Semester.valueOf(semesterNames[s]), false))
                            return new Pair<java.lang.Integer,Semester>(g,Semester.valueOf(semesterNames[s]));
                    }
                }
            }
            //Otherwise scan semester from top to bottom
            else
            {
                int max = 2;
                if(!includeSummer) max = 1;
                for(int s = max; s >= 0; s--)
                {
                    if(classes.getGradeList(g).presentDuring(Semester.valueOf(semesterNames[s]), false))
                        return new Pair<java.lang.Integer,Semester>(g,Semester.valueOf(semesterNames[s]));                    
                }
            }
            
    
        }
        
        return new Pair<java.lang.Integer, Semester>(-1, Semester.FALL);
        
    }  
    public Pair<java.lang.Integer, Semester> getLatestSemester(boolean includeSummer)
    {
        String semesterNames[] = {"FALL", "SPRING", "SUMMER"};
        
        for(int g = this.studentGrade.ordinal()+9; g >= 9; g--)
        {
            int max = 2;
            if(!includeSummer) max = 1;
            for(int s = max; s >= 0; s--)
            {
                if(classes.getGradeList(g).presentDuring(Semester.valueOf(semesterNames[s]),false))
                    return new Pair<java.lang.Integer,Semester>(g,Semester.valueOf(semesterNames[s]));
            }
    
        }
        
        return new Pair<java.lang.Integer, Semester>(-1, Semester.FALL);
    }
    
    public StudentInfo(String name) {
        this.name = name;
    }
    
    public StudentInfo(ClassArrayLists classes, String name, int age, StudentGrade grade) {
        this.classes = classes;
        this.name = name;
        this.age = age;
        this.studentGrade = grade;
        this.notes = new PlainDocument();
        //generateGPA()   
    }
    
    public StudentInfo(String name, int age, StudentGrade grade) {
        this.classes = new ClassArrayLists(grade.ordinal()+1);
        this.classes.setGradeList(age, new ClassList());
        this.name = name;
        this.age = age;
        this.studentGrade = grade;
        this.notes = new PlainDocument();
        //generateGPA()   
    }

    public StudentInfo(ClassArrayLists classes, String name, int age, StudentGrade studentGrade, Document notes) {
        this.classes = classes;
        this.name = name;
        this.age = age;
        this.studentGrade = studentGrade;
        this.notes = notes;
    }
    
    
    
    public StudentInfo(StudentInfo aStudentInfo) {
        this.classes = aStudentInfo.classes;
        this.age = aStudentInfo.age;
        this.studentGrade = aStudentInfo.studentGrade;
        this.name = aStudentInfo.name;
        this.notes = aStudentInfo.notes;
        //this.gpa = aStudentInfo.gpa;
    }
    
    public void printClassLists()
    {
        for(int i = 0; i < classes.getSize(); i++)
        {
            System.out.println(i+9+"th Grade");
            if(classes.getFromIndex(i) != null)
            {
                for(ClassInfo e : classes.getFromIndex(i))
                    e.print();
            }
        }
    }
    
    public boolean addClass(ClassInfo classAdded, int gradeTaken)
    {
        return classes.getGradeList(gradeTaken).add(classAdded);
    }
    
    public void removeAll(int gradeTaken, Semester semesterTaken, ClassType type)
    {
        classes.getGradeList(gradeTaken).removeAll(semesterTaken, type);
    }
    
    public ClassInfo findClass(int gradeTaken, Semester semesterTaken, String className)
    {
        return classes.getGradeList(gradeTaken).find(semesterTaken, className);
    }
    
    public boolean removeClass(ClassInfo classRemoved, int yearTaken)
    {
        return classes.getGradeList(yearTaken).remove(classRemoved);
    }

    public Document getNotes() {
        return notes;
    }

    public void setNotes(Document notes) {
        this.notes = notes;
    }
    
    public void generateClassPlaceholder(int grade, Semester semester)
    {
        //Semester semesterTaken, String title, ClassType subject, Grade grade
        this.classes.ensureGrade(grade);
        this.classes.getGradeList(grade).add(new ClassInfo(semester,"PLACEHOLDER",ClassType.PLACEHOLDER,new Grade(0.0F)));
        
    }
    
    public void deleateAllClassPlaceHolder()
    {
        //Debug.line("delAllPlaceHolderStart");
        for(int i = 0; i < this.classes.getSize(); i++)
        {
            this.classes.getFromIndex(i).removeAll(Semester.FALL, ClassType.PLACEHOLDER);
            this.classes.getFromIndex(i).removeAll(Semester.SPRING, ClassType.PLACEHOLDER);
            this.classes.getFromIndex(i).removeAll(Semester.SUMMER, ClassType.PLACEHOLDER);
        }
        //Debug.line("delAllPlaceHolderFinal");
    }
    
    public boolean onlyHasClassPlaceHolders()
    {
        for(int i = 0; i < this.classes.getSize(); i++)
        {
            if(this.classes.getFromIndex(i).onlyHasPlaceHolders() == false)
                return false;
        }
        
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == this.getClass())
        {
            return this.name.equals(((StudentInfo)obj).getName());
        }
        if(obj.getClass() == String.class)
        {
            return this.name.equals(obj);
        }
        return super.equals(obj);
    }
    
    
    

    public int getAge() { 
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ClassArrayLists getClasses() {
        return classes;
    }

    public void setClasses(ClassArrayLists classes) {
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentGrade getStudentGrade() {
        return studentGrade;
    }

    public void setStudentGrade(StudentGrade studentGrade) {
        this.studentGrade = studentGrade;
    }

    /*StudentInfo(ClassArrayLists student1ClassArrayLists, String string, int i, StudentGrade FRESHMAN) {
        throw new UnsupportedOperationException("Not yet implemented");
    }*/

    @Override
    public String toString() {
        return name;
    }

    
    
    
    /**
     * This method does not take into account any weight values for AP or Honors classes
     * It should be updated in the future
     */
    public Float getGPAFor(int grade, Semester semester, String weighed)
    {
        float sum = 0;
        float number = 0;
        
        if(classes.hasGradeList(grade))
        {
            for(ClassInfo e : classes.getGradeList(grade).getAll(semester))
            {
                if(e.getSubject() != ClassType.PLACEHOLDER)
                {
                    if(weighed.toLowerCase() == "weigh" || weighed.toLowerCase() == "with weighed grades")
                        sum += e.getGrade().floatValue();
                    else
                        sum += e.getGrade().intValue();
                    number++;
                }
            }
            return new Float(sum/number);
        }
        
        return new Float(0.0F);
    }
    
    public Float getGPAFor(int grade, Semester semester)
        { return getGPAFor(grade, semester, "with weighed grades"); } 
    
    public Float getGPA(String weighed)
    {
       
        float gradeSum = 0;
        float gradeNumber = 0;
        
        float semesterSum = 0;
        float semesterNumber = 0;
        
        for(int gradeIndex = 9; gradeIndex < classes.getSize()+9; gradeIndex++)
        {
            semesterSum = 0;
            semesterNumber = 0;
  
            for(Semester semester : Semester.values())
            {
                //this.getGPAFor(gradeIndex, semester)
                
                if(classes.getGradeList(gradeIndex).presentDuring(semester))
                {
                    semesterSum += this.getGPAFor(gradeIndex, semester, weighed);
                    semesterNumber++;
 
                }
                  
            }
            gradeSum += semesterSum;
            gradeNumber += semesterNumber;  
        }
        return new Float(gradeSum/gradeNumber);
        
        /*for(int gradeIndex = 9; gradeIndex < classes.getSize()+9; gradeIndex++)
        {
            float sum = 0;
            float number = 0;
            
            ClassList t = this.classes.getGradeList(9);
            for(Semester semester : Semester.values())
            {
                for(ClassInfo e : classes.getGradeList(gradeIndex).getAll(semester))
                {
                    sum += e.getFinalGrade().floatValue();
                    number++;
                }
            }
            return new Float(sum/number);
        }
        
        return new Float(Float.NaN);*/
    }
    
    
    
    public int compareTo(StudentInfo o) {
        //Debug.line("Ran a compareTo for "+this.name+".compareTo(o."+o.getName()+"):"+this.name.compareTo(o.name));
        return this.name.compareTo(o.name);
    }
    
    public int compareTo(String name) {
        return this.name.compareTo(name);
    }

    /*public StudentInfo(ClassArrayLists classes, String name, int age, StudentGrade grade, float gpa) {
        this.classes = classes;
        this.name = name;
        this.age = age;
        this.studentGrade = grade;
        this.gpa = gpa;
    }
    

    
    private void generateGPA()
    {
        float sum = 0;
        float number = 0;
        for(int i = 0; i < classes.getSize(); i++) 
        {
            for(ClassInfo e : classes.getFromIndex(i)) 
            {
                sum += e.getFinalGrade().floatValue(); 
                number++;
            }
        }
        gpa = sum/number;
    }*/
}
