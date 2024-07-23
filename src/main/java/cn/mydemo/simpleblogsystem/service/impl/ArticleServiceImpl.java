package cn.mydemo.simpleblogsystem.service.impl;

import cn.mydemo.simpleblogsystem.bean.Article;
import cn.mydemo.simpleblogsystem.mapper.ArticleMapper;
import cn.mydemo.simpleblogsystem.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void createArticle(String title, String content, int userId) {

        articleMapper.createArticle(title,content,userId);

    }

    @Override
    public List<Article> getAllArticleByUserId(int userId) {

        List<Article> articles = articleMapper.getAllArticleByUuserId(userId);

        return articles;
    }

    @Override
    public Article getArticleByPostId(int postId) {

        return articleMapper.getArticleByPostId(postId);
    }

    @Override
    public String getUsernameByPostId(int postId) {

        String username = articleMapper.getUsernameByPostId(postId);

        return username;
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(int postId) {
        articleMapper.deleteArticle(postId);
    }
}
