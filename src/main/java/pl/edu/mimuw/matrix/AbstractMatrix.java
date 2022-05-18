package pl.edu.mimuw.matrix;

import pl.edu.mimuw.matrix.specific.Full;

public abstract class AbstractMatrix implements IDoubleMatrix {
    protected final Shape shape;

    public AbstractMatrix(int rows, int cols) {
        this.shape = Shape.matrix(rows, cols);
    }

    public AbstractMatrix(Shape shape) {
        this.shape = shape;
    }

    @Override
    public Shape shape() {
        return shape;
    }

    @Override
    public double[][] data() {
        int rows = this.shape.rows;
        int columns = this.shape.columns;
        double[][] data = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                data[i][j] = this.get(i, j);
            }
        }

        return data;
    }

    @Override
    public double normOne() {
        int maximum = 0;
        int sum = 0;

        for (int i = 0; i < shape.columns; i++) {
            sum = 0;
            for (int j = 0; j < shape.rows; j++) {
                sum += Math.abs(get(j, i));
            }
            maximum = Math.max(sum, maximum);
        }

        return maximum;
    }

    @Override
    public double normInfinity() {
        int maximum = 0;
        int sum = 0;

        for (int i = 0; i < shape.rows; i++) {
            sum = 0;
            for (int j = 0; j < shape.columns; j++) {
                sum += Math.abs(get(i, j));
            }
            maximum = Math.max(sum, maximum);
        }

        return maximum;
    }

    @Override
    public double frobeniusNorm() {
        int sum = 0;

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                sum += Math.abs(Math.pow(get(i, j), 2));
            }
        }

        return Math.sqrt(sum);
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assertTimes(other);

        double[][] data = new double[shape.rows][other.shape().columns];

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < other.shape().columns; j++) {
                for (int k = 0; k < shape.columns; k++) {
                    data[i][j] += this.get(i, k) * other.get(k, j);
                }
            }
        }

        return new Full(Shape.matrix(shape.rows, other.shape().columns), data);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        double[][] data = new double[shape.rows][shape.columns];

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                data[i][j] += this.get(i, j) * scalar;
            }
        }

        return new Full(shape, data);
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assertPlus(other);

        double[][] data = new double[shape.rows][shape.columns];

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                data[i][j] += this.get(i, j) + other.get(i, j);
            }
        }

        return new Full(shape, data);
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        double[][] data = new double[shape.rows][shape.columns];

        for (int i = 0; i < shape.rows; i++) {
            for (int j = 0; j < shape.columns; j++) {
                data[i][j] += this.get(i, j) + scalar;
            }
        }

        return new Full(shape, data);
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        return this.plus(other.times(-1));
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        return this.plus(scalar * -1);
    }

    protected void assertTimes(IDoubleMatrix matrix) {
        assert shape.columns == matrix.shape().rows;
    }

    protected void assertPlus(IDoubleMatrix matrix) {
        assert shape.equals(matrix.shape());
    }

    @Override
    public String toString() {
        double[][] data = this.data();
        var sb = new StringBuilder();

        sb.append(shape.toString());

        for (double[] elem : data) {
            for (double value : elem) {
                sb.append(value).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
