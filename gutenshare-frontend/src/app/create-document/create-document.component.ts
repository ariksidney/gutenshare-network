import {Component, OnInit, Pipe, PipeTransform} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-create-document',
  templateUrl: './create-document.component.html',
  styleUrls: ['./create-document.component.css']
})
export class CreateDocumentComponent implements OnInit {

  documentTypes: String[];

  createDocumentForm: FormGroup;
  post:any;                     // A property for our submitted form
  name:string = '';
  type:string = '';
  description:string = '';

  requiredAlert:string = "This field is required";
  descriptionAlert:string = "5 to 500 characters required";

  constructor(private fb: FormBuilder) {
    this.documentTypes = ['Summary', 'Book', 'Transcript', 'Exercise'];
    this.createForm();
  }

  addPost(post) {
    this.name = post.name;
    this.type = post.type;
    this.description = post.description;
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
    <FormArray>this.createDocumentForm.get('tags').push(new FormControl());
  }

  removeTag(i : number) {
    <FormArray>this.createDocumentForm.get('tags').removeAt(i);
  }

  ngOnInit() {
  }

}

