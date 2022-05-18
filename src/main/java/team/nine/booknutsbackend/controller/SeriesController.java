package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.Series;
import team.nine.booknutsbackend.domain.myStory.SeriesBoard;
import team.nine.booknutsbackend.dto.Request.SeriesRequest;
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
        return seriesService.allMySeries(user);
    }

    //시리즈 그룹핑
    @PostMapping("/groupingseries")
    public String grouping(@RequestBody SeriesRequest series, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        seriesService.saveSeries(series,user);
        //리턴 값을 뭘로? redirect:/list?? or redirect:/1 ??
        return null;
    }

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
