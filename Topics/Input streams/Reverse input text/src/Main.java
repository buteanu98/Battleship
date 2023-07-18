import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char[] input = new char[50];
        int number = reader.read(input);
        char[] reverse = new char[number];
        int j =0;
        for (int i = number; i > 0 ; i--) {
            if (input[i] != ' ') {
                reverse[j] = input[i - 1];
                j++;
            }
        }
        System.out.print(reverse);
        reader.close();
    }
}