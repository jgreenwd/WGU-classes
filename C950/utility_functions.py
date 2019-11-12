# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

from graph import Graph
from datetime import datetime, time
from package import Package
from hashtable import HashTable
from location import Location


def load_file(filename):
    """ Read data from text file and return as list. """
    fin = open(filename)
    result = []
    if fin.readable():
        for line in fin:
            result.append(line.upper().strip())

    fin.close()

    return result


def convert_time(args):
    """ Given a string in "HH:MM" format, return an equivalent time object. """
    # NOTE 1: ignores AM/PM
    # NOTE 2: "End Of Day" defaults to 20:00
    if args != "EOD" and len(args) < 6:
        return datetime.strptime(args, "%H:%M")
    elif args == "EOD":
        return time(hour=20, minute=00)
    else:
        return datetime.strptime(args[:-3], "%H:%M").time()


def parse_delivery_input(source, indices, weights):
    """ Given List() of string input, Return HashTable() of Package()s. """
    graph = Graph(indices, weights)
    table = HashTable(len(source))

    for line in source:
        # separate items on each line of CSV file
        temp = line.split(',')

        loc = Location(
            temp[1].upper(),                # - address
            temp[2].upper(),                # - city
            temp[3].upper(),                # - state
            temp[4]                         # - zip
        )

        if loc in graph:
            # if location present, get reference
            loc = graph.get_vertex(loc)
        else:
            # else add location
            graph.add_vertex(loc)

        p = Package(
            temp[0],                        # ID
            loc,                            # Location
            convert_time((temp[5])),        # Delivery Deadline
            float(temp[6]),                 # Weight
            temp[7])                        # Notes

        # add Package to HashTable
        table.insert(p)

        # add package key to Location
        loc.add_package_key(str(p))

    return graph, table


def mode_query(mode_dict, table):
    # single package or all packages?
    while True:
        try:
            user_mode_input = mode_dict[input('Display information for a (S)ingle package or (A)ll packages? ')[0].upper()]
            break
        except KeyError:
            print("Invalid entry. Please try again.")

    if user_mode_input == 1:
        # if all packages
        while True:
            # query for time to end simulation
            try:
                user_time_input = convert_time(input('Enter time in HH:MM format: '))
                break
            except ValueError:
                print("Invalid entry. Please try again.")
        package = None
    else:
        # if single package
        while True:
            try:
                identify = input("Enter the package ID to beginning tracking: ")
                weight = float(input("Package weight: "))
                package = table.search("ID: {:2.2}\tWeight: {:4.1f}".format(identify, weight))
                if package is not None:
                    break
                else:
                    raise NameError
            except NameError:
                print("Package not found. Try again. ")
            except ValueError:
                print("Invalid entry. Please try again.")

        # end simulation at closing time
        user_time_input = datetime(1, 1, 1, 20, 0, 0)

    return user_mode_input, user_time_input, package


def print_single_delivery_status(table, package):
    """ Display delivery information for individual package.

    :param table: HashTable
    :param package: Package """
    print("ID\tWgt \tAddress\t\t\t\t  City\t\t\t\t Zip\tDeliver-By\tStatus")
    p = table.search(package)
    print("{:2} {:4.1f} \t{:20.20}  {:18.16} {}\t{}\t{:24}".format(
        p.ID,
        p.weight,
        p.address.address,
        p.address.city,
        p.address.zip,
        p.deadline,
        p.get_status()
    ))
    print()


def print_all_delivery_status(table):
    """ Display Package() info for all packages.

    :param table: HashTable of Packages()"""
    print("ID\tWgt \tAddress\t\t\t\t  City\t\t\t\t Zip\tDeliver-By\tStatus")
    for bucket in table:
        if len(bucket) > 0:
            for package in bucket:
                print("{:2} {:4.1f} \t{:20.20}  {:18.16} {}\t{}\t{:24}".format(
                    package.ID,
                    package.weight,
                    package.address.address,
                    package.address.city,
                    package.address.zip,
                    package.deadline,
                    package.get_status()
                ))
    print()
