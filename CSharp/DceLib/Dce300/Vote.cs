namespace DceLib.Dce300;

public record Vote (int voterID, int candidateID) {
    public static bool TryParse (string text, out Vote result)
    {
        result = new Vote(-1,-1);

        string[] parts = text.Split(',');

        if (parts.Length != 2)
        {
            return false;
        }

        if (!int.TryParse(parts[0], out int voterID))
        {
            return false;
        }

        if (!int.TryParse(parts[1], out int candidateID))
        {
            return false;
        }

        result = new Vote(voterID, candidateID);

        return true;
    }
}

public record Result (int topCandidate, int secondCandidate, int thirdCandidate, bool fraud);