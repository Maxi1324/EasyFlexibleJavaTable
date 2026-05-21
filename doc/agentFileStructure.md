# Agent File Structure - Easy Flexible Java Table

## Root (`EasyFlexibleJavaTable/`)
- `README.md`: Human-facing architecture overview with dependency directions and package responsibilities.
- `pom.xml`: Maven project descriptor and GitHub Packages deployment target.
- `.github/workflows/maven-publish.yml`: Builds on push and pull request, publishes from `main`.
- `doc/`: Documentation and agent memory.
- `src/main/java/`: Source code.
- `src/test/java/`: Unit and smoke tests.

## Source Layout
- `easyflex/`: Public facade layer.
  - `EasyFlexibleTable.java`: Public fluent API, direct builder logic, and direct render entry point.
- `easyflex/model/`: Domain model.
  - `Table.java`: Immutable semantic table root.
  - `TableRow.java`: Sealed row abstraction.
  - `ContentRow.java`: Row of cells.
  - `SeparatorRow.java`: Horizontal separator row.
  - `Cell.java`: Semantic cell object with inline width and border emphasis.
  - `LineStyle.java`: Supporting enum for border weight.
- `easyflex/render/`: Rendering pipeline.
  - `UnicodeTableRenderer.java`: Unicode box-drawing renderer.
- `easyflex/render/layout/`: Render preparation.
  - `TableLayout.java`: Computes and stores render layout metadata.
- `easyflex/render/boxdrawing/`: Glyph resolution.
  - `BoxDrawingResolver.java`: Maps line weights to Unicode junctions.
- `easyflex/examples/`: Manual demo entry point.
  - `Main.java`: Example usage.

## Tests
- `easyflex/EasyFlexibleTableSmokeTest.java`: Smoke tests for rendering, semantic structure, and column insertion.
