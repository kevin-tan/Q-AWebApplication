import { Component, OnInit } from '@angular/core';
import {Question} from "./question";
import {QuestionsService} from "./questions.service";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css'],
  providers:[QuestionsService]
})
export class QuestionsComponent implements OnInit {

  questions: Array<Question>;

  constructor(private questionsService: QuestionsService) { }

  ngOnInit() {
    this.getQuestions();
  }

  getQuestions(): void {
    this.questionsService.getQuestions()
      .subscribe(questions => this.questions = questions);
  }

  add(message: string): void{
    if(!message){return;}

    const newQuestion: Question = { message } as Question;
    this.questionsService.addQuestion(newQuestion)
      .subscribe(question => this.questions.push(question));
  }

}
