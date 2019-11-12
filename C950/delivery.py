# Jeremy Greenwood ----- ID#: 000917613
# Mentor: Rebekah McBride
# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1

from datetime import timedelta
from package import Status


class DeliveryController:
    def __init__(self, route, table):
        """ Create Delivery Route Manager.

        :param route: Route
        :param table: HashTable """
        self._route = route
        self._current_edge = self._route.order[0]
        self.event_time = self._route.get_start_time()
        self.total_mileage = 0
        self._table = table

    def start_route(self):
        """ Initialize variables & advance next_node reference to Destination(). """
        # set all packages in truck as OUT_FOR_DELIVERY
        for edge in self._route.order:
            node = edge.next_node
            for pkg in node.get_package_keys():
                package = self._table.search(pkg)
                package.ofd()
        # mark time for first delivery to execute
        self.event_time += timedelta(hours=float(self._current_edge.weight) / self._route.rate_of_travel)

    def make_delivery(self, current_time):
        """ Mark packages as DELIVERED, set current_node, advance next_node.

        :param current_time: datetime """
        # advance mileage to destination
        self.total_mileage += float(self._current_edge.weight)

        # mark packages at vertex as delivered
        for package in self._route.get_next_node().get_package_keys():
            pkg = self._table.search(package)
            pkg.deliver(self.event_time)

        # advance to next edge
        self._current_edge = self._route.get_next_edge()

        # update time of next delivery event
        self.event_time = current_time + timedelta(hours=float(self._current_edge.weight) / self._route.rate_of_travel)

        # if route is complete, mark finished
        if self._current_edge == self._route.order[len(self._route.order) - 1]:
            self.total_mileage += float(self._current_edge.weight)
            self._route.finish_route()

    def alter_delivery(self, package, location, graph):
        """ Edit route.order to remove 1 vertex and add another.

        :param package: Package
        :param location: Vertex
        :param graph: Graph """

        # remove package_key from old location
        old_location = package.address                          # get address
        old_location = self._route.get_vertex(old_location)     # get obj reference
        old_location.del_package_key(str(package))              # alter keys


        # add package_key to new location
        package.address = location                              # update address
        new_location = graph.get_vertex(location)               # get obj reference
        new_location.add_package_key(str(package))              # update keys

        if package.get_status() == Status(4):
            print('retrieve')  # retrieve delivered package
        else:
            self._route.alter_course(new_location)
