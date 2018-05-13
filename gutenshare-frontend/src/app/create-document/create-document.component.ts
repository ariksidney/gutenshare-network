import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DOCUMENT_TYPES } from '../mock-data/mock-data';
import {ApiService} from "../api/api.service";

@Component({
  selector: 'app-create-document',
  templateUrl: './create-document.component.html',
  styleUrls: ['./create-document.component.scss']
})

export class CreateDocumentComponent implements OnInit {
  predefinedCategories: any = [] ;
  documentTypes: string[] = DOCUMENT_TYPES;
  createDocumentForm: FormGroup;
  documentType: string;
  activeSchool: any = null;
  activeDepartment: any = null;
  activeCourse: any = null;
  filterTextInput: string = '';
  fileName: string = 'Choose file...';

  showErrorMessages = false;
  requiredAlert:string = "* This field is required";
  requiredSchoolAlert:string = "* School is required";
  requiredDepartementAlert:string = "* Departement is required";
  requiredCourseAlert:string = "* Course is required";
  descriptionAlert:string = "* 5 to 500 characters required";

  isDocumentAddedSuccessfully = false;

  constructor(
    private fb: FormBuilder,
    private apiService: ApiService
  ) {
    this.initializeForm();
    this.fetchCategories();
  }

  ngOnInit(){}

  fetchCategories() {
    this.apiService.getCategories()
      .then(response => {
        this.predefinedCategories = response;
      });
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
    this.createDocumentForm.get('course').setValue(course.name);
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
      if (!this.createDocumentForm.valid) {
        this.showErrorMessages = true;
      }
      else {
        let payload = new FormData();

        payload.append('title', post.name);
        payload.append('documenttype', post.type.toUpperCase());
        payload.append('document', post.file);

        let prunedTags: string[] = this.pruneArray(post.tags);
        if (prunedTags.length > 0) {
          prunedTags.forEach(tag => {
            payload.append('tags', tag);
          });
        }

        if (post.description) {
          payload.append('description', post.description);
        }
        if (!post.description) {
          payload.append('description', ' ');
        }
        if (post.school) {
          payload.append('school', post.school);
        }
        if (post.department) {
          payload.append('department', post.department);
        }
        if (post.course) {
          payload.append('course', post.course);
        }

        this.apiService.addDocument(payload).subscribe(
        );

        this.isDocumentAddedSuccessfully = true;
      }
  }

  initializeForm():void {
    this.createDocumentForm = this.fb.group({
      name : [null, Validators.required],
      type : [null, Validators.required],
      file : [null, Validators.required],
      school: [null, Validators.required],
      department: [null, Validators.required],
      course: [null, Validators.required],
      description : [null, Validators.maxLength(500)],
      tags : this.fb.array([new FormControl()])
    });
  }

  resetForm(): void {
    this.createDocumentForm = null;
    this.initializeForm();
    this.isDocumentAddedSuccessfully = false;
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

