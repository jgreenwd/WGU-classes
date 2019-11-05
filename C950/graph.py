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
        self.adjacency_list = []

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

    def _add_edge(self, vert1, vert2):
        """ Add an edge to the Graph() between vert1 and vert2.

        :param vert1: Vertex to be joined...
        :param vert2: ... Vertex joined to
        """
        # find indexes for both vertices in order to...
        index_1 = self._get_index(vert1)
        index_2 = self._get_index(vert2)
        # ...find the weight for this edge
        weight = self._get_weight(index_1, index_2)
        edge = Edge(vert1, vert2, weight)

        # pass reference to adjacency list
        vert1.add_edge(vert2, weight)
        vert2.add_edge(vert1, weight)

        # do not add self-loops
        if edge not in self.adjacency_list and weight > 0.0:
            self.adjacency_list.append(edge)

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
        self.indices = [line for line in index_source]
        for index, line in enumerate(index_source):
            self.indices[index] = line

        self.weights = [[]] * len(weight_source)
        for index, line in enumerate(weight_source):
            self.weights[index] = [float(i) for i in line.split('\t')]

        # is this possible non-quadratically?
        i = 0
        while i < len(self.vertices):
            tmp = list(self.vertices)
            for loc in self.vertices:
                self._add_edge(tmp[i], loc)
            i += 1


class Edge:
    # in lieu of traditional Node: node w/ 2 pointers
    # use 2 Nodes with 1 edge between them
    def __init__(self, vertex1, vertex2, weight):
        self.weight = weight
        self.prev_node = vertex1
        self.next_node = vertex2

    def __eq__(self, other):
        return (self.weight == other.weight and
                (self.prev_node == other.prev_node and self.next_node == other.next_node) or
                (self.prev_node == other.next_node and self.next_node == other.prev_node))

    def __hash__(self):
        return hash(str(self))
