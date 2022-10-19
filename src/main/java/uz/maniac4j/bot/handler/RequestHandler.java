package uz.maniac4j.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.maniac4j.component.MessageTemplate;
import uz.maniac4j.model.*;
import uz.maniac4j.repository.TelegramUserRepository;
import uz.maniac4j.service.ItemService;
import uz.maniac4j.service.RequestService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class RequestHandler implements Handler{

    private final MessageTemplate messageTemplate;

    private final RequestService requestService;
    private final ItemService itemService;
    private final ItemHandler itemHandler;
    private final TelegramUserRepository telegramUserRepository;

    public RequestHandler(MessageTemplate messageTemplate, RequestService requestService, ItemService itemService, ItemHandler itemHandler, TelegramUserRepository telegramUserRepository) {
        this.messageTemplate = messageTemplate;
        this.requestService = requestService;
        this.itemService = itemService;
        this.itemHandler = itemHandler;
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, String message) throws IOException {

        String[] strings = message.split(":");
        System.out.println(Arrays.toString(strings));

        if (strings.length>=2){
            try {
                Integer.parseInt(strings[1]);
            }catch (Exception e){
                return Collections.singletonList(messageTemplate.addItem(user,"Mahsulotlar sonini to'g'ri kiriting!\n"+user.getSection().getRu() + " ga maxsulotni quyidagicha yuboring:\n name:amount:note"));
            }
            Item item = Item.builder()
                    .section(user.getSection())
                    .name(strings[0])
                    .amount(Integer.parseInt(strings[1]))
                    .description(strings.length == 3 ? strings[2] : "")
                    .build();
            item = itemService.add(user, item);
            return Collections.singletonList(messageTemplate.sectionSelect(user));
        }

        return null;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, CallbackQuery callback) throws IOException {
        String text=callback.getData();
        System.out.println("REQUEST HANDLER");

        Section section = checkSection(text);
        if (section!=null){
            user.setSection(section);
            user.setState(State.SECOND);
            user=telegramUserRepository.save(user);
            return Collections.singletonList(messageTemplate.addItem(user));
        }


        return null;
    }

    @Override
    public State operatedBotState() {
        return State.SECOND;
    }

    @Override
    public List<String> operatedCallBackQuery(TelegramUser user) {
        List<String> result=new ArrayList<>();

        for (Section section : Section.values()) {
            result.add(section.name());
        }
        result.add(Translations.SEND_REQUEST.name());
        return result;
    }

    public Section checkSection(String text){
        for (Section section : Section.values()) {
            if (section.name().equals(text))
                return section;
        }
        return null;
    }

}
