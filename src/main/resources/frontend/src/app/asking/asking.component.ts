import { Component, OnInit } from '@angular/core';
import {Answer} from "../questions/answer";
import {AskingService} from "./asking.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-asking',
  templateUrl: './asking.component.html',
  styleUrls: ['./asking.component.css']
})
export class AskingComponent implements OnInit {

  constructor(private askingService: AskingService, private router: Router) { }

  ngOnInit() {
  }
  /*
  addQuestion(message: string): void{
    if(!message){return;}

    const newAnswer: Answer = { message } as Answer;
    this.questionsService.addAnswerToQuestion(newAnswer, this.currentQuestion.id)
      .subscribe(answer => this.currentQuestion.answerModel.push(answer));
  }*/
}
