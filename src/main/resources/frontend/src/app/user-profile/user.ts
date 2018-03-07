import {Question} from "../questions/question";
import {Answer} from "../questions/answer";
import {Role} from "./role";

export interface User {
  id: number;
  dateJoined: string;
  reputation: number;
  questionModels: Array<Question>;
  answerModels: Array<Answer>;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  roleModel: Array<Role>;
  securityQuestion: string;
  securityAnswer: string;
}
