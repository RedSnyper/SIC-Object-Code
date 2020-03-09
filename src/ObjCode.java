import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ObjCode {
    private final String LDA = "0C";
    private final String MUL = "20";
    private final String DIV = "24";
    private final String STA = "0C";
    private final String ADD = "18";

    private FileInputStream fileInputStream = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private ArrayList<Integer> location;
    private ArrayList<String> label;
    private ArrayList<String> instruction;
    private HashMap<String,String> onePassValue;
    //private HashMap<String,String> mnenonicValue = new HashMap<>();
    private ArrayList<String> operand;
    private ArrayList<String> objectCode;

    private HashMap<ArrayList,ArrayList> operationOperand;

    public ObjCode() {
        this.location = new ArrayList<>();
        this.label = new ArrayList<>();
        this.instruction = new ArrayList<>();
        this.onePassValue = new HashMap<>();
        this.operand = new ArrayList<>();
        this.objectCode = new ArrayList<>();
        this.operationOperand = new HashMap<>();
    }

    public void readFile()
    {
        try{
            File file = new File("file.txt");
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            Scanner input = new Scanner(file);
            String a;
            {
                System.out.println("-------------------------------READING FROM FILE-----------------------------------");
                while((a=bufferedReader.readLine())!=null)
                    System.out.println(a);
                System.out.println("-----------------------------------------------------------------------------------");
            }
            int count=0;

            while(input.hasNext()==true) {
               String message = input.next();
                {
                    if (count % 3 == 0) {
                        label.add(message);

                    } else if (count % 3 == 1) {
                        instruction.add(message);

                    } else if (count % 3 == 2) {
                        operand.add(message);

                    }
                }
                count++;
            }
            System.out.println(Arrays.toString(label.toArray()));
            System.out.println(Arrays.toString(instruction.toArray()));
            System.out.println(Arrays.toString(operand.toArray()));
        }catch(IOException e)
        {
            System.out.println("Error in file reading");
            System.out.println(e.getMessage());
        }catch(Exception e)
        {
            System.out.println("error in Scanner input statement");
            System.out.println(e.getMessage());
        }
    }
}
