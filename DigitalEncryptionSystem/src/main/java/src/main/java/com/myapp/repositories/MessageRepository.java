package src.main.java.com.myapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import src.main.java.com.myapp.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Define custom methods if needed
}
