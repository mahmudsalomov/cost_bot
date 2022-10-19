package uz.maniac4j.bot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.maniac4j.bot.UpdateReceiver;
import uz.maniac4j.repository.TelegramUserRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.lang.StrictMath.toIntExact;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {


    @Value("${bot.name}")
    @Getter
    private String botUsername;

    @Value("${bot.token}")
    @Getter
    private String botToken;

    private final UpdateReceiver updateReceiver;
    private final TelegramUserRepository telegramUserRepository;


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
//        if (update.hasMessage()){
//            SendMessage sendMessage=new SendMessage();
//            sendMessage.setText("Salom");
//            System.out.println(update.getUpdateId()+" Update id");
//            System.out.println(update.getMessage().getChatId()+" Chat id");
//            System.out.println(update.getMessage().getFrom().getId()+" User id");
//            System.out.println(update.getMessage().getFrom().getUserName()+" Username");
//            System.out.println(update.getMessage().getFrom().getFirstName()+" Firstname");
//            System.out.println(update.getMessage().getFrom().getLastName()+" Lastname");
//            System.out.println(update.getMessage().getFrom().getLanguageCode()+" Language Code");
//            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
//            executeWithExceptionCheck(sendMessage);
//        }

        List<PartialBotApiMethod<? extends Serializable>> messagesToSend = updateReceiver.handle(update);
        if (messagesToSend != null && !messagesToSend.isEmpty()) {

            messagesToSend.forEach(response -> {
                if (response instanceof SendMessage) {


                    executeWithExceptionCheck((SendMessage) response);
//                    }
                }
                if (response instanceof SendPhoto) {
                    executeWithExceptionCheck((SendPhoto) response);
                }else if (response instanceof EditMessageReplyMarkup){
                    executeWithExceptionCheck((EditMessageReplyMarkup) response);
                }else if (response instanceof AnswerCallbackQuery){
                    executeWithExceptionCheck((AnswerCallbackQuery) response);
                } else if (response instanceof AnswerInlineQuery){
                    executeWithExceptionCheck((AnswerInlineQuery) response);
                } else if (response instanceof EditMessageText){
                    executeWithExceptionCheck((EditMessageText) response);
                }

            });
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public void executeWithExceptionCheck(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("oops");
        }
    }

    public void executeWithExceptionCheck(EditMessageReplyMarkup sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("oops");
        }
    }

    public void executeWithExceptionCheck(EditMessageText sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("oops");
        }
    }

    public void executeWithExceptionCheck(AnswerCallbackQuery sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("oops");
        }
    }

    public void executeWithExceptionCheck(SendPhoto sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("oops");
        }
    }

    public void executeWithExceptionCheck(AnswerInlineQuery answerInlineQuery) {
        try {
            execute(answerInlineQuery);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("oops");
        }
    }
}
