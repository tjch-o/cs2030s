/**
 * The Array<T> for CS2030S 
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY22/23 Semester 2
 */
class Array<T extends Comparable<T>> { 
  private T[] array;
  private final int size;

  Array(int size) {
    // The only way we can put an object into array is through
    // the method set() and we only put object of type T inside.
    // So it is safe to cast `Comparable[]` to `T[]`.
    @SuppressWarnings({"unchecked", "rawtypes"})
    T[] temp = (T[]) new Comparable[size];
    this.array = temp;
    this.size = size;
  }

  public void set(int index, T item) {
    this.array[index] = item;
  }

  public T get(int index) {
    return this.array[index];
  }

  public T min() {
    T min = this.array[0];
    T current;
    for (int i = 0; i < this.size; i += 1) {
      current = this.array[i];
      if (current.compareTo(min) < 0) {
        min = current;
      }
    }
    return min;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("[ ");
    for (int i = 0; i < array.length; i++) {
      s.append(i + ":" + array[i]);
      if (i != array.length - 1) {
        s.append(", ");
      }
    }
    return s.append(" ]").toString();
  }
}