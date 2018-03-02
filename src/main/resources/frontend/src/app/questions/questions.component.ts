import {Component, OnInit} from '@angular/core';
import {Question} from "./question";
import {QuestionsService} from "./questions.service";
import {Answer} from "./answer";
import {ActivatedRoute, Router} from "@angular/router";
import {votes} from "./votes";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css'],
})
export class QuestionsComponent implements OnInit {

  displayAnswerBox:boolean = (sessionStorage.getItem('status') == 'true');
  currentQuestion: Question;

  constructor(private questionsService: QuestionsService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    let id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.questionsService.getQuestionWithID(id).subscribe(currentQuestion => this.currentQuestion = currentQuestion);
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

  upVoteQuestionClick() {
    let userID = sessionStorage.getItem('id');
    let initialVotes: votes = this.currentQuestion.votes;

    this.questionsService.upVotingQuestion(this.currentQuestion, userID)
      .subscribe(value => {
        if (value.votes.upVotes == initialVotes.upVotes) {
          this.questionsService.unVotingQuestion(this.currentQuestion, userID)
            .subscribe(value => this.currentQuestion.votes = value.votes);
        } else {
          this.currentQuestion.votes = value.votes;
        }
      });
  }

  downVoteQuestionClick(){
    let userID = sessionStorage.getItem('id');
    let initialVotes: votes = this.currentQuestion.votes;

    this.questionsService.downVotingQuestion(this.currentQuestion, userID)
      .subscribe(value => {
        if (value.votes.downVotes == initialVotes.downVotes) {
          this.questionsService.unVotingQuestion(this.currentQuestion, userID)
            .subscribe(value => this.currentQuestion.votes = value.votes);
        } else {
          this.currentQuestion.votes = value.votes;
        }
      });
  }

  upVoteAnswerClick(answer: Answer){
    let userID = sessionStorage.getItem('id');
    let initialVotes = answer.votes;
    this.questionsService.upVotingAnswer(answer, this.currentQuestion.id, userID)
      .subscribe(value => {
        if(initialVotes.upVotes == value.votes.upVotes){
          this.questionsService.unVotingAnswer(answer, this.currentQuestion.id, userID)
            .subscribe(value => answer.votes = value.votes);
        }else{
          answer.votes = value.votes;
        }
      });
  }

  downVoteAnswerClick(answer: Answer){
    let userID = sessionStorage.getItem('id');
    let initialVotes = answer.votes;
    this.questionsService.downVotingAnswer(answer, this.currentQuestion.id, userID)
      .subscribe(value => {
        if(initialVotes.downVotes == value.votes.downVotes){
          this.questionsService.unVotingAnswer(answer, this.currentQuestion.id, userID)
            .subscribe(value => answer.votes = value.votes);
        }else{
          answer.votes = value.votes;
        }
      });
  }

}
