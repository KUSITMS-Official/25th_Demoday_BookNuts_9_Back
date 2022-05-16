package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Discussion;
import team.nine.booknutsbackend.repository.DiscussionRepository;

@RequiredArgsConstructor
@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;

    //토론장 개설
    @Transactional
    public Discussion createRoom(Discussion newRoom) {
        return discussionRepository.save(newRoom);
    }
}
