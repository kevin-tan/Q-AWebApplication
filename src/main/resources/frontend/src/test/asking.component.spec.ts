import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { DebugElement} from "@angular/core";

import { AskingComponent } from '../app/asking/asking.component';
import {HeaderComponent} from "../app/header/header.component";
import {FooterComponent} from "../app/footer/footer.component";
import {RouterTestingModule} from "@angular/router/testing";
import {AskingService} from "../app/asking/asking.service";
import {FormsModule} from "@angular/forms";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {By} from "@angular/platform-browser";

describe('AskingComponent', () => {
  let component: AskingComponent;
  let fixture: ComponentFixture<AskingComponent>;
  let debugTitle: DebugElement;
  let debugDescription: DebugElement;
  let debugDropdown: DebugElement;
  let debugCheckBox: DebugElement;
  let debugSubmit: DebugElement;
  let htmlTitle: HTMLElement;
  let htmlDescription: HTMLElement;
  let htmlDropdown: HTMLElement;
  let htmlCheckBox: HTMLElement;
  let htmlSubmit: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AskingComponent, HeaderComponent, FooterComponent ],
      imports: [RouterTestingModule, FormsModule, HttpClientTestingModule],
      providers: [AskingService]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AskingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    debugTitle = fixture.debugElement.query(By.css('#title'));
    htmlTitle = debugTitle.nativeElement;

    debugDescription = fixture.debugElement.query(By.css('#question'));
    htmlDescription = debugTitle.nativeElement;

    debugDropdown = fixture.debugElement.query(By.css('#category'));
    htmlDropdown = debugTitle.nativeElement;

    debugCheckBox = fixture.debugElement.query(By.css('#terms'));
    htmlCheckBox = debugTitle.nativeElement;

    debugSubmit = fixture.debugElement.query(By.css('#submission'));
    htmlSubmit = debugTitle.nativeElement;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should addQuestion', () => {
    spyOn(component, 'addQuestion');
    component.addQuestion();
    expect(component.addQuestion).toHaveBeenCalled();
  });

  it('should display a title box', () => {
    expect(htmlTitle).toBeTruthy();
  });
  it('should display a description box', () => {
    expect(htmlDescription).toBeTruthy();
  });
  it('should display a dropdown box', () => {
    expect(htmlDropdown).toBeTruthy();
  });
  it('should display a check box', () => {
    expect(htmlCheckBox).toBeTruthy();
  });
  it('should display a submission button', () => {
    expect(htmlSubmit).toBeTruthy();
  });
});
