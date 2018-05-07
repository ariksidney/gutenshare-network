import {Component, OnInit} from "@angular/core";
import { SPECIFICDOCUMENT } from '../mock-data/mock-data';


@Component({
  selector: 'app-document-detail',
  templateUrl: './document-detail.component.html',
  styleUrls: ['./document-detail.component.scss']
})

export class DocumentDetailComponent implements OnInit {
  specificDocument: any = SPECIFICDOCUMENT;
  ratingsForDocumentDetail: any[5] = [];
  userGaveRating: boolean = false;

  defineRatingForDocumentDetail(rating: number) {
    for (var i = 1; i <= rating; i++) {
      this.ratingsForDocumentDetail[i-1] = {
        "status": "ratingActive",
        "index": i
      };
    }

    for (var i = 5; i > (rating); i--) {
      console.log("i: "+ i);
      this.ratingsForDocumentDetail[i-1] = {
        "status": "ratingInactive",
        "index": i
      };
    }
  };

  ngOnInit() {
    this.defineRatingForDocumentDetail(this.specificDocument.rating);
  }

  addComment(commentText: string)
  {
    this.specificDocument.comments.push(
      {
        "user": "Arik",
        "content": commentText
      }
    );
  }

  calculateNewRating(newRating: number) {
    this.defineRatingForDocumentDetail(newRating);
    this.userGaveRating = true;
  }

}


