import { Injectable } from '@angular/core';
import {Answer} from "../questions/answer";
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class AskingService {

  constructor(private http: HttpClient) { }///user/{userId}/questions

/*
  //message, author, questionCate, QuestionTitle
  addQuestion(answer: Answer, questionID: Number): Observable<Answer>{
    this.postAnswerURL = 'http://localhost:8080/user/' + sessionStorage.getItem('id') + '/questions/' + questionID + '/replies';
    return this.http.post<Answer>(this.postAnswerURL, answer);
  }*/
}
