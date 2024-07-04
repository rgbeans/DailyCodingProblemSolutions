package com.rgbeans.dce300;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Reader implements Iterable<Vote>, AutoCloseable {
    private final InputStream stream;

    public Reader(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public void close() throws Exception {
        stream.close();
    }

    @Override
    public Iterator<Vote> iterator() {
        return new ReaderIterator(new BufferedReader(new InputStreamReader(stream)));
    }

    public List<Vote> toList() {
        List<Vote> votes = new ArrayList<>();
        for (Vote vote : this) {
            votes.add(vote);
        }
        return votes;
    }
}