package hack.foodit.domain.post.controller;

import hack.foodit.domain.post.entity.dto.PostRequestDTO;
import hack.foodit.domain.post.entity.dto.PostResponseDTO;
import hack.foodit.domain.post.entity.dto.ToggleRequestDto;
import hack.foodit.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    @GetMapping
    public ResponseEntity<?> getPostAll(@RequestParam(required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("likeCount").descending());
        try{
            List<PostResponseDTO> res = postService.findAll(pageable);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> createPost(
        @RequestPart PostRequestDTO reqDto,
        @RequestPart("files") List<MultipartFile> multipartFileList){

        try{
            postService.createPost(reqDto, multipartFileList);
            return new ResponseEntity<>("게시물이 정상적으로 작성되었습니다.", HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable("postId") Long id){
        try{
            PostResponseDTO res = postService.findPostById(id);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/popularity")
    public ResponseEntity<?> getPostSortByPopularity(@RequestParam(required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("likeCount").descending());
        try{
            List<PostResponseDTO> res = postService.findAll(pageable);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/new")
    public ResponseEntity<?> getPostSortByNew(
        @RequestParam(required=false, defaultValue = "0")int page){

        PageRequest pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        try{
            List<PostResponseDTO> res = postService.findAll(pageable);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable("postId") Long id, @RequestBody PostRequestDTO req){
        try{
            postService.updatePost(id, req);
            return new ResponseEntity<>("게시물이 정상적으로 수정되었습니다.",HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId") Long id){
        try{
            postService.deletePost(id);
            return new ResponseEntity<>("게시물이 정상적으로 삭제되었습니다.", HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byCategory")
    public ResponseEntity<?> getBusinessByCategory(@RequestParam("category") List<Integer> categoryList, @RequestParam(required=false, defaultValue = "0")int page){
        PageRequest pageable = PageRequest.of(page, 10);
        try{
            List<PostResponseDTO> res = postService.findPostByCategory(categoryList, pageable);
            return ResponseEntity.ok(res);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeToggle(@RequestBody ToggleRequestDto requestDto) {
        return new ResponseEntity<>(postService.likeToggle(requestDto), HttpStatus.OK);
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unlikeToggle(@RequestBody ToggleRequestDto requestDto) {
        return new ResponseEntity<>(postService.unlikeToggle(requestDto), HttpStatus.OK);
    }
}
