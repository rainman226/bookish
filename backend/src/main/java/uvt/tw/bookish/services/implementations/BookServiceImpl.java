package uvt.tw.bookish.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import uvt.tw.bookish.entities.Book;
import uvt.tw.bookish.repositories.BookRepository;
import uvt.tw.bookish.services.BookService;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBookByFields(Integer id,
                                      String title,
                                      String author,
                                      Integer genreID,
                                      String isbn) {
        Specification<Book> spec = Specification.where(null);

        if(id != null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id));
        }

        if(title != null){
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }

        if(author != null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("author"), author));
        }

        if(genreID != null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genreID"), genreID));
        }

        if(isbn != null){
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ISBN"), isbn));
        }

        List<Book> bookList = bookRepository.findAll(spec);

        if(bookList.isEmpty()) {
            //throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            System.out.println("ce nasol coaie");
        }

        return bookList;
    }
}
