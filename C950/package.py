# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

from enum import Enum


class Package:
    # O(1)
    def __init__(self, id=None, address=None, deadline=None, weight=None, notes=None):
        """ Create Package() object.

        :param id: Integer
        :param address: Location
        :param deadline: datetime.time
        :param weight: Float
        :param notes: String
        """
        self.ID = id
        self.address = address
        self.weight = weight
        self.notes = notes
        self.deadline = deadline
        self._time_of_delivery = ""
        self.status = Status(0)

    # O(1)
    def __str__(self):
        return "ID: {:2.2}\tWeight: {:4.1f}".format(self.ID, self.weight)

    # O(1)
    def __lt__(self, other):
        return self.deadline < other.deadline

    # O(1)
    def __eq__(self, other):
        return str(self) == other

    # O(1)
    def __hash__(self):
        return hash(str(self))

    # O(1)
    def in_route(self):
        """ Set status to In_Route. """
        self.status = Status(0)

    # O(1)
    def delay(self):
        """ Set status to Delayed. """
        self.status = Status(1)

    # O(1)
    def load(self):
        """ Set status to Loading. """
        self.status = Status(2)

    # O(1)
    def ofd(self):
        """ Set status to Out For Delivery. """
        self.status = Status(3)

    # O(1)
    def deliver(self, delivery_time=None):
        """ Set status to Delivered.

        :param delivery_time: datetime.time """
        if self.status != Status(4):
            self.status = Status(4)
            self._time_of_delivery = str(delivery_time.time())

    # O(1)
    def get_status(self):
        """ Return Package delivery Status. """
        if self.status == Status(4):
            return self.status.name + ": " + self._time_of_delivery
        else:
            return self.status.name


class Status(Enum):
    IN_ROUTE = 0
    DELAYED = 1
    LOADING = 2
    OUT_FOR_DELIVERY = 3
    DELIVERED = 4
