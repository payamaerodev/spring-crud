package ramz.test.testr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BookRepository  extends JpaRepository<Book, Long> {
    Optional<Book> findByFirstName(String firstName);
}

