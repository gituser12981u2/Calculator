package Calculator;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Scanner sc = new Scanner(System.in);

        
        while(true) {
            System.out.println("Enter a mathematical expression:");
            String input = sc.nextLine();

            try {
                List<Token> tokens = lexer.tokenize(input);
                double result = parser.evaluate(tokens);
                System.out.println("Result: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (input.equals("quit")) {
                break;
            }
        }
        System.out.println("Program ended");
        sc.close();
    }
}
