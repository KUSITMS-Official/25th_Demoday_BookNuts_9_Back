package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.Series;
import team.nine.booknutsbackend.domain.myStory.SeriesBoard;
import team.nine.booknutsbackend.dto.Response.SeriesResponse;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.service.BoardService;
import team.nine.booknutsbackend.service.SeriesService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;
    private final UserService userService;

    //내 시리즈 조회
    @GetMapping("/list")
    public List<SeriesResponse> allMySeries (Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        System.out.println(user.getLoginId());
        return seriesService.allMySeries(user);
    }

    //시리즈 그룹핑
//    @PostMapping("/groupseries")
//    public String grouping(@RequestBody Map<String, String> postlist, Principal principal) {
//        User user = userService.loadUserByUsername(principal.getName());
//        Series series = new Series();
//        //myStory.
//        //String boardIdlist=board.get("boardlist");
//        //long[] boardlist= Arrays.stream(boardIdlist.split(",")).mapToLong(Long::parseLong).toArray();
//
//        //myStoryService.grouping(boardlist);
//        return "redirect/mystory/all";
//    }

    //특정 시리즈 조회
//    @GetMapping("/{mystoryId}")
//    public List<BoardResponse> findmystory(@PathVariable Long mystoryId, Principal principal) {
//        User user = userService.loadUserByUsername(principal.getName());
//        List<SeriesBoard> seriesBoards = seriesService.find(mystoryId);
//        List<BoardResponse> mystoryboardlist = new ArrayList<>();
//
//        for(SeriesBoard seriesBoard : seriesBoards){
//            Long boardId= seriesBoard.getBoard().getBoardId();
//            //Board board=boardService.find(boardId);
//            //mystoryboardlist.add(BoardResponse.boardResponse(board, user));
//        }
//
//        return mystoryboardlist;
//    }

}
