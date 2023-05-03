package it.polimi.ingsw.utils;

public class Triple <V,T,E>{
    private final V first;
    private final T second;
    private final E third;

    public Triple(V first, T second, E third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public V getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public E getThird() {
        return third;
    }
}
