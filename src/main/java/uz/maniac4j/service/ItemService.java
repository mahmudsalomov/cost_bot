package uz.maniac4j.service;

import org.springframework.stereotype.Service;
import uz.maniac4j.model.Item;
import uz.maniac4j.model.Request;
import uz.maniac4j.model.Section;
import uz.maniac4j.model.TelegramUser;
import uz.maniac4j.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final RequestService requestService;

    public ItemService(ItemRepository itemRepository, RequestService requestService) {
        this.itemRepository = itemRepository;
        this.requestService = requestService;
    }

    public Item add(TelegramUser user, Item item){
        Request request = requestService.get(user);
        item.setRequest(request);
        return itemRepository.save(item);
    }

    public Item edit(Item item){
        return itemRepository.save(item);
    }


    //Nomni tahrirlash uchun maxsus
    public Item changeName(Long itemId, String name){
        Optional<Item> byId = itemRepository.findById(itemId);
        if (byId.isPresent()){
            byId.get().setName(name);
            return edit(byId.get());
        }
        return null;
    }

    //Sonini tahrirlash uchun maxsus
    public Item changeAmount(Long itemId, int amount){
        Optional<Item> byId = itemRepository.findById(itemId);
        if (byId.isPresent()){
            byId.get().setAmount(amount);
            return edit(byId.get());
        }
        return null;
    }

    //Section bo'yicha yangi yaratish uchun maxsus
    public Item firstAdd(TelegramUser user, Section section){
        return add(user, Item.builder().section(section).build());
    }

    public List<Item> allByRequest(Request request){
        return itemRepository.findAllByRequest(request);
    }
}
