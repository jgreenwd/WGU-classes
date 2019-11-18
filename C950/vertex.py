# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1


class Vertex:
    # O(1)
    def __init__(self, data):
        """ Create Vertex Object.

        :param data: Object """
        self._data = data
        self.visited = False

    # O(1)
    def __str__(self):
        return str(self._data)

    # O(1)
    def __lt__(self, other):
        return self._data < other.data

    # O(1)
    def __eq__(self, other):
        return self._data == other.data

    # O(1)
    def __hash__(self):
        return hash(str(self))
