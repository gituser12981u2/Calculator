package Calculator;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a mathematical expression:");

        while(true) {
            String input = sc.nextLine();

            

            if (input.equals("quit")) {
                break;
            }
        }
        System.out.println("Program ended");
        sc.close();
    }
}
