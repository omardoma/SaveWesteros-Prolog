# SaveWesteros-Prolog

The project is a simulation for Game of Thrones, SaveWesteros problem, using Successor State Axioms in Prolog. Our implementation is relying on a Java code to generate a random grid as a knowledge base in Prolog, then Prolog takes over by applying an Iterative Deepening searching algorithm utilizing Prolog's default Depth First algorithm.

## Technology:

* Java
* SWI-Prolog

## How to run manually?

1. `java -jar GenPrologGrid.jar`
2. The generated grid will be displayed in the terminal, make sure that the agent can reach the Dragonstone and White Walkers without being trapped in his initial position due to random assignment of cells or else terminate and run again to generate a new one.
3.  When prompted for the path to export the knowledge base, an example would be: 
    * `./kb.pl`
4. Copy the predicates in the `axioms.pl` file to the end of the `kb.pl` file
5. Rename the modified `kb.pl` to `search.pl`
6. Consult the `search.pl` file in SWI-Prolog
7. run `main(G,L).` where the first parameter is the goal state and the second parameter is the depth limit

## How to evaluate?

1. Consult the already prepared `search.pl` file in SWI-prolog
2. run `main(G,L).` where the first parameter is the goal state and the second parameter is the depth limit

&#9400; Omar Doma, Rodaina Mohamed & Reem Eslam 2018
