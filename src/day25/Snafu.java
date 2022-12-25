package day25;

public final class Snafu {
    public final String number;
    public final long decimalValue;

    public Snafu(final String number) {
        this.number = number;
        this.decimalValue = this.toDecimal(number);
    }

    public static Snafu fromDecimal(long decimal) {
        String snafuNumber = "";

        int carry = 0;
        while (decimal > 0) {
            final int remainder = (int) (decimal % 5 + carry);

            switch (remainder) {
                case 5:
                    snafuNumber += "0";
                    carry = 0;
                    break;
                case 4:
                    snafuNumber += "-";
                    carry = 1;
                    break;
                case 3:
                    snafuNumber += "=";
                    carry = 1;
                    break;
                default:
                    snafuNumber += Integer.toString(remainder);
                    carry = 0;
            }

            decimal /= 5;
        }

        if (carry == 1) {
            snafuNumber += "1";
        }

        return new Snafu(new StringBuilder(snafuNumber).reverse().toString());
    }

    private long toDecimal(final String number) {
        long decimalValue = 0;
        long fivePower = 1;

        for (int i = number.length() - 1; i >= 0; i--) {
            final char digit = number.charAt(i);
            final long value = digit == '=' ? -2 : digit == '-' ? -1 : Character.getNumericValue(digit);

            decimalValue += value * fivePower;
            fivePower *= 5;
        }

        return decimalValue;
    }
}
