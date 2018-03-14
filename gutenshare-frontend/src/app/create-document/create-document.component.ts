import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { DocumentService } from "./document.service";
import { Document } from './document';

@Component({
  selector: 'app-create-document',
  templateUrl: './create-document.component.html',
  styleUrls: ['./create-document.component.scss']
})

export class CreateDocumentComponent implements OnInit {

  schools = [
    {
      'name': 'zhaw',
      'departments': [
        {
          'name': 'School of Engineering',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        },
        {
          'name': 'School of Economics',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        },
        {
          'name': 'School of Law',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        }
      ]
    },
    {
      'name': 'ethz',
      'departments': [
        {
          'name': 'Physics',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        },
        {
          'name': 'Chemistry',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        },
        {
          'name': 'Biology',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        }
      ]
    },
    {
      'name': 'UZH',
      'departments': [
        {
          'name': 'DüSe',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        },
        {
          'name': 'Linguistik',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        },
        {
          'name': 'Mathematik',
          'courses': ['nmit1', 'psit3', 'ctit1', 'dab2', 'wing1', 'swen1']
        }
      ]
    },
  ];

  documentTypes: string[];
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
    this.documentTypes = ['Summary', 'Book', 'Transcript', 'Exercise', 'Exam'];
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

  postDocument(post): void {
    let doc = new Document();
    doc.name = post.name;
    doc.type = post.type;
    doc.tags = this.pruneArray(post.tags);
    doc.description = post.description;
    doc.storageUrl = "test.com";
    doc.school = post.school;
    doc.department = post.department;
    doc.course = post.department;

    console.log(post);
    console.log(doc);
    // this.documentService.addDocument(doc).subscribe(resp => console.log(resp));
  }

  initializeForm():void {
    this.createDocumentForm = this.fb.group({
      name : [null, Validators.required],
      type : [null, Validators.required],
      school: [null],
      department: [null],
      course: [null],
      description : [null, Validators.compose([Validators.maxLength(500)])],
      tags : this.fb.array([new FormControl()])
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

    for (let i = 0; i < array.length; i++) {
      if (array[i] != null && array[i] != undefined && array[i] != '') {
        arrayCleaned.push(array[i]);
      }
    }
    return arrayCleaned;
  }

}

