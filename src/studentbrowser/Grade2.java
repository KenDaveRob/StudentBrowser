/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package studentbrowser;

/**
 *
 * @author GuestUser
 */

public class Grade2 extends Grade
{
            
    private float floatValue;
    private String letterValue;


    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        return (floatValue == ((Grade2)(obj)).floatValue());
    }

    public Grade2(String letterValue) throws Exception
    {
        this.setLetterValue(letterValue);
        Debug.line("Ran Grade2 constructor(String "+letterValue+")");
        /*
        this.letterValue = letterValue;
        //Debug.line("letterValue inside Grade2 constructor = "+letterValue);
        if(letterValue.equals("A+"))
            this.floatValue = 4.4F;
        else if(letterValue.equals("A"))
            this.floatValue = 4.0F;
        else if(letterValue.equals("A-"))
            this.floatValue = 3.7F;
        else if(letterValue.equals("B+"))
            this.floatValue = 3.4F;
        else if(letterValue.equals("B"))
            this.floatValue = 3.0F;
        else if(letterValue.equals("B-"))
            this.floatValue = 2.7F;
        else if(letterValue.equals("C+"))
            this.floatValue = 2.4F;
        else if(letterValue.equals("C"))
            this.floatValue = 2.0F;
        else if(letterValue.equals("C-"))
            this.floatValue = 1.6F;
        else if(letterValue.equals("D+"))
            this.floatValue = 1.3F;
        else if(letterValue.equals("D"))
            this.floatValue = 1.0F;
        else if(letterValue.equals("D-"))
            this.floatValue = 0.6F;
        else if(letterValue.equals("F+"))
            this.floatValue = 0.3F;
        else if(letterValue.equals("F") || letterValue.equals("NM") || letterValue.equals("I"))
            this.floatValue = 0.0F;
        
        else throw new Exception("Unknown Letter Grade2");*/
    }
    
    public Grade2(Grade grade)
    {
        this.floatValue = grade.floatValue();
        this.letterValue = this.determineLetterValue();
    }
    
    

    public Grade2(float value) {
        Debug.line("Ran Grade2 constructor(float "+value+")");
        this.floatValue = value;
        this.letterValue = determineLetterValue();
    }

    public Grade2() {
        Debug.line("Ran empty Grade2 constructor");
        this.floatValue = 0.0F;
        this.letterValue = "NM";
    }
    
    public String letterValue()
    {
        return letterValue;
    }
            
    private String determineLetterValue() 
    {/*
        if(floatValue == 0.0F) return "F";
        else if(floatValue == 0.3F) return "F+";
        else if(floatValue == 0.6F) return "D-";
        else if(floatValue == 1.0F) return "D";
        else if(floatValue == 1.3F) return "D+";
        else if(floatValue == 1.6F) return "C-";
        else if(floatValue == 2.0F) return "C";
        else if(floatValue == 2.4F) return "C+";
        else if(floatValue == 2.7F) return "B-";
        else if(floatValue == 3.0F) return "B";
        else if(floatValue == 3.4F) return "B+";
        else if(floatValue == 3.7F) return "A-";
        else if(floatValue == 4.0F) return "A";
        else if(floatValue == 4.4F) return "A+";
        return "NM";*/
        if(floatValue == 0.0F) return new String("F");
        else if(floatValue == 0.3F) return new String("F+");
        else if(floatValue == 0.6F) return new String("D-");
        else if(floatValue == 1.0F) return new String("D");
        else if(floatValue == 1.3F) return new String("D+");
        else if(floatValue == 1.6F) return new String("C-");
        else if(floatValue == 2.0F) return new String("C");
        else if(floatValue == 2.4F) return new String("C+");
        else if(floatValue == 2.7F) return new String("B-");
        else if(floatValue == 3.0F) return new String("B");
        else if(floatValue == 3.4F) return new String("B+");
        else if(floatValue == 3.7F) return new String("A-");
        else if(floatValue == 4.0F) return new String("A");
        else if(floatValue == 4.4F) return new String("A+");
        return new String("NM");
    }

    public void setValue(float value) {
        this.floatValue = value;
        this.letterValue = this.determineLetterValue();
    }
    
    public void setLetterValue(String value) throws Exception
    {
        this.letterValue = value;
        //Debug.line("letterValue inside Grade2 constructor = "+letterValue);
        if(letterValue.equals("A+"))
            this.floatValue = 4.4F;
        else if(letterValue.equals("A"))
            this.floatValue = 4.0F;
        else if(letterValue.equals("A-"))
            this.floatValue = 3.7F;
        else if(letterValue.equals("B+"))
            this.floatValue = 3.4F;
        else if(letterValue.equals("B"))
            this.floatValue = 3.0F;
        else if(letterValue.equals("B-"))
            this.floatValue = 2.7F;
        else if(letterValue.equals("C+"))
            this.floatValue = 2.4F;
        else if(letterValue.equals("C"))
            this.floatValue = 2.0F;
        else if(letterValue.equals("C-"))
            this.floatValue = 1.6F;
        else if(letterValue.equals("D+"))
            this.floatValue = 1.3F;
        else if(letterValue.equals("D"))
            this.floatValue = 1.0F;
        else if(letterValue.equals("D-"))
            this.floatValue = 0.6F;
        else if(letterValue.equals("F+"))
            this.floatValue = 0.3F;
        else if(letterValue.equals("F") || letterValue.equals("NM") || letterValue.equals("I"))
            this.floatValue = 0.0F;
        
        else throw new Exception("Unknown Letter Grade2");
        
    }
    
            
    @Override
    public int intValue() {
        return (int)floatValue;
    }

    @Override
    public long longValue() {
        return (long)floatValue;
    }

    @Override
    public float floatValue() {
        return floatValue;
    }

    @Override
    public double doubleValue() {
        return (double)floatValue;
    }
}
            
