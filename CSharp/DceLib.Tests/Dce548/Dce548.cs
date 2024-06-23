namespace DceLib.Tests;

public class Dce548
{

    [Theory]
    [MemberData(nameof(GetData))]
    public void Test1(string time, int expectedAngle, bool expectedToThrow)
    {
        var d = Environment.CurrentDirectory;
        if (expectedToThrow)
        {
            Assert.Throws<ArgumentException>(() => DceLib.Dce548.TimeToAngle(time));
            return;
        }
        int angle = DceLib.Dce548.TimeToAngle(time);
        Assert.Equal(expectedAngle, angle);
    }

    public static IEnumerable<object[]> GetData() =>
         File.ReadAllLines(Path.Combine(Environment.CurrentDirectory, "..", "..", "..", "..", "..", "TestResources", "Dce548.csv"))
            .Where(i => !string.IsNullOrWhiteSpace(i))
            .Select(i => i.Split(','))
            .Where(i => i.Length == 3)
            .Select(columns => new object[] { columns[0], int.Parse(columns[1]), bool.Parse(columns[2]) })
                  ;
}