# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

from graph import Graph
from datetime import time
from edge import Edge


class Route(Graph):
    def __init__(self, indices, weights):
        """ Create Route Object. 
        
        :param indices: List
        :param weights: List """
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
        :param rate: float """
        self.rate_of_travel = rate

    def set_start_time(self, time):
        """ Set the time when the vehicle will leave the HUB.
        :param time: datetime.time """
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
        # B1. LOGIC COMMENTS - Utilizing Nearest Neighbor
        # B3. SPACE-TIME AND BIG-O

        # 1. INITIALIZE ALL VERTICES AS UNVISITED
        # O(n) -> Each vertex is initialized within a for-loop. This requires
        # 1 assignment operation per vertex, regardless of the size of the graph
        unvisited = self.vertices.copy()
        edges = self.adjacency_list.copy()
        for vertex in unvisited:
            vertex.visited = False

        # 2. SELECT AN INITIAL VERTEX
        # O(1) -> Every call of create_cycle() will execute this exactly once.
        current_vertex = self._starting_vertex
        current_edge = None

        while len(unvisited) > 1:
            # 3. FIND THE NEAREST, UNVISITED NEIGHBOR TO THE CURRENT VERTEX
            # This occurs within the graph._get_nearest_neighbor() method. The method
            # itself operates in O(n) time, performing 2 comparison operations per vertex:
            #     1. is it visited?
            #     2. is the weight lower than the current minimum?
            candidate = self._get_nearest_neighbor(current_vertex)
            if candidate == current_vertex:
                break
            # This loop is necessitated by my implementation of a separate Edge class.
            # It does increase the amount of overhead required and its use should probably
            # be re-examined in the future. It executes 1 comparison per edge, which has
            # a worst-case runtime of O(n)
            for edge in edges:
                if candidate in edge and current_vertex in edge:
                    current_edge = edge
            self.order.append(current_edge)
            # 4A. MARK CURRENT VERTEX AS VISITED AND ...
            current_vertex.visited = True
            unvisited.remove(current_vertex)
            # 4B. ... SET NEAREST NEIGHBOR AS CURRENT VERTEX
            current_vertex = candidate
            # 5. REPEAT UNTIL ALL VERTICES ARE VISITED
            # if length of unvisited is 0, then all vertices are visited

            # RESULT: O(n) + O(1) + O(n)( O(n) + O(1) + O(n) + O(1) + O(1) + O(1) + O(1) )
            # = O(n) + O(n) * (2 * O(n))
            # = O(n) + O(n) * O(n)
            # = O(n) + O(n)^2 
            # = O(n)^2

        # The following code serves to make the result conform to necessary formatting
        # criteria necessary to retrieve consistently useful results from my implementation.
        # It does not increase the runtime complexity of the algorithm, nor the space complexity.

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

    def alter_course(self, add_vert):
        """ Insert Vertex at end of all other deliveries, but before returning to HUB.

        :param add_vert: Vertex """
        # simplest solution: insert new vertex before returning to HUB
        last_idx = len(self.order) - 1

        # from index, determine previous vertex and next vertex
        prev_vert = self.order[last_idx].prev_node
        next_vert = self.order[last_idx].next_node
        front_edge = Edge(prev_vert, add_vert, self._get_weight(self._get_index(prev_vert), self._get_index(add_vert)))
        back_edge = Edge(add_vert, next_vert, self._get_weight(self._get_index(add_vert), self._get_index(next_vert)))

        # order the edge's vertices
        if front_edge.prev_node is not prev_vert:
            front_edge.swap_nodes()
        if back_edge.next_node is not next_vert:
            back_edge.swap_nodes()

        # insert new edges
        self.order[last_idx] = front_edge
        self.order.append(back_edge)
