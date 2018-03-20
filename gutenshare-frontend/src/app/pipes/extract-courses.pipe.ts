import { Pipe, PipeTransform } from '@angular/core';
import {forEach} from "@angular/router/src/utils/collection";

@Pipe({
  name: 'extractCourses'
})

export class ExtractCoursesPipe implements PipeTransform {

  transform(schools: any[]): string[] {

    if(!schools) return [];

    let courses: string[] = new Array();

    schools.forEach(school => {
      school.departments.forEach(department => {
        department.courses.forEach(course => {
          courses.push(course);
        });
      });
    });

    return courses;
  }

}
