/// Renders table layouts using Unicode box drawing characters.
package easyflex.render;

import easyflex.model.Cell;
import easyflex.model.ContentRow;
import easyflex.model.LineStyle;
import easyflex.model.SeparatorRow;
import easyflex.model.TableRow;

import java.util.ArrayList;
import java.util.List;

/// Converts a table layout into a Unicode text table.
public final class UnicodeTableRenderer {
    private final BoxDrawingResolver boxDrawingResolver = new BoxDrawingResolver();

    public String render(TableLayout layout) {
        List<String> renderedRows = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < layout.rowCount(); rowIndex++) {
            TableRow row = layout.row(rowIndex);
            if (row instanceof ContentRow contentRow) {
                renderedRows.add(renderContentRow(layout, contentRow));
            } else if (row instanceof SeparatorRow separatorRow) {
                String renderedSeparator = renderSeparatorRow(layout, rowIndex, separatorRow);
                if (!renderedSeparator.isEmpty()) {
                    renderedRows.add(renderedSeparator);
                }
            }
        }

        return String.join("\n", renderedRows);
    }

    private String renderContentRow(TableLayout layout, ContentRow row) {
        StringBuilder builder = new StringBuilder();
        List<Cell> cells = row.cells();

        for (int column = 0; column < cells.size(); column++) {
            builder.append(verticalBorder(row, column));
            builder.append(renderCell(cells.get(column), layout.columnWidth(column)));
        }

        if (!cells.isEmpty()) {
            builder.append(verticalBorder(row, cells.size()));
        }

        return builder.toString();
    }

    private String renderSeparatorRow(TableLayout layout, int rowIndex, SeparatorRow row) {
        if (layout.maxColumns() == 0) {
            return "";
        }

        ContentRow contentAbove = immediateContentRow(layout, rowIndex - 1);
        ContentRow contentBelow = immediateContentRow(layout, rowIndex + 1);
        StringBuilder builder = new StringBuilder();

        for (int boundary = 0; boundary <= layout.maxColumns(); boundary++) {
            int leftWeight = boundary == 0 ? 0 : horizontalWeight(row, contentAbove, contentBelow, boundary - 1);
            int rightWeight = boundary == layout.maxColumns() ? 0 : horizontalWeight(row, contentAbove, contentBelow, boundary);
            int upWeight = verticalWeight(contentAbove, boundary);
            int downWeight = verticalWeight(contentBelow, boundary);

            builder.append(boxDrawingResolver.resolve(upWeight, downWeight, leftWeight, rightWeight));

            if (boundary < layout.maxColumns()) {
                char horizontalGlyph = rightWeight == LineStyle.THICK.weight() ? '━' : '─';
                builder.append(String.valueOf(horizontalGlyph).repeat(layout.columnWidth(boundary) + 2));
            }
        }

        return builder.toString();
    }

    private String renderCell(Cell cell, int width) {
        int padding = Math.max(0, width - cell.contentWidth());
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;

        return " " + " ".repeat(leftPadding) + cell.content() + " ".repeat(rightPadding) + " ";
    }

    private char verticalBorder(ContentRow row, int boundary) {
        return verticalWeight(row, boundary) == LineStyle.THICK.weight() ? '┃' : '│';
    }

    private ContentRow immediateContentRow(TableLayout layout, int rowIndex) {
        if (rowIndex < 0 || rowIndex >= layout.rowCount()) {
            return null;
        }

        TableRow row = layout.row(rowIndex);
        return row instanceof ContentRow contentRow ? contentRow : null;
    }

    private int horizontalWeight(SeparatorRow row, ContentRow contentAbove, ContentRow contentBelow, int column) {
        int weight = row.lineStyle().weight();
        if (isThick(contentAbove, column) || isThick(contentBelow, column)) {
            return LineStyle.THICK.weight();
        }
        return weight;
    }

    private int verticalWeight(ContentRow row, int boundary) {
        if (row == null || boundary < 0) {
            return 0;
        }

        List<Cell> cells = row.cells();
        if (cells.isEmpty() || boundary > cells.size()) {
            return 0;
        }

        if (boundary == 0) {
            return cellWeight(cells.get(0));
        }
        if (boundary == cells.size()) {
            return cellWeight(cells.get(cells.size() - 1));
        }
        return Math.max(cellWeight(cells.get(boundary - 1)), cellWeight(cells.get(boundary)));
    }

    private boolean isThick(ContentRow row, int column) {
        return row != null && column >= 0 && column < row.cells().size() && row.cells().get(column).usesThickBorders();
    }

    private int cellWeight(Cell cell) {
        return cell.lineStyle().weight();
    }
}
