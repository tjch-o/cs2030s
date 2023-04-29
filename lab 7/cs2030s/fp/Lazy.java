package cs2030s.fp;

/**
 * Generic lazy class that produces a value upon lazy evaluation. 
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY 22/23 Sem 2
 * @param <T> the type of the value to be processed within the Lazy class.
 */
public class Lazy<T> {
  /**
   * Producer that evaluates the value when producer.produce() is called.
   */
  private Producer<? extends T> producer;
  /**
   * Value wrapped inside a Maybe instance of type T.
   */
  private Maybe<T> value;
  
  /**
   * Private constructor of Lazy which takes in an input of type T.
   *
   * @param value the input value T to be passed in
   */
  private Lazy(T value) {
    this.value = Maybe.some(value);
  }

  /**
   * Private constructor of Lazy which takes in an input of type Producer.
   *
   * @param s producer to be passed in
   */
  private Lazy(Producer<? extends T> s) {
    this.producer = s;
    this.value = Maybe.none();
  }

  /**
   * A factory method used to create new instances of Lazy with delayed evaluation when given
   * an input of type T.
   *
   * @param <T> type of value to be wrapped around 
   * @param v   value to be wrapped around Lazy 
   * @return    a new instance of Lazy after a value of type T has been passed in
   */
  public static <T> Lazy<T> of(T v) {
    return new Lazy<>(v);
  }

  /**
   * A factory method used to create new instances of Lazy with delayed evaluation when given
   * a Producer which would produce the value. 
   *
   * @param <T> type of the value being wrapped and returned  
   * @param s   producer which produces the value upon evaluation
   * @return    a new Lazy instance with delayed evaluation
   */
  public static <T> Lazy<T> of(Producer<? extends T> s) {
    return new Lazy<>(s);
  }
  
  /**
   * If the value has not be evaluated yet, we will compute it and store it; the evaluation is 
   * only done once and subsequent calls will return the "cached" value.
   *
   * @return the computed / "cached" value
   */
  public T get() {
    /* we have to update the this.value so that we will not call this.producer.produce again cannot 
     * just return this.value.orElseGet(this.producer) because it will call this.producer.produce 
     * more than once */
    T newValue = this.value.orElseGet(this.producer);
    this.value = Maybe.some(newValue);
    return newValue;
  } 
  
  /**
   * Transforms the wrapped value when given a transformer. 
   *
   * @param <U> type of the value after the transformation
   * @param tf  transformation function
   * @return    new Lazy instance wrapping around the value after transformation
   */
  public <U> Lazy<U> map(Transformer<? super T, ? extends U> tf) {
    Producer<U> p = () -> tf.transform(this.get());
    return Lazy.of(p);
  } 

  /**
   * Transforms the value from one Lazy instance to another Lazy instance; preventing a nested
   * Lazy.
   *
   * @param <U> type of the value after the transformation
   * @param tf  transformation function
   * @return    new Lazy instance wrapping around the value after transformation 
   */
  public <U> Lazy<U> flatMap(Transformer<? super T, ? extends Lazy<? extends U>> tf) {
    Producer<? extends U> p = () -> tf.transform(this.get()).get();
    return Lazy.of(p);
  }

  /**
   * Filters the value in the Lazy instance based on the boolean condition given; boolean 
   * condition is lazily evaluated.
   * 
   * @param bc boolean condition used to test
   * @return   a Lazy instance containing a boolean 
   */
  public Lazy<Boolean> filter(BooleanCondition<? super T> bc) {
    Producer<Boolean> bool = () -> bc.test(this.get());
    return Lazy.of(bool);
  }

  /**
   * Combines the values of two Lazy instances using a combiner function and the result is wrapped
   * by a new Lazy instance. 
   *
   * @param <S>       type of Lazy instance we are going to combine with
   * @param <R>       type of resulting Lazy instance 
   * @param lazyS     a lazy instance that we are going to combine with
   * @param combiner  a combiner implementation 
   * @return          new Lazy instance
   */
  public <S, R> Lazy<R> combine(Lazy<? extends S> lazyS, Combiner<? super T, ? super S, 
      ? extends R> combiner) {
    // order here matters: have to figure out from test case that uses <Integer, Double, String>
    Producer<? extends R> lazyR = () -> combiner.combine(this.get(), lazyS.get());
    return Lazy.of(lazyR);
  }

  /**
   * Overrides the Object equals method to return true if both of the objects compared are
   * instances of Lazy and the values inside them which are eagerly evaluated are equal; false 
   * otherwise.
   *
   * @param obj an object of type Object to be compared with the current Lazy instance
   * @return    a boolean that tells us whether the two objects are equal
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } 

    if (obj instanceof Lazy<?>) {
      Lazy<?> lazyO = (Lazy<?>) obj;
      T t = this.get();
      if (t == null) {
        return t == lazyO.get();
      }
      return t.equals(lazyO.get());
    }
    return false;
  }
  
  /**
   * Overrides the Object toString method to return "?" if the value in the Lazy instance is not
   * yet available; else we return the string representation of the value.
   *
   * @return "?" if value is not yet available else a string representation of the value of type T.
   */
  @Override
  public String toString() {
    return this.value.map(String::valueOf).orElse("?");
  }
}
