import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Question } from "./question";
import { Observable } from 'rxjs/Observable';
import { Answer } from "./answer";
import { userReputation } from "./userReputation";
import { User } from "../user-profile/user";

@Injectable()
export class QuestionsService {

  getQuestionURL: string;
  getSearchURL: string;
  getTagSearchURL: string;
  getUpvoteQuestionURL: string;
  getDownvoteQuestionURL: string;
  getUpvoteAnswerURL: string;
  getDownvoteAnswerURL: string;
  postAnswerURL: string;
  getLeaderboardURL: string
  putQuestionURL: string;
  deleteQuestionURL: string;
  putAnswerURL: string;
  deleteAnswerURL: string;
  bestAnswerURL: string;
  leaderBoardHighestQuestion: string;

  constructor(private http: HttpClient) { }

  // querys the server to return an array of all questions
  getQuestions(): Observable<Question[]> {
    this.getQuestionURL = 'http://localhost:8080/questions';
    return this.http.get<Question[]>(this.getQuestionURL);
  }

  // querys the server to return an array of questions, via the passed URL
  getQuestionsWithURL(URL): Observable<Question[]> {
    return this.http.get<Question[]>(URL);
  }

  addAnswerToQuestion(answer: Answer, questionID: Number, userID: Number): Observable<Answer> {
    this.postAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + questionID + '/replies';
    return this.http.post<Answer>(this.postAnswerURL, answer);
  }

  editingQuestion(userID: Number, question: Question): Observable<Question> {
    this.putQuestionURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id;
    return this.http.put<Question>(this.putQuestionURL, question);
  }

  deletingQuestion(userID: Number, question: Question): Observable<{}> {
    this.deleteQuestionURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id;
    return this.http.delete(this.deleteQuestionURL);
  }

  editingAnswer(answer: Answer, userID: Number, question: Question) {
    this.putAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies/' + answer.id;
    return this.http.put<Question>(this.putAnswerURL, answer);
  }

  deletingAnswer(answer: Answer, userID: Number, question: Question) {
    this.deleteAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id + '/replies/' + answer.id;
    return this.http.delete(this.deleteAnswerURL);
  }

  // querys the server to return an array of questions matching the given search term via title
  searchDashboard(searchTerm) {
    if (searchTerm == "") {
      this.getSearchURL = 'http://localhost:8080/questions';
      return this.getQuestionsWithURL(this.getSearchURL);
    }

    this.getSearchURL = 'http://localhost:8080/questions/searchByTitle/' + searchTerm;
    return this.getQuestionsWithURL(this.getSearchURL);
  }

  // querys the server to return an array of questions matching the given tag attribute
  searchTag(tag) {
    this.getTagSearchURL = 'http://localhost:8080/questions/searchByCategory/' + tag;
    return this.getQuestionsWithURL(this.getTagSearchURL);
  }

  upVotingQuestion(question: Question, userID): Observable<Question> {
    this.getUpvoteQuestionURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id + '/upVote';
    return this.http.put<Question>(this.getUpvoteQuestionURL, question);
  }

  downVotingQuestion(question: Question, userID): Observable<Question> {
    this.getDownvoteQuestionURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id + '/downVote';
    return this.http.put<Question>(this.getDownvoteQuestionURL, question);
  }

  upVotingAnswer(answer: Answer, questionID, userID): Observable<Answer> {
    this.getUpvoteAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + questionID + '/replies/' + answer.id + '/upVote';
    return this.http.put<Answer>(this.getUpvoteAnswerURL, answer);
  }

  downVotingAnswer(answer: Answer, questionID, userID): Observable<Answer> {
    this.getDownvoteAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + questionID + '/replies/' + answer.id + '/downVote';
    return this.http.put<Answer>(this.getDownvoteAnswerURL, answer);
  }

  unVotingQuestion(question: Question, userID): Observable<Question> {
    this.getDownvoteQuestionURL = 'http://localhost:8080/user/' + userID + '/questions/' + question.id + '/unVote';
    return this.http.put<Question>(this.getDownvoteQuestionURL, question);
  }

  unVotingAnswer(answer: Answer, questionID, userID): Observable<Answer> {
    this.getUpvoteAnswerURL = 'http://localhost:8080/user/' + userID + '/questions/' + questionID + '/replies/' + answer.id + '/unVote';
    return this.http.put<Answer>(this.getUpvoteAnswerURL, answer);
  }

  // querys the server to return an array of user's for diaplay on the leaderboards
  getLeaderBoard(): Observable<userReputation[]> {
    this.getLeaderboardURL = 'http://localhost:8080/leaderboard';
    return this.http.get<userReputation[]>(this.getLeaderboardURL);
  }

  // querys the server to return a question with a given URL
  getAnswerWithURL(URL): Observable<Answer[]> {
    return this.http.get<Answer[]>(URL);
  }

  // querys the server to return a question with a given id
  getQuestionWithID(id): Observable<Question> {
    this.getQuestionURL = 'http://localhost:8080/questions/' + id;
    return this.http.get<Question>(this.getQuestionURL);
  }

  // querys the server to change the bestAsnwer attribute of the passed question to the passed answer's id
  bestAnswer(answer: Answer, questionID, userID) {
    this.bestAnswerURL = 'http://localhost:8080/users/' + userID + '/questions/' + questionID + '/bestAnswer/' + answer.id;
    return this.http.put<Answer>(this.bestAnswerURL, answer.id);
  }

  getHighestVotedQuestion(leaderUserId): Observable<Question> {
    this.leaderBoardHighestQuestion = 'http://localhost:8080/users/' + leaderUserId + '/highestRatedQuestion';
    return this.http.get<Question>(this.leaderBoardHighestQuestion);
  }
}
