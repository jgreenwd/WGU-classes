# WGU C950 - Data Structures and Algorithms II
# Performance Assessment: NHP1
# Jeremy Greenwood
# Student ID#: 000917613
# Mentor: Rebekah McBride


class DeliveryController:
    def __init__(self, route, rate):
        """ Create Delivery Route Manager.

        :param route: reference to List() of Destinations()
        :param rate: rate of travel in MPH
        """
        self.route = route
        self.rate = rate
        self.current_node = self.route.get_current_node()
        self.next_node = self.route.get_current_node()
        self.delivery_sequence = self.route.order
        self.event_time = self.route.get_start_time()
        self.wait_time = self.current_node.get_weight(self.next_node) / self.rate
        self._total_mileage = 0

    def start_route(self):
        """ Initialize variables & advance next_node reference to Destination(). """
        for loc in self.delivery_sequence:
            for package in loc.packages:
                package.ofd()
        self.current_node = self.route.get_current_node()
        self.next_node = self.route.get_next_node()
        self.event_time = self.route.get_start_time()
        self.wait_time = self.current_node.get_weight(self.next_node) / self.rate
        self._total_mileage += self.current_node.get_weight(self.next_node)

    def make_delivery(self, current_time):
        """ Mark packages as DELIVERED, set current_node, advance next_node.

        :param current_time: datetime of time of delivery
        """
        for package in self.next_node.packages:
            package.deliver()
            package.time_of_delivery = str(current_time.time())
        self.event_time = current_time
        self.current_node = self.route.get_current_node()
        self.next_node = self.route.get_next_node()
        self.wait_time = self.current_node.get_weight(self.next_node) / self.rate
        self._total_mileage += self.current_node.get_weight(self.next_node)

    def get_total_distance(self):
        """ Return total distance traveled in route. """
        return self._total_mileage
