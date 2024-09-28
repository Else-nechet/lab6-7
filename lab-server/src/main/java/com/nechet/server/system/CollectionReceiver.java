package com.nechet.server.system;


import java.sql.SQLException;
import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

public interface CollectionReceiver<T extends AbstractCollection<E>, E> {
    T getCollection() throws SQLException;
    void setCollection(T value) throws SQLException;
    void addElementToCollection(E value) throws SQLException;
    void clearCollection() throws SQLException;
    int getSize();
    void baseSort();
    Date getInitDate();
    E getMaxElement(Comparator<E> comp);
    E getMinElement(Comparator<E> comp);
    boolean isEmpty();
}