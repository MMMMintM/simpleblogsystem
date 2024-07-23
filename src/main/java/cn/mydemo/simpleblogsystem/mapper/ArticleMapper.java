package cn.mydemo.simpleblogsystem.mapper;


import cn.mydemo.simpleblogsystem.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {

    void createArticle(@Param("title") String title,@Param("content") String content,@Param("userId") int userId);

    List<Article> getAllArticleByUuserId(@Param("user_id") int userId);

    Article getArticleByPostId(@Param("post_id") int postId);

    String getUsernameByPostId(@Param("post_id") int postId);

    int updateArticle(Article article);

    int deleteArticle(@Param("post_id") int postId);

}
