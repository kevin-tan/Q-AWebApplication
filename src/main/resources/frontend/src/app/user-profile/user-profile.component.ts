import { Component, OnInit } from '@angular/core';
import {UserProfileService} from "./user-profile.service";
import {User} from "./user";
import {QuestionsService} from "../questions/questions.service";
import {Router, ActivatedRoute} from "@angular/router";
import {Question} from "../questions/question";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  public user: User;
  public questions =[];
  public answers =[];
  public question: Question;

  edit: boolean = false;
  changePass; isLogged: boolean;
  loggedID; routeID: string;
  oldPassword; newPassword; newPasswordConfirm: string;
  errorUser: boolean = false;
  errorEmail: boolean = false;
  errorPassword: boolean;

  constructor(private userService: UserProfileService, private questionsService: QuestionsService, private router: Router, private route: ActivatedRoute) {
    route.params.subscribe(value => {this.update();});
  }
  ngOnInit() {}
  update(){
    this.loggedID = sessionStorage.getItem('id');
    this.routeID = this.route.snapshot.paramMap.get('userID');
    const url = 'http://localhost:8080/users/' + this.routeID;
    this.userService.getUserByID(this.routeID).subscribe(user => {
      this.user = user;
      console.log(user);
      if(this.loggedID==this.routeID)
        this.isLogged= true;
      this.questionsService.getQuestionsWithURL(url+'/questions').subscribe(question =>this.questions = question);
      this.questionsService.getAnswerWithURL(url+'/replies').subscribe(answer => this.answers = answer);
    });

  }
  OnSelectQuestion(question){
    this.router.navigate(['/dashboard/question', question.id]);
  }
  OnSelectReply(answer) {
    this.router.navigate(['/dashboard/question', answer.questionModelId]);
  }
  onEdit(){
    this.edit = true;
  }
  userInfoMenu(){
    this.edit = false;
    this.changePass = false;
  }
  popInput(){
    this.changePass = true;
  }
  save(){
    let username = this.user.username;
    let email = this.user.email;
    let firstName = this.user.firstName;
    let lastName = this.user.lastName;
    const user: User = {username, email, firstName, lastName} as User;

    this.userService.changeUserInfo(this.loggedID, user).subscribe();
    this.userInfoMenu();
  }

  changePassword(){
    let validate = this.oldPassword;
    let password = this.newPassword;
    let confirmPass = this.newPasswordConfirm;
    const user: User ={validate, password} as User;

    this.userService.changePassword(this.loggedID, user).subscribe(()=>{
      console.log(user);
    });
    if((password == confirmPass) && (password == '')){
      this.errorPassword = false;
      this.userInfoMenu();
      console.log(user);
    }
    else{
      this.errorPassword = true;
    }
  }
}
