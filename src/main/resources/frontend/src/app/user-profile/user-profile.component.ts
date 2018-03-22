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
  selected; isLogged: boolean;
  selectInput: string ='';
  loggedID; routeID: string;
  passwordData; data: string;

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
    this.selected = false;
    this.selectInput ='';
  }
  popInput(select: string){
    this.selectInput = select;
    this.selected = true;
    console.log(this.selectInput);
  }
  save(){
    let username = this.user.username;
    let email = this.user.email;
    let firstName = this.user.firstName;
    let lastName = this.user.lastName;

    this.userService.changeUsername(this.loggedID,this.user.username).subscribe();

    if(this.selectInput =='Username'){
      username = this.data;
      const user: User ={username, email, firstName, lastName} as User;
      this.userService.changeUserInfo(this.loggedID,user).subscribe();
    }
    else if(this.selectInput =='Email'){
      email = this.data;
      const user: User ={username, email, firstName, lastName} as User;
      this.userService.changeUserInfo(this.loggedID,user).subscribe();
    }
    else if(this.selectInput =='Name'){
      let firstName = this.user.firstName;
      let lastName = this.user.lastName;
      const user: User ={username, email, firstName, lastName} as User;
      this.userService.changeUserInfo(this.loggedID,user).subscribe();
    }
    else if(this.selectInput =='Password'){

    }
    this.userInfoMenu();
  }
  getUserInput(usernameEdit: string){
    console.log(usernameEdit);
  }
}
