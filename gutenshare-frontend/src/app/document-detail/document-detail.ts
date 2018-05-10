import {DocumentRating} from "./document-rating";
import {DocumentComment} from "./document-comment";


export class DocumentDetail {
  id: string;
  title: string;
  user: string;
  uploadDate: string;
  description: string;
  school: string;
  departement: string;
  course: string;
  tags: string[];
  rating: number;
  comments: DocumentComment[];
}
