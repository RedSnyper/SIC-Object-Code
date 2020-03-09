import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ObjCode {
    private final String LDA = "0C";
    private final String MUL = "20";
    private final String DIV = "24";
    private final String STA = "0C";
    private final String ADD = "18";

    private FileInputStream fileInputStream = null;
    private InputStreamReader inputStreamReader = null;

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
}
