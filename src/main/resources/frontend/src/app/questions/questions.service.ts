import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Question} from "./question";
import {Observable} from 'rxjs/Observable';
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Injectable()
export class QuestionsService {

    getQuestionURL = 'http://localhost:8080/questions';
    addQuestionURL = 'http://localhost:8080/user/1/questions';

    private sourceQuestion = new BehaviorSubject<Question>(null);
    currentQuestion = this.sourceQuestion.asObservable();

    constructor(private http: HttpClient) {}

    getQuestions(): Observable<Question[]> {
        return this.http.get<Question[]>(this.getQuestionURL);
    }

    setCurrentQuestion(question: Question){
      this.sourceQuestion.next(question);
    }

    addAnswer(answer: String){

    }
}
