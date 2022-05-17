package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;
import team.nine.booknutsbackend.domain.Debate.DebateUser;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.repository.DebateRoomRepository;
import team.nine.booknutsbackend.repository.DebateUserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class DebateService {

    private final DebateRoomRepository debateRoomRepository;
    private final DebateUserRepository debateUserRepository;

    //토론장 개설
    @Transactional
    public DebateRoom createRoom(DebateRoom newRoom) {
        return debateRoomRepository.save(newRoom);
    }

    //토론장 참여
    @Transactional
    public DebateRoom join(DebateRoom room, User user, boolean opinion) {

        DebateUser debateUser = new DebateUser();
        debateUser.setUser(user);
        debateUser.setDebateRoom(room);
        debateUser.setOpinion(opinion);
        debateUserRepository.save(debateUser);

        room.setCurYesUser(userCount(room).get("yesUser"));
        room.setCurNoUser(userCount(room).get("noUser"));

        return debateRoomRepository.save(room);
    }

    //찬성 & 반대 유저 카운팅
    @Transactional(readOnly = true)
    public Map<String, Integer> userCount(DebateRoom room) {
        List<DebateUser> debateUsers = debateUserRepository.findByDebateRoom(room);

        int yesUser = 0;
        int noUser = 0;
        for (DebateUser user : debateUsers) {
            if (user.getOpinion()) yesUser++;
            else noUser++;
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("yesUser", yesUser);
        map.put("noUser", noUser);

        return map;
    }
}
