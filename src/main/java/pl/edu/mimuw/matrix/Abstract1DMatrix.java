package pl.edu.mimuw.matrix;

import pl.edu.mimuw.matrix.AbstractMatrix;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

public abstract class Abstract1DMatrix extends AbstractMatrix {
    protected final double[] data;

    public Abstract1DMatrix(int rows, int cols, double... values) {
        super(rows, cols);
        this.data = new double[values.length];

        System.arraycopy(values, 0, this.data, 0, values.length);
    }

    public Abstract1DMatrix(Shape shape, double... values) {
        super(shape);
        this.data = new double[values.length];

        System.arraycopy(values, 0, this.data, 0, values.length);
    }
}
