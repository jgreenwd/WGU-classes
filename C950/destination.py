# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from vertex import Vertex
from copy import copy


class Destination(Vertex):
    def __init__(self, address, city=None, state=None, zip=None):
        """ Create Destination Object.

        :param address: string of street address
        :param city: string of city
        :param state: string of state
        :param zip: integer of zip code
        """
        Vertex.__init__(self, address)
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self.packages = []

    def __str__(self):
        return self.address + "\t" + self.city + "\t" + self.state + "\t" + self.zip

    def __eq__(self, other):
        if other is not None:
            return (self.address == other.address and
                    self.zip == other.zip)

    def __lt__(self, other):
        return (self.address < other.address and
                self.zip == other.zip)

    def __hash__(self):
        return hash(str(self))

    def add_package(self, package):
        """ Append package to vertex's list of packages & sort list by deadline. """
        self.packages.append(package)
        self.packages.sort()

    def get_nearest_neighbor(self):
        """ Return List() of closest vertices. """
        min_key = min(self.adjacent, key=self.adjacent.get)

        # use min/max to order vertices for set() comparison
        return max(self, min_key), min(self, min_key), self.get_weight(min_key)
