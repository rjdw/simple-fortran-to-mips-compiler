# Simple Compiler to MIPS Assembly

This compiler takes a file input in a simple, fortran-like, 
language and compiles to MIPS assembly.

The langauge supports variable declaration, basic arithmetic
operations, procedural programming, writing/reading in terminal, 
for and while loops, if statements and boolean operators.

The MIPS compilation was written specifically for QTSPIM, the 
MIPS processor simulator.

## Examples

Examples for the possiblities of the language are included in 
the src/testfiles. These can be tested for compilation by 
using the src/parser/Tester.java file, where you can see an
input and output file are defined.

Examples for compiled MIPS assembly are included in the 
comp\_examples folder, there corresponding non-compiled code
for these examples live in the src/testfiles/compiling\_procedres\_test
folder.
