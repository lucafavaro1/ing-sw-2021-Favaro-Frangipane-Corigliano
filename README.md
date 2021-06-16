# Prova Finale Ingegneria del Software 2021

 - ### Luca Favaro ([**@lucafavaro1**](https://github.com/lucafavaro1))	<br>luca1.favaro@mail.polimi.it
 - ### Emilio Corigliano ([**@EmilioCorigliano**](https://github.com/EmilioCorigliano)) <br> emilio.corigliano@mail.polimi.it
 - ### Gianmarco Frangipane ([**@giamma-frangipane**](https://github.com/giamma-frangipane)) <br> gianmarco.frangipane@mail.polimi.it
	
## Implemented Functionalities
| Functionality | Status |
|:-----------------------|:------------------------------------:|
| Basic rules | ![GREEN](http://placehold.it/15/44bb44/44bb44) |
| Complete rules | ![GREEN](http://placehold.it/15/44bb44/44bb44)|
| Socket |![GREEN](http://placehold.it/15/44bb44/44bb44) |
| GUI | ![GREEN](http://placehold.it/15/44bb44/44bb44) |
| CLI |![GREEN](http://placehold.it/15/44bb44/44bb44) |
| Multiple Games | ![GREEN](http://placehold.it/15/44bb44/44bb44)|
| Disconnection Resilience |![GREEN](http://placehold.it/15/44bb44/44bb44) |


<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->






# Masters of Renaissance


![MdR Logo](src/main/java/resources/logo.jpg)


## Overview

This project implements the complete rules of the game [Maestri del Rinascimento](http://www.craniocreations.it/prodotto/masters-of-renaissance/ "Buy the Game")
with both a Command Line Interface and a Graphical User Interface, using sockets for client-server communication.

The [deliverables](/deliverables) folder contains the initial UML of the application, the final UML diagram (splitted in 5 parts) and three Sequence Diagrams illustrating how client and server communicate in various scenarios.

The 5 parts of Final UML Diagram partially overlap in order to make the application design more clear. A unique diagram couldn't be loaded for the excessive size. 

The [src](/src) folder contains source code and unit tests.




## Setup
In the [deliverables](deliverables/final/jar) folder there are two jar files, one to run the Server and the other one to run the Client.

Server can be run with the following command:

> java -jar ServerLauncher.jar


Client can be run with the following command, as default it runs in gui mode:

> java -jar ClientLauncher.jar

This command can be followed by these arguments:
> - **-cli**: to run the client in Command Line Interface mode
> - **-gui**: to run the client in Graphical User Interface mode


## Tools
 
 * [draw.io](https://app.diagrams.net/) - Model UML 
 * [UMLet](https://www.umlet.com/) -  Sequence Diagrams
 * [Maven](https://maven.apache.org/) - Dependency Management
 * [IntelliJ 2021](https://www.jetbrains.com/idea/) - IDE
 * [JavaFX](https://openjfx.io) - Graphical Framework
 * [SceneBuilder](https://gluonhq.com/products/scene-builder/) - Visual Layout Tool 
 
## Requirements
The game requires Java 8 or later in order to run correctly.
 
 ## License
 
 This project is developed in collaboration with [Politecnico di Milano](https://www.polimi.it) and [Cranio Creations](http://www.craniocreations.it).
 
<!--
[![RED](http://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](http://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](http://placehold.it/15/44bb44/44bb44)](#)
-->

