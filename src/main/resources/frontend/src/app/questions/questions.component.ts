import {Component, OnInit} from '@angular/core';
import {Question} from "./question";
import {QuestionsService} from "./questions.service";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css'],
})
export class QuestionsComponent implements OnInit {

  currentQuestion: Question;

  constructor(private questionsService: QuestionsService) { }

  ngOnInit() {
    this.questionsService.currentQuestion.subscribe(currentQuestion => this.currentQuestion = currentQuestion)
  }

  addAnswer(message: string): void{
    if(!message){return;}

    const newQuestion: Question = { message } as Question;
    this.questionsService.addQuestion(newQuestion)
      .subscribe(question => this.questions.push(question));
  }

}
