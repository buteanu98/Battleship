class Converter {

    /**
     * It returns a double value or 0 if an exception occurred
     */
    public static double convertStringToDouble(String input) {
        double d;
        try {
            d=Double.parseDouble(input);
        } catch (Exception e) {
           d=0.0d;
        }
        return d;
    }
}
