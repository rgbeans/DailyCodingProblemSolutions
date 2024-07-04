from collections import defaultdict
from typing import List, Tuple, Any, Iterable
from io import TextIOWrapper, BytesIO

class Vote:
    def __init__(self, voter_id, candidate_id):
        self.voter_id = voter_id
        self.candidate_id = candidate_id

    @staticmethod
    def try_parse(text):
        parts = text.split(',')

        if len(parts) != 2:
            return False, None

        try:
            voter_id = int(parts[0])
            candidate_id = int(parts[1])
        except ValueError:
            return False, None

        return True, Vote(voter_id, candidate_id)


class Result:
    def __init__(self, first, second, third, fraud):
        self.first = first
        self.second = second
        self.third = third
        self.fraud = fraud

class Dce300:
    def __init__(self):
        self.voters = set()
        self.candidates = defaultdict(int)

    def top_candidate(self, stream: BytesIO) -> 'Result':
        reader = Reader(stream)
        return self.top_candidate_from_votes(reader)

    def top_candidate_from_votes(self, votes: Iterable[Vote]) -> 'Result':
        fraud_detected = False
        for vote in votes:
            if vote.voter_id in self.voters:
                fraud_detected = True
                continue
            else:
                self.voters.add(vote.voter_id)

            self.candidates[vote.candidate_id] += 1

        top_candidates = sorted(self.candidates.items(), key=lambda item: item[1], reverse=True)[:3]

        while len(top_candidates) < 3:
            top_candidates.append((0, 0))

        return Result(top_candidates[0][0], top_candidates[1][0], top_candidates[2][0], fraud_detected)

class Reader:
    def __init__(self, stream: BytesIO):
        self.stream = TextIOWrapper(stream, encoding='utf-8')

    def __iter__(self) -> Iterable[Vote]:
        return ReaderEnumerator(self.stream)

class ReaderEnumerator:
    def __init__(self, reader: TextIOWrapper):
        self.reader = reader
        self.next_vote = None
        self.is_end_of_file = False

    def __iter__(self) -> 'ReaderEnumerator':
        return self

    def __next__(self) -> Vote:
        if self.is_end_of_file:
            raise StopIteration("End of file")

        line = self.reader.readline()
        if not line:
            self.is_end_of_file = True
            raise StopIteration("End of file")

        success, vote = Vote.try_parse(line)
        if not success:
            raise ValueError("Invalid vote format")

        return vote

    def close(self):
        self.reader.close()