export interface Question {
  id: number;
  updatedTime: string;
  postedDate: string;
  message: string;
  author: string;
  votes: {
    id: number;
    upVotes: number;
    downVotes: number;
    totalVotes: number;
  },
  answerModel: Array<any>;
  questionTitle: string,
  bestAnswer: number
}

