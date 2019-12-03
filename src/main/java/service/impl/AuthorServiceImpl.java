package service.impl;

import dao.AuthorDao;
import dao.entity.AuthorEntity;
import domain.Author;
import org.apache.log4j.Logger;
import service.AuthorService;
import service.mapper.AuthorMapper;

import java.util.List;
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

    @Override
    public Author save(Author author) {
        AuthorEntity authorEntity = authorMapper.mapAuthorToAuthorEntity(author);
        return authorMapper.mapAuthorEntityToAuthor(authorDao.save(authorEntity));
    }
}
