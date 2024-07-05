package com.rgbeans.dce300;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ReaderIterator implements Iterator<Vote> {
    private final BufferedReader reader;
    private Vote nextVote;
    private boolean isEndOfFile = false;
    private boolean isFirstCheck = true;

    public ReaderIterator(BufferedReader reader) {
        this.reader = reader;
    }

    private void moveNext() throws IOException {
        String nextLine = reader.readLine();
        if (nextLine == null) {
            isEndOfFile = true;
            nextVote = null;
        } else {
            Vote[] result = new Vote[1];
            if (Vote.tryParse(nextLine, result)) {
                nextVote = result[0];
            } else {
                throw new NumberFormatException("Invalid vote format");
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (isFirstCheck) {
            try {
                moveNext();
                isFirstCheck = false;
            } catch (IOException e) {
                throw new RuntimeException("Failed to read the first line", e);
            }
        }
        return nextVote != null;
    }

    @Override
    public Vote next() {
        if (!hasNext()) throw new NoSuchElementException("End of file reached or no more elements");
        Vote currentVote = nextVote;
        try {
            moveNext();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read next line", e);
        }
        return currentVote;
    }
}