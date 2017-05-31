package rpncalc;

/**
 *
 * @author mitsikeli
 */
public class TokenValue implements Token {

    private double value;

    public TokenValue(String input) {
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal number (" + input + ") as input.");
        }
    }

    /**
     *
     * @param input
     */
    public TokenValue(double input) {
        value = input;
    }

    @Override
    public double getValue() {
        return value;
    }

}
