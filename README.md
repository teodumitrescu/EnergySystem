# EnergySystem

Copyright 2020 - Dumitrescu Ioana - Teodora, 325CD

--------------------------------
    Main idea of the project
--------------------------------
The main idea is to simulate a network of producers, distributors and consumers for an
electrical company. Distributors choose their producers based on a preferred strategy, and 
assure that each of their consumers receives energy while paying a monthly bill. The 
consumers choose the distributor with the cheapest contract at the end of the previous
contract length. 

The simulation goes on for an initial round, and then for a specified number of
turns, while receiving updates on price, quantity or new consumers.

--------------------------------
        Design Decisions
--------------------------------
The main focus in this project is on encapsulation. Therefore, all the actions made
by a certain entity are represented by methods of that entity. Also, there are classes
that are used for input reading and parsing and also some for writing. The input, along
with any further updates are stored in a database, on which was applied the Singleton
design pattern in order for the class to not be able to be instantiated multiple
times. This way, we assure that the data has only one source to be accessed from.


Also, the Simulator class, which is used to repeatedly play the rounds of the game,
has also a singleton pattern applied because there is only one way to play the game.

The factory design pattern was also used, in order to create entities (Consumer and
Distributor class both extend the Entity class) when populating the input. It was later
created a factory for the strategies, to create the specific strategies for the
distributors. Every factory is of Singleton design pattern, because we only need one
instance of each that would help us.

The Strategy design pattern was used for the strategies (green, price and quantity).
Each class for a specific strategy implemented the interface Strategy, and have overriden
its method "applyStrategy", using comparators to order the producers in the correct order.

The Observer design pattern was used to ease the interaction between producers and distributors.
Therefore, the producer class extends the Observable class, because he is the entity that can constantly
change its state. The distributor class implements the Observer class, because he is the entity
that has to make changes based on the producers he is linked to. The producer notifies the 
distributor that his state is changed and the distributor sets the value for the "mustFindNewProducers"
variable upon notifying. 

--------------------------------
       Flow & Interactions
--------------------------------

The input is read from the file, parsed in the InputParse class and then added into
the database. Then, the initial round and the turns start, where:
-distributors choose their producers
- the final price of the distributors is calculated
- each new consumer finds the distributor with the cheapest contract price
- consumers get their salary and then pay their bills or postpone them if
they can
- distributors also pay their monthly costs
- consumers which are in the last month of the current contract or are currently
at a bankrupt distributor change their current distributor
- producers store their monthly stats
- bankrupt consumers are eliminated from the distributors' list
- bankrupt distributors are eliminated from the producers' list

It is to be mentioned that the consumers, distributors and producers are stored in a map in the
database, this making it easier to find the entity by its id. For finding the
cheapest non-bankrupt distributor, all of them are added into a list which sorts them
by their final price by using a comparator. Similarly, to sort them easier in different ways for
the strategies, producers are also stored in an additional list.

After the final round, the output is written into the results file with the help of
some auxiliary classes that collect the relevant data from each consumer and distributor
in order not to show all the details.
