# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class KeyValuePair:
    def __init__(self, key, value):
        self.key = key
        self.value = value


class HashTable:
    #   1st axis = Buckets
    #   2rd axis = List of KeyValuePairs  in each bucket, see insert() # 3.a.
    def __init__(self, length=64):
        self.buckets = [None] * length

    def _generate_hash(self, key):
        total = 0
        for char in str(key):
            total += ord(char)

        return total % len(self.buckets)

    def insert(self, key, args):
        """ insert(args) into HashTable"""
        # 1. create K/V pair from package (wrap the object)
        kvp = KeyValuePair(key, args)

        # 2. generate hash from supplied key components (determine which bucket to put object in)
        index = self._generate_hash(key)

        # 3. check for collision & assign pair to index
        # - a. if empty, insert
        if self.buckets[index] is None:
            self.buckets[index] = list([kvp])
            return True
        # - b. else check for update or insertion
        else:
            for item in self.buckets[index]:
                if item.key == key:
                    item.value = args
                    return True
            self.buckets[index].append(kvp)
            return True

    def search(self, key):
        """ return reference to object matching hash & key """
        index = self._generate_hash(key)

        if self.buckets[index] is not None:
            for item in self.buckets[index]:
                if item.key == key:
                    return item

        return None

