# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Graph:
    def __init__(self):
        self._index = 0
        self.vertices = set()

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

    def add_vertex(self, vertex):
        self.vertices.add(vertex)

    def get_vertex(self, vertex):
        for item in list(self.vertices):
            if item == vertex:
                return item
        return None
