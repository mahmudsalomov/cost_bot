package uz.maniac4j.component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import uz.maniac4j.model.*;
import uz.maniac4j.repository.TelegramUserRepository;
import uz.maniac4j.service.ItemService;
import uz.maniac4j.service.RequestService;
import uz.maniac4j.utils.ButtonModel.Col;
import uz.maniac4j.utils.ButtonModel.Row;

import java.util.List;

@Component
public class MessageTemplate {

    private final TelegramUserRepository telegramUserRepository;
    private final RequestService requestService;
    private final ItemService itemService;

    public MessageTemplate(TelegramUserRepository telegramUserRepository, RequestService requestService, ItemService itemService) {
        this.telegramUserRepository = telegramUserRepository;
        this.requestService = requestService;
        this.itemService = itemService;
    }

    public SendMessage mainMenu(TelegramUser user){
        Col col=new Col();

        col.add(Translations.ADD_REQUEST.getRu(), Translations.ADD_REQUEST.name());
        Request last = requestService.getLast(user);
        if (last!=null) col.add("Sizda  yuborilmagan zayavka bor!",last.getId().toString());

//        col.add("Yangi zayavka yaratish!");

//        col.add(Translations.BACK.getRu(),Translations.BACK.name());
        return SendMessage.builder()
                .chatId(user.getId())
                .text("Xush kelibsiz!")
                .replyMarkup(col.getMarkup())
                .build();
    }


    //Razdellarni tanlash
    public SendMessage sectionSelect(TelegramUser user){
        Col col=new Col();
        Request request = requestService.get(user);
        List<Item> items = itemService.allByRequest(request);
        for (Section section : Section.values()) {
            col.add("➕ "+section.name(),section.name());
            for (Item item : items) {
                if (item.getSection().equals(section)){
                    Row row=new Row();
                    row.add(item.getName());
                    row.add(String.valueOf(item.getAmount()));
//                    row.add("-");
//                    row.add("+");
                    row.add("❌");
                    col.add(row);
                }
            }
        }
        if (items.size()>0) col.add(Translations.SEND_REQUEST.getRu(),Translations.SEND_REQUEST.name());

        col.add(Translations.BACK.getRu(),Translations.BACK.name());
        return SendMessage.builder()
                .chatId(user.getId())
                .text("Section tanlang")
                .replyMarkup(col.getMarkup())
                .build();
    }


    //Mahsulot qo'shishni so'rash
    public SendMessage addItem(TelegramUser user,String text){
        Col col=new Col();
        col.add(Translations.BACK.getRu(),Translations.BACK.name());

        return SendMessage.builder()
                .chatId(user.getId())
                .text(text)
                .replyMarkup(col.getMarkup())
                .build();
    }
    public SendMessage addItem(TelegramUser user){
        return addItem(user,user.getSection().getRu() + " ga maxsulotni quyidagicha yuboring:\n name:amount:note");
    }


}
