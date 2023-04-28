import java.util.Scanner;

/**
 * CS2030S Lab 0: Estimating Pi with Monte Carlo
 * Semester 2, 2022/23
 *
 * <p>This program takes in two command line arguments: the 
 * number of points and a random seed.  It runs the
 * Monte Carlo simulation with the given argument and print
 * out the estimated pi value.
 *
 * @author Choo Tze Jie 
 */

class Lab0 {

  public static double estimatePi(int numOfPoints, int seed) {
    Point centre = new Point(0.5, 0.5);
    Circle c = new Circle(centre, 0.5);
    double howManyWithinCircle = 0;
    RandomPoint.setSeed(seed);
    RandomPoint rp;

    for (int i = 0; i < numOfPoints; i += 1) {
      rp = new RandomPoint(0, 1.0, 0, 1.0);
      if (c.contains(rp)) {
        howManyWithinCircle += 1;
      }
    }
    return 4 * (howManyWithinCircle / numOfPoints);
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int numOfPoints = sc.nextInt();
    int seed = sc.nextInt();

    double pi = estimatePi(numOfPoints, seed);

    System.out.println(pi);
    sc.close();
  }
}
