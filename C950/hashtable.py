# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class HashTable:
    #   Table is a 2-axis array
    #   1st axis = buckets
    #   2rd axis = List of KeyValuePairs in each bucket, see insert() # 3.a.
    def __init__(self, length=64):
        self.buckets = []
        self.length = length
        self._index = 0
        for i in range(self.length):
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
        raise StopIteration

    def _generate_hash(self, key):
        h1 = 17
        h2 = 13
        string = str(key)
        for i in range(0,len(string),2):
            h1 *= ord(string[i]) ** 3

        for i in range(1, len(string),2):
            h2 += ord(string[i]) ** 2

        return ((h1 - 311) % self.length * (127 - h2 * 311) % self.length) % self.length

    def insert(self, args):
        """ insert(args) into HashTable"""
        # 1. generate hash from supplied key components (determine which bucket to put object in)
        index = self._generate_hash(args)

        # 2. check for collision & assign pair to index
        # - a. if empty, insert as list

        bucket = self.buckets[index]
        bucket.append(args)

        #     return True
        # - b. else check for update or insertion
        # else:
        #     for item in self.buckets[index]:
        #         if item.key == :
        #             item.value = args
        #             return True
        #     self.buckets[index].append(kvp)
        #     return True

    def search(self, key):
        """ return reference to object matching hash & key """
        index = self._generate_hash(key)

        if self.buckets[index] is not None:
            for item in self.buckets[index]:
                if item.key == key:
                    return item

        return None
