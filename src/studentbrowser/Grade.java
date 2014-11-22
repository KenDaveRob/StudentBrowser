/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */

public class Grade extends Number
{
    public static final long serialVersionUID = -129717900268051697L;
            
    private float value;
    private String letterValue;


    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        return (value == ((Grade)(obj)).floatValue());
    }

    public Grade(String letterValue) throws Exception
    {
        this.setLetterValue(letterValue);
        //Debug.line("Ran Grade constructor(String "+letterValue+")");
        
        
    }
    
    public Grade(Grade grade)
    {
        this.value = grade.floatValue();
        this.letterValue = this.determineLetterValue();
    }
    
    

    public Grade(float value) {
        //Debug.line("Ran Grade constructor(float "+value+")");
        this.value = value;
        this.letterValue = determineLetterValue();
    }

    public Grade() {
        //Debug.line("Ran empty Grade constructor");
        this.value = 0.0F;
        this.letterValue = "NM";
    }
    
    public String letterValue()
    {
        if(letterValue == null) letterValue = this.determineLetterValue();
        return letterValue;
    }
            
    private String determineLetterValue() 
    {
        if(value == 0.0F) return new String("F");
        else if(value == 0.3F) return new String("F+");
        else if(value == 0.6F) return new String("D-");
        else if(value == 1.0F) return new String("D");
        else if(value == 1.3F) return new String("D+");
        else if(value == 1.6F) return new String("C-");
        else if(value == 2.0F) return new String("C");
        else if(value == 2.4F) return new String("C+");
        else if(value == 2.7F) return new String("B-");
        else if(value == 3.0F) return new String("B");
        else if(value == 3.4F) return new String("B+");
        else if(value == 3.7F) return new String("A-");
        else if(value == 4.0F) return new String("A");
        else if(value == 4.4F) return new String("A+");
        return new String("NM");
    }

    public void setValue(float value) {
        this.value = value;
        this.letterValue = this.determineLetterValue();
    }
    
    public void setLetterValue(String value) throws Exception
    {
        this.letterValue = value;
        //Debug.line("letterValue inside Grade constructor = "+letterValue);
        if(letterValue.equals("A+"))
            this.value = 4.4F;
        else if(letterValue.equals("A"))
            this.value = 4.0F;
        else if(letterValue.equals("A-"))
            this.value = 3.7F;
        else if(letterValue.equals("B+"))
            this.value = 3.4F;
        else if(letterValue.equals("B"))
            this.value = 3.0F;
        else if(letterValue.equals("B-"))
            this.value = 2.7F;
        else if(letterValue.equals("C+"))
            this.value = 2.4F;
        else if(letterValue.equals("C"))
            this.value = 2.0F;
        else if(letterValue.equals("C-"))
            this.value = 1.6F;
        else if(letterValue.equals("D+"))
            this.value = 1.3F;
        else if(letterValue.equals("D"))
            this.value = 1.0F;
        else if(letterValue.equals("D-"))
            this.value = 0.6F;
        else if(letterValue.equals("F+"))
            this.value = 0.3F;
        else if(letterValue.equals("F") || letterValue.equals("NM") || letterValue.equals("I"))
            this.value = 0.0F;
        
        else throw new Exception("Unknown Letter Grade");
        
    }
    
            
    @Override
    public int intValue() {
        return (int)value;
    }

    @Override
    public long longValue() {
        return (long)value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return (double)value;
    }
}


