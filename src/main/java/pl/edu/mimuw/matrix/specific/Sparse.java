package pl.edu.mimuw.matrix.specific;

import pl.edu.mimuw.matrix.AbstractMatrix;
import pl.edu.mimuw.matrix.IDoubleMatrix;
import pl.edu.mimuw.matrix.MatrixCellValue;
import pl.edu.mimuw.matrix.Shape;

import java.util.ArrayList;
import java.util.Arrays;


public class Sparse extends AbstractMatrix {
    private final ArrayList<ArrayList<MatrixCellValue>> data;

    public Sparse(int rows, int cols, MatrixCellValue... values) {
        super(rows, cols);

        int lastRow = -1;
        int lastIndex = -1;

        Arrays.sort(values);
        this.data = new ArrayList<>();

        for (var elem: values) {
            if (elem.row != lastRow) {
                lastIndex++;
                this.data.add(new ArrayList<>());
            }
            shape.assertInShape(elem.row, elem.column);
            this.data.get(lastIndex).add(elem);

            lastRow = elem.row;
        }
    }

    public Sparse(Shape shape, MatrixCellValue... values) {
        super(shape);

        int lastRow = -1;
        int lastIndex = -1;

        Arrays.sort(values);
        this.data = new ArrayList<>();

        for (var elem: values) {
            if (elem.row != lastRow) {
                lastIndex++;
                this.data.add(new ArrayList<>());
            }
            shape.assertInShape(elem.row, elem.column);
            this.data.get(lastIndex).add(elem);

            lastRow = elem.row;
        }
    }

    @Override
    public double get(int row, int column) {
        shape.assertInShape(row, column);

        for (ArrayList<MatrixCellValue> datum : this.data) {
            if (datum.get(0).row == row) {
                for (MatrixCellValue matrixCellValue : datum) {
                    if (matrixCellValue.column > column) {
                        return 0;
                    } else if (matrixCellValue.column == column) {
                        return matrixCellValue.value;
                    }
                }
            } else if (datum.get(0).row > row) {
                return 0;
            }
        }

        return 0;
    }

    public ArrayList<ArrayList<MatrixCellValue>> getRowColumnList() {
        var newData = new ArrayList<ArrayList<MatrixCellValue>>();

        for (var elem: this.data) {
            newData.add(new ArrayList<>());
            for (var cell: elem) {
                newData.get(newData.size() - 1).add(cell);
            }
        }

        return newData;
    }

    public MatrixCellValue[] getDataArray() {
        var newData = new ArrayList<MatrixCellValue>();

        for (var elem: this.data) {
            newData.addAll(elem);
        }

        return newData.toArray(MatrixCellValue[]::new);
    }

