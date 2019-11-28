package service;

import domain.Author;
import domain.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author findById(Integer id);

    List<Author> getAllAuthors();
}
