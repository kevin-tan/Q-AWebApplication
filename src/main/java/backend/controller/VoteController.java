package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.QuestionRepository;
import backend.repository.user.UserRepository;
import backend.repository.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin
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

    @RequestMapping(value = "/user/{userId}/questions/{questionId}/upVote")
    public void upVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        VoteModel vote = voteRepository.findByForumPostId(questionId);
        QuestionModel question = questionRepository.findOne(questionId);
        if (vote.incrementUpVotes(userRepository.findOne(userId))) {
            question.getUserQuestion().incrementReputation();
        }
        voteRepository.save(vote);
        questionRepository.save(question);

    }

    //Get Id for all users who up voted
    @RequestMapping(value = "/questions/{questionId}/upVotedUsers")
    public List<Long> getAllUpVotedUsers(@PathVariable long questionId) {
        List<Long> list = new LinkedList<>();
        userRepository.findByUpVotedVoteModelsId(voteRepository.findByForumPostId(questionId).getId())
                      .forEach(user -> list.add(user.getId()));
        return list;
    }

}