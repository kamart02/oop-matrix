package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.AbstractMatrix;
import pl.edu.mimuw.matrix.Shape;

public class Constant extends AbstractMatrix {
    protected final double val;

    public Constant(int rows, int cols, double val) {
        super(rows, cols);
        this.val = val;
    }

    public Constant(Shape shape, double val) {
        super(shape);
        this.val = val;
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return val;
    }

    @Override
    public double normOne() {
        return this.shape.rows * val;
    }

    @Override
    public double normInfinity() {
        return this.shape.columns * val;
    }

    @Override
    public double frobeniusNorm() {
        return Math.sqrt(Math.abs(val * val * this.shape.columns * this.shape.rows));
    }

    @Override
    public String toString() {
        if (shape.columns > 2) {
            var sb = new StringBuilder();

            sb.append(shape.toString());
            for (int i = 0; i < shape.rows; i++) {
                sb.append(this.val).append(" ... ").append(this.val).append("\n");
            }

            return sb.toString();
        }
        else {
            return super.toString();
        }
    }
}
