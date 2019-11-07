# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from edge import Edge


class Graph:
    def __init__(self):
        """ Create Graph Object. """
        self._index = 0
        self._vertices = set()
        self._adjacency_list = []
        self._indices = []
        self._weights = []

    def __contains__(self, vertex):
        return vertex in self._vertices

    def __len__(self):
        return len(self._vertices)

    def __iter__(self):
        return self

    def __next__(self):
        iterable = list(self._vertices)
        if self._index < len(iterable):
            result = iterable[self._index]
            self._index += 1
            return result
        self._index = 0
        raise StopIteration

    def _get_index(self, location):
        """ Return index of location in list or -1.

        :param location: Vertex object """
        index = 0
        search = location.address + " " + location.zip
        for item in self._indices:
            if search == item:
                return index
            index += 1
        return -1

    def _get_weight(self, vert1, vert2):
        """ Return weight of edge between both vertex indices as float.

        :param vert1: index of 1st vertex
        :param vert2: index of 2nd vertex"""
        low, high = sorted([vert1, vert2])
        return self._weights[high][low]

    def _add_edge(self, vert1, vert2):
        """ Add an edge to the Graph() between vert1 and vert2.

        :param vert1: Vertex to be joined...
        :param vert2: ... Vertex joined to
        """
        # find indexes for both vertices
        index_1 = self._get_index(vert1)
        index_2 = self._get_index(vert2)

        # do not add self-loops
        if index_1 == index_2:
            return

        # find the weight for this edge
        weight = self._get_weight(index_1, index_2)
        edge = Edge(vert1, vert2, weight)

        if edge not in self._adjacency_list:
            self._adjacency_list.append(edge)

    def _get_edge(self, edge):
        """ Return reference to Edge() object or None. """
        for candidate in self._adjacency_list:
            if candidate == edge:
                return candidate
        return None

    def _get_neighbors(self, vertex):
        """ Return Edge()s where Vertex() param appears.

        :param vertex: point of origin """
        return [edge for edge in self._adjacency_list if vertex in edge]

    def _get_nearest_neighbor(self, iterable, vertex):
        """ Return Vertex() with least weight respective to param.

        :param iterable: adjacency list of Edge()s
        :param vertex: point of origin"""
        minimum = 100

        neighbors = self._get_neighbors(vertex)
        output = []
        for edge in neighbors:
            if edge.next_node is not vertex:
                if not edge.next_node.visited:
                    output.append(edge)
            elif edge.prev_node is not vertex:
                if not edge.prev_node.visited:
                    output.append(edge)
        output = sorted(output, key=lambda x: x.weight)

        if output[0].prev_node == vertex:
            neighbor = output[0].next_node
        else:
            neighbor = output[0].prev_node
        return neighbor

    def add_vertex(self, vertex):
        """ Add vertex to Set() of Vertices.

        :param vertex: Vertex() object """
        self._vertices.add(vertex)

    def get_vertex(self, vertex):
        """ Return reference to vertex.

        :param vertex: Vertex() object w/ same address & zip """
        for candidate in list(self._vertices):
            if candidate == vertex:
                return candidate
        return None

    def del_vertex(self, vertex):
        """ Remove vertex from Set() of Vertices.

        :param vertex: Vertex() object """
        if vertex in self._vertices:
            self._vertices.remove(vertex)

    def generate_edges(self, index_source, weight_source):
        """ Add edges & weights to/from all Vertices within the Graph.

        :param index_source: ordered List() of Vertex() names & zips
        :param weight_source: ordered 2D-List() of Vertex() edge weights
        """
        self._indices = [line for line in index_source]
        for index, line in enumerate(index_source):
            self._indices[index] = line

        self._weights = [[]] * len(weight_source)
        for index, line in enumerate(weight_source):
            self._weights[index] = [float(i) for i in line.split('\t')]

        # is this possible non-quadratically?
        i = 0
        while i < len(self._vertices):
            tmp = list(self._vertices)
            for loc in self._vertices:
                self._add_edge(tmp[i], loc)
            i += 1
