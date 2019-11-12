# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1


class Edge:
    def __init__(self, vertex1, vertex2, weight):
        """ Create Edge Object.

        :param vertex1: Vertex
        :param vertex2: Vertex
        :param weight: float """
        self.weight = weight
        self.prev_node = vertex1
        self.next_node = vertex2

    def __eq__(self, other):
        return (self.weight == other.weight and
                (self.prev_node == other.prev_node and self.next_node == other.next_node) or
                (self.prev_node == other.next_node and self.next_node == other.prev_node))

    def __lt__(self, other):
        return self.prev_node < other.prev_node

    def __hash__(self):
        return hash(str(self))

    def __contains__(self, vertex):
        return self.prev_node == vertex or self.next_node == vertex

    def get_next_start(self):
        """ Return unvisited Vertex() """
        if not self.prev_node.visited:
            return self.prev_node
        elif not self.next_node.visited:
            return self.next_node
        return None

    def swap_nodes(self):
        """ Swap node positions. """
        tmp = self.prev_node
        self.prev_node = self.next_node
        self.next_node = tmp
