public class StartException extends Exception {

    public StartException(String message)
    {
        System.out.println(message);
        System.exit(-1);
    }

}
