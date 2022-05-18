package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.MyStory;
import team.nine.booknutsbackend.domain.myStory.MyStoryBoard;
import team.nine.booknutsbackend.dto.MyStoryDto;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.exception.mystory.MyStoryNotFoundException;
import team.nine.booknutsbackend.repository.MyStoryRepository;
import team.nine.booknutsbackend.service.BoardService;
import team.nine.booknutsbackend.service.MyStoryService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mystory")
public class MyStoryController {

    private final MyStoryService myStoryService;
    private final UserService userService;
    private final BoardService boardService;

    //전체 스토리 조회
    @GetMapping("/all")
    public List<MyStoryDto> allMyStory (Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return myStoryService.allMyStory(user);
    }

    //스토리 그룹핑
    @PostMapping("/groupstory")
    public String write(@RequestBody Map<String, String> board, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        MyStory myStory = new MyStory();
        //myStory.
        //String boardIdlist=board.get("boardlist");
        //long[] boardlist= Arrays.stream(boardIdlist.split(",")).mapToLong(Long::parseLong).toArray();

        //myStoryService.grouping(boardlist);
        return "redirect/mystory/all";
    }

    //특정 스토리 조회
    @GetMapping("/{mystoryId}")
    public List<BoardResponse> findmystory(@PathVariable Long mystoryId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        List<MyStoryBoard> myStoryBoards=myStoryService.find(mystoryId);
        List<BoardResponse> mystoryboardlist = new ArrayList<>();

        for(MyStoryBoard myStoryBoard:myStoryBoards){
            Long boardId=myStoryBoard.getBoard().getBoardId();
            Board board=boardService.find(boardId);
            mystoryboardlist.add(BoardResponse.boardResponse(board, user));
        }

        return mystoryboardlist;
    }

}
