package com.distribuida.services;

import com.distribuida.db.Book;

import java.util.List;

public interface BookService {

    void insert(Book libro);

    Book findById(int id);

    List<Book> findAll();

    Boolean update(Book libro);

    Boolean delete(Integer id);
}
