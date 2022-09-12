package converter;

public class NumberFactory {
    public static Number getNumber(int radix) {
        switch (radix) {
            case 10:
                return new DecimalNumber();
            case 2:
                return new BinaryNumber();
            case 8:
                return new OctalNumber();
            case 16:
                return new HexadecimalNumber();
            default:
                return new ExtendedBaseNumber(radix);
        }
    }

    public static Number getNumber(int radix, String value) {
        switch (radix) {
            case 10:
                return new DecimalNumber(value);
            case 2:
                return new BinaryNumber(value);
            case 8:
                return new OctalNumber(value);
            case 16:
                return new HexadecimalNumber(value);
            default:
                return new ExtendedBaseNumber(value, radix);
        }
    }
}
