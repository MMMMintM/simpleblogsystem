package cn.mydemo.simpleblogsystem.controller;


import cn.mydemo.simpleblogsystem.bean.Article;
import cn.mydemo.simpleblogsystem.http.HttpResult;
import cn.mydemo.simpleblogsystem.service.ArticleService;
import cn.mydemo.simpleblogsystem.util.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    public HttpResult createArticle(@RequestParam String title,@RequestParam String content,@RequestParam int userId){

        articleService.createArticle(title,content,userId);

        return HttpResult.ok("创建成功");
    }

    @GetMapping
    public HttpResult getAllArticleByUserId(@RequestParam int uid){
        System.out.println(uid);

        List<Article> articles = articleService.getAllArticleByUserId(uid);

        return HttpResult.ok(articles);
    }

    @GetMapping("/{id}")
    public HttpResult getArticleByPostId(@PathVariable int id){

        Article article = articleService.getArticleByPostId(id);

        System.out.println(article.getTitle());

        return HttpResult.ok(article);


    }

    @PutMapping("/{id}")
    public HttpResult updateArticle(@PathVariable int id, @RequestParam String title, @RequestParam String content, HttpServletRequest request){

        String token = request.getHeader("token");

        String username = JwtTokenUtils.getUsernameFromToken(token);

        if(username.equals(articleService.getUsernameByPostId(id))){
            Article article = articleService.getArticleByPostId(id);

            if (title.length() != 0 && !(title.equals(article.getTitle()))){
                article.setTitle(title);
            }

            if(content.length() != 0 && !(content.equals(article.getContent()))){
                article.setContent(content);
            }

            articleService.updateArticle(article);

            return HttpResult.ok("修改成功");
        }

        return HttpResult.error("权限不足");
    }

    @DeleteMapping("/{id}")
    public HttpResult deleteArticle(@PathVariable int id,HttpServletRequest request){
        String token = request.getHeader("token");

        String username = JwtTokenUtils.getUsernameFromToken(token);

        if(username.equals(articleService.getUsernameByPostId(id))){

            articleService.deleteArticle(id);

            return HttpResult.ok("删除成功");
        }

        return HttpResult.error("权限不足");
    }
}
