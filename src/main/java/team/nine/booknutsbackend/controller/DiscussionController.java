package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.nine.booknutsbackend.domain.Discussion;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.RoomDto;
import team.nine.booknutsbackend.service.DiscussionService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
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
        newRoom.setMaxUser(Long.valueOf(room.get("maxUser")));
        newRoom.setUser(user);

        //개설자가 '반대'일 경우
        if(Integer.parseInt(room.get("yesno")) == 0){
            newRoom.setCurYesUser(0L);
            newRoom.setCurNoUser(1L);
        }
        //개설자가 '찬성'일 경우
        else{
            newRoom.setCurYesUser(1L);
            newRoom.setCurNoUser(0L);
        }

        Discussion saveRoom = discussionService.createRoom(newRoom);
        return new ResponseEntity<>(RoomDto.roomResponse(saveRoom, user), HttpStatus.CREATED);
    }
}
