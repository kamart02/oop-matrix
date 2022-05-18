package pl.edu.mimuw.matrix;

import pl.edu.mimuw.matrix.Abstract1DMatrix;
import pl.edu.mimuw.matrix.Shape;

public abstract class AbstractDiagonal extends Abstract1DMatrix {
    public AbstractDiagonal(int rows, int cols, double... values) {
        super(rows, cols, values);
    }

    public AbstractDiagonal(Shape shape, double... values) {
        super(shape, values);
    }
    @Override
    public double normOne() {
        double maximum = 0;

        for (var elem: data) {
            maximum = Math.max(Math.abs(elem), maximum);
        }

        return maximum;
    }

    @Override
    public double normInfinity() {
        return this.normOne();
    }

    @Override
    public double frobeniusNorm() {
        double sum = 0;

        for (var elem: data) {
            sum += elem * elem;
        }

        return Math.sqrt(sum);
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append(shape.toString());
        for (int i = 0; i < shape.rows; i++) {
            if (i > 2) {
                sb.append("0 ... 0 ");
            }
            else {
                sb.append("0 ".repeat(i));
            }
            sb.append(this.get(i, i)).append(" ");
            if (i < shape.rows - 3) {
                sb.append("0 ... 0");
            }
            else {
                sb.append("0 ".repeat(shape.rows - i - 1));
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
