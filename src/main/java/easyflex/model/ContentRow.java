/// Represents a row that contains user-facing cells.
package easyflex.model;

import java.util.List;

/// Stores the cells that belong to one content row.
/// @param cells the cells in display order
public record ContentRow(List<Cell> cells) implements TableRow {

    public ContentRow {
        cells = List.copyOf(cells);
    }
}
