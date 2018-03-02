import { Component, OnInit } from '@angular/core';
import {AskingService} from "./asking.service";
import {Router} from "@angular/router";
import {Question} from "../questions/question";

@Component({
  selector: 'app-asking',
  templateUrl: './asking.component.html',
  styleUrls: ['./asking.component.css']
})
export class AskingComponent implements OnInit {

  constructor(private askingService: AskingService, private router: Router) { }

  ngOnInit() {
  }

  questionTitle: string;
  message: string;
  questionCategories: Array<string>;
  acceptance: boolean;

  categories = ['Java', 'C++',
    'C#', 'Python', 'C'];

  //message, author, questionCate, QuestionTitle
  addQuestion(): void{
    let author: string = sessionStorage.getItem('username');
    let questionTitle = this.questionTitle;
    let message = this.message;
    let questionCategories = this.questionCategories;
    const newQuestion: Question = { message, author, questionCategories, questionTitle} as Question;

    this.askingService.addQuestion(newQuestion)
      .subscribe(
        () => this.router.navigate(['/dashboard'])
      );

  }
}
