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

A modification where the parent of a node is changed to one in the nodes subtree follows a hierarchical approach, so
that a promotion is given to the node which is both a child of the modified node and an ancestor of the new parent.

This is best illustrated with an example. Given the following tree.
    
          1  
         / \
        2   3  
       / \
      5   4  
     /
    6 
    
Changing the parent of 2 from 1 to 6 will result in the following tree.

            1  
           / \
          5   3  
         /
        6  
       /
      2
     / 
    4  

### Building and running the system

To run the system, first build a JAR of the application with: 

`./mvnw clean install`

Then to run it use:

`docker-compose up`

### Further Tasks to Complete the System

There is still a few tasks that could improve the solution, which are listed here:
1) Add REST API with Swagger.
2) Setup integration testing with Postgres
3) Performance test the application.
