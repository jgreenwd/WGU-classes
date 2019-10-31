# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Graph:
    def __init__(self):
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
        self.vertices.add(vertex)

    def get_vertex(self, vertex):
        for item in list(self.vertices):
            if item == vertex:
                return item
        return None

    def generate_edges(self, index_source, weight_source):
        self.indices = [None] * len(index_source)
        for index, line in enumerate(index_source):
            self.indices[index] = line

        self.weights = [[]] * len(weight_source)
        for index, line in enumerate(weight_source):
            self.weights[index] = [float(i) for i in line.split('\t')]

        i = 0
        while i < len(self.vertices):
            tmp = list(self.vertices)
            for j, loc in enumerate(self.vertices):
                if i == j:
                    continue
                # find index for both graph vertices
                index_1 = self._get_index(tmp[i])
                index_2 = self._get_index(loc)
                # add edge & weight given those two vertices
                tmp[i].add_edge(loc, self._get_weight(index_1, index_2))
            i += 1

    def _get_weight(self, vert1, vert2):
        """ Return float found at intersection of both vertices.

            matrix = 2D list of floats
            vert1 = int index of first axis
            vert2 = int index of second axis
        """
        low, high = sorted([vert1, vert2])
        return self.weights[high][low]

    def _get_index(self, location):
        """ Return index of target in list or -1.

            indices = ordinal list of address names
            location = target search value
        """
        index = 0
        search = location.address + " " + location.zip
        for item in self.indices:
            if search == item:
                return index
            index += 1
        return -1
