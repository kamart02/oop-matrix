package pl.edu.mimuw.matrix;

import pl.edu.mimuw.matrix.specific.*;

public class DoubleMatrixFactory {

  private DoubleMatrixFactory() {
  }

  public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values){
    return new Sparse(shape, values);
  }

  public static IDoubleMatrix full(double[][] values) {
    assert values != null;

    int lengthRow = values.length;
    assert lengthRow != 0;

    int lengthColumn = values[0].length;
    assert lengthColumn != 0;

    for (double[] value : values) {
      assert value.length == lengthColumn;
    }

    return new Full(values.length, values[0].length, values);
  }

  public static IDoubleMatrix identity(int size) {
    return new Identity(size);
  }

  public static IDoubleMatrix diagonal(double... diagonalValues) {
   return new Diagonal(diagonalValues.length, diagonalValues.length, diagonalValues);
  }

  public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
    return new AntiDiagonal(antiDiagonalValues.length, antiDiagonalValues.length, antiDiagonalValues);
  }

  public static IDoubleMatrix vector(double... values){
    double[][] data = new double[values.length][1];

    for (int i = 0; i < values.length; i++) {
      data[i][0] = values[i];
    }

    return new Full(values.length, 1, data);
  }

  public static IDoubleMatrix constant(Shape shape, double value) {
    return new Constant(shape, value);
  }

  public static IDoubleMatrix zero(Shape shape) {
    return new Constant(shape, 0);
  }

  public static IDoubleMatrix rows(Shape shape, double... values) {
    return new Rows(shape, values);
  }

  public static IDoubleMatrix columns(Shape shape, double... values) {
    return new Columns(shape, values);
  }
}
