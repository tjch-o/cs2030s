package cs2030s.fp;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Generic lazily evaluated InfiniteList class that represents an infinite list of items. 
 *
 * @author Choo Tze Jie (Group 08H)
 * @version CS2030S AY 22/23 Sem 2
 * @param <T> the type of the value in the list
 */
public class InfiniteList<T> {
  /** head of the InfiniteList. */
  private final Lazy<Maybe<T>> head;
  /** tail of the InfiniteList. */
  private final Lazy<InfiniteList<T>> tail;
  /** cached Sentinel instance to mark the end of the InfiniteList. */
  private static final Sentinel SENTINEL = new Sentinel(); 

  /**
   * Private constructor to initialise an empty InfiniteList. 
   */
  private InfiniteList() { 
    this.head = null; 
    this.tail = null;
  }

  /**
   * Private constructor to initialise a InfiniteList when given a head of type T and a 
   * producer.
   *
   * @param head the already evaluated head value
   * @param tail Producer for the unevaluated tail value  
   */
  private InfiniteList(T head, Producer<InfiniteList<T>> tail) {
    this.head = Lazy.of(Maybe.some(head));
    this.tail = Lazy.of(tail);
  }

  /**
   * Private constructor to initialise a InfiniteList when given a head which is an instance of 
   * Lazy with a Maybe instance wrapped within and a tail that is also an InfiniteList. 
   *
   * @param head lazily evaluated head value 
   * @param tail lazily evaluated tail value
   */
  private InfiniteList(Lazy<Maybe<T>> head, Lazy<InfiniteList<T>> tail) {
    this.head = head;
    this.tail = tail;
  }

  /**
   * Factory method for initialising an InfiniteList with elements all just being the value 
   * produced by the producer.
   *
   * @param <T>      type of the InfiniteList
   * @param producer Producer to be passed in
   * @return         an InfiniteList instance
   */
  public static <T> InfiniteList<T> generate(Producer<T> producer) {
    return new InfiniteList<T>(Lazy.of(() -> Maybe.some(producer.produce())), Lazy.of(() ->
          generate(producer)));
  }

  /**
   * Factory method for initialising a InfiniteList with the first element being the seed and 
   * subsequent elements are the values returned when a transformer function next is applied on the
   * previous element.
   *
   * @param <T>   type of the InfiniteList
   * @param seed  starting element of the InfiniteList
   * @param next  Transformer function to be applied on each element to generate the next element
   * @return      an InfiniteList instance 
   */
  public static <T> InfiniteList<T> iterate(T seed, Transformer<T, T> next) {
    return new InfiniteList<T>(seed, () -> iterate(next.transform(seed), next)); 
  }

  /**
   * Returns the value of the head of the InfiniteList since the head of the InfiniteList is an 
   * instance of Lazy with a Maybe wrapped within it; when the Maybe is evaluated, in the case of
   * the head being a None then we will move to the next element in the InfiniteList.
   * 
   * @return value of the head of the InfiniteList which is not a None
   */
  public T head() {
    return this.head.get().orElseGet(() -> this.tail.get().head());
  }

  /**
   * Returns the tail of the InfiniteList; if the head of the current list is None then the 
   * tail will start from the third element.
   * 
   * @return tail of the InfiniteList
   */
  public InfiniteList<T> tail() {
    return this.head.get().map(x -> this.tail.get()).orElseGet(() -> this.tail.get().tail());
  }

  /**
   * Transforms all the elements in the InfiniteList when given a transformer function.
   *
   * @param <R>    type of the output InfiniteList 
   * @param mapper transformer function to be applied 
   * @return       an InfiniteList of type R 
   */
  public <R> InfiniteList<R> map(Transformer<? super T, ? extends R> mapper) {
    Lazy<Maybe<R>> maybeR = Lazy.of(() -> this.head.get().map(mapper));
    Lazy<InfiniteList<R>> infiniteListR = Lazy.of(() -> this.tail.get().map(mapper));
    return new InfiniteList<>(maybeR, infiniteListR);
  }

