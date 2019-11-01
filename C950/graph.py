# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Graph:
    def __init__(self):
        """ Create Graph Object. """
        self._index = 0
        self.vertices = set()
        self.indices = []
        self.weights = []

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
        self._index = 0
        raise StopIteration

    def add_vertex(self, vertex):
        """ Add vertex to Set() of Vertices. """
        self.vertices.add(vertex)

    def get_vertex(self, vertex):
        """ Return reference to vertex. """
        for item in list(self.vertices):
            if item == vertex:
                return item
        return None

    def generate_edges(self, index_source, weight_source):
        """ Add edges & weights to/from all Vertices within the Graph.

        :param index_source: ordered List() of Vertex() names & zips
        :param weight_source: ordered 2D-List() of Vertex() edge weights
        """
        self.indices = [None] * len(index_source)
        for index, line in enumerate(index_source):
            self.indices[index] = line

        self.weights = [[]] * len(weight_source)
        for index, line in enumerate(weight_source):
            self.weights[index] = [float(i) for i in line.split('\t')]

        i = 0
        while i < len(self.vertices):
            tmp = list(self.vertices)
            for loc in self.vertices:
                # find index for both graph vertices
                index_1 = self._get_index(tmp[i])
                index_2 = self._get_index(loc)
                # add edge & weight given those two vertices
                tmp[i].add_edge(loc, self._get_weight(index_1, index_2))
            i += 1

    def _get_weight(self, vert1, vert2):
        """ Return weight of edge between both vertices as float. """
        low, high = sorted([vert1, vert2])
        return self.weights[high][low]

    def _get_index(self, location):
        """ Return index of location in list or -1. """
        index = 0
        search = location.address + " " + location.zip
        for item in self.indices:
            if search == item:
                return index
            index += 1
        return -1
