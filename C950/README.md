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

One alternative would have been to use [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm). This would have been more likely to produce more efficient results, but the implimentiation would have increased in complexity. The two algorithms are similar in the early stages. The principle difference is that Dijkstra's algorithm works towards a known endpoint, finding the shortest path. Nearest Neighbor works away from a starting point with no real consideration for the end point.

Another alternative would have been to use [Breadth-First Search](https://en.wikipedia.org/wiki/Breadth-first_search). Again, this would have been more likely to produce more efficient results, and the implementation would have again increased in complexity. Primarily, BFS differs from Nearest Neighbor in that it reads ahead before offering a solution by use of a queue. Nearest Neighbor simply compares the weight of the remaining unvisited edges and returns the lowest. In theory, BFS could also result in visiting the same vertex multiple times in order to complete the path. In practice, this is easily avoided.

## Algorithm Overview
### B
My implementation of Nearest Neighbor works as follows:
```
  empty list of edges_to_traverse
  
  set all vertices as unvisited
    
  current_vertex = starting_vertex
  current_edge = None
  
  while unvisited vertices remain
      visit current_vertex
      find_nearest_neighbor() of current_vertex
      current_edge = edge( current_vertex and nearest_neighbor )
      add current_edge to edges_to_traverse
      
  return edges_to_visit
```

This runs in O(n) time with O(n) space complexity.

I have tried to minimize the use of nested loops where possible, to minimize inefficiencies. The biggest inefficiencies occur when access to a vertex, edge, or location is needed. This has resulted in several methods that operate in O(n) time, but which could theoretically operate in O(1).
```        
  for candidate in container:
      if candidate == search_value:
          return candidate
      return None
```

This issue is mostly negated when searching for packages, via the use of a hash table for package storage. This has a theoretical access time of O(1) for each element. Since I chose to implement a chaining hash table, the actual time is slightly higher. This is a result of collisions produced from the method I used to generate the hash value. I attempted to use the built-in hash function from Python. Unfortunately, I was not able to get consistent results while using it.
