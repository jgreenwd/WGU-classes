from graph import Graph
from datetime import datetime, time
from package import Package
from hashtable import HashTable
from destination import Destination


def load_file(filename):
    """ Read data from text file and return as list. """
    fin = open(filename)
    result = []
    if fin.readable():
        for line in fin:
            result.append(line.strip())

    fin.close()

    return result


def convert_time(args):
    """ Given a string in "HH:MM" format, return an equivalent time object. """
    # NOTE 1: ignores AM/PM
    # NOTE 2: "End Of Day" defaults to 20:00
    if args != "EOD":
        result = datetime.strptime(args[:-3], "%H:%M").time()
    else:
        result = time(hour=20, minute=00)
    return result


def build_package_table(source):
    """ Given List() of string input, Return HashTable() of Package()s. """
    table = HashTable(64)

    for line in source:
        # separate items on each line of CSV file
        temp = line.split(',')

        loc = Destination(
            temp[1],                        # - address
            temp[2],                        # - city
            temp[3],                        # - state
            temp[4]                         # - zip
        )

        p = Package(
            temp[0],                        # ID
            loc,                            # Location
            convert_time((temp[5])),        # Delivery Deadline
            float(temp[6]),                 # Weight
            temp[7])                        # Notes

        # add Package to HashTable
        table.insert(p)

    return table


# Creates duplication of effort from build_package_table(), (parsing the same data twice)
# but allows separation of Graph() creation from Package() creation. Implementation
# becomes overly cumbersome when they are combined.
def build_destination_graph(source):
    """ Return Graph() of Destination()s from List() of string input. """
    locales = Graph()

    for line in source:
        temp = line.split(',')

        loc = Destination(
            temp[1],  # - address
            temp[2],  # - city
            temp[3],  # - state
            temp[4]  # - zip
        )

        locales.add_vertex(loc)

    return locales
