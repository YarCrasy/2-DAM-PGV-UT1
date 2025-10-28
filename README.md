# 2-DAM-PGV-UT1

Small Java project that processes text files under `./datos/` using a parent/child
workflow: the Parent discovers files and launches a Child Java process for each file.
Children write lowercase copies and per-line vowel counts into `./resultados/`.

## Project structure

- `src/` - Java sources: `Parent`, `Child`, `Utils` (and a small `RunParent` helper).
- `datos/` - input text files used by the application.
- `resultados/` - output folder created by the program. Contains `minusculas/`, `vocales/` and `parent.res`.

## Requirements

- Java JDK 8+ installed and available on PATH (`javac` and `java` commands).
- Works on Windows (cmd.exe) — commands below are targeted for cmd.

## Quick build (Windows cmd)

Open a command prompt in the project root (where `src\` is located) and run:

```cmd
javac -d out src\*.java
```

This compiles the sources into the `out` directory.

## Run

There are two primary ways to run parts of the project:

1. Run a single `Child` on a file (useful for testing):

```cmd
java -cp out Child datos\datos1.txt
```

This will create files like `resultados\minusculas\minusculas-1.res` and
`resultados\vocales\vocales-1.res` (file numbering is derived from the input
filename using `Utils.getFileNum`).

2. Run the whole parent workflow that discovers all files and spawns children:

A small helper class `RunParent` is included to delegate to the non-standard
`Parent.main()` method (which does not accept `String[] args`). Use:

```cmd
java -cp out RunParent
```

Notes:
- `Parent` spawns a `Child` process for each file it finds under `./datos/`.
- Results are aggregated and a parent summary `resultados\parent.res` is written.

## Generate Javadoc (Windows cmd)

To generate HTML Javadoc for the project (including non-public members):

```cmd
javadoc -d docs -encoding UTF-8 -charset UTF-8 -private -author -version -sourcepath src -subpackages .
```

This produces the documentation under `docs\index.html`.

If you prefer IntelliJ IDEA:
- Tools > Generate JavaDoc...
- Output directory: `docs`
- Add `-private -author -version -encoding UTF-8` to "Other command line arguments"
- Click OK and open `docs\index.html` when finished.

## Notes and troubleshooting

- If `java` cannot find `Child` when `Parent` spawns it, ensure your current
  working directory and the `java.class.path` contain the compiled classes or
  the project JAR.
- If you modify `Parent` to a standard `public static void main(String[] args)`,
  you can run `Parent` directly with `java -cp out Parent`.

## License

No license specified.
