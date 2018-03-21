import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DocumentService } from "./document.service";
import { SCHOOLS, DOCUMENT_TYPES } from '../mock-data/mock-data';


@Component({
  selector: 'app-create-document',
  templateUrl: './create-document.component.html',
  styleUrls: ['./create-document.component.scss']
})

export class CreateDocumentComponent implements OnInit {


  schools: any[] = SCHOOLS;
  documentTypes: string[] = DOCUMENT_TYPES;
  createDocumentForm: FormGroup;
  documentType: string;
  activeSchool: any = null;
  activeDepartment: any = null;
  activeCourse: string = null;
  filterTextInput: string = '';
  fileName: string = 'Choose file...';

  requiredAlert:string = "This field is required";
  descriptionAlert:string = "5 to 500 characters required";

  constructor(
    private fb: FormBuilder,
    private documentService: DocumentService
  ) {
    this.initializeForm();
  }

  ngOnInit() {
  }

  pickSchool(school: any): void {
    this.activeSchool = school;
    this.createDocumentForm.get('school').setValue(school.name);
    this.filterTextInput = '';
  }

  pickDepartment(department: any): void {
    this.activeDepartment = department;
    this.createDocumentForm.get('department').setValue(department.name);
    this.filterTextInput = '';
  }

  pickCourse(course: any): void {
    this.activeCourse = course;
    this.createDocumentForm.get('course').setValue(course);
    this.filterTextInput = '';
  }

  resetSchool(): void {
    this.activeSchool = null;
    this.createDocumentForm.get('school').setValue(null);
    this.resetDepartment();
  }

  resetDepartment(): void {
    this.activeDepartment = null;
    this.createDocumentForm.get('department').setValue(null);
    this.resetCourse();
  }

  resetCourse(): void {
    this.activeCourse = null;
    this.createDocumentForm.get('course').setValue(null);
    this.filterTextInput = '';
  }

  setDocumentType(n : number): void {
    this.documentType = this.documentTypes[n];
  }

  setFile(event): void {
    if(event.target.files.length == 1) {
      let file = event.target.files[0];
      this.createDocumentForm.get('file').setValue(file);
    }
  }

  postDocument(post): void {
    let payload = new FormData();
    payload.append('title', post.name);
    payload.append('document', post.file);
    this.pruneArray(post.tags).forEach(tag => {
      payload.append('tags', tag);
    });
    payload.append('documenttype', post.type.toLowerCase());
    payload.append('description', post.description);

    this.documentService.addDocument(payload).subscribe(
      resp => console.log(resp),
    );
  }

  initializeForm():void {
    this.createDocumentForm = this.fb.group({
      name : [null, Validators.required],
      type : [null, Validators.required],
      school: [null],
      department: [null],
      course: [null],
      description : [null, Validators.compose([Validators.maxLength(500)])],
      tags : this.fb.array([new FormControl()]),
      file : [null, Validators.required]
    });
  }

  addTag(): void {
    (this.createDocumentForm.get('tags') as FormArray).push(new FormControl());
  }

  removeTag(i : number): void {
    (this.createDocumentForm.get('tags') as FormArray).removeAt(i);
  }

  pruneArray(array: string[]): string[] {

    let arrayCleaned: string[] = [];

    if (array == null || array == undefined || array.length == 0) {
      return arrayCleaned;
    }

    array.forEach(tag => {
      if (tag != undefined && tag != null && tag.trim() != '') {
        arrayCleaned.push(tag.trim().toUpperCase());
      }
    });

    return arrayCleaned.filter((tag, index, arrayCleaned) => {
      if (arrayCleaned.indexOf(tag) == index) {
        return tag;
      }
    });
  }

}

