/// Resolves weighted border junctions to Unicode box drawing glyphs.
package easyflex.render;

import java.util.HashMap;
import java.util.Map;

/// Maps directional line weights to the corresponding Unicode junction glyph.
public final class BoxDrawingResolver {
    private static final Map<Integer, Character> GLYPHS = new HashMap<>();

    static {
        put(0, 1, 0, 1, '┌'); put(0, 1, 0, 2, '┍'); put(0, 2, 0, 1, '┎'); put(0, 2, 0, 2, '┏');
        put(0, 1, 1, 0, '┐'); put(0, 1, 2, 0, '┑'); put(0, 2, 1, 0, '┒'); put(0, 2, 2, 0, '┓');
        put(1, 0, 0, 1, '└'); put(1, 0, 0, 2, '┕'); put(2, 0, 0, 1, '┖'); put(2, 0, 0, 2, '┗');
        put(1, 0, 1, 0, '┘'); put(1, 0, 2, 0, '┙'); put(2, 0, 1, 0, '┚'); put(2, 0, 2, 0, '┛');

        put(0, 1, 1, 1, '┬'); put(0, 1, 2, 1, '┭'); put(0, 1, 1, 2, '┮'); put(0, 1, 2, 2, '┯');
        put(0, 2, 1, 1, '┰'); put(0, 2, 2, 1, '┱'); put(0, 2, 1, 2, '┲'); put(0, 2, 2, 2, '┳');

        put(1, 0, 1, 1, '┴'); put(1, 0, 2, 1, '┵'); put(1, 0, 1, 2, '┶'); put(1, 0, 2, 2, '┷');
        put(2, 0, 1, 1, '┸'); put(2, 0, 2, 1, '┹'); put(2, 0, 1, 2, '┺'); put(2, 0, 2, 2, '┻');

        put(1, 1, 0, 1, '├'); put(1, 1, 0, 2, '┝'); put(2, 1, 0, 1, '┞'); put(1, 2, 0, 1, '┟');
        put(2, 2, 0, 1, '┠'); put(2, 1, 0, 2, '┡'); put(1, 2, 0, 2, '┢'); put(2, 2, 0, 2, '┣');

        put(1, 1, 1, 0, '┤'); put(1, 1, 2, 0, '┥'); put(2, 1, 1, 0, '┦'); put(1, 2, 1, 0, '┧');
        put(2, 2, 1, 0, '┨'); put(2, 1, 2, 0, '┩'); put(1, 2, 2, 0, '┪'); put(2, 2, 2, 0, '┫');

        put(1, 1, 1, 1, '┼'); put(1, 1, 2, 1, '┽'); put(1, 1, 1, 2, '┾'); put(1, 1, 2, 2, '┿');
        put(2, 1, 1, 1, '╀'); put(1, 2, 1, 1, '╁'); put(2, 2, 1, 1, '╂'); put(2, 1, 2, 1, '╃');
        put(2, 1, 1, 2, '╄'); put(1, 2, 2, 1, '╅'); put(1, 2, 1, 2, '╆'); put(2, 1, 2, 2, '╇');
        put(1, 2, 2, 2, '╈'); put(2, 2, 2, 1, '╉'); put(2, 2, 1, 2, '╊'); put(2, 2, 2, 2, '╋');
    }

    /// resolve
    ///
    /// Resolves a junction character for the provided directional weights.
    /// @param up the upward line weight
    /// @param down the downward line weight
    /// @param left the leftward line weight
    /// @param right the rightward line weight
    /// @return the matching Unicode box drawing glyph
    public char resolve(int up, int down, int left, int right) {
        return GLYPHS.getOrDefault(key(up, down, left, right), ' ');
    }

    private static void put(int up, int down, int left, int right, char glyph) {
        GLYPHS.put(key(up, down, left, right), glyph);
    }

    private static int key(int up, int down, int left, int right) {
        return (up << 6) | (down << 4) | (left << 2) | right;
    }
}
