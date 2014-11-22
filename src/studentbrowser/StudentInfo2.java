/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */


import java.util.*;
import javax.rmi.CORBA.Util;
import javax.swing.text.*;

/**
 *
 * @author GuestUser
 */
/*
enum Semester {FALL, SPRING, SUMMER};
enum StudentGrade { FRESHMAN, SOPHOMORE, JUNIOR, SENIOR };

enum ClassType 
{
    ENGLISH("ENGL"), MATH("MATH"), SCIENCE("SCI"), 
    HISTORY("HIST"), FOREIGN_LANGUAGE("FLANG"), OTHER("OTHR"), PLACEHOLDER("PLAC");
    
    private ClassType(String abrev) {
        abreviation = abrev;
    }

    public String getAbreviation() {
        return abreviation;
    }
    
    
    private String abreviation;
}
*/
         

    

public class StudentInfo2 implements Comparable<StudentInfo2>, java.io.Serializable
{
    private ClassArrayLists classes;
    private String name;
    private java.util.GregorianCalendar dateOfBirth;
    private StudentGrade studentGrade;
    private Document notes;
    private Gender gender;
    private CahseeStatus mathCahseeStatus;
    private CahseeStatus englishCahseeStatus;
    public static final long serialVersionUID = 6081859347916543664L;

    //private float gpa;
    
    public StudentInfo2(String name) {
        this.name = name;
    }
    
    public StudentInfo2(ClassArrayLists classes, String name, java.util.GregorianCalendar dob, StudentGrade grade, Gender gender) {
        this.classes = classes;
        this.name = name;
        this.dateOfBirth = dob;
        this.studentGrade = grade;
        this.notes = new PlainDocument();
        this.gender = gender;
        this.mathCahseeStatus = CahseeStatus.NOT_TAKEN;
        this.englishCahseeStatus = CahseeStatus.NOT_TAKEN;
        //generateGPA()   
    }
    
    public StudentInfo2(ClassArrayLists classes, String name, java.util.GregorianCalendar dob, StudentGrade grade) {
        this.classes = classes;
        this.name = name;
        this.dateOfBirth = dob;
        this.studentGrade = grade;
        this.notes = new PlainDocument();
        this.gender = Gender.MALE;
        this.mathCahseeStatus = CahseeStatus.NOT_TAKEN;
        this.englishCahseeStatus = CahseeStatus.NOT_TAKEN;
        //generateGPA()   
    }
    
    public StudentInfo2(String name, java.util.GregorianCalendar dob, StudentGrade grade) {
        this.classes = new ClassArrayLists(grade.ordinal()+1);
        this.classes.setGradeList(grade.ordinal()+9, new ClassList());
        this.name = name;
        this.dateOfBirth = dob;
        this.studentGrade = grade;
        this.notes = new PlainDocument();
        this.gender = Gender.MALE;
        this.mathCahseeStatus = CahseeStatus.NOT_TAKEN;
        this.englishCahseeStatus = CahseeStatus.NOT_TAKEN;
        //generateGPA()   
    }

    public StudentInfo2(ClassArrayLists classes, String name, java.util.GregorianCalendar dob, StudentGrade studentGrade, Document notes) {
        this.classes = classes;
        this.name = name;
        this.dateOfBirth = dob;
        this.studentGrade = studentGrade;
        this.notes = notes;
        this.gender = Gender.MALE;
        this.mathCahseeStatus = CahseeStatus.NOT_TAKEN;
        this.englishCahseeStatus = CahseeStatus.NOT_TAKEN;
    }
    
    
    
    public StudentInfo2(StudentInfo2 aStudentInfo) {
        this.classes = aStudentInfo.classes;
        this.dateOfBirth = aStudentInfo.dateOfBirth;
        this.studentGrade = aStudentInfo.studentGrade;
        this.name = aStudentInfo.name;
        this.notes = aStudentInfo.notes;
        this.gender = aStudentInfo.gender;
        this.mathCahseeStatus = aStudentInfo.mathCahseeStatus;
        this.englishCahseeStatus = aStudentInfo.englishCahseeStatus;
        //this.gpa = aStudentInfo.gpa;
    }
    public StudentInfo2(StudentInfo aOldStudentInfo)
    { setFromOldVerson(aOldStudentInfo); }
    
