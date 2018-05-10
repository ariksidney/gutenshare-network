import {Component, OnInit} from "@angular/core";
import {ApiService} from "../api/api.service";
import {ActivatedRoute} from "@angular/router";
import {DocumentRating} from "./document-rating";
import {DocumentComment} from "./document-comment";
import {DocumentDetail} from "./document-detail";

@Component({
  selector: 'app-document-detail',
  templateUrl: './document-detail.component.html',
  styleUrls: ['./document-detail.component.scss']
})


export class DocumentDetailComponent implements OnInit {
  response;
  specificDocument = new DocumentDetail();
  ratingsForDocumentDetail: any[5] = [];
  userGaveRating: boolean = false;

  constructor(private apiService: ApiService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.apiService.getDocumentDetails(this.route.snapshot.params.id)
      .then(response => {
        this.response = response;

        this.mapResponseToSpecificDocument(response);

        console.log(response);

        this.defineRatingForDocumentDetail(this.specificDocument.rating);
      });
  }

  addComment(commentText: string) {
    let documentComment = new DocumentComment();
    documentComment.documentid = this.specificDocument.id;
    documentComment.user = 'rudi';
    documentComment.content = commentText;

    this.apiService.postComment(documentComment).then(data => {
        this.apiService.getDocumentDetails(this.route.snapshot.params.id)
          .then(response => {
            this.mapResponseToSpecificDocument(response);
          });
      }
    );
  }

  addRating(newRating: number) {
    let documentReview = new DocumentRating;

    documentReview.documentid = this.specificDocument.id;
    documentReview.user = 'rudi';
    documentReview.rating = newRating;

    this.apiService.postRating(documentReview).then(data => {
        this.apiService.getDocumentDetails(this.route.snapshot.params.id)
          .then(response => {
            this.mapResponseToSpecificDocument(response);
            this.defineRatingForDocumentDetail(this.specificDocument.rating);
            this.userGaveRating = true;
          });

      }
    );
  }

  private mapResponseToSpecificDocument(response: any) {
    this.specificDocument.id = this.response.id;
    this.specificDocument.title = this.response.title;
    this.specificDocument.user = this.response.user;
    this.specificDocument.uploadDate = this.refactorDate(this.response.uploadDate);
    this.specificDocument.description = this.response.description;
    this.specificDocument.school = this.response.school;
    this.specificDocument.departement = this.response.department;
    this.specificDocument.course = this.response.course;
    this.specificDocument.tags = this.response.tags;
    this.specificDocument.rating = this.response.rating;
    this.specificDocument.comments = this.response.comments;
  }

  private defineRatingForDocumentDetail(rating: number) {
    for (let i = 1; i <= rating; i++) {
      this.ratingsForDocumentDetail[i - 1] = {
        "status": "ratingActive",
        "index": i
      };
    }

    for (let i = 5; i > (rating); i--) {
      this.ratingsForDocumentDetail[i - 1] = {
        "status": "ratingInactive",
        "index": i
      };
    }
  };

  private refactorDate(date: string[]): string {
    return this.specificDocument.uploadDate = `${date[2]}.${date[1]}.${date[0]}`
  }

  public downloadFile(): void {
    var a = document.createElement('a');
    var t = atob(this.response.documentAsBytes);
    var byteNumbers = new Array(t.length);
    for (var i = 0; i < t.length; i++) {
      byteNumbers[i] = t.charCodeAt(i);
    }
    var byteArray = new Uint8Array(byteNumbers);
    let blob = new Blob([byteArray], {
      type: this.getMimeType(this.response.fileType)
    });
    let url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = this.response.title + '.' + this.response.fileType;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  }

  private getMimeType(extension:string):string {
    if (extension === 'pdf') {
      return 'application/pdf';
    } else if (extension === 'doc') {
      return 'application/msword';
    } else if (extension === 'docx') {
      return 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    } else if (extension === 'txt') {
      return 'text/plain';
    } else if (extension === 'jpg' || extension === 'jpeg') {
      return 'image/jpeg'
    } else if (extension === 'png') {
      return 'image/png';
    } else if (extension === 'md') {
      return 'text/markdown';
    } else if (extension === 'ppt') {
      return 'application/mspowerpoint';
    } else if (extension === 'pptx') {
      return 'application/vnd.openxmlformats-officedocument.presentationml.presentation'
    }
    return '';
  }
}


