package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.ArchiveRequest;
import team.nine.booknutsbackend.dto.Request.SeriesRequest;
import team.nine.booknutsbackend.dto.Response.ArchiveResponse;
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

    //아카이브 추가
    @PostMapping("/addarchive")
    public ResponseEntity<Object> addArchive(@RequestBody ArchiveRequest archiveRequest, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        archiveService.saveArchive(archiveRequest, user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "추가 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
