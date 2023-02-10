package uz.maniac4j.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.maniac4j.model.*;
import uz.maniac4j.repository.TelegramUserRepository;
import uz.maniac4j.service.ItemService;
import uz.maniac4j.service.RequestService;
import uz.maniac4j.utils.ButtonModel.Col;
import uz.maniac4j.utils.ButtonModel.Row;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MessageTemplate {

    @Value("${prixod.permission.list}")
    String[] permissionList;
//    private final List<Long> permissionList=List.of(1168887L,5398131093L);
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

        col.add(Translations.ADD_REQUEST.getRu(user), Translations.ADD_REQUEST.name());
        Request last = requestService.getLast(user);
        if (last!=null) col.add(Translations.DRAFT_REQUEST.getRu(user),Translations.DRAFT_REQUEST.name());

//        col.add("Yangi zayavka yaratish!");

//        col.add(Translations.BACK.getRu(user),Translations.BACK.name());
        return SendMessage.builder()
                .chatId(user.getId())
                .text(Translations.TXT_MAIN.getRu(user))
                .replyMarkup(col.getMarkup())
                .parseMode(ParseMode.MARKDOWN)
                .build();
    }

    public List<PartialBotApiMethod<? extends Serializable>> mainMenuEdit(TelegramUser user, Integer messageId){
        Col col=new Col();


        col.add(Translations.ADD_REQUEST.getRu(user), Translations.ADD_REQUEST.name());
        Request last = requestService.getLast(user);
        if (last!=null) col.add(Translations.DRAFT_REQUEST.getRu(user),Translations.DRAFT_REQUEST.name());

//        col.add("Yangi zayavka yaratish!");

//        col.add(Translations.BACK.getRu(user),Translations.BACK.name());
        List<PartialBotApiMethod<? extends Serializable>> list=new ArrayList<>();
        list.add(editText(user, Translations.TXT_MAIN.getRu(user), messageId));
        list.add(editReplyMarkup(user, col.getMarkup(), messageId));
        return list;
//        return SendMessage.builder()
//                .chatId(user.getId())
//                .text(Translations.TXT_MAIN.getRu(user))
//                .replyMarkup(col.getMarkup())
//                .parseMode(ParseMode.MARKDOWN)
//                .build();
    }


//    public List<PartialBotApiMethod<? extends Serializable>> editTextAndReplyMarkup(TelegramUser user, Integer messageId, String text, InlineKeyboardMarkup markup){
//
//        List<PartialBotApiMethod<? extends Serializable>> list=new ArrayList<>();
//        list.add(editText(user, text, messageId));
//        list.add(editReplyMarkup(user, markup, messageId));
//        return list;
//
//    }



