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
import org.springframework.web.bind.annotation.*;

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
    @PutMapping(value = "/user/{userId}/questions/{questionId}/upVote")
    public QuestionModel upVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        return questionVoting(questionId, (voteModel, questionModel) -> {
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
    @PutMapping(value = "/user/{userId}/questions/{questionId}/downVote")
    public QuestionModel downVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        return questionVoting(questionId, (voteModel, questionModel) -> {
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
    @PutMapping(value = "/user/{userId}/questions/{questionId}/unVote")
    public QuestionModel unVoteQuestion(@PathVariable long userId, @PathVariable long questionId) {
        return questionVoting(questionId, (voteModel, questionModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = questionModel.getUserQuestion();
            if (voteModel.decrementDownVotes(userVoting)) author.incrementReputation();
            if (voteModel.decrementUpVotes(userVoting)) author.decrementReputation();
            return author;
        });
    }

    //Up vote an answer by user
    @PutMapping(value = "/user/{userId}/questions/{questionId}/replies/{replyId}/upVote")
    public AnswerModel upVoteAnswer(@PathVariable long userId, @PathVariable long replyId) {
        return answerVoting(replyId, ((voteModel, answerModel) -> {
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
    @PutMapping(value = "/user/{userId}/questions/{questionId}/replies/{replyId}/downVote")
    public AnswerModel downVoteAnswer(@PathVariable long userId, @PathVariable long replyId) {
        return answerVoting(replyId, ((voteModel, answerModel) -> {
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
    @PutMapping(value = "/user/{userId}/questions/{questionId}/replies/{replyId}/unVote")
    public AnswerModel unVoteAnswer(@PathVariable long userId, @PathVariable long replyId) {
        return answerVoting(replyId, ((voteModel, answerModel) -> {
            UserModel userVoting = userRepository.findOne(userId);
            UserModel author = answerModel.getUserAnswer();
            if (voteModel.decrementDownVotes(userVoting)) author.incrementReputation();
            if (voteModel.decrementUpVotes(userVoting)) author.decrementReputation();
            return author;
        }));
    }

    //Get Id for all users who up voted for a question
    @GetMapping(value = "/questions/{questionId}/upVotedUsers")
    public List<Long> getAllUpVotedUsersQuestions(@PathVariable long questionId) {
        return getAllUpVotedUserId(questionId);
    }

    //Get Id for all users who up voted for a answer
    @GetMapping(value = "/questions/{questionId}/replies/{replyId}/upVotedUsers")
    public List<Long> getAllUpVotedUsersAnswers(@PathVariable long replyId) {
        return getAllUpVotedUserId(replyId);
    }

    //Update Best Answer for Question
    @PutMapping(value = "/users/{userId}/questions/{questionId}/bestAnswer/{answerId}")
    public AnswerModel setBestAnswer(@PathVariable long questionId, @PathVariable long answerId, @PathVariable long userId) {
        QuestionModel question = questionRepository.getOne(questionId);
        if (userId == question.getUserQuestion().getId()) question.setBestAnswer(answerId);
        questionRepository.save(question);
        return answerRepository.findOne(answerId);
    }

    //Get Best Answer for Question
    @GetMapping(value = "/questions/{questionId}/bestAnswer")
    public AnswerModel getBestAnswer(@PathVariable long questionId) {
        QuestionModel question = questionRepository.getOne(questionId);
        return answerRepository.findOne(question.getBestAnswer());
    }

    //Helper method tog et all up voted users
    private List<Long> getAllUpVotedUserId(long postId) {
        List<Long> list = new LinkedList<>();
        userRepository.findByUpVotedVoteModelsId(voteRepository.findByForumPostId(postId).getId()).forEach(user -> list.add(user.getId()));
        return list;
    }

    //Helper method for voting on questions
    private QuestionModel questionVoting(long postId, BiFunction<VoteModel, QuestionModel, UserModel> voting) {
        VoteModel vote = voteRepository.findByForumPostId(postId);
        QuestionModel question = questionRepository.findOne(postId);
        userRepository.save(voting.apply(vote, question));
        voteRepository.save(vote);
        questionRepository.save(question);
        return question;
    }

    //Helper method for voting on answers
    private AnswerModel answerVoting(long postId, BiFunction<VoteModel, AnswerModel, UserModel> voting) {
        VoteModel vote = voteRepository.findByForumPostId(postId);
        AnswerModel answer = answerRepository.findOne(postId);
        userRepository.save(voting.apply(vote, answer));
        voteRepository.save(vote);
        answerRepository.save(answer);
        return answer;
    }
}
