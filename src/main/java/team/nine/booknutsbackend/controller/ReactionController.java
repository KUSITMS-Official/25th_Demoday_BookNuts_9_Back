package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.service.BoardService;
import team.nine.booknutsbackend.service.ReactionService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reaction")
public class ReactionController {

    private final UserService userService;
    private final ReactionService reactionService;
    private final BoardService boardService;

    @PutMapping("/nuts/{boardId}")
    public ResponseEntity<Object> clickNuts(@PathVariable Long boardId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        String result = reactionService.clickNuts(boardId, user);
        boardService.updateCount(boardService.findBoard(boardId));

        Map<String, String> map = new HashMap<>();
        map.put("result", result);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("/heart/{boardId}")
    public ResponseEntity<Object> clickHeart(@PathVariable Long boardId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        String result = reactionService.clickHeart(boardId, user);
        boardService.updateCount(boardService.findBoard(boardId));

        Map<String, String> map = new HashMap<>();
        map.put("result", result);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}