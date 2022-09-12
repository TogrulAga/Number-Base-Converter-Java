package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class Number {
    protected int radix;
    protected String value = "";
    protected List<Integer> wholeDigits = new ArrayList<>();
    protected List<Integer> fractionalDigits = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public Number() {}

    public Number(String value) {
        this.value = value.toUpperCase(Locale.ROOT);
        String wholeDigits;
        String fractionalDigits = null;

        if (this.value.contains(".")) {
            wholeDigits = this.value.split("\\.")[0];
            fractionalDigits = this.value.split("\\.")[1];
        } else {
            wholeDigits = this.value;
        }


        for (int i = 0; i < wholeDigits.length(); i++) {
            this.wholeDigits.add(Digit.filterByRepr(String.valueOf(wholeDigits.charAt(i))).ordinal());
        }

        if (fractionalDigits == null) {
            return;
        }

        for (int i = 0; i < fractionalDigits.length(); i++) {
            this.fractionalDigits.add(Digit.filterByRepr(String.valueOf(fractionalDigits.charAt(i))).ordinal());
        }
    }

    enum Digit {
        ZERO("0"), ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"),
        EIGHT("8"), NINE("9"), A("A"), B("B"), C("C"), D("D"), E("E"), F("F"),
        G("G"), H("H"), I("I"), J("J"), K("K"), L("L"), M("M"), N("N"),
        O("O"), P("P"), Q("Q"), R("R"), S("S"), T("T"), U("U"), V("V"), W("W"),
        X("X"), Y("Y"), Z("Z");

        public final String repr;

        Digit(String repr) {
            this.repr = repr;
        }

        public static Digit filterByRepr(String repr) {
            return (Digit) Arrays.stream(Digit.values()).filter(i -> i.repr.equals(repr)).toArray()[0];
        }
    }

    protected Number convertToRadix(int radix) {
        Number decimalNumber = this.convertToDecimal();

        String[] value = decimalNumber.getValue().split("\\.");
        BigInteger wholeValue = new BigInteger(value[0]);
        BigDecimal fractionalValue = value.length == 2 ? new BigDecimal("0." + value[1]) : null;

        Number newNumber = NumberFactory.getNumber(radix);
        while (!wholeValue.equals(BigInteger.ZERO)) {
            newNumber.addDigit(wholeValue.remainder(BigInteger.valueOf(radix)).intValue());
            wholeValue = wholeValue.divide(BigInteger.valueOf(radix));
        }

        newNumber.reverseValue();

        if ("".equals(newNumber.value)) {
            newNumber.addDigit(0);
        }

        if (fractionalValue == null) {
            return newNumber;
        }

        newNumber.addFloatingPoint();

        int counter = 5;
        while (!fractionalValue.equals(BigDecimal.ZERO) && counter != 0) {
            newNumber.addFractionalDigit(fractionalValue.multiply(BigDecimal.valueOf(radix)).remainder(BigDecimal.valueOf(radix)).intValue());
            fractionalValue = fractionalValue.multiply(BigDecimal.valueOf(radix)).remainder(BigDecimal.ONE);
            counter--;
        }

        return newNumber;
    }

    protected Number convertToDecimal() {
        BigInteger value = BigInteger.ZERO;
        BigDecimal fractionalValue = BigDecimal.ZERO;

        for (int i = 0, j = wholeDigits.size() - 1; i < wholeDigits.size(); i++, j--) {
            value = value.add(BigInteger.valueOf(wholeDigits.get(i)).multiply(bigPow(radix, j)));
        }

        if (fractionalDigits == null) {
            return new DecimalNumber(String.valueOf(value));
        }

        for (int i = 1; i <= fractionalDigits.size(); i++) {
            fractionalValue = fractionalValue.add(BigDecimal.valueOf(fractionalDigits.get(i - 1)).multiply(bigFractionalPow(radix, i)));
        }

        return new DecimalNumber(fractionalValue.add(new BigDecimal(value)).toString());
    }

    protected BigInteger bigPow(int a, int b) {
        BigInteger result = BigInteger.ONE;

        for (int i = 0; i < b; i++) {
            result = result.multiply(BigInteger.valueOf(a));
        }

        return result;
    }

    protected BigDecimal bigFractionalPow(int a, int b) {
        BigDecimal result = BigDecimal.ONE;

        for (int i = -b; i < 0; i++) {
            result = result.divide(BigDecimal.valueOf(a), 30, RoundingMode.CEILING);
        }

        return result;
    }

    protected void addDigit(int digit) {
        wholeDigits.add(digit);
        value += Digit.values()[digit].repr;
    }

    protected void addFractionalDigit(int digit) {
        fractionalDigits.add(digit);
        value += Digit.values()[digit].repr;
    }

    protected void addFloatingPoint() {
        value += ".";
    }

    public void reverseValue() {
        StringBuilder newValue = new StringBuilder();
        List<Integer> newDigits = new ArrayList<>();

        for (int i = 0; i < value.length(); i++) {
            newValue.insert(0, value.charAt(i));
            newDigits.add(0, wholeDigits.get(i));
        }

        value = newValue.toString();
        wholeDigits = newDigits;
    }
}
