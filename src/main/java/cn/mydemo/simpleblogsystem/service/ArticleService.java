package cn.mydemo.simpleblogsystem.service;

import cn.mydemo.simpleblogsystem.bean.Article;

import java.util.List;

public interface ArticleService {


    void createArticle(String title,String content,int userId);

    List<Article> getAllArticleByUserId(int userId);

    Article getArticleByPostId(int postId);

    String getUsernameByPostId(int postId);

    void updateArticle(Article article);

    void deleteArticle(int postId);


}
