# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from datetime import time
from enum import Enum


class Package:
    def __init__(self, id=None, address=None, deadline=None, weight=None, notes=None):
        """ Create Package() object.

        :param id: integer value representing unique package
        :param address: string value representing street address
        :param deadline: time object representing expected delivery time
        :param weight: float value of package mass
        :param notes: string value of delivery notes
        """
        self.ID = id
        self.address = address
        self.weight = weight
        self.notes = notes
        self.deadline = deadline
        self.status = Status(0)

    def __str__(self):
        return ("ID: " + self.ID +
                "\tWeight: " + "{:4.1f}".format(self.weight) +
                "\tDelivery By: " + self.deadline.strftime("%H:%M") +
                "\tStatus: " + self.status.name)

    def __lt__(self, other):
        return self.deadline < other.deadline


class Status(Enum):
    IN_ROUTE = 0
    LOADING = 1
    DELAYED = 2
    OUT_FOR_DELIVERY = 3
    DELIVERED = 4
