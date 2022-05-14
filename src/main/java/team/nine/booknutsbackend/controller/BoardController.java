package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.service.BoardService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    //게시글 작성
    @PostMapping("/write")
    public ResponseEntity<Object> write(@RequestBody Map<String, String> board, Principal principal) {

        User user = userService.loadUserByUsername(principal.getName());

        Board newBoard = new Board();
        newBoard.setTitle(board.get("title"));
        newBoard.setContent(board.get("content"));
        newBoard.setBookTitle(board.get("bookTitle"));
        newBoard.setBookImgUrl(board.get("bookImgUrl"));
        newBoard.setBookGenre(board.get("bookGenre"));
        newBoard.setUser(user);

        Board saveBoard = boardService.newPost(newBoard);
        return new ResponseEntity<>(saveBoard, HttpStatus.CREATED);
    }
}
