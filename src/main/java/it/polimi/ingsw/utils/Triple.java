package it.polimi.ingsw.utils;

/**
 * A generic class representing a triple of values.
 * @param <V> the type of the first value
 * @param <T> the type of the second value
 * @param <E> the type of the third value
 */
public class Triple <V,T,E>{
    private final V first;
    private final T second;
    private final E third;

    /**
     * Constructs a new Triple with the specified values.
     * @param first the first value
     * @param second the second value
     * @param third the third value
     */
    public Triple(V first, T second, E third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * Returns the first value of the triple.
     * @return the first value
     */
    public V getFirst() {
        return first;
    }

    /**
     * Returns the second value of the triple.
     * @return the second value
     */
    public T getSecond() {
        return second;
    }

    /**
     * Returns the third value of the triple.
     * @return the third value
     */
    public E getThird() {
        return third;
    }
}
