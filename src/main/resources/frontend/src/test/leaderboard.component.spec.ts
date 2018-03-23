import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderboardComponent } from '../app/leaderboard/leaderboard.component';
import { HeaderComponent } from "../app/header/header.component";
import { FooterComponent } from "../app/footer/footer.component";
import { RouterTestingModule } from "@angular/router/testing";
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { QuestionsService } from "../app/questions/questions.service";
import { userReputation } from '../app/questions/userReputation';

describe('LeaderboardComponent', () => {
  let component: LeaderboardComponent;
  let fixture: ComponentFixture<LeaderboardComponent>;
  let service: QuestionsService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [LeaderboardComponent, HeaderComponent, FooterComponent],
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers: [QuestionsService]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaderboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    service = new QuestionsService(this.http);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('#getTheLeaderBoard()', () => {

    let mockEmpty: userReputation[];
    let mockData: userReputation[] = [
      {
        "id": 1,
        "dateJoined": "Test1",
        "email": "Test1",
        "firstName": "Test1",
        "lastName": "Test1",
        "password": "Test1",
        "reputation": 1,
        "username": "Test1"
      },
      {
        "id": 2,
        "dateJoined": "Test2",
        "email": "Test2",
        "firstName": "Test2",
        "lastName": "Test2",
        "password": "Test2",
        "reputation": 2,
        "username": "Test2"
      }
    ];

    it('should return an array of UserReputation if there are users in the leaderboard', () => {
      spyOn(service, 'getLeaderBoard').and.returnValue(mockData);
      expect(component.getTheLeaderBoard()).toEqual(this.mockData);
    });

    it('should return empty array if there are no user in the leaderboard', () => {
      spyOn(service, 'getLeaderBoard').and.returnValue(mockEmpty);
      expect(component.getTheLeaderBoard()).toEqual(this.mockEmpty);
    });
  });
});
