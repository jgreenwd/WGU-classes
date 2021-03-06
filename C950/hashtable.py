# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1


class HashTable:
    # Chaining Hash Table:
    #   - Table is a 2-axis array
    #   - 1st axis = List of buckets
    #   - 2rd axis = List of items in each bucket
    # Length is declared at creation and immutable. If it changes, the hash value in
    # _generate_hash() will no longer be able to retrieve values previously insert()ed.

    # O(n)
    def __init__(self, length=64):
        """ Create HashTable Object.

        :param length: Integer """
        self.buckets = []
        self.length = length
        self._index = 0
        for i in range(4 * length // 3):
            self.buckets.append([])

    # O(1)
    def __contains__(self, item):
        return item in self.buckets

    # O(1)
    def __len__(self):
        return self.length

    # O(1)
    def __iter__(self):
        return self

    # O(1)
    def __next__(self):
        iterable = self.buckets
        if self._index < len(iterable):
            result = iterable[self._index]
            self._index += 1
            return result
        self._index = 0
        raise StopIteration

    # O(1)
    def _generate_hash(self, key):
        """ Return Integer for index in HashTable.

        :param key: String-able """
        # Generate Hash value from key for indexing HashTable.
        h1 = 13
        h2 = 17
        string = str(key)
        for i in range(0, len(string), 2):
            h1 += ord(string[i])

        for i in range(1, len(string), 2):
            h2 += ord(string[i])

        return ((h1 % self.length) - h2 ** 3) % self.length

    # O(1)
    def insert(self, obj):
        """ Insert object into HashTable.

        :param obj: Object """
        index = self._generate_hash(obj)

        bucket = self.buckets[index]
        bucket.append(obj)

    # O(n)
    def search(self, key):
        """ Return reference to object matching key or None.

        :param key: String-able """
        index = self._generate_hash(key)

        if len(self.buckets[index]) > 0:
            for item in self.buckets[index]:
                if item == key:
                    return item
        return None

    # O(1)
    def remove(self, obj):
        """ Remove object from HashTable; Return Boolean of success.

        :param obj: Object """
        index = self._generate_hash(obj)

        if len(self.buckets[index]) > 0:
            if obj in self.buckets[index]:
                self.buckets[index].remove(obj)
                return True
        return False
