package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.nine.booknutsbackend.repository.HeartRepository;
import team.nine.booknutsbackend.repository.NutsRepository;

@RequiredArgsConstructor
@Service
public class ReactionService {

    private final HeartRepository heartRepository;
    private final NutsRepository nutsRepository;
}
