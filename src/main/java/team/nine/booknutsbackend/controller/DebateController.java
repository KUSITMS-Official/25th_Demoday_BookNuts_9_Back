package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.DebateRoomRequest;
import team.nine.booknutsbackend.dto.Response.DebateRoomResponse;
import team.nine.booknutsbackend.exception.Debate.CannotJoinException;
import team.nine.booknutsbackend.exception.Debate.StatusChangeException;
import team.nine.booknutsbackend.service.DebateService;
import team.nine.booknutsbackend.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/debate")
public class DebateController {

    private final DebateService debateService;
    private final UserService userService;

    //토론장 개설
    @PostMapping("/create")
    public ResponseEntity<DebateRoomResponse> createRoom(@RequestBody @Valid DebateRoomRequest room, Principal principal) throws CannotJoinException {
        User user = userService.loadUserByUsername(principal.getName());
        DebateRoom newRoom = debateService.createRoom(DebateRoomRequest.newRoom(room, user));
        DebateRoom saveRoom = debateService.join(newRoom.getDebateRoomId(), user, room.isOpinion());
        return new ResponseEntity<>(DebateRoomResponse.roomResponse(saveRoom), HttpStatus.CREATED);
    }

    //참여 가능 여부
    @GetMapping("/canjoin/{roomId}")
    public ResponseEntity<Object> canJoin(@PathVariable Long roomId) {
        return new ResponseEntity<>(debateService.canJoin(roomId), HttpStatus.OK);
    }

    //토론 참여
    @GetMapping("/join/{roomId}")
    public ResponseEntity<DebateRoomResponse> join(@PathVariable Long roomId, @RequestParam Boolean opinion, Principal principal) throws CannotJoinException {
        User user = userService.loadUserByUsername(principal.getName());
        DebateRoom room = debateService.join(roomId, user, opinion);
        return new ResponseEntity<>(DebateRoomResponse.roomResponse(room), HttpStatus.OK);
    }

    //토론 나가기
    @GetMapping("/exit/{roomId}")
    public ResponseEntity<Object> exit(@PathVariable Long roomId, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        debateService.exit(debateService.findRoom(roomId), user);

        Map<String, String> map = new HashMap<>();
        map.put("result", "나가기 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //토론장 상태 변경
    @PatchMapping("/update/{roomId}")
    public ResponseEntity<DebateRoomResponse> changeStatus(@PathVariable Long roomId, @RequestParam int status, Principal principal) throws StatusChangeException {
        User user = userService.loadUserByUsername(principal.getName());
        DebateRoom updateRoom = debateService.changeStatus(roomId, status, user);
        return new ResponseEntity<>(DebateRoomResponse.roomResponse(updateRoom), HttpStatus.OK);
    }

    //토론장 리스트
    //텍스트 = 0, 음성 = 1, 전체 = 2
    @GetMapping("/list/{type}")
    public ResponseEntity<Object> roomList(@PathVariable int type) {
        Map<String, List<DebateRoomResponse>> map = new LinkedHashMap<>();
        map.put("맞춤 토론", debateService.customDebate(type));
        map.put("현재 진행 중인 토론", debateService.ingDebate(type));
        map.put("현재 대기 중인 토론", debateService.readyDebate(type));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
