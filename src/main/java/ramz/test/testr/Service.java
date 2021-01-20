package ramz.test.testr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


@org.springframework.stereotype.Service
public class Service {

    @Autowired
    BookRepository repository;

    public List<Book> getAllBooks() {
        List<Book> bookList = repository.findAll();

        if (bookList.size() > 0) {
            return bookList;
        } else {
            return new ArrayList<Book>();
        }
    }

    public Book getEmployeeById(Long id) throws NotFoundException {
        Optional<Book> book = repository.findById(id);

        if (book.isPresent()) {
            return book.get();
        } else {
            throw new NotFoundException("No book record exist for given id");
        }
    }

    public ResponseEntity<Book> create(UserRegistrationRequest userRegistrationRequest) {
        Book book=new Book();
        book.setEmail(userRegistrationRequest.getEmail());
        book.setFirstName(userRegistrationRequest.getFirstname());
        book.setLastName(userRegistrationRequest.getLastname());
        repository.save(book);
    return new  ResponseEntity<Book>( new HttpHeaders(), HttpStatus.OK);}

    public Book createOrUpdateEmployee(Book book) throws NotFoundException {
//        Optional<Book> employee = repository.findByFirstName(book.getFirstName());
        Optional<Book> employee = repository.findById(book.getId());

        if (employee.isPresent()) {
            Book newEntity = employee.get();
            newEntity.setEmail(book.getEmail());
            newEntity.setFirstName(book.getFirstName());
            newEntity.setLastName(book.getLastName());

            newEntity = repository.save(newEntity);

            return newEntity;
        } else {
            book = repository.save(book);

            return book;
        }
    }

    public void deleteEmployeeById(Long id) throws NotFoundException {
        Optional<Book> employee = repository.findById(id);

        if (employee.isPresent()) {
            repository.delete(employee.get());
        } else {
            throw new NotFoundException("No employee record exist for given id");
        }
    }
}
