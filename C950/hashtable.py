# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class HashTable:
    #   Chaining Hash Table:
    #   Table is a 2-axis array
    #   1st axis = List of buckets
    #   2rd axis = List of items in each bucket
    def __init__(self, length=64):
        self.buckets = []
        self.length = length
        self._index = 0
        for i in range(length):
            self.buckets.append([])

    def __contains__(self, item):
        return item in self.buckets

    def __len__(self):
        return self.length

    def __iter__(self):
        return self

    def __next__(self):
        iterable = self.buckets
        if self._index < len(iterable):
            result = iterable[self._index]
            self._index += 1
            return result
        self._index = 0
        raise StopIteration

    def _generate_hash(self, key):
        h1 = 17
        h2 = 13
        string = str(key)
        for i in range(0, len(string), 2):
            h1 *= ord(string[i]) ** 3

        for i in range(1, len(string), 2):
            h2 += ord(string[i]) ** 2

        return ((h1 - 311) % self.length * (127 - h2 * 311) % self.length) % self.length

    def insert(self, args):
        """ Insert(args) into HashTable. """
        index = self._generate_hash(args)

        bucket = self.buckets[index]
        bucket.append(args)

    def search(self, key):
        """ Return reference to object matching key or None. """
        index = self._generate_hash(key)

        if len(self.buckets[index]) > 0:
            for item in self.buckets[index]:
                if item.key == key:
                    return item

        return None
