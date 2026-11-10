#!/usr/bin/env bash
# Compile and run the Cucumber suite with only a JDK and a folder of jars (no Maven, no Gradle).
# Works in Git Bash on Windows, and on Linux/macOS.
#
#   ./run-tests.sh                                   all scenarios except @wip
#   ./run-tests.sh -t "@intake"                      one app
#   ./run-tests.sh -t "@proposal and @agent" -Dheadless=true -Dbrowser=firefox
#   ./run-tests.sh --compile-only                    just check that it compiles
#   ./run-tests.sh --dry-run                         print the commands, run nothing
#
# Any -Dkey=value is passed to the JVM, so the same settings as before work (-Dheadless=true,
# -Dintake.base.url=..., -Dclient.password=..., -Daddress.seed=...).
#
# Environment:
#   LIB_DIR    folder holding ALL the jars (Selenium + its dependencies, Cucumber, JUnit API...). Default: ./lib
#   JAVA_HOME  JDK to use. If unset, the IT-deployed Temurin below is used when present, else whatever is on PATH.

set -euo pipefail

cd "$(dirname "${BASH_SOURCE[0]}")"

TAGS="not @wip"
COMPILE_ONLY=false
DRY_RUN=false
JVM_ARGS=()

while [[ $# -gt 0 ]]; do
  case "$1" in
    -t|--tags)      TAGS="$2"; shift 2 ;;
    -D*)            JVM_ARGS+=("$1"); shift ;;
    --compile-only) COMPILE_ONLY=true; shift ;;
    --dry-run)      DRY_RUN=true; shift ;;
    -h|--help)      sed -n '2,17p' "$0"; exit 0 ;;
    *) echo "Unknown option: $1 (try --help)" >&2; exit 2 ;;
  esac
done

# ------------------------------------------------------------------ platform helpers

case "$(uname -s)" in
  MINGW*|MSYS*|CYGWIN*) SEP=";" ;;
  *)                    SEP=":" ;;
esac

# java.exe on Windows needs C:\-style paths, not Git Bash's /c/... paths
native_path() {
  if command -v cygpath >/dev/null 2>&1; then cygpath -w "$1"; else printf '%s' "$1"; fi
}

# ------------------------------------------------------------------ find the JDK

has_jdk() { [[ -x "$1/bin/javac" || -x "$1/bin/javac.exe" ]]; }

JDK_DIR=""
if [[ -n "${JAVA_HOME:-}" ]]; then
  candidate="$(cygpath -u "$JAVA_HOME" 2>/dev/null || printf '%s' "$JAVA_HOME")"
  has_jdk "$candidate" && JDK_DIR="$candidate"
fi
if [[ -z "$JDK_DIR" ]]; then
  candidate="/c/Program Files/Eclipse Adoptium/jdk-25.0.5.8-hotspot"
  has_jdk "$candidate" && JDK_DIR="$candidate"
fi
if [[ -n "$JDK_DIR" ]]; then
  JAVAC="$JDK_DIR/bin/javac"
  JAVA="$JDK_DIR/bin/java"
else
  command -v javac >/dev/null 2>&1 || { echo "No JDK found. Set JAVA_HOME to the JDK folder." >&2; exit 1; }
  JAVAC="javac"
  JAVA="java"
fi

# ------------------------------------------------------------------ check the jars

LIB_DIR="${LIB_DIR:-lib}"
[[ -d "$LIB_DIR" ]] || { echo "Jar folder not found: $LIB_DIR  (set LIB_DIR to the folder that holds the jars)" >&2; exit 1; }

# Name prefixes of the jars the project needs directly. Anything these depend on must be in the folder too;
# a missing one shows up at run time as NoClassDefFoundError naming the class.
REQUIRED=(
  selenium-api selenium-remote-driver selenium-support selenium-chrome-driver
  cucumber-java cucumber-core cucumber-picocontainer picocontainer
  junit-jupiter-api junit-platform-commons opentest4j
)

missing=()
for name in "${REQUIRED[@]}"; do
  compgen -G "$LIB_DIR/$name-[0-9]*.jar" >/dev/null || missing+=("$name")
done
if (( ${#missing[@]} > 0 )); then
  echo "Missing jars in $LIB_DIR (looked for <name>-<version>.jar):" >&2
  printf '  %s\n' "${missing[@]}" >&2
  exit 1
fi

# ------------------------------------------------------------------ compile

OUT_DIR="build/classes"
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR" target

# The JUnit suite runner is not needed: Cucumber's own entry point runs the features.
find src/test/java -name '*.java' -not -path '*/runners/*' > build/sources.txt
[[ -s build/sources.txt ]] || { echo "No .java files found under src/test/java" >&2; exit 1; }

LIB_CP="$(native_path "$LIB_DIR")/*"
OUT_CP="$(native_path "$OUT_DIR")"

COMPILE_CMD=("$JAVAC" --release 17 -encoding UTF-8 -cp "$LIB_CP" -d "$OUT_CP" "@build/sources.txt")
RUN_CMD=("$JAVA" -Dcucumber.publish.quiet=true ${JVM_ARGS[@]+"${JVM_ARGS[@]}"}
         -cp "$OUT_CP${SEP}$LIB_CP" io.cucumber.core.cli.Main
         --glue com.example
         --plugin pretty
         --plugin html:target/cucumber-report.html
         --plugin json:target/cucumber.json
         --tags "$TAGS"
         classpath:features)

if $DRY_RUN; then
  echo "# compile"; printf '%q ' "${COMPILE_CMD[@]}"; echo
  echo "# copy resources: src/test/resources/. -> $OUT_DIR/"
  $COMPILE_ONLY || { echo "# run"; printf '%q ' "${RUN_CMD[@]}"; echo; }
  exit 0
fi

echo "Compiling..."
"${COMPILE_CMD[@]}"

# features, config.properties, ... must sit on the classpath next to the classes
cp -R src/test/resources/. "$OUT_DIR"/

if $COMPILE_ONLY; then
  echo "Compile OK"
  exit 0
fi

echo "Running scenarios with tags: $TAGS"
"${RUN_CMD[@]}"
