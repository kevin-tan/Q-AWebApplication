import { Component, OnInit } from '@angular/core';
import { QuestionsService } from "../questions/questions.service";
import { Router } from '@angular/router'

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

  public leaderboards = []


  constructor(private questionsService: QuestionsService, private router: Router) { }

  ngOnInit() {
    this.questionsService.getLeaderBoard().subscribe(data => this.leaderboards = data);
  }

  OnSelectUser(userID) {
    this.router.navigate(['profile', userID]);
  }

}
