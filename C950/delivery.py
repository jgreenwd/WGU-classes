# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride

from datetime import timedelta


class DeliveryController:
    def __init__(self, route):
        """ Create Delivery Route Manager.

        :param route: reference to Route()
        :param rate: rate of travel in MPH
        """
        self._route = route
        self._current_edge = self._route.order[0]
        self.event_time = self._route.get_start_time()
        self.total_mileage = 0

    def start_route(self):
        """ Initialize variables & advance next_node reference to Destination(). """
        # set all packages in truck as OUT_FOR_DELIVERY
        for edge in self._route.order:
            node = edge.next_node
            for package in node.packages:
                package.ofd()

    def make_delivery(self, current_time):
        """ Mark packages as DELIVERED, set current_node, advance next_node.

        :param current_time: datetime of time of delivery
        """
        # advance mileage to destination
        self.total_mileage += self._current_edge.weight

        # update time of next delivery event
        self.event_time = current_time + timedelta(hours=self._current_edge.weight / self._route.rate_of_travel)

        print("({:5.5} to {:5.5}) : {}/{}miles".format(str(self._current_edge.prev_node),
                                                       str(self._current_edge.next_node), self._current_edge.weight,
                                                       self.total_mileage))

        # mark packages at vertex as delivered
        for package in self._route.get_next_node().packages:
            package.deliver(self.event_time)

        # advance to next edge
        try:
            self._current_edge = self._route.get_next_edge()
        except AttributeError:
            self.total_mileage -= self._current_edge.weight
            self._route.finish_route()
