/// Defines supported border weights for tables.
package easyflex.model;

/// Describes whether borders should be rendered using thin or thick glyphs.
public enum LineStyle {
    THIN(1),
    THICK(2);

    private final int weight;

    LineStyle(int weight) {
        this.weight = weight;
    }

    public int weight() {
        return weight;
    }
}
