import { Component, OnInit } from '@angular/core';
import { QuestionsService } from "../questions/questions.service";
import { Router } from '@angular/router'

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public questions = [];

  constructor(private questionsService: QuestionsService, private router: Router) { }

  ngOnInit() {
    this.questionsService.getQuestions().subscribe(data => this.questions = data);
  }

  OnSelect(question){
    this.router.navigate(['/dashboard/question', question.id]);
    this.questionsService.setCurrentQuestion(question);
  }

  OnSearch(searchTerm){
    this.questionsService.searchDashboard(searchTerm).subscribe(data => this.questions = data);
  }

}
