# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from vertex import Vertex


class Destination(Vertex):
    def __init__(self, address, city=None, state=None, zip=None):
        Vertex.__init__(self, address)
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self.packages = []
        self.visited = False

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
        """ Append package to self's list of packages & sort list by deadline """
        self.packages.append(package)
        self.packages.sort()

    def is_visited(self):
        """ Return self.visited as Bool """
        return self.visited

    def visit(self):
        """ Set self.visited to True (False by default) """
        self.visited = True
