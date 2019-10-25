# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from datetime import time
from enum import Enum


class Package:
    def __init__(self, id=None, location=None, deadline=None, weight=None, notes=None):
        self.ID = id
        self.location = location
        self.weight = weight
        self.notes = notes
        self.deadline = deadline
        self.status = Status(0)

    def __str__(self):
        return ("ID: " + self.ID +
                "\tWeight: " + "{:4.1f}".format(self.weight) +
                "\tDelivery By: " + self.deadline.strftime("%H:%M") +
                "\tStatus: " + self.status.name +
                "\t\tAddress: " + str(self.location))


class Status(Enum):
    LOADING = 0
    IN_ROUTE = 1
    DELAYED = 2
    DELIVERED = 3
