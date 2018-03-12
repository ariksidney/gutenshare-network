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

  activeSchool: any = null;
  activeDepartment: any = null;
  activeCourse: any = null;

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

  pickSchool(school: any) {
    this.activeSchool = school;
  }

  pickDepartment(department: any) {
    this.activeDepartment = department;
  }

  pickCourse(course: any) {
    this.activeCourse = course;
  }

  createCourse(course: string) {
    this.activeCourse = course;
  }

  resetSchool() {
    this.activeSchool = null;
    this.resetDepartment();
  }

  resetDepartment() {
    this.activeDepartment = null;
    this.resetCourse();
  }

  resetCourse() {
    this.activeCourse = null;
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
    doc.school = post.school;
    doc.department = post.department;
    doc.course = post.course;

    this.documentService.addDocument(doc).subscribe(resp => console.log(resp));
  }

  createForm() {
    this.createDocumentForm = this.fb.group({
      name : [null, Validators.required],
      type : [null, Validators.required],
      description : [null, Validators.compose([
        Validators.required, Validators.minLength(5), Validators.maxLength(500)])],
      tags : this.fb.array([new FormControl()]),
      school : [null, Validators.required],
      department : [null, Validators.required],
      course : [null, Validators.required]
    });
  }

  addTag() {
    (this.createDocumentForm.get('tags') as FormArray).push(new FormControl());
  }

  removeTag(i : number) {
    (this.createDocumentForm.get('tags') as FormArray).removeAt(i);
  }

}

