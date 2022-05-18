package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.AbstractMatrix;
import pl.edu.mimuw.matrix.Shape;

public class Full extends AbstractMatrix {
    private final double[][] data;

    public Full(int rows, int cols, double[][] data) {
        super(rows, cols);
        this.data = Array2DCopy(data);
    }

    public Full(Shape shape, double[][] data) {
        super(shape);
        this.data = Array2DCopy(data);
    }

    private double[][] Array2DCopy(double[][] data) {
        double[][] newData = new double[data.length][data[0].length];

        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, newData[i], 0, data[0].length);
        }

        return newData;
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        return data[row][column];
    }
}
