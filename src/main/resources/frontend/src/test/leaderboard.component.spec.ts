import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderboardComponent } from '../app/leaderboard/leaderboard.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {QuestionsService} from "../app/questions/questions.service";

describe('LeaderboardComponent', () => {
  let component: LeaderboardComponent;
  let fixture: ComponentFixture<LeaderboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaderboardComponent, HeaderComponent, FooterComponent ],
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers: [QuestionsService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaderboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