    public ArrayList<ArrayList<MatrixCellValue>> getColumnRowList() {
        var newData = this.getDataArray();

        Arrays.sort(newData, MatrixCellValue::compareToColumnsRows);

        int lastColumn = -1;
        int lastIndex = -1;

        var newList = new ArrayList<ArrayList<MatrixCellValue>>();

        for (var elem: newData) {
            if (elem.column != lastColumn) {
                lastIndex++;
                newList.add(new ArrayList<>());
            }
            newList.get(lastIndex).add(elem);

            lastColumn = elem.column;
        }

        return newList;
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assertTimes(other);

        if (other.getClass() == Identity.class) {
            return this;
        }
        else if (other.getClass() == Sparse.class) {
            var otherData = ((Sparse) other).getColumnRowList();

            int thisMaxRow = this.data.size();
            int otherMaxColumn = otherData.size();

            int thisIteratorRow = 0;
            int otherIteratorColumn = 0;

            var newData = new ArrayList<MatrixCellValue>();

            for (int i = 0; i < thisMaxRow; i++) {
                for (int j = 0; j < otherMaxColumn; j++) {
                    double sum = 0;

                    int currentRowColumnLength = this.data.get(i).size();
                    thisIteratorRow = 0;
                    otherIteratorColumn = 0;

                    var thisRow = this.data.get(i);
                    var otherColumn = otherData.get(j);

                    while (thisIteratorRow < currentRowColumnLength && otherIteratorColumn < currentRowColumnLength) {
                        int comparison = Integer.compare(thisRow.get(thisIteratorRow).column, otherColumn.get(otherIteratorColumn).row);
                        if (comparison == 0) {
                            sum += thisRow.get(thisIteratorRow).value * otherColumn.get(otherIteratorColumn).value;
                            thisIteratorRow++;
                            otherIteratorColumn++;
                        }
                        else if (comparison < 0) {
                            thisIteratorRow++;
                        }
                        else {
                            otherIteratorColumn++;
                        }
                    }

                    newData.add(new MatrixCellValue(thisRow.get(0).row, otherColumn.get(0).column, sum));
                }
            }

            return new Sparse(this.shape.rows, ((Sparse) other).shape.columns, newData.toArray(MatrixCellValue[]::new));
        }
        else {
            return super.times(other);
        }
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        var dataArr = this.getDataArray();
        var newDataArr = new ArrayList<MatrixCellValue>();

        for (var elem: dataArr) {
            newDataArr.add(new MatrixCellValue(elem.row, elem.column, elem.value * scalar));
        }

        return new Sparse(this.shape, newDataArr.toArray(MatrixCellValue[]::new));
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        if (other.getClass() == Constant.class) {
            if (other.get(0,0) == 0) {
                return this;
            }
        }
        else if (other.getClass() == Sparse.class) {
            int thisIterator = 0;
            int otherIterator = 0;

            var thisData = this.getDataArray();
            var otherData = ((Sparse) other).getDataArray();

            var newData = new ArrayList<MatrixCellValue>();

            while (thisIterator < thisData.length && otherIterator < otherData.length) {
                int compare = thisData[thisIterator].compareTo(otherData[otherIterator]);

                int row = thisData[thisIterator].row;
                int column = thisData[thisIterator].column;

                if (compare == 0) {
                    double value = thisData[thisIterator].value + otherData[otherIterator].value;

                    newData.add(new MatrixCellValue(row, column, value));
                    thisIterator++;
                    otherIterator++;
                }
                else if (compare < 0) {
                    double value = thisData[thisIterator].value;
                    newData.add(new MatrixCellValue(row, column, value));

                    thisIterator++;
                }
                else {
                    row = otherData[otherIterator].row;
                    column = otherData[otherIterator].column;
                    double value = otherData[otherIterator].value;
                    newData.add(new MatrixCellValue(row, column, value));

                    otherIterator++;
                }
            }

            for (int i = thisIterator; i < thisData.length; i++) {
                int row = thisData[i].row;
                int column = thisData[i].column;
                double value = thisData[i].value;

                newData.add(new MatrixCellValue(row, column, value));
            }
            for (int i = otherIterator; i < otherData.length; i++) {
                int row = otherData[i].row;
                int column = otherData[i].column;
                double value = otherData[i].value;

                newData.add(new MatrixCellValue(row, column, value));
            }

            return new Sparse(shape, newData.toArray(MatrixCellValue[]::new));
        }

        return super.plus(other);
    }

    @Override
    public double normOne() {
        double maximum = 0;

        for (var elem: this.getColumnRowList()) {
            double sum = 0;
            for (var cell: elem) {
                sum += Math.abs(cell.value);
            }

            maximum = Math.max(maximum, sum);
        }

        return maximum;
    }

    @Override
    public double normInfinity() {
        double maximum = 0;

        for (var elem: this.data) {
            double sum = 0;
            for (var cell: elem) {
                sum += Math.abs(cell.value);
            }

            maximum = Math.max(maximum, sum);
        }

        return maximum;
    }

    @Override
    public double frobeniusNorm() {
        double sum = 0;

        for (var elem: this.data) {
            for (var cell: elem) {
                sum += cell.value * cell.value;
            }
        }

        return Math.sqrt(sum);
    }

    private String addZeroRow(int amount) {
        return "0 ... 0\n".repeat(Math.max(0, amount));
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append(shape.toString());

        int lastRow = -1;

        for (var elem: data) {
            if (elem.get(0).row - 1 != lastRow) {
                sb.append(addZeroRow(elem.get(0).row - lastRow + 1));
            }
            int lastColumn = -1;
            for (int i = 0; i < elem.size(); i++) {
                if (elem.get(i).column - lastColumn > 3) {
                    sb.append("0 ... 0 ");
                }
                else {
                    sb.append("0 ".repeat(elem.get(i).column - lastColumn - 1));
                }


                sb.append(elem.get(i).toString()).append(" ");

                if (i == elem.size() - 1) {
                    if (elem.get(i).column < shape.columns - 3) {
                        sb.append("0 ... 0 ");
                    }
                    else {
                        sb.append("0 ".repeat(shape.columns - elem.get(i).column - 1));
                    }
                }

                lastColumn = elem.get(i).column;
            }
            lastRow = elem.get(0).row;
            sb.append("\n");
        }

        if (lastRow != shape.rows - 1) {
            sb.append(addZeroRow(shape.rows - lastRow - 1));
        }

        return sb.toString();
    }
}
