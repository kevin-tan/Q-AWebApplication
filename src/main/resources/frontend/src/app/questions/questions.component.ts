import {Component, OnInit} from '@angular/core';
import {Question} from "./question";
import {QuestionsService} from "./questions.service";
import {Answer} from "./answer";
import {ActivatedRoute, Router} from "@angular/router";
import {votes} from "./votes";
import {UserProfileService} from "../user-profile/user-profile.service";
import {User} from "../user-profile/user";

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css'],
})
export class QuestionsComponent implements OnInit {

<<<<<<< userRouting+bestanswer
  public displayAnswerBox: boolean = (sessionStorage.getItem('status') == 'true');
  public currentQuestion: Question;
  public currentQuestionPosterId: number;
  public currentPoster: boolean = false;
  public id: number;
  public currentUserID = sessionStorage.getItem('id');
=======
  displayAnswerBox:boolean = (sessionStorage.getItem('status') == 'true');
  currentQuestion: Question;
  currentUser: string;
  editing: Number = 0;
>>>>>>> master

  constructor(private questionsService: QuestionsService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
<<<<<<< userRouting+bestanswer

    console.log(this.currentUserID);
        
    this.id = parseInt(this.route.snapshot.paramMap.get('id'));
    this.questionsService.getQuestionWithID(this.id).subscribe(currentQuestion => this.currentQuestion = currentQuestion);
    this.questionsService.getQuestionWithID(this.id).subscribe(currentQuestion => {if(currentQuestion.userId ==  parseInt(sessionStorage.getItem('id'))) {
      this.currentPoster = true;
      }});
     
    console.log(this.currentQuestionPosterId);

    if (this.currentQuestionPosterId ==  parseInt(sessionStorage.getItem('id'))) {
    this.currentPoster = true;
    }

=======
    let id = parseInt(this.route.snapshot.paramMap.get('id'));
    if(sessionStorage.getItem('id') != null){
      this.currentUser = sessionStorage.getItem('username');
    }
    this.questionsService.getQuestionWithID(id).subscribe(currentQuestion => this.currentQuestion = currentQuestion);
>>>>>>> master
  }

  addAnswer(message: string): void{
    if(!message){return;}

    const newAnswer: Answer = { message } as Answer;
    let userID = parseInt(sessionStorage.getItem('id'));
    this.questionsService.addAnswerToQuestion(newAnswer, this.currentQuestion.id, userID)
      .subscribe(answer => this.currentQuestion.answerModel.push(answer));
  }

  registerButtonClick(){
    this.router.navigate(['/register']);
  }

  loginButtonClick(){
    this.router.navigate(['/login']);
  }

  editQuestion(editedQuestion: string){
    let userID = parseInt(sessionStorage.getItem('id'));
    this.currentQuestion.message = editedQuestion;
    this.questionsService.editingQuestion(userID, this.currentQuestion)
      .subscribe();
    this.editing = 0;
  }


  deleteQuestion(){
    let userID = parseInt(sessionStorage.getItem('id'));
    this.questionsService.deletingQuestion(userID, this.currentQuestion)
      .subscribe(null, null, ()=> {
        this.router.navigate(['/dashboard'])
      });
  }

  editAnswer(answer: Answer, editedAnswer: string){
    let userID = parseInt(sessionStorage.getItem('id'));
    answer.message = editedAnswer;
    this.questionsService.editingAnswer(answer, userID, this.currentQuestion)
      .subscribe();
    this.editing = 0;
  }


  deleteAnswer(answer: Answer){
    let userID = parseInt(sessionStorage.getItem('id'));

    this.questionsService.deletingAnswer(answer, userID, this.currentQuestion)
      .subscribe(null, null, ()=> {
        location.reload();
      });
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

  chooseBestAnswer(answer: Answer) {
    this.questionsService.bestAnswer(answer, this.id, this.currentUserID).subscribe(answer => answer = answer);
  }

}
