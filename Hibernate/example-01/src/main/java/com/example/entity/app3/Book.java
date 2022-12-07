package com.example.entity.app3;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Book.findBookByBookId", query = "from Book b where b.bookId.title = ?1 and b.bookId.language = ?2")
public class Book {
    @EmbeddedId
    private BookId bookId;

    private String description;

    public Book() {

    }

    public Book(final BookId bookId) {
        this.bookId = bookId;
    }

    public BookId getBookId() {
        return this.bookId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book [bookId=" + this.bookId + ", description=" + this.description + "]";
    }
}
