import java.io.*;
import java.util.*;

public class ObjCode {
    private final String LDA = "00";
    private final String MUL = "20";
    private final String DIV = "24";
    private final String STA = "0C";
    private final String ADD = "18";

    private FileInputStream fileInputStream = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private HashMap<String, String> mnenomicMapping;
    private ArrayList<String> locationValue;
    private ArrayList<String> label;
    private ArrayList<String> instruction;
    private HashMap<String, String> onePassValue;
    private HashMap<String, String> locOperandValue = new HashMap<>();
    private ArrayList<String> operand;
    private ArrayList<String> opCodeList = new ArrayList<>();
    private ArrayList<String> objectCode;
    private String opCodeValue;
    private String operandCodeValue;


    private HashMap<ArrayList, ArrayList> operationOperand;

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
        this.onePassValue = new HashMap<>();
        this.operand = new ArrayList<>();
        this.objectCode = new ArrayList<>();
        this.operationOperand = new HashMap<>();
        this.locOperandValue = new HashMap<>();
        this.opCodeList = new ArrayList<>();
    }

    public void readFile() {
        try {
            File file = new File("file.txt");
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            Scanner input = new Scanner(file);
            String a;
            {
                System.out.println("-------------------------------READING FROM FILE-----------------------------------");
                while ((a = bufferedReader.readLine()) != null)
                    System.out.println(a);
                System.out.println("-----------------------------------------------------------------------------------");
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
            System.out.println(Arrays.toString(label.toArray()));
            System.out.println(Arrays.toString(instruction.toArray()));
            System.out.println(Arrays.toString(operand.toArray()));
        } catch (IOException e) {
            System.out.println("Error in file reading");
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("error in Scanner input statement");
            System.out.println(e.getMessage());
        }
    }

    public void onePassAssemble() throws StartException {
        int value;
        if (instruction.get(0).equals("START")) {
            String loc = operand.get(0);
            value = Integer.parseInt(loc);  //value==2000
            locationValue.add(Integer.toString(value));
        } else {
            throw new StartException("START instruction not found in the beginning. NO location can be identified");

        }
        int locationIncreament = 0;
        for (int i = 1; i < instruction.size(); i++) {
            //--------------------------FOR RESW WITH RANDOM NUMBER IN OPERAND(FUTURE MA IF I UPDATE THIS)-------------------------------
//            if(instruction.get(i).equals("RESW"))
//            {
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
            if (value + locationIncreament <= value + 9) {
                locationValue.add(Integer.toString(value + locationIncreament));
            } else if (locationIncreament > 9 && locationIncreament <= 15) {
                locationValue.add(value / 10 + Integer.toHexString(locationIncreament).toUpperCase());
            } else {

                locationValue.add(value / 100 + Integer.toHexString(locationIncreament).toUpperCase());
            }
            locationIncreament = locationIncreament + 3;
        }

        System.out.println("------------------------------------------ONE PASS ASSEMBLY----------------------------------------");
        for (int i = 0; i < instruction.size(); i++) {
            System.out.println(locationValue.get(i) + "\t|\t" + label.get(i) + "\t|\t" + instruction.get(i) + "\t|\t" + operand.get(i));

        }
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println(Arrays.toString(locationValue.toArray()));
    }

    public void twoPassAssemble() {
        opCodeList.add(LDA);
        opCodeList.add(MUL);
        opCodeList.add(DIV);
        opCodeList.add(STA);
        opCodeList.add(ADD);
        System.out.println(Arrays.toString(opCodeList.toArray()));


        for (int i = 0; i < instruction.size(); i++) {
            locOperandValue.put(label.get(i), locationValue.get(i));
        }
        System.out.println((locOperandValue.toString()));

        for (int i = 0; i < instruction.size(); i++) {
            if (instruction.get(i).equals("START") || instruction.get(i).equals("RESW") || instruction.get(i).equals("END")) {
                opCodeValue = "-";
                operandCodeValue = "-";
                objectCode.add(opCodeValue + operandCodeValue);

            } else {
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

                int k = 0;
                // System.out.println("okayyy" + instruction.get(i));
                System.out.println(opCodeList.get(k));

                for (Map.Entry<String, String> mnenomicValue : mnenomicMapping.entrySet())
                //System.out.println("Key = " + entry.getKey() +
                //        ", Value = " + entry.getValue());
                {
                    while (true) {
//                        if (instruction.get(i).equals(opCodeList.get(k))) {
//                            break;
//                        } else {
                        if(!instruction.get(i).equals(mnenomicValue.getKey())) {
                            i++;
                            break;
                        }else {

                            opCodeValue = mnenomicValue.getValue();
                            //System.out.println(opCodeList.get(k));
                            operandCodeValue = locationValue.get(i);
                            objectCode.add(opCodeValue + operandCodeValue);
                            break;
                        }
                    }




                }
            }
        }
        System.out.println(Arrays.toString(objectCode.toArray()));

    }

}


