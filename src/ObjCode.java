import java.io.*;
import java.util.*;

public class ObjCode {
//    private final String LDA = "00";
//    private final String MUL = "20";
//    private final String DIV = "24";
//    private final String STA = "0C";
//    private final String ADD = "18";

    private File file;
    private File fileMnemonicFetch;
    private FileInputStream fileInputStream = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;

    private HashMap<String, String> mnenomicMapping;
    private ArrayList<String> locationValue;
    private ArrayList<String> label;
    private ArrayList<String> instruction;
    private HashMap<String, String> locOperandValue;
    private ArrayList<String> operand;
    private ArrayList<String> mnemonicName;
    private ArrayList<String> mnemonicNameValue;
    private Scanner input;
    // private ArrayList<String> opCodeList;
    private ArrayList<String> objectCode;
    private String opCodeValue;
    private String operandCodeValue;

    public ObjCode() {
        this.mnenomicMapping = new HashMap<>();
        this.mnemonicName = new ArrayList<>();
        this.mnemonicNameValue = new ArrayList<>();

        this.locationValue = new ArrayList<>();
        this.label = new ArrayList<>();
        this.instruction = new ArrayList<>();
        this.operand = new ArrayList<>();
        this.objectCode = new ArrayList<>();
        this.locOperandValue = new HashMap<>();
        //   this.opCodeList = new ArrayList<>();
    }


    public void readMnemonic(){
        try {
            int count=0;
            fileMnemonicFetch = new File("mnemonic.txt");
            Scanner readValues = new Scanner(fileMnemonicFetch);
            while(readValues.hasNext())
            {
                String message = readValues.next();
                if(count%2==0)
                {
                    this.mnemonicName.add(message);
                }else{
                    this.mnemonicNameValue.add(message);
                }
                count++;
            }
            for(int i = 0; i< mnemonicName.size();i++)
            {
                mnenomicMapping.put(mnemonicName.get(i) , mnemonicNameValue.get(i));
            }
        }catch(IOException e)
        {
            System.out.println("File not found");
            System.out.println(e.getMessage());

        }

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

            while (input.hasNext()) {
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
            //System.out.println("decimal value" + value);
            locationValue.add(Integer.toHexString(value));


        } else {
            throw new StartException("START instruction not found in the beginning. No location can be identified for one pass assembly. Program closing....");

        }
        int locationIncreament = 0;
        int byteIncreament = 3;
        for (int i = 1; i < instruction.size(); i++) {
            //--------------------------FOR RESW WITH RANDOM NUMBER IN OPERAND(FUTURE MA IF I UPDATE THIS)-------------------------------
            if(instruction.get(i).equals("RESW")) {
                int reswValue;
                if(!instruction.get(i-1).equals("RESW"))
                {
                    value = value + 3;
                    locationValue.add(Integer.toHexString(value).toUpperCase());


                }else {
                    reswValue = Integer.parseInt(operand.get(i-1));
                    reswValue = reswValue * 3;
                    value = value + reswValue;
                    locationValue.add(Integer.toHexString(value).toUpperCase());


                }
                if(!instruction.get(i+1).equals("RESW"))
                {
                    reswValue = Integer.parseInt(operand.get(i));
                    reswValue = reswValue*3;
                    value = value + reswValue;
                    locationIncreament = 0;

                }
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

            }else if(instruction.get(i).equals("RESB")){


                int resbValue = Integer.parseInt(operand.get(i));
                // value = value + 3;
                locationValue.add(Integer.toHexString(value).toUpperCase());
                value = value + resbValue;
                locationIncreament = 0;

            }else if(instruction.get(i).equals("BYTE"))
            {
                value = value + byteIncreament;
                locationValue.add(Integer.toHexString(value).toUpperCase());
                String val = operand.get(i);

                int k = 2;
                // System.out.println(val.charAt(k));
                while( val.charAt(k)!='\'')
                {
                    k++;
                }
                if(val.charAt(0) =='C')
                {
                    k = k-2;
                    //System.out.println("val of k" + k);
                    //  System.out.println(Integer.toHexString(value));
                    value = value + k;
                    // System.out.println("after adding" + Integer.toHexString(value));


                }else if(val.charAt(0)=='X')
                {
                    k = k-2;
                    k = k/2;
                    value = value + k;


                }
                locationIncreament = 0;
            }
            else {
                value = value + locationIncreament;
                locationIncreament = 3;
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

        for (int i = 0; i < instruction.size(); i++) {
            locOperandValue.put(label.get(i), locationValue.get(i));
        }

        // System.out.println((mnenomicMapping));

        for (int i = 0; i < instruction.size(); i++) {
            if (instruction.get(i).equals("START") || instruction.get(i).equals("RESW") || instruction.get(i).equals("END") || instruction.get(i).equals("RESB")){
                opCodeValue = "-";
                operandCodeValue = "-";
                objectCode.add(opCodeValue + operandCodeValue);

            }else if(instruction.get(i).equals("WORD"))
            {
                int value = Integer.parseInt(operand.get(i));
                if(value <= 15)
                    objectCode.add("00000"+Integer.toHexString(value).toUpperCase());
                else
                    objectCode.add("0000"+Integer.toHexString(value).toUpperCase());
            }else if(instruction.get(i).equals("RSUB"))
            {
                for(Map.Entry<String,String> RSUBCheck : mnenomicMapping.entrySet() )
                {
                    if(RSUBCheck.getKey().equals("RSUB"))
                    {
                        opCodeValue = RSUBCheck.getValue();
                        operandCodeValue = "0000";
                        objectCode.add(opCodeValue+operandCodeValue);
                        break;
                    }
                }
            }else if( instruction.get(i).equals("BYTE"))
            {
                String val = operand.get(i);
                String objectCodeMessage = "";
                String charEquivalent;
                int k = 2;
                // System.out.println(val.charAt(k));
                if(val.charAt(k-2)=='X')
                {
                    while( val.charAt(k)!='\'')
                    {
                        objectCodeMessage += "" + val.charAt(k);

                        k++;
                    }
                    objectCode.add(objectCodeMessage);
                }else if(val.charAt(k-2)=='C') {
                    while (val.charAt(k) != '\'') {

                        objectCodeMessage += Integer.toHexString(val.charAt(k)).toUpperCase();

                        k++;
                    }
                    objectCode.add(objectCodeMessage);
                }
            }
            else {
                int j = 1;
                while (!operand.get(i).equals(label.get(j))) {
                    if (j >= label.size())
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
                        break;
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


