package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.BoardRequest;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.exception.board.NoAccessException;
import team.nine.booknutsbackend.service.BoardService;
import team.nine.booknutsbackend.service.UserService;

import javax.validation.Valid;
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
    public ResponseEntity<BoardResponse> write(@RequestBody @Valid BoardRequest board, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        Board saveBoard = boardService.write(BoardRequest.newBoard(board, user));
        return new ResponseEntity<>(BoardResponse.boardResponse(saveBoard, user), HttpStatus.CREATED);
    }

    //게시글 조회
    //나의 구독 = 0, 오늘 추천 = 1, 독립 출판 = 2
    @GetMapping("/list/{type}")
    public List<BoardResponse> boardList(@PathVariable int type, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return boardService.boardList(user, type);
    }

    //내가 작성한 게시글
    @GetMapping("/mypost")
    public List<BoardResponse> myBoardList(Principal principal){
        User user = userService.loadUserByUsername(principal.getName());
        return boardService.myBoardList(user);
    }

    //특정 게시글 조회
    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse> findPost(@PathVariable Long boardId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(BoardResponse.boardResponse(boardService.findBoard(boardId), user), HttpStatus.OK);
    }

    //게시글 수정
    @PutMapping("/{boardId}")
    public ResponseEntity<BoardResponse> update(@PathVariable Long boardId, @RequestBody BoardRequest board, Principal principal) throws NoAccessException {
        Board originBoard = boardService.findBoard(boardId);
        User user = userService.loadUserByUsername(principal.getName());

        if (board.getTitle() != null) originBoard.setTitle(board.getTitle());
        if (board.getContent() != null) originBoard.setContent(board.getContent());

        Board updateBoard = boardService.update(originBoard, user);
        return new ResponseEntity<>(BoardResponse.boardResponse(updateBoard, user), HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Object> delete(@PathVariable Long boardId, Principal principal) throws NoAccessException {
        User user = userService.loadUserByUsername(principal.getName());
        boardService.delete(boardId, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
