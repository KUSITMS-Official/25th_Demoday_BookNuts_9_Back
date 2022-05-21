package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.SeriesRequest;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.dto.Response.SeriesResponse;
import team.nine.booknutsbackend.exception.board.NoAccessException;
import team.nine.booknutsbackend.service.SeriesService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.HashMap;
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
    public List<SeriesResponse> allMySeries(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return seriesService.allMySeries(user);
    }

    //시리즈 발행
    @PostMapping("/groupingseries")
    public ResponseEntity<Object> grouping(@RequestBody SeriesRequest series, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        seriesService.saveSeries(series, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "그룹핑 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //특정 시리즈 조회
    @GetMapping("/{seriesId}")
    public List<BoardResponse> findMySeries(@PathVariable Long seriesId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return seriesService.findSeries(seriesId, user);
    }

    //시리즈 삭제
    @DeleteMapping("/{seriesId}")
    public ResponseEntity<Object> delete(@PathVariable Long seriesId, Principal principal) throws NoAccessException {
        User user = userService.loadUserByUsername(principal.getName());
        seriesService.delete(seriesId, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "시리즈 삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //시리즈에 게시글 추가
    @GetMapping("/addseries/{seriesId}/{boardId}")
    public ResponseEntity<Object> addToSeries(@PathVariable Long seriesId, @PathVariable Long boardId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        seriesService.addToSeries(seriesId, boardId);

        Map<String, String> map = new HashMap<>();
        map.put("result", "시리즈에 추가 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