//    public SendMessage sectionSelect(TelegramUser user){
//        return sectionSelect(user,false);
//    }


    //Razdellarni tanlash

    public SendMessage sectionTypeSelect(TelegramUser user){

        Col col=new Col();
        Row row=new Row();
        boolean permission = checkPermission(user);
        for (SectionType type : SectionType.values()) {
            if (!permission){
                if (!type.equals(SectionType.THIRD_TYPE))
                    row.add(type.getRu(user), type.name());
            } else
                row.add(type.getRu(user), type.name());
        }
        col.add(row);
        col.add(Translations.BACK.getRu(user),Translations.BACK.name());


        return SendMessage.builder()
                .chatId(user.getId())
                .text(Translations.TXT_SECTION.getRu(user))
                .replyMarkup(col.getMarkup())
                .parseMode(ParseMode.HTML)
                .build();

    }
    public SendMessage sectionSelect(TelegramUser user, boolean isNew){
        if (isNew) {
            user.setSection(null);
            user=telegramUserRepository.save(user);
//            requestService.reset(user);
        }
//        Request request = requestService.getLast(user);
//        if (isNew||request==null){
//            requestService.reset(user);
        InlineKeyboardMarkup replyMarkup = sectionSelect(user);
        if (replyMarkup==null) return sectionTypeSelect(user);
        return SendMessage.builder()
                    .chatId(user.getId())
                    .text(user.getSection()==null?"<i>"+user.getSectionType().getRu(user)+"</i>\n\n"+Translations.TXT_SECTION.getRu(user):user.getSectionType().getRu(user)+"\n"+user.getSection().getRu(user))
                    .replyMarkup(replyMarkup)
                    .parseMode(ParseMode.HTML)
                    .build();
//        }else {
//            return SendMessage.builder()
//                    .chatId(user.getId())
//                    .text("Section tanlang")
//                    .replyMarkup(sectionSelect(user))
//                    .parseMode(ParseMode.MARKDOWN)
//                    .build();
//        }

    }


    public InlineKeyboardMarkup sectionSelect(TelegramUser user){

        List<Section> sections=new ArrayList<>();
        if (user.getSectionType().equals(SectionType.FIRST_TYPE)){
            sections.add(Section.CONST_1);
            sections.add(Section.CONST_2);
            sections.add(Section.CONST_3);
            sections.add(Section.CONST_4);
            sections.add(Section.CONST_5);
            sections.add(Section.CONST_6);



//
//            sections.add(Section.FIRST);
//            sections.add(Section.SECOND);
//            sections.add(Section.THIRD);
//            sections.add(Section.FOURTH);
//            sections.add(Section.FIFTH);
//            sections.add(Section.SIXTH);
        } else {
            if (user.getSectionType().equals(SectionType.SECOND_TYPE)){
                sections.add(Section.CHANGEABLE_1);
                sections.add(Section.CHANGEABLE_2);
                sections.add(Section.CHANGEABLE_3);
                sections.add(Section.CHANGEABLE_4);
                sections.add(Section.CHANGEABLE_5);
                sections.add(Section.CHANGEABLE_6);
                sections.add(Section.CHANGEABLE_7);
                sections.add(Section.CHANGEABLE_8);
                sections.add(Section.CHANGEABLE_9);
                sections.add(Section.CHANGEABLE_10);
                sections.add(Section.CHANGEABLE_11);
                sections.add(Section.CHANGEABLE_12);
                sections.add(Section.CHANGEABLE_13);
                sections.add(Section.CHANGEABLE_14);
                sections.add(Section.CHANGEABLE_15);
                sections.add(Section.CHANGEABLE_16);
                sections.add(Section.CHANGEABLE_17);
            }
            else {
                if (checkPermission(user)){
                    sections.add(Section.COMING_1);
                    sections.add(Section.COMING_2);
                } else return null;

            }






//            sections.add(Section.SEVENTH);
//            sections.add(Section.EIGHTH);
//            sections.add(Section.NINTH);
//            sections.add(Section.TENTH);
//            sections.add(Section.ELEVENTH);
        }
        if (user.getSection()!=null){
            requestService.sectionReset(user);
            Col col=new Col();
            Section section=user.getSection();
            Request request = requestService.getLast(user);
            List<Item> items = itemService.allByRequest(request);
            col.add("➕ "+Translations.ADD.getRu(user),section.name());
            itemer(col, section, items);
            if (items.size()>0) col.add(Translations.SEND_REQUEST.getRu(user),Translations.SEND_REQUEST.name());

            col.add(Translations.BACK.getRu(user),Translations.BACK.name());
            return col.getMarkup();
        }

        Request request = requestService.getLast(user);
        Col col=new Col();
        if (request!=null){
            List<Item> items = itemService.allByRequest(request);
            for (Section section : sections) {
                col.add(section.getRu(user),section.name());
                itemer(col, section, items);
            }
            if (items.size()>0) col.add(Translations.SEND_REQUEST.getRu(user),Translations.SEND_REQUEST.name());

            col.add(Translations.BACK.getRu(user),Translations.BACK.name());
            return col.getMarkup();
        }else {
            requestService.reset(user);

            for (Section section : sections) {
                col.add(section.getRu(user),section.name());
            }
            col.add(Translations.BACK.getRu(user),Translations.BACK.name());
            return col.getMarkup();
        }
    }

    private void itemer(Col col, Section section, List<Item> items) {
        for (Item item : items) {
            if (item.getSection().equals(section)){
                Row row=new Row();
                System.out.println(item);
                if (item.getAmount()!=0) row.add(String.valueOf(item.getAmount()));
                if (item.getAmountUsd()!=0) row.add("$"+item.getAmountUsd());
                if (item.getDescription()!=null&& !Objects.equals(item.getDescription(), ""))
                    row.add(item.getDescription());
                row.add(Translations.DELETE_ITEM.getRu(),Translations.DELETE_ITEM.name()+":"+item.getId());
                col.add(row);
            }
        }
    }


    //Mahsulot qo'shishni so'rash
    public SendMessage addItem(TelegramUser user,String text){
        Col col=new Col();
        col.add(Translations.BACK.getRu(user),Translations.BACK.name());

        return SendMessage.builder()
                .chatId(user.getId())
                .text(text)
                .replyMarkup(col.getMarkup())
                .parseMode(ParseMode.HTML)
                .build();
    }
    public SendMessage addItem(TelegramUser user){
//        if (user.getSection().equals(Section.EIGHTH)||user.getSection().equals(Section.NINTH)||user.getSection().equals(Section.TENTH)){
//            return addItem(user,"<i>"+user.getSectionType().getRu(user)+"</i>\n\n"+"<b>"+user.getSection().getRu(user) +"</b>\n"+Translations.TXT_PRODUCT_ANOTHER.getRu(user));
//        }
        boolean b = checkPermission(user);
        if (b){
            return addItem(user,"<i>"+user.getSectionType().getRu(user)+"</i>\n\n"+"<b>"+user.getSection().getRu(user) +"</b>\n"+Translations.TXT_PRODUCT.getRu(user));
        } else {
            if (user.getSectionType().equals(SectionType.THIRD_TYPE))
                return mainMenu(user);
            return addItem(user,"<i>"+user.getSectionType().getRu(user)+"</i>\n\n"+"<b>"+user.getSection().getRu(user) +"</b>\n"+Translations.TXT_PRODUCT.getRu(user));


        }
    }

    public SendMessage confirmRequestMessage(TelegramUser user){
        Request request = requestService.get(user);
        List<Item> items = itemService.allByRequest(request);
        String text="";
        String itemList="";
        int counter=0;
        if (items.size()>0){
            text+=Translations.TXT.getRu(user);
            for (Section section : Section.values()) {
                itemList="";
                counter=0;
                for (Item item : items) {
                    if (item.getSection().equals(section)){
                        counter++;
                        if (item.getAmount()!=0 && item.getAmountUsd()!=0){
                            itemList+="<b>"+counter+" - </b>"+item.getAmount()+" сум \uD83D\uDD8A "+item.getAmountUsd()+"$ \uD83D\uDD8A "+item.getDescription()+"\n";
                        } else itemList+="<b>"+counter+" - </b>"+(item.getAmount()!=0?item.getAmount():"$"+item.getAmountUsd())+" \uD83D\uDD8A "+item.getDescription()+"\n";
                    }
                }
                if (itemList.length()>1) text+="\n✳ <b>"+section.getRu(user)+"</b> : \n"+itemList;
            }
            text+=Translations.TXT_REQUEST_QUESTION.getRu(user);
        }
        Col col=new Col();
        Row row=new Row();
        row.add(Translations.CONFIRM_REQUEST.getRu(user),Translations.CONFIRM_REQUEST.name());
        row.add(Translations.CANCEL_REQUEST.getRu(user),Translations.CANCEL_REQUEST.name());
        col.add(row);
        col.add(Translations.BACK_SECTION.getRu(user),Translations.BACK_SECTION.name());
        return SendMessage.builder()
                .chatId(user.getId())
                .text(text)
                .replyMarkup(col.getMarkup())
                .parseMode(ParseMode.HTML)
                .build();
    }

    public SendMessage confirm(TelegramUser user) {
        return SendMessage.builder().chatId(user.getId()).text(Translations.TXT_SEND_REQUEST.getRu(user)).build();
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


    public boolean checkPermission(TelegramUser user){
        for (String id : permissionList) {
            if (Objects.equals(user.getId(), Long.parseLong(id))) return true;
        }
        return false;
    }

}
