import java.io.*;
import java.util.*;

public class ObjCode {
    private final String LDA = "00";
    private final String MUL = "20";
    private final String DIV = "24";
    private final String STA = "0C";
    private final String ADD = "18";

    private File file;
    private FileInputStream fileInputStream = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private HashMap<String, String> mnenomicMapping;
    private ArrayList<String> locationValue;
    private ArrayList<String> label;
    private ArrayList<String> instruction;
    private HashMap<String, String> locOperandValue;
    private ArrayList<String> operand;
    private Scanner input;
   // private ArrayList<String> opCodeList;
    private ArrayList<String> objectCode;
    private String opCodeValue;
    private String operandCodeValue;

    public ObjCode() {
        this.mnenomicMapping = new HashMap<>();
        this.mnenomicMapping.put("LDA", LDA);
        this.mnenomicMapping.put("MUL", MUL);
        this.mnenomicMapping.put("DIV", DIV);
        this.mnenomicMapping.put("STA", STA);
        this.mnenomicMapping.put("ADD", ADD);

        this.locationValue = new ArrayList<>();
        this.label = new ArrayList<>();
        this.instruction = new ArrayList<>();
        this.operand = new ArrayList<>();
        this.objectCode = new ArrayList<>();
        this.locOperandValue = new HashMap<>();
     //   this.opCodeList = new ArrayList<>();
    }

    public void readFile() throws IOException {
        try {
            file = new File("file.txt");
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            input = new Scanner(file);
            String a;
            {
                System.out.println("-------------------------------READING FROM FILE-----------------------------------------------------");
                while ((a = bufferedReader.readLine()) != null)
                    System.out.println(a);
                System.out.println("----------------------------------------------------------------------------------------------------");
            }
            int count = 0;

            while (input.hasNext() == true) {
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
//            System.out.println(Arrays.toString(label.toArray()));
//            System.out.println(Arrays.toString(instruction.toArray()));
//            System.out.println(Arrays.toString(operand.toArray()));
        } catch (IOException e) {
            System.out.println("Error in file reading");
            System.out.println(e.getMessage());
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("error in Scanner input statement");
            System.out.println(e.getMessage());
            input.close();
        }
    }

    public void onePassAssemble() throws StartException {
        int value;
        if (instruction.get(0).equals("START")) {
            String loc = operand.get(0);
            value = Integer.parseInt(loc, 16);  //value==2000
            System.out.println("decimal value" + value);
            locationValue.add(Integer.toHexString(value));


        } else {
            throw new StartException("START instruction not found in the beginning. NO location can be identified");

        }
        int locationIncreament = 0;
        for (int i = 1; i < instruction.size(); i++) {
            //--------------------------FOR RESW WITH RANDOM NUMBER IN OPERAND(FUTURE MA IF I UPDATE THIS)-------------------------------
           if(instruction.get(i).equals("RESW")) {
               int reswValue;
               reswValue = Integer.parseInt(operand.get(i));
               reswValue = reswValue * 3;
               value = value + reswValue;
               locationValue.add(Integer.toHexString(value).toUpperCase());


//                String valueReswString;
//                int valueResw;
//                valueResw = Integer.parseInt(operand.get(i));
//                valueResw = valueResw*3;
//                if()
//                valueReswString = Integer.toHexString(valueResw);  //to bytes
//                if(valueResw <=9)
//                locationValue.add(Integer.toString(+valueResw));
//                else if (valueResw <=15)
//                {
//                    locationValue.add(value/10 + Integer.toHexString(valueResw+value).toUpperCase());
//                }else{
//                    locationValue.add(value /100 + Integer.toHexString(valueResw).toUpperCase());
//                }
//            }
//            if (value + locationIncreament <= value + 9) {
//                locationValue.add(Integer.toString(value + locationIncreament));
//            } else if (locationIncreament > 9 && locationIncreament <= 15) {
//                locationValue.add(value / 10 + Integer.toHexString(locationIncreament).toUpperCase());
//            } else {
//
//                locationValue.add(value / 100 + Integer.toHexString(locationIncreament).toUpperCase());
//            }
//            locationIncreament = locationIncreament + 3;

           }else {
               value = value + locationIncreament;
               locationIncreament = 3;
               System.out.println("loc inc value" + locationIncreament);
               System.out.println("loop dec value" + value);
               locationValue.add(Integer.toHexString(value).toUpperCase());
           }
           }

            System.out.println("------------------------------------------ONE PASS ASSEMBLY----------------------------------------");
            for (int i = 0; i < instruction.size(); i++) {
                System.out.println(String.format("%-10s %-10s %-10s %-10s ", locationValue.get(i), label.get(i), instruction.get(i), operand.get(i)));
            }
            System.out.println("-----------------------------------------------------------------------------------------------------");
            //   System.out.println(Arrays.toString(locationValue.toArray()));
        }


    public void twoPassAssemble() {
//        opCodeList.add(LDA);
//        opCodeList.add(MUL);
//        opCodeList.add(DIV);
//        opCodeList.add(STA);
//        opCodeList.add(ADD);
       // System.out.println(Arrays.toString(opCodeList.toArray()));

        for (int i = 0; i < instruction.size(); i++) {
            locOperandValue.put(label.get(i), locationValue.get(i));
        }
       // System.out.println((mnenomicMapping));

        for (int i = 0; i < instruction.size(); i++) {
            if (instruction.get(i).equals("START") || instruction.get(i).equals("RESW") || instruction.get(i).equals("END")) {
                opCodeValue = "-";
                operandCodeValue = "-";
                objectCode.add(opCodeValue + operandCodeValue);

            }else if(instruction.get(i).equals("WORD"))
            {
                int value = Integer.parseInt(operand.get(i));
                if(value < 10)
                    objectCode.add("000"+Integer.toHexString(value).toUpperCase());
                else
                    objectCode.add("00"+Integer.toHexString(value).toUpperCase());
            }
            else {
                int j = 1;
                while (!operand.get(i).equals(label.get(j))) {
                    if (j >= 11)
                        break;
                    if (operand.get(i).equals(label.get(j))) {
                        break;
                    } else {
                        j++;
                    }
                }
                for (Map.Entry<String, String> mnenomicValue : mnenomicMapping.entrySet())
                {
                        if(!instruction.get(i).equals(mnenomicValue.getKey())) {
                            continue;
                            }
                        else {
                            opCodeValue = mnenomicValue.getValue();
                            operandCodeValue = locationValue.get(j);
                            objectCode.add(opCodeValue + operandCodeValue);
                        }
                    }
                }
            }
       // System.out.println(Arrays.toString(objectCode.toArray()));

        System.out.println("---------------------------------------------------TWO PASS ASSEMBLY-----------------------------------------------");
        for(int i=0;i<instruction.size();i++)
        System.out.println(String.format("%-10s %-10s %-10s %-10s %-10s",locationValue.get(i) , label.get(i) , instruction.get(i) , operand.get(i) , objectCode.get(i)));
        System.out.println("--------------------------------------------------------------------------------------------------------------------");

    }

    public void closeAll() {
        try {

            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            input.close();
        }catch (IOException e)
        {
            System.out.println("error while file closing" + e.getMessage());
        }
    }

}


