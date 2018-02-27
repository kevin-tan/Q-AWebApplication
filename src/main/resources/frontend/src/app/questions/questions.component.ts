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
  userID;

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

  //Incorrect implementation
  upVoteQuestionClick(){
    this.userID = sessionStorage.getItem('id');
    this.questionsService.upVotingQuestion(this.currentQuestion, this.userID)
      .subscribe(value => this.currentQuestion.votes = value.votes);
  }

  //Incorrect implementation
  downVoteQuestionClick(){
    this.userID = sessionStorage.getItem('id');
    this.questionsService.downVotingQuestion(this.currentQuestion, this.userID)
      .subscribe(value => this.currentQuestion.votes = value.votes);
  }

  upVoteAnswerClick(answer: Answer){
    this.userID = sessionStorage.getItem('id');
    this.questionsService.upVotingAnswer(answer, this.currentQuestion.id, this.userID)
      .subscribe(value => answer.votes = value.votes);
  }

  //Incorrect implementation
  downVoteAnswerClick(answer: Answer){
    this.userID = sessionStorage.getItem('id');
    this.questionsService.downVotingAnswer(answer, this.currentQuestion.id, this.userID)
      .subscribe(value => answer.votes = value.votes);
  }

}
