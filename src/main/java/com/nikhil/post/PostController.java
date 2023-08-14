package com.nikhil.post;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

  private final JsonPlaceholderService jsonPlaceholderService;

  private List<Post> posts = new ArrayList<>();

  @GetMapping
  List<Post> findAll() {
    return posts;
  }

  @GetMapping("/{id}")
  Optional<Post> findById(@PathVariable Integer id) {
    return Optional.ofNullable(posts
        .stream()
        .filter(post -> post.id().equals(id))
        .findFirst()
        .orElseThrow(() -> new PostNotFoundException("Post with id: " + id + " not found.")));
  }

  @PostMapping
  void create(@RequestBody Post post) {
    posts.add(post);
  }

  @PutMapping("/{id}")
  void update(@RequestBody Post post, @PathVariable Integer id) {
    posts.stream()
        .filter(p -> p.id().equals(id))
        .findFirst()
        .ifPresent(value -> posts.set(posts.indexOf(value), post));
  }

  @DeleteMapping("/{id}")
  void delete(@PathVariable Integer id) {
    posts.removeIf(post -> post.id().equals(id));
  }

  @PostConstruct
  private void init() {
    if (posts.isEmpty()) {
      log.info("Loading Posts using JsonPlaceHolderService");
      posts = jsonPlaceholderService.loadPosts();
    }
  }

}