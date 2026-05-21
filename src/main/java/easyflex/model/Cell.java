/// Represents a single semantic table cell.
package easyflex.model;

/// Stores the cell content together with its visual parameters.
/// @param content the textual cell content
/// @param fixedWidth optional minimum width for the content area
/// @param lineStyle border weight preferred by the cell
public record Cell(String content, Integer fixedWidth, LineStyle lineStyle) {

    public Cell {
        content = content == null ? "" : content;
        fixedWidth = fixedWidth != null && fixedWidth > 0 ? fixedWidth : null;
        lineStyle = lineStyle == null ? LineStyle.THIN : lineStyle;
    }

    /// Cell
    ///
    /// Creates a regular cell.
    /// @param content the cell content
    public Cell(String content) {
        this(content, null, LineStyle.THIN);
    }

    /// Cell
    ///
    /// Creates a cell with border emphasis.
    /// @param content the cell content
    /// @param thick whether thick borders should be used
    public Cell(String content, boolean thick) {
        this(content, null, thick ? LineStyle.THICK : LineStyle.THIN);
    }

    /// Cell
    ///
    /// Creates a cell with explicit width and border emphasis.
    /// @param content the cell content
    /// @param fixedWidth optional minimum width for the content area
    /// @param thick whether thick borders should be used
    public Cell(String content, Integer fixedWidth, boolean thick) {
        this(content, fixedWidth, thick ? LineStyle.THICK : LineStyle.THIN);
    }

    /// contentWidth
    ///
    /// Returns the width of the raw content.
    /// @return the content width
    public int contentWidth() {
        return content.length();
    }

    /// usesThickBorders
    ///
    /// Returns whether the cell requests thick borders.
    /// @return {@code true} when thick borders should be used
    public boolean usesThickBorders() {
        return lineStyle == LineStyle.THICK;
    }
}
