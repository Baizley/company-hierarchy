This is a proposed solution for a challenge given during a hiring process. 

The challenge:

> We in Amazing Co need to model how our company is structured so we can do awesome stuff.
> 
> We have a root node (only one) and several children nodes, each one with its own children as well. It's a tree-based structure. Something like:       
> 
> We need two HTTP APIs that will serve the two basic operations:
> 
> 1) Get all children nodes of a given node (the given node can be anyone in the tree structure).
> 
> 2) Change the parent node of a given node (the given node can be anyone in the tree structure).
> 
> They need to answer quickly, even with tons of nodes. Also,we can't afford to lose this information, so some sort of persistence is required. 
> 
> Each node should have the following info:
> 
> 1) node identification
> 
> 2) who is the parent node 
> 
> 3) who is the root node 
> 
> 4) the height of the node. In the above example,height(root) = 0 and height(a) == 1.
> 
> Our boss is evil and we can only have docker and docker-compose on our machines. So your server needs to be ran using them.

### System specification

The system allows modifications for all nodes in the tree. 

However, changing the parent of a node to one in its subtree currently results in a incorrect state, 
which is of course not desirable.  

### Building and running the system

To run the system, first build a JAR of the application with: 

`./mvnw clean install`

Then to run it use:

`docker-compose up`

### Further Tasks to Complete the System

There is still a few tasks that could improve the solution, which are listed here:
1) Introduce error handling (do not leak stacktrace, ensure nodes exists before changing parent, etc.).
2) Add REST API with Swagger.
3) Setup testing infrastructure and add test cases.
4) Performance test the application.