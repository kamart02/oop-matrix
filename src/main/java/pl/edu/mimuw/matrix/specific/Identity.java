package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.AbstractDiagonal;

import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

public class Identity extends AbstractDiagonal {
    public Identity(int size) {
        super(size, size);
    }

    public Identity(Shape shape) {
        super(shape);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return 1;
    }

    @Override
    public double normOne() {
        return 1;
    }

    @Override
    public double normInfinity() {
        return 1;
    }

    @Override
    public double frobeniusNorm() {
        return Math.sqrt(shape.columns);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assertTimes(other);
        return other;
    }
}
