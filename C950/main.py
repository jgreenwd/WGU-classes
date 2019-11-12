# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

import utility_functions as utils
from datetime import datetime, timedelta, time
from location import Location
from route import Route
from package import Status
from delivery import DeliveryController

# TODO: finish implementing delivery change implementation
#       move delivery change to delivery controller


if __name__ == '__main__':
    # --------------- ESTABLISH PARAMETERS -----------------
    # starting location for delivery routes
    HUB = Location("HUB", "Salt Lake City", "UT", "84107")

    # Times each truck departs the HUB
    TRUCK2_DEPARTURE = datetime(1, 1, 1, 8, 0, 0)                           # Route[0]
    TRUCK1_DEPARTURE = datetime(1, 1, 1, 9, 0, 5)                           # Route[1]
    TRUCK3_DEPARTURE = datetime(1, 1, 1, 12, 0, 0)                          # Route[2]

    # average rate of travel in MPH
    RATE_OF_TRAVEL = 18.0
    # ------------------------------------------------------

    # -------------------- BUILD CONTAINERS ----------------
    # Retrieve Graph and Table data
    data = utils.load_file("WGUPS Package File.csv")                        # Packages & Locations
    indices = utils.load_file("vertices.txt")                               # Indices of Vertices
    weights = [line.split('\t') for line in utils.load_file("matrix.tsv")]  # Weights for Edges

    # build Graph() // HashTable() // Route()s
    graph, table = utils.parse_delivery_input(data, indices, weights)
    graph.add_vertex(HUB)       # HUB not found in package data, must be added to Graph
    routes = [Route(indices, weights), Route(indices, weights), Route(indices, weights)]
    # ------------------------------------------------------

    # --------------------- SORT PACKAGES ------------------
    # sort packages to appropriate truck
    # mark packages arriving at 9:05 as DELAYED

    # PACKAGE DELIVERY EXCEPTIONS found in package.notes
    # NOTE: HUB personnel will need to populate these lists daily
    TRUCK2_REQUIRED = ['ID: 3 \tWeight:  2.0', 'ID: 18\tWeight:  6.0',
                       'ID: 36\tWeight: 88.0', 'ID: 38\tWeight:  9.0']
    DELAYED = ['ID: 6 \tWeight: 88.0', 'ID: 25\tWeight:  7.0',
               'ID: 28\tWeight:  7.0', 'ID: 32\tWeight:  1.0']
    BUNDLED = ["ID: 13\tWeight:  2.0", "ID: 14\tWeight: 88.0", "ID: 15\tWeight:  4.0",
               "ID: 16\tWeight: 88.0", "ID: 19\tWeight: 37.0", "ID: 20\tWeight: 37.0"]

    # Given the key for each exception, find the delivery address, add to appropriate route
    for package_key in BUNDLED:
        pkg = table.search(package_key)
        if pkg.address not in routes[0].vertices:
            routes[0].add_vertex(pkg.address)
        # mark packages as LOADING
        for p in pkg.address.get_package_keys():
            tmp = table.search(p)
            tmp.load()

    for package_key in TRUCK2_REQUIRED:
        pkg = table.search(package_key)
        if pkg.address not in routes[0].vertices:
            # print(pkg.address, pkg.address.get_package_keys())
            routes[0].add_vertex(pkg.address)
        # mark packages as LOADING
        for p in pkg.address.get_package_keys():
            tmp = table.search(p)
            tmp.load()

    for package_key in DELAYED:
        pkg = table.search(package_key)
        routes[1].add_vertex(pkg.address)
        # mark packages as DELAYED
        for p in pkg.address.get_package_keys():
            tmp = table.search(p)
            tmp.delay()

    # Load remaining packages
    for vertex in graph:
        if len(vertex.get_package_keys()) == 0:
            continue
        any_early = False
        for package in vertex.get_package_keys():
            pkg = table.search(package)
            if pkg.get_status() != Status(0).name:
                continue
            elif pkg.deadline <= datetime(1,1,1,10,30,0).time():
                any_early = True
        if any_early:
            routes[1].add_vertex(vertex)
            any_early = False
        elif vertex not in routes[0] and vertex not in routes[1]:
            routes[2].add_vertex(vertex)

    for i, route in enumerate(routes):
        # create reference to avg speed
        route.set_rate_of_travel(RATE_OF_TRAVEL)
        # add HUB to each Route
        route.add_vertex(HUB)
        # set time each truck leaves HUB
        route.create_cycle(HUB)
        if i == 0:
            route.set_start_time(TRUCK2_DEPARTURE)
        elif i == 1:
            route.set_start_time(TRUCK1_DEPARTURE)
        else:
            route.set_start_time(TRUCK3_DEPARTURE)

    # ------------------------------------------------------

    # -------------------- SIMULATE ROUTE ------------------
    # Gather user input: when to stop the simulation and read delivery Status()
    # single package or all packages
    modes = {'S': 0, 'A': 1}
    user_mode, user_time, single_package = utils.mode_query(modes, table)

    # initialize variables used to track delivery progress
    truck2 = DeliveryController(routes[0], table)
    truck1 = DeliveryController(routes[1], table)
    truck3 = DeliveryController(routes[2], table)

    time_iter = datetime(1, 1, 1, 8, 0, 0)
    while time_iter.time() <= user_time.time():
        # if user_time_input is reached, print delivery status and end sim
        if time_iter.time() == user_time.time():
            if user_mode == 0:
                utils.print_single_delivery_status(table, single_package)
            else:
                utils.print_all_delivery_status(table)
                break

        # at 8:00 Truck2 departs, includes all packages in BUNDLED
        if time_iter.time() == routes[0].get_start_time().time():
            truck2.start_route()

        # at 9:05 Truck1 departs, includes all packages marked DELAYED and TRUCK2_REQUIRED
        if time_iter.time() == routes[1].get_start_time().time():
            truck1.start_route()

        # at 10:00 Truck3 departs including all other packages (Truck2 has returned by then)
        if time_iter.time() == routes[2].get_start_time().time():
            truck3.start_route()

        # at 10:20 Address for Package.ID 9 changes
        if time_iter.time() == datetime(1,1,1,10,20,0).time():
            location = Location("410 S State St".upper(), "Salt Lake City".upper(), "UT".upper(), "84111")
            package = table.search("ID: 9 	Weight:  2.0")
            truck1.alter_delivery(package, location, graph)

        # if time for a delivery, execute .make_delivery(time)
        if not routes[0].finished() and time_iter.time() == truck2.event_time.time():
            truck2.make_delivery(time_iter)

        if not routes[1].finished() and time_iter.time() == truck1.event_time.time():
            truck1.make_delivery(time_iter)

        if not routes[2].finished() and time_iter.time() == truck3.event_time.time():
            truck3.make_delivery(time_iter)

        # increment current_time by 1 second throughout delivery sim
        time_iter += timedelta(seconds=1)

    if user_mode == 1:
        print("Truck 1: {:4.1f}\nTruck 2: {:4.1f}\nTruck 3: {:4.1f}\n------------\nTotal Miles Traveled: {:5.1f}".
                format(truck1.total_mileage, truck2.total_mileage, truck3.total_mileage,
                     truck1.total_mileage + truck2.total_mileage + truck3.total_mileage))
