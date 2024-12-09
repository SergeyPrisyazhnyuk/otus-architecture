package ru.otus.moduletests;

public class DiscriminantSolver {

    public double[] solve(double a, double b, double c) {
        if (!(isDouble(a) && isDouble(b) && isDouble(c))) {
            throw new IllegalArgumentException("Some variable/s is/are Nan");
        }

        if (isZero(a)) {
            throw new IllegalArgumentException("Coefficient a is zero");
        }

        double discriminant = Math.pow(b, 2) - 4 * a * c;

        if (isZero(discriminant)) {

            return new double[]{(-1) * b / (2 * a)};

        } else if (discriminant < 0) {

            return new double[]{};

        } else {

            return new double[]{
                    ((-1) * b + Math.sqrt(discriminant)) / (2 * a),
                    ((-1) * b - Math.sqrt(discriminant)) / (2 * a)};
        }

    }

    private static boolean isDouble(double value) {
        return !Double.isNaN(value) && Double.isFinite(value);
    }

    private static boolean isZero(double value) {
        return value >= -.000001 && value <= .000001;
    }

}
