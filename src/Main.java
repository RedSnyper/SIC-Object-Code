import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException,StartException {
        ObjCode objCode = new ObjCode();
        objCode.readFile();
        objCode.readMnemonic();
        objCode.onePassAssemble();
        objCode.twoPassAssemble();
        objCode.closeAll();

    }
}
