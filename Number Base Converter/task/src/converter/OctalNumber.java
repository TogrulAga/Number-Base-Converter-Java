package converter;

public class OctalNumber extends Number {
    public OctalNumber() {}

    public OctalNumber(String value) {
        super(value);
        this.radix = 8;
    }
}
