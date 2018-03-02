import {Answer} from "./answer";
import {votes} from "./votes";

export interface Question {
  id: number;
  updatedTime: string;
  postedDate: string;
  message: string;
  author: string;
  votes: votes;
  answerModel: Array<Answer>;
  questionCategory: Array<string>;
  questionTitle: string,
  bestAnswer: number
}

