# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Vertex:
    def __init__(self, data):
        """ Create Vertex Object.

        :param data: object placed at Vertex """
        self._data = data
        self.visited = False

    def __str__(self):
        return str(self._data)

    def __lt__(self, other):
        return self._data < other.data

    def __eq__(self, other):
        return self._data == other.data

    def __hash__(self):
        return hash(str(self))
