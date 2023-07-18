public class CustomException extends Exception {
    public CustomException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}

public class Main {
    public static void methodThrowsException() throws CustomException {
        throw new CustomException('This is a custom exception', new RuntimeException());
    }

    public static void main(String[] args) {
        try {
            methodThrowsException();
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
