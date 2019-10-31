# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from graph import Graph
from destination import Destination


class Route(Graph):
    def __init__(self):
        Graph.__init__(self)

    def find_midpoint(self, start):
        loc = start
        max_dist = loc.get_weight(start)

        for item in self.vertices:
            if item is not start:
                if item.get_weight(start) > max_dist:
                    max_dist = item.get_weight(start)
                    loc = item

        return loc
