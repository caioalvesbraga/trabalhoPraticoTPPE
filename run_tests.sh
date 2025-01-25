#!/bin/bash

# Clean and compile
rm -rf bin/
mkdir bin/
javac -cp lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar -d bin app/*.java tst/*.java

# Run tests
java -cp bin:lib/junit-4.13.2.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore tst.AllTests

