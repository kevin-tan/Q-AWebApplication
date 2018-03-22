import {inject, TestBed, getTestBed} from '@angular/core/testing';

import {QuestionsService} from '../app/questions/questions.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Question} from "../app/questions/question";
import {votes} from "../app/questions/votes";
import {Answer} from "../app/questions/answer";

describe('QuestionsService', () => {
  let injector: TestBed;
  let questionsService: QuestionsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [QuestionsService]
    });

    injector = getTestBed();
    questionsService = injector.get(QuestionsService);
    httpMock = injector.get(HttpTestingController);

  });

  it('should be created', inject([QuestionsService], (service: QuestionsService) => {
    expect(service).toBeTruthy();
  }));

    // the test below check to see if the component contains the required methods
    it('should have method getQuestions()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getQuestions)
      }));
    it('should have method getQuestionsWithURL()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getQuestionURL)
      }));
    it('should have method addAnswerToQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.addAnswerToQuestion)
      }));
    it('should have method editingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.editingQuestion)
      }));
    it('should have method deletingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.deletingQuestion)
      }));
    it('should have method editingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.editingAnswer)
      }));
    it('should have method deletingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.deletingAnswer)
      }));
    it('should have method searchDashboard()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.searchDashboard)
      }));
    it('should have method searchTag()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.searchTag)
      }));
    it('should have method upVotingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.upVotingAnswer)
      }));
    it('should have method downVotingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.downVotingQuestion)
      }));
    it('should have method upVotingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.upVotingAnswer)
      }));
    it('should have method downVotingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.downVotingAnswer)
      }));
    it('should have method unVotingQuestion()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.unVotingQuestion)
      }));
    it('should have method unVotingAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.unVotingAnswer)
      }));
    it('should have method getLeaderBoard()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getLeaderBoard)
      }));
    it('should have method getAnswerWithURL()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getAnswerWithURL)
      }));
    it('should have method getQuestionWithID()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.getQuestionWithID)
      }));
    it('should have method bestAnswer()',
      inject([QuestionsService], (service: QuestionsService) => {
        expect(questionsService.bestAnswer)
      }));

  describe('#getQuestions', () => {
    it('should return an observable<Question> via GET', () => {
      let id = 1;
      let message = "gg";
      const mockData: Question = {id, message} as Question;

      questionsService.getQuestions().subscribe(data => {
        expect(data[0]).toBe(this.mockData);
      });
      const request = httpMock.expectOne('http://localhost:8080/questions');
      expect(request.request.method).toBe('GET');
      request.flush(mockData);
    });
  });

  //================================================================================================================
  //Editing/Deleting Start
  //================================================================================================================

  describe('#edditingQuestion', () => {
    it('should return an observable<Question> via PUT', () => {
      let userID = 1;

      let id = 2;
      let message = "gg";

      const question: Question = {id, message} as Question;

      questionsService.editingQuestion(userID, question).subscribe(data => {
        expect(data).toBe(question);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id);
      expect(request.request.method).toBe('PUT');
      request.flush(question);
    });
  });

  describe('#deletingQuestion', () => {
    it('should return an observable<Question> via DELETE', () => {
      let userID = 1;

      let id = 1;
      let message = "gg";
      const question: Question = {id, message,} as Question;

      questionsService.deletingQuestion(userID, question).subscribe(data => {
        expect(data).toBe(question);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id);
      expect(request.request.method).toBe('DELETE');
      request.flush(question);
    });
  });

  describe('#editingAnswer', () => {
    it('should return an observable<Question> via PUT', () => {
      let userID = 1;

      let id = 2;
      let message = "gg";
      let answer: Answer = {id, message} as Answer;
      let answerModel: Array<Answer> = [answer];

      id = 1;
      message = "gg";
      const question: Question = {id, message, answerModel} as Question;

      questionsService.editingAnswer(answer, userID, question).subscribe(data => {
        expect(data).toBe(question);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies/' + answer.id);
      expect(request.request.method).toBe('PUT');
      request.flush(question);
    });
  });

  describe('#deletingAnswer', () => {
    it('should return an observable<Question> via DELETE', () => {
      let userID = 1;

      let id = 2;
      let message = "gg";
      let answer: Answer = {id, message} as Answer;
      let answerModel: Array<Answer> = [answer];

      id = 1;
      message = "gg";
      const question: Question = {id, message, answerModel} as Question;

      questionsService.deletingAnswer(answer, userID, question).subscribe(data => {
        expect(data).toBe(question);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies/' + answer.id);
      expect(request.request.method).toBe('DELETE');
      request.flush(question);
    });
  });

  describe('#addAnswerToQuestion', () => {
    it('should return an observable<Question> via POST', () => {
      let userID = 1;

      let id = 2;
      let message = "gg";
      let answer: Answer = {id, message} as Answer;
      let answerModel: Array<Answer> = [answer];

      id = 1;
      message = "gg";
      const question: Question = {id, message, answerModel} as Question;

      questionsService.addAnswerToQuestion(answer, question.id, userID).subscribe(data => {
        expect(data).toBe(question);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies');
      expect(request.request.method).toBe('POST');
      request.flush(question);
    });
  });

  //================================================================================================================
  //Editing/Deleting End
  //================================================================================================================

  //================================================================================================================
  //Voting Start
  //================================================================================================================
  describe('#upVotingQuestion', () => {
    it('should return an observable<Question> via PUT', () => {

      let userID = 1;

      let id = 1;
      let message = "gg";

      let upVotes = 0;
      let downVotes = 0;
      let totalVotes = 0;
      let votes: votes = {id, upVotes, downVotes, totalVotes} as votes;
      const question: Question = {id, message, votes} as Question;

      questionsService.upVotingQuestion(question, userID).subscribe(data => {
        expect(data.votes.upVotes+1).toEqual(question.votes.upVotes+1);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/upVote');
      expect(request.request.method).toBe('PUT');
      request.flush(question);
    });
  });
  describe('#downVotingQuestion', () => {
    it('should return an observable<Question> via PUT', () => {
      let userID = 1;

      let id = 1;
      let message = "gg";

      let upVotes = 0;
      let downVotes = 0;
      let totalVotes = 0;
      let votes: votes = {id, upVotes, downVotes, totalVotes} as votes;
      const question: Question = {id, message, votes} as Question;

      questionsService.downVotingQuestion(question, userID).subscribe(data => {
        expect(data.votes.downVotes+1).toEqual(question.votes.downVotes+1);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/downVote');
      expect(request.request.method).toBe('PUT');
      request.flush(question);
    });
  });
  describe('#upVotingAnswer', () => {
    it('should return an observable<Question> via PUT', () => {
      let userID = 1;

      let id = 2;
      let message = "gg";
      let upVotes = 0;
      let downVotes = 0;
      let totalVotes = 0;

      let votes: votes = {id, upVotes, downVotes, totalVotes} as votes;
      let answer: Answer = { id, message, votes } as Answer;
      let answerModel: Array<Answer> = [answer];

      id = 1;
      message = "gg";
      upVotes = 0;
      downVotes = 0;
      totalVotes = 0;

      votes = {id, upVotes, downVotes, totalVotes} as votes;
      const question: Question = {id, message, answerModel, votes} as Question;

      questionsService.upVotingAnswer(answer, question.id, userID).subscribe(data => {
        expect(data.votes.upVotes+1).toBe(answer.votes.upVotes+1);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies/' + answer.id + '/upVote');
      expect(request.request.method).toBe('PUT');
      request.flush(answer);
    });
  });
  describe('#downVotingAnswer', () => {
    it('should return an observable<Question> via PUT', () => {
      let userID = 1;

      let id = 2;
      let message = "gg";
      let upVotes = 0;
      let downVotes = 0;
      let totalVotes = 0;

      let votes: votes = {id, upVotes, downVotes, totalVotes} as votes;
      let answer: Answer = { id, message, votes } as Answer;
      let answerModel: Array<Answer> = [answer];

      id = 1;
      message = "gg";
      upVotes = 0;
      downVotes = 0;
      totalVotes = 0;

      votes = {id, upVotes, downVotes, totalVotes} as votes;
      const question: Question = {id, message, answerModel, votes} as Question;

      questionsService.downVotingAnswer(answer, question.id, userID).subscribe(data => {
        expect(data.votes.downVotes+1).toBe(answer.votes.downVotes+1);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies/' + answer.id + '/downVote');
      expect(request.request.method).toBe('PUT');
      request.flush(answer);
    });
  });
  describe('#unVotingQuestion', () => {
    it('should return an observable<Question> via PUT', () => {
      let userID = 1;

      let id = 1;
      let message = "gg";

      let upVotes = 0;
      let downVotes = 0;
      let totalVotes = 1;
      let votes: votes = {id, upVotes, downVotes, totalVotes} as votes;
      const question: Question = {id, message, votes} as Question;

      questionsService.unVotingQuestion(question, userID).subscribe(data => {
        expect(data.votes.totalVotes-1).toEqual(question.votes.totalVotes-1);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/unVote');
      expect(request.request.method).toBe('PUT');
      request.flush(question);
    });
  });
  describe('#unVotingAnswer', () => {
    it('should return an observable<Question> via PUT', () => {
      let userID = 1;

      let id = 2;
      let message = "gg";
      let upVotes = 0;
      let downVotes = 0;
      let totalVotes = 1;

      let votes: votes = {id, upVotes, downVotes, totalVotes} as votes;
      let answer: Answer = { id, message, votes } as Answer;
      let answerModel: Array<Answer> = [answer];

      id = 1;
      message = "gg";
      upVotes = 0;
      downVotes = 0;
      totalVotes = 0;

      votes = {id, upVotes, downVotes, totalVotes} as votes;
      const question: Question = {id, message, answerModel, votes} as Question;

      questionsService.unVotingAnswer(answer, question.id, userID).subscribe(data => {
        expect(data.votes.totalVotes-1).toBe(answer.votes.totalVotes-1);
      });
      const request = httpMock.expectOne('http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies/' + answer.id + '/unVote');
      expect(request.request.method).toBe('PUT');
      request.flush(answer);
    });
  });
  //================================================================================================================
  //Voting End
  //================================================================================================================
});
