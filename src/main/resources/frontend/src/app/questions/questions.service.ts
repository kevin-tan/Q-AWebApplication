import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Question} from "./question";
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Answer} from "./answer";

@Injectable()
export class QuestionsService {

    getQuestionURL = 'http://localhost:8080/questions';
    getSearchURL;
    getTagSearchURL;
    getUpvoteQuestionURL;
    getDownvoteQuestionURL;
    getUpvoteAnswerURL;
    getDownvoteAnswerURL;
    postAnswerURL: string = null;


    //addQuestionURL = 'http://localhost:8080/user/1/questions';

    private sourceQuestion = new BehaviorSubject<Question>(null);
    currentQuestion = this.sourceQuestion.asObservable();

    constructor(private http: HttpClient) {}

    getQuestions(): Observable<Question[]> {
        return this.http.get<Question[]>(this.getQuestionURL);
    }

    getQuestionsWithURL(URL): Observable<Question[]> {
      return this.http.get<Question[]>(URL);
  }

    setCurrentQuestion(question: Question){
      this.sourceQuestion.next(question);
    }

    addAnswerToQuestion(answer: Answer, questionID: Number): Observable<Answer>{
      this.postAnswerURL = 'http://localhost:8080/user/' + sessionStorage.getItem('id') + '/questions/' + questionID + '/replies';
      return this.http.post<Answer>(this.postAnswerURL, answer);
    }

    searchDashboard(searchTerm) {
      this.getSearchURL = 'http://localhost:8080/questions/search/' + searchTerm;
      return this.getQuestionsWithURL(this.getSearchURL);
    }

    searchTag(tag) {
      this.getTagSearchURL = 'http://localhost:8080/questions/search/' + tag;
      return this.getQuestionsWithURL(this.getTagSearchURL);
    }

    upVotingQuestion(question: Question, userID): Observable<Question>{
      this.getUpvoteQuestionURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id + '/upVote';
      return this.http.put<Question>(this.getUpvoteQuestionURL, question);
    }

    downVotingQuestion(question: Question, userID): Observable<Question>{
      this.getDownvoteQuestionURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id+ '/downVote';
      return this.http.put<Question>(this.getDownvoteQuestionURL, question);
    }

    upVotingAnswer(answer: Answer, questionID, userID): Observable<Answer>{
      this.getUpvoteAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + questionID + '/replies/' + answer.id + '/upVote';
      return this.http.put<Answer>(this.getUpvoteAnswerURL, answer);
    }

    downVotingAnswer(answer: Answer, questionID, userID): Observable<Answer>{
      this.getDownvoteAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + questionID + '/replies/' + answer.id + '/downVote';
      return this.http.put<Answer>(this.getDownvoteAnswerURL, answer);
    }
}
