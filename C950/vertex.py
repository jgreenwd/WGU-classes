# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from edge import Edge


class Vertex:
    def __init__(self, node):
        """ Create Vertex Object.

        :param node: object placed at Vertex """
        self.id = node
        self.adjacent = {}

    def __str__(self):
        return str(self.id)

    def add_edge(self, neighbor, weight=0):
        """ Add weighted edge from self to neighbor. """
        self.adjacent[neighbor] = weight

    def get_connections(self):
        """ Return dict keys of all connections to this Vertex. """
        return self.adjacent.keys()

    def get_weight(self, neighbor):
        """ Return weight of edge from self to neighbor as float. """

        if self == neighbor:
            return 0.0
        return self.adjacent[neighbor]

    def _get_nearest_neighbor(self):
        """ Return Tuple() of least weighted edge. """
        min_key = min(self.adjacent, key=self.adjacent.get)
        result = sorted([self, min_key], key=lambda x: x.address)
        return Edge(result[0], result[1], self.get_weight(min_key))
