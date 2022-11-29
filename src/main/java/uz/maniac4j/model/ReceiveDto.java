package uz.maniac4j.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiveDto {
    public long amount;
    public long amountUsd;
    public String noteOrNumber;

    public static ReceiveDto receive(String[] strings){
        ReceiveDto dto=new ReceiveDto();
        if (strings.length==0) return null;
        String t1=strings[0].replace("$","").replace(" ","");
        if (strings.length==1){
            if (usdCheck(strings[0].replace(" ",""))){
                dto.amountUsd=Long.parseLong(t1);
            }else {
                if (numberCheck(t1)){
                    dto.amount=Long.parseLong(t1);
                    return dto;
                }
                else return null;
            }
        }

        String t2=strings[1].replace("$","").replace(" ","");
        if (strings.length==2){

            if (
                    numberCheck(t1)
                    &&
                            numberCheck(t2)
            ){
                if (usdCheck(strings[0])){
                    dto.amountUsd=Long.parseLong(t1);
                    dto.amount=Long.parseLong(t2);
                }else {
                    if (usdCheck(strings[1])){
                        dto.amountUsd=Long.parseLong(t2);
                        dto.amount=Long.parseLong(t1);
                    }
                }
            } else {
                if (numberCheck(t1)){
                    if (usdCheck(strings[0])){
                        dto.amountUsd=Long.parseLong(t1);
                    }else {
                        dto.amount=Long.parseLong(t1);
                    }
                    dto.noteOrNumber=strings[1];
                } else return null;
            }

        }

        if (strings.length>=3){
            if (
                    numberCheck(t1)
                            &&
                            numberCheck(t2)
            ){
                if (usdCheck(strings[0])){
                    dto.amountUsd=Long.parseLong(t1);
                    dto.amount=Long.parseLong(t2);
                    dto.noteOrNumber=strings[2];
                }else {
                    if (usdCheck(strings[1])){
                        dto.amountUsd=Long.parseLong(t2);
                        dto.amount=Long.parseLong(t1);
                        dto.noteOrNumber=strings[2];
                    }
                }
            } else {
                return null;
            }
        }


        return dto;
    }

    public static boolean usdCheck(String str){
        return str.replace(" ","").startsWith("$");
    }

    public static boolean numberCheck(String str){
        try {
            Long.parseLong(str);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
