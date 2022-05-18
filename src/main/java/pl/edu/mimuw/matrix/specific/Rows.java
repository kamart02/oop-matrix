package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.Abstract1DMatrix;
import pl.edu.mimuw.matrix.Shape;

public class Rows extends Abstract1DMatrix {
    public Rows(int rows, int cols, double... values) {
        super(rows, cols, values);
    }

    public Rows(Shape shape, double... values) {
        super(shape, values);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return data[row];
    }

    @Override
    public double normOne() {
        double sum = 0;

        for (var elem: data) {
            sum += Math.abs(elem);
        }

        return sum;
    }

    @Override
    public double normInfinity() {
        double maximum = 0;

        for (var elem: data) {
            maximum = Math.max(Math.abs(elem), maximum);
        }

        return maximum * shape.columns;
    }

    @Override
    public double frobeniusNorm() {
        double sum = 0;

        for (var elem: data) {
            sum += elem * elem * shape.columns;
        }

        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        if (shape.columns > 2) {
            var sb = new StringBuilder();

            sb.append(shape.toString());
            for (int i = 0; i < shape.rows; i++) {
                sb.append(this.data[i]).append(" ... ").append(this.data[i]).append("\n");
            }

            return sb.toString();
        }
        else {
            return super.toString();
        }
    }
}
