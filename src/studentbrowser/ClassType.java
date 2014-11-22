/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */
public enum ClassType {
    ENGLISH("ENGL"), MATH("MATH"), SCIENCE("SCI"), 
    HISTORY("HIST"), FOREIGN_LANGUAGE("FLANG"), OTHER("OTHR"), PLACEHOLDER("PLAC");
    
    private ClassType(String abrev) {
        abreviation = abrev;
    }
    
    public static ClassType generateClassType(int ordinal) throws java.lang.IndexOutOfBoundsException
    {
        if(ordinal == 0) return ClassType.ENGLISH;
        
        else if(ordinal == 1) return ClassType.MATH;
        
        else if(ordinal == 2) return ClassType.SCIENCE;
        
        else if(ordinal == 3) return ClassType.HISTORY;
        
        else if(ordinal == 4) return ClassType.FOREIGN_LANGUAGE;
        
        else if(ordinal == 5) return ClassType.OTHER;
        
        else if(ordinal == 6) return ClassType.PLACEHOLDER;
             
        else throw new java.lang.IndexOutOfBoundsException("ClassType ordinal was out of bounds.");
           
    }

    public String getAbreviation() {
        return abreviation;
    }
    
    
    private String abreviation;
}
