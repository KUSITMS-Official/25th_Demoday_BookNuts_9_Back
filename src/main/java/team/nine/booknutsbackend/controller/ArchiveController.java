package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.ArchiveRequest;
import team.nine.booknutsbackend.dto.Response.ArchiveResponse;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.service.ArchiveService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/archive")
public class ArchiveController {

    private final ArchiveService archiveService;
    private final UserService userService;

    //아카이브 목록 조회
    @GetMapping("/list")
    public List<ArchiveResponse> allarchive(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return archiveService.allarchive(user);
    }

    //아카이브 생성
    @PostMapping("/createarchive")
    public ResponseEntity<Object> createArchive(@RequestBody ArchiveRequest archiveRequest, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        archiveService.saveArchive(archiveRequest, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "아카이브 생성 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //특정 아카이브 조회
    @GetMapping("/{archiveId}")
    public List<BoardResponse> findArchive(@PathVariable Long archiveId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return archiveService.findArchive(archiveId, user);
    }

    //아카이브에 추가
    @GetMapping("/addarchive/{archiveId}/{boardId}")
    public ResponseEntity<Object> addToArchive(@PathVariable Long archiveId, @PathVariable Long boardId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        archiveService.addToArchive(archiveId, boardId, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "아카이브에 추가 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
