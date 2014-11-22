/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

import java.util.*;
import java.util.regex.*;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author GuestUser
 */
public class StudentInformationTableModel extends javax.swing.table.AbstractTableModel
{
    private StudentInfo2 studentInformation;
    private StudentBrowserView studentView;
    private int columnCount;
    //NEED TO USE SORTEDARRAYLIST AND ADD A COMPARATOR FOR PAIR
    //Then fix the extrarows
    private ArrayList<IntegerLastTriple<Integer, Semester>> gradeAndSemesterList;//The extra integer stores what progressgrade level to display
    private int extraRows;
    private int extraGradeLevel; //Extra Book-keeping varible
    private final String[] gradeProgressCatagories = {"Final Grade", "1st Progress", "2nd Progress", "3rd Progress"};
    //public Pair<String[], Boolean> addSemesterStrings[];
    
    //The controler pointer is superfulous and should be removed
    private JTable controler;

    
    public StudentInformationTableModel(StudentInfo2 aStudentsInformation, int aColumnCount, StudentBrowserView studentView) 
    {
        //System.out.println("Problem?");
        extraRows = 0;
        extraGradeLevel = 9;
        this.studentInformation = aStudentsInformation;
        this.studentView = studentView;
        this.controler = studentView.getStudentInformationTable();
        gradeAndSemesterList = new SortedArrayList<IntegerLastTriple<Integer, Semester>>(new IntegerSemesterPairComparator());
        columnCount = aColumnCount;
        /*Prints studentInfo at start of model constructor
        System.out.println("Inside TableModel Constructor: ");
        studentInformation.printClassLists();*/
        
        //Correct forloop limit
        //((Enum)studentInformation.getStudentGrade()).ordinal()+1
        
        //System.out.println("StudentGradeOrdinal + 1:"+(((Enum)studentInformation.getStudentGrade()).ordinal()+1));

        //studentInformation.setStudentGrade(StudentGrade.FRESHMAN);
        //Debug.line("ROUND 2");
        //gradeAndSemesterList.add(new Pair<Integer,Semester>(new Integer(2),Semester.FALL));
        
        generateGradeAndSemesterList();
        //Debug.line("Ok after generation");
        //Debug.line("Row count from controler in constructor:"+controler.getRowCount());
    
            
   
    }
    

    public void generateGradeAndSemesterList()
    {
        /*
        //This is not the first time the table is loaded
        if(gradeAndSemesterList != null)
        {
            gradeAndSemesterList.size()
            
        }
         */
        
        int oldSize = gradeAndSemesterList.size() + this.extraRows;
        //gradeAndSemesterList = new ArrayList<Pair<Integer, Semester>>();
        for(int l = 0; l < ((Enum)studentInformation.getStudentGrade()).ordinal()+1;l++)
        {
            //ClassArrayLists t = new ClassArrayLists(4);
            //ClassList list = t.getGradeList(9);
            //studentInformation.getClasses().getFromIndex(0);
            //studentInformation.getClasses().getGradeList(l).size()
            //ClassArrayLists testList = studentInformation.getClasses();
            //System.out.println("Has taken classes in "+(l+9)+"th Grade:"+testList.hasGradeList(l+9));
            //studentInformation.getClasses().hasGradeList(l+9)
            if(studentInformation.getClasses().hasGradeList(l+9))
            {
                //Debug.line("Has Grade List passed");
                //Debug.line("testList.hasGradeList(l+9):"+testList.hasGradeList(l+9));
                if(studentInformation.getClasses().getGradeList(l+9).presentDuring(Semester.FALL))
                    gradeAndSemesterList.add(new IntegerLastTriple<Integer, Semester>
                            (new Integer(l+9), Semester.FALL));
                if(studentInformation.getClasses().getGradeList(l+9).presentDuring(Semester.SPRING))
                    gradeAndSemesterList.add(new IntegerLastTriple<Integer, Semester>
                            (new Integer(l+9), Semester.SPRING));
                if(studentInformation.getClasses().getGradeList(l+9).presentDuring(Semester.SUMMER))
                    gradeAndSemesterList.add(new IntegerLastTriple<Integer, Semester>
                            (new Integer(l+9), Semester.SUMMER));
        
            }
        }
        //Addition code
        if(oldSize < gradeAndSemesterList.size()+this.extraRows)
            this.fireTableRowsInserted(oldSize, gradeAndSemesterList.size());
        
        //Deleation code
        else if(oldSize > gradeAndSemesterList.size()+this.extraRows)
            this.fireTableRowsDeleted(oldSize, gradeAndSemesterList.size());
        
        
        /*
        Debug.line("gradeAndSemesterList size:"+gradeAndSemesterList.size());
        Debug.line("getRowCount:"+this.getRowCount());
        Debug.line("Tables column count:"+controler.getRowCount());
        */
    }
    public void setStudentInformation(StudentInfo2 studentInformation) {
       // int oldRowNumber = controler.getRowCount();
        //Debug.line("Row count from controler before new info:"+controler.getRowCount());
        this.studentInformation = studentInformation;
        this.extraRows = 0;
        this.extraGradeLevel = 9;
        gradeAndSemesterList = new SortedArrayList<IntegerLastTriple<Integer, Semester>>(new IntegerSemesterPairComparator());
        generateGradeAndSemesterList();
        //Debug.line("Row count from controler after new info:"+controler.getRowCount());
        //Add or subtract rows as needed
        //this.controler.addRowSelectionInterval(extraRows, extraRows);
        
    }
    

