package com.springbooot.blog.controller;

import com.springbooot.blog.payload.PostDto;
import com.springbooot.blog.payload.PostDtoV2;
import com.springbooot.blog.payload.PostResponse;
import com.springbooot.blog.service.PostService;
import com.springbooot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    //get all post rest api
    @GetMapping("/v1/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

    ){

        return postService.getAllPosts(pageNo,pageSize, sortBy, sortDir);
    }

    // get post by id
    @GetMapping("/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/v2/posts/{id}")
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") Long id){
        PostDto postDto = postService.getPostById(id);
        PostDtoV2 postDtoV2 =  new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setComments(postDto.getComments());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setTitle(postDto.getTitle());
        List <String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring boot");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // update post by id rest api
    @PutMapping("/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Long id){
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    // delete post rest api
    @DeleteMapping("/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
    }
}