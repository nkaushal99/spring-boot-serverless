package com.nikhil.post;

import java.util.List;
import org.springframework.web.service.annotation.GetExchange;

public interface JsonPlaceholderService {

  @GetExchange("/posts")
  List<Post> loadPosts();
}
