/**
 * CS2030S Lab 0: Point.java
 * Semester 2, 2022/23
 *
 * <p>The Point class encapsulates a point on a 2D plane.
 *
 * @author Choo Tze Jie
 */
class Point {
  private double x;
  private double y;

  Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public String toString() {
    return "(" + this.x + ", " + this.y + ")";
  }

  public double distanceTo(Point p) {
    return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
  }
}
