package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;
import team.nine.booknutsbackend.domain.Debate.DebateUser;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Response.DebateRoomResponse;
import team.nine.booknutsbackend.exception.Debate.CannotJoinException;
import team.nine.booknutsbackend.exception.Debate.RoomNotFoundException;
import team.nine.booknutsbackend.exception.Debate.StatusChangeException;
import team.nine.booknutsbackend.exception.Debate.UserNotFoundException;
import team.nine.booknutsbackend.repository.DebateRoomRepository;
import team.nine.booknutsbackend.repository.DebateUserRepository;

import java.util.*;

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

    //참여 가능 여부
    @Transactional(readOnly = true)
    public Object canJoin(Long roomId) {
        DebateRoom room = findRoom(roomId);
        int sideUser = room.getMaxUser() / 2;
        int curYesUser = room.getCurYesUser();
        int curNoUser = room.getCurNoUser();

        Map<String, Boolean> map = new LinkedHashMap<>();
        map.put("canJoinYes", sideUser > curYesUser);
        map.put("canJoinNo", sideUser > curNoUser);
        return map;
    }

    //토론장 참여
    @Transactional
    public DebateRoom join(Long roomId, User user, boolean opinion) throws CannotJoinException {
        DebateRoom room = findRoom(roomId);

        if (debateUserRepository.findByDebateRoomAndUser(room, user).isPresent())
            throw new CannotJoinException("이미 참여 중인 유저입니다.");
        if (room.getStatus() != 0) throw new CannotJoinException("참여할 수 없는 토론 상태입니다.");
        if (opinion && (room.getMaxUser() / 2 <= room.getCurYesUser()))
            throw new CannotJoinException("찬성측 인원 초과로 참여할 수 없습니다.");
        if (!opinion && (room.getMaxUser() / 2 <= room.getCurNoUser()))
            throw new CannotJoinException("반대측 인원 초과로 참여할 수 없습니다.");

        DebateUser debateUser = new DebateUser();
        debateUser.setUser(user);
        debateUser.setDebateRoom(room);
        debateUser.setOpinion(opinion);
        debateUserRepository.save(debateUser);

        return updateCount(room);
    }

    //토론 나가기
    @Transactional
    public void exit(DebateRoom room, User user) {
        DebateUser debateUser = debateUserRepository.findByDebateRoomAndUser(room, user)
                .orElseThrow(() -> new UserNotFoundException("토론에 참여한 유저가 아닙니다."));
        debateUserRepository.delete(debateUser);
        updateCount(room);
    }

    //토론장 상태 변경
    @Transactional
    public DebateRoom changeStatus(Long roomId, int status, User user) throws StatusChangeException {
        DebateRoom room = findRoom(roomId);

        if (room.getOwner().getUserId() != user.getUserId()) throw new StatusChangeException("토론 개설자만 상태를 변경할 수 있습니다.");
        if (status <= 0 || status > 2) throw new StatusChangeException("상태값은 토론 진행 중(=1) 또는 토론 종료(=2) 여야 합니다.");

        room.setStatus(status);
        return debateRoomRepository.save(room);
    }

    //참여 유저 수 업데이트
    @Transactional
    public DebateRoom updateCount(DebateRoom room) {
        room.setCurYesUser(debateUserRepository.countByDebateRoomAndOpinion(room, true));
        room.setCurNoUser(debateUserRepository.countByDebateRoomAndOpinion(room, false));
        return debateRoomRepository.save(room);
    }

    //토론장 조회
    @Transactional(readOnly = true)
    public DebateRoom findRoom(Long roomId) {
        return debateRoomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("존재하지 않는 토론장 아이디입니다."));
    }

    //맞춤 토론 리스트
    @Transactional(readOnly = true)
    public List<DebateRoomResponse> customDebate(int type) {
        List<DebateRoom> rooms;
        if (type == 2) rooms = debateRoomRepository.findByStatus(0);
        else rooms = debateRoomRepository.findByTypeAndStatus(type, 0);
        List<DebateRoomResponse> roomDtoList = new ArrayList<>();

        //임의로, '토론 대기 중' 상태인 3개의 토론을 반환
        int cnt = 0;
        for (DebateRoom room : rooms) {
            roomDtoList.add(DebateRoomResponse.roomResponse(room));
            cnt++;
            if (cnt == 3) break;
        }

        return roomDtoList;
    }

    //현재 진행 중인 토론 리스트
    @Transactional(readOnly = true)
    public List<DebateRoomResponse> ingDebate(int type) {
        List<DebateRoom> rooms;
        if (type == 2) rooms = debateRoomRepository.findByStatus(1);
        else rooms = debateRoomRepository.findByTypeAndStatus(type, 1);
        List<DebateRoomResponse> roomDtoList = new ArrayList<>();

        for (DebateRoom room : rooms) {
            roomDtoList.add(DebateRoomResponse.roomResponse(room));
        }
        Collections.reverse(roomDtoList); //최신순
        return roomDtoList;
    }

    //현재 대기 중인 토론 리스트
    @Transactional(readOnly = true)
    public List<DebateRoomResponse> readyDebate(int type) {
        List<DebateRoom> rooms;
        if (type == 2) rooms = debateRoomRepository.findByStatus(0);
        else rooms = debateRoomRepository.findByTypeAndStatus(type, 0);
        List<DebateRoomResponse> roomDtoList = new ArrayList<>();

        for (DebateRoom room : rooms) {
            roomDtoList.add(DebateRoomResponse.roomResponse(room));
        }
        Collections.reverse(roomDtoList); //최신순
        return roomDtoList;
    }

}
