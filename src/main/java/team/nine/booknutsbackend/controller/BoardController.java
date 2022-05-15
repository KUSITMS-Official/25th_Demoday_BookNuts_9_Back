package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.BoardDto;
import team.nine.booknutsbackend.exception.board.NoAccessException;
import team.nine.booknutsbackend.service.BoardService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    //게시글 작성
    @PostMapping("/write")
    public ResponseEntity<BoardDto> write(@RequestBody Map<String, String> board, Principal principal) {

        User user = userService.loadUserByUsername(principal.getName());

        Board newBoard = new Board();
        newBoard.setTitle(board.get("title"));
        newBoard.setContent(board.get("content"));
        newBoard.setBookTitle(board.get("bookTitle"));
        newBoard.setBookImgUrl(board.get("bookImgUrl"));
        newBoard.setBookGenre(board.get("bookGenre"));
        newBoard.setUser(user);

        Board saveBoard = boardService.write(newBoard);
        return new ResponseEntity<>(BoardDto.boardResponse(saveBoard), HttpStatus.CREATED);
    }

    //전체 게시글 조회
    @GetMapping("/all")
    public List<BoardDto> allPosts() {
        return boardService.allPosts();
    }

    //특정 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> findPost(@PathVariable Long boardId) {
        return new ResponseEntity<>(BoardDto.boardResponse(boardService.find(boardId)), HttpStatus.OK);
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardDto> update(@PathVariable Long boardId, @RequestBody Map<String, String> board, Principal principal) throws NoAccessException {
        Board originBoard = boardService.find(boardId);
        User user = userService.loadUserByUsername(principal.getName());

        if (board.get("title") != null) originBoard.setTitle(board.get("title"));
        if (board.get("content") != null) originBoard.setContent(board.get("content"));

        Board updateBoard = boardService.update(originBoard, user.getUserId());
        return new ResponseEntity<>(BoardDto.boardResponse(updateBoard), HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Object> delete(@PathVariable Long boardId, Principal principal) throws NoAccessException {
        Board originBoard = boardService.find(boardId);
        User user = userService.loadUserByUsername(principal.getName());

        originBoard.setDeleteCheck(true);
        Board updateBoard = boardService.update(originBoard, user.getUserId());

        Map<String, String> map = new HashMap<>();
        map.put("result", "삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
