# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from graph import Graph
from datetime import time


class Route(Graph):
    def __init__(self):
        """ Create Route Object. """
        Graph.__init__(self)
        self.order = []
        self._start_time = time()
        self._starting_vertex = None
        self.delivery_time = time()
        self._rate_of_travel = 0
        self._index = 0
        self._finished = False

    def set_rate_of_travel(self, rate):
        """ Set the average speed of delivery vehicle in MPH.

        :param rate: float of the vehicle speed """
        self._rate_of_travel = rate

    def set_start_time(self, time):
        """ Set the time when the vehicle will leave the HUB.

        :param time: expects datetime() value
        """
        self._start_time = time

    def get_start_time(self):
        """ Return time when the delivery vehicle leaves the HUB. """
        return self._start_time

    def get_starting_node(self):
        """ Return starting Vertex(). """
        return self._starting_vertex

    def get_current_node(self):
        """ Return most recent node visited in Cycle. """
        return self.order[self._index].prev_node

    def get_next_node(self):
        """ Return next node to be visited in Cycle. """
        return self.order[self._index].next_node

    def get_next_edge(self):
        """ Return next edge to be visited in Cycle.

            CAUTION: This method advances the internal index to the next node. Every
            invocation of the method will return a different value until the last
            value is reached.
        """
        if self._index < len(self.order) - 1:
            self._index += 1
            return self.order[self._index]
        else:
            return self.order[len(self.order) - 1]

    def finish_route(self):
        """ Set route as finished. """
        self._finished = True

    def finished(self):
        """ Return route completion status. """
        return self._finished

    def create_cycle(self, start):
        """ Place delivery destinations in order to minimize time & distance.

        :param start: Starting Vertex for the Graph cycle
        """
        self._starting_vertex = start
        verts = self._vertices.copy()
        edges = self._adjacency_list.copy()

        # starting vertex
        current_vertex = self._starting_vertex
        current_edge = None

        # Nearest neighbor
        i = 0
        while len(self.order) < len(self._vertices) - 1:
            neighbor = self._get_nearest_neighbor(edges, current_vertex)
            for edge in edges:
                if current_vertex in edge and neighbor in edge:
                    current_edge = edge
                    self.order.append(current_edge)
            current_vertex.visited = True
            current_vertex = current_edge.get_next_start()
            if current_vertex.visited:
                current_vertex = start
            edges.remove(current_edge)
            verts.remove(current_vertex)
            i += 1

        # add edge returning to starting vertex
        neighbors = self._get_neighbors(current_vertex)
        for edge in neighbors:
            if current_vertex in edge and start in edge:
                current_edge = edge
                self.order.append(current_edge)

        # order heads and tails to connect in sequence
        current_vertex = self._starting_vertex
        for edge in self.order:
            if edge.prev_node is not current_vertex:
                edge.flip()
            current_vertex = edge.next_node
