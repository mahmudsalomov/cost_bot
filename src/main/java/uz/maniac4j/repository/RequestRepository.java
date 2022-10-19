package uz.maniac4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.maniac4j.model.Request;
import uz.maniac4j.model.TelegramUser;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request,Long> {
    List<Request> findAllByUser(TelegramUser user);
    List<Request> findAllByUserAndSentFalse(TelegramUser user);
    List<Request> findAllByUserAndSentFalseOrderByCreatedAtDesc(TelegramUser user);
}
