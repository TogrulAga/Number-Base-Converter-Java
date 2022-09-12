package converter;

import java.util.Scanner;

public class BaseConverter {
    Scanner scanner = new Scanner(System.in);

    public BaseConverter() {
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String value = scanner.nextLine();

            if ("/exit".equals(value)) {
                System.exit(0);
            }

            int sourceRadix = Integer.parseInt(value.split(" ")[0]);
            int targetRadix = Integer.parseInt(value.split(" ")[1]);

            convertSourceToTargetBase(sourceRadix, targetRadix);
        }
    }

    private void convertSourceToTargetBase(int sourceRadix, int targetRadix) {
        while (true) {
            System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", sourceRadix, targetRadix);
            String value = scanner.nextLine();

            if ("/back".equals(value)) {
                System.out.println();
                break;
            }

            Number number = NumberFactory.getNumber(sourceRadix, value);

            Number targetNumber = number.convertToRadix(targetRadix);

            System.out.printf("Conversion result: %s%n%n", targetNumber.getValue());
        }
    }
}
