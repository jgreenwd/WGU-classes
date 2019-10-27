# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Destination:
    def __init__(self, address, city, state, zipcode):
        self.address = address
        self.city = city
        self.state = state
        self.zip = zipcode
        self.neighbors = []
        self.packages = []

    def add_neighbor(self, neighbor):
        self.neighbors.append(neighbor)

    def add_package(self, package):
        self.packages.append(package)
        self.packages.sort()

    def __str__(self):
        return self.address + "\t" + self.city + "\t" + self.state + "\t" + self.zip

    def __eq__(self, other):
        return (self.address == other.address and
                self.city == other.city and
                self.state == other.state and
                self.zip == other.zip)

    def __lt__(self, other):
        return (self.address < other.address and
                self.city == other.city and
                self.state == other.state and
                self.zip == other.zip)

    def __hash__(self):
        return hash(str(self))
