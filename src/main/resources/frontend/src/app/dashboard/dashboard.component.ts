import { Component, OnInit } from '@angular/core';
import { QuestionsService } from "../questions/questions.service";
import { Router } from '@angular/router'

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public questions = []; // array to store all questions for display on the dashboard

  constructor(private questionsService: QuestionsService, private router: Router) { }

  ngOnInit() {
    this.questionsService.getQuestions().subscribe(data => this.questions = data); // calls the question service to retrieve all questions from the server
  }

  // calls the question service to retrieve all questions from the server
  getAllQuestions() {
    this.questionsService.getQuestions().subscribe(data => this.questions = data); 
  }

  // routes the user to the chosen question's display page
  OnSelectQuestion(question){
    this.router.navigate(['/dashboard/question', question.id]);
  }

  // routes the user to the asking page
  OnSelectAsking(){
    this.router.navigate(['/dashboard/asking']);
  }

  // routes the user to the chosen user's profile
  OnSelectUser(userID){
    this.router.navigate(['profile',userID]);
  }

  // calls the question service to return all questions that match the search term
  OnSearch(searchTerm){
    this.questionsService.searchDashboard(searchTerm).subscribe(data => this.questions = data);
  }

  // calls the question service to return all questions that match the question tag
  TagSearch(tag) {
    this.questionsService.searchTag(tag).subscribe(data => this.questions = data);
  }

}
