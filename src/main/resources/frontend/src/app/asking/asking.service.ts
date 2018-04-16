import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient} from "@angular/common/http";
import {Question} from "../questions/question";

@Injectable()
export class AskingService {

  constructor(private http: HttpClient) { }

  postQuestionURL: string;

  addQuestion(question: Question): Observable<Question>{
    this.postQuestionURL = 'http://localhost:8080/user/' + sessionStorage.getItem('id') + '/questions';
    return this.http.post<Question>(this.postQuestionURL, question);
  }
}
