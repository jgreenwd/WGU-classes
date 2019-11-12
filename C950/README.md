Jeremy Greenwood --- ID#: 000917613

WGU C950 - Data Structures and Algorithms II ---Performance Assessment: NHP1

## Algorithm Selection
### A, I.1, I.3
To start with, I attempted to utilize a graph traversal algorithm based on the following:
1. The ultimate goal is to return to the starting vertex. It must produce a cycle.
2. The vertex farthest from the starting vertex should be the "apex" of the cycle.
3. Utilize the [Nearest Neighbor algorithm](https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm) to select each succeeding vertex until the apex is reached (mid-point of the traversal).
4. Once at the apex, continue using nearest neighbor, starting from the apex.

This produced somewhat satisfactory results. Unfortunately, it was unable to meet the delivery deadline specifications. This was primarily due to the assumption that the distribution of weights in the path would be even. In order to make this algorithm work, a more effective sorting algorithm would need to be used to assign vertices to each cycle.

Instead, I proceeded to utilize Nearest Neighbor to generate the cycles. It has the benefits of being straight-forward to implement and a known track record for producing acceptable results. The drawback is that the results are not guaranteed to be the most efficient.

One alternative would have been to use [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm). This would have been more likely to produce more efficient results, but the implimentiation would have increased in complexity. The two algorithms are similar in the early stages. They principle difference is that Dijkstra's algorithm works towards a known endpoint, finding the shortest path. Nearest Neighbor works away from a starting point with no consideration for the end point.

