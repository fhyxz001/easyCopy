package moshare.easycopy.service;

import moshare.easycopy.entity.News;

public interface EasyCopyService {
    News getNewsByUrl(String url,String siteType,String comments);

}
