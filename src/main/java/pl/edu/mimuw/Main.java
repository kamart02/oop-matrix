package pl.edu.mimuw;

import pl.edu.mimuw.matrix.DoubleMatrixFactory;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.Shape;

import static pl.edu.mimuw.matrix.DoubleMatrixFactory.sparse;
import static pl.edu.mimuw.matrix.MatrixCellValue.cell;
import static pl.edu.mimuw.matrix.Shape.matrix;

public class Main {

  public static void main(String[] args) {
    double[][] fullValues = new double[10][10];
    for (int i = 0; i < 100; i++) {
      fullValues[i / 10][i % 10] = i;
    }

    System.out.println("Full matrix");
    var full = DoubleMatrixFactory.full(fullValues);
    System.out.println(full);

    System.out.println("Antidiagonal matrix");
    var antiDiagonal = DoubleMatrixFactory.antiDiagonal(1, 2, 3, 4, 0, 4, 1, 2, 1, 4);
    System.out.println(antiDiagonal);

    System.out.println("Diagonal matrix");
    var diagonal = DoubleMatrixFactory.diagonal(1, 2, 3, 4, 0, 4, 1, 2, 1, 4);
    System.out.println(diagonal);

    System.out.println("Identity matrix");
    var identity = DoubleMatrixFactory.identity(10);
    System.out.println(identity);

    System.out.println("Constant matrix");
    var constant = DoubleMatrixFactory.constant(Shape.matrix(10, 10), 5);
    System.out.println(constant);

    System.out.println("Columns matrix");
    var columns = DoubleMatrixFactory.columns(Shape.matrix(10, 10), 1, 2, 3, 4, 0, 4, 1, 2, 1, 4);
    System.out.println(columns);

    System.out.println("Rows matrix");
    var rows = DoubleMatrixFactory.rows(Shape.matrix(10, 10), 1, 2, 3, 4, 0, 4, 1, 2, 1, 4);
    System.out.println(rows);

    System.out.println("Sparse matrix");
    var sparse = sparse(matrix(10, 10),
            cell(0, 0, 1),
            cell(0, 1, 2),
            cell(1, 0, 3),
            cell(1, 1, 4),
            cell(2, 0, 5),
            cell(2, 1, 6),
            cell(4, 2, 11),
            cell(4, 9, 12),
            cell(5, 2, 0),
            cell(5, 5, 112),
            cell(6, 8, 6)
    );
    System.out.println(sparse);

    System.out.println("Norm one");
    System.out.println(sparse.normOne());

    System.out.println("Norm infinity");
    System.out.println(full.normInfinity());

    System.out.println("Frobenius norm");
    System.out.println(sparse.frobeniusNorm());

    System.out.println("Times");
    System.out.println(full.times(sparse));

    System.out.println("Plus");
    System.out.println(antiDiagonal.plus(diagonal));

    System.out.println("Minus");
    System.out.println(rows.minus(columns));
  }
}
