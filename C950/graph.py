# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

# TODO: refactor get nearest neighbor - with sorted list, can just return
#       1st element that isn't visited, assuming no ties; also iterable is broken

from edge import Edge


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
    def add_vertex(self, vertex1):
        """ Add vertex to Set of Vertices.

        :param vertex: Vertex """
        self.vertices.add(vertex1)

        for vertex2 in self.vertices:
            self._add_edge(vertex1, vertex2)

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
            self.vertices.remove(vertex)
            # can not manipulate Set() while in use; reference a copy
            tmp = self.adjacency_list.copy()
            for edge in tmp:
                if vertex in edge:
                    self.adjacency_list.remove(edge)

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

    def _get_nearest_neighbor(self, vertex, iterable=None):
        """ Return Vertex with least weight respective to vertex.

        :param iterable: List
        :param vertex: Vertex """

        # get list of adjacent edges, sorted by weight
        neighbors = sorted(self._get_neighbors(vertex), key=lambda x: x.weight)

        # filter list of edges by visited status
        output = []
        for edge in neighbors:
            if edge.prev_node is vertex:
                if not edge.next_node.visited:
                    output.append(edge)
            elif edge.next_node is vertex:
                if not edge.prev_node.visited:
                    output.append(edge)

        # test for ties in weight
        if len(output) > 1 and iterable is not None:
            if output[0].weight == output[1].weight:
                # select a winner from ties
                self._look_ahead(vertex, output)

        if output[0].prev_node == vertex:
            neighbor = output[0].next_node
        else:
            neighbor = output[0].prev_node
        return neighbor

    def _look_ahead(self,vertex, tied_edge_weights):
        """ Return shortest path when multiple edges are equal.

        :param tied_edge_weights: List of Edges"""
        # select all edges with tied weight
        candidates = list(filter(lambda x: x.weight == tied_edge_weights[0].weight, tied_edge_weights))

        # find all neighbors for the candidate vertex - insert as tuple(vertex, neighbors)
        for i, candidate in enumerate(candidates):
            if candidate.prev_node == vertex:
                candidates[i] = (self._get_neighbors(candidate.next_node), candidate.next_node)
            else:
                candidates[i] = (self._get_neighbors(candidate.prev_node), candidate.prev_node)

        # determine weight for candidate vertex's nearest neighbor
        for i, candidate in enumerate(candidates):
            candidates[i] = (candidate[1], (self._get_weight(self._get_index(vertex), self._get_index(self._get_nearest_neighbor(*candidate)))))

        return min(candidates, key=lambda x: x[1])
