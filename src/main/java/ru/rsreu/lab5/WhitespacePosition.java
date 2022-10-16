package ru.rsreu.lab5;

public class WhitespacePosition {

    private final String fileName;
    private final int lineNumber;
    private final int whitespacePosition;

    public WhitespacePosition(String fileName, int lineNumber, int whitespacePosition) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.whitespacePosition = whitespacePosition;
    }

    public String getFileName() {
        return this.fileName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getWhitespacePosition() {
        return this.whitespacePosition;
    }

    @Override
    public String toString() {
        return "Whitespace { " +
                fileName +
                ", line = " + lineNumber +
                ", position = " + whitespacePosition +
                "}";
    }
}
