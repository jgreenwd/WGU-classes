# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from datetime import time
from enum import Enum


class Package:
    def __init__(self, id=None, addr=None, city=None, state=None, zip=None, notes=None):
        self.ID = id
        self.addr = addr
        self.city = city
        self.state = state
        self.zip = zip
        self.weight = 0.0
        self.notes = notes
        self.deadline = time()
        self.status = Status(0)

    def __str__(self):
        return ("ID: " + self.ID +
                "\tWeight: " + "{:4.1f}".format(self.weight) +
                "\tExpected Delivery: " + self.deadline.strftime("%H:%M") +
                "\tStatus: " + self.status.name +
                "\t\tAddress: " + self.addr + "\t" + self.city + "\t" + self.state + "\t" + self.zip)


class Status(Enum):
    LOADING = 0
    IN_ROUTE = 1
    DELAYED = 2
    DELIVERED = 3
