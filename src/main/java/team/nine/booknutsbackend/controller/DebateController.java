package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.Debate;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.DebateRoomRequest;
import team.nine.booknutsbackend.dto.Response.DebateListResponse;
import team.nine.booknutsbackend.dto.Response.RoomResponse;
import team.nine.booknutsbackend.exception.Debate.OpinionValueException;
import team.nine.booknutsbackend.exception.Debate.StatusValueException;
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
    public ResponseEntity<RoomResponse> createRoom(@RequestBody @Valid DebateRoomRequest room, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        Debate saveRoom = debateService.createRoom(DebateRoomRequest.newRoom(room, user));
        return new ResponseEntity<>(RoomResponse.roomResponse(saveRoom), HttpStatus.CREATED);
    }

    //토론장 참여 가능 여부 (가능할 경우 참여)
    @GetMapping("/join/{roomId}")
    public Boolean joinRoom(@PathVariable Long roomId, @RequestParam int opinion) throws OpinionValueException {
        Boolean canJoin = debateService.joinRoomCheck(roomId, opinion);

        if (canJoin) {
            debateService.updateJoin(roomId, opinion); //true 일 경우 참여
            return true;
        } else {
            return false;
        }
    }

    //토론장 나가기
    @GetMapping("/exit/{roomId}")
    public ResponseEntity<Object> exitRoom(@PathVariable Long roomId, @RequestParam int opinion) throws OpinionValueException {
        debateService.updateExit(roomId, opinion);

        Map<String, String> map = new HashMap<>();
        map.put("result", "나가기 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //토론장 상태 변경 가능 여부 (인원이 모였는지)
    @GetMapping("/check/{roomId}")
    public Boolean checkStatus(@PathVariable Long roomId) {
        return debateService.checkStatus(roomId);
    }

    //토론장 상태 변경
    @PutMapping("/update/{roomId}")
    public ResponseEntity<Object> updateStatus(@PathVariable Long roomId, @RequestParam int status) throws StatusValueException {
        debateService.updateStatus(roomId, status);
        Map<String, String> map = new HashMap<>();
        map.put("result", "상태 변경 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //토론 리스트 반환
    //텍스트 = 0, 음성 = 1, 전체 = 2
    @GetMapping("/list/{type}")
    public ResponseEntity<Object> roomList(@PathVariable int type) {
        Map<String, List<DebateListResponse>> map = new LinkedHashMap<>();
        map.put("맞춤 토론", debateService.customDebate(type));
        map.put("현재 진행 중인 토론", debateService.ingDebate(type));
        map.put("현재 대기 중인 토론", debateService.readyDebate(type));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
