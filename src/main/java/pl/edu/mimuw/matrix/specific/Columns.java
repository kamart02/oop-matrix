package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.Abstract1DMatrix;
import pl.edu.mimuw.matrix.Shape;

public class Columns extends Abstract1DMatrix {
    public Columns(int rows, int cols, double... values) {
        super(rows, cols, values);
    }

    public Columns(Shape shape, double... values) {
        super(shape, values);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return data[column];
    }

    @Override
    public double normOne() {
        double maximum = 0;

        for (var elem: data) {
            maximum = Math.max(Math.abs(elem), maximum);
        }

        return maximum * shape.rows;

    }

    @Override
    public double normInfinity() {
        double sum = 0;

        for (var elem: data) {
            sum += Math.abs(elem);
        }

        return sum;
    }

    @Override
    public double frobeniusNorm() {
        double sum = 0;

        for (var elem: data) {
            sum += elem * elem * shape.rows;
        }

        return Math.sqrt(sum);
    }
}
