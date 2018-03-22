import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {QuestionsComponent} from '../app/questions/questions.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {RouterTestingModule} from "@angular/router/testing";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {QuestionsService} from "../app/questions/questions.service";
import {AskingComponent} from "../app/asking/asking.component";
import {DebugElement} from "@angular/core";
import {By} from "@angular/platform-browser";
import {votes} from "../app/questions/votes";
import {Answer} from "../app/questions/answer";

describe('QuestionsComponent', () => {
  let component: QuestionsComponent;
  let fixture: ComponentFixture<QuestionsComponent>;

  let debugQuestionVotes: DebugElement;
  let debugQuestionPost: DebugElement;
  let debugAnswerSection: DebugElement;
  let htmlQuestionVotes: HTMLElement;
  let htmlQuestionPost: HTMLElement;
  let htmlAnswerSection: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestionsComponent, HeaderComponent, FooterComponent ],
      imports: [RouterTestingModule, HttpClientTestingModule],
      providers: [QuestionsService]

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();

    debugQuestionVotes = fixture.debugElement.query(By.css('#questionVotes'));
    htmlQuestionVotes = debugQuestionVotes.nativeElement;

    debugQuestionPost = fixture.debugElement.query(By.css('#questionPost'));
    htmlQuestionPost = debugQuestionPost.nativeElement;

    debugAnswerSection = fixture.debugElement.query(By.css('#answerSection'));
    htmlAnswerSection = debugAnswerSection.nativeElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should display a question votes', () => {
    expect(htmlQuestionVotes).toBeTruthy();
  });
  it('should display a question post', () => {
    expect(htmlQuestionPost).toBeTruthy();
  });
  it('should display a answer section', () => {
    expect(htmlAnswerSection).toBeTruthy();
  });


  describe('#Methods from Question', () => {

    let id = 1;
    let message = "gg";
    let upVotes = 0;
    let downVotes = 0;
    let totalVotes = 0;

    let votes: votes = {id, upVotes, downVotes, totalVotes} as votes;
    let answer: Answer = { id, message, votes } as Answer;

    it('should addAnswer', () => {
      spyOn(component, 'addAnswer');
      component.addAnswer("Testing");
      expect(component.addAnswer).toHaveBeenCalled();
    });
    it('should registerButtonClick', () => {
      spyOn(component, 'registerButtonClick');
      component.registerButtonClick();
      expect(component.registerButtonClick).toHaveBeenCalled();
    });
    it('should loginButtonClick', () => {
      spyOn(component, 'loginButtonClick');
      component.loginButtonClick();
      expect(component.loginButtonClick).toHaveBeenCalled();
    });
    it('should editQuestion', () => {
      spyOn(component, 'editQuestion');
      component.editQuestion("Testing");
      expect(component.editQuestion).toHaveBeenCalled();
    });
    it('should deleteQuestion', () => {
      spyOn(component, 'deleteQuestion');
      component.deleteQuestion();
      expect(component.deleteQuestion).toHaveBeenCalled();
    });
    it('should editAnswer', () => {
      spyOn(component, 'editAnswer');
      component.editAnswer(answer, "Testing");
      expect(component.editAnswer).toHaveBeenCalled();
    });
    it('should deleteAnswer', () => {
      spyOn(component, 'deleteAnswer');
      component.deleteAnswer(answer);
      expect(component.deleteAnswer).toHaveBeenCalled();
    });
    it('should upVoteQuestionClick', () => {
      spyOn(component, 'upVoteQuestionClick');
      component.upVoteQuestionClick();
      expect(component.upVoteQuestionClick).toHaveBeenCalled();
    });
    it('should downVoteQuestionClick', () => {
      spyOn(component, 'downVoteQuestionClick');
      component.downVoteQuestionClick();
      expect(component.downVoteQuestionClick).toHaveBeenCalled();
    });
    it('should upVoteAnswerClick', () => {
      spyOn(component, 'upVoteAnswerClick');
      component.upVoteAnswerClick(answer);
      expect(component.upVoteAnswerClick).toHaveBeenCalled();
    });
    it('should downVoteAnswerClick', () => {
      spyOn(component, 'downVoteAnswerClick');
      component.downVoteAnswerClick(answer);
      expect(component.downVoteAnswerClick).toHaveBeenCalled();
    });
    it('should chooseBestAnswer', () => {
      spyOn(component, 'chooseBestAnswer');
      component.chooseBestAnswer(answer);
      expect(component.chooseBestAnswer).toHaveBeenCalled();
    });
  });
});
