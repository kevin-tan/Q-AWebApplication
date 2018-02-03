package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user/{userId}/questions/{questionId}")
public class VoteController {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository, UserRepository userRepository, QuestionRepository questionRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @RequestMapping(value = "/upVote")
    public void upVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        VoteModel vote = voteRepository.findByForumPostId(questionId);
        QuestionModel question = questionRepository.findOne(questionId);
        if (vote.incrementUpVotes(userRepository.findOne(userId))) {
            question.getUser().incrementReputation();
        }
        voteRepository.save(vote);
        questionRepository.save(question);
    }
}
