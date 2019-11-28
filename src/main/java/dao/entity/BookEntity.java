package dao.entity;

import java.time.LocalDate;
import java.util.List;

public class BookEntity {
    private Integer id;
    private List<AuthorEntity> authors;
    private String name;
    private String description;
    private Integer shelfId;
    private LocalDate issueDate;
    private LocalDate returnDate;

    private BookEntity(Builder builder) {
        this.id = builder.id;
        this.authors = builder.authors;
        this.name = builder.name;
        this.description = builder.description;
        this.shelfId = builder.shelfId;
        this.issueDate = builder.issueDate;
        this.returnDate = builder.returnDate;
    }

    public Integer getId() {
        return id;
    }

    public List<AuthorEntity> getAuthors() {
        return authors;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getShelfId() {
        return shelfId;
    }
    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {
        private Integer id;
        private List<AuthorEntity> authors;
        private String name;
        private String description;
        private Integer shelfId;
        private LocalDate issueDate;
        private LocalDate returnDate;

        private Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withAuthors(List<AuthorEntity> authors) {
            this.authors = authors;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withShelfId(Integer shelfId) {
            this.shelfId = shelfId;
            return this;
        }
        public Builder  withIssueDate(LocalDate issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder  withReturnDate(LocalDate returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public BookEntity build() {
            return new BookEntity(this);
        }
    }
}
