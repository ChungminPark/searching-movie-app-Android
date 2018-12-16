package com.example.chuck.movie_app.data;

/*
    "title": "<b>고양이</b> 여행 리포트",
    "link": "https://movie.naver.com/movie/bi/mi/basic.nhn?code=174321",
    "image": "https://ssl.pstatic.net/imgmovie/mdi/mit110/1743/174321_P01_174128.jpg",
    "subtitle": "The Travelling Cat Chronicles",
    "pubDate": "2018",
    "director": "미키 코이치로|",
    "actor": "후쿠시 소우타|타카하타 미츠키|다케우치 유코|",
    "userRating": "10.00"
 */

public class MovieItem {

    String title;
    String link;
    String image;
    String subtitle;
    String pubDate;
    String director;
    String actor;
    float userRating;

    public MovieItem(String title, String link, String image, String subtitle, String pubDate,
                     String director, String actor, float userRating) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.subtitle = subtitle;
        this.pubDate = pubDate;
        this.director = director;
        this.actor = actor;
        this.userRating = userRating;
    }

    public MovieItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
}
