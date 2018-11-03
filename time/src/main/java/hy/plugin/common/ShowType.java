package hy.plugin.common;


import java.text.SimpleDateFormat;

public enum ShowType {


    SYS_DF("yyyy-MM-dd HH:mm:ss"),
    HOUR_MIN("HH:mm"),
    MONTH_DAY("MM-dd"),
    YEAR("yyyy-MM-dd HH:mm"),
    YEAR_DAY("yyyy-MM-dd"),
    YEAR_MONTH("yyyy-MM"),
    D_YEAR_DAY("yyyy.MM.dd"),
    N_SYS_DF("yyyyMMddHHmmss"),
    ND_YEAR_DAY ("yyMMdd")
    ;

    private String format;
    private SimpleDateFormat dateFormat;
    ShowType(String  format){
        this.format =format;
        this.dateFormat = new SimpleDateFormat(format);
    }

    public SimpleDateFormat get(){
        return this.dateFormat;
    }

    public static ShowType getShowType(String format){

        for (ShowType sub:ShowType.values()){
            if(sub.format.equals(format)){
                return sub;
            }
        }

        return null;
    }
}
