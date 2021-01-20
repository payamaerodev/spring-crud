package ramz.test.testr;

import javassist.NotFoundException;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@Controller
@RequestMapping("/books")
public class UserController {
    @Autowired
    Service service;
    @Autowired
    BookRepository bookRepository;


    @GetMapping("/showBook")
    public ModelAndView show() {
        List<Book> list = service.getAllBooks();

        ModelAndView modelAndView = new ModelAndView("book");
        modelAndView.addObject("book", list);
        return modelAndView;
//        return "book";
    }


    @GetMapping
    public ResponseEntity<List<Book>> getAllEmployees() {
        List<Book> list = service.getAllBooks();

        return new ResponseEntity<List<Book>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/showEdit/{id}")
    public ModelAndView showEdit(@PathVariable("id") Long id) throws NotFoundException{
        Book book = bookRepository.getOne(id);
        ModelAndView modelAndView = new ModelAndView("editbook");
        modelAndView.addObject("book", book);
        return modelAndView;
    }
    @GetMapping("/showCreate")
    public String showCreate() throws NotFoundException{
        return "createbook";
    }
    @PostMapping(value = "/CreateUser", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE,MediaType.ALL_VALUE })
    public ResponseEntity<Book> createUser(@RequestBody UserRegistrationRequest userRegistrationRequest) throws NotFoundException{
        System.out.println(userRegistrationRequest.getEmail());
        return service.create(userRegistrationRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getEmployeeById(@PathVariable("id") Long id)
            throws NotFoundException {
        Book entity = service.getEmployeeById(id);

        return new ResponseEntity<Book>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> createOrUpdateEmployee(Book employee)
            throws NotFoundException {
        Book updated = service.createOrUpdateEmployee(employee);
        return new ResponseEntity<Book>(updated, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteEmployeeById(@PathVariable("id") Long id)
            throws NotFoundException {
        Book book=bookRepository.getOne(id);
        System.out.println(book.getEmail());
        service.deleteEmployeeById(id);

        return new RedirectView("/books/showBook");    }

}