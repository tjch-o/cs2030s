import java.util.Random;

class RandomPoint extends Point {
  private static Random rng = new Random(1);
  
  RandomPoint(double minX, double maxX, double minY, double maxY) {
    super(0, 0);
    // generate random number between 0 and 1 for both x and y
    double randomX = rng.nextDouble();
    double randomY = rng.nextDouble();
    double diffInX = maxX - minX;
    double diffInY = maxY - minY;
    
    // each random point generated must be within the range of min and max
    super.setX(minX + randomX * diffInX);
    super.setY(minY + randomY * diffInY);
  }

  public static void setSeed(int seed) {
    rng = new Random(seed);
  }
}
