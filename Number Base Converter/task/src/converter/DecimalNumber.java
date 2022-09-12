package converter;

public class DecimalNumber extends Number {

    public DecimalNumber() {}

    public DecimalNumber(String value) {
        super(value);
        this.radix = 10;
    }
}
