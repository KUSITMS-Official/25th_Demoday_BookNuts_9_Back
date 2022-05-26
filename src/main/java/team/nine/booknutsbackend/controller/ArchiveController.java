package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;
import team.nine.booknutsbackend.dto.Request.ArchiveRequest;
import team.nine.booknutsbackend.dto.Response.ArchiveResponse;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.exception.board.NoAccessException;
import team.nine.booknutsbackend.service.ArchiveService;
import team.nine.booknutsbackend.service.AuthService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/archive")
public class ArchiveController {

    private final ArchiveService archiveService;
    private final AuthService userService;

    //아카이브 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ArchiveResponse>> archiveList(Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(archiveService.archiveList(user), HttpStatus.OK);
    }

    //아카이브 생성
    @PostMapping("/createarchive")
    public ResponseEntity<ArchiveResponse> create(@RequestBody ArchiveRequest archiveRequest, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        Archive newArchive = archiveService.create(ArchiveRequest.newArchive(archiveRequest, user));
        return new ResponseEntity<>(ArchiveResponse.archiveResponse(newArchive), HttpStatus.CREATED);
    }

    //특정 아카이브 조회
    @GetMapping("/{archiveId}")
    public ResponseEntity<List<BoardResponse>> findArchive(@PathVariable Long archiveId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return new ResponseEntity<>(archiveService.findArchive(archiveId, user), HttpStatus.OK);
    }

    //아카이브에 추가
    @GetMapping("/addarchive/{archiveId}/{boardId}")
    public ResponseEntity<Object> addPostToArchive(@PathVariable Long archiveId, @PathVariable Long boardId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        archiveService.addPostToArchive(archiveId, boardId, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "추가 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //아카이브 삭제
    @DeleteMapping("/{archiveId}")
    public ResponseEntity<Object> delete(@PathVariable Long archiveId, Principal principal) throws NoAccessException {
        User user = userService.loadUserByUsername(principal.getName());
        archiveService.delete(archiveId, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //아카이브 내의 게시글 삭제
    @DeleteMapping("/{archiveId}/{boardId}")
    public ResponseEntity<Object> deletePostFromArchive(@PathVariable Long archiveId, @PathVariable Long boardId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        archiveService.deletePostFromArchive(archiveId, boardId);

        Map<String, String> map = new HashMap<>();
        map.put("result", "삭제 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //아카이브 수정
    @PatchMapping("/{archiveId}")
    public ResponseEntity<ArchiveResponse> update(@PathVariable Long archiveId, @RequestBody ArchiveRequest archiveRequest, Principal principal) throws NoAccessException {
        Archive archive = archiveService.findByArchiveId(archiveId);
        User user = userService.loadUserByUsername(principal.getName());

        if (archiveRequest.getTitle() != null) archive.setTitle(archiveRequest.getTitle());
        if (archiveRequest.getContent() != null) archive.setContent(archiveRequest.getContent());
        if (archiveRequest.getImgUrl() != null) archive.setImgUrl(archiveRequest.getImgUrl());

        Archive updateArchive = archiveService.update(archive, user);
        return new ResponseEntity<>(ArchiveResponse.archiveResponse(updateArchive), HttpStatus.OK);
    }

}