package uz.maniac4j.component;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import uz.maniac4j.model.*;
import uz.maniac4j.repository.TelegramUserRepository;
import uz.maniac4j.service.ItemService;
import uz.maniac4j.service.RequestService;
import uz.maniac4j.utils.ButtonModel.Col;
import uz.maniac4j.utils.ButtonModel.Row;

import java.io.Serializable;
import java.util.ArrayList;
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
        if (last!=null) col.add(Translations.DRAFT_REQUEST.getRu(),Translations.DRAFT_REQUEST.name());

//        col.add("Yangi zayavka yaratish!");

//        col.add(Translations.BACK.getRu(),Translations.BACK.name());
        return SendMessage.builder()
                .chatId(user.getId())
                .text("Xush kelibsiz!")
                .replyMarkup(col.getMarkup())
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }


    //Razdellarni tanlash
//    public SendMessage sectionSelect(TelegramUser user){
//        return sectionSelect(user,false);
//    }



    public SendMessage sectionSelect(TelegramUser user, boolean isNew){
        Request request = requestService.getLast(user);
        if (isNew||request==null){
//            requestService.reset(user);
            return SendMessage.builder()
                    .chatId(user.getId())
                    .text("Section tanlang")
                    .replyMarkup(sectionSelect(user))
                    .parseMode(ParseMode.MARKDOWN)
                    .build();
        }else {
            return SendMessage.builder()
                    .chatId(user.getId())
                    .text("Section tanlang")
                    .replyMarkup(sectionSelect(user))
                    .parseMode(ParseMode.MARKDOWN)
                    .build();
        }

    }


    public InlineKeyboardMarkup sectionSelect(TelegramUser user){
        Request request = requestService.getLast(user);
        Col col=new Col();
        if (request!=null){
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
                        row.add(Translations.DELETE_ITEM.getRu(),Translations.DELETE_ITEM.name()+":"+item.getId());
                        col.add(row);
                    }
                }
            }
            if (items.size()>0) col.add(Translations.SEND_REQUEST.getRu(),Translations.SEND_REQUEST.name());

            col.add(Translations.BACK.getRu(),Translations.BACK.name());
            return col.getMarkup();
        }else {
            requestService.reset(user);
            for (Section section : Section.values()) {
                col.add("➕ "+section.name(),section.name());
            }
            col.add(Translations.BACK.getRu(),Translations.BACK.name());
            return col.getMarkup();
        }
    }



    //Mahsulot qo'shishni so'rash
    public SendMessage addItem(TelegramUser user,String text){
        Col col=new Col();
        col.add(Translations.BACK.getRu(),Translations.BACK.name());

        return SendMessage.builder()
                .chatId(user.getId())
                .text(text)
                .replyMarkup(col.getMarkup())
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }
    public SendMessage addItem(TelegramUser user){
        return addItem(user,user.getSection().getRu() + " ga maxsulotni quyidagicha yuboring:\n name:amount:note");
    }

    public SendMessage confirmRequestMessage(TelegramUser user){
        Request request = requestService.get(user);
        List<Item> items = itemService.allByRequest(request);
        String text="";
        String itemList="";
        int counter=0;
        if (items.size()>0){
            text+="*\uD83D\uDCDD Zayavka\n*";
            for (Section section : Section.values()) {
                itemList="";
                counter=0;
                for (Item item : items) {
                    if (item.getSection().equals(section)){
                        counter++;
                        itemList+="*"+counter+" - *"+item.getName()+" \uD83D\uDD8A "+item.getAmount()+" \uD83D\uDD8A "+item.getDescription()+"\n";
                    }
                }
                if (itemList.length()>1) text+="\n✳ *"+section.getRu()+"* : \n"+itemList;
            }
            text+="\n\n*Zayavkani tasdiqlaysizmi?*";
        }
        Col col=new Col();
        Row row=new Row();
        row.add(Translations.CONFIRM_REQUEST.getRu(),Translations.CONFIRM_REQUEST.name());
        row.add(Translations.CANCEL_REQUEST.getRu(),Translations.CANCEL_REQUEST.name());
        col.add(row);
        col.add(Translations.BACK_SECTION.getRu(),Translations.BACK_SECTION.name());
        return SendMessage.builder()
                .chatId(user.getId())
                .text(text)
                .replyMarkup(col.getMarkup())
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }

    public SendMessage confirm(TelegramUser user) {
        return SendMessage.builder().chatId(user.getId()).text("Request yuborildi!").build();
    }








    /** Edit messages **/
    public EditMessageText editText(TelegramUser user, String text, Integer messageId){
        EditMessageText editMessageText=new EditMessageText();
        editMessageText.setChatId(String.valueOf(user.getId()));
        editMessageText.setText(text);
        editMessageText.enableMarkdown(true);
        editMessageText.enableWebPagePreview();
        editMessageText.setMessageId(messageId);
        return editMessageText;
    }

    public EditMessageReplyMarkup editReplyMarkup(TelegramUser user, InlineKeyboardMarkup markup, Integer messageId){
        EditMessageReplyMarkup editMessageReplyMarkup=new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(String.valueOf(user.getId()));
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(markup);
        return editMessageReplyMarkup;
    }

    public List<PartialBotApiMethod<? extends Serializable>> editTextAndReplyMarkup(TelegramUser user, Integer messageId, String text, InlineKeyboardMarkup markup){

        List<PartialBotApiMethod<? extends Serializable>> list=new ArrayList<>();
        list.add(editText(user, text, messageId));
        list.add(editReplyMarkup(user, markup, messageId));
        return list;

    }
}
