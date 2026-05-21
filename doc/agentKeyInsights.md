# Agent Key Insights - Easy Flexible Java Table

## Discoveries
- Project is a Maven project using Java 21.
- The public API started token-driven, but the maintainable architecture is a semantic table model with a separate layout and rendering pipeline.
- `EasyFlexibleTable` can remain the stable public entry point even when it owns the mutable builder logic directly.
- Column insertion is best implemented after closing the current row, because the builder logic needs the full mutable semantic row list.
- The repository root is now intended to be the Maven project directory itself.
- `TableData` and `TableRenderer` were unnecessary intermediate abstractions; the code is clearer when `EasyFlexibleTable` builds `Table` and directly invokes `UnicodeTableRenderer`.
- `Alignment` was dead weight because the renderer supported it, but the public API could only ever create centered cells.
- `CellStyle` was also light enough to merge into `Cell`, which makes the model flatter and easier to read.

## Lessons Learned
- Preserving the fluent API while replacing the internals is feasible when a semantic model is introduced first.
- Column width calculation belongs in a dedicated render-preparation step, not in the renderer or builder; for this project the step is simple enough to live as `TableLayout.from(...)` instead of a separate engine class.
- Unicode box drawing resolution is a separate concern and should be isolated from domain objects.
- Keeping the public fluent API is enough compatibility for now; carrying the old token layer forward only adds architectural noise.
- For `insertColumn`, shorter rows should be padded with empty standard cells up to the insertion point so the global column index stays stable across uneven rows.
- A small `README.md` makes architectural boundaries more concrete than class-level comments alone.
- In this project, a dedicated `TableBuilder` class did not add enough value; keeping the mutable build logic directly in `EasyFlexibleTable` is simpler and clearer.
- If a style dimension cannot be reached or configured through the public API, it usually should not exist in the model yet.
- If a style object only wraps one or two scalar values with no real independent behaviour, it is often better to inline it into the owning model object.
