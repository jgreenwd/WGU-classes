# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class Graph:
    def __init__(self):
        self._index = 0
        self.contents = set()

    def __contains__(self, node):
        return node in self.contents

    def __len__(self):
        return len(self.contents)

    def __iter__(self):
        return self

    def __next__(self):
        iterable = list(self.contents)
        if self._index < len(iterable):
            result = iterable[self._index]
            self._index += 1
            return result
        raise StopIteration

    def add_node(self, node):
        self.contents.add(node)

    def get_node(self, args):
        for item in list(self.contents):
            if item == args:
                return item
        return None
