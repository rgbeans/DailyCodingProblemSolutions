import unittest
from Dce548 import Dce548
from parameterized import parameterized, parameterized_class
import csv
import math
import os

def get_data():
        # get path of csv file
        path = os.path.join(os.path.dirname(os.path.dirname(os.path.dirname(__file__))), 'TestResources', 'Dce548.csv')
        # read csv file
        with open(path, 'r') as file:
            reader = csv.reader(file)
            # return the data in the csv file
            return [
                (row[0], int(row[1]), row[2].lower() == 'true')
                for row in reader
                if row and len(row) == 3
            ]

class Dce548Test(unittest.TestCase):    
    @parameterized.expand(get_data)    
    def test_time_to_angle(self, time, expected_angle, expected_to_throw):
        if expected_to_throw:
            with self.assertRaises(ValueError):
                Dce548.time_to_angle(time)
        else:
            angle = Dce548.time_to_angle(time)
            self.assertEqual(expected_angle, angle)

if __name__ == '__main__':
    unittest.main()