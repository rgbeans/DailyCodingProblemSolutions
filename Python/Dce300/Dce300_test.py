import unittest
from io import BytesIO, StringIO
from Dce300 import Dce300, Vote, Result, Reader  # Make sure to import your classes correctly

class TestDce300(unittest.TestCase):
    
    def test_reader_try_parse(self):
        stream = BytesIO(b"notanintiger,sdasd\n")
        reader = Reader(stream)
        
        with self.assertRaises(ValueError):  # Changed exception to ValueError based on the Python implementation
            for _ in reader:
                pass

    def test_reader_no_data(self):
        stream = BytesIO(b"")
        reader = Reader(stream)
        self.assertEqual(len(list(reader)), 0)

    def test_logic_normal(self):
        votes = [
            Vote(10, 1),
            Vote(11, 2),
            Vote(12, 4),
            Vote(13, 7),
            Vote(14, 1),
            Vote(15, 2),
            Vote(16, 2),
            Vote(17, 1),
            Vote(18, 1),
            Vote(19, 1),
            Vote(20, 4)
        ]
        dce = Dce300()
        result = dce.top_candidate_from_votes(votes)
        self.assertEqual(result.first, 1)
        self.assertEqual(result.second, 2)
        self.assertEqual(result.third, 4)
        self.assertFalse(result.fraud)

    def test_logic_fraud(self):
        votes = [
            Vote(10, 1),
            Vote(11, 2),
            Vote(12, 4),
            Vote(13, 7),
            Vote(14, 1),
            Vote(15, 2),
            Vote(16, 2),
            Vote(17, 1),
            Vote(18, 1),
            Vote(10, 1),
            Vote(20, 4)
        ]
        dce = Dce300()
        result = dce.top_candidate_from_votes(votes)
        self.assertEqual(result.first, 1)
        self.assertEqual(result.second, 2)
        self.assertEqual(result.third, 4)
        self.assertTrue(result.fraud)

    def test_logic_fraud_affects_vote(self):
        votes = [
            Vote(10, 1),
            Vote(11, 2),
            Vote(12, 2),
            Vote(13, 2),
            Vote(14, 3),
            Vote(15, 3),
            Vote(14, 3),
            Vote(14, 3),
            Vote(14, 3),
        ]
        dce = Dce300()
        result = dce.top_candidate_from_votes(votes)
        self.assertEqual(result.first, 2)
        self.assertEqual(result.second, 3)
        self.assertEqual(result.third, 1)
        self.assertTrue(result.fraud)

if __name__ == '__main__':
    unittest.main()