# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

# TODO: refactor get nearest neighbor - with sorted list, can just return
#       1st element that isn't visited, assuming no ties; also iterable is broken

from edge import Edge
from location import Location


class Graph:
    def __init__(self, indices, weights):
        """ Create Graph Object. """
        self.vertices = set()
        self.adjacency_list = set()
        self._index = 0
        self._indices = indices
        self._weights = weights

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

# -------------------- Vertex Manipulation --------------------
    def add_vertex(self, vertex):
        """ Add vertex to Set of Vertices.

        :param vertex: Vertex """
        self.vertices.add(vertex)

        for vertex2 in self.vertices:
            self._add_edge(vertex, vertex2)

    def get_vertex(self, vertex):
        """ Return reference to vertex.

        # :param vertex: Vertex  """
        for candidate in self.vertices:
            if candidate == vertex:
                return candidate
        return None

    def del_vertex(self, vertex):
        """ Remove vertex from Set of Vertices.

        :param vertex: Vertex """
        if vertex in self.vertices:
            # can not manipulate Set() while in use; reference a copy
            tmp = self.adjacency_list.copy()
            for edge in tmp:
                if vertex in edge:
                    self.adjacency_list.remove(edge)
            self.vertices.remove(vertex)


# --------------------  Edge Manipulation  --------------------
    def _add_edge(self, vertex1, vertex2):
        """ Add an edge to the Graph between vertexA and vertexB.

        :param vertex1: Vertex
        :param vertex2: Vertex """
        # find indexes for both vertices
        index_1 = self._get_index(vertex1)
        index_2 = self._get_index(vertex2)

        # do not add self-loops
        if index_1 == index_2:
            return

        # find the weight for this edge
        weight = self._get_weight(index_1, index_2)
        edge = Edge(vertex1, vertex2, weight)

        if edge not in self.adjacency_list:
            self.adjacency_list.add(edge)

    def _get_edge(self, edge):
        """ Return reference to Edge object or None.

        :param edge: Edge """
        for candidate in self.adjacency_list:
            if candidate == edge:
                return candidate
        return None

    def _del_edge(self, edge):
        """ Remove edge from List of Edges
        
        :param edge: Edge """
        if edge in self.adjacency_list:
            self.adjacency_list.remove(edge)

# --------------------  Support Functions  --------------------
    def _get_index(self, vertex):
        """ Return index of location in list or -1.

        :param vertex: Vertex """
        if len(self._indices) == 0:
            raise UnboundLocalError

        index = 0
        search = vertex.address + " " + vertex.zip
        for item in self._indices:
            if search == item:
                return index
            index += 1
        return -1

    def _get_weight(self, index1, index2):
        """ Return weight of edge between both indices as float.

        :param index1: integer
        :param index2: integer """
        low, high = sorted([index1, index2])
        return self._weights[high][low]

    def _get_neighbors(self, vertex):
        """ Return Edges connected to vertex.

        :param vertex: Vertex """
        return [edge for edge in self.adjacency_list if vertex in edge]

    def _get_nearest_neighbor(self, vertex):
        """ Return Vertex with least weight respective to vertex.

        :param vertex: Vertex
        :param iterable: List """

        # get list of adjacent edges, filter by visited, sorted by weight
        candidates = filter(lambda x: self._get_other_vertex(x, vertex), self.adjacency_list)
        neighbors = sorted(list(candidates), key=lambda x: float(x.weight))

        if len(neighbors) > 0:
            if neighbors[0].prev_node == vertex:
                return neighbors[0].next_node
            else:
                return neighbors[0].prev_node

    def _get_other_vertex(self, edge, vertex):
        """ Return the other vertex. """
        if vertex in edge:
            if edge.prev_node == vertex:
                if not edge.next_node.visited:
                    return edge.next_node
            else:
                if not edge.prev_node.visited:
                    return edge.prev_node
