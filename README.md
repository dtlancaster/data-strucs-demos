##### Table of Contents  
[Binary Search Trees](#headers)

[1. Binary Search Tree](#headers)
[Emphasis](#emphasis)  
...snip...    
<a name="headers"/>

## Binary Search Trees

I've built a binary search tree and use it to solve a problem involving runway requests by airlines. In this program, I demonstrate that actual data consisting of large meta-information can be stored outside the data structure, while the binary search tree can be keyed on a particular field within the data. Several binary search tree operations beyond a dictionary data structure, like computing the minimum element in a set of the predecessor element of a key within the set. Note that in this implementation, it is not required to balance the tree. The input data will be sufficiently random to keep the tree balanced.

### I. Binary Search Tree

A binary search tree is a data structure that maintains a collection of keys (along with associated values) which encodes a sorted ordering of the elements within its inorder traversal. This allows us to perform several operations that are not possible with hash tables.


The binary search tree is keyed on an integer ```time```. The ```Node``` class is implemented with two data fields--```time``` and ```req_index```. It contains appropriate accessors and mutators. The second part of this implementation is dependent on access to airline runway requests, which contain meta-data (flight name, source, destination, etc) for each request. The requests are stored as an array, and implement a binary search tree keyed on ```time``` with associated value ```req_index``` that points to the index of the request in our data array. All our needed operations for our binary search tree are implemented in the file ```BST.java```. The binary search tree supports the following operations:

(a) ```insert(time, req_index)```: This method inserts an integer ```time``` along with the associated value ```req_index``` into the tree. Note that the tree is keyed on ```time```.

(b)```pred(time)```: This returns the node within the binary search tree that is the predecessor of ```time```, i.e., the largest integer smaller or equal to ```time```. This returns ```null``` if there is no predecessor.

(c)```succ(time)```: This returns the node within the binary search tree that is the successor of ```time```, i.e., the smallest integer greater or equal to ```time```. This returns ```null``` if there is no successor.

(d)```min()```: This returns pointer to the minimum element in the binary search tree, or ```null``` if the tree is empty.

(e)```max()```: This returns a pointer to the maximum element in the binary search tree, or ```null``` if the tree is empty.

(f)```delete(time)```: This removes the node with the key ```time``` if it is present, otherwise does nothing.

(g)```print()```: This method prints the contents of the tree in sorted order, by doing an inorder traversal of the tree.

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

(a) For each request ```reqs[i]```, do the following:
1. If the request is 'r', then check if it is a valid request (there is a grace period of *k* units between this request and its previous and next reserved requests). If it is valid, then insert it into a BST *T*.
2. If the request is 't', update the current time, and remove and print all requests from tree *T* with times that are strictly less than the current time. These are the successful requests who have used the runway.

For each request, we can check for validity in only *O*(log *n*) time. Throughout the entirety of the algorithm, each request is inserted into the tree at most once, and removed at most once, both of which take only *O*(log *n*) time per element. So the total running time of the algorithm is *O*(*n* log *n*).

The output is formatted as follows. For each 't' command, we print the current time followed by all successful airlines that have used the runway after the previous 't' command. We assume that there is a 't' command at the end of input that advances current time to the last successful request.