    public void setFromOldVerson(StudentInfo aOldStudentInfo)
    {
        this.classes = aOldStudentInfo.getClasses();
        this.studentGrade = aOldStudentInfo.getStudentGrade();
        this.name = aOldStudentInfo.getName();
        this.notes = aOldStudentInfo.getNotes();
        this.dateOfBirth = new java.util.GregorianCalendar(1990, 0, 1);
        this.gender = Gender.MALE;
        this.mathCahseeStatus = CahseeStatus.NOT_TAKEN;
        this.englishCahseeStatus = CahseeStatus.NOT_TAKEN;
    }
    
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
            return this.name.equals(((StudentInfo2)obj).getName());
        }
        if(obj.getClass() == String.class)
        {
            return this.name.equals(obj);
        }
        return super.equals(obj);
    }
    
    
    

    public java.util.GregorianCalendar getDOB() { 
        return this.dateOfBirth;
    }

    public void setDOB(java.util.GregorianCalendar dob) {
        this.dateOfBirth = dob;
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


    @Override
    public String toString() {
        return name;
    }

    /**
     * This method does not take into account any weight values for AP or Honors classes
     * It should be updated in the future
     */
    public Float getGPAFor(int grade, Semester semester, String weighed, ClassType classType)
    {
        float sum = 0;
        float number = 0;
        
        if(classes.hasGradeList(grade))
        {
            for(ClassInfo e : classes.getGradeList(grade).getAll(semester))
            {
                if(e.getSubject() != ClassType.PLACEHOLDER && e.getSubject() == classType)
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
    
    public Float getGPAFor(int grade, Semester semester, int progressReport)
    {
        float sum = 0;
        float number = 0;
        
        String weighed = "with weighed grades";
        
        if(classes.hasGradeList(grade))
        {
            for(ClassInfo e : classes.getGradeList(grade).getAll(semester, progressReport))
            {
                if(e.getSubject() != ClassType.PLACEHOLDER)
                {
                    Grade current = e.getProgressGrade(progressReport);
                    //Debug.line("current.letterValue():"+current.letterValue());
                    //Debug.line("current.floatValue():"+current.floatValue());
                    if(current.letterValue().equals("NM")) number--;
                    
                    if(weighed.toLowerCase() == "weigh" || weighed.toLowerCase() == "with weighed grades")
                        sum += current.floatValue();
                    else
                        sum += current.intValue();
                    number++;
                    
                }
            }
            //Debug.line("number:"+number);
            if(number <= 0) return new Float(-1.0F);
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
        
    }
    
    //Month/Day/Year
    public String getStringDateOfBirth()
    {
        return Integer.toString(dateOfBirth.get(java.util.Calendar.MONTH)+1)+"/"+
            Integer.toString(dateOfBirth.get(java.util.Calendar.DATE))+"/"+
            Integer.toString(dateOfBirth.get(java.util.Calendar.YEAR)).substring(2);
        //return java.text.DateFormat.getDateInstance().format(dateOfBirth.getTime());
    }

    public CahseeStatus getMathCahseeStatus() {
        return mathCahseeStatus;
    }

    public void setMathCahseeStatus(CahseeStatus cahseeStatus) {
        this.mathCahseeStatus = cahseeStatus;
    }

    public CahseeStatus getEnglishCahseeStatus() {
        return englishCahseeStatus;
    }

    public void setEnglishCahseeStatus(CahseeStatus englishCahseeStatus) {
        this.englishCahseeStatus = englishCahseeStatus;
    }
    
    

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    
    
    public int compareTo(StudentInfo2 o) {
        //Debug.line("Ran a compareTo for "+this.name+".compareTo(o."+o.getName()+"):"+this.name.compareTo(o.name));
        return this.name.compareTo(o.name);
    }
    
    public int compareTo(String name) {
        return this.name.compareTo(name);
    }


}
