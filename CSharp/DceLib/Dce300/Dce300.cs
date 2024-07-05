namespace DceLib.Dce300;

using System.Collections;
using System.Collections.Generic;
using System.Net.Http.Headers;

public class Dce300
{
    readonly HashSet<int> voters = new HashSet<int>();
    readonly Dictionary<int, int> candidates = new Dictionary<int, int>();
    public Result TopCandidate(Stream stream){
        using var reader = new Reader(stream);
        return TopCandidate(reader);
    }
    public Result TopCandidate(IEnumerable<Vote> votes)
    {
        bool fraudDetected = false;
        foreach (var voter in votes)
        {
            if (voters.Contains(voter.voterID))
            {
                fraudDetected = true;
                continue;
            }
            else
            {
                voters.Add(voter.voterID);
            }

            if (candidates.ContainsKey(voter.candidateID))
            {
                candidates[voter.candidateID] = candidates[voter.candidateID] + 1; 
            }
            else
            {
                candidates.Add(voter.candidateID, 1);
            }
        }

        var topCandidates = candidates.OrderByDescending(c => c.Value).Take(3).ToList();

        while (topCandidates.Count < 3)
        {
            topCandidates.Add(new KeyValuePair<int, int>(0, 0));
        }


        return new Result(topCandidates[0].Key, topCandidates[1].Key, topCandidates[2].Key, fraudDetected);
    }

}

public class Reader : IEnumerable<Vote>, IDisposable
{
    private readonly Stream _stream;

    public Reader(Stream stream)
    {
        _stream = stream;
    }

    public void Dispose()
    {
        _stream.Dispose();
    }

    public IEnumerator<Vote> GetEnumerator() => new ReaderEnumerator(new StreamReader(_stream));

    IEnumerator IEnumerable.GetEnumerator() => GetEnumerator();
}

public class ReaderEnumerator : IEnumerator<Vote>
{
    private readonly TextReader _reader;
    private Vote _nextVote = new Vote(0,0);
    private bool _isEndOfFile = false;
    
    public ReaderEnumerator (TextReader reader)
    {
        _reader = reader;
    }
    public Vote Current => _isEndOfFile ? throw new IndexOutOfRangeException("end of file!!!!!!!") : _nextVote;

    object IEnumerator.Current => Current;

    public void Dispose() 
    {
        _reader.Dispose();
    }

    public bool MoveNext()
    {
        string? nextLine;
        if (_isEndOfFile) return false;
        nextLine = _reader.ReadLine();
        if (nextLine == null) 
        {
            _isEndOfFile = true;
            return false;
        }
        if (!Vote.TryParse(nextLine, out _nextVote)) throw new InvalidOperationException("odahosidsufu"); //replace exception
        return true;
    }

    public void Reset() {}
}