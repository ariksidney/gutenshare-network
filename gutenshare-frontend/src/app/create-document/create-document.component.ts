import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {DocumentService} from "./document.service";
import {Document} from './document';

@Component({
  selector: 'app-create-document',
  templateUrl: './create-document.component.html',
  styleUrls: ['./create-document.component.css']
})
export class CreateDocumentComponent implements OnInit {

  documentTypes: String[];
  createDocumentForm: FormGroup;
  documentType: String;

  requiredAlert:string = "This field is required";
  descriptionAlert:string = "5 to 500 characters required";

  constructor(
    private fb: FormBuilder,
    private documentService: DocumentService
  ) {
    this.documentTypes = ['Summary', 'Book', 'Transcript', 'Exercise'];
    this.createForm();
  }

  ngOnInit() {
  }

  setDocumentType(n : number) {
    this.documentType = this.documentTypes[n];
  }

  doPost(post) {
    let doc = new Document();
    doc.name = post.name;
    doc.type = post.type;
    doc.tags = post.tags;
    doc.description = post.description;
    doc.storageUrl = "test.com";

    this.documentService.addDocument(doc).subscribe(resp => console.log(resp));
  }

  createForm() {
    this.createDocumentForm = this.fb.group({
      name : [null, Validators.required],
      type : [null, Validators.required],
      description : [null, Validators.compose([
        Validators.required, Validators.minLength(5), Validators.maxLength(500)])],
      tags : this.fb.array([new FormControl()]),
    });
  }

  addTag() {
    (this.createDocumentForm.get('tags') as FormArray).push(new FormControl());
  }

  removeTag(i : number) {
    (this.createDocumentForm.get('tags') as FormArray).removeAt(i);
  }

}

