# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class DeliveryController:
    def __init__(self, route):
        """ Create Delivery Route Manager.

        :param route: reference to Route()
        :param rate: rate of travel in MPH
        """
        self._route = route
        self._current_edge = self._route.order[0]
        self._next_edge = self._current_edge
        self.event_time = self._route.get_start_time()
        self.wait_time = self._current_edge.weight / self._route._rate_of_travel
        self.total_mileage = 0

    def start_route(self):
        """ Initialize variables & advance next_node reference to Destination(). """
        for edge in self._route.order:
            node = edge.next_node
            for package in node.packages:
                package.ofd()
        self._current_edge = self._next_edge
        self._next_edge = self._route.get_next_edge()
        self.event_time = self._route.get_start_time()
        self.wait_time = self._current_edge.weight / self._route._rate_of_travel
        self.total_mileage += self._current_edge.weight

    def make_delivery(self, current_time):
        """ Mark packages as DELIVERED, set current_node, advance next_node.

        :param current_time: datetime of time of delivery
        """
        # if returning to the beginning, mark route as finished
        if self._route.get_next_node() == self._route._starting_vertex:
            self._route.finished = True

        # deliver packages at current address and advance
        for package in self._route.get_current_node().packages:
            package.deliver(current_time)
        self.event_time = current_time
        self._current_edge = self._next_edge
        self._next_edge = self._route.get_next_edge()
        self.wait_time = self._current_edge.weight / self._route._rate_of_travel
        self.total_mileage += self._current_edge.weight
