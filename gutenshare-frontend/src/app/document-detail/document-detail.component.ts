import {Component, OnInit} from "@angular/core";
import {ApiService} from "../api/api.service";
import {ActivatedRoute} from "@angular/router";
import {DocumentRating} from "./document-rating";
import {DocumentComment} from "./document-comment";
import {DocumentDetail} from "./document-detail";
import {forEach} from "@angular/router/src/utils/collection";

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
    this.specificDocument.id = response.id;
    this.specificDocument.title = response.title;
    this.specificDocument.user = response.user;
    this.specificDocument.uploadDate = this.refactorDate(this.response.uploadDate);
    this.specificDocument.description = response.description;
    this.specificDocument.school = response.school;
    this.specificDocument.departement = response.department;
    this.specificDocument.course = response.course;
    this.specificDocument.tags = response.tags;
    this.specificDocument.rating = response.rating;
    this.specificDocument.comments = this.mapComments(response.comments);
  }

  private mapComments(comments: any[]): DocumentComment[] {
    let allComments: DocumentComment[] = [];
    for (let i = 0; i < comments.length; i++) {
      let documentComment = new DocumentComment;
      documentComment.content = comments[i].comment;
      documentComment.user = comments[i].user;
      documentComment.documentid = this.specificDocument.id;
      allComments.push(documentComment);
    }

    return allComments;
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
    let a = document.createElement('a');
    let t = atob(this.response.documentAsBytes);
    let byteNumbers = new Array(t.length);
    for (let i = 0; i < t.length; i++) {
      byteNumbers[i] = t.charCodeAt(i);
    }
    let byteArray = new Uint8Array(byteNumbers);
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


