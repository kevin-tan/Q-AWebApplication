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
  public tags = ["Category 1","Category 2","Category 3","Category 4","Category 5","Category 6","Category 7"];

  constructor(private questionsService: QuestionsService, private router: Router) { }

  ngOnInit() {
    this.questionsService.getQuestions().subscribe(data => this.questions = data);
  }

  OnSelectQuestion(question){
    this.router.navigate(['/dashboard/question', question.id]);
  }

  OnSelectAsking(){
    this.router.navigate(['/dashboard/asking']);
  }

  OnSearch(searchTerm){
    this.questionsService.searchDashboard(searchTerm).subscribe(data => this.questions = data);
  }

  TagSearch(tag) {
    this.questionsService.searchTag(tag).subscribe(data => this.questions = data);
  }

}
