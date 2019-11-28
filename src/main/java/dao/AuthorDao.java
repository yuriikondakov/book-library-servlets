package dao;

import dao.entity.AuthorEntity;

import java.util.List;

public interface AuthorDao extends CrudDao<AuthorEntity, Integer> {
    List<AuthorEntity> findByBookId(Integer id);
}
