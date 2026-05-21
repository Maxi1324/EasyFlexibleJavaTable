/// Represents the semantic structure of a table.
package easyflex.model;

import java.util.List;

/// Stores the ordered semantic rows of a table.
/// @param rows the ordered rows of the table
public record Table(List<TableRow> rows) {

public Table {
        rows = List.copyOf(rows);
    }

    /// maxColumns
    ///
    /// Returns the maximum number of cells contained in a content row.
    /// @return the largest cell count of any content row
    public int maxColumns() {
        int maxColumns = 0;
        for (TableRow row : rows) {
            if (row instanceof ContentRow contentRow) {
                maxColumns = Math.max(maxColumns, contentRow.cells().size());
            }
        }
        return maxColumns;
    }
}
