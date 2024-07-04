using DceLib.Dce300;

namespace DceLib.Tests;


public class Dce300
{
    [Fact]
    public void TestReaderTryParse ()
    {
        MemoryStream stream = new MemoryStream();
        StreamWriter writer = new StreamWriter(stream);
        writer.WriteLine("notanintiger,sdasd");
        writer.Flush();
        stream.Seek(0L,SeekOrigin.Begin);
        using Reader reader = new Reader(stream);
        Assert.Throws<InvalidOperationException>(() => {
            foreach (var i in reader)
            {
                
            }
        });
    }
    [Fact]
    public void TestReaderNoData ()
    {
        MemoryStream stream = new MemoryStream();
        StreamWriter writer = new StreamWriter(stream);
        writer.Flush();
        stream.Seek(0L,SeekOrigin.Begin);
        using Reader reader = new Reader(stream);
        Assert.Equal(0, reader.ToList().Count);
    }
    [Fact]
    public void TestLogicNormal ()
    {
        var votes = new []
        {
            new Vote(10,1),
            new Vote(11,2),
            new Vote(12,4),
            new Vote(13,7),
            new Vote(14,1),
            new Vote(15,2),
            new Vote(16,2),
            new Vote(17,1),
            new Vote(18,1),
            new Vote(19,1),
            new Vote(20,4)
        };
        Assert.Equal(new Result(1,2,4,false), new DceLib.Dce300.Dce300().TopCandidate(votes));
    }
    [Fact]
    public void TestLogicFraud ()
    {
        var votes = new []
        {
            new Vote(10,1),
            new Vote(11,2),
            new Vote(12,4),
            new Vote(13,7),
            new Vote(14,1),
            new Vote(15,2),
            new Vote(16,2),
            new Vote(17,1),
            new Vote(18,1),
            new Vote(10,1),
            new Vote(20,4)
        };
        Assert.Equal(new Result(1,2,4,true), new DceLib.Dce300.Dce300().TopCandidate(votes));
    }
    [Fact]
    public void TestLogicFraudAffectsVote ()
    {
        var votes = new []
        {
            new Vote(10,1),
            new Vote(11,2),
            new Vote(12,2),
            new Vote(13,2),
            new Vote(14,3),
            new Vote(15,3),
            new Vote(14,3),
            new Vote(14,3),
            new Vote(14,3),
        };
        Assert.Equal(new Result(2,3,1,true), new DceLib.Dce300.Dce300().TopCandidate(votes));
    }
}