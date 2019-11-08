# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

import utility_functions as utils
from datetime import datetime, timedelta, time
from destination import Destination
from route import Route
from delivery import DeliveryController

# TODO: handle address change exception for Package 9


if __name__ == '__main__':
    # --------------- ESTABLISH PARAMETERS -----------------
    # starting location for delivery routes
    HUB = Destination("HUB", "Salt Lake City", "UT", "84107")

    # zones are zip codes used to sort packages to appropriate truck routes
    ZONE_ONE = (84103, 84104, 84105, 84106, 84107, 84111, 84119, 84123)
    ZONE_TWO = (84115, 84117, 84118, 84121)

    # Times each truck departs the HUB
    TRUCK1_DEPARTURE = datetime(1, 1, 1, 8, 0, 0)
    TRUCK2_DEPARTURE = datetime(1, 1, 1, 9, 0, 5)
    TRUCK3_DEPARTURE = datetime(1, 1, 1, 12, 0, 0)

    # average rate of travel in MPH
    RATE_OF_TRAVEL = 18.0

    # PACKAGE DELIVERY EXCEPTIONS found in package.notes
    TRUCK2_REQUIRED = ("3", "18", "36", "38")
    DELAYED = ("6", "25", "28", "32")
    BUNDLED = ("13", "14", "15", "16", "19", "20")
    ADDRESS_CHANGE = tuple("9")
    # ------------------------------------------------------


    # -------------------- BUILD CONTAINERS ----------------
    # Retrieve Graph(Destination) and HashTable(Package) data
    data = utils.load_file("WGUPS Package File.csv")

    # Retrieve Vertex Labels & Edge Weights
    vertices = utils.load_file("vertices.txt")
    edges = utils.load_file("matrix.tsv")

    # build HashTable() // Graph() // Route()
    table = utils.build_package_table(data)         # Package Data
    graph = utils.build_destination_graph(data)     # Location Data
    routes = [Route(), Route(), Route()]            # Graph Cycles, Weights, Edges, & associated Packages

    # sort Package()s at the HUB to appropriate Destination.packages list, based on Package.address
    for bucket in table:
        if len(bucket) > 0:
            for package in bucket:
                # Package() is not at HUB
                if package.ID in DELAYED:
                    package.delay()
                v = graph.get_vertex(package.address)
                v.add_package(package)

    for i, route in enumerate(routes):
        # create reference to avg speed
        route.set_rate_of_travel(RATE_OF_TRAVEL)
        # add HUB to each Route
        route.add_vertex(HUB)
        # set time each truck leaves HUB
        if i == 0:
            route.set_start_time(TRUCK1_DEPARTURE)
        elif i == 1:
            route.set_start_time(TRUCK2_DEPARTURE)
        else:
            route.set_start_time(TRUCK3_DEPARTURE)
    # ------------------------------------------------------


    # -------------------- LOAD PACKAGES -------------------
    # add locations to Route from HashTable
    for bucket in table:
        if len(bucket) > 0:
            for package in bucket:
                loc = graph.get_vertex(package.address)
                if package.ID in BUNDLED or loc in routes[0]:
                    routes[0].add_vertex(loc)
                    if loc in routes[2]:
                        routes[2].del_vertex(loc)
                elif package.ID in TRUCK2_REQUIRED or package.ID in DELAYED or loc in routes[1]:
                    routes[1].add_vertex(loc)
                    if loc in routes[2]:
                        routes[2].del_vertex(loc)
                else:
                    routes[2].add_vertex(loc)

    # At this point, BUNDLED, DELAYED, & TRUCK2_REQUIRED are sorted correctly, but Truck3
    # has too many packages.
    removals = []

    # group destinations by .zip code & delivery .deadline
    for location in routes[2]:
        zone = int(location.zip)
        if len(location.packages) == 0:  # <- SKIP THE HUB
            continue

        # Packages in each bucket are sorted by deadline, so just check the first one for earliest.
        # If a location has a package with a deadline of 10:30, no need to make a second trip for
        # a package with a later deadline. Remove vertices from truck3 and place on truck1 or 2 by zone
        for package in location.packages:
            zone = int(package.address.zip)
            if package.deadline <= time(hour=10, minute=30):
                if zone in ZONE_ONE or location in routes[0]:
                    routes[0].add_vertex(location)
                    removals.append(location)
                elif zone in ZONE_TWO or location in routes[1]:
                    routes[1].add_vertex(location)
                    removals.append(location)

    # Remove reference to vertices from truck3 that have been moved to trucks 1 & 2
    for item in removals:
        routes[2].del_vertex(item)

    # using a list of vertices - txt file - and 2D list of edge weights - tsv file, generate edges
    for route in routes:
        route.generate_edges(vertices, edges)
        route.create_cycle(HUB)
    # ------------------------------------------------------


    # -------------------- SIMULATE ROUTE ------------------
    # Gather user input: when to stop the simulation and read delivery Status()
    while True:
        try:
            user_time_input = utils.convert_time(input('Enter time in HH:MM format: '))
            break
        except ValueError:
            print("Invalid entry")

    # initialize variables used to track delivery progress
    truck1 = DeliveryController(routes[0])
    truck2 = DeliveryController(routes[1])
    truck3 = DeliveryController(routes[2])

    time_iter = datetime(1, 1, 1, 8, 0, 0)
    while time_iter.time() <= user_time_input.time():
        # if user_time_input is reached, print delivery status and end sim
        if time_iter.time() == user_time_input.time():
            utils.get_delivery_status(table)
            break

        # at 8:00 Truck1 departs, includes all packages in BUNDLED
        if time_iter.time() == routes[0].get_start_time().time():
            truck1.start_route()

        # at 9:05 Truck2 departs, includes all packages marked DELAYED and TRUCK2_REQUIRED
        if time_iter.time() == routes[1].get_start_time().time():
            truck2.start_route()

        # at 12:00 Truck3 departs including all other packages
        if time_iter.time() == routes[2].get_start_time().time():
            truck3.start_route()

        # if time for a delivery, execute .make_delivery(time)
        if not routes[0].finished() and time_iter.time() == truck1.event_time.time():
            truck1.make_delivery(time_iter)

        if not routes[1].finished() and time_iter.time() == truck2.event_time.time():
            truck2.make_delivery(time_iter)

        if not routes[2].finished() and time_iter.time() == truck3.event_time.time():
            truck3.make_delivery(time_iter)

        # increment current_time by 1 second throughout delivery sim
        time_iter += timedelta(seconds=1)

    print("Truck 1: {:4.1f}\nTruck 2: {:4.1f}\nTruck 3: {:4.1f}\n------------\nTotal Miles Traveled: {:5.1f}".
          format(truck1.total_mileage, truck2.total_mileage, truck3.total_mileage,
                 truck1.total_mileage + truck2.total_mileage + truck3.total_mileage))
