import {Component, OnInit} from "@angular/core";
import {ApiService} from "../api/api.service";
import {ActivatedRoute} from "@angular/router";
import {DocumentReview} from "./document-rating";
import {DocumentComment} from "./document-comment";

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

  ngOnInit() {
    this.apiService.getDocumentDetails(this.route.snapshot.params.id)
      .then(response => {
        this.specificDocument = response;
        this.defineRatingForDocumentDetail(this.specificDocument.rating);
        // this.getDownloadLink(this.specificDocument.documentAsBytes);
      });
  }

  // getDownloadLink(documentAsBytes: Uint8Array){
  //   var reader = new FileReader();
  //
  //   reader.readAsDataURL(new Blob([documentAsBytes], {type:'application/pdf'}));
  //
  //   reader.onload = function(e) {
  //     window.open(decodeURIComponent(reader.result), '_self', '', false);
  //   }
  //
  // }
  
  defineRatingForDocumentDetail(rating: number) {
    for (var i = 1; i <= rating; i++) {
      this.ratingsForDocumentDetail[i - 1] = {
        "status": "ratingActive",
        "index": i
      };
    }

    for (var i = 5; i > (rating); i--) {
      this.ratingsForDocumentDetail[i - 1] = {
        "status": "ratingInactive",
        "index": i
      };
    }
  };

  addComment(commentText: string) {
    let documentComment = new DocumentComment();

    documentComment.documentid = this.specificDocument.id;
    documentComment.user = 'rudi';
    documentComment.content = commentText;

    this.apiService.postComment(documentComment).then(data =>
      {
        this.apiService.getDocumentDetails(this.route.snapshot.params.id)
          .then(response => {
            this.specificDocument = response;
          });
      }
    );
  }

  addRating(newRating: number) {

    let documentReview = new DocumentReview;

    documentReview.documentid = this.specificDocument.id;
    documentReview.user = 'rudi';
    documentReview.rating = newRating;

    this.apiService.postRating(documentReview).then(data =>
      {
        this.apiService.getDocumentDetails(this.route.snapshot.params.id)
          .then(response => {
            this.specificDocument = response;
            this.defineRatingForDocumentDetail(this.specificDocument.rating);
            this.userGaveRating = true;
          });
      }
    );
  }
}


