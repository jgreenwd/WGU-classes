# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Vertex:
    def __init__(self, node, mass):
        self.node = node
        self.adjacent = {}
        self.mass = mass

    def __str__(self):
        return self.node

    def add_neighbor(self, neighbor, weight=0):
        self.adjacent[neighbor] = weight

    def get_connections(self):
        return self.adjacent.keys()

    def get_node(self):
        return self.node

    def get_weight(self, neighbor):
        return self.adjacent[neighbor]


class Graph:
    def __init__(self):
        self._index = 0
        self.vertices = set()

    def __contains__(self, vertex):
        return vertex in self.vertices

    def __len__(self):
        return len(self.vertices)

    def __iter__(self):
        return self

    def __next__(self):
        iterable = list(self.vertices)
        if self._index < len(iterable):
            result = iterable[self._index]
            self._index += 1
            return result
        raise StopIteration

    def add_vertex(self, vertex, mass=0):
        self.vertices.add(Vertex(vertex, mass))

    def get_vertex(self, vertex):
        for item in list(self.vertices):
            if item == vertex:
                return item
        return None
