Jeremy Greenwood --- ID#: 000917613

WGU C950 - Data Structures and Algorithms II ---Performance Assessment: NHP1

## Algorithm Selection
### A
Initially, I attempted to devise a graph traversal algorithm based on the following:
1. The ultimate goal is to return to the starting vertex. It must produce a cycle.
2. The vertex farthest from the starting vertex should be the "apex" of the cycle.
3. Utilize the [Nearest Neighbor algorithm](https://en.wikipedia.org/wiki/Nearest_neighbour_algorithm) to select each succeeding vertex until the apex is reached (mid-point of the traversal).
4. Once at the apex, continue using nearest neighbor, starting from the apex, eventually returning to the point of origin.

This produced somewhat satisfactory results. Unfortunately, it was unable to meet the delivery deadline specifications. This was primarily due to the assumption that the distribution of weights in the path would be even. In order to make this algorithm work, a more effective package sorting algorithm would need to be used to assign vertices to each cycle. This sorting algorithm is beyond the scope of this project and was not pursued further.

Instead, I proceeded to utilize Nearest Neighbor to generate the cycles. It has the benefits of being straight-forward to implement and a known track record for producing acceptable results. The drawback is that the results are not guaranteed to be the most efficient.

One alternative would have been to use [Dijkstra's algorithm](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm). This would have been more likely to produce more efficient results, but the implementiation would have increased in complexity. The two algorithms are similar in their initial stages. The principle difference is that Dijkstra's algorithm works towards a known endpoint, finding the shortest path. Nearest Neighbor works away from a starting point with no real consideration for the end point.

Another alternative would have been to use [Breadth-First Search](https://en.wikipedia.org/wiki/Breadth-first_search). Again, this would have been more likely to produce more efficient results, and the implementation would have again increased in complexity. Primarily, BFS differs from Nearest Neighbor in that it reads ahead before offering a solution by use of a queue. Nearest Neighbor simply compares the weight of the remaining unvisited edges and returns the lowest. In theory, BFS could also result in visiting the same vertex multiple times in order to complete the path. In practice, this is easily avoided.

## Algorithm Overview
### B1
Comments regarding my implementation of Nearest Neighbor can be found in route.py within the create_cycle() method, starting on line 70. 

A brief description of Nearest Neighbor:
```
  Initialize all vertices as unvisited                          O(n)
    
  Choose an initial value for current_vertex                    O(n)
  
  While unvisited vertices remain                               O(n)
      find the nearest, unvisited neighbor of current_vertex      - O(n)
      mark current_vertex as visited                              - O(1)
      set nearest neighbor as current_vertex                      - O(1)
      
```

### B2
The host environment for this program is a single workstation. 

Input data is constrained to the following:
  1. CSV file of daily package details. Each item below should be separated with a comma. Each package listing should be followed with a new-line character.
      a. Package ID
      b. Street address
      c. City
      d. State Abbreviation
      e. Five-digit Postal Code
      f. Delivery deadline in
      g. Package weight in pounds
      h. Notes for package delivery exceptions
  2. TXT file of delivery addresses. Addresses consist of street address and a five-digit postal code. State and city are omitted. The address and postal code are separated by whitespace. The postal code should be the last 5 characters for each entry. Each entry should be separated by a new-line character. It is important that this list be ordered and coincide with the ordering for the following TSV file.
  3. TSV file of floating point data, representing distances between street addresses. The rows and columns each represent a different address. The order for rows and columns should be identical, ie. row A represents the same address as column A, row B is the same as column B, etc. The intersection of each row and column represents the distance between the respective addresses, ie. row A intersecting column A is 0.0 (same point), row A intersecting column B is some positive float (distance between those 2 points). Each datapoint should be separated by a tab character. Each entry should end with a new-line character. It is not necessary for any data to be entered after the point of mutual intersection on each line. Any data entered after such simply be ignored.

Program output is rendered on-screen at the workstation. In this scenario, it is unnecessary to provide any export functionality.

It will be necessary for facility personnel to generate and supply the TXT and TSV files daily. This could be automated before production. However, at present, we lack sufficient information to devise an implementation strategy for such automation.

### B3
This runs with a worst-case time and space complexity of O(n)<sup>2</sup>. More detailed analysis is available in route.py, starting at line 74.

The minimum number of edges would be equal to the number of vertices: O(n) space complexity. However, my graph structure creates complete graphs. A complete graph, by definition, necessitates that there be n(n-1)/2 edges. This increases the space complexity from O(n) to slightly less than O(n)<sup>2</sup>.
> <br>n(n-1)/2
> <br>= (1/2) * n * (n-1)
> <br>= n(n-1)
> <br>= n<sup>2</sup> - n
> <br>< n<sup>2</sup>

### B4
The algorithm may suffer performance degradation when handling large datasets. This is a result of the O(n)<sup>2</sup> complexities. Datasets with similar size to the sample set, would present no performance issues in production. In fact, datasets could easily doulbe in size with no significant decrease in performance. However, if the same algorithm were to be used on a larger scale (perhaps for a regional distribution center), the exponential increase in number of calculations would become problematic and would most definitely be inadequate.

### B5
I have tried to minimize the use of nested loops where possible, to minimize inefficiencies. The biggest inefficiencies occur when access to a __Vertex__, __Edge__, or __Location__ object is needed. This has resulted in several methods that operate in O(n) time, but which could theoretically operate in O(1).
```        
  for candidate in container:                                   O(n)
      if candidate == search_value:                               - O(1)
          return candidate
      return None
```

This issue is mostly negated when searching for __Packages__, via the use of a __HashTable__ for __Package__ storage. This has a theoretical access time of O(1) for each element. Since I chose to implement a chaining __HashTable__, the actual time is slightly higher. This is a result of collisions produced from the method I used to generate the hash value. I attempted to use the built-in hash function from Python. Unfortunately, I was not able to get consistent results while using it.

I made use of a weighted __Graph__ structure to organize the delivery locations. My implementation of __Graph__ uses __Sets__ as containers for __Vertex__ and __Edge__ members. My initial thought was that each __Vertex__ should be unique and will only appear once in the __Graph__. I assumed the same for the edges. This proved problematic when I later needed to revisit the same __Vertex__. The use of __Set__ may also have the added issue of affecting scalability; many operations performed on __Vertex__ and __Edge__ require a cast to __List__. By itself, this is insignificant. However, at large scales, the cumulative effect of this and the lack of direct access to elements could negatively affect performance.

### B6
I attempted to use self-adjusting data structures throughout, most notably when adding and removing __Vertex__, __Location__, and __Edge__ members. As an example, whenever a __Vertex__ is added to a __Graph__, an __Edge__ is generated connecting it to every other __Vertex__. This is an O(n) operation that results in all instances of __Graph__ being complete graphs. This ensures that every __Graph__ has a complete adjacency list from which to work. Unfortunately, it does create added overhead and a larger program footprint. 

Maintenance is rendered somewhat easier by extracting complex functions and methods to utility_functions.py and to other class files. The interface for the __Route__ class could benefit by being integrated and merged with __DeliveryController__.

### K - HashTable
Access time: Theoretically, access time of O(1) is possible. In practice, actual access time is slightly higher. This is a result of collisions generated with the _generate_hash() function.
```
generate hash (target value)                                    O(1)

read table [hash value]                                         O(1)
if length of table[hash value] is greater than 1                O(1)
    linear search for target value                                - O(n)
```
Linear search stands to increase access time. In practice the increase is negligible. The actual amount of increase is dictated by the size of the bucket at the hashed index. With adequately implemented hash functions, the bucket will have a functional maximum of 3. While still technically O(n), the result is returned much faster than O(n) would typically imply.

Memory requirements: The __HashTable__ at declaration accepts an integer representing table capacity (default value of 64). At creation, memory is allocated for 133% of capacity, giving a load factor of 75%. This equates roughly to O(n) space complexity. If capacity is known, space complexity will remain O(n). If the load factor changes, memory requirements can potentially negatively affect performance by increasing access time. This occurs as a byproduct of the hash function's use of capacity in determining hash values. For optimal efficiency, it is necessary for the user to have a reasonable approximation of needed capacity before deploying to production.

Bandwidth: The application operates on a single workstation and does not require bandwidth for network communication.

## Annotations
### J
If I were to do this assignment again, the biggest change would be my __Route__ class. I chose to develop it as a child class of __Graph__. I now see redundancies that could have been eliminated by merging __Route__ and __DeliveryController__. I also think this might make the interface slightly less cumbersome. I also question the use of a separate __Edge__ class. I think the same results could have been achieved with a simpler implementation. Lastly, I would change the __Route__ **order** property to utilize a __Queue__. This would help in alleviating the problem with revisiting a __Vertex__.
