import { Component, OnInit } from '@angular/core';
import {Answer} from "../questions/answer";
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

  //message, author, questionCate, QuestionTitle
  addQuestion(message: string, questionCategory: string, questionTitle: string): void{
    let author: string = sessionStorage.getItem('username');
    const newQuestion: Question = { message, author, questionTitle} as Question;

    this.askingService.addQuestion(newQuestion)
      .subscribe(
        () => this.router.navigate(['/dashboard'])
      );

  }
}
