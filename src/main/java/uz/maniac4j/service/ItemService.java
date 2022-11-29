package uz.maniac4j.service;

import org.springframework.stereotype.Service;
import uz.maniac4j.model.Item;
import uz.maniac4j.model.Request;
import uz.maniac4j.model.Section;
import uz.maniac4j.model.TelegramUser;
import uz.maniac4j.repository.ItemRepository;
import uz.maniac4j.repository.RequestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final RequestRepository requestRepository;
    private final RequestService requestService;

    public ItemService(ItemRepository itemRepository, RequestRepository requestRepository, RequestService requestService) {
        this.itemRepository = itemRepository;
        this.requestRepository = requestRepository;
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
//    public Item changeName(Long itemId, String name){
//        Optional<Item> byId = itemRepository.findById(itemId);
//        if (byId.isPresent()){
//            byId.get().setName(name);
//            return edit(byId.get());
//        }
//        return null;
//    }

    //Sonini tahrirlash uchun maxsus
//    public Item changeAmount(Long itemId, String amount){
//        Optional<Item> byId = itemRepository.findById(itemId);
//        if (byId.isPresent()){
//            byId.get().setAmount(amount);
//            return edit(byId.get());
//        }
//        return null;
//    }

    //Section bo'yicha yangi yaratish uchun maxsus
    public Item firstAdd(TelegramUser user, Section section){
        return add(user, Item.builder().section(section).build());
    }

    public List<Item> allByRequest(Request request){
        return itemRepository.findAllByRequest(request);
    }

    public void delete(TelegramUser user, Long id){
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()){
            Request request=optionalItem.get().getRequest();
            itemRepository.delete(optionalItem.get());
            List<Item> items = itemRepository.findAllByRequest(request);
            if (items.size()==0){
//                request.setSent(true);
//                requestRepository.save(request);
                requestService.reset(user);
//                System.out.println(requestRepository.findAll());
            }
        }
    }
}
