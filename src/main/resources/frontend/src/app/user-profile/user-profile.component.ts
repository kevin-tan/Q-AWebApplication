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
  public linkedUserID: String;
  isLogged: boolean;
  edit: boolean = false;
  selectInput: string ='';
  selected: boolean;
  profileID: string;
  data; data1 : string;
  private sub: any;
  url: string = 'http://localhost:8080/users/'+sessionStorage.getItem('id');
  constructor(private userService: UserProfileService, private questionsService: QuestionsService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.update();
    console.log('test');
  }
  update(){
    this.isLogged = new Boolean(sessionStorage.getItem('status')).valueOf();
    this.profileID = sessionStorage.getItem('id');
    this.linkedUserID = this.route.snapshot.paramMap.get('userID');


    // this.sub = this.route.params.subscribe(params =>{
    //   if(params['data'] != this.profileID){
    //     this.linkedUserID = params['data'];
    //     }
    //   else {
    //     this.linkedUserID = this.profileID;
    //   }
    // });

      this.url = 'http://localhost:8080/users/' + this.linkedUserID;


    if(this.profileID == this.linkedUserID){
    }
    this.userService.getUser(this.url).subscribe(user => {this.user = user;
      console.log(this.user);});
    this.questionsService.getQuestionsWithURL(this.url+'/questions').subscribe(question =>this.questions = question);
    this.questionsService.getAnswerWithURL(this.url+'/replies').subscribe(answer => this.answers = answer);
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

    if(this.selectInput =='Username'){
      username = this.data;
      const user: User ={username, email, firstName, lastName} as User;
      this.userService.changeUserInfo(this.profileID,user).subscribe();
    }
    else if(this.selectInput =='Email'){
      email = this.data;
      const user: User ={username, email, firstName, lastName} as User;
      this.userService.changeUserInfo(this.profileID,user).subscribe();
    }
    else if(this.selectInput =='Name'){
      let firstName = this.user.firstName;
      let lastName = this.user.lastName;
      const user: User ={username, email, firstName, lastName} as User;
      this.userService.changeUserInfo(this.profileID,user).subscribe();
    }
    else if(this.selectInput =='Password'){

    }
    this.userInfoMenu();
  }
  getUserInput(usernameEdit: string){
    console.log(usernameEdit);
  }
}
