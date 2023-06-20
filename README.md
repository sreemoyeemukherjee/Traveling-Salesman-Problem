# Traveling-Salesman-Problem
This project aims to solve the travelling salesman problem using Prim's algorithm for minimum spanning trees.

### 1. Using an approximation algorithm to solve TSP
Given an undirected graph G = (V, E) we want to find a minimum length cycle that visits each vertex once and then returns to the start vertex. In general, this problem is NP-Hard, but here we find a cycle that may not be of minimal length but will be no more than twice the minimum. The solution that we built will run in polynomial time. 

**Approx-TSP-Tour(G,c):**
1. Select a vertex r  -> V[G] to be a root vertex
2. Compute a minimum spanning tree T for G from root r using MST-Prim(G,c,r) 
3. Let L be the list of vertices visited in a preorder tree walk of T  
4. Return the Hamiltonian cycle H that visits the vertices in the order L 

*Data File:* CrimeLatLonXY1990.csv

*Sample Output:*
Enter start date

1/14/90
Enter end date 
1/15/90  
Crime records between 1/14/90 and 1/15/90 
1340358.516,418063.7574,32887,1 ALPINE ST,AGGRAVATED ASSAULT,1/14/90,250300,40.45891236,-80.00757768 1351183.029,410482.0054,32888,211 BURROWS ST,AGGRAVATED ASSAULT,1/15/90,51000,40.43886004,-79.968006 1346775.118,410574.5466,32888,1600 FIFTH AV,AGGRAVATED ASSAULT,1/15/90,10300,40.43880904,-79.98384521 1346775.118,410574.5466,32888,1600 FIFTH AV,ROBBERY,1/15/90,10300,40.43880904,-79.98384521 1375199.387,415958.6475,32888,8100 FRANKSTOWN AV,AGGRAVATED ASSAULT,1/15/90,130600,40.45551239,79.88222498 1342709.971,416295.7223,32888,609 E OHIO ST,ROBBERY,1/15/90,230400,40.45422538,-79.99896828 1341087.095,417642.7939,32888,1400 SANDUSKY ST,AGGRAVATED ASSAULT,1/15/90,220600,40.45780829,-80.00492164 1370004.246,420807.4845,32888,6628 BRAINARD ST,AGGRAVATED ASSAULT,1/15/90,120300,40.46847265,-79.90131253 1355089.881,420937.5121,32888,422 FORTY-FOURTH ST,ROBBERY,1/15/90,90200,40.46781983,-79.95491236 
Hamiltonian Cycle (not necessarily optimum):
0  6  5  2  1  8  7  4  3  0   
Length Of cycle:  16.354300334021968 miles 

***Note:***  Two equally good programs may produce different results. It is the case that Prim will remove nodes from the heap in order of small to large. But the children of a particular node in Prim’s tree may be ordered differently – producing different tours. These tours may be of different length but all will be less than twice the value of the optimal tour. 
### 2. Finding an optimal solution to TSP
In this part we find an optimal tour using a brute force approach. 

*Sample Output:*
Enter start date 
1/1/90
Enter end date
1/1/90 
Crime records between 1/1/90 and 1/1/90 
1348656.471,399538.5342,32874,100 BONIFAY ST,ROBBERY,1/1/90,160600,40.40865518,-79.9760891 1359951.481,410726.1273,32874,320 SCHENLEY RD,ROBBERY,1/1/90,140100,40.44013011,-79.93653583 1357049.25,418429.9175,32874,4779 LIBERTY AV,ROBBERY,1/1/90,80900,40.46107271,-79.94764804 1361745.729,419343.2848,32874,5600 PENN AV,AGGRAVATED ASSAULT,1/1/90,111500,40.46389868,-79.93085611 
Hamiltonian Cycle (not necessarily optimum): 
0  1  2  3  0   
Length Of cycle: 9.972129467002565 miles 
Looking at every permutation to find the optimal solution 
The best permutation
0  2  3  1  0  
Optimal Cycle length = 9.499048743818797 miles 
### 3. Displaying the output to Google Earth
In this part we generate a KML file that contains both tours (the first tour will shown in blue and the second optimal tour shown in red). This KML file, when loaded into Google Earth, will show both tours as below. 
![](https://github.com/sreemoyeemukherjee/Traveling-Salesman-Problem/blob/main/Screenshots/ScreenshotGoogleEarth.jpg)
