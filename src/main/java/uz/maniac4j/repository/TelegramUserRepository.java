package uz.maniac4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.maniac4j.model.TelegramUser;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
}