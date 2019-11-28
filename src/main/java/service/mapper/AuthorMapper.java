package service.mapper;

import domain.Author;
import dao.entity.AuthorEntity;

public class AuthorMapper {

    public Author mapAuthorEntityToAuthor(AuthorEntity authorEntity) {
        return new Author(authorEntity.getId(), authorEntity.getFirstName(), authorEntity.getLastName());
    }

    public AuthorEntity mapAuthorToAuthorEntity(Author author) {
        return new AuthorEntity(author.getId(), author.getFirstName(), author.getLastName());
    }
    
}
