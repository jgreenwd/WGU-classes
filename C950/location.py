# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

from vertex import Vertex


class Location(Vertex):
    def __init__(self, address, city=None, state=None, zip=None):
        """ Create Destination Object.

        :param address: String
        :param city: String
        :param state: String
        :param zip: Integer
        """
        Vertex.__init__(self, address)
        self.address = address
        self.city = city
        self.state = state
        self.zip = zip
        self._package_keys = []

    def __str__(self):
        return self.address + "\t" + self.city + "\t" + self.state + "\t" + self.zip

    def __lt__(self, other):
        return (self.address < other.address and
                self.zip == other.zip)

    def __eq__(self, other):
        if other is not None:
            return (self.address == other.address and
                    self.zip == other.zip)

    def __hash__(self):
        return hash(str(self))

# Package_key is a String made up of str(package), used to determine the hash
# value of the actual package in HashTable. self.package_keys is a List of Strings.

    def add_package_key(self, package_key):
        """ Add package key to Location.

        :param package_key: String """
        self._package_keys.append(package_key)

    def get_package_keys(self):
        """ Return List of package keys at Location. """
        return self._package_keys

    def del_package_key(self, package_key):
        """ Remove package key from Location.

        :param package_key: String """
        if package_key in self._package_keys:
            self._package_keys.remove(package_key)
