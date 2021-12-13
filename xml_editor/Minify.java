import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Minify {
    private static void minify(String originalfilename,String minifiedfilename) throws IOException {
        char space = ' ';

        //Creating the new minified file
        new File(minifiedfilename);

        //Creating the writer of the file
       FileWriter myWriter = new FileWriter(minifiedfilename);

        //reading the original file and writing to the new file in a minified way
            File myObj = new File(originalfilename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                boolean flag = true;
                String data = myReader.nextLine();
                for (int i=0;i<data.length();i++){
                    if (data.charAt(i)==space && flag )
                        continue;
                    flag = false;
                    myWriter.write(data.charAt(i));
                }
              //  System.out.println(data);
             // if(flag == false)
                //    myWriter.write("\n");
            }
            myReader.close();

        //closing the writer of the file
            myWriter.close();
    }

    public static void minifycaller (String originalfilename,String minifiedfilename){
        try {
            minify(originalfilename, minifiedfilename);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}