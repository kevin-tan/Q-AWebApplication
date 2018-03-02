import {votes} from "./votes";

export interface Answer {
  id: number;
  updatedTime: string;
  postedDate: string;
  message: string;
  author: string;
  votes: votes;
}
