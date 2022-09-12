package converter;

public class ExtendedBaseNumber extends Number{
    public ExtendedBaseNumber(int radix) {
        this.radix = radix;
    }

    public ExtendedBaseNumber(String value, int radix) {
        super(value);
        this.radix = radix;
    }
}
