package converter;

public class BinaryNumber extends Number {

    public BinaryNumber() {}

    public BinaryNumber(String value) {
        super(value);
        this.radix = 2;
    }
}