    public StudentInfo2 getStudentInformation() {
        return studentInformation;
    }
    
    //Returns the position of the greatest element in the gradeAndSemester list 
    //strictly less than the given grade/semester, or the last position if the 
    //given value is the greatest.
    public int getRowLower(int grade, Semester semester)
    {
        int i = 0;
        this.fireTableRowsInserted(gradeAndSemesterList.size()-1+extraRows, gradeAndSemesterList.size()-1+extraRows+1);
        
        for(Pair<Integer, Semester> e: gradeAndSemesterList)
        {
            if(e.type1.intValue() > grade) return i;
            
            if(e.type1.intValue() == grade && e.type2.ordinal() > semester.ordinal()) return i;
                    
            i++;
        }
        
        return i;
    }

    public int getExtraGradeLevel() {
        return extraGradeLevel;
    }
    
    
    
    public void insertRow(int index, int grade, Semester semester)
    {
        
        gradeAndSemesterList.add(index, new IntegerLastTriple<Integer,Semester>(new Integer(grade),semester));
        this.fireTableRowsInserted(index,index+1);
    }
    
    
    public boolean setExtraGrades(int grade)
    {
        //If information is input for a new grade must make sure it will be accepted
        studentInformation.getClasses().ensureGrade(grade);
        extraRows = 0;
        //Extra Book-keeping varible
        this.extraGradeLevel = grade;
        final int oldSize = gradeAndSemesterList.size()-1+extraRows;
        //If there the desired grade level is less than or  equal to the current grade level do nothing
        if(gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type1.intValue() > grade)
            return false;
        
        //If not all three of the possible semesters are filled
        if(gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type2.ordinal()< 2)
            extraRows += 2 - gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type2.ordinal();
        
        
        extraRows += 3*(grade-gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type1.intValue());
        
        
        this.fireTableRowsInserted(oldSize, gradeAndSemesterList.size()-1+extraRows);
        return true;
    }
    
    
    private boolean correctRowNumber()
    {
        //Debug.line("extra rows pre correction:"+extraRows);
        
        extraRows = 0;
        final int oldSize = gradeAndSemesterList.size()-1+extraRows;
        
        
        if(gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type2.ordinal()< 2)
            extraRows += 2 - gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type2.ordinal();
        
        extraRows += 3*(this.extraGradeLevel-gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type1.intValue());
        //Debug.line("extra rows post correction:"+extraRows);
        
        
        this.fireTableRowsDeleted(gradeAndSemesterList.size()-1+extraRows, oldSize);
        return true; 
    }
    
