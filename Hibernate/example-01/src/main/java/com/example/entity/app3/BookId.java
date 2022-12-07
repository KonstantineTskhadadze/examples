package com.example.entity.app3;


import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class BookId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String language;

    public BookId() {

    }

    public BookId(final String title, final String language) {
        this.title = title;
        this.language = language;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.language == null) ? 0 : this.language.hashCode());
        result = prime * result + ((this.title == null) ? 0 : this.title.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        final BookId other = (BookId) obj;
        if (this.language == null) {
            if (other.language != null)
                return false;
        } else if (!this.language.equals(other.language))
            return false;
        if (this.title == null) {
            if (other.title != null)
                return false;
        } else if (!this.title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BookId [title=" + this.title + ", language=" + this.language + "]";
    }


}
