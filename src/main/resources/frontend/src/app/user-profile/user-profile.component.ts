import { Component, OnInit } from '@angular/core';
import {UserProfileService} from "./user-profile.service";
import {User} from "./user";
import {QuestionsService} from "../questions/questions.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Question} from "../questions/question";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  public user : User;
  public questions =[];
  public answers =[];
  public question :Question;
  url: string = 'http://localhost:8080/users/';
  IDurl: string;
  userID;
  profileID;

  constructor(private userService: UserProfileService, private questionsService: QuestionsService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.profileID = sessionStorage.getItem('id');
    this.userID = parseInt(this.route.snapshot.paramMap.get('id'));
    this.IDurl = this.url+this.userID;

    this.userService.getUser(this.IDurl).subscribe(user => this.user = user);
    this.questionsService.getQuestionsWithURL(this.IDurl+'/questions').subscribe(question =>this.questions = question);
    this.questionsService.getAnswerWithURL(this.IDurl+'/replies').subscribe(answer => this.answers = answer);
  }
  OnSelectQuestion(question){
    this.router.navigate(['/dashboard/question', question.id]);
  }
  OnSelectReply(answer) {
    this.router.navigate(['/dashboard/question', answer.questionModelId]);
  }
}
