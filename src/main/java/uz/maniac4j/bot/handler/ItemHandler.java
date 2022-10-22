//package uz.maniac4j.bot.handler;
//
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
//import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
//import uz.maniac4j.component.MessageTemplate;
//import uz.maniac4j.model.Section;
//import uz.maniac4j.model.State;
//import uz.maniac4j.model.TelegramUser;
//import uz.maniac4j.model.Translations;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class ItemHandler implements Handler{
//
//    private final MessageTemplate messageTemplate;
//
//    public ItemHandler(MessageTemplate messageTemplate) {
//        this.messageTemplate = messageTemplate;
//    }
//
//    @Override
//    public List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, String message) throws IOException {
//        return null;
//    }
//
//    @Override
//    public List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, CallbackQuery callback) throws IOException {
////        System.out.println("ITEM HANDLER");
//        return null;
//    }
//
//    @Override
//    public State operatedBotState() {
//        return State.THIRD;
//    }
//
//    @Override
//    public List<String> operatedCallBackQuery(TelegramUser user) {
//        List<String> result=new ArrayList<>();
//        result.add(Translations.ADD_ITEM.name());
//        return result;
//    }
//}
