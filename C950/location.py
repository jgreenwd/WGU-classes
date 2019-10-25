# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Location:
    def __init__(self, address, city, state, zipcode):
        self.address = address
        self.city = city
        self.state = state
        self.zip = zipcode
        self.neighbors = []
        self.deliveries = []

    def add_neighbor(self, neighbor):
        self.neighbors.append(neighbor)

    def add_package(self, package):
        self.deliveries.append(package)

    def __str__(self):
        return self.address + "\t" + self.city + "\t" + self.state + "\t" + self.zip
