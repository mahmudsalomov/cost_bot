package uz.maniac4j.bot.handler;

import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import uz.maniac4j.model.State;
import uz.maniac4j.model.TelegramUser;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface Handler {
    List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, String message) throws IOException;
    List<PartialBotApiMethod<? extends Serializable>> handle(TelegramUser user, CallbackQuery callback) throws IOException;

    State operatedBotState();

    List<String> operatedCallBackQuery(TelegramUser user);
}
