package com.rgbeans.dce300;

import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import java.util.stream.Collectors;

public class Dce300 {
    private final Set<Integer> voters = new HashSet<>();
    private final Map<Integer, Integer> candidates = new HashMap<>();

    public Result topCandidate(InputStream stream) throws Exception {
        try (Reader reader = new Reader(stream)) {
            return topCandidate(reader);
        }
    }

    public Result topCandidate(Iterable<Vote> votes) {
        boolean fraudDetected = false;
        for (Vote vote : votes) {
            if (!voters.add(vote.voterID())) { // add returns false if the set already contains the element
                fraudDetected = true;
            } else {
                candidates.merge(vote.candidateID(), 1, Integer::sum);
            }
        }

        var topCandidates = candidates.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        while (topCandidates.size() < 3) {
            topCandidates.add(new AbstractMap.SimpleEntry<>(0, 0));
        }

        return new Result(topCandidates.get(0).getKey(), topCandidates.get(1).getKey(), topCandidates.get(2).getKey(), fraudDetected);
    }
}