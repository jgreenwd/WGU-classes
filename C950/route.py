# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

from graph import Graph
from datetime import time


class Route(Graph):
    def __init__(self, indices, weights):
        """ Create Route Object. """
        Graph.__init__(self, indices, weights)
        self.order = []
        self.rate_of_travel = 0
        self.package_count = 0
        self._starting_vertex = None
        self._start_time = time()
        self.delivery_time = time()
        self._finished = False
        self._index = 0

    def set_rate_of_travel(self, rate):
        """ Set the average speed of delivery vehicle in MPH.
        :param rate: float of the vehicle speed """
        self.rate_of_travel = rate

    def set_start_time(self, time):
        """ Set the time when the vehicle will leave the HUB.
        :param time: expects datetime() value
        """
        self._start_time = time

    def get_start_time(self):
        """ Return time when the delivery vehicle leaves the HUB. """
        return self._start_time

    def get_current_node(self):
        """ Return most recent node visited in Cycle. """
        return self.order[self._index].prev_node

    def get_next_node(self):
        """ Return next node to be visited in Cycle. """
        return self.order[self._index].next_node

    def get_next_edge(self):
        """ Move pointer to next edge to be visited in Cycle.
            CAUTION: This method advances the internal index to the next node. Every
            invocation of the method will return a different value until the last
            value is reached.
        """
        if self._index < len(self.order) -1:
            self._index += 1
        return self.order[self._index]

    def finish_route(self):
        """ Set route as finished. """
        self._finished = True

    def finished(self):
        """ Return route completion status. """
        return self._finished

    def create_cycle(self, start):
        """ Place delivery destinations in order to minimize time & distance.

        :param start: Vertex """
        self._starting_vertex = start

        edges = self.adjacency_list.copy()
        unvisited = self.vertices.copy()
        for vertex in unvisited:
            vertex.visited = False

        current_vertex = self._starting_vertex
        current_edge = None

        while len(unvisited) > 1:
            candidate = self._get_nearest_neighbor(current_vertex)
            if candidate == current_vertex:
                break
            for edge in edges:
                if candidate in edge and current_vertex in edge:
                    current_edge = edge
            self.order.append(current_edge)
            current_vertex.visited = True
            unvisited.remove(current_vertex)
            current_vertex = candidate

        # return to HUB
        for edge in edges:
            if current_vertex in edge and self._starting_vertex in edge:
                self.order.append(edge)

        # link corresponding vertex ends to each other (aka A-B, B-C, C-D...)
        current_vertex = self._starting_vertex
        for edge in self.order:
            if not edge.prev_node is current_vertex:
                edge.swap_nodes()
            current_vertex = edge.next_node
