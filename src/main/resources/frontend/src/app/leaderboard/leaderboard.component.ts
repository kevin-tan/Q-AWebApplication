import { Component, OnInit } from '@angular/core';
import { QuestionsService } from "../questions/questions.service";
import { Router } from '@angular/router'

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

  public leaderboards = [] // array used to store the leaderboard for display


  constructor(private questionsService: QuestionsService, private router: Router) { }

  ngOnInit() {
    this.getTheLeaderBoard()
  }

   // calls the question service to return a leaderboard
  getTheLeaderBoard() {
    this.questionsService.getLeaderBoard().subscribe(data => this.leaderboards = data);
  }

  // routes the user to the chosen user's profile
  OnSelectUser(userID) {
    this.router.navigate(['profile', userID]);

  }

}
