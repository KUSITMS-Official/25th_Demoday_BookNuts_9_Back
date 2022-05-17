package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.DebateRoomRequest;
import team.nine.booknutsbackend.dto.Response.DebateRoomResponse;
import team.nine.booknutsbackend.service.DebateService;
import team.nine.booknutsbackend.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/debate")
public class DebateController {

    private final DebateService debateService;
    private final UserService userService;

    //토론장 개설
    @PostMapping("/create")
    public ResponseEntity<DebateRoomResponse> createRoom(@RequestBody @Valid DebateRoomRequest room, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        DebateRoom newRoom = debateService.createRoom(DebateRoomRequest.newRoom(room, user));
        DebateRoom saveRoom = debateService.join(newRoom, user, room.isOpinion());
        return new ResponseEntity<>(DebateRoomResponse.roomResponse(saveRoom), HttpStatus.CREATED);
    }

}
