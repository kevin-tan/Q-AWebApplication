export interface Answer {
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
  }
}
