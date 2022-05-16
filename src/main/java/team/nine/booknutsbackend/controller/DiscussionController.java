package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.Discussion;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.RoomDto;
import team.nine.booknutsbackend.service.DiscussionService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/discuss")
public class DiscussionController {

    private final DiscussionService discussionService;
    private final UserService userService;

    //토론장 개설
    @PostMapping("/create")
    public ResponseEntity<RoomDto> createRoom(@RequestBody Map<String, String> room, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        Discussion newRoom = new Discussion();
        newRoom.setBookTitle(room.get("bookTitle"));
        newRoom.setBookImgUrl(room.get("bookImgUrl"));
        newRoom.setBookGenre(room.get("bookGenre"));
        newRoom.setTopic(room.get("topic"));
        newRoom.setCoverImgUrl(room.get("coverImgUrl"));
        newRoom.setType(Integer.parseInt(room.get("type")));
        newRoom.setMaxUser(Integer.parseInt(room.get("maxUser")));
        newRoom.setUser(user);

        //개설자가 '반대'일 경우
        if (Integer.parseInt(room.get("yesno")) == 0) {
            newRoom.setCurYesUser(0);
            newRoom.setCurNoUser(1);
        }
        //개설자가 '찬성'일 경우
        else {
            newRoom.setCurYesUser(1);
            newRoom.setCurNoUser(0);
        }

        Discussion saveRoom = discussionService.createRoom(newRoom);
        return new ResponseEntity<>(RoomDto.roomResponse(saveRoom, user), HttpStatus.CREATED);
    }

    //토론장 참여 가능 여부 (가능할 경우 참여)
    @PostMapping("/join/{roomId}")
    public Boolean joinRoom(@PathVariable Long roomId, @RequestBody Map<String, String> yesno) {
        int opinion = Integer.parseInt(yesno.get("yesno"));
        Boolean canJoin = discussionService.joinRoomCheck(roomId, opinion);

        if (canJoin) {
            discussionService.updateJoin(roomId, opinion); //true 일 경우 참여
            return true;
        } else {
            return false;
        }
    }
    
    //토론장 나가기
    @PostMapping("/exit/{roomId}")
    public ResponseEntity<Object> exitRoom(@PathVariable Long roomId, @RequestBody Map<String, String> yesno) {
        int opinion = Integer.parseInt(yesno.get("yesno"));
        discussionService.updateExit(roomId, opinion);

        Map<String, String> map = new HashMap<>();
        map.put("result", "나가기 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
