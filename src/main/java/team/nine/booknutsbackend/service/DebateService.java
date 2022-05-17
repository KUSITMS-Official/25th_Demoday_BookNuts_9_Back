package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Debate;
import team.nine.booknutsbackend.dto.Response.DebateListResponse;
import team.nine.booknutsbackend.exception.Debate.OpinionValueException;
import team.nine.booknutsbackend.exception.Debate.RoomNotFoundException;
import team.nine.booknutsbackend.exception.Debate.StatusValueException;
import team.nine.booknutsbackend.repository.DebateRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DebateService {

    private final DebateRepository debateRepository;

    //토론장 개설
    @Transactional
    public Debate createRoom(Debate newRoom) {
        return debateRepository.save(newRoom);
    }

    //토론장 참여 가능 여부
    @Transactional(readOnly = true)
    public Boolean joinRoomCheck(Long roomId, int opinion) throws OpinionValueException {
        Debate room = find(roomId);
        if (opinion < 0 || opinion > 2) throw new OpinionValueException("찬성(= 1) 또는 반대(= 0)에 해당하는 값이어야합니다.");

        int status = room.getStatus();
        int sideUser = room.getMaxUser() / 2;
        int curYesUser = room.getCurYesUser();
        int curNoUser = room.getCurNoUser();

        if (status > 0) return false; //0 : 토론 대기 중, 1 : 토론 진행 중, 2 : 토론 종료
        if (opinion == 0) return sideUser > curNoUser; //'반대'로 참여
        else return sideUser > curYesUser; //'찬성'으로 참여
    }

    //토론장 조회
    @Transactional(readOnly = true)
    public Debate find(Long roomId) {
        return debateRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("존재하지 않는 토론장 아이디입니다."));
    }

    //토론 참여하기 - 참여자수 업데이트
    @Transactional
    public void updateJoin(Long roomId, int opinion) {
        Debate room = find(roomId);

        if (opinion == 0) room.setCurNoUser(room.getCurNoUser() + 1); //'반대'로 참여
        else room.setCurYesUser(room.getCurYesUser() + 1); //'찬성'으로 참여

        debateRepository.save(room);
    }

    //토론 나가기 - 참여자수 업데이트
    @Transactional
    public void updateExit(Long roomId, int opinion) throws OpinionValueException {
        Debate room = find(roomId);
        if (opinion < 0 || opinion > 2) throw new OpinionValueException("찬성(= 1) 또는 반대(= 0)에 해당하는 값이어야합니다.");

        if (opinion == 0) room.setCurNoUser(room.getCurNoUser() - 1); //'반대'유저 나가기
        else room.setCurYesUser(room.getCurYesUser() - 1); //'찬성'유저 나가기

        debateRepository.save(room);
    }

    //토론장 상태 변경 가능 여부
    @Transactional(readOnly = true)
    public Boolean checkStatus(Long roomId) {
        Debate room = find(roomId);
        return room.getMaxUser() == (room.getCurYesUser() + room.getCurNoUser());
    }

    //토론장 상태 변경
    @Transactional
    public void updateStatus(Long roomId, int status) throws StatusValueException {
        Debate room = find(roomId);
        if (status <= 0 || status > 2) throw new StatusValueException("변경할 상태 값은 1 또는 2 여야합니다.");

        room.setStatus(status);
        debateRepository.save(room);
    }

    //맞춤 토론 리스트
    @Transactional(readOnly = true)
    public List<DebateListResponse> customDebate(int type) {
        List<Debate> rooms = findRoomByType(type);
        List<DebateListResponse> roomDtoList = new ArrayList<>();

        //임의로,토론 대기 중 상태인 3개 토론을 반환
        int cnt = 0;
        for (Debate room : rooms) {
            if (room.getStatus() == 0) {
                roomDtoList.add(DebateListResponse.listResponse(room));
                cnt++;
            }
            if (cnt == 3) break;
        }

        return roomDtoList;
    }

    //현재 진행 중인 토론 리스트
    @Transactional(readOnly = true)
    public List<DebateListResponse> ingDebate(int type) {
        List<Debate> rooms = findRoomByType(type);
        List<DebateListResponse> roomDtoList = new ArrayList<>();

        for (Debate room : rooms) {
            if (room.getStatus() == 1) {
                roomDtoList.add(DebateListResponse.listResponse(room));
            }
        }

        Collections.reverse(roomDtoList); //최신순
        return roomDtoList;
    }

    //현재 대기 중인 토론 리스트
    @Transactional(readOnly = true)
    public List<DebateListResponse> readyDebate(int type) {
        List<Debate> rooms = findRoomByType(type);
        List<DebateListResponse> roomDtoList = new ArrayList<>();

        for (Debate room : rooms) {
            if (room.getStatus() == 0) {
                roomDtoList.add(DebateListResponse.listResponse(room));
            }
        }

        Collections.reverse(roomDtoList); //최신순
        return roomDtoList;
    }

    //타입별 토론방 반환
    @Transactional(readOnly = true)
    public List<Debate> findRoomByType(int type) {
        if (type == 0 || type == 1) return debateRepository.findByType(type);
        else return debateRepository.findAll();
    }

}