  /**
   * Filters out elements in the InfiniteList that fail a given boolean condition; returning a 
   * lazily filtered InfiniteList.
   * 
   * @param predicate boolean condition that we are filtering the elements with
   * @return          new InfiniteList which is filtered        
   */
  public InfiniteList<T> filter(BooleanCondition<? super T> predicate) {
    Lazy<Maybe<T>> filterHead = Lazy.of(() -> this.head.get().filter(predicate));
    Lazy<InfiniteList<T>> filterTail = Lazy.of(() -> this.tail.get().filter(predicate));
    return new InfiniteList<>(filterHead, filterTail);
  }

  /**
   * Returns a empty InfiniteList. 
   *
   * @param <T> type of the Sentinel
   * @return    a Sentinel instance
   */
  public static <T> InfiniteList<T> sentinel() {
    /* This is fine because SENTINEL is supposed to represent an empty InfiniteList and type of an 
     empty InfiniteList will always be the same */
    @SuppressWarnings("unchecked")
    InfiniteList<T> sentinelT = (InfiniteList<T>) SENTINEL;
    return sentinelT;
  }

  /**
   * Returns a truncated finite list which was originally infinite, containing at most n elements 
   * and not counting elements that are filtered by filter.
   * 
   * @param n  maximum number of elements in the resulting finite list excluding the filtered 
   *     elements
   * @return   an instance of InfiniteList that represents a finite list of at most n elements  
   */
  public InfiniteList<T> limit(long n) {
    // we need the head first for an infinite list before we can continue and get the tail
    // the orElseGet lets us skip over elements that are filtered out by filter
    Lazy<InfiniteList<T>> currentTail = Lazy.of(() -> this.head.get()
        .map(x -> this.tail.get().limit(n - 1)).orElseGet(() -> this.tail.get().limit(n)));
    return Maybe.of(n).filter(x -> x > 0).map(x -> new InfiniteList<>(this.head, currentTail))
      .orElseGet(() -> InfiniteList.sentinel());
  }

  /**
   * Truncates the InfiniteList as soon as it finds an element that when tested by the boolean 
   * condition returns false.
   *
   * @param predicate BooleanCondition to be tested on each element in the list
   * @return          a truncated InfiniteList       
   */
  public InfiniteList<T> takeWhile(BooleanCondition<? super T> predicate) {
    Lazy<Maybe<T>> lazyHead = Lazy.of(() -> this.head.get().filter(predicate));
    // we filter the head then check if the lazyHead is the same as head
    // if equal means we do from None -> None or from Some -> Some so we shouldn't terminate
    // if not equal means we did from Some -> None and we should terminate
    Lazy<InfiniteList<T>> lazyTail = this.head.combine(lazyHead, (x, y) -> x.equals(y)
        ? this.tail.get().takeWhile(predicate)
        : InfiniteList.sentinel());
    return new InfiniteList<>(lazyHead, lazyTail);
  }

  /**
   * Checks if the InfiniteList is an instance of Sentinel.
   *
   * @return a boolean
   */
  public boolean isSentinel() {
    return false;
  }
  
  /**
   * Reduces elements of the InfiniteList to a single value of type U by combining them with 
   * an accumulator.
   *
   * @param <U>         type of result to be returned after reduction 
   * @param identity    initial value  
   * @param accumulator binary function used for combining the elements
   * @return            the value obtained after combining all the elements 
   */
  public <U> U reduce(U identity, Combiner<U, ? super T, U> accumulator) {
    return this.head.get().map(x -> this.tail.get().reduce(accumulator.combine(identity, x), 
          accumulator)).orElseGet(() -> this.tail.get().reduce(identity, accumulator));
  }

  /**
   * Returns number of elements in the InfiniteList.
   *
   * @return number of elements in the InfiniteList
   */
  public long count() {
    // each time we combine we increase by 1
    return this.reduce(0, (x, y) -> x + 1);
  }

