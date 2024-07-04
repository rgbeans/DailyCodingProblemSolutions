package com.rgbeans.dce300;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class Dce300Test {
    @Test
    void testReaderTryParse() {
        String data = "notanintiger,sdasd";
        ByteArrayInputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        com.rgbeans.dce300.Reader reader = new com.rgbeans.dce300.Reader(stream);
        Assertions.assertThrows(NumberFormatException.class, () -> {
            for (Vote i : reader) {
            }
        }); 
    }

    @Test
    public void testReaderNoData() {
        ByteArrayInputStream stream = new ByteArrayInputStream(new byte[0]);
        Reader reader = new Reader(stream);
        var size = reader.toList().size();
        Assertions.assertEquals(0, size);
    }

    @Test
    public void testLogicNormal() {
        List<Vote> votes = List.of(
                new Vote(10, 1),
                new Vote(11, 2),
                new Vote(12, 4),
                new Vote(13, 7),
                new Vote(14, 1),
                new Vote(15, 2),
                new Vote(16, 2),
                new Vote(17, 1),
                new Vote(18, 1),
                new Vote(19, 1),
                new Vote(20, 4)
        );
        Assertions.assertEquals(new Result(1, 2, 4, false), new Dce300().topCandidate(votes));
    }

    @Test
    public void testLogicFraud() {
        List<Vote> votes = List.of(
                new Vote(10, 1),
                new Vote(11, 2),
                new Vote(12, 4),
                new Vote(13, 7),
                new Vote(14, 1),
                new Vote(15, 2),
                new Vote(16, 2),
                new Vote(17, 1),
                new Vote(18, 1),
                new Vote(10, 1),
                new Vote(20, 4)
        );
        Assertions.assertEquals(new Result(1, 2, 4, true), new Dce300().topCandidate(votes));
    }

    @Test
    public void testLogicFraudAffectsVote() {
        List<Vote> votes = List.of(
                new Vote(10, 1),
                new Vote(11, 2),
                new Vote(12, 2),
                new Vote(13, 2),
                new Vote(14, 3),
                new Vote(15, 3),
                new Vote(14, 3), 
                new Vote(14, 3), 
                new Vote(14, 3)  
        );
        var s = new Result(2, 3, 1, true);
        Assertions.assertEquals(s, new Dce300().topCandidate(votes));
    }
}
