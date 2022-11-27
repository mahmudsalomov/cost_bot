package uz.maniac4j.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.maniac4j.component.MessageTemplate;
import uz.maniac4j.model.State;
import uz.maniac4j.model.TelegramUser;
import uz.maniac4j.model.Translations;
import uz.maniac4j.service.SheetService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class StartHandler implements Handler{

    private final MessageTemplate messageTemplate;
    private final SheetService sheetService;

    public StartHandler(MessageTemplate messageTemplate, SheetService sheetService) {
        this.messageTemplate = messageTemplate;
        this.sheetService = sheetService;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, String message) throws IOException {
//        System.out.println(user);
//        System.out.println(message);
        if (Objects.equals(message, "start")||Objects.equals(message, "/start")) {
//            sheetService.write();
            return Collections.singletonList(messageTemplate.mainMenu(user));
        }
        return null;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, CallbackQuery callback) throws IOException {
        String text=callback.getData();
//        System.out.println("START HANDLER");
//        SendMessage sendMessage = messageTemplate.sectionSelect(user, true);

//        if (text.equals(Translations.ADD_REQUEST.name())) return messageTemplate.editTextAndReplyMarkup(user,callback.getMessage().getMessageId(),sendMessage.getText(),messageTemplate.sectionSelect(user));
//        if (text.equals(Translations.ADD_REQUEST.name())) return Collections.singletonList(messageTemplate.sectionSelect(user, true));
        if (text.equals(Translations.ADD_REQUEST.name())) return Collections.singletonList(messageTemplate.sectionTypeSelect(user));
        return null;
    }

    @Override
    public State operatedBotState() {
        return State.FIRST;
    }

    @Override
    public List<String> operatedCallBackQuery(TelegramUser user) {
        List<String> result=new ArrayList<>();
//        result.add(State.PRODUCT.name());
        result.add(Translations.ADD_REQUEST.getRu());
        result.add(Translations.ADD_REQUEST.name());
//        result.add("p");
//        result.add("b");
        return result;
    }
}
