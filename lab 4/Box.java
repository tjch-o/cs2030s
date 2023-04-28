/**
 * A generic box storing an item.
 * CS2030S Lab 4
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 */
public class Box<T> {
  private final T x;
  // EMPTY_BOX is of unknown type
  private static final Box<?> EMPTY_BOX = new Box<>(null);

  private Box(T x) {
    this.x = x;
  }

  public static <T> Box<T> of(T x) {
    if (x == null) {
      return null;
    }
    return new Box<T>(x);
  }

  public static <T> Box<T> empty() {
    // We will cast the box to the type specified 
    // This is fine since the empty box contains null
    @SuppressWarnings("unchecked")
    Box<T> result = (Box<T>) Box.EMPTY_BOX;
    return result;
  }

  public boolean isPresent() {
    if (this.x != null) {
      return true;
    } else {
      return false;
    }
  }

  public static <T> Box<T> ofNullable(T x) {
    if (x != null) {
      return Box.of(x);
    } else {
      return Box.empty();
    }
  }

  public Box<T> filter(BooleanCondition<? super T> condition) {
    if (!(this.isPresent())) {
      return Box.empty();
    } else {
      if (condition.test(this.x)) {
        return this;
      } else {
        return Box.empty();
      }
    }
  }

  // we use PECS here
  // from debugging a test case, they pass in an integer when T is a string so got error
  public <U> Box<U> map(Transformer<? super T, ? extends U> transformer) {
    if (!(isPresent())) {
      return Box.empty();
    } else {
      return Box.ofNullable(transformer.transform(this.x));
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof Box<?>) {
      Box<?> b = (Box<?>) obj;
      if (this.x == b.x) {
        return true;
      }

      if (!(this.isPresent()) || !(b.isPresent())) {
        return false;
      }

      return this.x.equals(b.x);
    }
    return false;
  }

  @Override
  public String toString() {
    if (!(this.isPresent())) {
      return "[]";
    }
    return "[" + this.x + "]";
  }
}