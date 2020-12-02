/*
	MOST OF YOUR CODE WILL GO IN THIS CLASS

	This is the main class for your project.  It does the following:
		- constructs KILLTOPOPULATION random cars
		- runs GENERATIONS breed/race/kill/mutate generations to evolve a car that completes the racetrack
		- shows the resulting car

	A generation consists of the following:
		- breed: mate pairs of cars, with probability BREED_RATE, adding the resulting cars to the population
		- race every car.  for each car, make a simulated world, run the car for ITERATIONS frames, then score it
			- cars are scored first by distance traveled.  Further is better.
			- cars that make the end of the track (position of 500) are scored second by iterations, or time taken to reach the end.  Smaller is better.
		- kill: sort the cars by score, and keep only the top KILLTOPOPULATION
		- mutate: each car, with probability MUTATE_SELECTION_RATE, has the chance to produce a new mutant car that is added to the population

	YOU SHOULD WRITE, AT A MINIMUM, FUNCTIONS BREED() KILL() and MUTATE().  Find the TODO lines.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GeneticCars implements MouseListener {
	// GENETIC PARAMETERS

	// how many frames the car is raced for
	public static final int ITERATIONS = 2000;

	// number of breed/race/kill/mutate rounds
	// public static final int GENERATIONS=100;
	public static final int GENERATIONS = 20;

	// after each kill round, this many cars are left
	public static final int KILLTOPOPULATION = 10;

	// the probability that any two cars will mate in a breed round
	public static final double BREED_RATE = 0.5;

	// the probability that any car will produce a baby mutant in a mutate round
	public static final double MUTATE_SELECTION_RATE = 0.5;

	// if the mutant is made, the probability that any single ball position or link
	// is altered
	public static final double MUTATE_RATE = 0.1;

	// indicates after how many generations should we change the race track
	public static final int RACE_TRACK_CHANGE_FREQUENCY = 3;

	// Controls if we should race our cars on different tracks
	public static final boolean ENABLE_MULTI_TRACK = false;

	// Tracks the current generation
	public static int currentGeneration = 0;

	// Index of the raceTrack that should be used, it can change!
	public static int raceTrackIndex = 0;

	// This arraylist holds the population of cars
	public ArrayList<Car> population;

	// Program starts here:
	// creates an initial population
	// does the genetic simulation
	// shows the winning car

	public GeneticCars() {
		population = new ArrayList<Car>();

		generateInitialPopulation(KILLTOPOPULATION);
		doGenetic(GENERATIONS);

		// print number of cars that made it to the end
		// long champions = population.stream().filter(car -> car.score_position >=
		// 500).count();
		// System.out.println(champions + " cars made it to the end of the track!");

		// print percentage of cars that made it to the end
		// double championsPercentage = (champions * 100) / population.size();
		// System.out.println(championsPercentage + "% of the cars made it to the end of
		// the track!");

		show(population.get(0));
	}

	// does the genetic simulation
	public void doGenetic(int generations) {
		// runs for generations cycles
		for (int g = 0; g < generations; g++) {
			// calls the breed, race, kill, mutate functions and prints the winner
			currentGeneration++;
			if (currentGeneration % 3 == 0 && ENABLE_MULTI_TRACK) {
				raceTrackIndex = new Random().nextInt(3);
				// System.out.println("Changed Race Track!");
			}
			// System.out.println("Best car score before breed:" +
			// population.get(0).score_position);
			breed();
			// System.out.println("Best car score before racing:" +
			// population.get(0).score_position);
			raceAll();
			// System.out.println("Best car score before killing:" +
			// population.get(0).score_position);
			kill();
			// System.out.println("Best car score before mutating:" +
			// population.get(0).score_position);
			mutate();
			System.out.println("Generation " + (g + 1) + ": best car has distance " + population.get(0).score_position
					+ "/500, Iterations " + population.get(0).score_iterations + "/2000");
		}
	}

	// creates n new cars, each with 10 balls and random links, puts them in the
	// population arraylist
	public void generateInitialPopulation(int n) {
		for (int i = 0; i < n; i++)
			population.add(new Car(10));
	}

	// TODO
	// YOU WRITE THIS
	public void breed() {
		// Make an arraylist of new cars
		ArrayList<Car> newCars = new ArrayList<Car>();

		// Go through every pair of cars in population
		for (Car daddy : population) {
			for (Car mommy : population) {
				// with probability BREED_RATE, mate them by calling the "breed"
				// method in class Car, and add the child to the new car arraylist
				if (Math.random() < BREED_RATE && daddy != mommy) {
					Car child = daddy.breed(mommy);
					newCars.add(child);
				}
			}
		}

		// finally copy the cars in new car over to the population
		population.addAll(newCars);
	}

	// TODO
	// YOU WRITE THIS
	public void mutate() {
		// Make an arraylist of new cars
		ArrayList<Car> newCars = new ArrayList<Car>();

		// Go through every car in the population
		for (Car car : population) {
			// with probability MUTATE_SELECTION_RATE
			if (Math.random() < MUTATE_SELECTION_RATE) {
				// call the "mutate" method in class Car
				Car mutant = car.mutate(MUTATE_RATE);

				// add the child to the new car arraylist
				newCars.add(mutant);
			}
		}

		// finally copy the cars in new car over to the population
		population.addAll(newCars);
	}

	// TODO
	// YOU WRITE THIS
	public void kill() {
		// make a "keep" arraylist of cars
		ArrayList<Car> carsToKeep = new ArrayList<Car>();

		// Do this KILLTOPOPULATION times:
		// System.out.println("Best car score before killing:" +
		// population.get(0).score_position);
		for (int i = 0; i < KILLTOPOPULATION; i++) {
			Car bestCar = population.get(0);
			int bestCarIndex = 0;

			// go through your population and find the best car.
			// Use the compare function (below).
			for (int index = 0; index < population.size(); index++) {
				Car car = population.get(index);

				// if car is better than bestCar
				if (compare(bestCar, car)) {
					bestCar = car;
					bestCarIndex = index;
				}
			}

			// remove the best car from population and put it in the keep list
			population.remove(bestCarIndex);
			carsToKeep.add(bestCar);
			// System.out.println("Score of removed best car: " + bestCar.score_position);
		}

		// set population=keep to make the keep list your population
		population = carsToKeep;
		// System.out.println("Best car score after killing:" +
		// population.get(0).score_position);
	}

	// false if a is better, true if b is better
	// Use this in your kill function to select the best cars
	private boolean compare(Car a, Car b) {
		if (a.score_position >= 500 && b.score_position >= 500)
			return b.score_iterations < a.score_iterations;
		else
			return b.score_position > a.score_position;
	}

	// go through every car and race it
	public void raceAll() {
		for (Car car : population)
			race(car);
	}

	// make a World object containing a racetrack of walls
	// if you do the optional step, you should make several of these and return one
	// of them at random
	public World makeRaceCourse() {
		World world = new World();
		world.WIDTH = 500;
		world.HEIGHT = 500;
		world.makeWall(1, 500, 499, 500);
		world.makeWall(-20, 132, 123, 285);
		world.makeWall(104, 285, 203, 277);
		world.makeWall(202, 275, 271, 344);
		world.makeWall(271, 344, 320, 344);
		world.makeWall(321, 345, 354, 318);
		world.makeWall(354, 318, 394, 324);
		world.makeWall(394, 324, 429, 390);
		world.makeWall(429, 391, 498, 401);
		return world;
	}

	// Create several worlds, then return one at random
	public World makeRaceCourseOptional() {

		// First World (the original world)
		World world = new World();
		world.WIDTH = 500;
		world.HEIGHT = 500;
		world.makeWall(1, 500, 499, 500);
		world.makeWall(-20, 132, 123, 285);
		world.makeWall(104, 285, 203, 277);
		world.makeWall(202, 275, 271, 344);
		world.makeWall(271, 344, 320, 344);
		world.makeWall(321, 345, 354, 318);
		world.makeWall(354, 318, 394, 324);
		world.makeWall(394, 324, 429, 390);
		world.makeWall(429, 391, 498, 401);

		if (!ENABLE_MULTI_TRACK) {
			return world;
		}

		ArrayList<World> worlds = new ArrayList<World>();

		worlds.add(world);

		// Second World (hard)
		world = new World();
		world.WIDTH = 500;
		world.HEIGHT = 500;
		world.makeWall(2, 148, 87, 265);
		world.makeWall(77, 266, 168, 220);
		world.makeWall(160, 218, 190, 287);
		world.makeWall(175, 291, 237, 272);
		world.makeWall(227, 260, 271, 334);
		world.makeWall(261, 336, 335, 319);
		world.makeWall(328, 304, 373, 385);
		world.makeWall(367, 391, 419, 349);
		world.makeWall(411, 348, 485, 410);

		worlds.add(world);

		// Third World (easy)
		world = new World();
		world.WIDTH = 500;
		world.HEIGHT = 500;

		world.makeWall(3, 181, 91, 294);
		world.makeWall(88, 294, 122, 276);
		world.makeWall(121, 274, 177, 326);
		world.makeWall(175, 328, 258, 329);
		world.makeWall(260, 329, 330, 382);
		world.makeWall(331, 384, 407, 391);
		world.makeWall(408, 389, 494, 424);

		worlds.add(world);

		return worlds.get(raceTrackIndex);
	}

	// take an individual car, make a racetrack for it and simulate it
	// at the end of the function the car will have a score
	public void race(Car car) {
		// World w = makeRaceCourse();
		World w = makeRaceCourseOptional();
		car.constructCar(w);
		int i = 0;
		for (i = 0; i < ITERATIONS; i++) {
			w.doFrame();
			if (car.getPosition() >= 500)
				break;
		}
		car.setScore(i);
	}

	// show every car in population racing, one at a time
	public void showAll() {
		for (Car car : population) {
			// World w = makeRaceCourse();
			World w = makeRaceCourseOptional();
			car.constructCar(w);
			show(w);
		}
	}

	// show a single car racing
	public void show(Car car) {
		// World w = makeRaceCourse();
		World w = makeRaceCourseOptional();
		car.constructCar(w);
		show(w);
	}

	// pop up a window and show a car falling down its track
	private void show(World world) {
		JFrame window = new JFrame("World");
		window.setSize(600, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(world);
		world.addMouseListener(this);
		world.graphics = true;

		window.setVisible(true);

		for (int i = 0; i < ITERATIONS; i++) {
			world.doFrame();
			try {
				Thread.sleep((int) (world.DT * 1000 / 30));
			} catch (InterruptedException e) {
			}
			;
		}
	}

	// these methods don't do anything currently
	// they're here only if you want to make the "show" window interactive
	public void mouseClicked(MouseEvent e) {
		int px = e.getX();
		int py = e.getY();
	}

	int px, py;

	public void mousePressed(MouseEvent e) {
		px = e.getX();
		py = e.getY();
	}

	int rx, ry;

	public void mouseReleased(MouseEvent e) {
		rx = e.getX();
		ry = e.getY();
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	// main just calls GeneticCars
	public static void main(String[] args) {
		new GeneticCars();
	}
}
