package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.AbstractDiagonal;
import pl.edu.mimuw.matrix.Shape;

public class AntiDiagonal extends AbstractDiagonal {
    public AntiDiagonal(int rows, int cols, double... values) {
        super(rows, cols, values);
    }

    public AntiDiagonal(Shape shape, double... values) {
        super(shape, values);
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);
        if (row == shape.rows - column - 1) {
            return data[row];
        }
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append(shape.toString());
        for (int i = 0; i < shape.rows; i++) {
            if (i < shape.rows - 3) {
                sb.append("0 ... 0 ");
            }
            else {
                sb.append("0 ".repeat(shape.rows - i - 1));
            }
            sb.append(data[i]).append(" ");
            if (i > 2) {
                sb.append("0 ... 0");
            }
            else {
                sb.append("0 ".repeat(i));
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
