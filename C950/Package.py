# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from datetime import time
from enum import Enum

class Package:
    def __init__(self, Id = None, addr = None, city = None, state = None, zip = None, notes = None):
        self.ID = Id
        self.addr = addr
        self.city = city
        self.state = state
        self.zip = zip
        self.weight = 0.0
        self.notes = notes
        self.deadline = time()
        self.status = Status(0)


class Status(Enum):
    LOADING = 0
    IN_ROUTE = 1
    DELAYED = 2
    DELIVERED = 3
