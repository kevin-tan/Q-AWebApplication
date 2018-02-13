import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {Question} from "./question";

@Injectable()
export class QuestionsService {

  getQuestionURL = 'http://localhost:8080/questions';

  addQuestionURL = 'http://localhost:8080/user/1/questions';

  constructor(private http: HttpClient) { }

  //GET
  getQuestions(): Observable<Question[]>{
    return this.http.get<Question[]>(this.getQuestionURL);
  }
  //searchBasedOnID();
  //searchQuestionByUser()

  //POST
  addQuestion(question: Question): Observable<Question>{
    return this.http.post<Question>(this.addQuestionURL, question);
  }

  //PUT

  //DELETE
}
