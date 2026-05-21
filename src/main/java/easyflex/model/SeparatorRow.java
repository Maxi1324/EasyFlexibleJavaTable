/// Represents a horizontal separator between content rows.
package easyflex.model;

/// Stores the base style for a rendered separator row.
/// @param lineStyle the default separator line style
public record SeparatorRow(LineStyle lineStyle) implements TableRow {
    public SeparatorRow {
        lineStyle = lineStyle == null ? LineStyle.THIN : lineStyle;
    }
}
