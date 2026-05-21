/// Stores the computed layout metadata for rendering a table.
package easyflex.render;

import easyflex.model.Cell;
import easyflex.model.ContentRow;
import easyflex.model.Table;
import easyflex.model.TableRow;

import java.util.ArrayList;
import java.util.List;

/// Provides the semantic table together with computed column widths.
public final class TableLayout {
    private final Table table;
    private final List<Integer> columnWidths;
    private final int maxColumns;

    public TableLayout(Table table, List<Integer> columnWidths) {
        this.table = table;
        this.columnWidths = List.copyOf(columnWidths);
        this.maxColumns = table.maxColumns();
    }

    /// from
    ///
    /// Computes the layout metadata required for rendering.
    /// @param table the semantic table
    /// @return the computed layout
    public static TableLayout from(Table table) {
        int maxColumns = table.maxColumns();
        List<Integer> columnWidths = new ArrayList<>(maxColumns);
        for (int index = 0; index < maxColumns; index++) {
            columnWidths.add(0);
        }

        int globalFlexibleWidth = 0;

        for (TableRow row : table.rows()) {
            if (row instanceof ContentRow contentRow) {
                globalFlexibleWidth = updateColumnWidths(columnWidths, contentRow, globalFlexibleWidth);
            }
        }

        for (int column = 0; column < columnWidths.size(); column++) {
            if (columnWidths.get(column) == 0) {
                columnWidths.set(column, globalFlexibleWidth);
            }
        }

        return new TableLayout(table, columnWidths);
    }

    /// table
    ///
    /// Returns the semantic table.
    /// @return the table model
    public Table table() {
        return table;
    }

    /// rowCount
    ///
    /// Returns the number of rows in the table.
    /// @return the row count
    public int rowCount() {
        return table.rows().size();
    }

    /// row
    ///
    /// Returns the row at the given index.
    /// @param index the row index
    /// @return the row at the index
    public TableRow row(int index) {
        return table.rows().get(index);
    }

    /// columnWidth
    ///
    /// Returns the computed width of a column.
    /// @param index the column index
    /// @return the column width or {@code 0} when absent
    public int columnWidth(int index) {
        return index >= 0 && index < columnWidths.size() ? columnWidths.get(index) : 0;
    }

    /// maxColumns
    ///
    /// Returns the maximum number of content columns.
    /// @return the maximum content column count
    public int maxColumns() {
        return maxColumns;
    }

    private static int updateColumnWidths(List<Integer> columnWidths, ContentRow row, int globalFlexibleWidth) {
        List<Cell> cells = row.cells();
        for (int column = 0; column < cells.size(); column++) {
            Cell cell = cells.get(column);
            if (cell.fixedWidth() != null) {
                columnWidths.set(column, Math.max(columnWidths.get(column), cell.fixedWidth()));
            } else {
                globalFlexibleWidth = Math.max(globalFlexibleWidth, cell.contentWidth());
            }
        }
        return globalFlexibleWidth;
    }
}
