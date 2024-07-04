package com.rgbeans.dce300;

public record Vote(int voterID, int candidateID) {
    public static boolean tryParse(String text, Vote[] result) {
        result[0] = new Vote(-1, -1);

        String[] parts = text.split(",");

        if (parts.length != 2) {
            return false;
        }

        try {
            int voterID = Integer.parseInt(parts[0]);
            int candidateID = Integer.parseInt(parts[1]);
            result[0] = new Vote(voterID, candidateID);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}