package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.AbstractDiagonal;
import pl.edu.mimuw.matrix.Shape;

public class Diagonal extends AbstractDiagonal {
    public Diagonal(int rows, int cols, double... values) {
        super(rows, cols, values);
        assert rows == cols;
        assert values.length == rows;
    }

    public Diagonal(Shape shape, double... values) {
        super(shape, values);
        assert shape.rows == shape.columns;
        assert values.length == shape.columns;
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        if (row == column) {
            return data[row];
        }
        else {
            return 0;
        }
    }


}
