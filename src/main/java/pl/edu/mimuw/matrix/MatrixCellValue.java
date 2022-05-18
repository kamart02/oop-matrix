package pl.edu.mimuw.matrix;

public final class MatrixCellValue implements Comparable {

  public final int row;
  public final int column;
  public final double value;

  public MatrixCellValue(int row, int column, double value) {
    this.column = column;
    this.row = row;
    this.value = value;
  }

  @Override
  public String toString() {
    return "{" + value + " @[" + row + ", " + column + "]}";
  }

  public static MatrixCellValue cell(int row, int column, double value) {
    return new MatrixCellValue(row, column, value);
  }

  @Override
  public int compareTo(Object o) {
    var newO = (MatrixCellValue) o;

    if (this.row < newO.row) {
      return -1;
    }
    else if (this.row > newO.row) {
      return 1;
    }
    else {
      return Integer.compare(this.column, newO.column);
    }
  }

  public int compareToColumnsRows(Object o) {
    var newO = (MatrixCellValue) o;

    if (this.column < newO.column) {
      return -1;
    }
    else if (this.column > newO.column) {
      return 1;
    }
    else {
      return Integer.compare(this.row, newO.row);
    }
  }
}
