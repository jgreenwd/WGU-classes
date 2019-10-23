# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from math import floor


class HashTable:
    # Essentially, it's a 3-dimensional array.
    #   1st axis = Table
    #   2nd axis = Buckets
    #   3rd axis = [Key/Value] entries in each bucket
    def __init__(self, length=64):
        self.length = length
        self.table = [None] * self.length

    def _generate_hash(self, key):
        """ generate hash from Package ID & weight """
        id_cubed_mod_length = int(key[0]) ** 3 % self.length
        weight_cubed_mod_length = floor(float(key[1]) ** 3 % self.length)

        return (id_cubed_mod_length + (311 - weight_cubed_mod_length)) % self.length

    def insert(self, args):
        """ insert(args) into HashTable"""
        # 1. generate hash -> index
        index = self._generate_hash((args.ID, args.weight))

        # 2. create list of [k, v] pairs
        value = [args.ID, args]

        # 3. check for collision & assign pair to index
        # - a. if collision NOT possible
        if self.table[index] is None:
            self.table[index] = list([value])
            return True
        # - b. else check for update or insertion
        else:
            for entry in self.table[index]:
                if entry[0] == args.ID:
                    entry[1] = args
                    return True
            self.table[index].append(value)
            return True

    def search(self, args):
        """ return reference to object matching hash & ID """
        index = self._generate_hash((args.ID, args.weight))

        if self.table[index] is not None:
            for item in self.table[index]:
                if item[1].ID == args.ID:
                    return item[1]

        return None
