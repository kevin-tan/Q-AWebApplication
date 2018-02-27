import {Component, OnInit} from '@angular/core';
import {Question} from "./question";
import {QuestionsService} from "./questions.service";
import {Answer} from "./answer";
import {Router} from "@angular/router";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css'],
})
export class QuestionsComponent implements OnInit {

  displayAnswerBox:boolean = (sessionStorage.getItem('status') == 'true');
  currentQuestion: Question;

  constructor(private questionsService: QuestionsService, private router: Router) { }

  ngOnInit() {
    this.questionsService.currentQuestion.subscribe(currentQuestion => this.currentQuestion = currentQuestion)
  }

  addAnswer(message: string): void{
    if(!message){return;}

    const newAnswer: Answer = { message } as Answer;
    this.questionsService.addAnswerToQuestion(newAnswer, this.currentQuestion.id)
      .subscribe(answer => this.currentQuestion.answerModel.push(answer));
  }

  registerButtonClick(){
    this.router.navigate(['/register']);
  }

  loginButtonClick(){
    this.router.navigate(['/login']);
  }

}
