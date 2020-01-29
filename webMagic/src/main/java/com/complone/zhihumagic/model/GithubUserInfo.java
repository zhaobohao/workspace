package com.complone.zhihumagic.model;

import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by complone on 2018/11/2.
 */
@TargetUrl("https://github.com/\\w+/\\w+")
@HelpUrl("https://github.com/\\w+")
public class GithubUserInfo {


    @Id
    private Integer githubId;

    @ExtractBy(value = "//h1[@class='vcard-names']/span[2]/text()")
    private String nickname;

    @ExtractBy(value = "//h1[@class='vcard-names']/span[2]/text()")
    private String author;


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGithubId(Integer githubId) {
        this.githubId = githubId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGithubId() {
        return githubId;
    }

    public String getAuthor() {
        return author;
    }

    public String getNickname() {
        return nickname;
    }
}
