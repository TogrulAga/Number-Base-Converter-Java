package converter;

public class HexadecimalNumber extends Number {
    public HexadecimalNumber() {}

    public HexadecimalNumber(String value) {
        super(value);
        this.radix = 16;
    }
}
