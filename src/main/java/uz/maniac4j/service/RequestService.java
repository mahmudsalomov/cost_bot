package uz.maniac4j.service;

import org.springframework.stereotype.Service;
import uz.maniac4j.model.Request;
import uz.maniac4j.model.TelegramUser;
import uz.maniac4j.repository.ItemRepository;
import uz.maniac4j.repository.RequestRepository;
import uz.maniac4j.repository.TelegramUserRepository;

import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final ItemRepository itemRepository;
    private final TelegramUserRepository telegramUserRepository;

    public RequestService(RequestRepository requestRepository, ItemRepository itemRepository, TelegramUserRepository telegramUserRepository) {
        this.requestRepository = requestRepository;
        this.itemRepository = itemRepository;
        this.telegramUserRepository = telegramUserRepository;
    }

    public Request save(TelegramUser user){
        List<Request> all = requestRepository.findAllByUserAndSentFalseOrderByCreatedAtDesc(user);

        if (all.size()>0){
            for (int i = 1; i < all.size(); i++) {
                all.get(i).setSent(true);
                requestRepository.save(all.get(i));
            }

            return all.get(0);
        }
        else {
            return requestRepository.save(Request.builder().sent(false).user(user).build());
        }
    }

    public Request get(TelegramUser user){
        return save(user);
    }

    public Request getLast(TelegramUser user){
        List<Request> all = requestRepository.findAllByUserAndSentFalseOrderByCreatedAtDesc(user);

        if (all.size()>0){
            for (int i = 1; i < all.size(); i++) {
                all.get(i).setSent(true);
                requestRepository.save(all.get(i));
            }

            return all.get(0);
        }
        else {
            return null;
        }
    }

//    public Request addItem(TelegramUser user, Request request){
//
//    }
}
