/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */

public class ClassArrayLists implements Cloneable, java.io.Serializable
{
        public static final long serialVersionUID = -3923118739244035831L;

        private ClassList[] list;

        public int getSize() { return list.length; }
                

        public ClassArrayLists(ClassList[] list) {
            this.list = list;
        }


        public ClassArrayLists(int number) {
            list = new ClassList[number];
        }
        
        
        public ClassArrayLists() {
            list = new ClassList[4];
        }
        
        public void ensureGrade(int grade)
        {
            if(grade-8 > list.length)
            {
                int i;
                ClassList[] oldList = list;
                list = new ClassList[grade-8];
                for(i = 0; i < oldList.length; i++)
                    list[i] = oldList[i];
                
                for(i = oldList.length;i < list.length; i++)
                    list[i] = new ClassList();
                
            }
        }
        
        public boolean hasGradeList(int grade)
        {
            //Debug.line("Length:"+list.length);
            //Debug.line("grade-9:"+(grade-9));
            return (grade-9) < list.length;
        }
        
        public ClassList getGradeList(int grade)
        {
            return list[grade-9];
        }
        
        public ClassList getFromIndex(int index)
        {
            return list[index];
        }
        
        public void setGradeList(int grade, ClassList aList)
        {
            list[grade-9] = aList;
        }
        
        public void clear()
        {
            list = new ClassList[4];
        }
        
    }