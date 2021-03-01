### Table of Contents  
[1. Binary Search Trees](https://github.com/dtlancaster/data-strucs-demos/blob/master/README.md#1-binary-search-trees)  
[2. Dynamic Sequences](https://github.com/dtlancaster/data-strucs-demos/blob/master/README.md#2-dynamic-sequences)  
[3. Graphs](https://github.com/dtlancaster/data-strucs-demos/blob/master/README.md#3-graphs)  
[4. Hash Functions](https://github.com/dtlancaster/data-strucs-demos/blob/master/README.md#4-hash-functions)  
[5. Hash Tables](https://github.com/dtlancaster/data-strucs-demos/blob/master/README.md#5-hash-tables)

## 1. Binary Search Trees

I've built a binary search tree and use it to solve a problem involving runway requests by airlines. In this program, I demonstrate that actual data consisting of large meta-information can be stored outside the data structure, while the binary search tree can be keyed on a particular field within the data. Several binary search tree operations beyond a dictionary data structure, like computing the minimum element in a set of the predecessor element of a key within the set. Note that in this implementation, it is not required to balance the tree. The input data will be sufficiently random to keep the tree balanced.

### I. Binary Search Tree

A binary search tree is a data structure that maintains a collection of keys (along with associated values) which encodes a sorted ordering of the elements within its inorder traversal. This allows us to perform several operations that are not possible with hash tables.


The binary search tree is keyed on an integer ```time```. The ```Node``` class is implemented with two data fields--```time``` and ```req_index```. It contains appropriate accessors and mutators. The second part of this implementation is dependent on access to airline runway requests, which contain meta-data (flight name, source, destination, etc) for each request. The requests are stored as an array, and implement a binary search tree keyed on ```time``` with associated value ```req_index``` that points to the index of the request in our data array. All our needed operations for our binary search tree are implemented in the file ```BST.java```. The binary search tree supports the following operations:

```insert(time, req_index)```: This method inserts an integer ```time``` along with the associated value ```req_index``` into the tree. Note that the tree is keyed on ```time```.

```pred(time)```: This returns the node within the binary search tree that is the predecessor of ```time```, i.e., the largest integer smaller or equal to ```time```. This returns ```null``` if there is no predecessor.

```succ(time)```: This returns the node within the binary search tree that is the successor of ```time```, i.e., the smallest integer greater or equal to ```time```. This returns ```null``` if there is no successor.

```min()```: This returns pointer to the minimum element in the binary search tree, or ```null``` if the tree is empty.

```max()```: This returns a pointer to the maximum element in the binary search tree, or ```null``` if the tree is empty.

```delete(time)```: This removes the node with the key ```time``` if it is present, otherwise does nothing.

```print()```: This method prints the contents of the tree in sorted order, by doing an inorder traversal of the tree.

Note that most of the above methods are public wrapper methods for private recursive methods. From the user's point of view, only these public methods are called--but since all operations in a binary search tree are recursive, they need to be implemented as private methods.

Also, note the version of both the ```pred``` and ```succ``` methods. We are looking for the deepest ancestor who is smaller and larger than the given ```time``` respectively. While you traverse from the root of the tree in search of ```time```, we must keep track of this deepest ancestor. This can be done iteratively.

The method ```delete``` can be written in a recursive way. Suppose we try to delete from a subtree rooted at node ```x```. The recursive method always returns the root of the subtree in which we perform ```delete```. As a base case, we return ```null``` if ```x``` is ```null``` (deleting from an empty tree). If ```x``` contains key ```time```, then we have three cases:

1. **x is a leaf:** Then return ```null```. Note that the parent's appropriate child pointer will be updated to ```null``` therefore removing ```x``` from the tree.
2. **x has one child y:** Then return ```y```. Note that the parent's appropriate child pointed will be updated to ```y```, thereby removing ```x``` from the tree.
3. **x has two children:** In this case, we find the successor ```s``` of ```x```, then copy ```s```'s data fields onto ```x```, and delete ```s``` from the right subtree of ```x``` (Note that ```s``` can only be in ```x```'s right subtree as its key is larger than ```x```). This will cause an update to ```x```'s right child.

The two recursive changes remain, that of deleting ```time``` from either the left or right subtree of the root depending on the comparison between ```time``` and th ekey stored in node ```x```.

### II. Runway Reservation System

In the second part of this demo, I have implemented a runway reservation system. Suppose that there is a single runway in an airport. Airlines can make reservations on the runway based on the times they need to use the runway. In order to use the runway efficiently and to accommodate for slight changes in flight schedules, we allow a grace period of *k* units of time between successive use of the runway. All runway reservation requests are to be processed on a first-come-first-serve basis. In this program, we will print out all the requests that have been successful in using the runway. Note that the above constraints mean that some requests may be unsuccessful.

The file ```Requests.java``` contains the class template for a single reservation request. It contains a ```command``` (either a request or a time command, see below for an explanation), the request ```time```, and an ```Airline``` object to store te meta-information about an airline (flight name, number, source, and destination airports). This file also contains appropriate constructors, accessor methods, and other methods.

The code in ```RunwayReservation.java``` reads through the input file (from ```System.in```) and loads each request into an array ```reqs```. The input file contains two numbers *n* and *k* on the first line that specifies the total number of requests and the grace time period between successive requests respectively. The next *n* lines provide information about a request. Each request consists of one of two commands--a 'r' or 'request reservation' command to request use of the runway for an airline or a 't' or 'advance time' command to move time forwards by some units. Each 'r' command follows with information about the airline--namely the time to use the runway, the airline flight name, number, its source and destination airports, all space separated on the same line. Each 't' request follows with some integer time units to advance the current time.

Since all requests are stored as an array, we are now ready to solve the runway reservation problem. We need to use a binary search tree keyed on time (so the inorder traversal of the tree prints the requests sorted on time) to store all valid or successful requests. We process all the requests in order. The following pseudocode solves this problem at a high level.

For each request ```reqs[i]```, do the following:
1. If the request is 'r', then check if it is a valid request (there is a grace period of *k* units between this request and its previous and next reserved requests). If it is valid, then insert it into a BST *T*.
2. If the request is 't', update the current time, and remove and print all requests from tree *T* with times that are strictly less than the current time. These are the successful requests who have used the runway.

For each request, we can check for validity in only *O*(log *n*) time. Throughout the entirety of the algorithm, each request is inserted into the tree at most once, and removed at most once, both of which take only *O*(log *n*) time per element. So the total running time of the algorithm is *O*(*n* log *n*).

The output is formatted as follows. For each 't' command, we print the current time followed by all successful airlines that have used the runway after the previous 't' command. We assume that there is a 't' command at the end of input that advances current time to the last successful request.

---

## 2. Dynamic Sequences

In this program, I've built a robust binary tree, balanced using randomized mechanisms, to store a dynamic sequence. This tree is then used to solve a problem of ordering 100000 football teams such that each time *i* in the ordering defeated team *i* + 1.

### I. Randomly-Built Binary Trees

A randomly built binary search tree (RBST) provides all binary search tree operations in only *O*(log *n*) time with high probability. In this program, an RBST is built do encode a dynamic sequence. A dynamic sequence *a*<sub>1</sub> ... *a*<sub>*n*</sub> is a mathematical object that represents a sequence of items. The *i*<sup>*th*</sup> item in the sequence is *a*<sub>*i*</sub>. A dynamic sequence can be encoded in a RBST, by storing some item *a*<sub>*i*</sub> at the root, and recursively storing the sequence *a*<sub>1</sub> ... *a*<sub>*i*-1</sub> as the left subtree of the root, and the sequence *a*<sub>*i*+1</sub> ... *a*<sub>*n*</sub> as the right subtree of the root. If each node in the tree is augmented with subtree sizes, then the *i*<sup>*th*</sup> item can be accessed using the ```select``` operation.

The ```Node``` class is implemented with all the required accessor and mutator methods. It also contains the methods ```incSize``` which increments the size of the tree and ```updateSize``` which updates the size of the tree rooted at the node.

The ```RBST``` class implements a randomly-built binary search tree. It contains instance fields ```root``` which is a reference to the root node of the tree, and a ```Random``` object to make probabilistic decisions in the ```insert``` routine. The class is currently filled with two constructors, a ```getSize()``` method that returns the size of the tree, and several public wrapper methods that are described below:

```insertNormal(team, rank)```: This method inserts the data ```team``` at position ```rank```. This is a simple insert routine without any balancing mechanism.

```print()```: This method performs an inorder traversal of the tree and prints the sequence stored in the tree.

```split(rank)```: This method splits the tree at position specified by ```rank```. It returns an array of two RBSTs, the first being the left side of the split, and the second being the right side of the split.

```insert(team, rank)```: This method inserts the data ```team``` at position ```rank```. The insert routine maintains balance in a probabilistic manner. THat is, it inserts the new node at the root of the tree with probability 1/*n*, where *n* is the size of the tree including the new node. This is ideally done by splitting the tree at rank ```rank - 1```, and attaching the left side and right side of the split as the left and right children of the new node respectively. With probability 1 - 1/*n*, the data is recursively inserted to either the left tree or the right tree depending upon the position *rank* (whether rank is less or greater than the rank of the root).

```select(rank)```: This method returns the node in the RBST at position ```rank```. An element is rank *k* if it appears in position *k* in the inorder traversal of the tree. Since the tree encodes a sequence, the element with rank *k* = 1 and *k* = *n* are the first and last elements in the sequence respectively.

Note that the above methods are public interfaces, which are wrapper methods that contain calls to corresponding private methods. It is written this way for two reasons. Firstly, all the above methods are recursive, and operate on a single node which represents the tree rooted at that node, instead of a ```RBST``` object. Secondly, it allows the user to interact much more easily.

### II. Ordering Football Teams

Suppose *n* football teams, numbered 1 ... *n*, all play each other in a large tournament, where there are no ties. In file ```Ordering.java```, you will find a method called ```boolean didXbeatY(int x, int y)``` which returns ```true``` if team *x* won its game against team *y*, ```false``` otherwise. The task for this demo is to compute an ordering of the teams, say *a*<sub>1</sub> ... *a*<sub>*n*</sub>, so that team *a*<sub>1</sub> defeated team *a*<sub>2</sub>, team *a*<sub>2</sub> defeated team *a*<sub>3</sub>, and so on. Lets call such a sequence valid.

Note that there could be many possible valid solutions. For example, if team 1 beat team 2, team 2 beat team 3, etc., and team *n* beat team 1, then any "cyclic rotation" of the sequence 1, 2, 3, ... n (e.g., 3, 4, 5, ... *n* - 1, *n*, 1, 2) will be a valid solution.

The algorithm used to solve this problem is straightforward. It involves building up a sequence (stored in a ```RBST```) to which we add teams 1 ... *n* one by one. Suppose we have built up a valid sequence of teams 1 ... *k* - 1 and we want to add team *k* in a position so that the entire sequence is valid. It turns out that there always exists such a position, and that we can find it quickly usin gout balanced ```RBST```. If team *k* defeated the first team in our sequence, then we can just add it to the beginning. Likewise, if team *k* lost to the last team in the sequence, we can add it to the end. If neither of these cases holds, then let us imagine the sequence with arrows going from the winning team to the losing team. That is *a* → *b* means that team *a* won against team *b*. Since we know that team *k* lost to the first team and beat the last team, there must be some location in the ordering where there is an adjacent pair of teams (*a*, *b*) for which *a* → *k* and *k* → *b*. It is thus feasible to place *k* between *a* and *b*. Moreover, we can binary search for such a position with only *O*(log *k*) ≤ *O*(log *n*) calls to ```select```. Since each call to select takes only *O*(log *n*) time with high probability, this gives a total running time of *O*(*n* log<sup>2</sup> *n*) with high probability to solve the entire problem. This algorithm is implemented in the method ```orderTeams```.

---

## 3. Graphs

This program demonstrates graph traversal and shortest path algorithms. In particular, we will solve water jugs with a depth-first search traversal, and the word ladder problem using breadth-first search. Both traversals are performed on "implicit graphs"--graphs whose nodes and edges are revealed while traversing it.

### I. Filling Water Jugs

Consider a scenario where you are staniding next to a river with a head of cabbage and two water jugs, which have inteer sizes *A* ≤ 1000 and *B* ≤ 1000. In order to boil the cabbage for your dinner, you would like to measure out exactly *C* units of water. For this problem, given *A*, *B*, and *C*, we are able to compute how to do this, or to determine if the task is impossible.

In order to solve this, we perform a depth-first search through a graph where each node corresponds to a pair of integers (*a*, *b*), indicating that we are in the state where jug 1 contains *a* units of water and jug 2 contains *b* units of water. We start from the state (0, 0) where both jugs are empty, and the goal is to reach a state (*a*, *b*) with *a* + *b* = *C*. There are three possible actions to take to move between states: filling one of the jugs to its capacity, emptying out one of the jugs, or pouring the contents of one jug into another (until the first becomes empty or the second reaches its capacity). If the program is able to find a solution, it prints out a step-by-step transcript of the solution.

In ```Jugs.java```, we use a double dimensional array to represent the states we have visited. Note that the graph is implicit, meaning the graph is discovered as we run depth-first search. We also utilize "backpointers" to print the solution. Every time a new state is visited, say (*x*, *y*), the previous state (*px*, *py*) is recorded. This information will be used to print the solution. A string can also be attached to print meaningful information about the actions that lead to state (*x*, *y*).

### II. Word Ladder

Consider a word ladder that can be formed between two 4-letter words: lose → lost → lest → best → beat  
Each word is formed from the previous word by changing exactly one letter. In this program, we find the shortest possible set of words that transforms the start word into the end word. Note that all valid words should belong to the dictionary (file ```dictionary4```) and valid letters are small case 'a' through 'z'.

The solution to this is to perform a breadth-first search through a graph where each node corresponds to a word, and edges correspond to possible transitions from a given word to the next. To solve this effectively, we use two data structures. The first is a ```StringMap```, that is a hash map of a set of words. The hash map will be used for two things--to store valid words from ```dictionary4```, and secondly, to keep track of visited and previous nodes during breadth-first search. We can use the ```key``` field to store the visited words, and the corresponding ```value``` field to store "backpointers"--the word that is visited previously during breadth-first search. This is use to print the path (or the sequence of words) from the start to the end word.

We also need a simple FIFO queue data structure, implemented in ```Queue.java``` using circular arrays. Note that each element in ```Queue``` is of type ```QNode```, which contains two data fields--```dist``` and ```word```. The ```dist``` field stores the shortest distance of the current ```word``` from the start word while performing breadth-first search. The queue is initialized with an array of 1000 elements, and utilizes ```enqueue``` to expand the array whenever more room is needed.

The word ladder is implemented in ```WordLadder.java```. This file loads all the 4-letter words from the dictionary onto a string map. It also acquires user input for the start and end words. This file also prints the shortest sequence of words to go from the start to end words, or prints such a sequence is impossible.

---

## 4. Hash Functions

This program demonstrates hash functions. Specifically, you are given 4 hash functions, and the task is to play the role of a malicious adversary, who having acquired the knowledge of these functions, is interested in producing keys that all hash to the table index. Assume that *n* keys are hashed into a table of size *n*. If *C* is the largest possible key that can be hashed, then *C*/*n* is the faction of keys that can be hashed to the same index. If *C*/*n* ≥ *n*, then according to the pigeonhole principle, we can find a set of *n* keys that all hash to the same table index. So we will assume *C* = *n*<sup>2</sup>. Let *k* be the key being hashed. Then we have the following hash functions.

1. ```hash1```(*k*) = *k* mod *n*.
2. ```hash2```(*k*) = (*n*/*C*) *k*.
3. ```hash3```(*k*) = ((2971*k* + 101923) mod 128189) mod *n*.
4. ```hash4```(*k*) is the first number generated from a pseudo-random number generator (the ```Random``` class in Java) with seed *k*.

---

## 5. Hash Tables

This program builds hash tables and uses them to develop a simple spell checker application. We will also use spatial hashing to the cloest pair of points among a set of points in the two dimensional plane.

### I. Spellchecker

We will be building a rudamentary spell checker--one that is capable of suggesting alternate spellings to words that are misspelled by exactly one character. This is achieved by first building a string set data structure implemented as a hash table, that is capable of storing all words in the English dictionary. It will then be possible to query this data structure for all possible alternate spellings of a word.

The ```StringNode``` class provides the basic data structure for this implementation, complete with a constructor and appropriate get and set methods. The ```StringSet``` class fully supports operations ```insert```, ```find```, and ```print```. It also contains a method ```hash``` that hashes an input String to valid table indices using a polynomial hash function. Note that the ```StringSet``` class contains a constructor that only allocates a table of size 100. If the number of elements stored in this table reaches its size, then the table expands to twice the size of the original, and re-hash the elements.

The ```SpellChecker``` class contains the ```main``` method of the spell checker application. An object of the ```StringSet``` class is declared, and is loaded with words from the dictionary. The main while loop waits for user input, and then provides alternate spellings to the user input that differ in at most one character. Note that only words that can be modifying at most one character are suggested, not words that are obtained by adding or removing characters.

### II. Closest Pair of Points

In this program, we will be computing the closest pair of points among one million points on the 2D plane using spatial hashing. Finding the closest pair of points has many applications; in machine learning classification, hierarchical clustering, collision detection in games and more.

The input is given in the file ```points.txt``` that contains one million points, described by its ```x``` and ```y``` coordinates on the unit square (each ```x``` and ```y``` coordinate lies in 0, 1)). The file contains each ```x``` and ```y``` coordinate of a point in a line, separated by a space.

The naive solution to this is to compute the distance between every pair of points, and always keep track of the minimum distance computed. This takes *O*(*n*<sup>2</sup>) time, and is infeasible in our case, where *n* = 1000000. Instead we divide the unit square into *b* x *b* grids, each of size 1/*b* x 1/*b*. Each point is therefore hashed into its specific grid, and needs to be only compared against points in its grid and the neighboring 8 grids. This significantly reduces the number of pairs of points we look at, provided we choose a good grid size.

Each of these grid cells contain a linked list to store all the points, and roughly follows these steps:
1. Allocate a 2D array of grid cells, for a particular choice of *b*.
2. Read the input file, and hash each point to its corresponding grid cell.
3. For each point, compare against all points in its grid cell and neighboring grid cells, always maintaining the minimum distance computed.
4. Print the minimum distance.

---


