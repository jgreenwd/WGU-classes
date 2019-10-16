import math

# Point class
class Point:
    def __init__(self):
        self.x = 0
        self.y = 0

    def shift(self,x,y):
        self.x += x
        self.y += y

    def toString(self):
        return '(' + str(self.x) + ',' + str(self.y) + ')'

def dist_between(pt1, pt2):
  return math.sqrt( (pt1.x - pt2.x)**2 + (pt1.y - pt2.y)**2)

# Main program
# Read in x and y for Point P
p = Point()
p.x = int(input())
p.y = int(input())

# Read in num of steps to be taken in X and Y directions
fwd_x = int(input())
fwd_y = int(input())

# Read in num of steps to be taken (backwards) every 3 steps
back = -int(input())

# Write dynamic programming algorithm
closest_point = Point()
current_point = Point()
counter = 1

while closest_point.x < p.x:
  if counter % 3 == 0:
    current_point.shift(back,back)
  else:
    current_point.shift(fwd_x,fwd_y)

  if dist_between(current_point,p) < dist_between(closest_point,p):
    closest_point.x = current_point.x
    closest_point.y = current_point.y

  if current_point.x == p.x:
    break
  counter += 1

# Output
print('Point P: ' + p.toString())
print('Arrival point: ' + closest_point.toString())
print('Distance between P and arrival: ' + '{:7.6f}'.format(dist_between(closest_point,p)))
print('Number of iterations: ' + str(counter - counter // 3))
