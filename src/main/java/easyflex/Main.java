package easyflex;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        EasyFlexibleTable table = new EasyFlexibleTable();

        table.addHorizontalLine().endRow();
        table.addCell("H1", true).addCell("H2", true).addCell("H3", true).addCell("H4", true).addCell("H5", true).endRow();
        table.addHorizontalLine().endRow();

        for (int index = 1; index <= 4; index++) {
            table.addCell("L" + index, true);
            table.addCell("Data " + index + ".2");
            table.addCell("Data " + index + ".3");
            table.addCell("Data " + index + ".4");
            table.addCell("Data " + index + ".5");
            table.endRow();
        }
        table.addHorizontalLine();
        table.addCell("L" + 5, true);
        table.addCell("Data " + 5 + ".2");
        table.addCell("Data " + 5 + ".3");
        table.addCell("Data " + 5 + ".4");
        table.addCell("Data " + 5 + ".5");
        table.addHorizontalLine();

        System.out.println(table.render());
    }
}
