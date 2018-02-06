package backend.controller;

import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
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
import java.util.function.BiFunction;

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
        questionVoting(questionId, (voteModel, questionModel) -> {
            UserModel user = userRepository.findOne(userId);
            UserModel author = questionModel.getUserQuestion();
            if (voteModel.incrementUpVotes(user)) {
                if (voteModel.decrementDownVotes(user)) author.incrementReputation();
                author.incrementReputation();
            }
            return author;
        });
    }

    @RequestMapping(value = "/user/{userId}/questions/{questionId}/downVote")
    public void downVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        questionVoting(questionId, (voteModel, questionModel) -> {
            UserModel user = userRepository.findOne(userId);
            UserModel author = questionModel.getUserQuestion();
            if (voteModel.incrementDownVotes(user)) {
                if (voteModel.decrementUpVotes(user)) author.decrementReputation();
                author.decrementReputation();
            }
            return author;
        });
    }

    @RequestMapping(value = "/user/{userId}/questions/{questionId}/unVote")
    public void unVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        questionVoting(questionId, (voteModel, questionModel) -> {
            UserModel user = userRepository.findOne(userId);
            UserModel author = questionModel.getUserQuestion();
            if(voteModel.decrementDownVotes(user)) author.incrementReputation();
            if(voteModel.decrementUpVotes(user)) author.decrementReputation();
            return author;
        });
    }

    //Get Id for all users who up voted for a question
    @RequestMapping(value = "/questions/{questionId}/upVotedUsers")
    public List<Long> getAllUpVotedUsers(@PathVariable long questionId) {
        List<Long> list = new LinkedList<>();
        userRepository.findByUpVotedVoteModelsId(voteRepository.findByForumPostId(questionId).getId())
                      .forEach(user -> list.add(user.getId()));
        return list;
    }

    private void questionVoting(long postId, BiFunction<VoteModel, QuestionModel, UserModel> voting) {
        VoteModel vote = voteRepository.findByForumPostId(postId);
        QuestionModel question = questionRepository.findOne(postId);
        userRepository.save(voting.apply(vote, question));
        voteRepository.save(vote);
        questionRepository.save(question);
    }

}
