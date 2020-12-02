import java.util.Random;

/*
MODIFY THIS CLASS TO MATE TWO CARS AND MUTATE CARS

Your code will go in methods BREED() and MUTATE().  Find the TODO lines.
  you will call these methods from your code in GeneticCars

A "Car" is a collection of balls and links
*/

public class Car {
	// how many balls in the car
	int nodes;
	// position of balls
	int[] balls_x;
	int[] balls_y;
	// for every ball i,j true if there's a link between them
	boolean[][] linkmatrix;

	// these are set by the setScore function after a simulated race
	double score_position; // how far did the car get
	double score_iterations; // how long did it take the car to reach the end

	// the simulated world the car is running in. null until the car is raced.
	World world;

	// construct a car with nodes balls and random links
	// every ball is placed between (5,5) and (50,50)

	public Car(int nodes) {
		this.world = null;
		this.nodes = nodes;

		balls_x = new int[nodes];
		balls_y = new int[nodes];
		linkmatrix = new boolean[nodes][nodes];

		// randomly place balls between (5,5 and 50,50)
		for (int i = 0; i < nodes; i++) {
			balls_x[i] = randint(5, 50);
			balls_y[i] = randint(5, 50);
		}

		// assign a link between two balls with probability 1/3
		for (int i = 0; i < nodes; i++) {
			for (int j = 0; j < nodes; j++) {
				if (randint(1, 3) == 1)
					linkmatrix[i][j] = true;
			}
		}
	}

	// return the average x position of the nodes
	// this is called only after the car has been raced
	public double getPosition() {
		int sum = 0;
		for (int i = 0; i < nodes; i++)
			sum += world.getBall(i).position.x;
		return sum / nodes;
	}

	// set the car's score
	// this is called once the race simulation is done
	// don't call it before then or you'll get a nullpointerexception
	public void setScore(int iterations) {
		score_position = getPosition();
		if (score_position > world.WIDTH)
			score_position = world.WIDTH;
		score_iterations = iterations;
	}

	// build the car into the world: create its balls and links
	// call this when you're ready to start racing
	public void constructCar(World world) {
		this.world = world;
		for (int i = 0; i < nodes; i++) {
			world.makeBall(balls_x[i], balls_y[i]);
		}
		for (int i = 0; i < nodes; i++)
			for (int j = 0; j < nodes; j++)
				if (linkmatrix[i][j])
					world.makeLink(i, j);
	}

	// returns a random integer between [a,b]
	private int randint(int a, int b) {
		return (int) (Math.random() * (b - a + 1) + a);
	}

	// TODO
	// YOU WRITE THIS FUNCTION
	// It should return a "child" car that is the crossover between this car and
	// parameter car c
	public Car breed(Car mate) {
		Car child = new Car(nodes);

		// YOUR WORK HERE

		// Choose a car to go first
		Random random = new Random();

		Car firstParent;
		Car secondParent;

		// generate int (0 or 1)
		if (random.nextInt(2) == 1) {
			firstParent = this;
			secondParent = mate;
		} else {
			firstParent = mate;
			secondParent = this;
		}

		// Choose a random crossover point.
		int crossoverPoint = (int) (Math.random() * nodes); // 0..nodes

		// copy the balls from the first car's balls_x and balls_y to the child
		for (int i = 0; i < crossoverPoint; i++) {
			child.balls_x[i] = firstParent.balls_x[i];
			child.balls_y[i] = firstParent.balls_y[i];
		}

		// after the crossover, copy the balls_x and balls_y from the second car to the
		// child
		for (int i = crossoverPoint; i < nodes; i++) {
			child.balls_x[i] = secondParent.balls_x[i];
			child.balls_y[i] = secondParent.balls_y[i];
		}

		// Pick up new crossover point
		crossoverPoint = (int) (Math.random() * nodes); // 0..nodes

		// do the same with the linkmatrix
		for (int i = 0; i < crossoverPoint; i++) {
			for (int j = 0; j < crossoverPoint; j++) {
				child.linkmatrix[i][j] = firstParent.linkmatrix[i][j];
			}
		}

		for (int i = crossoverPoint; i < nodes; i++) {
			for (int j = crossoverPoint; j < nodes; j++) {
				child.linkmatrix[i][j] = secondParent.linkmatrix[i][j];
			}
		}

		return child;
	}

	// TODO
	// YOU WRITE THIS FUNCTION
	// It should return a car "newcar" that is identical to the current car, except
	// with mutations
	public Car mutate(double probability) {
		Car newCar = new Car(nodes);

		// YOUR WORK HERE

		// You should copy over the car's balls_x and balls_y to newcar
		// newCar.balls_x = this.balls_x;
		// newCar.balls_y = this.balls_y;
		for (int i = 0; i < nodes; i++) {
			newCar.balls_x[i] = this.balls_x[i];
			newCar.balls_y[i] = this.balls_y[i];
		}

		// with probability "probability"
		// change the balls_x and balls_y to a random number from 5 to 50
		for (int i = 0; i < nodes; i++) {
			if (Math.random() < probability) {
				newCar.balls_x[i] = randint(5, 50);
				newCar.balls_y[i] = randint(5, 50);
			}
		}

		// Then copy over the links
		// newCar.linkmatrix = this.linkmatrix;
		for (int i = 0; i < nodes; i++) {
			for (int j = 0; j < nodes; j++) {
				newCar.linkmatrix[i][j] = this.linkmatrix[i][j];
			}
		}

		// with probability "probability"
		// set the link to true/false (50/50 chance)
		for (int i = 0; i < nodes; i++) {
			for (int j = 0; j < nodes; j++) {
				if (Math.random() < probability) {
					// Random random = new Random();

					// if (random.nextInt(2) == 1)
					// {
					// newCar.linkmatrix[i][j] = true;
					// }
					// else
					// {
					// newCar.linkmatrix[i][j] = false;
					// }

					newCar.linkmatrix[i][j] = !newCar.linkmatrix[i][j];
				}
			}
		}

		return newCar;
	}
}
