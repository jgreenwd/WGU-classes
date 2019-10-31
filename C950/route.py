# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from graph import Graph


class Route(Graph):
    def __init__(self):
        Graph.__init__(self)
        self.order = []

    def _find_midpoint(self, start, vert_list):
        # return list member farthest from starting vertex
        return sorted(vert_list, key=lambda i: i.get_weight(start))[len(vert_list) - 1]

    def _find_next(self, current, vert_list):
        # return list member closest to current vertex
        return sorted(vert_list, key=lambda i: i.get_weight(current))[1]

    def create_cycle(self, start):
        self.order = [None] * len(self.vertices)
        self.order[0] = start
        tmp = sorted(list(self.vertices), key=lambda i: i.get_weight(start))
        current = tmp[0]

        i = 1
        m = len(self.order) // 2
        while None in self.order:
            prev = current

            if i == len(self.order) // 2:
                self.order[i] = self._find_midpoint(start, sorted(list(self.vertices), key=lambda i: i.get_weight(start)))
            else:
                self.order[i] = self._find_next(current, tmp)

            current = self.order[i]
            tmp.remove(prev)
            i += 1
