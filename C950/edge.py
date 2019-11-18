# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1


class Edge:
    # O(1)
    def __init__(self, vertex1, vertex2, weight):
        """ Create Edge Object.

        :param vertex1: Vertex
        :param vertex2: Vertex
        :param weight: float """
        self.weight = weight
        self.prev_node = vertex1
        self.next_node = vertex2

    # O(1)
    def __eq__(self, other):
        return (self.weight == other.weight and
                (self.prev_node == other.prev_node and self.next_node == other.next_node) or
                (self.prev_node == other.next_node and self.next_node == other.prev_node))

    # O(1)
    def __lt__(self, other):
        return self.prev_node < other.prev_node

    # O(1)
    def __hash__(self):
        return hash(str(self))

    # O(1)
    def __contains__(self, vertex):
        return self.prev_node == vertex or self.next_node == vertex

    # O(1)
    def get_next_start(self):
        """ Return unvisited Vertex() """
        if not self.prev_node.visited:
            return self.prev_node
        elif not self.next_node.visited:
            return self.next_node
        return None

    # O(1)
    def swap_nodes(self):
        """ Swap node positions. """
        tmp = self.prev_node
        self.prev_node = self.next_node
        self.next_node = tmp
