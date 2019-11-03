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
        self.delivery_time = time()
        self._rate_of_travel = 0
        self._index = 0

    def _find_midpoint(self, start, vert_list):
        # return list member farthest from starting vertex
        return sorted(vert_list, key=lambda i: i.get_weight(start))[len(vert_list) - 1]

    def _find_next(self, current, vert_list):
        # return list member closest to current vertex
        return sorted(vert_list, key=lambda i: i.get_weight(current))[1]

    def create_cycle(self, start):
        """ Place delivery destinations in order to minimize time & distance.

        :param start: Starting Vertex for the Graph cycle
        """
        self.order = [None] * len(self.vertices)

        # place starting point at 0th index
        self.order[0] = start

        # cast Set() of vertices as List() and order by distance/weight from starting point
        tmp = sorted(list(self.vertices), key=lambda i: i.get_weight(start))

        # ALGORITHM EXPLAINED:
        # 1. Find the Destination farthest from the starting vertex
        #   - this will be midpoint of the cycle
        # 2. Find the Destination closest to the starting vertex
        # ----- Repeat -------
        # 3. Travel to that vertex
        # 4. Find the Destination closest to the current vertex
        # --------------------
        # 5. Repeat until arrival at middle index (midpoint from #1)
        # 6. Find the Destination closest to the midpoint
        # ----- Repeat -------
        # 7. Travel to that vertex
        # 8. Find the Destination closest to the current vertex
        # --------------------
        # 9. Repeat until no vertices remain in sorted list
        # 10. Return to starting vertex

        current = tmp[0]
        i = 1
        while None in self.order:
            # save reference to the last place visited in tmp List() to be able to .remove() it later
            prev = current

            if i == len(self.order) // 2:
                self.order[i] = self._find_midpoint(start, sorted(list(self.vertices), key=lambda i: i.get_weight(start)))
            else:
                self.order[i] = self._find_next(current, tmp)

            current = self.order[i]
            tmp.remove(prev)
            i += 1

        # return to starting vertex
        self.order.append(start)

    def set_rate_of_travel(self, rate):
        """ Set the average speed of delivery vehicle in MPH.

        :param rate: float of the vehicle speed """
        self._rate_of_travel = rate

    def set_start_time(self, time):
        """ Set the time at which the vehicle will leave the HUB.

        :param time: expects datetime() value
        """
        self._start_time = time

    def get_start_time(self):
        """ Return time when the delivery vehicle leaves the HUB. """
        return self._start_time

    def get_current_node(self):
        """ Return most recent node visited in Cycle. """
        return self.order[self._index]

    def get_next_node(self):
        """ Return next node to be visited in Cycle.

            CAUTION: This method advances the internal index to the next node. Every
            invocation of the method will return a different value.
        """
        if self._index < len(self.order) - 1:
            self._index += 1
            return self.order[self._index]
        else:
            return self.order[len(self.order) - 1]
