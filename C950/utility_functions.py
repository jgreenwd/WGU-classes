from graph import Graph
from datetime import datetime, time
from package import Package
from hashtable import HashTable
from destination import Destination


def load_file(filename):
    """ Read data from file and return as list. """
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


def get_weight(matrix, vert1, vert2):
    """ Return float found at intersection of both vertices.

        matrix = 2D list of floats
        vert1 = int index of first axis
        vert2 = int index of second axis
    """
    low, high = sorted([vert1, vert2])
    return matrix[high][low]


def get_index(indices, location):
    """ Return index of target in list or -1.

        indices = ordinal list of address names
        location = target search value
    """
    index = 0
    search = location.address + " " + location.zip
    for item in indices:
        if search == item:
            return index
        index += 1
    return -1


def build_graph_and_hashtable(source1, source2, source3):
    """ Return Graph() of Destinations() and HashTable() of Packages.

        source1 = CSV of Package() info
        source2 = TXT of matrix names/indices
        source3 = TSV of Graph() weights
    """
    locales = Graph()
    table = HashTable(64)

    # ------- Create Destination Vertices and add Package -------
    HUB = Destination("HUB", "Salt Lake City", "UT", "84107")
    locales.add_vertex(HUB)
    for line in source1:
        # separate items on each line of CSV file
        temp = line.split(',')

        loc = Destination(
            temp[1],                          # - address
            temp[2],                          # - city
            temp[3],                          # - state
            temp[4]                           # - zip
        )

        p = Package(
            temp[0],                          # ID
            convert_time((temp[5])),          # Delivery Deadline
            float(temp[6]),                   # Weight
            temp[7])                          # Notes

        # add Package to HashTable
        table.insert(p)
        HUB.add_package(p)

        # add Package to its Destination
        if loc in locales:
            loc = locales.get_vertex(loc)
            loc.add_package(p)
        else:
            loc.add_package(p)
            locales.add_vertex(loc)

    # build list of Addresses from TXT file
    indices = [None] * 27
    for index, line in enumerate(source2):
        indices[index] = line

    # build matrix of graph weights from TSV file
    matrix = [[]] * 27
    for index, line in enumerate(source3):
        matrix[index] = [float(i) for i in line.split('\t')]

    # assign weights to all possible graph edges
    i = 0
    while i < len(locales):
        tmp = list(locales)
        for j, loc in enumerate(locales):
            if i == j:
                continue
            # find index for both graph vertices
            index_1 = get_index(indices, tmp[i])
            index_2 = get_index(indices, loc)
            # add edge & weight given those two vertices
            tmp[i].add_edge(loc, get_weight(matrix, index_1, index_2))
        i += 1

    print(HUB.adjacent)

    return locales, table
