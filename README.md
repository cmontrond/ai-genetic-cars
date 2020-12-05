# Fourth Project: Genetic Cars

Fourth Project for COMP 560: Artificial Intelligence at Bridgewater State University.<br>

Given a physics engine that models balls, links, gravity, and friction, and a simulated race track, this project aims to make a 2D car out of balls and links that can successfully navigate the race course, or, rather, evolve a car genetically that can complete the course.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Have Java/JDK Installed;

### Running the Project

Git Clone this repository:

```
git clone https://github.com/cmontrond/ai-genetic-cars.git
```

CD into the project folder:

```
cd ai-genetic-cars
```

Compile the project:

```
javac *.java
```

Run the project:

```
java GeneticCars
```

Testing the physics simulator:

```
java Interactive
```

Enabling multiple tracks:

```
Set ENABLE_MULTI_TRACK (line 52) to true. This will make it so that the program randomly
chooses a different track every RACE_TRACK_CHANGE_FREQUENCY generations.
```

## What Was Successfully Accomplished
* Breed step of Genetic Algorithm ✔
* Kill step of Genetic Algorithm ✔
* Mutate step of Genetic Algorithm ✔
* Complete Genetic Algorithm working for single track ✔
* Complete Genetic Algorithm working for multiple tracks ✔

## Statement
The project was completed, including all of the steps and the "Optional A".

## Built With

* [Java](https://www.oracle.com/java/technologies/javase-downloads.html) - The Programming Language
* [Visual Studio Code](https://code.visualstudio.com/) - The Code Editor

## Author

**Christopher Montrond da Veiga Fernandes** - [Contact](mailto:cmontronddaveigafern@student.bridgew.edu)<br>

## Instructor

**Dr. Michael Black** - [Contact](mailto:m1black@bridgew.edu)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
