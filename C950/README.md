Jeremy Greenwood --- ID#: 000917613

WGU C950 - Data Structures and Algorithms II ---Performance Assessment: NHP1


## SCENARIO

The Western Governors University Parcel Service (WGUPS) needs to determine the best route and delivery distribution for their Daily Local Deliveries (DLD) because packages are not currently being consistently delivered by their promised deadline. The Salt Lake City DLD route has three trucks, two drivers, and an average of 40 packages to deliver each day; each package has specific criteria and delivery requirements. <br><br>
Your task is to determine the best algorithm, write code, and present a solution where all 40 packages, listed in the attached “WGUPS Package File,” will be delivered on time with the least number of miles added to the combined mileage total of all trucks. The specific delivery locations are shown on the attached “Salt Lake City Downtown Map” and distances to each location are given in the attached “WGUPS Distance Table.” <br><br>
While you work on this assessment, take into consideration the specific delivery time expected for each package and the possibility that the delivery requirements—including the expected delivery time—can be changed by management at any time and at any point along the chosen route. In addition, you should keep in mind that the supervisor should be able to see, at assigned points, the progress of each truck and its packages by any of the variables listed in the “WGUPS Package File,” including what has been delivered and what time the delivery occurred. <br><br>
The intent is to use this solution (program) for this specific location and to use the same program in many cities in each state where WGU has a presence. As such, you will need to include detailed comments, following the industry-standard Python style guide, to make your code easy to read and to justify the decisions you made while writing your program. <br><br>
Assumptions: 
* Each truck can carry a maximum of 16 packages. 
* Trucks travel at an average speed of 18 miles per hour. 
* Trucks have a “infinite amount of gas” with no need to stop. 
* Each driver stays with the same truck as long as that truck is in service. 
* Drivers leave the hub at 8:00 a.m., with the truck loaded, and can return to the hub for packages if needed. The day ends when all 40 packages have been delivered. 
* Delivery time is instantaneous, i.e., no time passes while at a delivery (that time is factored into the average speed of the trucks). 
* There is up to one special note for each package. 
* The wrong delivery address for package #9, Third District Juvenile Court, will be corrected at 10:20 a.m. The correct address is 410 S State St., Salt Lake City, UT 84111. 
* The package ID is unique; there are no collisions. 
* No further assumptions exist or are allowed. 


## REQUIREMENTS

### Section 1: Programming/Coding 

##### A. Identify the algorithm that will be used to create a program to deliver the packages and meets all requirements specified in the scenario. 

##### B. Write a core algorithm overview, using the sample given, in which you do the following: 

1. Comment using pseudocode to show the logic of the algorithm applied to this software solution. 
2. Apply programming models to the scenario. 
3. Evaluate space-time complexity using Big O notation throughout the coding and for the entire program. 
4. Discuss the ability of your solution to adapt to a changing market and to scalability. 
5. Discuss the efficiency and maintainability of the software. 
6. Discuss the self-adjusting data structures chosen and their strengths and weaknesses based on the scenario. 

##### C. Write an original code to solve and to meet the requirements of lowest mileage usage and having all packages delivered on time. 

1. Create a comment within the first line of your code that includes your first name, last name, and student ID. 
2. Include comments at each block of code to explain the process and flow of the coding. 

##### D. Identify a data structure that can be used with your chosen algorithm to store the package data. 
1. Explain how your data structure includes the relationship between the data points you are storing. 
Note: Do NOT use any existing data structures. You must design, write, implement, and debug all code that you turn in for this assessment. Code downloaded from the internet or acquired from another student or any other source may not be submitted and will result in automatic failure of this assessment. 

##### E. Develop a hash table, without using any additional libraries or classes, with an insertion function that takes the following components as input and inserts the components into the hash table:

• package ID number <br>
• delivery address <br>
• delivery deadline <br>
• delivery city <br>
• delivery zip code <br>
• package weight <br>
• delivery status (e.g., delivered, in route) <br>

##### F. Develop a look-up function that takes the following components as input and returns the corresponding data elements: 

• package ID number <br>
• delivery address <br>
• delivery deadline <br>
• delivery city <br>
• delivery zip code <br>
• package weight <br>
• delivery status (e.g., delivered, in route) <br>

##### G. Provide an interface for the insert and look-up functions to view the status of any package at any time. This function should return all information about each package, including delivery status. 

1. Provide screenshots to show package status of all packages at a time between 8:35 a.m. and 9:25 a.m. 
2. Provide screenshots to show package status of all packages at a time between 9:35 a.m. and 10:25 a.m. 
3. Provide screenshots to show package status of all packages at a time between 12:03 p.m. and 1:12 p.m. 

##### H. Run your code and provide screenshots to capture the complete execution of your code. 

### Section 2: Annotations 

##### I. Justify your choice of algorithm by doing the following: 

1. Describe at least two strengths of the algorithm you chose. 
2. Verify that the algorithm you chose meets all the criteria and requirements given in the scenario. 
3. Identify two other algorithms that could be used and would have met the criteria and requirements given in the scenario. <br>
    a. Describe how each algorithm identified in part I3 is different from the algorithm you chose to use in the solution. 

##### J. Describe what you would do differently if you did this project again. 

##### K. Justify your choice of data structure by doing the following: 

1. Verify that the data structure you chose meets all the criteria and requirements given in the scenario. <br>
    a. Describe the efficiency of the data structure chosen. <br>
    b. Explain the expected overhead when linking to the next data item. <br>
    c. Describe the implications of when more package data is added to the system or other changes in scale occur. <br>

2. Identify two other data structures that can meet the same criteria and requirements given in the scenario. <br>
    a. Describe how each data structure identified in part K2 is different from the data structure you chose to use in the solution. <br>


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
      1. Package ID
      2. Street address
      3. City
      4. State Abbreviation
      5. Five-digit Postal Code
      6. Delivery deadline in
      7. Package weight in pounds
      8. Notes for package delivery exceptions
  
  2. TXT file of delivery addresses. Addresses consist of street address and a five-digit postal code. It is important that this list be ordered and coincide with the ordering for the following TSV file.
  
  State and city are omitted. The address and postal code are separated by a single space. The postal code should be the last 5 characters for each entry. Each entry should be separated by a new-line character. 
  
  3. TSV file of floating point data, representing distances between street addresses. The rows and columns each represent a different address. The order for rows and columns should be identical, ie. row A represents the same address as column A, row B is the same as column B, etc. The intersection of each row and column represents the distance between the respective addresses, ie. row A intersecting column A is 0.0 (same point), row A intersecting column B is some positive float (distance between those 2 points). Each datapoint should be separated by a tab character. Each entry should end with a new-line character. It is not necessary for any data to be entered after the point of mutual intersection on each line, eg. row D intersect column D. Any data entered after such simply be ignored.

Program output is rendered on-screen at the workstation. In this scenario, it is unnecessary to provide any export functionality.

It will be necessary for facility personnel to generate and supply the TXT and TSV files daily. This could be automated before production. However, at present, we lack sufficient information to devise an implementation strategy for such automation.

### B3
This runs with a worst-case time and space complexity of O(n)<sup>2</sup>. More detailed analysis is available in route.py, starting at line 82.

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
