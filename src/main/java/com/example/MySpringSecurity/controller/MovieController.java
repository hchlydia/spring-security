package com.example.MySpringSecurity.controller;

import com.example.MySpringSecurity.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String getMovies() {
        return "取得電影列表";
    }

    @PostMapping("/free")
    public String watchFreeMovies() {
        return "觀看免費電影";
    }

    @PostMapping("/vip")
    public String watchVipMovies() {
        return "觀看付費電影";
    }

    @PostMapping("/upload")
    public String uploadMovies() {
        return "上傳電影";
    }

    @DeleteMapping("/delete")
    public String deleteMovies() {
        return "刪除電影";
    }
}
