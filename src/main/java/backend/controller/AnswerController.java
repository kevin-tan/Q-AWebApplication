package backend.controller;

import backend.model.qa.AnswerModel;
import backend.model.vote.AnswerVoteModel;
import backend.repository.qa.AnswerRepository;
import backend.repository.qa.QuestionRepository;
import backend.repository.vote.AnswerVoteRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static backend.controller.constants.ForumPostConstants.FORMAT;

@RestController
@RequestMapping("/questions/{questionId}/replies")
public class AnswerController {

    private long count;
    private AnswerVoteRepository answerVoteRepository;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    @Autowired
    public AnswerController(QuestionRepository questionRepository, AnswerRepository answerRepository,
                            AnswerVoteRepository answerVoteRepository) {
        count = questionRepository.count();
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.answerVoteRepository = answerVoteRepository;
    }

    @RequestMapping(value = "")
    public List<AnswerModel> getAllReplies(@PathVariable long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @RequestMapping(value = "/{author}")
    public List<AnswerModel> getAllRepliesByAuthor(@PathVariable long questionId, @PathVariable String author) {
        return getRepliesByAuthor(questionId, author);
    }

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public void postReply(@PathVariable long questionId, @RequestBody AnswerModel answerModel) {
        DateTime dateTime = new DateTime();
        answerModel.setId(count);
        answerModel.setPostedDate(dateTime.toString(FORMAT));
        answerModel.setUpdatedTime(dateTime.toString(FORMAT));
        answerModel.setQuestion(questionRepository.findOne(questionId));
        answerModel.setVotes(new AnswerVoteModel(answerModel));
        answerRepository.save(answerModel);
        answerVoteRepository.save(answerRepository.findOne(count).getVotes());
        count++;
    }

    @PutMapping(value = "/{author}/{replyId}", consumes = "application/json", produces = "application/json")
    public void updateReply(@PathVariable long questionId, @PathVariable String author, @PathVariable int replyId,
                            @RequestBody AnswerModel answer) {
        List<AnswerModel> replies = getRepliesByAuthor(questionId, author);
        AnswerModel answerModel = replies.get(replyId);
        answerModel.setMessage(answer.getMessage());
        answerModel.setUpdatedTime(new DateTime().toString(FORMAT));
        answerRepository.save(answerModel);
    }

    @DeleteMapping(value = "/{author}/{replyId}")
    public void deleteReply(@PathVariable long questionId, @PathVariable String author, @PathVariable int replyId) {
        List<AnswerModel> answerModels = getRepliesByAuthor(questionId, author);
        answerRepository.delete(answerModels.get(replyId));
    }

    private List<AnswerModel> getRepliesByAuthor(long questionId, String author) {
        List<AnswerModel> collection = new ArrayList<>();
        answerRepository.findByQuestionId(questionId).stream().filter(answerModel -> answerModel.getAuthor().equals(author))
                        .forEach(collection::add);
        return collection;
    }

}
