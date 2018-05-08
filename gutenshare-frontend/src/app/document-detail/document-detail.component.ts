import {Component, OnInit} from "@angular/core";
import {ApiService} from "../api/api.service";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-document-detail',
  templateUrl: './document-detail.component.html',
  styleUrls: ['./document-detail.component.scss']
})

export class DocumentDetailComponent implements OnInit {
  specificDocument;
  ratingsForDocumentDetail: any[5] = [];
  userGaveRating: boolean = false;

  constructor(private apiService: ApiService, private route: ActivatedRoute) {
  }

  defineRatingForDocumentDetail(rating: number) {
    for (var i = 1; i <= rating; i++) {
      this.ratingsForDocumentDetail[i - 1] = {
        "status": "ratingActive",
        "index": i
      };
    }

    for (var i = 5; i > (rating); i--) {
      console.log("i: " + i);
      this.ratingsForDocumentDetail[i - 1] = {
        "status": "ratingInactive",
        "index": i
      };
    }
  };

  ngOnInit() {
    this.apiService.getDocumentDetails(this.route.snapshot.params.id)
      .then(response => {
        this.specificDocument = response;
        this.defineRatingForDocumentDetail(this.specificDocument.rating);
      });
  }

  addComment(commentText: string) {
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


