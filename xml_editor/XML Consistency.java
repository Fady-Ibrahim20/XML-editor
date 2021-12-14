import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Project2 
{
    public static String listtostring(ArrayList<String> lis) // Converting ArrayList to String 
    {
        StringBuffer a = new StringBuffer() ;
        for (int i =0  ; i< lis.size() ; i++)
        {
          a.append(lis.get(i)) ;
        }
        return a.toString() ;
    }
     public static void print(ArrayList<String> fixError) //Printing the XML Code after Correction
    {    
        for (int i = 0; i < fixError.size(); i ++)
        {
           System.out.println( fixError.get(i));
        }
    }
    public static int find_char(String line, char x)
    {
        return line.indexOf(x) ; // returns the index of found char or -1 if not found
    }
    public static void check_error(ArrayList<String> line, ArrayList<Integer> lineOfError,ArrayList<String> fixError)
    {
        Stack <String> s = new Stack<>() ;
        int openBracket, endBracket ;
        
        String currentLine ;
        String correct ;
        String value_Stack ;
        for(int i = 0; i < line.size(); i ++) // filling the fixerror list with the original code
        {
            fixError.add(line.get(i));
            lineOfError.add(0);
        }
        for (int i = 0; i < line.size(); i ++)
        {
            currentLine = line.get(i) ;
            while(currentLine.length() != 0 && (find_char(currentLine, '<') != -1) )
            {
                openBracket = find_char(currentLine, '<') ;
                if( openBracket != -1 ) // if = -1 , line full of words without opening or closing tags
                {
                    if(currentLine.indexOf('/') == (openBracket + 1)) // Closing Tag
                    {
                        endBracket = find_char(currentLine , '>') ;
                        if (endBracket != -1)
                        {
                            value_Stack = currentLine.substring((openBracket + 2),(endBracket )) ;
                            boolean no_error = value_Stack.equals(s.peek());

                            if(!no_error)
                            {
                                lineOfError.add(i,1);
                                while(!value_Stack.equals(s.peek()))
                                {
                                    correct =   "</" + s.peek() + ">" ;
                                    fixError.add(i , correct) ;
                                    s.pop() ;
                                }
                            }
                            s.pop();
                        }
                        else 
                        {
                            lineOfError.add(i + 1,1);
                            int x = find_char(currentLine, '<') ;
                            currentLine = currentLine.substring(0, x) + "</" + s.peek() + ">";
                            fixError.add(i + 1, currentLine) ;
                            correct = fixError.get(i) ;
                            correct = correct.substring(0, find_char(correct , '>') + 1) ;
                            fixError.set(i , correct) ;
                            s.pop() ;
                        }
                        
                    }
                    else  
                    {
                        endBracket = find_char(currentLine , '>') ;
                        value_Stack = currentLine.substring(openBracket + 1, endBracket) ;
                        if (value_Stack.length() != 0)
                        {
                            s.push(value_Stack);
                            //
                        }
                    }
                    endBracket = find_char(currentLine , '>') ;
                    currentLine = currentLine.substring(endBracket +1) ;
                   
                }
            }
        }
        int end  = line.size() - 1 ;
        while(!s.empty())
        {
            lineOfError.add(end,1);
            correct = fixError.get(end ) +  "</" + s.peek() + ">" ;
            fixError.add(end , correct) ;
            s.pop() ;
        }
        boolean flag = false ;
        for (int i = 0; i < fixError.size(); i ++) // finding the line that contaion the error
        {
            
            if (lineOfError.get(i) == 1)
            {
                System.out.println("You have Error in line " + i ) ;
                System.out.println("Please Fix it or Press the Correct Button Below" ) ;
                flag = true ;
                
            }
        }
        if (flag == false ) // if the code is error free
        {
            System.out.println("Your Code is Error free, Nice Coding =)" ) ;
        }
     
    }
    public static void main(String[] args) throws FileNotFoundException 
    {
        File file = new File("\\Users\\Ahmed\\Desktop\\sample.txt");
        Scanner scan = new Scanner(file) ;
        ArrayList<String> line = new ArrayList<String>();
        ArrayList<Integer> lineOfError = new ArrayList<Integer>();
        ArrayList<String> fixError = new ArrayList<String>();
        
        while(scan.hasNextLine())
        {
            line.add(scan.nextLine()) ;
        }
        try 
        {
            check_error(line, lineOfError, fixError);
        }
        catch(Exception EmptyStackException)
        {
            System.out.println("Wrong XML file , You have a Closing Tag without Opening it" );
            System.out.println("Please Fix it, then Try again" );
        }
            
        
    }
    
}
