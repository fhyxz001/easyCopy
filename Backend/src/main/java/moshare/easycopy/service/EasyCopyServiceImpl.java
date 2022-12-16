package moshare.easycopy.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import moshare.easycopy.code.NewsSources;
import moshare.easycopy.entity.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class EasyCopyServiceImpl implements EasyCopyService {
    String date = "";
    String title = "";
    String newTitle = "";
    String content = "";

    @Override
    public News getNewsByUrl(String url, String siteType, String comments) {
        Document doc = this.getDocument(url);
        News news = new News();
        String code = this.SiteCheck(url);
        if(code!=null){
            news.setSiteType(code);
        }
        NewsSources newsSources = NewsSources.getNewsSourcesByCode(news.getSiteType());
        switch (siteType) {
            case "0":
                moeShareHandle(news, url, doc, newsSources);
                break;
            case "1":
                sayhuahuoHandle(news, url, doc, newsSources);
                break;
            default:
                throw new IllegalArgumentException("网站类型不合法");
        }
        return news;
    }


    private String SiteCheck(String url) {
        List<String> nameList = NewsSources.getNameList();
        //遍历nameList,如果url包含nameList中的某个字符串，则调用getSiteTypeByCode方法，将SiteType的code传入news中
        for (int i = 0; i < nameList.size(); i++) {
            if (url.contains(nameList.get(i))) {
                return NewsSources.getSiteTypeByName(nameList.get(i)).getCode();
            }
        }
        return null;
    }

    private Document getDocument(String url) {
        //正则检查url是否合法，不合法则抛出异常
        if (!url.matches("^(http|https)://.*")) {
            throw new IllegalArgumentException("url不合法");
        }
        //http访问url，获取html
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            throw new IllegalArgumentException("http访问url，获取html失败");
        }
        return doc;
    }



    private void sayhuahuoHandle(News news, String url, Document doc, NewsSources newsSources) {
        Elements allElements = doc.body().getAllElements();
        boolean firstDate = true;
        for (int i = 0; i < allElements.size(); i++) {
            if (allElements.get(i).className().equals(newsSources.getContentSelector())) {
                content = this.getContent(allElements, i);
            }
            if (allElements.get(i).className().equals(newsSources.getDateSelector()) && firstDate) {
                DateTime parse = this.getDateTime(newsSources, allElements, i);

                Calendar ca = Calendar.getInstance();
                ca.setTime(parse);
                int year = ca.get(Calendar.YEAR);
                int month = ca.get(Calendar.MONTH) + 1;
                int day = ca.get(Calendar.DATE);

                //检查月和日是否为一位数，如果是，则在前面补0
                String monthStr = month < 10 ? "0" + month : String.valueOf(month);
                String dayStr = day < 10 ? "0" + day : String.valueOf(day);
                date = year + "年" + monthStr + "月" + dayStr + "日";
                firstDate = false;
            }
        }

        newTitle = this.getTitle(doc, newsSources);
        //在content的开头添加url，作为文章的来源
        content = "[b]文章来源：[/b]" + url + " " + "\n" + "[b]新闻发布时间:[/b]" + date + "\n" + content;
        //保留content中的p标签和img标签
        content = content.replaceAll("<(?!p|img|/p|/img).*?>", "");
        //将img标签的src的值替换成以[img]开头，以[/img]结尾的字符串
        content = content.replaceAll("<img.*?src=\"(.*?)\".*?>", "[img]$1[/img]");
        //删除其中所有除了[img]和[/img]的标签
        content = content.replaceAll("<.*?>", "");

        news.setTitle(newTitle);
        news.setContent(content);
    }


    private void moeShareHandle(News news,
                                String url,
                                Document doc,
                                NewsSources newsSources
    ) {
        Elements allElements = doc.body().getAllElements();
        //遍历allElements，找到其中有<div class="entry-content">的子元素，保留文本的格式，并且将其中的img标签的数据替换成以[img]开头，以[/img]结尾的字符串
        //找到其中有entry-date的第一条数据，将其格式转换为[yyMMdd],赋予给data字符串
        //找到其中有entry-title的参数，将其赋予给title字符串
        //拼接data和title，赋予给newTitle字符串
        boolean firstDate = true;
        for (int i = 0; i < allElements.size(); i++) {
            if (allElements.get(i).className().equals(newsSources.getContentSelector())) {
                content = this.getContent(allElements, i);
            }
            if (allElements.get(i).className().equals(newsSources.getDateSelector()) && firstDate) {
                DateTime parse = this.getDateTime(newsSources, allElements, i);

                Calendar ca = Calendar.getInstance();
                ca.setTime(parse);
                int year = ca.get(Calendar.YEAR);
                int month = ca.get(Calendar.MONTH) + 1;
                int day = ca.get(Calendar.DATE);

                //检查月和日是否为一位数，如果是，则在前面补0
                String monthStr = month < 10 ? "0" + month : String.valueOf(month);
                String dayStr = day < 10 ? "0" + day : String.valueOf(day);
                date = year + monthStr + dayStr;
                //删除date前两个字符
                date = date.substring(2);
                //用[]包裹
                date = "[" + date + "]";
                firstDate = false;
            }
        }
        title = this.getTitle(doc, newsSources);
        newTitle = date + title;
        //在content的开头添加url，作为文章的来源
        content = "[b]文章来源：[/b]" + url + " " + "\n" + content;
        //保留content中的p标签和img标签
        content = content.replaceAll("<(?!p|img|/p|/img).*?>", "");
        //将img标签的src的值替换成以[img]开头，以[/img]结尾的字符串
        content = content.replaceAll("<img.*?src=\"(.*?)\".*?>", "[img]$1[/img]");
        //删除其中所有除了[img]和[/img]的标签
        content = content.replaceAll("<.*?>", "");
        news.setTitle(newTitle);
        news.setContent(content);
    }

    private String getTitle(Document doc, NewsSources newsSources) {
        String title;
        title = doc.title();
        if (title.length() > 0) {
            int suffixLength = newsSources.getSuffixLength();
            //删除title最后面的suffixLength个字符
            title = title.substring(0, title.length() - suffixLength);
        }
        return title;
    }

    private DateTime getDateTime(NewsSources newsSources, Elements allElements, int i) {
        String date;
        date = allElements.get(i).text();
        //去除date中的所有汉字
        date = date.replaceAll("[\u4e00-\u9fa5]", "");
        //去除第一个字符，直到第一个字符为数字2
        while (!date.substring(0, 1).equals("2")) {
            date = date.substring(1);
        }
        //去除最后一个字符，直到最后一个字符为数字
        while (!date.substring(date.length() - 1).matches("[0-9]")) {
            date = date.substring(0, date.length() - 1);
        }
        DateTime parse = null;
        //使用hutool工具类，将date转换为Date类型
        if (Objects.isNull(newsSources.getDateFormat())) {
            parse = DateUtil.parse(date);
        } else {
            String dateFormat = newsSources.getDateFormat();
            //将dateFormat以逗号分割，得到一个数组
            String[] split = dateFormat.split(",");
            for (String s : split) {
                try {
                    parse = DateUtil.parse(date, s);
                } catch (Exception e) {
                    continue;
                }
                break;
            }
        }
        return parse;
    }

    private String getContent(Elements allElements, int i) {
        String content;
        content = allElements.get(i).toString();
        //取出img标签的src数据
        if (content.contains("<img")) {
            int imgStartIndex = content.indexOf("<img");
            int imgEndIndex = content.indexOf(">", imgStartIndex);
            String imgSrc = content.substring(imgStartIndex, imgEndIndex);
            imgSrc = imgSrc.substring(imgSrc.indexOf("src=") + 5, imgSrc.indexOf("\"", imgSrc.indexOf("src=") + 5));
            content = content.replace(content.substring(imgStartIndex, imgEndIndex), "[img]" + imgSrc + "[/img]");
        }
        // 将]>替换成]
        content = content.replace("]>", "]");
        return content;
    }



}