    public boolean removeExtraGrades()
    {
        if(this.extraGradeLevel == 9)
            return false;
        
        else if(gradeAndSemesterList.get(gradeAndSemesterList.size()-1).type1.intValue() < extraGradeLevel)
        {
            
        }   
        
        return false;
                    
    }
    
    public void setAllProgressBoxes(int index)
    {/*
        Debug.line("index: "+index);
        javax.swing.JComboBox specificProgressGradeCombo;
        for(int row = 0; row < this.getRowCount(); row++)
        {
            specificProgressGradeCombo = ((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)controler.getCellEditor(row, 1)).getComponent());
            Debug.line("Before: "+specificProgressGradeCombo.getSelectedItem().toString());
            specificProgressGradeCombo.setSelectedIndex(index);
            Debug.line("After: "+specificProgressGradeCombo.getSelectedItem().toString());
            controler.repaint();
        
        }
       */ 
        
                    javax.swing.JComboBox specificProgressGradeCombo;
                    for(int row = 0; row < this.getRowCount(); row++)
                    {
                        Debug.line("Ran LOOP row:"+row+", getRowCount:"+getRowCount());
                        specificProgressGradeCombo = ((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)controler.getCellEditor(row, 1)).getComponent());
                        Debug.line("Before: "+specificProgressGradeCombo.getSelectedItem().toString());
                        specificProgressGradeCombo.setSelectedIndex(index);
                        Debug.line("After: "+specificProgressGradeCombo.getSelectedItem().toString());
                        controler.selectAll();
                        specificProgressGradeCombo.grabFocus();
                        specificProgressGradeCombo.repaint();

                    }
                    
