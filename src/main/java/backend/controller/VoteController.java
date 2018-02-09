package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.qa.QuestionModel;
import backend.model.user.UserModel;
import backend.model.vote.VoteModel;
import backend.repository.qa.AnswerRepository;
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
    private final AnswerRepository answerRepository;

    @Autowired
    public VoteController(VoteRepository voteRepository, UserRepository userRepository, QuestionRepository questionRepository,
                          AnswerRepository answerRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    //Up vote a question by user
    @RequestMapping(value = "/user/{userId}/questions/{questionId}/upVote")
    public void upVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        questionVoting(questionId, (voteModel, questionModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = questionModel.getUserQuestion();
            if (voteModel.incrementUpVotes(userVoting)) {
                if (voteModel.decrementDownVotes(userVoting)) author.incrementReputation();
                author.incrementReputation();
            }
            return author;
        });
    }

    //Down vote a question by user
    @RequestMapping(value = "/user/{userId}/questions/{questionId}/downVote")
    public void downVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        questionVoting(questionId, (voteModel, questionModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = questionModel.getUserQuestion();
            if (voteModel.incrementDownVotes(userVoting)) {
                if (voteModel.decrementUpVotes(userVoting)) author.decrementReputation();
                author.decrementReputation();
            }
            return author;
        });
    }

    //Remove vote on a question by user
    @RequestMapping(value = "/user/{userId}/questions/{questionId}/unVote")
    public void unVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        questionVoting(questionId, (voteModel, questionModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = questionModel.getUserQuestion();
            if (voteModel.decrementDownVotes(userVoting)) author.incrementReputation();
            if (voteModel.decrementUpVotes(userVoting)) author.decrementReputation();
            return author;
        });
    }

    //Up vote an answer by user
    @RequestMapping(value = "/user/{userId}/questions/{questionId}/replies/{replyId}/upVote")
    public void upVoteAnswer(@PathVariable long userId, @PathVariable long replyId) {
        answerVoting(replyId, ((voteModel, answerModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = answerModel.getUserAnswer();
            if (voteModel.incrementUpVotes(userVoting)) {
                if (voteModel.decrementDownVotes(userVoting)) author.incrementReputation();
                author.incrementReputation();
            }
            return author;
        }));
    }

    //Down vote an answer by user
    @RequestMapping(value = "/user/{userId}/questions/{questionId}/replies/{replyId}/downVote")
    public void downVoteAnswer(@PathVariable long userId, @PathVariable long replyId) {
        answerVoting(replyId, ((voteModel, answerModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = answerModel.getUserAnswer();
            if (voteModel.incrementDownVotes(userVoting)) {
                if (voteModel.decrementUpVotes(userVoting)) author.decrementReputation();
                author.decrementReputation();
            }
            return author;
        }));
    }

    //Remove vote on answer by user
    @RequestMapping(value = "/user/{userId}/questions/{questionId}/replies/{replyId}/unVote")
    public void unVoteAnswer(@PathVariable long userId, @PathVariable long replyId) {
        answerVoting(replyId, ((voteModel, answerModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = answerModel.getUserAnswer();
            if (voteModel.decrementDownVotes(userVoting)) author.incrementReputation();
            if (voteModel.decrementUpVotes(userVoting)) author.decrementReputation();
            return author;
        }));
    }

    //Get Id for all users who up voted for a question
    @RequestMapping(value = "/questions/{questionId}/upVotedUsers")
    public List<Long> getAllUpVotedUsersQuestions(@PathVariable long questionId) {
        return getAllUpVotedUserId(questionId);
    }

    //Get Id for all users who up voted for a answer
    @RequestMapping(value = "/questions/{questionId}/replies/{replyId}/upVotedUsers")
    public List<Long> getAllUpVotedUsersAnswers(@PathVariable long replyId) {
        return getAllUpVotedUserId(replyId);
    }
    
    //Update Best Answer for Question
    @RequestMapping(value = "/questions/{questionId}/bestAnswer/{AnswerId}")
    public AnswerModel setBestAnswer(@PathVariable long questionId, @PathVariable long AnswerId) {
    	QuestionModel question = questionRepository.getOne(questionId);
    	question.setBestAnswer(AnswerId);
    	questionRepository.save(question);
    	return answerRepository.findOne(AnswerId);
    }
    
    //Get Best Answer for Question
    @RequestMapping(value = "/questions/{questionId}/bestAnswer")
    public AnswerModel getBestAnswer(@PathVariable long questionId) {
    	QuestionModel question = questionRepository.getOne(questionId);
    	Long answerId = question.getBestAnswer();
    	return answerRepository.findOne(answerId);
    }

    //Helper method tog et all up voted users
    private List<Long> getAllUpVotedUserId(long postId) {
        List<Long> list = new LinkedList<>();
        userRepository.findByUpVotedVoteModelsId(voteRepository.findByForumPostId(postId).getId()).forEach(user -> list.add(user.getId()));
        return list;
    }

    //Helper method for voting on questions
    private void questionVoting(long postId, BiFunction<VoteModel, QuestionModel, UserModel> voting) {
        VoteModel vote = voteRepository.findByForumPostId(postId);
        QuestionModel question = questionRepository.findOne(postId);
        userRepository.save(voting.apply(vote, question));
        voteRepository.save(vote);
        questionRepository.save(question);
    }

    //Helper method for voting on answers
    private void answerVoting(long postId, BiFunction<VoteModel, AnswerModel, UserModel> voting) {
        VoteModel vote = voteRepository.findByForumPostId(postId);
        AnswerModel answer = answerRepository.findOne(postId);
        userRepository.save(voting.apply(vote, answer));
        voteRepository.save(vote);
        answerRepository.save(answer);
    }
}
