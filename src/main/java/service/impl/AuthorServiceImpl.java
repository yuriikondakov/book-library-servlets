package service.impl;

import dao.AuthorDao;
import domain.Author;
import domain.Book;
import org.apache.log4j.Logger;
import service.AuthorService;
import service.BookService;
import service.mapper.AuthorMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {
    private static final Logger LOGGER = Logger.getLogger(AuthorServiceImpl.class);
    private final AuthorDao authorDao;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorDao authorDao, AuthorMapper authorMapper) {
        this.authorDao = authorDao;
        this.authorMapper = authorMapper;
    }


    @Override
    public Author findById(Integer id) {
        return authorDao.findById(id).map(authorMapper::mapAuthorEntityToAuthor).orElse(null);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll().stream().map(authorMapper::mapAuthorEntityToAuthor).collect(Collectors.toList());
    }
}
