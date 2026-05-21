# Agent Memory - Easy Flexible Java Table

## Project Overview
A Java library for building and rendering flexible text tables. The repository root and Maven project root are now the same directory: `EasyFlexibleJavaTable/`.

## Core Directives
- Follow SoC: keep the fluent facade thin and push structure into `model`, width calculation into `layout`, and glyph generation into `render`.
- Keep files short and descriptive.
- Adhere to Java 21 standards.

## Current Focus
- `EasyFlexibleTable` now owns the fluent builder logic directly instead of delegating to a separate `TableBuilder`.
- The fluent API supports `insertColumn(int index, List<String> contents)` for semantic column insertion across existing content rows.
- The semantic core uses `Table`, `ContentRow`, `SeparatorRow`, `Cell`, and `LineStyle`; `CellStyle` and `Alignment` were removed because they did not justify separate abstraction layers.
- `EasyFlexibleTable.render()` now uses `TableLayout.from(...)` plus `UnicodeTableRenderer` directly; `TableData`, `TableRenderer`, and `TableLayoutEngine` no longer exist.
- Architecture responsibilities and dependency direction are documented in `README.md`.
- The repository root contains the Maven project, GitHub workflow, and agent memory files together.
