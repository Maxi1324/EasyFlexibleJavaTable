package easyflex;

import easyflex.model.Cell;
import easyflex.model.ContentRow;
import easyflex.model.LineStyle;
import easyflex.model.SeparatorRow;
import easyflex.model.Table;
import easyflex.model.TableRow;
import easyflex.render.UnicodeTableRenderer;
import easyflex.render.TableLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EasyFlexibleTable {
    private final List<TableRow> rows = new ArrayList<>();
    private List<Cell> currentCells = new ArrayList<>();

    /// addCell
    ///
    /// Adds a regular cell.
    /// @param content the cell content
    /// @return this builder
    public EasyFlexibleTable addCell(String content) {
        return addCell(content, false);
    }

    /// addCell
    ///
    /// Adds a cell and optionally emphasizes its borders.
    /// @param content the cell content
    /// @param thick whether thick borders should be used
    /// @return this builder
    public EasyFlexibleTable addCell(String content, boolean thick) {
        currentCells.add(new Cell(content, thick));
        return this;
    }

    /// addHorizontalLine
    ///
    /// Adds a horizontal separator row.
    /// @return this builder
    public EasyFlexibleTable addHorizontalLine() {
        finishCurrentRow();
        rows.add(new SeparatorRow(LineStyle.THIN));
        return this;
    }

    /// endRow
    ///
    /// Finishes the current content row.
    /// @return this builder
    public EasyFlexibleTable endRow() {
        finishCurrentRow();
        return this;
    }

    /// addRowEndWithThinLines
    ///
    /// Finishes a row and optionally adds a separator row.
    /// @param addSeparator whether to add a separator row
    /// @return this builder
    public EasyFlexibleTable addRowEndWithThinLines(boolean addSeparator) {
        finishCurrentRow();
        if (addSeparator) {
            rows.add(new SeparatorRow(LineStyle.THIN));
        }
        return this;
    }

    /// addHeader
    ///
    /// Adds a thick header row wrapped by separators.
    /// @param headers the header labels
    /// @return this builder
    public EasyFlexibleTable addHeader(String... headers) {
        addHorizontalLine();
        for (String header : headers) {
            addCell(header, true);
        }
        endRow();
        addHorizontalLine();
        return this;
    }

    /// insertColumn
    ///
    /// Inserts a standard column into every content row.
    /// @param index the zero-based target column index
    /// @param contents one cell value per content row
    /// @return this builder
    public EasyFlexibleTable insertColumn(int index, List<String> contents) {
        Objects.requireNonNull(contents, "contents");
        finishCurrentRow();

        int contentRowCount = 0;
        int maxColumns = 0;

        for (TableRow row : rows) {
            if (row instanceof ContentRow contentRow) {
                contentRowCount++;
                maxColumns = Math.max(maxColumns, contentRow.cells().size());
            }
        }

        if (index < 0 || index > maxColumns) {
            throw new IndexOutOfBoundsException("Column index " + index + " must be between 0 and " + maxColumns + '.');
        }
        if (contents.size() != contentRowCount) {
            throw new IllegalArgumentException("Expected " + contentRowCount + " column values but got " + contents.size() + '.');
        }

        int contentIndex = 0;

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            TableRow row = rows.get(rowIndex);
            if (row instanceof ContentRow contentRow) {
                List<Cell> cells = new ArrayList<>(contentRow.cells());
                while (cells.size() < index) {
                    cells.add(new Cell(""));
                }
                cells.add(index, new Cell(contents.get(contentIndex)));
                rows.set(rowIndex, new ContentRow(cells));
                contentIndex++;
            }
        }

        return this;
    }

    /// build
    ///
    /// Builds the semantic table model.
    /// @return the immutable table model
    public Table build() {
        finishCurrentRow();
        return new Table(rows);
    }

    /// render
    ///
    /// Renders the table directly with the Unicode table renderer.
    /// @return the rendered table
    public String render() {
        UnicodeTableRenderer renderer = new UnicodeTableRenderer();
        return renderer.render(TableLayout.from(build()));
    }

    private void finishCurrentRow() {
        if (!currentCells.isEmpty()) {
            rows.add(new ContentRow(currentCells));
            currentCells = new ArrayList<>();
        }
    }
}
