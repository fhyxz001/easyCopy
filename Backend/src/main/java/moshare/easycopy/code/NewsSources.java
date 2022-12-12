package moshare.easycopy.code;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum NewsSources {
    HOTACG("0",
            "hotacg",
            "热点ACG",
            "0",
            "entry-title",
            "entry-date",
            "entry-content",
            "yyyyMMd HH:mm,yyyyMdd HH:mm,yyyyMd HH:mm",
            7),
    _3DMGAME("1",
            "3dmgame",
            "三大妈",
            "0",
            "bt",
            "time",
            "news_warp_center",
            null,
            6),
    VGTIME("2",
            "vgtime",
            "游戏时光",
            "0",
            "art_tit",
            "time_box",
            "topicContent front_content",
            null,
            13),
    ALI213("3",
            "ali213",
            "游侠资讯",
            "0",
            "newstit",
            "newstag_l",
            "n_show box-shadow",
            null,
            15),
    DMZJ("4",
            "dmzj",
            "动漫之家",
            "0",
            "news_content_head li_img_de autoHeight",
            "data_time",
            "news_content_con",
            null,
            8),
    GAO7("99",
            "gao7",
            "搞趣网",
            "0",
            "article-title",
            "article-meta",
            "area-gao7-article",
            null,
            4);
    //编号序号
    private String code;
    //网站名称
    private String name;
    //网站中文名
    private String cnName;
    //网站类型
    private String type;
    //标题所在的标签
    private String titleSelector;
    //时间所在的标签
    private String dateSelector;
    //内容所在的标签
    private String contentSelector;
    //时间格式化格式，null的话为标准格式
    private String dateFormat;
    //需要删除的后缀字符串长度
    private int suffixLength;


    NewsSources(String code,
                String name,
                String chName,
                String type,
                String titleSelector,
                String dateSelector,
                String contentSelector,
                String dateFormat,
                int suffixLength) {
        this.code = code;
        this.name = name;
        this.cnName = chName;
        this.type = type;
        this.titleSelector = titleSelector;
        this.dateSelector = dateSelector;
        this.contentSelector = contentSelector;
        this.dateFormat = dateFormat;
        this.suffixLength = suffixLength;
    }

    public static NewsSources getNewsSourcesByCode(String code) {
        for (NewsSources newsSources : NewsSources.values()) {
            if (newsSources.getCode().equals(code)) {
                return newsSources;
            }
        }
        return null;
    }
    //getCodeByName
    public static NewsSources getSiteTypeByName(String name) {
        for (NewsSources siteType : NewsSources.values()) {
            if (siteType.getName().equals(name)) {
                return siteType;
            }
        }
        return null;
    }
    //返回一个Name的List
    public static List<String> getNameList(){
        List<String> nameList = new ArrayList<>();
        for (NewsSources siteType : NewsSources.values()) {
            nameList.add(siteType.getName());
        }
        return nameList;
    }
}