  /**
   * Collects elements in the InfiniteList into a Java List.
   *
   * @return a Java List representation of the InfiniteList
   */
  public List<T> toList() {
    List<T> arr = new ArrayList<>();
    InfiniteList<T> temp = this;
    while (!temp.isSentinel()) {
      /* remember consumer takes in a value and doesnt return anything it consumes the thing inside
       the maybe if the maybe is a some */
      temp.head.get().consumeWith(arr::add);
      temp = temp.tail.get();
    }
    return arr;
  }

  /**
   * Returns a string representation of the InfiniteList.
   *
   * @return String representation of the InfiniteList
   */
  public String toString() {
    return "[" + this.head + " " + this.tail + "]";
  }

  /**
   * Nested static Sentinel class that represents an InfiniteList that is basically an empty
   * InfiniteList and is used to mark the end of the list.
   */
  private static class Sentinel extends InfiniteList<Object> {
    /**
     * Returns the string representation of a Sentinel. 
     *
     * @return "-"
     */
    @Override
    public String toString() {
      return "-";
    }
    
    /**
     * Calling head on an empty InfiniteList should throw a NoSuchElementException.
     * 
     * @return doesn't return, just throws NoSuchElementException
     * @throws NoSuchElementException always thrown since Sentinel instances have no elements
     */
    @Override
    public Object head() {
      throw new NoSuchElementException();
    }

    /**
     * Calling tail on an empty InfiniteList should throw a NoSuchElementException.
     * 
     * @return doesn't return, just throws NoSuchElementException
     * @throws NoSuchElementException always thrown since Sentinel instances have no elements
     */
    @Override
    public InfiniteList<Object> tail() {
      throw new NoSuchElementException();
    }
    
    /**
     * Calling map on an empty InfiniteList should return an empty InfiniteList.
     *
     * @return a Sentinel
     */
    @Override
    public <R> InfiniteList<R> map(Transformer<? super Object, ? extends R> mapper) {
      return InfiniteList.sentinel();
    }

    /**
     * Calling filter on an empty InfiniteList should return an empty InfiniteList.
     *
     * @return a Sentinel
     */
    @Override
    public InfiniteList<Object> filter(BooleanCondition<? super Object> predicate) {
      return InfiniteList.sentinel();
    }
    
    /**
     * Calling isSentinel on an empty InfiniteList should return true.
     *
     * @return a boolean
     */
    @Override
    public boolean isSentinel() {
      return true;
    }

    /**
     * Calling limit on a empty InfiniteList should return a empty InfiniteList.
     * 
     * @param n number of elements in the resulting finite list excluding the filtered elements
     * @return  a Sentinel 
     */
    @Override
    public InfiniteList<Object> limit(long n) {
      return InfiniteList.sentinel();
    }
    
    /**
     * Calling toList on an empty InfiniteList should return an empty Java List.
     *
     * @return an empty Java List
     */
    @Override
    public List<Object> toList() {
      return new ArrayList<>();
    }

    /**
     * Calling takeWhile on an emptyInfiniteList should return an empty InfiniteList.
     *
     * @param predicate boolean condition we are filtering the elements with
     * @return          a Sentinel
     */
    @Override
    public InfiniteList<Object> takeWhile(BooleanCondition<? super Object> predicate) {
      return InfiniteList.sentinel();
    }
    
    /**
     * Calling reduce on an empty InfiniteList should return the identity that was passed in.
     *
     * @param <U>         type of the result to be returned after reduction
     * @param identity    the initial value
     * @param accumulator binary function used for combining the elements
     * @return            the identity passed in
     */
    @Override
    public <U> U reduce(U identity, Combiner<U, ? super Object, U> accumulator) {
      return identity;
    }

    /**
     * Calling count on an empty InfiniteList should return 0.
     *
     * @return count of 0
     */
    @Override
    public long count() {
      return 0L;
    }
  }
}