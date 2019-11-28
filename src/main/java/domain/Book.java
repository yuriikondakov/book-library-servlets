package domain;

import java.time.LocalDate;
import java.util.List;

public class Book {
    private Integer id;
    private List<Author> authors;
    private String name;
    private String description;
    private Integer shelfId;
    private LocalDate issueDate;
    private LocalDate returnDate;

    private Book(Builder builder) {
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

    public List<Author> getAuthors() {
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

    public static Builder builder() {
        return new Builder();
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", authors=" + authors +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", shelfId=" + shelfId +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                '}';
    }

    public static class Builder {
        private Integer id;
        private List<Author> authors;
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

        public Builder withAuthors(List<Author> authors) {
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

        public Book build() {
            return new Book(this);
        }
    }
}