                    controler.repaint();
        
    }
    
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

        /* TESTS studentInformation at start of method
        System.out.println("Inside setValueAt Method: ");
        studentInformation.printClassLists();
        */
        
        if(columnIndex == 0) return;
        else if(columnIndex == 1) 
        {
            //if(aValue.getClass() == String.class)
            {
                //try
                {
                    
                    //NEED TO FILL EXTRABOXSOSIZE IS OK
                    //Debug.line("GradeANDSEMESTERLIST SIZE: "+gradeAndSemesterList.size());
                    //Debug.line("rowIndex: "+rowIndex);
                    /*
                    if(gradeAndSemesterList.size() == rowIndex)
                    {
                        gradeAndSemesterList.get(gradeAndSemesterList.size()-1)
                    }*/
                    //HERENOW!
                    //Integer intValue = (Integer)aValue;
                    //Debug.line("aValue: "+aValue+", index: "+((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)controler.getColumnModel().getColumn(1).getCellEditor()).getComponent()).getSelectedIndex());
                    gradeAndSemesterList.get(rowIndex).integer = ((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)controler.getColumnModel().getColumn(1).getCellEditor()).getComponent()).getSelectedIndex();
                    
                    /*
                    Debug.line("Col, Row: "+columnIndex+","+rowIndex);
                    javax.swing.JComboBox specificProgressGradeCombo = ((javax.swing.JComboBox)((javax.swing.DefaultCellEditor)controler.getCellEditor(rowIndex, columnIndex)).getComponent());
                    Debug.line("Item Name: "+specificProgressGradeCombo.getSelectedItem().toString());
                    */
                    
                    
                }
                /*catch(Exception E)
                {
                    System.out.println("Exception caused in StudentInformationTableModel.setValueAt.");
                    System.out.println("Exception message reads: "+E.getMessage());
                    E.printStackTrace();
                }*/
                
            }
            return;
            
        }
        final int gradeTaken;
        final Semester timeTaken;
        final ClassType subject;
        
        String[] gradeAndSemesterStrings = ((String)(this.getValueAt(rowIndex, 0))).split("th Grade ");
        gradeTaken = Integer.parseInt(gradeAndSemesterStrings[0]);
        timeTaken = Semester.valueOf(gradeAndSemesterStrings[1]);
        
        //If information is input for a new grade must make sure it will be accepted
        studentInformation.getClasses().ensureGrade(gradeTaken);
        
        
        //If information is input for a new grade the students grade must be increased
        if(gradeTaken > (studentInformation.getStudentGrade().ordinal()+9) ||
                (gradeTaken ==(studentInformation.getStudentGrade().ordinal()+9) &&
                timeTaken.ordinal() > studentInformation.getClasses().getGradeList(gradeTaken).greatestSemester().ordinal()))
        {
          
            
            StudentGrade newStudentGrade = studentInformation.getStudentGrade();
            if(gradeTaken == 9) newStudentGrade = StudentGrade.FRESHMAN;
            if(gradeTaken == 10) newStudentGrade = StudentGrade.SOPHOMORE;
            else if(gradeTaken == 11) newStudentGrade = StudentGrade.JUNIOR;
            else if(gradeTaken == 12)newStudentGrade = StudentGrade.SENIOR;
            else Debug.line("ERROR incorrect gradeTaken in setValue TableModel :"+gradeTaken);
            
            studentInformation.setStudentGrade(newStudentGrade);
            //Debug.line("LAUNCHED!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        
            
            this.gradeAndSemesterList.add(new IntegerLastTriple<Integer,Semester>(new Integer(gradeTaken),timeTaken));
            this.correctRowNumber();
        }
        
        
 
        
        //removeAll(gradeTaken,semesterTaken, ClassType);
        if(columnIndex == 2)
            subject = ClassType.ENGLISH;
        else if(columnIndex == 3)
            subject = ClassType.MATH;
        else if(columnIndex == 4)
            subject = ClassType.SCIENCE;
        else if(columnIndex == 5)
            subject = ClassType.HISTORY;
        else if(columnIndex == 6)
            subject = ClassType.FOREIGN_LANGUAGE;
        else if(columnIndex == 7)
            subject = ClassType.OTHER;
        else
        {
            subject = ClassType.OTHER;
            System.out.println("ERROR: Invalid ClassType Remove Operation.");
        }
        
        
        
        //FORMAT:"Title1(A", "Title2(A"
        java.util.regex.Pattern perenSpace = java.util.regex.Pattern.compile("\\) ");
        java.util.regex.Pattern peren = java.util.regex.Pattern.compile("\\(");
        String[] newClassesInformationStrings = perenSpace.split(((String)aValue));
        
        //HERE!!!!!!!!!!MUST FIND SUBSTATUTE THAT SAVES THE CURRENT CLASSES IN TEXT AND DELEATES ALL ELSE
        //studentInformation.removeAll(gradeTaken, timeTaken, subject);
        ClassList newClasses = new ClassList();
        for(int i = 0; i < newClassesInformationStrings.length; i++)
        {
            String[] newClassInformationStrings = peren.split(newClassesInformationStrings[i]);
             
            try
            {
                //if class is found add the grade info to the correct location
                //if class isnt found create the class with the grade info in the correct location
                ClassInfo currentClass = studentInformation.findClass(gradeTaken, timeTaken, newClassInformationStrings[0]);
                if(currentClass == null)
                {
                    currentClass = new ClassInfo(timeTaken, newClassInformationStrings[0], 
                        subject);
                    
                }
                
                
                currentClass.setProgressGrade(new Grade((Pattern.compile("\\)")).matcher(newClassInformationStrings[1]).replaceFirst("")) ,gradeAndSemesterList.get(rowIndex).integer);
                //Debug.line(currentClass.getTitle()+" "+gradeAndSemesterList.get(rowIndex).integer+"th grade: "+(new Grade((Pattern.compile("\\)")).matcher(newClassInformationStrings[1]).replaceFirst(""))).letterValue());
                newClasses.add(currentClass);
                
                /*
                studentInformation.addClass((new ClassInfo(timeTaken, newClassInformationStrings[0], 
                        subject, new Grade((Pattern.compile("\\)")).matcher(newClassInformationStrings[1]).replaceFirst("")))),gradeTaken);
                */
                
                
                
                /*
                Grade testG = new Grade((Pattern.compile("\\)")).matcher(newClassInformationStrings[1]).replaceFirst(""));
                Debug.line("testGradelettervalue = "+testG.letterValue());
                System.out.println("NAME:"+newClassInformationStrings[0]+
                ",LETTERGRADE:"+Pattern.compile("\\)").matcher(newClassInformationStrings[1]).replaceFirst("")+
                ",GRADETAKEN:"+gradeTaken+",SEMESTERTAKEN:"+(timeTaken));
                String letterGrade = Pattern.compile("\\)").matcher(newClassInformationStrings[1]).replaceFirst("");
                Debug.line("Reprint letterGrade var: "+letterGrade);
                if(letterGrade.equals("A+")) Debug.line("A+ replaced correctly");
                */
            }
            catch (Exception e)
            {
                Debug.line("Error: In, StudentInformationTableModel.setValueAt; Type, regular expression.");
                //Debug.line("newClassInformationStrings[0]:"+newClassInformationStrings[0]);
                //Debug.line("newClassInformationStrings[1]:"+newClassInformationStrings[1]);
                e.printStackTrace();
            }
            //Semester semesterTaken, String title, ClassType subject, Grade grade
        }
        studentInformation.removeAll(gradeTaken, timeTaken, subject);
        for(ClassInfo c : newClasses)
        {
            //Debug.line("Title: "+c.getTitle());
            //c.printGrades();
            //for(int i = 0; i < 4; i++)
            //    Debug.line(i+"th Grade"+c.getProgressGrade(0).letterValue());
            studentInformation.addClass(c, gradeTaken); 
        }
        
        
        
        //THIS PROBABLY ISNT NEEDED AND SHOULD BE CHECKED FOR CHANGED GRID
        //this.controler.repaint();
        //Tests studentInfo after method
        //System.out.println("After setValueAt Method");
        //studentInformation.printClassLists();
        
        //Controler doesnt need to repaint
        //this.controler.repaint();
        this.studentView.generateAddSemestersMenuItems();
        this.studentView.resetListGPAAverage();
        this.fireTableCellUpdated(rowIndex, columnIndex);
        
    }


    

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }

    public void setExtraRows(int extraRows) {
        this.extraRows = extraRows;
    }
    
    
    
    
    public int getRowCount() {
        //int rowCount = 0;
        /*for(int l = 0; l < ((Enum)studentInformation.getStudentGrade()).ordinal()+1;l++)
        {
            //ClassArrayLists t = new ClassArrayLists(4);
            //ClassList list = t.getGradeList(9);
            
            //studentInformation.getClasses().getGradeList(l).size()
            if(studentInformation.getClasses().hasGradeList(l+9))
            {
                if(studentInformation.getClasses().getGradeList(l+9).presentDuring(Semester.SPRING))
                    rowCount++;
                if(studentInformation.getClasses().getGradeList(l+9).presentDuring(Semester.FALL))
                    rowCount++;
                if(studentInformation.getClasses().getGradeList(l+9).presentDuring(Semester.SUMMER))
                    rowCount++;
            }
        }
        //System.out.println(Integer.toString(rowCount)+"ROWCOUNT");
        
        return rowCount+extraRows;
         */
        return gradeAndSemesterList.size() +extraRows;
        //return gradeAndSemesterList.size();CODE

        //return 5;
      
    }

    public int getColumnCount() {
        //return columnCount;CODE

        return columnCount;
    }
    


    public Object getValueAt(int rowIndex, int columnIndex) {
        //System.out.println(new String(gradeAndSemesterList.get(rowIndex).type1.toString()+"th Grade "+gradeAndSemesterList.get(rowIndex).type2));
        
        //Debug.line("GetValueAt, rowIndex="+rowIndex+", columnIndex="+columnIndex);
        //if(rowIndex > 1) return "extraRow";
        
        //if(rowIndex>1) Debug.line("RowIndex(>1):"+rowIndex);
        //Debug.line("getValueAt run");
        
        
        if(gradeAndSemesterList.size()-1 < rowIndex)
        {
            if(columnIndex == 0)
            {
                                
                //#th Grade Semester
                StringBuilder cell = new StringBuilder();
                
                final int lastGrade;
                final Semester lastSemester;
                
                String[] gradeAndSemesterStrings = ((String)getValueAt(rowIndex-1,columnIndex)).split("th Grade ");
                lastGrade = Integer.parseInt(gradeAndSemesterStrings[0]);

                lastSemester = Semester.valueOf(gradeAndSemesterStrings[1].toUpperCase());
                
                if(lastSemester == Semester.FALL) 
                    return new String(lastGrade+"th Grade "+Semester.SPRING.toString()/*+" "+progressGradeCombo.getSelectedItem()*/);
                
                else if(lastSemester == Semester.SPRING)
                    return new String(lastGrade+"th Grade "+Semester.SUMMER.toString()/*+" "+progressGradeCombo.getSelectedItem()*/);
                
                else
                    return new String((lastGrade+1)+"th Grade "+Semester.FALL.toString()/*+" "+progressGradeCombo.getSelectedItem()*/);
            }
            
            Debug.line("Error: in StudentInformationTableModel.getValueAt method;");
            Debug.line("GradeAndSemesterList is smaller than the number of rows on table.");
            
            return "";
        }
        
        if(columnIndex == 0)
        {
            //Debug.line("rowIndex:"+rowIndex);
            //Debug.line("Column0String:"+gradeAndSemesterList.get(rowIndex).type1.toString()+"th Grade "+gradeAndSemesterList.get(rowIndex).type2);
            return new String(gradeAndSemesterList.get(rowIndex).type1.toString()+"th Grade "+gradeAndSemesterList.get(rowIndex).type2);             
        
        }
        else
        {
            java.lang.StringBuilder cell = new java.lang.StringBuilder();
            if(columnIndex == 1)
            {
                return gradeProgressCatagories[this.gradeAndSemesterList.get(rowIndex).integer];
            }
            if(columnIndex == 2)
            {

                for(ClassInfo e : studentInformation.getClasses().
                        getGradeList(gradeAndSemesterList.get(rowIndex).type1).
                        getAll(gradeAndSemesterList.get(rowIndex).type2, ClassType.ENGLISH))
                {
                    //e.getProgressGrade(gradeAndSemesterList.get(rowIndex).integer).letterValue()
                    
                    //cell.append(e.getTitle()+"("+e.getGrade().letterValue()+") "); 
                    cell.append(e.getTitle()+"("+e.getProgressGrade(gradeAndSemesterList.get(rowIndex).integer).
                            letterValue()+") "); 
                }
                return cell;
            }

            else if(columnIndex == 3)
            { 
                //studentInformation.getClasses().getFromIndex(rowIndex).getAll(timePeriod, subject)

                //studentInformation.getClasses().getGradeList(dddddd).getAll(gradeAndSemesterList.get(rowIndex).type1.intValue(), gradeAndSemesterList.get(rowIndex).type2);

                //studentInformation.getClasses().getGradeList(rowIndex)
                //System.out.println("Inside Math Column getValueAt(), semester:"+gradeAndSemesterList.get(rowIndex).type2);
                for(ClassInfo e : studentInformation.getClasses().
                        getGradeList(gradeAndSemesterList.get(rowIndex).type1).
                        getAll(gradeAndSemesterList.get(rowIndex).type2, ClassType.MATH))
                {
                    
                    //HERE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //TRY CHANGING ClassInfo to ClassInfo2 and see if it can access the new functions and varibles
                    
                    //Debug.line("e.getFinalGrade().letterValue(): "+e.getFinalGrade().letterValue());
                    //Debug.line("gradeAndSemesterList(progressGrade level): "+gradeAndSemesterList.get(rowIndex).integer);
                    cell.append(e.getTitle()+"("+e.getProgressGrade(gradeAndSemesterList.get(rowIndex).integer).
                            letterValue()+") "); 
                    //cell.append(e.getTitle()+"("+e.getGrade().letterValue()+") "); 
                }
                return cell;
            }

            else if(columnIndex == 4)
            { 
                for(ClassInfo e : studentInformation.getClasses().
                        getGradeList(gradeAndSemesterList.get(rowIndex).type1).
                        getAll(gradeAndSemesterList.get(rowIndex).type2, ClassType.SCIENCE))
                {
                    //cell.append(e.getTitle()+"("+e.getGrade().letterValue()+") ");
                    cell.append(e.getTitle()+"("+e.getProgressGrade(gradeAndSemesterList.get(rowIndex).integer).
                            letterValue()+") "); 
                }
                return cell;
            }

            else if(columnIndex == 5)
            {
                for(ClassInfo e : studentInformation.getClasses().
                        getGradeList(gradeAndSemesterList.get(rowIndex).type1).
                        getAll(gradeAndSemesterList.get(rowIndex).type2, ClassType.HISTORY))
                {
                    //cell.append(e.getTitle()+"("+e.getGrade().letterValue()+") ");
                    cell.append(e.getTitle()+"("+e.getProgressGrade(gradeAndSemesterList.get(rowIndex).integer).
                            letterValue()+") "); 
                }
                return cell;
            }

            else if(columnIndex == 6)
            {
                for(ClassInfo e : studentInformation.getClasses().
                        getGradeList(gradeAndSemesterList.get(rowIndex).type1).
                        getAll(gradeAndSemesterList.get(rowIndex).type2, ClassType.FOREIGN_LANGUAGE))
                {
                    //cell.append(e.getTitle()+"("+e.getGrade().letterValue()+") ");
                    cell.append(e.getTitle()+"("+e.getProgressGrade(gradeAndSemesterList.get(rowIndex).integer).
                            letterValue()+") "); 
                }
                return cell;
            }

            else if(columnIndex == 7)
            {
                for(ClassInfo e : studentInformation.getClasses().
                        getGradeList(gradeAndSemesterList.get(rowIndex).type1).
                        getAll(gradeAndSemesterList.get(rowIndex).type2, ClassType.OTHER))
                {
                    //cell.append(e.getTitle()+"("+e.getGrade().letterValue()+") ");
                    cell.append(e.getTitle()+"("+e.getProgressGrade(gradeAndSemesterList.get(rowIndex).integer).
                            letterValue()+") "); 
                }
                return cell;
            }

            else return "ERROR";
        }
        //CODE ABOVE
        //return "TEST";
        
    }

    @Override
    public String getColumnName(int column) {
        if(column == 0)
            return "Semester";
        else if(column == 1)
            return "Grade Period";
        else if(column == 2)
            return "English";
        else if(column == 3)
            return "Math";
        else if(column == 4)
            return "Science";
        else if(column == 5)
            return "History";
        else if(column == 6)
            return "Foreign Language";
        else if(column == 7)
            return "Other";
        else
            return "ERROR";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex > 0);
    }
    
    private class IntegerSemesterPairComparator implements Comparator<IntegerLastTriple<Integer,Semester>>
    {

        public int compare(IntegerLastTriple<Integer, Semester> a, IntegerLastTriple<Integer, Semester> b) {
            //First argu less than negative
            //First argu equal than 0
            //First argu greater than postive
            if(!a.type1.equals(b.type1)) return a.type1.intValue()-b.type1.intValue();
            
            return a.type2.ordinal()-b.type2.ordinal();
        }
    }
    
    
    //Table constructor argument
    //new StudentInformationTableModel(studentsInformation.first(),7,studentInformationTable)

}
/*
ENGLISH("ENGL"), MATH("MATH"), SCIENCE("SCI"), 
    HISTORY("HIST"), FOREIGN_LANGUAGE("FLANG"), OTHER("OTHR");*/