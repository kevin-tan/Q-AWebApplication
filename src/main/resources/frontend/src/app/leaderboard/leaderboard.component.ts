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
  public time;


  constructor(private questionsService: QuestionsService, private router: Router) { }

  ngOnInit() {
    this.time = setTimeout(this.showPage, 5000);
    this.questionsService.getLeaderBoard().subscribe(data => this.leaderboards = data); // calls the question service to return a leaderboard
  }

  // routes the user to the chosen user's profile
  OnSelectUser(userID) {
    this.router.navigate(['profile', userID]);

  }

  showPage() {
    document.getElementById("loader").style.display = "none";
    document.getElementById("ranklist").style.display = "block";
  }

}
