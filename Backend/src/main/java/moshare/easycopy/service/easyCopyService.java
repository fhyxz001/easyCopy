package moshare.easycopy.service;

import moshare.easycopy.entity.News;
import moshare.easycopy.entity.TitleUrl;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public interface easyCopyService {
    News getNewsByUrl(String url,String siteType,String comments);

}
