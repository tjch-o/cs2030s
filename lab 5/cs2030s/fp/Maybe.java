/**
 * CS2030S Lab 5
 * AY22/23 Semester 2
 *
 * @author Choo Tze Jie (Lab Group 08H)
 */

package cs2030s.fp;

import java.util.NoSuchElementException;

public abstract class Maybe<T> {
  private static final Maybe<?> NONE = new None();

  public static <T> Maybe<T> none() {
    // This is fine since NONE is nothing and type of nothing will always remain the same
    @SuppressWarnings("unchecked")
    Maybe<T> result = (Maybe<T>) NONE;
    return result;
  }
  
  public static <T> Maybe<T> some(T input) {
    return new Some<>(input); 
  }

  public static <T> Maybe<T> of(T input) {
    if (input == null) {
      return Maybe.none();
    } else {
      return Maybe.some(input);
    }
  }

  protected abstract T get();

  public abstract Maybe<T> filter(BooleanCondition<? super T> predicate);

  public abstract <U> Maybe<U> map(Transformer<? super T, ? extends U> f);

  public abstract <U> Maybe<U> flatMap(Transformer<? super T, ? extends Maybe<? extends U>> f);

  public abstract T orElse(T input);

  public abstract T orElseGet(Producer<? extends T> producer);

  public abstract void ifPresent(Consumer<? super T> consumer);

  private static class None extends Maybe<Object> {
    @Override
    public String toString() {
      return "[]";
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof None;
    }

    @Override
    protected Object get() {
      throw new NoSuchElementException();
    }

    @Override
    public Maybe<Object> filter(BooleanCondition<? super Object> predicate) {
      return Maybe.none();
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super Object, ? extends U> f) {
      return Maybe.none();
    } 

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super Object, ? extends Maybe<? extends U>> f) {
      return Maybe.none();
    }

    @Override
    public Object orElse(Object input) {
      return input;
    }

    @Override
    public Object orElseGet(Producer<? extends Object> producer) {
      return producer.produce();
    }

    @Override
    public void ifPresent(Consumer<? super Object> consumer) {
      return;
    }
  }

  private static final class Some<T> extends Maybe<T> {
    private final T x;

    private Some(T x) {
      this.x = x;
    }

    @Override
    public String toString() {
      return "[" + this.x + "]";
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if (obj instanceof Some<?>) {
        Some<?> maybe = (Some<?>) obj;
        if (this.x == maybe.x) {
          return true;
        }
        if (this.x == null || maybe.x == null) {
          return false;
        } 
        return this.x.equals(maybe.x);
      }
      return false;
    }

    @Override
    protected T get() {
      return this.x;
    }

    @Override
    public Maybe<T> filter(BooleanCondition<? super T> predicate) {
      if (this.x != null && !predicate.test(this.x)) {
        return Maybe.none();
      } else {
        return this;
      }
    }

    @Override
    public <U> Maybe<U> map(Transformer<? super T, ? extends U> f) {
      return Maybe.some(f.transform(this.x));
    }

    @Override
    public <U> Maybe<U> flatMap(Transformer<? super T, ? extends Maybe<? extends U>> f) {
      Maybe<? extends U> maybeU = f.transform(this.x);
      if (maybeU == Maybe.none()) {
        return Maybe.none();
      }

      U u = maybeU.get();
      return Maybe.some(u);
    }

    @Override
    public T orElse(T input) {
      return this.x;
    }

    @Override
    public T orElseGet(Producer<? extends T> producer) {
      return this.x;
    }

    @Override
    public void ifPresent(Consumer<? super T> consumer) {
      consumer.consume(this.x);
    }
  }
}
