package com.distribuida.db;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(generator = "seq_book", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_book", sequenceName = "seq_book", allocationSize = 1)
    private Integer id;

    private String isbn;

    private String title;

    private String author;

    private BigDecimal price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
