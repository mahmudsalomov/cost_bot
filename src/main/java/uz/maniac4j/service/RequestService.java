package uz.maniac4j.service;

import org.springframework.stereotype.Service;
import uz.maniac4j.model.Item;
import uz.maniac4j.model.Request;
import uz.maniac4j.model.TelegramUser;
import uz.maniac4j.repository.ItemRepository;
import uz.maniac4j.repository.RequestRepository;
import uz.maniac4j.repository.TelegramUserRepository;

import java.io.IOException;
import java.util.List;

@Service
public class RequestService {
    private final RequestRepository requestRepository;
    private final ItemRepository itemRepository;
    private final TelegramUserRepository telegramUserRepository;
    private final SheetService sheetService;

    public RequestService(RequestRepository requestRepository, ItemRepository itemRepository, TelegramUserRepository telegramUserRepository, SheetService sheetService) {
        this.requestRepository = requestRepository;
        this.itemRepository = itemRepository;
        this.telegramUserRepository = telegramUserRepository;
        this.sheetService = sheetService;
    }

    public void reset(TelegramUser user){
        List<Request> all = requestRepository.findAllByUserAndSentFalseOrderByCreatedAtDesc(user);
        for (Request request : all) {
            request.setSent(true);
            requestRepository.save(request);
        }
    }

    public void sectionReset(TelegramUser user){
        List<Request> all = requestRepository.findAllByUserAndSentFalseOrderByCreatedAtDesc(user);
        for (Request request : all) {
            List<Item> items = itemRepository.findAllByRequest(request);
            for (Item item : items) {
                if (item.getSection()!=user.getSection()){
                    itemRepository.delete(item);
                }
            }
            items = itemRepository.findAllByRequest(request);
            if (items.size()==0){
                request.setSent(true);
                requestRepository.save(request);
            }
        }
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

    public Request confirm(TelegramUser user) throws IOException {
        Request last = getLast(user);

        sheetService.write(last,itemRepository.findAllByRequest(last));
        last.setSent(true);
        return requestRepository.save(last);
    }

//    public Request addItem(TelegramUser user, Request request){
//
//    }
}
